package com.lucasfilm.starwars.data.network.reqres;

import com.lucasfilm.starwars.data.model.Person;

import java.util.List;

public class GetPeopleResponse extends BaseResponse {
    private List<Person> results;
    public List<Person> getResults() {
        return results;
    }
}
