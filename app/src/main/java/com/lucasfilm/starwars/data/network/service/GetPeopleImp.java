package com.lucasfilm.starwars.data.network.service;

import com.lucasfilm.starwars.data.network.ApiClient;
import com.lucasfilm.starwars.data.network.ApiInterface;
import com.lucasfilm.starwars.data.network.reqres.BaseResponse;
import com.lucasfilm.starwars.data.network.reqres.GetPeopleResponse;
import com.lucasfilm.starwars.util.Const;

import javax.inject.Inject;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lucasfilm.starwars.util.Const.SUCCESS_CODE;

public class GetPeopleImp implements GetPeopleService {
    private ApiInterface apiService;

    @Inject
    public GetPeopleImp() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void getPeople(int page, final GetPeopleServiceCallback<BaseResponse> callback) {
        apiService.getPeople(page).enqueue(new Callback<GetPeopleResponse>() {
            @Override
            public void onResponse(Call<GetPeopleResponse> call, Response<GetPeopleResponse> response) {
                if (response.code() == SUCCESS_CODE) {
                    callback.onResponse(response.body());
                } else if (response.code() == Const.PAGE_NOT_FOUND) {
                    callback.onPageNotFound();
                }
            }

            @Override
            public void onFailure(Call<GetPeopleResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
