package com.example.manageserver.repository;

import com.example.manageserver.model.SubProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubProductCategoryRepository extends CrudRepository<SubProductCategory, Long> {
}
