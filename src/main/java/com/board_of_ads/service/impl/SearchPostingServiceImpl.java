package com.board_of_ads.service.impl;

import com.board_of_ads.models.dto.PostingDto;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.SearchPostingSevice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class SearchPostingServiceImpl implements SearchPostingSevice {
    private final PostingService postingService;
    private final CityService cityService;

    @Override
    public List<PostingDto> searchPostings(String categorySelect, String citySelect, String searchText, String photoOption) {

        List<PostingDto> postingDtos;
        if(citySelect != null && !(citySelect.equals("undefined"))) {
            if (citySelect.matches("(.*)" +"Область" + "(.*)")
                    || citySelect.matches("(.*)" + "Край" + "(.*)")
                    || citySelect.matches("(.*)" + "Республика" + "(.*)")
                    || citySelect.matches("(.*)" + "Автономный округ" + "(.*)")
                    || citySelect.matches("(.*)" + "Город" + "(.*)")
            ) {
                postingDtos = postingService.getPostingByFullRegionName(citySelect);
            } else {
                postingDtos = postingService.getPostingByCity(cityService.findCityByName(citySelect).get());
            }
        } else {
            postingDtos = postingService.getAllPostings();
        }

        List<PostingDto> resultList = new ArrayList<>();

        for (PostingDto postingDto : postingDtos) {

            boolean categoryFlag = false;
            boolean photoFlag = false;
            boolean textFlag = false;

            if (categorySelect.equals("Любая категория")) {
                categoryFlag = true;
            } else if (postingDto.getCategory().equals(categorySelect)) {
                categoryFlag = true;
            }
            if(photoOption != null) {
                if(photoOption.equals("пункт2")) {
                    if(postingDto.getImages().size() > 0) {
                        photoFlag = true;
                    }
                } else if(photoOption.equals("пункт3")) {
                    if(postingDto.getImages().size() == 0) {
                        photoFlag = true;
                    }
                } else if(photoOption.equals("пункт1")) {
                    photoFlag = true;
                }
            } else {
                photoFlag = true;
            }
            if(searchText != null && !(searchText.equals("")) && !(searchText.equals("undefined"))) {
                if(postingDto.getTitle().toLowerCase().matches("(.*)" + searchText.toLowerCase() + "(.*)")) {
                    textFlag = true;
                }
            } else {
                textFlag = true;
            }

            if(categoryFlag && photoFlag && textFlag) {
                resultList.add(postingDto);
            }
        }

        return resultList;
    }
}
