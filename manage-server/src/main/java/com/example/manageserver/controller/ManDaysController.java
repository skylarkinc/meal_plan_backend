package com.example.manageserver.controller;

import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.ManDays;
import com.example.manageserver.service.ManDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mandays")
@CrossOrigin
public class ManDaysController {

    @Autowired
    private ManDaysService manDaysService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ManDays manDays) {
        ManDays res = manDaysService.saveMandays(manDays);
        return new ResponseEntity<ManDays>(res, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long  id) {

        ManDays res = manDaysService.getManDays(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
