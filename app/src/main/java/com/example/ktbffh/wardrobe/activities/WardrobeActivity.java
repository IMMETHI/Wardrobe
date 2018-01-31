package com.example.ktbffh.wardrobe.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.ktbffh.wardrobe.BaseActivity;
import com.example.ktbffh.wardrobe.Interface.FragmentInteractionListenerPant;
import com.example.ktbffh.wardrobe.Interface.FragmentInteractionListenerShirt;
import com.example.ktbffh.wardrobe.R;
import com.example.ktbffh.wardrobe.view.WardRobeView;
import com.example.ktbffh.wardrobe.database.DbHandler;
import com.example.ktbffh.wardrobe.database.DbUtils;
import com.example.ktbffh.wardrobe.fragment.PantsFragment;
import com.example.ktbffh.wardrobe.fragment.ShirtsFragment;
import com.example.ktbffh.wardrobe.model.Pants;
import com.example.ktbffh.wardrobe.model.Shirt;
import com.example.ktbffh.wardrobe.presenters.WardrobePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WardrobeActivity extends BaseActivity implements
        WardRobeView, FragmentInteractionListenerPant, FragmentInteractionListenerShirt {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.suffle)
    ImageView suffle;
    @BindView(R.id.fav)
    ImageView fav;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    DbHandler dbHandler;
    DbUtils dbUtils;
    int type;
    ShirtsFragment shirtsFragment;
    PantsFragment pantsFragment;
    WardrobePresenter wardrobePresenter;
    List<Pants> pantsList;
    List<Shirt> shirtList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        dbUtils = new DbUtils(this);
        shirtsFragment = new ShirtsFragment();
        pantsFragment = new PantsFragment();
        wardrobePresenter = new WardrobePresenter(getApplicationContext(), this, shirtsFragment, pantsFragment, dbUtils);
        setShirtFragment(shirtsFragment);
        setPantFragment(pantsFragment);
    }

    public void setShirtFragment(Fragment frag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.shirt, frag, "Shirt");
        fragmentTransaction.commitAllowingStateLoss();

    }

    public void setPantFragment(Fragment frag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.pant, frag, "pant");
        fragmentTransaction.commitAllowingStateLoss();

    }

    @OnClick(R.id.fav)
    void onFavClicked() {
        OnFavClicked();
    }

    @OnClick(R.id.suffle)
    void onSuffleClicked() {
        OnSuffleClicked();
    }

    @Override
    public void OnFavClicked() {
        wardrobePresenter.getFavPicked();
    }

    @Override
    public void OnSuffleClicked() {
        shirtsFragment.getShirtList();
        pantsFragment.getPantsList();
        wardrobePresenter.suffle(shirtList, pantsList);
    }

    @Override
    public Context getContext() {
        return WardrobeActivity.this;
    }

    @Override
    public void changeFavIcon(boolean status) {
        if(status)
        {
            fav.setImageResource(R.drawable.fav_icon_selected);
        }
        else {
            fav.setImageResource(R.drawable.fav_icon_unselected);
        }
    }

    @Override
    public void OnFavPant(Pants pant) {
        wardrobePresenter.OnFavPant(pant);
    }

    @Override
    public void setPainList(List<Pants> pantsList) {
        this.pantsList = pantsList;
    }

    @Override
    public void currentItem(Pants pants) {
       wardrobePresenter.checkFavouriteForPant(pants);
    }

    @Override
    public void OnAddtionItem(int type) {
        this.type = type;
        wardrobePresenter.selectImage(type);
    }

    @Override
    public void OnFavShirt(Shirt shirt) {
        wardrobePresenter.OnFavShirt(shirt);
    }

    @Override
    public void setShirtList(List<Shirt> shirtList) {
        this.shirtList = shirtList;

    }

    @Override
    public void currentItem(Shirt shirt) {
        wardrobePresenter.checkFavouriteForShirt(shirt);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                wardrobePresenter.onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                wardrobePresenter.onCaptureImageResult(data);
        }
    }

}



