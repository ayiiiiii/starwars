package com.lucasfilm.starwars.ui.MainActivity;

import com.lucasfilm.starwars.data.model.Person;

import java.util.List;

public interface PublishFilterResultsListener {
    void onPublishResults(List<Person> personList);
}