package com.lucasfilm.starwars.data.network;


import com.lucasfilm.starwars.data.model.Film;
import com.lucasfilm.starwars.data.model.Homeworld;
import com.lucasfilm.starwars.data.model.Vehicle;
import com.lucasfilm.starwars.data.network.reqres.GetPeopleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface
{
    @GET("people")
    Call<GetPeopleResponse> getPeople(@Query("page") int page);

    @GET
    Call<Film> getFilm(@Url String url);

    @GET
    Call<Vehicle> getVehicle(@Url String url);

    @GET
    Call<Homeworld> getHomeworld(@Url String url);
}
