package com.example.ktbffh.wardrobe.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ktbffh.wardrobe.model.Shirt;

import java.util.List;

/**
 * Created by ktbffh on 28/01/18.
 */

public interface FragmentShirtView {
    void showMainProgressBar();

    void showThisErrorMsg();

    void removeThisErrorMsg();

    void showOrUpdateRecyclerView(List<Shirt> products);

    LinearLayoutManager getLayoutManager();

    void getFavShirt();

    void getShirtList();

    void setItemPosition(int index);

    public void UpdateList(Shirt shirt);
}
