package com.lucasfilm.starwars.ui.MainActivity;

import android.widget.Filter;

import com.lucasfilm.starwars.data.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonFilter extends Filter {
    List<Person> list;
    PublishFilterResultsListener publishFilterResultsListener;

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            List<Person> tempList = new ArrayList<>();

            // search content in person list
            for (Person p : list) {
                if (p.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                    tempList.add(p);
                }
            }

            filterResults.count = tempList.size();
            filterResults.values = tempList;
        } else {
            filterResults.count = list.size();
            filterResults.values = list;
        }

        return filterResults;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if (publishFilterResultsListener != null) {
            publishFilterResultsListener.onPublishResults((List<Person>) results.values);
        }
    }

    public void setPublishFilterResultsListener(PublishFilterResultsListener publishFilterResultsListener) {
        this.publishFilterResultsListener = publishFilterResultsListener;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }
}