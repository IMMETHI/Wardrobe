package com.example.ktbffh.wardrobe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ktbffh.wardrobe.model.Combination;
import com.example.ktbffh.wardrobe.model.Favourite;
import com.example.ktbffh.wardrobe.model.Pants;
import com.example.ktbffh.wardrobe.model.Shirt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ktbffh on 26/01/18.
 */

public class DbUtils {
    Context context;
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;
    private static final String TABLE_Shirt = "shirt";
    private static final String TABLE_Pant = "pant";
    private static final String TABLE_Fav = "favourite";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_IMAGE_NAME = "image_name";
    private static final String KEY_Shirt_ID = "Shirt_Id";
    private static final String KEY_Pant_ID = "Paint_Id";

    public DbUtils(Context context) {
        dbhandler = new DbHandler(context);
    }

    public List<Shirt> getAllShirts() {
        List<Shirt> shirtList = new ArrayList<Shirt>();
        String selectQuery = "SELECT  * FROM " + TABLE_Shirt;
        db = dbhandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Shirt td = new Shirt();
                td.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                td.setName(cursor.getString(cursor.getColumnIndex(KEY_IMAGE_NAME)));
                td.setImage(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));
                shirtList.add(td);
            } while (cursor.moveToNext());
        }
        db.close();
        return shirtList;
    }


    public List<Pants> getAllPants() {
        List<Pants> pantsList = new ArrayList<Pants>();
        String selectQuery = "SELECT  * FROM " + TABLE_Pant;
        db = dbhandler.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Pants td = new Pants();
                td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                td.setName(c.getString(c.getColumnIndex(KEY_IMAGE_NAME)));
                td.setImage(c.getString(c.getColumnIndex(KEY_IMAGE)));
                // adding to todo list
                pantsList.add(td);
            } while (c.moveToNext());
        }
        dbhandler.close();
        return pantsList;
    }

    public Shirt addShirt(Shirt shirt) {
        db = dbhandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE, shirt.getImage());
        values.put(KEY_IMAGE_NAME, shirt.getName());
        long insertid = db.insert(TABLE_Shirt, null, values);
        db.close();
        return shirt;
    }

    public Pants addPaint(Pants pants) {
        db = dbhandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE, pants.getImage());
        values.put(KEY_IMAGE_NAME, pants.getName());
        long insertid = db.insert(TABLE_Pant, null, values);
        db.close();
        return pants;
    }

    public Favourite addFav(Favourite favourite) {
        db = dbhandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Pant_ID, favourite.getPant_id());
        values.put(KEY_Shirt_ID, favourite.getShirt_id());
        long insertid = db.insert(TABLE_Fav, null, values);
        db.close();
        return favourite;
    }

    public Combination getCombination() {
        Combination combination = new Combination();
        String selectRandomShirtQuery = "SELECT * FROM " + TABLE_Shirt + " ORDER BY RANDOM() LIMIT 1";
        String selectRandomPantQuery = "SELECT * FROM " + TABLE_Pant + " ORDER BY RANDOM() LIMIT 1";
        db = dbhandler.getWritableDatabase();
        Cursor c = db.rawQuery(selectRandomShirtQuery, null);
        if (c.moveToFirst()) {
            Shirt td = new Shirt();
            td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            td.setName(c.getString(c.getColumnIndex(KEY_IMAGE_NAME)));
            td.setImage(c.getString(c.getColumnIndex(KEY_IMAGE)));
            combination.setShirt(td);
        }
        c.close();

        Cursor cursor = db.rawQuery(selectRandomPantQuery, null);
        if (cursor.moveToFirst()) {
            Pants td = new Pants();
            td.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            td.setName(cursor.getString(cursor.getColumnIndex(KEY_IMAGE_NAME)));
            td.setImage(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));
            combination.setPants(td);
        }
        return combination;
    }
    public boolean checkFavourite(Shirt currentShirt, Pants currentPant) {
        String selectFav = "SELECT * FROM " + TABLE_Fav + " where " + KEY_Shirt_ID + "=" + currentShirt.getId() +" AND " + KEY_Pant_ID + "=" + currentPant.getId();
        db = dbhandler.getWritableDatabase();
        Cursor c = db.rawQuery(selectFav, null);
        if(c!=null && c.getCount()>0)
        {
            return true;
        }
        else {
            return false;
        }
    }


}


