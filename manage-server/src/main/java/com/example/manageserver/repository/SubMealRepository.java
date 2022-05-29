package com.example.manageserver.repository;
import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.SubMeal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubMealRepository extends JpaRepository<SubMeal, Long> {

    List<SubMeal> findByMainMealId(Long id);

    Page<SubMeal> findByMainMealId(Long id, Pageable pageable);
}