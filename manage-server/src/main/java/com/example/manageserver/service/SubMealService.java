package com.example.manageserver.service;
import com.example.manageserver.common.exception.ItemNotFoundException;
import com.example.manageserver.model.BaseIngredient;
import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.SubMeal;
import com.example.manageserver.model.Unit;
import com.example.manageserver.repository.BaseIngredientRepository;
import com.example.manageserver.repository.MainMealRepository;
import com.example.manageserver.repository.SubMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubMealService {

    @Autowired
    SubMealRepository subMealRepository;

    @Autowired
    BaseIngredientRepository baseIngredientRepository;

    public SubMeal addSubMeal(SubMeal subMeal){

        if(subMeal.getBaseIngredientList()!= null){
            List<BaseIngredient> baseIngredientList = baseIngredientRepository.saveAll(subMeal.getBaseIngredientList());
            subMeal.setBaseIngredientList(baseIngredientList);
        }

        return  subMealRepository.save(subMeal);

    }

    public SubMeal get(Long id) {

        SubMeal subMeal = subMealRepository.findById(id).get();

        if(subMeal == null)
            throw new ItemNotFoundException("Unit not found");

        return  subMeal;

    }

    public List<SubMeal> getAll() {

        List<SubMeal> subMealList = subMealRepository.findAll();

        if(subMealList == null)
            throw new ItemNotFoundException("List not found");

        return  subMealList;

    }

    public Page<SubMeal> getAllPageable(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SubMeal> subMealList = subMealRepository.findAll(pageable);

        if(subMealList == null)
            throw new ItemNotFoundException("List not found");

        return  subMealList;

    }

    public List<SubMeal> findByMainMealId(Long mainMealId) {

          List<SubMeal> subMealList = subMealRepository.findByMainMealId(mainMealId);

        if(subMealList == null)
            throw new ItemNotFoundException("List not found");

        return  subMealList;

    }

    public Page<SubMeal> findByMainMealIdPageable(Long mainMealId, Integer page, Integer size) {


            Pageable pageable = PageRequest.of(page, size);
            Page<SubMeal> subMealList = subMealRepository.findByMainMealId(mainMealId,pageable);


        if(subMealList == null)
            throw new ItemNotFoundException("List not found");

        return  subMealList;

    }

    public Boolean delete(Long id) {

        subMealRepository.deleteById(id);

        return  true;

    }
}

