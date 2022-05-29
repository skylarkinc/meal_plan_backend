package com.example.manageserver.dto;

import com.example.manageserver.model.DayMeal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DayMealDto {

    private Integer dayIndex;

    private String dayOfWeek;

    private DayMeal meal;

    private Double cost;

    private Double quantity;
    public DayMealDto(DayMeal dayMeal){

        this.dayIndex =dayMeal.getDayIndex();

        this.dayOfWeek =dayMeal.getDayOfWeek();

        this.cost = dayMeal.getMealCost();

        this.meal = dayMeal;

    }

    /**
     *   dayIndex:0,
     *         dayOfWeek:"Monday",
     *         meal :null
     */
}
