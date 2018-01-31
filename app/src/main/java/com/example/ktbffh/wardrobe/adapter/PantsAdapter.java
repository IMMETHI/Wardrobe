package com.example.ktbffh.wardrobe.adapter;

import android.app.LoaderManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.ktbffh.wardrobe.R;
import com.example.ktbffh.wardrobe.model.Pants;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ktbffh on 26/01/18.
 */

public class PantsAdapter extends RecyclerView.Adapter<PantsAdapter.ResultsHolder> {
    List<Pants> pantsList=new ArrayList<>();
    Context context;

    public PantsAdapter( Context context,List<Pants> pants) {
        this.pantsList = pants;
        this.context = context;
    }


    public class ResultsHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ResultsHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.image);
        }
    }

    @Override
    public PantsAdapter.ResultsHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shirt_item_layout, parent, false);
        return new ResultsHolder(view);
    }


    @Override
    public void onBindViewHolder(ResultsHolder holder, final int position) {
            File f=new File(pantsList.get(position).getImage(),pantsList.get(position).getName());
            Picasso.with(context).load(new File(f.getAbsolutePath())).fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return pantsList.size();
    }

}
