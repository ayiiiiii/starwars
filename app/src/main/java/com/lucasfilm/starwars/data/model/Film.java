package com.lucasfilm.starwars.data.model;

import com.lucasfilm.starwars.data.network.reqres.BaseResponse;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

@Table
public class Film extends BaseResponse {
    private String url;

    private String title;

    private Person person;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Person getPerson() {
        return person;
    }
}
