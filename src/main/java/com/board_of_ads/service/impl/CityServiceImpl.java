package com.board_of_ads.service.impl;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Region;
import com.board_of_ads.repository.CityRepository;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.service.interfaces.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final RegionService regionService;

    @Override
    public Set<City> getCitiesList() {
        Set<City> cities = new HashSet<>();
        cityRepository.findAll()
                .forEach(city -> {
                    String regionName = "";
                    String formSubject = "";
                    if (city.getRegion() != null) {
                        regionName = city.getRegion().getName() == null ? "" : city.getRegion().getName();
                        formSubject = city.getRegion().getFormSubject() == null ? "" : city.getRegion().getFormSubject();
                    }
                    cities.add(new City(city.getName(), new Region(), regionName + " " + formSubject));
                });
        regionService.findAll()
                .forEach(region -> {
                    String regionName = region.getName();
                    String formSubject = region.getFormSubject();
                    if (formSubject.equals("Республика") || formSubject.equals("Город")) {
                        cities.add(new City(formSubject + " " + regionName, new Region(), ""));
                    } else {
                        cities.add(new City(regionName + " " + formSubject, new Region(), ""));
                    }
                });
        return cities;
    }

    @Override
    public Optional<City> findCityByName(String name) {
        return cityRepository.findCitiesByName(name);
    }
}
