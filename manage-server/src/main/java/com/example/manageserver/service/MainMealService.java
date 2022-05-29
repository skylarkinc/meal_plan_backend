package com.example.manageserver.service;
import com.example.manageserver.common.exception.ItemNotFoundException;
import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.Unit;
import com.example.manageserver.repository.MainMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainMealService {

    @Autowired
    MainMealRepository mainMealRepository;

    public MainMeal addMeal(MainMeal mainMeal){

        return  mainMealRepository.save(mainMeal);

    }

    public MainMeal get(Long id) {

        MainMeal mainMeal = mainMealRepository.findById(id).get();

        if(mainMeal == null)
            throw new ItemNotFoundException("Unit not found");

        return  mainMeal;

    }

    public List<MainMeal> getAll() {

        List<MainMeal> mainMealList = mainMealRepository.findAll();

        if(mainMealList == null)
            throw new ItemNotFoundException("List not found");

        return  mainMealList;

    }

    public Page<MainMeal> getAllPageable(Integer page,Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<MainMeal> mainMealList = mainMealRepository.findAll(pageable);

        if(mainMealList == null)
            throw new ItemNotFoundException("List not found");

        return  mainMealList;

    }

    public Boolean delete(Long id) {

        mainMealRepository.deleteById(id);

        return  true;

    }
}

