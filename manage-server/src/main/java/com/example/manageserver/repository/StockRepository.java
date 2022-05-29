package com.example.manageserver.repository;
import com.example.manageserver.model.Stock;
import com.example.manageserver.model.SubMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByProductId(Long id);

}