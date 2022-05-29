package com.example.manageserver.service;
import com.example.manageserver.common.exception.ItemNotFoundException;
import com.example.manageserver.dto.FilterItemDto;
import com.example.manageserver.dto.StockFullDto;
import com.example.manageserver.dto.StoreTransactionSaveDto;
import com.example.manageserver.model.*;
import com.example.manageserver.repository.MainMealRepository;
import com.example.manageserver.repository.StockTransactionRepository;
import com.example.manageserver.repository.WeekMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StockTransactionService {

    @Autowired
    ProductService productService;

    @Autowired
    UnitService unitService;

    @Autowired
    StockService stockService;



    @Autowired
    StockTransactionRepository stockTransactionRepository;

    @Autowired
    private WeekMealRepository weekMealRepository;

    @Autowired
    private WeekMealService weekMealService;

    public StockTransaction addStockTransaction(StockTransaction stockTransaction){

        return  stockTransactionRepository.save(stockTransaction);

    }

    public StockFullDto getStockTransactionFullObject(FilterItemDto filterItemDto){
        Page<Product> productListPage = this.productService.filterByCustomPageable(filterItemDto);

         List<Product> productList = productListPage.getContent();
         if(productList == null)
             throw new ItemNotFoundException("Items not found");

        List<StockFullDto> stockFullDtoList = new ArrayList<>();
        WeekMeal weekMeal =null;
        System.out.println("filterItemDto.isActive() "+filterItemDto.getIsActive());
        if(filterItemDto.getIsActive() != null && filterItemDto.getIsActive()){
            weekMeal = weekMealRepository.findByStatus("ACTIVE").stream().findFirst().orElse(null);

        }
        if(weekMeal==null){
            weekMeal = weekMealRepository.findByMonthAndWeekAndYear(filterItemDto.getMonth(),filterItemDto.getWeek(),filterItemDto.getYear());
        }
        if(weekMeal != null){
            filterItemDto.setYear(weekMeal.getYear());
            filterItemDto.setMonth(weekMeal.getMonth());
            filterItemDto.setWeek(weekMeal.getWeek());
        }



        HashMap<Long,ProductConsumer> longProductConsumerHashMap = null;
        if(weekMeal != null && weekMeal.getProductConsumerList()!=null){
           longProductConsumerHashMap = weekMealService.groupByProductHashMap(weekMeal.getProductConsumerList());

        }
        if(productList != null){
            Double totalQuantity = 0.0;
            Double totalCost = 0.0;
            for (Product product : productList){
                StockFullDto stockFullDto = new StockFullDto();
                stockFullDto.setProduct( product);
                Stock stock = this.stockService.findByProduct(product);
                if(stock== null){
                     stock = new Stock();
                }
                StockTransaction stockTransaction = this.stockTransactionRepository.findByProductIdAndMonthAndWeekAndYear(product.getId(),filterItemDto.getMonth(),filterItemDto.getWeek(),filterItemDto.getYear());
                if(stockTransaction != null){
                    stockFullDto.setQuantity(stockTransaction.getQuantity());
                    stockFullDto.setTotalCost(stockTransaction.getTotalCost());
                    totalQuantity +=stockTransaction.getQuantity();
                    totalCost +=stockTransaction.getTotalCost();
                }
                if(longProductConsumerHashMap!= null){
                  ProductConsumer productConsumer =  longProductConsumerHashMap.get(product.getId());
                  if(productConsumer!= null){
                      stockFullDto.setWeekConsumeQuantity(productConsumer.getQuantity());
                      double qut = stock.getTotalStock()-stockFullDto.getWeekConsumeQuantity();
                        if(qut<0){
                            qut = qut*-1;
                            stockFullDto.setQuantity(qut);
                            stockFullDto.setTotalCost(qut*stock.getProduct().getUnitPrice());
                        }else{
                            stockFullDto.setQuantity(0.0);
                            stockFullDto.setTotalCost(0.0);
                        }
                  }else{
                      stockFullDto.setWeekConsumeQuantity(0.0);
                  }
                }
                stockFullDto.setStock(stock);
                stockFullDto.setStockTransaction(stockTransaction);
                //if(stockFullDto.getWeekConsumeQuantity() != null && stockFullDto.getWeekConsumeQuantity()> stock.getTotalStock())

                stockFullDtoList.add(stockFullDto);
            }
            StockFullDto returnStockFullDto = new StockFullDto();
            returnStockFullDto.setQuantity(totalQuantity);
            returnStockFullDto.setTotalCost(totalCost);
            returnStockFullDto.setStockFullDtoList(stockFullDtoList);
            returnStockFullDto.setSize(productListPage.getSize());
            returnStockFullDto.setTotalElements(productListPage.getTotalElements());
            returnStockFullDto.setWeekMeal(weekMeal);
            return returnStockFullDto;
        }

        return null;
    }

    @Transactional
    public boolean saveStoreTransactionFull(StoreTransactionSaveDto storeTransactionSaveDto){

        StockTransaction stockTransaction = new StockTransaction();

        if(storeTransactionSaveDto.getProductId() !=null) {
            stockTransaction =this.stockTransactionRepository.findByProductIdAndMonthAndWeek(storeTransactionSaveDto.getProductId(),storeTransactionSaveDto.getMonth(),storeTransactionSaveDto.getWeek());
            if(stockTransaction == null) {
                stockTransaction = new StockTransaction();
                stockTransaction.setProduct(this.productService.get(storeTransactionSaveDto.getProductId()));
                stockTransaction.setUnit(stockTransaction.getProduct().getUnit());
            }
        }
        if(storeTransactionSaveDto.getQuantity() !=null)
            stockTransaction.setQuantity(storeTransactionSaveDto.getQuantity());

        if(storeTransactionSaveDto.getTotalCost() !=null)
            stockTransaction.setTotalCost(storeTransactionSaveDto.getTotalCost());

        if(storeTransactionSaveDto.getMonth() !=null)
            stockTransaction.setMonth(storeTransactionSaveDto.getMonth());

        if(storeTransactionSaveDto.getWeek() !=null)
            stockTransaction.setWeek(storeTransactionSaveDto.getWeek());

        Stock stock = this.stockService.findByProduct(stockTransaction.getProduct());

        stock.setTotalStock(stock.getTotalStock()+storeTransactionSaveDto.getQuantity());
        stock.setUnit(stockTransaction.getUnit());

        stock.setPortionControl(storeTransactionSaveDto.getPortionControl());

        stock = this.stockService.addStock(stock);

        stockTransaction.setStock(stock);

        this.stockTransactionRepository.save(stockTransaction);

        return true;
    }
}

