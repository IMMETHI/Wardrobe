package com.example.ktbffh.wardrobe.view;

import android.content.Context;

/**
 * Created by ktbffh on 28/01/18.
 */

public interface WardRobeView {

    void OnFavClicked();

    void OnSuffleClicked();

    Context getContext();

    void changeFavIcon(boolean status);
}
