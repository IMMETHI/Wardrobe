package com.example.ktbffh.wardrobe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ktbffh.wardrobe.Interface.FavClicked;
import com.example.ktbffh.wardrobe.Interface.FragmentInteractionListenerShirt;
import com.example.ktbffh.wardrobe.R;
import com.example.ktbffh.wardrobe.adapter.ShirtsAdapter;
import com.example.ktbffh.wardrobe.model.Shirt;
import com.example.ktbffh.wardrobe.presenters.FragmentShirtPresenter;
import com.example.ktbffh.wardrobe.service.ShirtService;
import com.example.ktbffh.wardrobe.utils.AppConstant;
import com.example.ktbffh.wardrobe.view.FragmentShirtView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ktbffh on 25/01/18.
 */

public class ShirtsFragment extends Fragment implements FragmentShirtView, FavClicked {
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.introMsg)
    TextView introMsg;
    FragmentInteractionListenerShirt mListener;
    ShirtsAdapter shirtsAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private LinearLayoutManager horizontalLayoutManagaer;
    private FragmentShirtPresenter fragmentShirtPresenter;
    ShirtService shirtService;
    List<Shirt> shirtList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shirt_layout, container, false);
        ButterKnife.bind(this, view);
        SnapHelper snapHelper = new PagerSnapHelper();
        shirtService = new ShirtService(getActivity());
        horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        snapHelper.attachToRecyclerView(recyclerView);
        fragmentShirtPresenter = new FragmentShirtPresenter(getActivity(), mListener, snapHelper, this, recyclerView, shirtService);
        return view;
    }

    @Override
    public void showOrUpdateRecyclerView(List<Shirt> shirts) {
        if (shirtsAdapter == null) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(horizontalLayoutManagaer);
            shirtList.addAll(shirts);
            shirtsAdapter = new ShirtsAdapter(getContext(), shirtList);
            recyclerView.setAdapter(shirtsAdapter);
            ;
        } else {
            shirtList.addAll(shirts);
            fragmentShirtPresenter.Updatelist(shirtList);
            shirtsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return horizontalLayoutManagaer;
    }

    @Override
    public void getFavShirt() {
        fragmentShirtPresenter.onFavClicked();
    }

    @Override
    public void getShirtList() {
        fragmentShirtPresenter.getShirtList();
    }

    public void setItemPosition(int index) {
        fragmentShirtPresenter.setItemPosition(index);
    }

    @Override
    public void UpdateList(Shirt shirt) {

    }

    @Override
    public void OnFavClicked() {
        fragmentShirtPresenter.onFavClicked();
    }

    @Override
    public void showMainProgressBar() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showThisErrorMsg() {
        introMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeThisErrorMsg() {
        introMsg.setVisibility(View.GONE);
    }

    @OnClick(R.id.add)
    void onClick() {
        mListener.OnAddtionItem(AppConstant.Shirt_Type);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListenerShirt) {
            mListener = (FragmentInteractionListenerShirt) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

}
