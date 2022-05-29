package com.example.manageserver.model;

import com.example.manageserver.model.Base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManDays extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mealType;

    private Integer mondayCount;

    private Integer tuesdayCount;

    private Integer wednesdayCount;

    private Integer thursdayCount;

    private Integer fridayCount;

    private Integer saturdayCount;

    @OneToOne
    private WeekMeal weekMeal;

    private Integer sundayCount;

    private Integer totalManDays;

    @PrePersist
    @PreUpdate
    private void calculateServices(){

        Integer total = 0;
        total +=mondayCount!=null?mondayCount:0;
        total +=tuesdayCount!=null?tuesdayCount:0;
        total +=wednesdayCount!=null?wednesdayCount:0;
        total +=thursdayCount!=null?thursdayCount:0;
        total +=fridayCount!=null?fridayCount:0;
        total +=saturdayCount!=null?saturdayCount:0;
        total +=sundayCount!=null?sundayCount:0;

        this.totalManDays = total;
    }


}
