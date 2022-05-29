package com.example.manageserver.controller;


import com.example.manageserver.common.exception.InvalidRequestDataException;
import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.Unit;
import com.example.manageserver.service.MainMealService;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mainMeal")
@CrossOrigin
public class MainMealController {

    @Autowired
    MainMealService mainMealService;


    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody MainMeal mainMeal, BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        MainMeal res = mainMealService.addMeal(mainMeal);
        return new ResponseEntity<MainMeal>(res, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id,BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        MainMeal res = mainMealService.get(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<?> listAll(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {

        if(page != null && size != null ){
            return new ResponseEntity<>(mainMealService.getAllPageable(page,size), HttpStatus.OK);
        }

        List<MainMeal> mainMealList = mainMealService.getAll();

        return new ResponseEntity<>(mainMealList, HttpStatus.OK);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (id == null)
            throw new InvalidRequestDataException("Invalid id");

        Boolean aBoolean = mainMealService.delete(id);
        return new ResponseEntity<>(aBoolean, HttpStatus.OK);
    }
}
