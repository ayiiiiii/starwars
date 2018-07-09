package com.lucasfilm.starwars.data.db;

import com.lucasfilm.starwars.data.model.Film;
import com.lucasfilm.starwars.data.model.Homeworld;
import com.lucasfilm.starwars.data.model.Person;
import com.lucasfilm.starwars.data.model.Vehicle;

import java.util.List;

public interface DBHelper {
    void savePeople(List<Person> person);
    void savePerson(Person person);
    Person getPerson(String name);
    List<Person> getPeople();
    List<Person> getPeopleFavourited();
    void updateFilms(List<Film> films);
    void updateVehicles(List<Vehicle> vehicleList);
    void updateHomeworld(Homeworld homeworld);
}
