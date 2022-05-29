package com.example.manageserver.controller;

import com.example.manageserver.dto.OrderSummaryDto;
import com.example.manageserver.dto.ServiceWeekDto;
import com.example.manageserver.dto.TotalItemCountsDto;
import com.example.manageserver.model.Product;
import com.example.manageserver.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/orderSummary")
    public ResponseEntity<?> orderSummary() {

        List<OrderSummaryDto> res = reportService.orderSummary();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/getServiceWeek")
    public ResponseEntity<?> getServiceWeek() {

        ServiceWeekDto res = reportService.getServiceWeek();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/getTotalItemCounts")
    public ResponseEntity<?> getTotalItemCounts() {

        TotalItemCountsDto res = reportService.getTotalItemCounts();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
