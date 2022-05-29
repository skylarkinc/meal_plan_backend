package com.example.manageserver.controller;

import com.example.manageserver.dto.FilterItemDto;
import com.example.manageserver.model.Product;
import com.example.manageserver.model.SubMeal;
import com.example.manageserver.model.Unit;
import com.example.manageserver.service.ProductService;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import com.example.manageserver.service.SubMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {

    @Autowired
    SubMealService subMealService;

    @Autowired
    ProductService productService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Product product, BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        Product res = productService.addProduct(product);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id,BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        Product res = productService.get(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/listAll")
    public ResponseEntity<?> listAll(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size) {


        if(page != null && size != null ){
            return new ResponseEntity<>(productService.getAllPageable(page,size), HttpStatus.OK);
        }

        List<Product> productList = productService.getAll();

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,BindingResult result) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        Boolean aBoolean = productService.delete(id);
        return new ResponseEntity<>(aBoolean, HttpStatus.OK);
    }

    @GetMapping("/listByMainCategory")
    public ResponseEntity<?> listByMainCategory(FilterItemDto filterItemDto) {


        List<Product> productList = productService.filterByCustom(filterItemDto);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}

