package com.lucasfilm.starwars.data.network.service;

public interface ServiceCallback<T> {
    void onResponse(T response);
    void onError(String errorMessage);
}