package com.board_of_ads.service.interfaces;


import com.board_of_ads.models.City;

import java.util.Optional;
import java.util.Set;

public interface CityService {

    Set<City> getCitiesList();

    Optional<City> findCityByName(String name);
}