package com.moraes.newcarshopbatch.api.converter;

import java.util.Date;

import com.moraes.newcarshopbatch.api.model.Car;
import com.moraes.newcarshopbatch.api.model.dto.CarDTO;
import com.moraes.newcarshopbatch.util.CarUtils;
import com.moraes.newcarshopbatch.util.DateUtils;

public final class CarConverter {

    private CarConverter() {
    }

    public static String[] getErrorToStringArray(String message) {
        String[] vet = new String[2];
        vet[0] = message;
        vet[1] = DateUtils.getNow();
        return vet;
    }

    public static String[] carToStringArray(CarDTO car) {
        String[] vet = new String[8];
        vet[0] = car.getRenavam();
        vet[1] = car.getBrand();
        vet[2] = car.getModel();
        vet[3] = car.getYearFabrication();
        vet[4] = car.getModelYear();
        vet[5] = car.getValueBuy();
        vet[6] = car.getInvalid();
        vet[7] = DateUtils.getNow();
        return vet;
    }

    public static Car getCarro(CarDTO dto) {
        return Car.builder()
                .renavam(dto.getRenavam())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .yearFabrication(CarUtils.convertToInteger(dto.getYearFabrication()))
                .modelYear(CarUtils.convertToInteger(dto.getModelYear()))
                .valueBuy(CarUtils.convertToBigDecimal(dto.getValueBuy()))
                .store(CarUtils.chooseStore(dto.getValueBuy()))
                .valueSale(CarUtils.calculatePrice(dto.getValueBuy()))
                .percentMaximumDiscount(CarUtils.calculateDiscount(dto.getValueBuy()))
                .dateRegister(new Date())
                .build();
    }
}
