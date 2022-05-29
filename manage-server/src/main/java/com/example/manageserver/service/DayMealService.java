package com.example.manageserver.service;

import com.example.manageserver.common.exception.ItemNotFoundException;
import com.example.manageserver.dto.DayMealCostDto;
import com.example.manageserver.dto.DayMealDto;
import com.example.manageserver.dto.DayMealFullDto;
import com.example.manageserver.dto.MainMealRowIndex;
import com.example.manageserver.model.*;
import com.example.manageserver.repository.DayMealItemConsumeRepository;
import com.example.manageserver.repository.DayMealRepository;
import com.example.manageserver.repository.ProductConsumerRepository;
import com.example.manageserver.repository.WeekMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DayMealService {

    @Autowired
    private ManDaysService manDaysService;

    @Autowired
    private DayMealRepository dayMealRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductConsumerRepository productConsumerRepository;

    @Autowired
    private DayMealItemConsumeRepository dayMealItemConsumeRepository;

    @Autowired
    private WeekMealService weekMealService;

    @Autowired
    private WeekMealRepository weekMealRepository;

    @Transactional
    public DayMeal saveDayMeal(DayMeal dayMeal){
        if(dayMeal == null)
            throw new ItemNotFoundException("Invalid Data");

        WeekMeal weekMeal = null;

        if(dayMeal.getWeekMeal()==null){
            weekMeal =  weekMealService.createOrGetWeekMealByDayMeal(dayMeal);
            dayMeal.setWeekMeal(weekMeal);
        }else{
            weekMeal = dayMeal.getWeekMeal();
        }
        //weekMeal.setRowIndex(dayMeal.getMainMeal().getId());
        weekMeal.setRowIndex(new MainMealRowIndex(dayMeal.getMainMeal().getId(),dayMeal.getMealType()));
        //List<DayMealItemConsume> dayMealItemConsumeList = dayMeal.getDayMealItemConsumeList();
        if(dayMeal.getDayMealItemConsumeList()!=null && !dayMeal.getDayMealItemConsumeList().isEmpty()){
            //dayMeal.setDayMealItemConsumeList( removeDuplicates(dayMeal.getDayMealItemConsumeList()));

            DayMealCostDto dayMealCostDto = this.calculateMealCost(dayMeal.getDayMealItemConsumeList(),dayMeal);
            dayMeal.setMealCost(dayMealCostDto.getTotalCost());
            //List<DayMealItemConsume> dayMealItemConsumeList = dayMealItemConsumeRepository.saveAll(dayMealCostDto.getDayMealItemConsumeList());
            //dayMealItemConsumeList.forEach(dayMealItemConsume -> dayMealItemConsume.setDayMeal(dayMeal));
            //dayMealItemConsumeRepository.
            if(dayMealCostDto.getDayMealItemConsumeIdList()!= null)
                dayMealItemConsumeRepository.deleteAllByIdInBatch(dayMealCostDto.getDayMealItemConsumeIdList());

            dayMeal.addDayMealItemConsumes(dayMealCostDto.getDayMealItemConsumeList());
//            if(dayMeal.getId()!= null){
//                for(DayMealItemConsume dayMealItemConsume :dayMealCostDto.getDayMealItemConsumeList()){
//                    if(dayMealItemConsume.getId()==null){
//                        dayMealItemConsume.setDayMeal(dayMeal);
//                    }
//                    this.dayMealItemConsumeRepository.save(dayMealItemConsume);
//                }
//            }else{
//                dayMeal.addDayMealItemConsumes(dayMealCostDto.getDayMealItemConsumeList());
//            }
        }else{
            dayMeal.setMealCost(0.0);
        }

        dayMeal = this.dayMealRepository.save(dayMeal);
        this.weekMealRepository.save(weekMeal);
        //dayMealToItemConsumeList(dayMeal.getDayMealItemConsumeList(),dayMeal);
        return dayMeal;
    }

    @Transactional
    private void dayMealToItemConsumeList(List<DayMealItemConsume> dayMealItemConsumeList,DayMeal dayMeal){
        List<DayMealItemConsume> dayMealItemConsumeList1 = new ArrayList<>();
        for(DayMealItemConsume dayMealItemConsume : dayMealItemConsumeList){
            dayMealItemConsume.setDayMeal(dayMeal);
            dayMealItemConsumeList1.add(dayMealItemConsume);
        }
        this.dayMealItemConsumeRepository.saveAll(dayMealItemConsumeList1);
    }

    private List<DayMealItemConsume> removeDuplicates(List<DayMealItemConsume> dayMealItemConsumeList){
        List<Long> idList = new ArrayList<>();
        System.out.println(dayMealItemConsumeList.size());
        for(DayMealItemConsume dayMealItemConsume: dayMealItemConsumeList){
            if(idList.contains(dayMealItemConsume.getId())){
                dayMealItemConsumeList.remove(dayMealItemConsume);
            }else {
                idList.add(dayMealItemConsume.getId());
            }
        }
        return dayMealItemConsumeList;
    }

    public DayMeal getDayMeal(Long id){
        if(id == null)
            throw new ItemNotFoundException("Invalid Data");

        return this.dayMealRepository.getById(id);
    }

    public DayMealFullDto getWeekMealPlan(String mealType, String month,String week,Boolean isActiveWeek,String year){
        System.out.println(isActiveWeek);
        DayMealFullDto returnObject = new DayMealFullDto();
        List<DayMealFullDto> dayMealFullDtoList = new ArrayList<>();
        List<DayMeal> dayMealList = null;
        WeekMeal weekMeal = null;
        if(isActiveWeek!= null && isActiveWeek){
            weekMeal = weekMealRepository.findByStatus("ACTIVE").stream().findFirst().orElse(null);
        }else{
            weekMeal = weekMealRepository.findByMonthAndWeekAndYear(month,week,year);
        }

        if(weekMeal==null){
            returnObject.setIsWeekMealFound(false);
            returnObject.setMessage("Please Setup a week");
            return returnObject;
        }
        returnObject.setIsWeekMealFound(true);

        if(weekMeal != null &&  mealType != null){
            dayMealList = dayMealRepository.findByMealTypeAndWeekMealId(mealType,weekMeal.getId());
        }


        ManDays manDays = this.manDaysService.getManDays(weekMeal.getId(),mealType);
        if(manDays ==null)
            throw  new ItemNotFoundException("Setup ManDays first");

        returnObject.setManDays(manDays);

        if(dayMealList != null){
            HashMap<MainMeal,List<DayMeal>> mainMealListHashMap = this.groupByMainMeal(dayMealList);
            returnObject.setTotalDayMealCostList(this.getTotalTemplate());
            WeekMeal finalWeekMeal = weekMeal;
            mainMealListHashMap.forEach((mainMeal, dayMeals) -> {
                DayMealFullDto dayMealFullDto =  new DayMealFullDto();
                dayMealFullDto.setMainMeal(mainMeal);
                Set<MainMealRowIndex> longSet = finalWeekMeal.getMainMealRowIndexs();
                dayMealFullDto.setRowIndexByWeekMeal(longSet,new MainMealRowIndex(mainMeal.getId(),mealType));
                dayMealFullDto.setDayMealDtoList(this.mapToDayMealDto(dayMeals,mainMeal));
                returnObject.setTotalDayMealCostList(this.calculateTotalDayMealCost(returnObject.getTotalDayMealCostList(),dayMealFullDto.getDayMealDtoList()));

                dayMealFullDtoList.add(dayMealFullDto);
            });
            System.out.println(mealType);
            if(mealType.equals(DayMeal.MealTypes.BREAKFAST.toString())){
                weekMeal.setBreakFastCost(this.getWeekTotal(returnObject.getTotalDayMealCostList()));
            }
            if(mealType.equals( DayMeal.MealTypes.CRIB.toString())){
                weekMeal.setCribCost(this.getWeekTotal(returnObject.getTotalDayMealCostList()));
            }
            if(mealType.equals( DayMeal.MealTypes.DINNER.toString())){
                weekMeal.setDinnerCost(this.getWeekTotal(returnObject.getTotalDayMealCostList()));
            }
            weekMeal = weekMealRepository.save(weekMeal);
            //weekMeal.setTotalCost();
            returnObject.setWeekMeal(weekMeal);
            //returnObject.setTotalCost(this.getWeekTotal(returnObject.getTotalDayMealCostList()));
            returnObject.setDayMealFullDtoList(dayMealFullDtoList);
        }
        System.out.println("line 143 calculateAndSaveWeekMealConsumerList");
        this.weekMealService.calculateAndSaveWeekMealConsumerList(weekMeal);

        return returnObject;
    }

    private Double getWeekTotal(List<DayMealDto> totalDayMealList){
        Double returnValue = 0.0;
        for(DayMealDto dayMealDto :totalDayMealList)
            returnValue +=dayMealDto.getCost();

        return returnValue;
    }

    private List<DayMealDto> calculateTotalDayMealCost(List<DayMealDto> totalDayMealCostList,List<DayMealDto> dayMealDtoList ){
        List<DayMealDto> returnList = new ArrayList<>();
        // totalDayMealCostList.forEach(object -> {
        for(DayMealDto object:totalDayMealCostList){
            DayMealDto dayMealObject =   dayMealDtoList.get(object.getDayIndex());
            System.out.println("object.getDayIndex() "+object.getDayIndex());
            System.out.println("dayMealObject "+dayMealObject.getCost());
            if(dayMealObject.getCost() !=null){
                Double totalCost = dayMealObject.getCost(); //dayMealObject.getCost()* this.getManDaysByDayIndex(object.getDayIndex(),null);

                object.setCost(object.getCost()+totalCost);

                returnList.add(object);
            }
        };


        return  totalDayMealCostList;
    }

    private List<DayMealDto> mapToDayMealDto(List<DayMeal> dayMealList,MainMeal mainMeal){
        dayMealList.sort(Comparator.comparing(DayMeal::getDayIndex));
        HashMap<Integer,DayMeal> dayMealHash = this.dayMealHashMap(dayMealList);

        List<DayMealDto> dayMealDtoList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            //System.out.println(i);
            DayMeal dayMeal = new DayMeal();
            try {
                if (dayMealList != null && !dayMealList.isEmpty() && dayMealList.size() >= i)
                    dayMeal = dayMealHash.get(i);
            }catch (Exception e){
                dayMeal = new DayMeal();
            }
           DayMealDto dayMealDto = new DayMealDto();
           if(dayMeal != null && dayMeal.getId() != null && dayMeal.getDayIndex()==i){
               if(dayMeal.getDayMealItemConsumeList()!=null && !dayMeal.getDayMealItemConsumeList().isEmpty()){
                 DayMealCostDto dayMealCostDto = this.calculateMealCost(dayMeal.getDayMealItemConsumeList(),dayMeal);
                 dayMeal.setMealCost(dayMealCostDto.getTotalCost());
               }else{
                   dayMeal.setMealCost(0.0);
               }
               dayMealDto = new DayMealDto(dayMeal);
           }else{
               dayMeal = new DayMeal();
               dayMealDto.setDayIndex(i);
               dayMealDto.setMeal(dayMeal);
               dayMealDto.getMeal().setMainMeal(mainMeal);
           }
            dayMealDtoList.add(dayMealDto);
        }
        return dayMealDtoList;

    }

    private HashMap<Integer,DayMeal> dayMealHashMap(List<DayMeal> dayMealList){
        HashMap<Integer,DayMeal> dayMealHash = new HashMap<>();
        for(DayMeal dayMeal :dayMealList){
            dayMealHash.put(dayMeal.getDayIndex(),dayMeal);
        }
        return dayMealHash;
    }

    private HashMap<MainMeal,List<DayMeal>> groupByMainMeal(List<DayMeal> dayMealList){
        HashMap<MainMeal,List<DayMeal>> mainMealListHashMap =  new HashMap<>();

        dayMealList.stream().forEach(dayMeal -> {
            List<DayMeal> mainMealDayMealList = mainMealListHashMap.get(dayMeal.getMainMeal());
            if(mainMealDayMealList == null)
                mainMealDayMealList = new ArrayList<>();

            mainMealDayMealList.add(dayMeal);

            mainMealListHashMap.put(dayMeal.getMainMeal(),mainMealDayMealList);
        });
        mainMealListHashMap.entrySet().stream().sorted(Comparator.comparing(key -> key.getKey().getId()));
        return  mainMealListHashMap;
    }


    private DayMealCostDto calculateMealCost(List<DayMealItemConsume> dayMealItemConsumeList,DayMeal dayMeal){

        DayMealCostDto dayMealCostDto = new DayMealCostDto();
        List<DayMealItemConsume> dayMealItemConsumes = new ArrayList<>();
        if(dayMealItemConsumeList == null) {
            dayMealCostDto.setTotalCost(0.0);
            return dayMealCostDto;
        }

        Double totalCost = 0.0;

        Integer manDays = this.getManDaysByDayIndex(dayMeal.getDayIndex(),dayMeal.getMealType(),dayMeal.getWeekMeal().getId());
        List<Long> dayMealItemConsumeIdList = new ArrayList<>();
        for(DayMealItemConsume dayMealItemConsume: dayMealItemConsumeList){
            Stock stock = this.stockService.findByProduct(dayMealItemConsume.getProduct());
            if(stock != null && stock.getPortionControl()>0){
              Double cost =  stock.getPortionControl()* dayMealItemConsume.getProduct().getUnitPrice()* manDays;
                totalCost+=cost;
                dayMealItemConsume.setCost(cost);
                if(dayMealItemConsume.getId()!= null){
                    dayMealItemConsumeIdList.add(dayMealItemConsume.getId());
                }
                dayMealItemConsume.setQuantity(stock.getPortionControl()*manDays);
                dayMealItemConsumes.add(new DayMealItemConsume(dayMealItemConsume.getProduct(),dayMealItemConsume.getDayMeal(),dayMealItemConsume.getQuantity(),dayMealItemConsume.getCost()));

            }

        }
        dayMealCostDto.setDayMealItemConsumeIdList(dayMealItemConsumeIdList);
        dayMealCostDto.setDayMealItemConsumeList(dayMealItemConsumes);
        dayMealCostDto.setTotalCost(totalCost);

        return dayMealCostDto;

    }


    private Integer getManDaysByDayIndex(Integer dayIndex,String mealType,Long weekMealId){
       ManDays manDays =  this.manDaysService.getManDays(weekMealId,mealType);

       if(manDays != null){
           switch (dayIndex) {
               case 0: return manDays.getMondayCount();
               case 1: return manDays.getTuesdayCount();
               case 2: return manDays.getWednesdayCount();
               case 3: return manDays.getThursdayCount();
               case 4: return manDays.getFridayCount();
               case 5: return manDays.getSaturdayCount();
               case 6: return manDays.getSundayCount();

           }
       }
       return 0;
    }

    private List<DayMealDto> getTotalTemplate(){
        List<DayMealDto> dayMealDtoList = new ArrayList<>();
        for(int i=0;i<7;i++){
            DayMealDto dayMealDto = new DayMealDto();
            dayMealDto.setDayIndex(i);
            dayMealDto.setCost(0.0);
            dayMealDtoList.add(dayMealDto);
        }
        return dayMealDtoList;
    }
}
