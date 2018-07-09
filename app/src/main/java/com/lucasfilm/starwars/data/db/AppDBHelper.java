package com.lucasfilm.starwars.data.db;

import com.lucasfilm.starwars.data.model.Film;
import com.lucasfilm.starwars.data.model.Homeworld;
import com.lucasfilm.starwars.data.model.Person;
import com.lucasfilm.starwars.data.model.Vehicle;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDBHelper implements DBHelper {
    @Inject
    public AppDBHelper() {
    }

    @Override
    public void savePeople(List<Person> people) {
        for (int i = 0; i < people.size(); i++) {
            Person person = people.get(i);
            Person dbPerson = getPerson(person.getName());
            if (dbPerson == null) {
                savePerson(person);
                for (int k = 0; k < person.getFilmUrls().size(); k++) {
                    String url = person.getFilmUrls().get(k);
                    Film film = new Film();
                    film.setPerson(person);
                    film.setUrl(url);
                    saveFilm(film);
                }
                for (int k = 0; k < person.getVehicleUrls().size(); k++) {
                    String url = person.getVehicleUrls().get(k);
                    Vehicle vehicle = new Vehicle();
                    vehicle.setPerson(person);
                    vehicle.setUrl(url);
                    saveVehicle(vehicle);
                }
                Homeworld homeworld = new Homeworld();
                homeworld.setPerson(person);
                homeworld.setUrl(person.getHomeworldUrl());
                saveHomeworld(homeworld);
            }
        }
    }

    @Override
    public void savePerson(Person person) {
        SugarRecord.save(person);
    }

    @Override
    public Person getPerson(String name) {
        return Select.from(Person.class).where(Condition.prop("name").eq(name)).first();
    }

    @Override
    public List<Person> getPeople() {
        return Select.from(Person.class).list();
    }

    @Override
    public List<Person> getPeopleFavourited() {
        return Select.from(Person.class).where(Condition.prop("favourited").eq(1)).list();
    }

    @Override
    public void updateFilms(List<Film> films) {
        for (Film f : films) {
            List<Film> dbFilms = Select.from(Film.class).where(Condition.prop("url").eq(f.getUrl())).list();
            for (Film dbFilm : dbFilms) {
                dbFilm.setTitle(f.getTitle());
                saveFilm(dbFilm);
            }
        }
    }

    private void saveFilm(Film film) {
        SugarRecord.save(film);
    }

    @Override
    public void updateVehicles(List<Vehicle> vehicleList) {
        for (Vehicle v : vehicleList) {
            List<Vehicle> dbVehicles = Select.from(Vehicle.class).where(Condition.prop("url").eq(v.getUrl())).list();
            for (Vehicle dbVehicle : dbVehicles) {
                dbVehicle.setName(v.getName());
                saveVehicle(dbVehicle);
            }
        }
    }

    private void saveVehicle(Vehicle vehicle) {
        SugarRecord.save(vehicle);
    }

    @Override
    public void updateHomeworld(Homeworld homeworld) {
        List<Homeworld> dbHomeworlds = Select.from(Homeworld.class).where(Condition.prop("url").eq(homeworld.getUrl())).list();
        for (Homeworld hw : dbHomeworlds) {
            hw.setName(homeworld.getName());
            saveHomeworld(hw);
        }
    }

    private void saveHomeworld(Homeworld homeworld) {
        SugarRecord.save(homeworld);
    }

}
