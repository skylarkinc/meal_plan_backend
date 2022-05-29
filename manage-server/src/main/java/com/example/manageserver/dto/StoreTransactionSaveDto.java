package com.example.manageserver.dto;

import lombok.Data;

@Data
public class StoreTransactionSaveDto {

    private Long productId;
    private Double quantity;
    private Double totalCost;

    private double portionControl;

    private double portionCost;

    private String month;

    private String week;

  //  private

}
