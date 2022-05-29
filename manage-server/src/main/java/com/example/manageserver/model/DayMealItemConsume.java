package com.example.manageserver.model;

import com.example.manageserver.model.Base.BaseModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class DayMealItemConsume extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    private double quantity;

    private double cost;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private DayMeal dayMeal;

    public DayMealItemConsume(Product product ,DayMeal dayMeal,double quantity,double cost){

        this.product = product;

        this.dayMeal = dayMeal;

        this.quantity = quantity;

        this.cost = cost;
    }
}
