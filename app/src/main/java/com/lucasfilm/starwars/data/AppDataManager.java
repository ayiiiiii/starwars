package com.lucasfilm.starwars.data;

import com.lucasfilm.starwars.data.db.DBHelper;
import com.lucasfilm.starwars.data.model.Film;
import com.lucasfilm.starwars.data.model.Homeworld;
import com.lucasfilm.starwars.data.model.Person;
import com.lucasfilm.starwars.data.model.Vehicle;
import com.lucasfilm.starwars.data.network.ApiHelper;
import com.lucasfilm.starwars.data.network.reqres.BaseResponse;
import com.lucasfilm.starwars.data.network.service.GetPeopleServiceCallback;
import com.lucasfilm.starwars.data.network.service.ServiceCallback;

import java.util.List;

import javax.inject.Inject;

public class AppDataManager implements DataManager {
    private ApiHelper apiHelper;
    private DBHelper dbHelper;

    @Inject
    public AppDataManager(ApiHelper apiHelper, DBHelper dbHelper) {
        this.apiHelper = apiHelper;
        this.dbHelper = dbHelper;
    }

    @Override
    public void getPeople(int page, GetPeopleServiceCallback<BaseResponse> callback) {
        apiHelper.getPeople(page, callback);
    }

    @Override
    public void getFilm(String url, ServiceCallback<BaseResponse> callback) {
        apiHelper.getFilm(url, callback);
    }

    @Override
    public void updateFilms(List<Film> films) {
        dbHelper.updateFilms(films);
    }

    @Override
    public void savePeople(List<Person> people) {
        dbHelper.savePeople(people);
    }

    @Override
    public void savePerson(Person person) {
        dbHelper.savePerson(person);
    }

    @Override
    public Person getPerson(String name) {
        return dbHelper.getPerson(name);
    }

    @Override
    public List<Person> getPeople() {
        return dbHelper.getPeople();
    }

    @Override
    public List<Person> getPeopleFavourited() {
        return dbHelper.getPeopleFavourited();
    }

    @Override
    public void updateVehicles(List<Vehicle> vehicleList) {
        dbHelper.updateVehicles(vehicleList);
    }

    @Override
    public void getVehicle(String url, ServiceCallback<BaseResponse> callback) {
        apiHelper.getVehicle(url, callback);
    }

    @Override
    public void updateHomeworld(Homeworld homeworld) {
        dbHelper.updateHomeworld(homeworld);
    }

    @Override
    public void getHomeworld(String url, ServiceCallback<BaseResponse> callback) {
        apiHelper.getHomeworld(url, callback);
    }
}
