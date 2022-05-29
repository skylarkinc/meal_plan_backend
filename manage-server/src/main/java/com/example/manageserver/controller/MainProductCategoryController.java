package com.example.manageserver.controller;

import com.example.manageserver.common.exception.InvalidRequestDataException;
import com.example.manageserver.model.MainProductCategory;
import com.example.manageserver.model.Unit;
import com.example.manageserver.service.MainProductCategoryService;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mainCategory")
@CrossOrigin
public class MainProductCategoryController {

    @Autowired
    MainProductCategoryService mainProductCategoryService;


    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody MainProductCategory mainProductCategory, BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        MainProductCategory res = mainProductCategoryService.addMainCategory(mainProductCategory);
        return new ResponseEntity<MainProductCategory>(res, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {

        if (id == null)
            throw new InvalidRequestDataException("Invalid id");

        MainProductCategory res = mainProductCategoryService.get(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<?> listAll(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {

        if(page!= null && size!= null){
            if(page != null && size != null ){
                return new ResponseEntity<>(mainProductCategoryService.getAllPageable(page,size), HttpStatus.OK);
            }
        }

        List<MainProductCategory> mainProductCategoryList = mainProductCategoryService.getAll();

        return new ResponseEntity<>(mainProductCategoryList, HttpStatus.OK);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Boolean aBoolean = mainProductCategoryService.delete(id);
        return new ResponseEntity<>(aBoolean, HttpStatus.OK);
    }
}

