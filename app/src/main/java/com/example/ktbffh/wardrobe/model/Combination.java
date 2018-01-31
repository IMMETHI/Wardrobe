package com.example.ktbffh.wardrobe.model;

/**
 * Created by ktbffh on 27/01/18.
 */

public class Combination {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shirt getShirt() {
        return shirt;
    }

    public void setShirt(Shirt shirt) {
        this.shirt = shirt;
    }

    public Pants getPants() {
        return pants;
    }

    public void setPants(Pants pants) {
        this.pants = pants;
    }

    int id;
   Shirt shirt;
   Pants pants;

   int shirtIndex;

    public int getShirtIndex() {
        return shirtIndex;
    }

    public void setShirtIndex(int shirtIndex) {
        this.shirtIndex = shirtIndex;
    }

    public int getPantIndex() {
        return pantIndex;
    }

    public void setPantIndex(int pantIndex) {
        this.pantIndex = pantIndex;
    }

    int pantIndex;


}
