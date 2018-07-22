package com.plaps.androidcleancode.home;

import com.plaps.androidcleancode.models.CityListResponse;

/**
 * Created by Varun on 7/17/18.
 */
public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getCityListSuccess(CityListResponse cityListResponse);

}
