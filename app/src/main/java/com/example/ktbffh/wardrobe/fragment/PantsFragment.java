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
import com.example.ktbffh.wardrobe.Interface.FragmentInteractionListenerPant;
import com.example.ktbffh.wardrobe.R;
import com.example.ktbffh.wardrobe.adapter.PantsAdapter;
import com.example.ktbffh.wardrobe.model.Pants;
import com.example.ktbffh.wardrobe.presenters.FragmentPantPresenter;
import com.example.ktbffh.wardrobe.service.PantsService;
import com.example.ktbffh.wardrobe.utils.AppConstant;
import com.example.ktbffh.wardrobe.view.FragmentPantView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ktbffh on 25/01/18.
 */

public class PantsFragment extends Fragment implements FragmentPantView, FavClicked {

    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.introMsg)
    TextView introMsg;
    FragmentInteractionListenerPant mListener;
    PantsAdapter pantsAdapter;
    List<Pants> pantsList = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private LinearLayoutManager horizontalLayoutManagaer;
    private FragmentPantPresenter fragmentPantPresenter;
    PantsService pantsService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.paint_layout, container, false);
        ButterKnife.bind(this, view);
        SnapHelper snapHelper = new PagerSnapHelper();
        pantsService = new PantsService(getActivity());
        horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        snapHelper.attachToRecyclerView(recyclerView);
        fragmentPantPresenter = new FragmentPantPresenter(getActivity(), mListener, snapHelper, this, recyclerView, pantsService);
        return view;
    }


    @Override
    public void showOrUpdateRecyclerView(List<Pants> pants) {
        if (pantsAdapter == null) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(horizontalLayoutManagaer);
            pantsList.addAll(pants);
            pantsAdapter = new PantsAdapter(getContext(), pantsList);
            recyclerView.setAdapter(pantsAdapter);
            ;
        } else {
            pantsList.addAll(pants);
            fragmentPantPresenter.UpdateList(pantsList);
            pantsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return horizontalLayoutManagaer;
    }

    @Override
    public void getFavPant() {
        fragmentPantPresenter.onFavClicked();
    }

    @Override
    public void getPantsList() {
        fragmentPantPresenter.getPantsList();
    }

    @Override
    public void setItemPosition(int index) {
        fragmentPantPresenter.setItemPosition(index);
    }

    @Override
    public void OnFavClicked() {
        fragmentPantPresenter.onFavClicked();
    }

    @OnClick(R.id.add)
    void onClick() {
        mListener.OnAddtionItem(AppConstant.Paint_Type);
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListenerPant) {
            mListener = (FragmentInteractionListenerPant) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}
