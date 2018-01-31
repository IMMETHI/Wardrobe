package com.example.ktbffh.wardrobe.view;

import android.support.v7.widget.LinearLayoutManager;

import com.example.ktbffh.wardrobe.model.Pants;

import java.util.List;

/**
 * Created by ktbffh on 28/01/18.
 */

public interface FragmentPantView {

    void showMainProgressBar();

    void showThisErrorMsg();

    void removeThisErrorMsg();

    void showOrUpdateRecyclerView(List<Pants> products);


    LinearLayoutManager getLayoutManager();

    void getFavPant();

    void getPantsList();

    void setItemPosition(int index);

}
