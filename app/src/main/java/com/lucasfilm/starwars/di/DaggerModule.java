package com.lucasfilm.starwars.di;

import android.app.Application;
import android.content.Context;

import com.lucasfilm.starwars.data.AppDataManager;
import com.lucasfilm.starwars.data.DataManager;
import com.lucasfilm.starwars.data.db.AppDBHelper;
import com.lucasfilm.starwars.data.db.DBHelper;
import com.lucasfilm.starwars.data.network.ApiHelper;
import com.lucasfilm.starwars.data.network.AppApiHelper;
import com.lucasfilm.starwars.data.network.service.GetPersonDetailImp;
import com.lucasfilm.starwars.data.network.service.GetPersonDetailService;
import com.lucasfilm.starwars.data.network.service.GetPeopleImp;
import com.lucasfilm.starwars.data.network.service.GetPeopleService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerModule {
    private Context context;


    public DaggerModule(Application app) {
        this.context = app;
    }


    @Provides
    Context providesContext() {
        return context;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(ApiHelper apiHelper, DBHelper dbHelper) {
        return new AppDataManager(apiHelper, dbHelper);
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(GetPeopleService getPeopleService, GetPersonDetailService getPersonDetailService) {
        return new AppApiHelper(getPeopleService, getPersonDetailService);
    }

    @Provides
    @Singleton
    GetPeopleService provideGetPeople() {
        return new GetPeopleImp();
    }

    @Provides
    @Singleton
    GetPersonDetailService provideGetFilm() {
        return new GetPersonDetailImp();
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper() {
        return new AppDBHelper();
    }
}
