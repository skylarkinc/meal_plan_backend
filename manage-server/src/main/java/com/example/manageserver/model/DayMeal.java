package com.example.manageserver.model;

import com.example.manageserver.model.Base.BaseModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class DayMeal extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer dayIndex;

    private String dayOfWeek;

    private Double mealCost;

    @OneToOne
    private MainMeal mainMeal;

    @OneToOne
    private SubMeal subMeal;

//    @ManyToMany
//    private List<BaseIngredient> baseIngredientList;

    private String mealType;

    @OneToOne
    private WeekMeal weekMeal;

    @Transient
    private String month;

    @Transient
    private String week;

    @Transient
    private String year;

    @JsonManagedReference
    @OneToMany(mappedBy = "dayMeal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayMealItemConsume> dayMealItemConsumeList;


    private void setMealType(MealTypes mealTypes){
        this.mealType = mealTypes.toString();
    }

    public enum MealTypes {
        BREAKFAST,
        CRIB,
        DINNER
    }

    public void addDayMealItemConsumes(List<DayMealItemConsume> dayMealItemConsumeLists){
        this.dayMealItemConsumeList = new ArrayList<>();
        for (DayMealItemConsume dayMealItemConsume: dayMealItemConsumeLists) {
            this.addDayMealItemConsume(dayMealItemConsume);
        }
    }
    public void addDayMealItemConsume(DayMealItemConsume  dayMealItemConsume){
        if(dayMealItemConsumeList.contains(dayMealItemConsume)){

        }
        dayMealItemConsumeList.add(dayMealItemConsume);
        dayMealItemConsume.setDayMeal(this);
    }

}
