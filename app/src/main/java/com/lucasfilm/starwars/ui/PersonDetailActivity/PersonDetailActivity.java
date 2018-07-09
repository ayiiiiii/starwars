package com.lucasfilm.starwars.ui.PersonDetailActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lucasfilm.starwars.AppController;
import com.lucasfilm.starwars.R;
import com.lucasfilm.starwars.data.DataManager;
import com.lucasfilm.starwars.data.model.Film;
import com.lucasfilm.starwars.data.model.Homeworld;
import com.lucasfilm.starwars.data.model.Person;
import com.lucasfilm.starwars.data.model.Vehicle;
import com.lucasfilm.starwars.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PersonDetailActivity extends BaseActivity implements IPersonDetailActivityView {

    public static final String INTENT_PERSON_NAME = "person_name";

    @Inject
    DataManager dataManager;

    private Person person;
    private PersonDetailActivityPresenter personDetailActivityPresenter;
    private LinearLayout llMovieContainer, llVehicleContainer, llHomeworldContainer;

    private List<TextView> tvFilmList = new ArrayList<>();
    private List<TextView> tvVehicleList = new ArrayList<>();
    private TextView tvHomeworld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        ((AppController) getApplication()).getDaggerComponent().inject(this);

        llMovieContainer = findViewById(R.id.llMovieContainer);
        llVehicleContainer = findViewById(R.id.llVehicleContainer);
        llHomeworldContainer = findViewById(R.id.llHomeworldContainer);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(getText(R.string.person_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String personName = getIntent().getStringExtra(INTENT_PERSON_NAME);
        person = dataManager.getPerson(personName);

        personDetailActivityPresenter = new PersonDetailActivityPresenter(this, dataManager, person);
        personDetailActivityPresenter.getFilms();
        personDetailActivityPresenter.getVehicles();
        personDetailActivityPresenter.getHomeworld();

        inflateFilms(person.getAllFilms());
        inflateVehicles(person.getAllVehicles());
        inflateHomeworld(person.getHomeworld());
    }

    @Override
    public void filmsReady(List<Film> response) {
        dataManager.updateFilms(response);
        inflateFilms(response);
    }

    @Override
    public void vehiclesReady(List<Vehicle> vehicleList) {
        dataManager.updateVehicles(vehicleList);
        inflateVehicles(vehicleList);
    }

    @Override
    public void homeworldReady(Homeworld homeworld) {
        dataManager.updateHomeworld(homeworld);
        inflateHomeworld(homeworld);
    }

    private void inflateFilms(List<Film> films) {
        for (TextView tv : tvFilmList) {
            llMovieContainer.removeView(tv);
        }
        tvFilmList.clear();
        for (Film f : films) {
            if (f.getTitle() != null) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.view_text, llMovieContainer, false);
                tv.setText(f.getTitle());
                llMovieContainer.addView(tv);
                tvFilmList.add(tv);
            }
        }
    }

    private void inflateVehicles(List<Vehicle> vehicleList) {
        for (TextView tv : tvVehicleList) {
            llVehicleContainer.removeView(tv);
        }
        tvVehicleList.clear();
        for (Vehicle v : vehicleList) {
            if (v.getName() != null) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.view_text, llMovieContainer, false);
                tv.setText(v.getName());
                llVehicleContainer.addView(tv);
                tvVehicleList.add(tv);
            }
        }
    }

    private void inflateHomeworld(Homeworld homeworld) {
        if (homeworld != null) {
            llHomeworldContainer.removeView(tvHomeworld);
            tvHomeworld = null;
            TextView tv = (TextView) getLayoutInflater().inflate(R.layout.view_text, llMovieContainer, false);
            tv.setText(homeworld.getName());
            llHomeworldContainer.addView(tv);
            tvHomeworld = tv;
        }
    }
}
