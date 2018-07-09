package com.lucasfilm.starwars.data.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;


public class Person extends SugarRecord {
    @Unique
    private String name;

    @SerializedName("birth_year")
    private String birthYear;

    @Ignore
    @SerializedName("films")
    private List<String> filmUrls;

    @Ignore
    @SerializedName("vehicles")
    private List<String> vehicleUrls;

    @SerializedName("homeworld")
    private String homeworldUrl;

    private boolean favourited;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public float getBirthYearFloat() {
        String strippedNonDigits = birthYear.replaceAll("[^\\\\.0123456789]", "");
        if (strippedNonDigits.equals("")) {
            return 0;
        } else {
            return Float.parseFloat(strippedNonDigits);
        }
    }

    public boolean isFavourited() {
        return favourited;
    }

    public void setFavourited(boolean favourited) {
        this.favourited = favourited;
    }

    public List<String> getFilmUrls() {
        return filmUrls;
    }

    public List<String> getVehicleUrls() {
        return vehicleUrls;
    }


    public String getHomeworldUrl() {
        return homeworldUrl;
    }


    public List<Film> getAllFilms() {
        return Select.from(Film.class).where(Condition.prop("person").eq(getId())).list();
    }

    public List<Vehicle> getAllVehicles() {
        return Select.from(Vehicle.class).where(Condition.prop("person").eq(getId())).list();
    }

    public Homeworld getHomeworld() {
        return Select.from(Homeworld.class).where(Condition.prop("person").eq(getId())).first();
    }
}
