package com.example.manageserver.repository;

import com.example.manageserver.model.DayMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayMealRepository extends JpaRepository<DayMeal,Long> {

     List<DayMeal> findByMealType(String mealType);

     List<DayMeal> findByWeekMealId(Long id);

     List<DayMeal> findByMealTypeAndWeekMealId(String mealType,Long weekMealId);

    // List<Long> findByWeekMeal(Long id);
}
