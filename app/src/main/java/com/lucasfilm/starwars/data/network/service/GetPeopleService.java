package com.lucasfilm.starwars.data.network.service;

import com.lucasfilm.starwars.data.network.reqres.BaseResponse;

public interface GetPeopleService {
    void getPeople(int pageNo, GetPeopleServiceCallback<BaseResponse> callback);
}
