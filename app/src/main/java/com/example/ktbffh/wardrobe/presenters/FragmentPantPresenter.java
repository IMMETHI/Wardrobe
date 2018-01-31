package com.example.ktbffh.wardrobe.presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import com.example.ktbffh.wardrobe.Interface.FragmentInteractionListenerPant;
import com.example.ktbffh.wardrobe.view.FragmentPantView;
import com.example.ktbffh.wardrobe.fragment.PantsFragment;
import com.example.ktbffh.wardrobe.model.Pants;
import com.example.ktbffh.wardrobe.service.PantsService;

import java.util.List;

/**
 * Created by ktbffh on 28/01/18.
 */

public class FragmentPantPresenter {
    Context context;
    FragmentPantView pantView;
    SnapHelper snapHelper;
    PantsService pantsService;
    int posstion;
    List<Pants> pantsList;
    RecyclerView recyclerView;
   FragmentInteractionListenerPant mListner;

    public FragmentPantPresenter(Context context, FragmentInteractionListenerPant mListner, SnapHelper snapHelper, FragmentPantView pantView, RecyclerView recyclerView, PantsService pantsService) {
        this.context = context;
        this.snapHelper = snapHelper;
        this.pantView = pantView;
        this.mListner = mListner;
        this.pantsService = pantsService;
        this.recyclerView = recyclerView;
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        makeGetProductsRequest();
    }

    private void makeGetProductsRequest() {
        pantsList = pantsService.loadInBackground();
        if(pantsList!=null && pantsList.size()>0) {
            mListner.currentItem(pantsList.get(0));
            pantView.showOrUpdateRecyclerView(pantsList);
        }
        else {
            mListner.currentItem(null);
            pantView.showThisErrorMsg();
        }
    }

    public RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                View centerView = snapHelper.findSnapView(pantView.getLayoutManager());
                if(centerView!=null) {
                    posstion = pantView.getLayoutManager().getPosition(centerView);
                    mListner.currentItem(pantsList.get(posstion));
                }
            }
        }
    };

    public void onFavClicked() {
        if(pantsList.size()>0)
        mListner.OnFavPant(pantsList.get(posstion));
    }

    public void setItemPosition(int posstion) {
        recyclerView.smoothScrollToPosition(posstion);
    }

    public void getPantsList() {
        mListner.setPainList(pantsList);
    }

    public void UpdateList(List<Pants> pantsList) {
        pantView.removeThisErrorMsg();
        this.pantsList.clear();
        this.pantsList.addAll(pantsList);
        setItemPosition(this.pantsList.size());
    }
}
