package com.example.manageserver.controller;

import com.example.manageserver.model.Unit;
import com.example.manageserver.model.WeekMeal;
import com.example.manageserver.service.UnitService;
import com.example.manageserver.service.WeekMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/weekMeal")
public class WeekMealController {

    @Autowired
    WeekMealService weekMealService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody WeekMeal weekMeal) {


        WeekMeal res = weekMealService.saveWeekMeal(weekMeal);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/getByMonthAndWeek")
    public ResponseEntity<?> getByMonthAndWeek(
            @RequestParam("month") String month,
            @RequestParam("week")String week,
            @RequestParam("year") String year,
            @RequestParam("isActive") boolean isActive) {

        WeekMeal res = weekMealService.getByMonthAndWeek(month,week,year,isActive);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

//    @GetMapping("/checkWeekMeal")
//    public ResponseEntity<?> getByMonthAndWeek(@RequestParam("year") String year,@RequestParam("month") String month,@RequestParam("week")String week) {
//
//        WeekMeal res = weekMealService.getByMonthAndWeek(month,week);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }

    @PostMapping("/statusUpdate")
    public ResponseEntity<?> statusUpdate(@RequestBody WeekMeal weekMeal) {


        WeekMeal res = weekMealService.statusUpdate(weekMeal);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
