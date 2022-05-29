package com.example.manageserver.service;

import com.example.manageserver.common.exception.ItemNotFoundException;
import com.example.manageserver.dto.DayMealCostDto;
import com.example.manageserver.dto.MainMealRowIndex;
import com.example.manageserver.model.*;
import com.example.manageserver.repository.DayMealItemConsumeRepository;
import com.example.manageserver.repository.DayMealRepository;
import com.example.manageserver.repository.ProductConsumerRepository;
import com.example.manageserver.repository.WeekMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeekMealService {

    @Autowired
    private WeekMealRepository weekMealRepository;

    @Autowired
    private DayMealRepository dayMealRepository;

    @Autowired
    private DayMealItemConsumeRepository dayMealItemConsumeRepository;

    @Autowired
    private ProductConsumerRepository productConsumerRepository;

    @Autowired
    private ManDaysService manDaysService;

    public WeekMeal saveWeekMeal(WeekMeal weekMeal){
        if(weekMeal == null)
            throw new ItemNotFoundException("Invalid data");
        if(weekMeal.isActiveWeek()){
            deActiveAllMeal();
        }

        WeekMeal savedWeekMeal = weekMealRepository.save(weekMeal);

        manDaysService.createDefaultManDaysWhenWeekMealCreate(weekMeal);

        return savedWeekMeal;
    }

    public WeekMeal statusUpdate(WeekMeal weekMeal){
        if(weekMeal == null)
            throw new ItemNotFoundException("Invalid data");
        if(weekMeal.getStatus().equals(WeekMeal.WeekMealStatus.ACTIVE.toString())){
            deActiveAllMeal();
        }

        WeekMeal savedWeekMeal = weekMealRepository.save(weekMeal);

        return savedWeekMeal;
    }

    private void deActiveAllMeal(){
        List<WeekMeal> weekMeals = this.weekMealRepository.findByStatus(WeekMeal.WeekMealStatus.ACTIVE.toString());
        if(weekMeals!= null){
            List<WeekMeal> weekMealsChanged =    weekMeals.stream().map(weekMeal -> {
                weekMeal.setActiveWeek(false);
                weekMeal.setStatus(WeekMeal.WeekMealStatus.PAST.toString());
                return weekMeal;
            }).collect(Collectors.toList());
            this.weekMealRepository.saveAll(weekMealsChanged);
        }

    }

    public WeekMeal createOrGetWeekMealByDayMeal(DayMeal dayMeal){
        WeekMeal weekMeal = weekMealRepository.findByMonthAndWeekAndYear(dayMeal.getMonth(),dayMeal.getWeek(),dayMeal.getYear());

        if(weekMeal== null) {
            weekMeal = new WeekMeal();
            weekMeal.setWeek(dayMeal.getWeek());
            weekMeal.setMonth(dayMeal.getMonth());
            weekMeal.setYear(dayMeal.getYear());
            weekMeal.setRowIndex(new MainMealRowIndex(dayMeal.getMainMeal().getId(),dayMeal.getMealType()));
            return weekMealRepository.save(weekMeal);
        }

       return  weekMeal;
    }

    @Transactional
    public void calculateAndSaveWeekMealConsumerList(WeekMeal weekMeal){
        System.out.println("line 72  calculateAndSaveWeekMealConsumerList in");
        if(weekMeal != null){
            System.out.println("weekMeal "+weekMeal.getId());
            HashMap<Long,ProductConsumer> longProductConsumerHashMap = new HashMap<>();
            List<ProductConsumer> productConsumerList = weekMeal.getProductConsumerList();
            if(productConsumerList != null){
                longProductConsumerHashMap = this.groupByProductHashMap(productConsumerList);

            }else{
                productConsumerList = new ArrayList<>();
            }
            System.out.println("longProductConsumerHashMap "+longProductConsumerHashMap.size());

            List<DayMeal> dayMealList = this.dayMealRepository.findByWeekMealId(weekMeal.getId());
            System.out.println("dayMealList "+dayMealList.size());
            List<Long> dayMealIdList = this.getIdList(dayMealList);
            System.out.println("dayMealIdList "+dayMealIdList.size());
            List<DayMealItemConsume> dayMealItemConsumeList = this.dayMealItemConsumeRepository.findByDayMealIdIn(dayMealIdList);
            System.out.println("dayMealItemConsumeList "+dayMealItemConsumeList.size());
            if(dayMealItemConsumeList != null){
                HashMap<Long, DayMealCostDto> longDayMealCostDtoHashMap = new HashMap<>();
                for(DayMealItemConsume dayMealItemConsume : dayMealItemConsumeList){
                     //longProductConsumerHashMap.get(dayMealItemConsume.getProduct().getId())

                    DayMealCostDto dayMealCostDto = longDayMealCostDtoHashMap.get(dayMealItemConsume.getProduct().getId());
                    if(dayMealCostDto ==null)
                        dayMealCostDto = new DayMealCostDto();
                    dayMealCostDto.setTotalCost(dayMealCostDto.getTotalCost()+dayMealItemConsume.getCost());
                    dayMealCostDto.setTotalQuantity(dayMealCostDto.getTotalQuantity()+dayMealItemConsume.getQuantity());
                    dayMealCostDto.setProduct(dayMealItemConsume.getProduct());
                    longDayMealCostDtoHashMap.put(dayMealItemConsume.getProduct().getId(),dayMealCostDto);
                }
                System.out.println("longDayMealCostDtoHashMap "+longDayMealCostDtoHashMap.size());
                productConsumerList = new ArrayList<>();
                for(Map.Entry<Long, DayMealCostDto> entry : longDayMealCostDtoHashMap.entrySet()) {
                    ProductConsumer productConsumer = longProductConsumerHashMap.get(entry.getKey());
                    if(productConsumer==null)
                        productConsumer = new ProductConsumer();

                    productConsumer.setProduct(entry.getValue().getProduct());
                    productConsumer.setCost(entry.getValue().getTotalCost());
                    productConsumer.setQuantity(entry.getValue().getTotalQuantity());
                    //productConsumer.setWeekMeal(weekMeal);
                    productConsumerList.add(productConsumer);
                }
                if(productConsumerList.size()>0){
                    for (ProductConsumer productConsumer: productConsumerList) {
                        productConsumer.setWeekMeal(weekMeal);
                        productConsumerRepository.save(productConsumer);
                    }
                    //productConsumerList = productConsumerRepository.saveAll(productConsumerList);
                    //weekMeal.addProductConsumerList(productConsumerList);
                }

                weekMealRepository.save(weekMeal);

            }
        }
    }
    private List<Long> getIdList( List<DayMeal> dayMealList){
        List<Long> longList = new ArrayList<>();
        dayMealList.stream().forEach(dayMeal -> {
            longList.add(dayMeal.getId());
        });

        return longList;
    }


    private HashMap<Long,BaseIngredient> groupByBaseIngredientHashMap(List<BaseIngredient> baseIngredientList,HashMap<Long,BaseIngredient> hashList){
       // HashMap<Long,BaseIngredient> returnList = new HashMap<>();

        for(BaseIngredient baseIngredient: baseIngredientList){
            hashList.put(baseIngredient.getProduct().getId(),baseIngredient);
        }

        return hashList;
    }

    public HashMap<Long,ProductConsumer> groupByProductHashMap(List<ProductConsumer> productConsumerList){
        HashMap<Long,ProductConsumer> returnList = new HashMap<>();

        for(ProductConsumer productConsumer: productConsumerList){
            returnList.put(productConsumer.getProduct().getId(),productConsumer);
        }

        return returnList;
    }

    public WeekMeal getByMonthAndWeek(String month, String week,String year,boolean isActive) {

        if(month ==null || week ==null || year == null)
            throw new ItemNotFoundException("Invalid Param");
        if(isActive)
            return weekMealRepository.findByStatus("ACTIVE").stream().findFirst().orElse(null);

        return weekMealRepository.findByMonthAndWeekAndYear(month,week,year);
    }
}
