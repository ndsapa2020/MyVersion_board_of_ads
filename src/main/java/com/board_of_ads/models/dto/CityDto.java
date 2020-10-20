package com.board_of_ads.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityDto {
    private String city;
    private String region;
    private String formSubject;
}
