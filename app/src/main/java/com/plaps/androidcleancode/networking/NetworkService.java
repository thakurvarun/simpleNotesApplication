package com.plaps.androidcleancode.networking;


import com.plaps.androidcleancode.models.CityListResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Varun on 7/17/18.
 */
public interface NetworkService {

    @GET("v1/city")
    Observable<CityListResponse> getCityList();

}
