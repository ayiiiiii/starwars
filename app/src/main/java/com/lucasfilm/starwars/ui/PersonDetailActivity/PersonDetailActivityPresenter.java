package com.lucasfilm.starwars.ui.PersonDetailActivity;

import android.app.Activity;

import com.lucasfilm.starwars.data.DataManager;
import com.lucasfilm.starwars.data.model.Film;
import com.lucasfilm.starwars.data.model.Homeworld;
import com.lucasfilm.starwars.data.model.Person;
import com.lucasfilm.starwars.data.model.Vehicle;
import com.lucasfilm.starwars.data.network.reqres.BaseResponse;
import com.lucasfilm.starwars.data.network.service.ServiceCallback;
import com.lucasfilm.starwars.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class PersonDetailActivityPresenter<V extends IPersonDetailActivityView> extends BasePresenter<V> implements IPersonDetailActivityPresenter<V> {
    private DataManager dataManager;
    private Person person;
    private List<Film> filmList = new ArrayList<>();
    private List<Vehicle> vehicleList = new ArrayList<>();
    private int numberOfFilms = 0;
    private int numberOfVehicles = 0;

    public PersonDetailActivityPresenter(Activity activity, DataManager dataManager, Person person) {
        super(activity);
        this.dataManager = dataManager;
        this.person = person;
    }

    @Override
    public void getFilms() {
        getMvpView().showLoading();
        filmList.clear();
        List<Film> films = person.getAllFilms();
        numberOfFilms = films.size();
        for (int i = 0; i < films.size(); i++) {
            getFilm(films.get(i).getUrl());
        }
    }

    @Override
    public void getVehicles() {
        getMvpView().showLoading();
        vehicleList.clear();
        List<Vehicle> vehicles = person.getAllVehicles();
        numberOfVehicles = vehicles.size();
        for (int i = 0; i < vehicles.size(); i++) {
            getVehicle(vehicles.get(i).getUrl());
        }
    }

    private void getFilm(final String url) {
        dataManager.getFilm(url, new ServiceCallback<BaseResponse>() {
            @Override
            public void onResponse(BaseResponse response) {
                Film film = (Film) response;
                film.setUrl(url);
                filmList.add(film);

                if (filmList.size() == numberOfFilms) {
                    getMvpView().dissmisLoading();
                    getMvpView().filmsReady(filmList);
                }
            }

            @Override
            public void onError(String errorMessage) {
                getMvpView().dissmisLoading();
            }
        });
    }

    private void getVehicle(final String url) {
        dataManager.getVehicle(url, new ServiceCallback<BaseResponse>() {
            @Override
            public void onResponse(BaseResponse response) {
                Vehicle vehicle = (Vehicle) response;
                vehicle.setUrl(url);
                vehicleList.add(vehicle);

                if (vehicleList.size() == numberOfVehicles) {
                    getMvpView().dissmisLoading();
                    getMvpView().vehiclesReady(vehicleList);
                }
            }

            @Override
            public void onError(String errorMessage) {
                getMvpView().dissmisLoading();
            }
        });
    }

    @Override
    public void getHomeworld() {
        dataManager.getHomeworld(person.getHomeworldUrl(), new ServiceCallback<BaseResponse>() {
            @Override
            public void onResponse(BaseResponse response) {
                Homeworld homeworld = (Homeworld) response;
                homeworld.setUrl(person.getHomeworldUrl());
                getMvpView().dissmisLoading();
                getMvpView().homeworldReady(homeworld);
            }

            @Override
            public void onError(String errorMessage) {
                getMvpView().dissmisLoading();
            }
        });
    }
}
