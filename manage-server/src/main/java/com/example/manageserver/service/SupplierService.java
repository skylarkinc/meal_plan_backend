package com.example.manageserver.service;
import com.example.manageserver.common.exception.ItemNotFoundException;
import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.Product;
import com.example.manageserver.model.SubMeal;
import com.example.manageserver.model.Supplier;
import com.example.manageserver.repository.SubMealRepository;
import com.example.manageserver.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public Supplier addSupplier(Supplier supplier){

        return  supplierRepository.save(supplier);

    }

    public Supplier get(Long id) {

        Supplier supplier = supplierRepository.findById(id).get();

        if(supplier == null)
            throw new ItemNotFoundException("Unit not found");

        return  supplier;

    }

    public List<Supplier> getAll() {

        List<Supplier> supplierList = supplierRepository.findAll();

        if(supplierList == null)
            throw new ItemNotFoundException("List not found");

        return  supplierList;

    }

    public Boolean delete(Long id) {

        supplierRepository.deleteById(id);

        return  true;

    }

    public Page<Supplier> getAllPageable(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Supplier> supplierList = supplierRepository.findAll(pageable);

        if(supplierList == null)
            throw new ItemNotFoundException("List not found");

        return  supplierList;

    }
}

