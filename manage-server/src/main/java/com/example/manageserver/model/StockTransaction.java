package com.example.manageserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    private String description;

    private String method;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Unit unit;

    private double quantity;

    private double totalCost;

    @OneToOne
    private Stock stock;

    private String month;

    private String week;

    private String year;





    @ManyToMany
    private List<MainMeal> mainMealList;

    @ManyToMany
    private List<BaseIngredient> baseIngredientList;

}
