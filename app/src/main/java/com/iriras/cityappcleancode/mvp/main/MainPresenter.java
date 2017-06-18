package com.iriras.cityappcleancode.mvp.main;

import com.iriras.cityappcleancode.api.model.CityListResponseModel;
import com.iriras.cityappcleancode.api.network.NetworkError;
import com.iriras.cityappcleancode.api.network.NetworkFetchData;
import com.iriras.cityappcleancode.base.BaseCallback;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by irfan on 6/18/17.
 */

public class MainPresenter {
    private NetworkFetchData mNetworkFetchData;
    private MainView mMainView;
    private CompositeSubscription mCompositeSubscription;

    public MainPresenter(NetworkFetchData mNetworkFetchData,
                         MainView mMainView){
        this.mNetworkFetchData = mNetworkFetchData;
        this.mMainView = mMainView;
        this.mCompositeSubscription = new CompositeSubscription();
    }

    public void onGetCityList(){
        mMainView.showProgress();
        Subscription mSubscription = mNetworkFetchData.onGetCityList(new BaseCallback() {
            @Override
            public void onSuccess(Object data) {
                mMainView.hideProgress();
                mMainView.onSuccessGetCityList((CityListResponseModel) data);
            }

            @Override
            public void onError(NetworkError error) {
                mMainView.hideProgress();
                mMainView.onFailure(error.getAppErrorMessage());
            }
        });

        mCompositeSubscription.add(mSubscription);
    }

    public void onStopSubscribe(){
        mCompositeSubscription.unsubscribe();
    }
}
