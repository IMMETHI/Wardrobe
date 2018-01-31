package com.example.ktbffh.wardrobe.service;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;


import com.example.ktbffh.wardrobe.database.DbHandler;
import com.example.ktbffh.wardrobe.database.DbUtils;
import com.example.ktbffh.wardrobe.model.Shirt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ktbffh on 26/01/18.
 */

public class ShirtService extends AsyncTaskLoader<List<Shirt>> {
    Context context;
    public ShirtService(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public List<Shirt> loadInBackground() {
        DbUtils dbUtils=new DbUtils(context);
        List<Shirt> shirtList=new ArrayList<>();
        shirtList.addAll(dbUtils.getAllShirts());
        return shirtList;
    }
}
