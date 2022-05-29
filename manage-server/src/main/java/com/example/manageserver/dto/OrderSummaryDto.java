package com.example.manageserver.dto;

import com.example.manageserver.model.Supplier;
import com.example.manageserver.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummaryDto {

    private Unit unit;

    private Supplier supplier;

    private Double inStockValue;

    private Double weeklyUse;

    private Double orderValue;
}
