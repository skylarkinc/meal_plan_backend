package com.example.manageserver.controller;

import com.example.manageserver.common.exception.InvalidRequestDataException;
import com.example.manageserver.model.SubMeal;
import com.example.manageserver.model.Supplier;
import com.example.manageserver.model.Unit;
import com.example.manageserver.service.SupplierService;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import com.example.manageserver.service.SubMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
@CrossOrigin
public class SupplierController {

    @Autowired
    SupplierService supplierService;


    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Supplier supplier, BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        Supplier res = supplierService.addSupplier(supplier);
        return new ResponseEntity<Supplier>(res, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id,BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        Supplier res = supplierService.get(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<?> listAll(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {

        if(page != null && size != null ){
            return new ResponseEntity<>(supplierService.getAllPageable(page,size), HttpStatus.OK);
        }

        List<Supplier> supplierList = supplierService.getAll();

        return new ResponseEntity<>(supplierList, HttpStatus.OK);
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (id == null)
            throw new InvalidRequestDataException("Invalid id");

        Boolean aBoolean = supplierService.delete(id);
        return new ResponseEntity<>(aBoolean, HttpStatus.OK);
    }
}

