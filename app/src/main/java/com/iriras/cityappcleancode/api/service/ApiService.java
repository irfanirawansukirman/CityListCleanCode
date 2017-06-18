package com.iriras.cityappcleancode.api.service;

import com.iriras.cityappcleancode.api.model.CityListResponseModel;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by irfan on 6/18/17.
 */

public interface ApiService {
    @GET("v1/city")
    Observable<CityListResponseModel> getCityList();
}
