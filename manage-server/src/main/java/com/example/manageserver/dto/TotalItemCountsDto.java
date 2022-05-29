package com.example.manageserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalItemCountsDto {

    private Integer stockItem;

    private Integer menus;

    private Integer recipes;

    private Integer services;

}
