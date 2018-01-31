package com.example.ktbffh.wardrobe.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ktbffh on 26/01/18.
 */

public class DbHandler extends SQLiteOpenHelper {
    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WardWore";

    private static final String TABLE_Shirt = "shirt";
    private static final String TABLE_Pant = "pant";
    private static final String TABLE_Fav = "favourite";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_IMAGE_NAME = "image_name";
    private static final String KEY_Shirt_ID = "Shirt_Id";
    private static final String KEY_Pant_ID = "Paint_Id";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_Shirt = "CREATE TABLE "
            + TABLE_Shirt + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_IMAGE_NAME + " String,"
            + KEY_IMAGE + " String" + ")";

    private static final String CREATE_TABLE_PANT = "CREATE TABLE "
            + TABLE_Pant + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_IMAGE_NAME + " String,"
            + KEY_IMAGE + " String" + ")";

    private static final String CREATE_TABLE_Fav = "CREATE TABLE "
            + TABLE_Fav + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_Shirt_ID + " INTEGER,"
            + KEY_Pant_ID + " INTEGER" + ")";


    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Shirt);
        db.execSQL(CREATE_TABLE_PANT);
        db.execSQL(CREATE_TABLE_Fav);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Pant);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Shirt);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Fav);
        onCreate(db);
    }
}
