package com.lucasfilm.starwars.data.model;

import com.lucasfilm.starwars.data.network.reqres.BaseResponse;
import com.orm.dsl.Table;

@Table
public class Vehicle extends BaseResponse
{
    private String url;

    private String name;

    private Person person;

    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getUrl() {
        return url;
    }
}
