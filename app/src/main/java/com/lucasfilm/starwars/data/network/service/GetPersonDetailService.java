package com.lucasfilm.starwars.data.network.service;

import com.lucasfilm.starwars.data.network.reqres.BaseResponse;

public interface GetPersonDetailService {
    void getFilm(String url, ServiceCallback<BaseResponse> callback);
    void getVehicle(String url, ServiceCallback<BaseResponse> callback);
    void getHomeworld(String url, ServiceCallback<BaseResponse> callback);
}
