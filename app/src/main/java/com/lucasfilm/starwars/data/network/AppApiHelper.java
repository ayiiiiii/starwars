package com.lucasfilm.starwars.data.network;

import com.lucasfilm.starwars.data.network.reqres.BaseResponse;
import com.lucasfilm.starwars.data.network.service.GetPersonDetailService;
import com.lucasfilm.starwars.data.network.service.GetPeopleService;
import com.lucasfilm.starwars.data.network.service.GetPeopleServiceCallback;
import com.lucasfilm.starwars.data.network.service.ServiceCallback;

import javax.inject.Inject;

public class AppApiHelper implements ApiHelper
{
    private GetPeopleService getPeopleService;
    private GetPersonDetailService getPersonDetailService;

    @Inject
    public AppApiHelper(GetPeopleService getPeopleService, GetPersonDetailService getPersonDetailService) {
        this.getPeopleService = getPeopleService;
        this.getPersonDetailService = getPersonDetailService;
    }

    @Override
    public void getPeople(int page, GetPeopleServiceCallback<BaseResponse> callback) {
        getPeopleService.getPeople(page, callback);
    }

    @Override
    public void getFilm(String url, ServiceCallback<BaseResponse> callback) {
        getPersonDetailService.getFilm(url, callback);
    }

    @Override
    public void getVehicle(String url, ServiceCallback<BaseResponse> callback) {
        getPersonDetailService.getVehicle(url, callback);
    }

    @Override
    public void getHomeworld(String url, ServiceCallback<BaseResponse> callback) {
        getPersonDetailService.getHomeworld(url, callback);
    }
}
