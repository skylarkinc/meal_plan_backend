package com.example.manageserver.dto;

import lombok.Data;

@Data
public class FilterItemDto {

    private Long mainCategoryId;

    private String month;

    private String week;

    private String year;

    //private Long mainMealId;

    private Integer page;

    private Integer size;

    private Boolean isActive;
}
