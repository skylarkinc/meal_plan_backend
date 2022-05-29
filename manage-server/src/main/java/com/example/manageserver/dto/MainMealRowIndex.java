package com.example.manageserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MainMealRowIndex implements Serializable {


    private Long mainMealId;

    private String mealType;

    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Date date;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer rowIndex;

    public MainMealRowIndex(Long mainMealId,String mealType){
        this.mainMealId = mainMealId;
        this.mealType = mealType;
    }

}
