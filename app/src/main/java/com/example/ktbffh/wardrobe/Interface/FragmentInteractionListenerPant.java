package com.example.ktbffh.wardrobe.Interface;

import com.example.ktbffh.wardrobe.model.Pants;

import java.util.List;

/**
 * Created by ktbffh on 29/01/18.
 */

public interface FragmentInteractionListenerPant {
    void OnAddtionItem(int type);

    void OnFavPant(Pants pant);

    void setPainList(List<Pants> pantsList);

    void currentItem(Pants pants);
}
