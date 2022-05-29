package com.example.manageserver.service;
import com.example.manageserver.common.exception.ItemNotFoundException;
import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.MainProductCategory;
import com.example.manageserver.model.Unit;
import com.example.manageserver.repository.MainProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainProductCategoryService {

    @Autowired
    MainProductCategoryRepository mainProductCategoryRepository;

    public MainProductCategory addMainCategory(MainProductCategory mainMeal){

        return  mainProductCategoryRepository.save(mainMeal);

    }

    public MainProductCategory get(Long id) {

        MainProductCategory mainProductCategory = mainProductCategoryRepository.findById(id).get();

        if(mainProductCategory == null)
            throw new ItemNotFoundException("Unit not found");

        return  mainProductCategory;

    }

    public List<MainProductCategory> getAll() {

        List<MainProductCategory> mainProductCategoryList = mainProductCategoryRepository.findAll();

        if(mainProductCategoryList == null)
            throw new ItemNotFoundException("List not found");

        return  mainProductCategoryList;

    }

    public Page<MainProductCategory> getAllPageable(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<MainProductCategory> mainProductCategoryList = mainProductCategoryRepository.findAll(pageable);

        if(mainProductCategoryList == null)
            throw new ItemNotFoundException("List not found");

        return  mainProductCategoryList;

    }

    public Boolean delete(Long id) {

        mainProductCategoryRepository.deleteById(id);

        return  true;

    }
}

