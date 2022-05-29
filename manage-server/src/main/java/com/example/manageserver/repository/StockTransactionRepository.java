package com.example.manageserver.repository;
import com.example.manageserver.model.StockTransaction;
import com.example.manageserver.model.SubMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

        StockTransaction findByProductIdAndMonthAndWeek(Long productId,String month,String week);

        StockTransaction findByProductIdAndMonthAndWeekAndYear(Long productId,String month,String week,String year);

}