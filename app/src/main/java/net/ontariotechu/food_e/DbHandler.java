package net.ontariotechu.food_e;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "foode";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "savedrecipes";
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
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_COL + " TEXT, "
                + IMAGEURL_COL + " TEXT, "
                + SAVED_RECIPE_URI_COL + " TEXT)";
        db.execSQL(query);
    }

    /**
     * Runs when the db is upgraded.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
