package com.lucasfilm.starwars.ui.MainActivity;

import android.app.Activity;

import com.lucasfilm.starwars.data.DataManager;
import com.lucasfilm.starwars.data.network.reqres.BaseResponse;
import com.lucasfilm.starwars.data.network.reqres.GetPeopleResponse;
import com.lucasfilm.starwars.data.network.service.GetPeopleServiceCallback;
import com.lucasfilm.starwars.ui.base.BasePresenter;

public class MainActivityPresenter<V extends IMainActivityView> extends BasePresenter<V> implements IMainActivityPresenter<V> {
    private DataManager dataManager;
    private int page = 1;
    private boolean noMorePagesToShow = false;

    public MainActivityPresenter(Activity activity, DataManager dataManager) {
        super(activity);
        this.dataManager = dataManager;
    }

    @Override
    public void getPeople() {
        if (!noMorePagesToShow) {
            getMvpView().isPeopleLoading(true);
            dataManager.getPeople(page, new GetPeopleServiceCallback<BaseResponse>() {
                @Override
                public void onResponse(BaseResponse response) {
                    GetPeopleResponse mResponse = (GetPeopleResponse) response;
                    page++;

                    getMvpView().dissmisLoading();
                    getMvpView().peopleReady(mResponse);
                    getMvpView().isPeopleLoading(false);
                }

                @Override
                public void onError(String errorMessage) {
                    getMvpView().dissmisLoading();
                    getMvpView().isPeopleLoading(false);
                }

                @Override
                public void onPageNotFound() {
                    noMorePagesToShow = true;
                    getMvpView().dissmisLoading();
                    getMvpView().isPeopleLoading(false);
                }
            });
        }
    }
}
