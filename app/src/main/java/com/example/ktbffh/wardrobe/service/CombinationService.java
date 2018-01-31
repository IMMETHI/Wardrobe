package com.example.ktbffh.wardrobe.service;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.ktbffh.wardrobe.database.DbUtils;
import com.example.ktbffh.wardrobe.model.Combination;
import com.example.ktbffh.wardrobe.model.Pants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ktbffh on 27/01/18.
 */

public class CombinationService extends AsyncTaskLoader<Combination> {
    Context context;

    public CombinationService(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Combination loadInBackground() {
        DbUtils dbUtils = new DbUtils(context);
        Combination combination =new Combination();
        combination=dbUtils.getCombination();
        return combination;
    }
}
