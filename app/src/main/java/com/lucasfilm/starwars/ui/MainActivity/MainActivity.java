package com.lucasfilm.starwars.ui.MainActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lucasfilm.starwars.AppController;
import com.lucasfilm.starwars.R;
import com.lucasfilm.starwars.data.DataManager;
import com.lucasfilm.starwars.data.network.reqres.GetPeopleResponse;
import com.lucasfilm.starwars.ui.Adapters.PersonAdapter;
import com.lucasfilm.starwars.ui.base.BaseActivity;
import com.lucasfilm.starwars.util.Const;

import java.util.Arrays;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements IMainActivityView, OnLoadMoreListener, SearchView.OnQueryTextListener {

    @Inject
    DataManager dataManager;
    RecyclerView rvList;
    PersonAdapter personAdapter;
    MainActivityPresenter mainActivityPresenter;
    SearchView searchView;
    MenuItem searchMenuItem;
    boolean isFavouriteMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((AppController) getApplication()).getDaggerComponent().inject(this);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(getText(R.string.app_name));

        rvList = findViewById(R.id.rvList);

        rvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        personAdapter = new PersonAdapter(this, rvList, dataManager);
        personAdapter.setOnLoadMoreListener(this);
        rvList.setAdapter(personAdapter);

        mainActivityPresenter = new MainActivityPresenter(this, dataManager);
        mainActivityPresenter.getPeople();

        personAdapter.updateList(dataManager.getPeople());
    }

    @Override
    public void onLoadMore() {
        mainActivityPresenter.getPeople();
    }

    @Override
    public void peopleReady(GetPeopleResponse response) {
        dataManager.savePeople(response.getResults());
        if (isFavouriteMode) {
            personAdapter.updateList(dataManager.getPeopleFavourited());
        } else {
            personAdapter.updateList(dataManager.getPeople());
        }
    }

    @Override
    public void isPeopleLoading(boolean isLoading) {
        personAdapter.setLoading(isLoading);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.iSearch);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getText(R.string.search_people));

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.equals("")) {
            personAdapter.setEnablePagination(true);
        } else {
            personAdapter.setEnablePagination(false);
        }
        personAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iSort:
                showSortDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortDialog() {

        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(MainActivity.this);
        alertdialogbuilder.setTitle(getText(R.string.sort_by));

        final String[] sortTypes = new String[Const.SORT_VALUE_RESOURCES.length];

        for (int i = 0; i < sortTypes.length; i++) {
            sortTypes[i] = getText(Const.SORT_VALUE_RESOURCES[i]).toString();
        }

        alertdialogbuilder.setItems(sortTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedText = Arrays.asList(sortTypes).get(which);
                if (selectedText.equals(getText(R.string.name))) {
                    personAdapter.setEnablePagination(true);
                    isFavouriteMode = false;
                    personAdapter.updateList(dataManager.getPeople());
                    personAdapter.sortList(PersonAdapter.SortType.NAME);
                } else if (selectedText.equals(getText(R.string.birth_year))) {
                    personAdapter.setEnablePagination(true);
                    isFavouriteMode = false;
                    personAdapter.updateList(dataManager.getPeople());
                    personAdapter.sortList(PersonAdapter.SortType.BIRTH_YEAR);
                } else if (selectedText.equals(getText(R.string.favourite))) {
                    personAdapter.setEnablePagination(false);
                    isFavouriteMode = true;
                    personAdapter.updateList(dataManager.getPeopleFavourited());
                }
                dialog.dismiss();
            }
        });

        alertdialogbuilder.create().show();
    }
}
