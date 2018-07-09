package com.lucasfilm.starwars.ui.MainActivity;


import com.lucasfilm.starwars.ui.base.IPresenter;

public interface IMainActivityPresenter<V extends IMainActivityView> extends IPresenter<V> {
    void getPeople();
}