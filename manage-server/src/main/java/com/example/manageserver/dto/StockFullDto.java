package com.example.manageserver.dto;

import com.example.manageserver.model.Product;
import com.example.manageserver.model.Stock;
import com.example.manageserver.model.StockTransaction;
import com.example.manageserver.model.WeekMeal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class StockFullDto {
    private Product product;
    private Stock stock;

    private StockTransaction stockTransaction;

    private Double quantity;

    private Double totalCost;

    private Double weekConsumeQuantity;
    List<StockFullDto> stockFullDtoList;

    private Integer size;

    private Long totalElements;

    private WeekMeal weekMeal;
}
