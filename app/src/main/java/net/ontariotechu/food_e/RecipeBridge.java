package net.ontariotechu.food_e;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class RecipeBridge {
    DbHandler dbHandler;

    /**
     * Pass in a dbHandler, retrieved by eg.:
     * new DbHandler(MainActivity.this);
     * @param dbHandler
     */
    public RecipeBridge(DbHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    /**
     * Add the recipe to the database
     * @param recipe
     */
    public void add(Recipe recipe) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHandler.SAVED_RECIPE_URI_COL, recipe.getUri());
        values.put(DbHandler.TITLE_COL, recipe.getTitle());
        values.put(DbHandler.IMAGEURL_COL, recipe.getImageUrl());
        db.insert(DbHandler.RECIPE, null, values);
        db.close();
    }

    /**
     * Get recipes from db matching the input recipeId and/or title
     * @param recipeId
     * @param title
     * @return Recipes found
     */
    public Recipe[] get(String recipeId, String title) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        ArrayList<String> filters = new ArrayList<>();

        if (recipeId != null && !recipeId.isEmpty()) {
            filters.add(DbHandler.SAVED_RECIPE_URI_COL + " LIKE %" + recipeId);
        }

        if (title != null && !title.isEmpty()) {
            filters.add(DbHandler.TITLE_COL + " = " + title);
        }

        String query = "SELECT * FROM " + DbHandler.RECIPE;

        if (filters.size() > 0) {
            query += " WHERE ";

            for (String filter : filters) {
                query += filter + " AND ";
            }

            query = query.substring(0, query.length() - 5);
        }

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Recipe> recipes = new ArrayList<>();
        while (cursor.moveToNext()) {
            Recipe recipe = new Recipe(cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.TITLE_COL)));
            recipe.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.IMAGEURL_COL)));
            recipe.setUri(cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.SAVED_RECIPE_URI_COL)));
            recipes.add(recipe);
        }
        cursor.close();

        Recipe[] results = new Recipe[recipes.size()];
        results = recipes.toArray(results);

        return results;
    }

    /**
     * Get all recipes
     * @return All recipes in database
     */
    public Recipe[] getAll() {
        return get(null, null);
    }

    /**
     * Deletes the recipe with the given uri from the database
     * @param uri
     */
    public void delete(String uri) {
        if (uri == null || uri.isEmpty()) {
            throw new IllegalArgumentException("Tried to delete all rows from the database");
        }
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(DbHandler.RECIPE, DbHandler.SAVED_RECIPE_URI_COL + " LIKE %?", new String[] { uri });
        db.close();
    }
}
