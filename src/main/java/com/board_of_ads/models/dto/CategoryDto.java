package com.board_of_ads.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class CategoryDto {

    private Long id;
    private String name;
    private boolean parent;
    private int layer;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parentName;

    public CategoryDto(Long id, String name, String parentName, int layer) {
        this.id = id;
        this.name = name;
        this.parentName = parentName;
        this.layer = layer;
    }

}
