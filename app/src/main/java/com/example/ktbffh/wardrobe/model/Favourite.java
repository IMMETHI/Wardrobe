package com.example.ktbffh.wardrobe.model;

/**
 * Created by ktbffh on 27/01/18.
 */

public class Favourite {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShirt_id() {
        return shirt_id;
    }

    public void setShirt_id(int shirt_id) {
        this.shirt_id = shirt_id;
    }

    public int getPant_id() {
        return pant_id;
    }

    public void setPant_id(int pant_id) {
        this.pant_id = pant_id;
    }

    int id;
    int shirt_id;
    int pant_id;
}
