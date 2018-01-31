package com.example.ktbffh.wardrobe.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ktbffh.wardrobe.R;
import com.example.ktbffh.wardrobe.model.Shirt;
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

public class ShirtsAdapter extends RecyclerView.Adapter<ShirtsAdapter.ResultsHolder> {
    List<Shirt> shirtList=new ArrayList<>();
    Context context;
    ShirtsAdapter shirtsAdapter;

    public ShirtsAdapter(Context context,List<Shirt> shirtLists) {
        this.shirtList = shirtLists;
        this.context = context;
    }


    public class ResultsHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;
        CardView cardView;


        public ResultsHolder(View v) {
            super(v);

            imageView = (ImageView) v.findViewById(R.id.image);
        }
    }

    @Override
    public ShirtsAdapter.ResultsHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shirt_item_layout, parent, false);
        return new ShirtsAdapter.ResultsHolder(view);
    }


    @Override
    public void onBindViewHolder(ShirtsAdapter.ResultsHolder holder, final int position) {
            File f=new File(shirtList.get(position).getImage(),shirtList.get(position).getName());
            Picasso.with(context).load(new File(f.getAbsolutePath())).fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return shirtList.size();
    }
}
