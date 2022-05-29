package com.example.manageserver.service;
import com.example.manageserver.common.exception.CustomResponseEntityExceptionHandler;
import com.example.manageserver.common.exception.ItemNotFoundException;
import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.Unit;
import com.example.manageserver.repository.MainMealRepository;
import com.example.manageserver.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    @Autowired
    UnitRepository unitRepository;

    public Unit addUnit(Unit unit){

        return  unitRepository.save(unit);

    }

    public Unit get(Long id) {

        Unit unit = unitRepository.findById(id).get();

        if(unit == null)
            throw new ItemNotFoundException("Unit not found");

        return  unit;

    }

    public List<Unit> getAll() {


        List<Unit> unitList = unitRepository.findAll();

        if(unitList == null)
            throw new ItemNotFoundException("List not found");

        return  unitList;

    }

    public Page<Unit> getAllPageable(Integer page,Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Unit> unitList = unitRepository.findAll(pageable);

        if(unitList == null)
            throw new ItemNotFoundException("List not found");

        return  unitList;

    }

    public Boolean delete(Long id) {

         unitRepository.deleteById(id);

        return  true;

    }
}

