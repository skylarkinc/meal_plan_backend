package com.example.manageserver.service;


import com.example.manageserver.dto.OrderSummaryDto;
import com.example.manageserver.dto.ServiceWeekDto;
import com.example.manageserver.dto.TotalItemCountsDto;
import com.example.manageserver.model.*;
import com.example.manageserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private WeekMealRepository weekMealRepository;

    @Autowired
    private ManDaysRepository manDaysRepository;

    @Autowired
    private MainMealRepository mainMealRepository;

    @Autowired
    private SubMealRepository subMealRepository;

    public List<OrderSummaryDto> orderSummary(){

         List<Stock>  stockList =   stockRepository.findAll();

         HashMap<Supplier,List<Stock>> supplierListHashMap = this.groupBySupplier(stockList);

         WeekMeal  weekMeal = weekMealRepository.findByStatus(WeekMeal.WeekMealStatus.ACTIVE.toString()).stream().findFirst().orElse(null);
         List<OrderSummaryDto> orderSummaryDtoList = new ArrayList<>();
        for (Map.Entry<Supplier, List<Stock>> entry : supplierListHashMap.entrySet()) {

            OrderSummaryDto orderSummaryDto = this.getStockTotal(entry.getValue(),weekMeal);
            orderSummaryDto.setSupplier(entry.getKey());
            orderSummaryDtoList.add(orderSummaryDto);
        }

        return orderSummaryDtoList;
    }

    private OrderSummaryDto getStockTotal(List<Stock> stockList,WeekMeal weekMeal){
        OrderSummaryDto orderSummaryDto = new OrderSummaryDto();
        double total = 0;
        double weeklyUse = 0;
        HashMap<Long,Double> productConsumerListMap = this.getItemConsume(weekMeal.getProductConsumerList());
        for(Stock stock: stockList){
            total += stock.getTotalStock();
            Double aDouble = productConsumerListMap.get(stock.getProduct().getId());
            if(aDouble != null)
                weeklyUse +=  aDouble;
        }
        orderSummaryDto.setInStockValue(total);
        orderSummaryDto.setUnit(stockList.stream().findFirst().orElse(null).getUnit());
        orderSummaryDto.setWeeklyUse(weeklyUse);


        return orderSummaryDto;
    }

    private HashMap<Long,Double> getItemConsume(List<ProductConsumer> productConsumerList){
        HashMap<Long,Double> longDoubleHashMap = new HashMap<>();

        if(productConsumerList == null)
            return null;
        for(ProductConsumer productConsumer: productConsumerList){

            longDoubleHashMap.put(productConsumer.getProduct().getId(),productConsumer.getQuantity());
        }

        return longDoubleHashMap;
    }

    private HashMap<Supplier,List<Stock>> groupBySupplier(List<Stock>  stockList){

        HashMap<Supplier,List<Stock>> supplierListHashMap = new HashMap<>();

        if(stockList == null)
            return null;

        for(Stock stock: stockList){

            List<Stock> stocks = supplierListHashMap.get(stock.getProduct().getSupplier());

            if(stocks ==null)
                stocks = new ArrayList<>();

            stocks.add(stock);

            supplierListHashMap.put(stock.getProduct().getSupplier(),stocks);
        }
        return supplierListHashMap;
    }

    public ServiceWeekDto getServiceWeek() {

        WeekMeal  weekMeal = weekMealRepository.findByStatus(WeekMeal.WeekMealStatus.ACTIVE.toString()).stream().findFirst().orElse(null);



        if(weekMeal ==null)
            return new ServiceWeekDto(0,null,"Active Week Not Found");

        List<ManDays> manDaysList = manDaysRepository.findByWeekMealId(weekMeal.getId());

        Integer totalServices = 0;

        if(manDaysList!=null){
            for(ManDays manDays : manDaysList){
                totalServices += this.calculateServices(manDays);
            }
        }

        return new ServiceWeekDto(totalServices,weekMeal,"");
    }

    private Integer calculateServices(ManDays manDays){
        if(manDays==null)
            return 0;

        Integer total = 0;
        total +=manDays.getMondayCount()!=null?manDays.getMondayCount():0;
        total +=manDays.getTuesdayCount()!=null?manDays.getTuesdayCount():0;
        total +=manDays.getWednesdayCount()!=null?manDays.getWednesdayCount():0;
        total +=manDays.getThursdayCount()!=null?manDays.getThursdayCount():0;
        total +=manDays.getFridayCount()!=null?manDays.getFridayCount():0;
        total +=manDays.getSaturdayCount()!=null?manDays.getSaturdayCount():0;
        total +=manDays.getSundayCount()!=null?manDays.getSundayCount():0;

        return total;
    }

    public TotalItemCountsDto getTotalItemCounts() {

        Integer totalStockItems = stockRepository.findAll().size();

        Integer totalMenus = mainMealRepository.findAll().size();

        Integer totalRecipe = subMealRepository.findAll().size();

        Integer totalServices = calculateTotalServices();

        return new TotalItemCountsDto(totalStockItems,totalMenus,totalRecipe,totalServices);

    }

    private Integer calculateTotalServices(){
        List<ManDays> manDaysList = this.manDaysRepository.findAll();
        Integer total = 0;
        for(ManDays manDays : manDaysList){
            if(manDays.getTotalManDays()!= null)
                total +=manDays.getTotalManDays();
            else
                total +=this.calculateServices(manDays);
        }
        return total;

    }
}
