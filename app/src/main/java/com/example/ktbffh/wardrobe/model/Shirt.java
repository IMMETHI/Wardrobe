package com.example.ktbffh.wardrobe.model;

import android.graphics.Bitmap;

/**
 * Created by ktbffh on 25/01/18.
 */

public class Shirt {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    int id;

    String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
}
