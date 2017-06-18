package com.iriras.cityappcleancode.mvp.main;

import com.iriras.cityappcleancode.api.model.CityListResponseModel;

/**
 * Created by irfan on 6/18/17.
 */

public interface MainView {
    void showProgress();

    void hideProgress();

    void onFailure(String errorMessage);

    void onSuccessGetCityList(CityListResponseModel cityListResponseModel);
}
