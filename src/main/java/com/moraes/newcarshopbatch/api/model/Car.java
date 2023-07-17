package com.moraes.newcarshopbatch.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.moraes.newcarshopbatch.api.model.enumeration.StoreEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "car")
public class Car implements Serializable {

    private static final long serialVersionUID = 2l;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String renavam;

    private String brand;

    private String model;

    private Integer yearFabrication;

    private Integer modelYear;

    private BigDecimal valueBuy;

    private BigDecimal valueSale;

    private BigDecimal percentMaximumDiscount;

    @Enumerated(EnumType.STRING)
    private StoreEnum store;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegister;
}
