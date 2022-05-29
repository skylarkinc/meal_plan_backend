package com.example.manageserver.repository;

import com.example.manageserver.model.ManDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManDaysRepository extends JpaRepository<ManDays,Long> {

    ManDays findByMealType(String mealType);

    ManDays findByWeekMealIdAndMealType(Long weekMealId, String mealType);

    List<ManDays> findByWeekMealId(Long weekMealId);
 }
