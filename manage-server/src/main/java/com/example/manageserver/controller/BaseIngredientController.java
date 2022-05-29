package com.example.manageserver.controller;

import com.example.manageserver.model.BaseIngredient;

import com.example.manageserver.service.BaseIngredientService;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/baseIngredient")
@CrossOrigin
public class BaseIngredientController {

    @Autowired
    BaseIngredientService baseIngredientService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody BaseIngredient baseIngredient, BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        BaseIngredient res = baseIngredientService.addBaseIngredientMeal(baseIngredient);
        return new ResponseEntity<BaseIngredient>(res, HttpStatus.CREATED);
    }
}


