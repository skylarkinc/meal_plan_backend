package com.example.manageserver.service;
import com.example.manageserver.model.BaseIngredient;
import com.example.manageserver.repository.BaseIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseIngredientService {

    @Autowired
    BaseIngredientRepository baseIngredientRepository;

    public BaseIngredient addBaseIngredientMeal(BaseIngredient baseIngredient){

        return  baseIngredientRepository.save(baseIngredient);

    }
}

