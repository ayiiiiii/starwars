package com.lucasfilm.starwars.ui.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucasfilm.starwars.R;
import com.lucasfilm.starwars.data.DataManager;
import com.lucasfilm.starwars.data.model.Person;
import com.lucasfilm.starwars.ui.MainActivity.OnLoadMoreListener;
import com.lucasfilm.starwars.ui.MainActivity.PersonFilter;
import com.lucasfilm.starwars.ui.MainActivity.PublishFilterResultsListener;
import com.lucasfilm.starwars.ui.PersonDetailActivity.PersonDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private List<Person> list = new ArrayList<>();
    private List<Person> filteredList = new ArrayList<>();
    private int totalItemCount, lastVisibleItem;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private SortType sortType = SortType.NAME;
    private DataManager dataManager;
    private PersonFilter personFilter;
    private boolean enablePagination = true;
    private String searchKeyword = "";
    private Activity activity;

    // The minimum amount of items to have below current scroll position before loading more.
    private int visibleThreshold = 5;

    public PersonAdapter(Activity activity, RecyclerView recyclerView, DataManager dataManager) {
        this.activity = activity;
        this.dataManager = dataManager;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (enablePagination) {
                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                            if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                            }
                        }
                    }
                });
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_person, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Person person = filteredList.get(position);
        MyViewHolder mHolder = (MyViewHolder) holder;

        mHolder.tvName.setText(person.getName());
        mHolder.tvBirthYear.setText(person.getBirthYear());

        if (person.isFavourited()) {
            mHolder.ivFav.setImageResource(R.drawable.star);
        } else {
            mHolder.ivFav.setImageResource(R.drawable.star_empty);
        }

        mHolder.ivFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setFavourited(!person.isFavourited());
                dataManager.savePerson(person);
                notifyDataSetChanged();
            }
        });

        mHolder.cvCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, PersonDetailActivity.class);
                i.putExtra(PersonDetailActivity.INTENT_PERSON_NAME, person.getName());
                activity.startActivity(i);
            }
        });
    }

    public void setEnablePagination(boolean enablePagination) {
        this.enablePagination = enablePagination;
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvBirthYear;
        private ImageView ivFav;
        private CardView cvCell;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvBirthYear = view.findViewById(R.id.tvBirthDay);
            ivFav = view.findViewById(R.id.ivFav);
            cvCell = view.findViewById(R.id.cvCell);
        }
    }

    public void updateList(List<Person> people) {
        list.clear();

        list.addAll(people);
        filteredList = list;
        getFilter().filter(searchKeyword);
        sortList(sortType);
    }

    public void sortList(SortType sortType) {
        this.sortType = sortType;

        if (sortType.equals(SortType.NAME)) {
            Collections.sort(filteredList, new Comparator<Person>() {
                @Override
                public int compare(Person o1, Person o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        } else if (sortType.equals(SortType.BIRTH_YEAR)) {
            Collections.sort(filteredList, new Comparator<Person>() {
                @Override
                public int compare(Person o1, Person o2) {
                    return Float.compare(o1.getBirthYearFloat(), o2.getBirthYearFloat());
                }
            });
        }

        notifyDataSetChanged();
    }

    public enum SortType {NAME, BIRTH_YEAR}

    @Override
    public PersonFilter getFilter() {
        if (personFilter == null) {
            personFilter = new PersonFilter();
            personFilter.setList(list);
            personFilter.setPublishFilterResultsListener(new PublishFilterResultsListener() {
                @Override
                public void onPublishResults(List<Person> personList) {
                    filteredList = personList;
                    notifyDataSetChanged();
                }
            });
        }
        return personFilter;
    }

}
