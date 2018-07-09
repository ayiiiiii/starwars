package com.lucasfilm.starwars.data.network.service;

import com.lucasfilm.starwars.data.model.Film;
import com.lucasfilm.starwars.data.model.Homeworld;
import com.lucasfilm.starwars.data.model.Vehicle;
import com.lucasfilm.starwars.data.network.ApiClient;
import com.lucasfilm.starwars.data.network.ApiInterface;
import com.lucasfilm.starwars.data.network.reqres.BaseResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lucasfilm.starwars.util.Const.SUCCESS_CODE;

public class GetPersonDetailImp implements GetPersonDetailService {
    private ApiInterface apiService;

    @Inject
    public GetPersonDetailImp() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void getFilm(String url, final ServiceCallback<BaseResponse> callback) {
        apiService.getFilm(url).enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.code() == SUCCESS_CODE) {
                    callback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getVehicle(String url, final ServiceCallback<BaseResponse> callback) {
        apiService.getVehicle(url).enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (response.code() == SUCCESS_CODE) {
                    callback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getHomeworld(String url, final ServiceCallback<BaseResponse> callback) {
        apiService.getHomeworld(url).enqueue(new Callback<Homeworld>() {
            @Override
            public void onResponse(Call<Homeworld> call, Response<Homeworld> response) {
                if (response.code() == SUCCESS_CODE) {
                    callback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Homeworld> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
