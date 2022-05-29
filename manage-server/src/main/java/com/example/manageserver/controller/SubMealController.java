package com.example.manageserver.controller;

import com.example.manageserver.common.exception.InvalidRequestDataException;
import com.example.manageserver.model.SubMeal;
import com.example.manageserver.model.Unit;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import com.example.manageserver.service.SubMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subMeal")
@CrossOrigin
public class SubMealController {

    @Autowired
    SubMealService subMealService;


    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SubMeal subMeal, BindingResult result) {

//        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
//        if (errMap != null) return errMap;

        SubMeal res = subMealService.addSubMeal(subMeal);
        return new ResponseEntity<SubMeal>(res, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id,BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        SubMeal res = subMealService.get(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<?> listAll(
            @RequestParam(value = "mainMealId", required = false) Long mainMealId,
            @RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size
    ) {
        List<SubMeal> subMealList = null;
        Page<SubMeal> subMealPages = null;

        if(mainMealId != null) {
            if(page!=null && size != null){
                subMealPages= subMealService.findByMainMealIdPageable(mainMealId,page,size);
                return    new ResponseEntity<>(subMealPages, HttpStatus.OK);
            }
            subMealList = subMealService.findByMainMealId(mainMealId);
        }else {
            subMealList = subMealService.getAll();
            if(page!=null && size != null){
                subMealPages= subMealService.getAllPageable(page,size);
                return    new ResponseEntity<>(subMealPages, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(subMealList, HttpStatus.OK);
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (id == null)
            throw new InvalidRequestDataException("Invalid id");

        Boolean aBoolean = subMealService.delete(id);
        return new ResponseEntity<>(aBoolean, HttpStatus.OK);
    }
}

