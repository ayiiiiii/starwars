package com.lucasfilm.starwars.di;

import com.lucasfilm.starwars.ui.MainActivity.MainActivity;
import com.lucasfilm.starwars.ui.PersonDetailActivity.PersonDetailActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules =  {DaggerModule.class})
public interface DaggerComponent {
    void inject(MainActivity mainActivity);
    void inject(PersonDetailActivity personDetailActivity);
}