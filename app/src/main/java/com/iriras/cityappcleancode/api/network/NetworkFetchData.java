package com.iriras.cityappcleancode.api.network;

import com.iriras.cityappcleancode.api.model.CityListResponseModel;
import com.iriras.cityappcleancode.api.service.ApiService;
import com.iriras.cityappcleancode.base.BaseCallback;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by irfan on 6/18/17.
 */

public class NetworkFetchData {
    private final ApiService mApiService;

    public NetworkFetchData(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    public Subscription onGetCityList(final BaseCallback mBaseCallback){
        return mApiService.getCityList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends CityListResponseModel>>() {
                    @Override
                    public Observable<? extends CityListResponseModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<CityListResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mBaseCallback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(CityListResponseModel cityListResponseModel) {
                        mBaseCallback.onSuccess(cityListResponseModel);
                    }
                });
    }
}
