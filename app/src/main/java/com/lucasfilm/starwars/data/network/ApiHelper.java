package com.lucasfilm.starwars.data.network;

import com.lucasfilm.starwars.data.network.service.GetPersonDetailService;
import com.lucasfilm.starwars.data.network.service.GetPeopleService;

public interface ApiHelper extends GetPeopleService, GetPersonDetailService {
}
