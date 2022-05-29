package com.example.manageserver.dto;

import com.example.manageserver.model.DayMeal;
import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.ManDays;
import com.example.manageserver.model.WeekMeal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class DayMealFullDto  {

    private MainMeal mainMeal;
    private ManDays manDays;
    private List<DayMealDto> dayMealDtoList;

    private List<DayMealFullDto> dayMealFullDtoList;

    private List<DayMealDto> totalDayMealCostList;

    private WeekMeal weekMeal;

    private Double totalCost;

    private Integer rowIndex;

    private String message;

    private Boolean isWeekMealFound;

    public void setRowIndexByWeekMeal(Set<MainMealRowIndex> rowIndexByWeekMeal,MainMealRowIndex mainMealRowIndex){
        if(rowIndexByWeekMeal != null){
            MainMealRowIndex rowIndex  =rowIndexByWeekMeal.stream().filter(mainMealRowIndex1 -> mainMealRowIndex1.getMainMealId().equals(mainMealRowIndex.getMainMealId()) && mainMealRowIndex1.getMealType().equals(mainMealRowIndex.getMealType())).findFirst().orElse(null);
            if(rowIndex!= null)
                this.rowIndex =rowIndex.getRowIndex();
            else
                this.rowIndex =0;
        }
    }
}
