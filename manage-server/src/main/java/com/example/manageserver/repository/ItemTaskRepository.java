package com.example.manageserver.repository;

import com.example.manageserver.model.ItemTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemTaskRepository extends CrudRepository<ItemTask, Long> {

    List<ItemTask> findByItemIdentifierOrderByPriority(String id);

    ItemTask findByItemSequence(String sequence);
}
