package com.moraes.newcarshopbatch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moraes.newcarshopbatch.api.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

}
