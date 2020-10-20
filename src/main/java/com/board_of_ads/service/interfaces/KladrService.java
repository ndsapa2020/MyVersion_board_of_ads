package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Region;

import java.io.IOException;
import java.util.Set;

public interface KladrService {

    Region getRegionByRegionNumber(String regionNumber);

    void saveRegion(Region region);

    void saveCity(City city);

    Set<City> getAllCityOfRegion(Region region);

    boolean existsCityByCityNameAndRegion(String cityName, Region region);

    boolean existsRegionByName(String regionName);

    void streamKladr() throws IOException;
}
