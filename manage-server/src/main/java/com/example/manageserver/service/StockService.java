package com.example.manageserver.service;
import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.Product;
import com.example.manageserver.model.Stock;
import com.example.manageserver.repository.MainMealRepository;
import com.example.manageserver.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;


    public Stock addStock(Stock stock){

        return  stockRepository.save(stock);

    }

    public Stock getStock(Long id){

        return  stockRepository.getById(id);

    }

    public Stock findByProduct(Product product){

        Stock stock = stockRepository.findByProductId(product.getId());
        if(stock == null){
            stock = new Stock();
            stock.setProduct(product);
        }
      return stock;
    }
}

