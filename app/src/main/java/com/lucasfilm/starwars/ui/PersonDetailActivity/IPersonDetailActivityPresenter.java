package com.lucasfilm.starwars.ui.PersonDetailActivity;


import com.lucasfilm.starwars.ui.base.IPresenter;

public interface IPersonDetailActivityPresenter<V extends IPersonDetailActivityView> extends IPresenter<V> {
    void getFilms();
    void getVehicles();
    void getHomeworld();
}