package com.example.manageserver.service;

import com.example.manageserver.common.exception.ItemNotFoundException;
import com.example.manageserver.model.DayMeal;
import com.example.manageserver.model.ManDays;
import com.example.manageserver.model.WeekMeal;
import com.example.manageserver.repository.ManDaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ManDaysService {

    @Autowired
    private ManDaysRepository manDaysRepository;


    public ManDays saveMandays(ManDays manDays){
        if(manDays==null)
            throw new ItemNotFoundException("Invalid Data");

        if(manDays.getId() == null && manDays.getMealType() != null){
           ManDays dbManday  = manDaysRepository.findByWeekMealIdAndMealType(manDays.getWeekMeal().getId(), manDays.getMealType());
           if(dbManday != null)
            manDays.setId(dbManday.getId());
        }

       return  manDaysRepository.save(manDays);
    }

    public ManDays getManDays(Long weekMealId, String mealType){

        ManDays manDays;
        if(mealType==null && weekMealId ==null)
            throw new ItemNotFoundException("Invalid param data");
        //    manDays = manDaysRepository.findAll().stream().findFirst().orElse(null);

        manDays = manDaysRepository.findByWeekMealIdAndMealType(weekMealId,mealType);

        if(manDays== null)
            throw new ItemNotFoundException("Mandays not configured");

        return  manDays;
    }

    public ManDays getManDays(Long id){

        ManDays manDays;
        if(id ==null)
            throw new ItemNotFoundException("Invalid param data");
        //    manDays = manDaysRepository.findAll().stream().findFirst().orElse(null);

        manDays = manDaysRepository.getById(id);

        if(manDays== null)
            throw new ItemNotFoundException("Mandays not configured");

        return  manDays;
    }

    public void createDefaultManDaysWhenWeekMealCreate(WeekMeal weekMeal) {

        if(weekMeal != null) {
            ManDays manDaysBREAKFAST = new ManDays(null, DayMeal.MealTypes.BREAKFAST.toString(), 0, 0, 0, 0, 0, 0, weekMeal, 0,0);
            manDaysRepository.save(manDaysBREAKFAST);
            ManDays manDaysCRIB = new ManDays(null, DayMeal.MealTypes.CRIB.toString(), 0, 0, 0, 0, 0, 0, weekMeal, 0,0);
            manDaysRepository.save(manDaysCRIB);
            ManDays manDaysDINNER = new ManDays(null, DayMeal.MealTypes.DINNER.toString(), 0, 0, 0, 0, 0, 0, weekMeal, 0,0);
            manDaysRepository.save(manDaysDINNER);
        }
    }
}
