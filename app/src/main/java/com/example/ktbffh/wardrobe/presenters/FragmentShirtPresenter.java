package com.example.ktbffh.wardrobe.presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.AbsListView;

import com.example.ktbffh.wardrobe.Interface.FragmentInteractionListenerShirt;
import com.example.ktbffh.wardrobe.view.FragmentShirtView;
import com.example.ktbffh.wardrobe.fragment.ShirtsFragment;
import com.example.ktbffh.wardrobe.model.Shirt;
import com.example.ktbffh.wardrobe.service.ShirtService;

import java.util.List;

/**
 * Created by ktbffh on 28/01/18.
 */

public class FragmentShirtPresenter {
    Context context;
    FragmentShirtView shirtview;
    SnapHelper snapHelper;
    ShirtService shirtService;
    int posstion;
    List<Shirt> shirtList;
    RecyclerView recyclerView;
    FragmentInteractionListenerShirt mListner;

    public FragmentShirtPresenter(Context context, FragmentInteractionListenerShirt mListner, SnapHelper snapHelper, FragmentShirtView shirtview, RecyclerView recyclerView, ShirtService shirtService) {
        this.context = context;
        this.snapHelper = snapHelper;
        this.shirtview = shirtview;
        this.mListner=mListner;
        this.shirtService=shirtService;
        this.recyclerView=recyclerView;
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        makeGetProductsRequest();
    }

    private void makeGetProductsRequest() {
        shirtList=shirtService.loadInBackground();
        if(shirtList!=null && shirtList.size()>0) {
            mListner.currentItem(shirtList.get(0));
            shirtview.showOrUpdateRecyclerView(shirtList);
        }
        else {
            mListner.currentItem(null);
            shirtview.showThisErrorMsg();
        }
    }

    public RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                View centerView = snapHelper.findSnapView(shirtview.getLayoutManager());
                if(centerView!=null) {
                    posstion = shirtview.getLayoutManager().getPosition(centerView);
                    mListner.currentItem(shirtList.get(posstion));
                }
            }
        }
    };

    public void onFavClicked()
    {
        if(shirtList.size()>0)
          mListner.OnFavShirt(shirtList.get(posstion));
    }

    public void setItemPosition(int posstion) {
        recyclerView.smoothScrollToPosition(posstion);
    }

    public void getPantsList() {
        mListner.setShirtList(shirtList);
    }

    public void getShirtList() {
        mListner.setShirtList(shirtList);
    }

    public void Updatelist(List<Shirt> shirtList) {
        shirtview.removeThisErrorMsg();
        this.shirtList.clear();
        this.shirtList.addAll(shirtList);
        setItemPosition(this.shirtList.size());
    }
}
