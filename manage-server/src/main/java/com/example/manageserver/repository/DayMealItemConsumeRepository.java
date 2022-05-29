package com.example.manageserver.repository;

import com.example.manageserver.model.DayMealItemConsume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayMealItemConsumeRepository extends JpaRepository<DayMealItemConsume,Long> {

    DayMealItemConsume findByDayMealIdAndProductId(Long dayMealId, Long productId);

    List<DayMealItemConsume> findByDayMealIdIn(List<Long> dayMealIdList);
}
