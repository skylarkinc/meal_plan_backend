package com.example.manageserver.repository;

import com.example.manageserver.model.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findByItemIdentifier(String Identifier);
}
