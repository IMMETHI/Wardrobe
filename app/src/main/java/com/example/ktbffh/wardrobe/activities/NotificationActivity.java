package com.example.ktbffh.wardrobe.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ktbffh.wardrobe.BaseActivity;
import com.example.ktbffh.wardrobe.R;
import com.example.ktbffh.wardrobe.model.Combination;
import com.example.ktbffh.wardrobe.service.CombinationService;
import com.example.ktbffh.wardrobe.widgets.SquareImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ktbffh on 28/01/18.
 */

public class NotificationActivity extends BaseActivity {
    @BindView(R.id.pant)
    SquareImageView pant;
    @BindView(R.id.shirt)
    SquareImageView shirt;
    @BindView(R.id.notFound)
    TextView notFound;
    @BindView(R.id.main_layout)
    LinearLayout mainLayout;
    CombinationService combinationService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        ButterKnife.bind(this);
        combinationService=new CombinationService(this);
        Combination combination=combinationService.loadInBackground();
        if(combination!=null && combination.getPants()!=null && combination.getShirt()!=null) {
            File pantFile = new File(combination.getPants().getImage(), combination.getPants().getName());
            Picasso.with(this).load(new File(pantFile.getAbsolutePath())).fit().centerCrop().into(pant);
            File shirtFile = new File(combination.getShirt().getImage(), combination.getShirt().getName());
            Picasso.with(this).load(new File(shirtFile.getAbsolutePath())).fit().centerCrop().into(shirt);
        }
        else {
            mainLayout.setVisibility(View.GONE);
            notFound.setVisibility(View.VISIBLE);
        }
    }
}
