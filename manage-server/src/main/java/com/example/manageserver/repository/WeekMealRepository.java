package com.example.manageserver.repository;

import com.example.manageserver.model.WeekMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeekMealRepository  extends JpaRepository<WeekMeal,Long> {

    WeekMeal findByMonthAndWeekAndYear(String month, String week,String year);


    List<WeekMeal> findByIsActiveWeek(boolean isActive);

    List<WeekMeal> findByStatus(String status);
}
