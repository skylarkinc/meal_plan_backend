package com.example.manageserver.controller;

import com.example.manageserver.model.Stock;
import com.example.manageserver.model.SubMeal;
import com.example.manageserver.service.StockService;
import com.example.manageserver.service.SubMealService;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin
public class stockController {

    @Autowired
    StockService stockService;


    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Stock stock, BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        Stock  res = stockService.addStock(stock);
        return new ResponseEntity<Stock>(res, HttpStatus.CREATED);
    }
}

