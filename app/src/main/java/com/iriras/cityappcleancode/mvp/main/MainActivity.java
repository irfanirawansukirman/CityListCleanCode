package com.iriras.cityappcleancode.mvp.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iriras.cityappcleancode.R;
import com.iriras.cityappcleancode.api.model.CityListDataModel;
import com.iriras.cityappcleancode.api.model.CityListResponseModel;
import com.iriras.cityappcleancode.api.network.NetworkFetchData;
import com.iriras.cityappcleancode.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView {
//    @BindView(R.id.rec_main_city)
    RecyclerView mRecyclerView;
//    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;

    @Inject
    NetworkFetchData mNetworkFetchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mRecyclerView = (RecyclerView) findViewById(R.id.rec_main_city);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getInjector().inject(this);
        MainPresenter mMainPresenter = new MainPresenter(mNetworkFetchData, this);
        mMainPresenter.onGetCityList();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onSuccessGetCityList(CityListResponseModel cityListResponseModel) {
        MainAdapter mMainAdapter = new MainAdapter(MainActivity.this, cityListResponseModel.getData(), new MainAdapter.OnItemClickListener() {
            @Override
            public void onClick(CityListDataModel cityListDataModel) {
                Toast.makeText(MainActivity.this, cityListDataModel.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(mMainAdapter);
    }
}
