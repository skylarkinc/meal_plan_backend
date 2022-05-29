package com.example.manageserver.repository;

import com.example.manageserver.model.MainProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainProductCategoryRepository extends JpaRepository<MainProductCategory, Long> {
}
