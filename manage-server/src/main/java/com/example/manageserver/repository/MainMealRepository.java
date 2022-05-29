package com.example.manageserver.repository;
import com.example.manageserver.model.MainMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainMealRepository extends JpaRepository<MainMeal, Long> {

}