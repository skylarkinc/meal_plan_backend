package com.example.manageserver.model;


import com.example.manageserver.dto.MainMealRowIndex;
import com.example.manageserver.model.Base.BaseModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WeekMeal extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalCost;

    private double breakFastCost;

    private double cribCost;

    private double dinnerCost;

    private String month;

    private String week;

    private boolean isActiveWeek;

    @JsonManagedReference
    @OneToMany(mappedBy = "weekMeal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductConsumer> productConsumerList;

    @ElementCollection( fetch = FetchType.EAGER )
    private Set<MainMealRowIndex> mainMealRowIndexs ;

    private Double weeklyBudget;

    private String year;


    private String status ;

    public void setRowIndex(MainMealRowIndex mainMealRowIndex){
        MainMealRowIndex rowIndex = mainMealRowIndexs.stream().filter(mainMealRowIndex1 ->
            mainMealRowIndex1.getMainMealId().equals(mainMealRowIndex.getMainMealId()) && mainMealRowIndex1.getMealType().equals(mainMealRowIndex.getMealType())
        ).findFirst().orElse(null);
        if(rowIndex==null){
            mainMealRowIndex.setRowIndex(mainMealRowIndexs.size());
            mainMealRowIndexs.add(mainMealRowIndex);
        }

    }


    @PrePersist
    protected void onCreate() {
        if(status==null)
            this.status ="DRAFT";

        this.created_At = new Date();
        this.totalCost = this.breakFastCost+this.cribCost+this.dinnerCost;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
        this.totalCost = this.breakFastCost+this.cribCost+this.dinnerCost;
    }

    public void addProductConsumerList(List<ProductConsumer> productConsumers){
        this.productConsumerList = new ArrayList<>();
        for (ProductConsumer productConsumer: productConsumers) {
            this.addDayMealItemConsume(productConsumer);
        }
    }
    public void addDayMealItemConsume(ProductConsumer  productConsumer){
        this.productConsumerList.add(productConsumer);
        productConsumer.setWeekMeal(this);
    }

    public enum WeekMealStatus {
        ACTIVE,
        DRAFT,
        PAST
    }

}
