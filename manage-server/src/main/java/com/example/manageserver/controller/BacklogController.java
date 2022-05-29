package com.example.manageserver.controller;

import com.example.manageserver.model.ItemTask;
import com.example.manageserver.service.ItemTaskService;
import com.example.manageserver.service.validationService.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ItemTaskService itemTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addITtoBacklog(@Valid @RequestBody ItemTask itemTask, BindingResult result, @PathVariable String backlog_id, Principal principal) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        ItemTask itemTask1 = itemTaskService.addItemTask(backlog_id, itemTask, principal.getName());
        return new ResponseEntity<ItemTask>(itemTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ItemTask> getItemBacklog(@PathVariable String backlog_id, Principal principal) {
        return itemTaskService.findBacklogById(backlog_id, principal.getName());
    }

    @GetMapping("/{backlog_id}/{it_id}")
    public ResponseEntity<?> getItemTask(@PathVariable String backlog_id, @PathVariable String it_id, Principal principal) {
        ItemTask itemTask = itemTaskService.findITByItemSequence(backlog_id, it_id, principal.getName());
        return new ResponseEntity<ItemTask>(itemTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{it_id}")
    public ResponseEntity<?> updateItemTask(@Valid @RequestBody ItemTask itemTask, BindingResult result, @PathVariable String backlog_id, @PathVariable String it_id, Principal principal) {

        ResponseEntity<?> errMap = mapValidationErrorService.MapValidationService(result);
        if (errMap != null) return errMap;

        ItemTask updatedTask = itemTaskService.updateByItemSequence(itemTask, backlog_id, it_id, principal.getName());
        return new ResponseEntity<ItemTask>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{it_id}")
    public ResponseEntity<?> deleteItemTask(@PathVariable String backlog_id, @PathVariable String it_id, Principal principal) {

        itemTaskService.deleteITByItemSequence(backlog_id, it_id, principal.getName());
        return new ResponseEntity<String>("Item task " + it_id + " was deleted successfully.", HttpStatus.OK);
    }
}
