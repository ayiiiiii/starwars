package com.lucasfilm.starwars.data.network.service;

public interface GetPeopleServiceCallback<T> extends ServiceCallback<T> {
    void onPageNotFound();
}