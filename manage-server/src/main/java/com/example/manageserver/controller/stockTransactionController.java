package com.example.manageserver.controller;

import com.example.manageserver.dto.FilterItemDto;
import com.example.manageserver.dto.StockFullDto;
import com.example.manageserver.dto.StoreTransactionSaveDto;
import com.example.manageserver.model.StockTransaction;
import com.example.manageserver.model.SubMeal;
import com.example.manageserver.model.Supplier;
import com.example.manageserver.service.StockService;
import com.example.manageserver.service.StockTransactionService;
import com.example.manageserver.service.SubMealService;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stockTransaction")
@CrossOrigin
public class stockTransactionController {

    @Autowired
    StockTransactionService stockTransactionService;


    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody StockTransaction stockTransaction, BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        StockTransaction res = stockTransactionService.addStockTransaction(stockTransaction);
        return new ResponseEntity<StockTransaction>(res, HttpStatus.CREATED);
    }

    @PostMapping("/getStockTransactionFullObject")
    public ResponseEntity<?> getStockTransactionFullObject(@RequestBody FilterItemDto filterItemDto) {

        System.out.println(filterItemDto);
        StockFullDto stockFullDto= stockTransactionService.getStockTransactionFullObject(filterItemDto);

        return new ResponseEntity<>(stockFullDto, HttpStatus.OK);
    }

    @PostMapping("/saveStoreTransactionFull")
    public ResponseEntity<?> saveStoreTransactionFull(@RequestBody StoreTransactionSaveDto storeTransactionSaveDto, BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        Boolean res = stockTransactionService.saveStoreTransactionFull(storeTransactionSaveDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}

