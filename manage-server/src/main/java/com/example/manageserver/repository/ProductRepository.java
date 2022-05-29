package com.example.manageserver.repository;

import com.example.manageserver.model.Item;
import com.example.manageserver.model.MainProductCategory;
import com.example.manageserver.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByMainCategoryId(Long id);

    Page<Product> findByMainCategoryId(Long id, Pageable pageable);
}
