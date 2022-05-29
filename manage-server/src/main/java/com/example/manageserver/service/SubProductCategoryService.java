package com.example.manageserver.service;
import com.example.manageserver.model.SubProductCategory;
import com.example.manageserver.repository.SubProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubProductCategoryService {

    @Autowired
    SubProductCategoryRepository subProductCategoryRepository;

    public SubProductCategory addSubCategory(SubProductCategory subProductCategory){

        return  subProductCategoryRepository.save(subProductCategory);

    }


}

