package com.example.manageserver.repository;

import com.example.manageserver.model.DayMeal;
import com.example.manageserver.model.MealTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealTemplateRepository extends JpaRepository<MealTemplate , Long> {
    //this is for get meal templates using meal type
    List<MealTemplate> findByMealType(DayMeal.MealTypes mealTypes);
}
