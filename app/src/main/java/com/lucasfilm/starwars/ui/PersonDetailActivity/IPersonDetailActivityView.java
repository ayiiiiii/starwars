package com.lucasfilm.starwars.ui.PersonDetailActivity;

import com.lucasfilm.starwars.data.model.Film;
import com.lucasfilm.starwars.data.model.Homeworld;
import com.lucasfilm.starwars.data.model.Vehicle;
import com.lucasfilm.starwars.ui.base.IView;

import java.util.List;


public interface IPersonDetailActivityView extends IView
{
    void filmsReady(List<Film> filmList);
    void vehiclesReady(List<Vehicle> vehicleList);
    void homeworldReady(Homeworld homeworld);
}