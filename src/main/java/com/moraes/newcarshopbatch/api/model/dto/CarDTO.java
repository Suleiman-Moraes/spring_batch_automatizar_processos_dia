package com.moraes.newcarshopbatch.api.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDTO implements Serializable {

    private static final long serialVersionUID = 1l;

    private String renavam;
    private String brand;
    private String model;
    private String yearFabrication;
    private String modelYear;
    private String valueBuy;
    private String invalid;
}
