package com.lucasfilm.starwars.ui.MainActivity;

import com.lucasfilm.starwars.data.network.reqres.GetPeopleResponse;
import com.lucasfilm.starwars.ui.base.IView;


public interface IMainActivityView extends IView
{
    void peopleReady(GetPeopleResponse response);
    void isPeopleLoading(boolean loading);
}