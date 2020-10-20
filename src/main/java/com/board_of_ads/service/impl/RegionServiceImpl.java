package com.board_of_ads.service.impl;


import com.board_of_ads.models.Region;
import com.board_of_ads.repository.RegionRepository;
import com.board_of_ads.service.interfaces.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public Optional<Region> findRegionByNameAndFormSubject(String name) {
        return regionRepository.findAll()
                .stream()
                .map(Region::getName)
                .filter(region -> (name.split(" ")[0].equals(region) || name.split(" ")[1].equals(region)))
                .map(regionRepository::findRegionByName).findFirst();
    }

    @Override
    public List<Region> findAll() {
        return regionRepository.findAll();
    }
}