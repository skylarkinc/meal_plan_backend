package com.example.manageserver.dto;

import com.example.manageserver.model.WeekMeal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceWeekDto {


    private Integer totalService;

    private WeekMeal weekMeal;

    private String message;
}
