package com.example.ktbffh.wardrobe.Interface;

import com.example.ktbffh.wardrobe.model.Shirt;

import java.util.List;

/**
 * Created by ktbffh on 29/01/18.
 */

public interface FragmentInteractionListenerShirt {
    public void OnAddtionItem(int type);

    void OnFavShirt(Shirt shirt);

    void setShirtList(List<Shirt> shirtList);

    void currentItem(Shirt shirt);
}
