package com.moraes.newcarshopbatch.api.service;

import java.util.List;

import com.moraes.newcarshopbatch.api.model.Car;

public interface ICarService {

    List<Car> findAll();

    Object batchExecute();
}
