package com.example.manageserver.dto;

import com.example.manageserver.model.DayMealItemConsume;
import com.example.manageserver.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class DayMealCostDto {

    private double totalCost;

    private List<DayMealItemConsume> dayMealItemConsumeList;

    private double totalQuantity;

    private Product product;

    private List<Long> dayMealItemConsumeIdList;

}
