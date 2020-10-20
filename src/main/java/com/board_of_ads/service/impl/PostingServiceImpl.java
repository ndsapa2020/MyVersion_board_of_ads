package com.board_of_ads.service.impl;


import com.board_of_ads.models.City;
import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.repository.CityRepository;
import com.board_of_ads.repository.PostingRepository;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class PostingServiceImpl implements PostingService {

    private final PostingRepository postingRepository;
    private final RegionService regionService;
    private final CityRepository cityRepository;

    @Override
    public void save(Posting posting) {
        postingRepository.save(posting);
    }

    @Override
    public Posting getPostingById(Long id) {
        return postingRepository.getOne(id);
    }

    @Override
    public Optional<Posting> getPostingByTitle(String title) {
        return Optional.ofNullable(postingRepository.findPostingByTitle(title));
    }

    @Override
    public List<PostingDto> getPostingByCity(City city) {
        List<PostingDto> result = postingRepository.findPostingByCity(city);
        return getPostingDtos(result);
    }

    @Override
    public List<PostingDto> getPostingByFullRegionName(String name) {
        List<PostingDto> result = new ArrayList<>();
        var cities = cityRepository.findCitiesByRegion(
                regionService.findRegionByNameAndFormSubject(name).get());
        cities.forEach(city -> result.addAll(postingRepository.findPostingByCity(city)));
        return getPostingDtos(result);
    }

    @Override
    public List<PostingDto> getAllPostings() {
        List<PostingDto> postingDtos = postingRepository.findAllPostings();
        return getPostingDtos(postingDtos);
    }

    private List<PostingDto> getPostingDtos(List<PostingDto> postingDtos) {
        for(PostingDto dto : postingDtos) {
           dto.setImages(getPostingById(dto.getId()).getImages());
           dto.setCategory(getPostingById(dto.getId()).getCategory().getName());
           if(getPostingById(dto.getId()).getCity() != null) {
               dto.setCity(getPostingById(dto.getId()).getCity().getName());
           }
        }
        return postingDtos;
    }
}