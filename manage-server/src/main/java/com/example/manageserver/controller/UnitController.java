package com.example.manageserver.controller;


import com.example.manageserver.common.exception.CustomResponseEntityExceptionHandler;
import com.example.manageserver.common.exception.InvalidRequestDataException;
import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.Unit;
import com.example.manageserver.service.MainMealService;
import com.example.manageserver.service.UnitService;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unit")
@CrossOrigin
public class UnitController {

    @Autowired
    UnitService unitService;


    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Unit unit, BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        Unit res = unitService.addUnit(unit);
        return new ResponseEntity<Unit>(res, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id,BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        Unit res = unitService.get(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<?> listAll(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {

        if(page != null && size != null ){
            return new ResponseEntity<>(unitService.getAllPageable(page,size), HttpStatus.OK);
        }
        List<Unit> unitList = unitService.getAll();

        return new ResponseEntity<>(unitList, HttpStatus.OK);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {


        if (id == null)
            throw new InvalidRequestDataException("Invalid id");

       Boolean aBoolean = unitService.delete(id);
        return new ResponseEntity<>(aBoolean, HttpStatus.OK);
    }

}
