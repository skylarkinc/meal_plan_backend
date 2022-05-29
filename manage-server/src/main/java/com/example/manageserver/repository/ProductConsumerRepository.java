package com.example.manageserver.repository;

import com.example.manageserver.model.ProductConsumer;
import com.example.manageserver.model.WeekMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductConsumerRepository extends JpaRepository<ProductConsumer,Long> {

  //  List<ProductConsumer> findByWeekMealId(Long id);

    ProductConsumer findByWeekMealIdAndProductId(Long weekMealId, Long productId);

}
