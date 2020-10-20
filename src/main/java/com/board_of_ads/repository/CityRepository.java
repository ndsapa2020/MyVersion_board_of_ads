package com.board_of_ads.repository;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Set<City> findCitiesByRegion(Region region);

    boolean existsCityByNameAndRegion(String cityName, Region region);

    Optional<City> findCitiesByName(String name);
}
