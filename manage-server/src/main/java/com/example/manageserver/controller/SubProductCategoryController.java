package com.example.manageserver.controller;

import com.example.manageserver.model.SubProductCategory;
import com.example.manageserver.service.SubProductCategoryService;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subCategory")
@CrossOrigin
public class SubProductCategoryController {

    @Autowired
    SubProductCategoryService subProductCategoryService;


    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SubProductCategory subProductCategory, BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        SubProductCategory res = subProductCategoryService.addSubCategory(subProductCategory);
        return new ResponseEntity<SubProductCategory>(res, HttpStatus.CREATED);
    }
}

