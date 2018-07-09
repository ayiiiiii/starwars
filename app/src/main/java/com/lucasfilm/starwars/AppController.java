package com.lucasfilm.starwars;


import com.lucasfilm.starwars.di.DaggerComponent;
import com.lucasfilm.starwars.di.DaggerDaggerComponent;
import com.lucasfilm.starwars.di.DaggerModule;
import com.orm.SugarApp;

import dagger.Module;

@Module
public class AppController extends SugarApp {
    private DaggerComponent daggerComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        daggerComponent = createDaggerComponent();
    }

    public DaggerComponent getDaggerComponent() {
        return daggerComponent == null ? createDaggerComponent() : daggerComponent;
    }


    private DaggerComponent createDaggerComponent() {
        return DaggerDaggerComponent.builder().daggerModule(new DaggerModule(this)).build();
    }

    public void clearComponent() {
        daggerComponent = null;
    }

}
