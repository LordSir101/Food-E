package net.ontariotechu.food_e;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "foode";
    private static final int DB_VERSION = 1;
    public static final String RECIPE = "savedrecipes";
    public static final String DIET = "diet";
    public static final String ALLERGY = "allergy";
    private static final String ID_COL = "id";
    public static final String SAVED_RECIPE_URI_COL = "uri";
    public static final String TITLE_COL = "title";
    public static final String IMAGEURL_COL = "imageurl";

    /**
     * Creates a DbHandler that is used to build and communicate with the SqLite DB.
     * You'll need one of these to pass to a bridge for db functions.
     * Create eg. new DbHandler(MainActivity.this)
     * @param context
     */
    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Creates the db the first time the app is run, if the db does not already exist
     * @param db
     */
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + RECIPE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_COL + " TEXT, "
                + IMAGEURL_COL + " TEXT, "
                + SAVED_RECIPE_URI_COL + " TEXT)";
        db.execSQL(query);

        query = "CREATE TABLE " + DIET + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DIET + " TEXT)";

        db.execSQL(query);

        query = "CREATE TABLE " + ALLERGY + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ALLERGY + " TEXT)";

        db.execSQL(query);
    }

    /**
     * Runs when the db is upgraded.
     * @param db
     * @param oldVersion
     * @param newVersion   */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + DIET);
        db.execSQL("DROP TABLE IF EXISTS " + ALLERGY);
        onCreate(db);
    }

    /**
     * Add diets to the database
     * @param diets Diets to add
     */
    public void addDiets(String[] diets) {
        SQLiteDatabase db = getWritableDatabase();
        for (String diet : diets) {
            ContentValues values = new ContentValues();
            values.put(DIET, diet);
            db.insert(DIET, null, values);
        }
        db.close();
    }

    /**
     * Get a list of diets from the database
     * @return All diets in the database
     */
    public String[] getDiets() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DIET, null);

        ArrayList<String> diets = new ArrayList<>();
        while (cursor.moveToNext()) {
            diets.add(cursor.getString(cursor.getColumnIndexOrThrow(DIET)));
        }

        String[] results = new String[diets.size()];
        results = diets.toArray(results);
        return results;
    }

    /**
     * Remove the specified diets from the database
     * @param diets Diets to remove
     */
    public void removeDiets(String[] diets) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DIET, DIET + " = ?", diets);
        db.close();
    }

    /**
     * Add list of allergies to the database
     * @param allergies Allergies to add
     */
    public void addAllergies(String[] allergies) {
        SQLiteDatabase db = getWritableDatabase();
        for (String allergy : allergies) {
            ContentValues values = new ContentValues();
            values.put(ALLERGY, allergy);
            db.insert(ALLERGY, null, values);
        }
        db.close();
    }

    /**
     * Get all allergies from the database
     * @return All allergies in the database
     */
    public String[] getAllergies() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ALLERGY, null);

        ArrayList<String> allergies = new ArrayList<>();
        while (cursor.moveToNext()) {
            allergies.add(cursor.getString(cursor.getColumnIndexOrThrow(ALLERGY)));
        }

        String[] results = new String[allergies.size()];
        results = allergies.toArray(results);
        return results;
    }

    /**
     * Remove specified allergies from the database
     * @param allergies Allergies to remove
     */
    public void removeAllergies(String[] allergies) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ALLERGY, ALLERGY + " = ?", allergies);
        db.close();
    }

    /**
     * Add the recipe to the database
     * @param recipe
     */
    public void addRecipe(Recipe recipe) {
        RecipeBridge bridge = new RecipeBridge(this);
        bridge.add(recipe);
    }

    /**
     * Get recipes from db matching the input recipeId and/or title
     * @param recipeId
     * @param title
     * @return Recipes found
     */
    public Recipe[] getRecipes(String recipeId, String title) {
        RecipeBridge bridge = new RecipeBridge(this);
        return bridge.get(recipeId, title);
    }

    /**
     * Get all recipes
     * @return All recipes in database
     */
    public Recipe[] getAllRecipes() {
        RecipeBridge bridge = new RecipeBridge(this);
        return bridge.getAll();
    }

    /**
     * Deletes the recipe with the given uri from the database
     * @param uri
     */
    public void removeRecipe(String uri) {
        RecipeBridge bridge = new RecipeBridge(this);
        bridge.delete(uri);
    }
}
