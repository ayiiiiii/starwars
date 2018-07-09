package com.lucasfilm.starwars.ui.base;

import android.app.Activity;

public class BasePresenter<V extends IView> implements IPresenter<V> {

    private Activity activity;

    public BasePresenter(Activity activity) {
        this.activity = activity;
        onAttach((V) activity);
    }

    private V mvpView;

    public V getMvpView() {
        return this.mvpView;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mvpView = null;
    }
}
