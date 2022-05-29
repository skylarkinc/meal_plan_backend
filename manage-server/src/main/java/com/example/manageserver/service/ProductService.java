package com.example.manageserver.service;
import com.example.manageserver.common.exception.ItemNotFoundException;
import com.example.manageserver.dto.FilterItemDto;
import com.example.manageserver.model.MainMeal;
import com.example.manageserver.model.MainProductCategory;
import com.example.manageserver.model.Product;
import com.example.manageserver.model.SubMeal;
import com.example.manageserver.repository.MainProductCategoryRepository;
import com.example.manageserver.repository.ProductRepository;
import com.example.manageserver.repository.SubMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MainProductCategoryRepository mainProductCategoryRepository;

    public Product addProduct(Product product){

        return  productRepository.save(product);

    }

    public Product get(Long id) {

        Product product = productRepository.findById(id).get();

        if(product == null)
            throw new ItemNotFoundException("Unit not found");

        return  product;

    }

    public List<Product> getAll() {

        List<Product> productList = productRepository.findAll();

        if(productList == null)
            throw new ItemNotFoundException("List not found");

        return  productList;

    }

    public Page<Product> getAllPageable(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productList = productRepository.findAll(pageable);

        if(productList == null)
            throw new ItemNotFoundException("List not found");

        return  productList;

    }

    public Boolean delete(Long id) {

        productRepository.deleteById(id);

        return  true;

    }

    public List<Product> findByMainCategory(Long mainCategoryId){

        MainProductCategory mainProductCategory = mainProductCategoryRepository.getById(mainCategoryId);
        if(mainProductCategory != null){
         return  this.productRepository.findByMainCategoryId(mainProductCategory.getId());
        }
        return null;
    }

    public List<Product> filterByCustom(FilterItemDto filterItemDto){



        if(filterItemDto.getMainCategoryId()!=null){
         return   this.findByMainCategory(filterItemDto.getMainCategoryId());
        }

        return this.getAll();
    }

    public Page<Product> filterByCustomPageable(FilterItemDto filterItemDto) {

        if(filterItemDto.getPage()==null)
            filterItemDto.setPage(0);
        if(filterItemDto.getSize() == null)
            filterItemDto.setSize(10);

        Pageable pageable = PageRequest.of(filterItemDto.getPage(), filterItemDto.getSize());
        Page<Product> productList = null;
        if(filterItemDto.getMainCategoryId()!=null){
          return   this.productRepository.findByMainCategoryId(filterItemDto.getMainCategoryId(),pageable);

        }
        return   this.productRepository.findAll(pageable);

    }
}

