package com.board_of_ads.repository;

import com.board_of_ads.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Region findRegionByRegionNumber(String regionNumber);

    boolean existsRegionByName(String name);

    Region findRegionByName(String name);
}