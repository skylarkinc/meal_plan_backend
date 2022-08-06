package com.example.manageserver.controller;

import com.example.manageserver.model.DayMeal;
import com.example.manageserver.model.MealTemplate;
import com.example.manageserver.service.MealTemplateService.MealTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/templates/")
@CrossOrigin
public class MealTemplateController {

    @Autowired
    MealTemplateService mealTemplateService;


    @PostMapping("api/mealTemplates")
    public ResponseEntity<?> addTemplate(@Valid @RequestBody MealTemplate mealTemplate) {
        //TODO add new class and proper response
     return new ResponseEntity<>(this.mealTemplateService.addTemplate(mealTemplate) , HttpStatus.OK);

    }

    @GetMapping("api/mealTemplates/all")
    public ResponseEntity<?> allTemplates() {
        //TODO add new class and proper response
        return new ResponseEntity<>(this.mealTemplateService.getTemplates() , HttpStatus.OK);

    }

    @GetMapping("api/mealTemplates/{type}")
    public ResponseEntity<?> templateByMealType(@PathVariable DayMeal.MealTypes type) {
        //TODO add new class and proper response add exception
        return new ResponseEntity<>(this.mealTemplateService.getTemplateByMealType(type) , HttpStatus.OK);

    }

}
