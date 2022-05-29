package com.example.manageserver.controller;


import com.example.manageserver.dto.DayMealFullDto;
import com.example.manageserver.model.DayMeal;
import com.example.manageserver.model.ManDays;
import com.example.manageserver.service.DayMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dayMeal")
@CrossOrigin
public class DayMealController {

    @Autowired
    private DayMealService dayMealService;


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody DayMeal dayMeal) {

        DayMeal res = dayMealService.saveDayMeal(dayMeal);

        return new ResponseEntity<DayMeal>(res, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {

        DayMeal res = dayMealService.getDayMeal(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/getWeekMealPlan")
    public ResponseEntity<?> getWeekMealPlan(
            @RequestParam(value = "mealType", required = false) String mealType,
            @RequestParam(value = "month", required = false) String month,
            @RequestParam(value = "week", required = false) String week,
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestParam(value = "year", required = false) String year
        ) {

        DayMealFullDto res = dayMealService.getWeekMealPlan(mealType,month,week,isActive,year);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
