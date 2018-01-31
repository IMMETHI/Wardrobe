package com.example.ktbffh.wardrobe.service;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;


import com.example.ktbffh.wardrobe.database.DbHandler;
import com.example.ktbffh.wardrobe.database.DbUtils;
import com.example.ktbffh.wardrobe.model.Pants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ktbffh on 26/01/18.
 */

public class PantsService  extends AsyncTaskLoader<List<Pants>> {
    Context context;

    public PantsService(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public List<Pants> loadInBackground() {
        DbUtils dbUtils = new DbUtils(context);
        List<Pants> pantsList = new ArrayList<>();
        pantsList.addAll(dbUtils.getAllPants());
        return pantsList;
    }
}