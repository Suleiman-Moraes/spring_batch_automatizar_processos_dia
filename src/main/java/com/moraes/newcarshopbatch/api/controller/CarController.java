package com.moraes.newcarshopbatch.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moraes.newcarshopbatch.api.model.Car;
import com.moraes.newcarshopbatch.api.service.ICarService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/car")
public class CarController {

    private ICarService service;

    @GetMapping
    public ResponseEntity<List<Car>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("batch")
    public ResponseEntity<String> execute() {
        return ResponseEntity.ok(service.batchExecute().toString());
    }
}
