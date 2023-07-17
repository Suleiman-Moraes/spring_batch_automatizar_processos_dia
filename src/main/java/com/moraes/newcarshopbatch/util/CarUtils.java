package com.moraes.newcarshopbatch.util;

import java.math.BigDecimal;

import com.moraes.newcarshopbatch.api.model.dto.PercentEnum;
import com.moraes.newcarshopbatch.api.model.enumeration.StoreEnum;

public final class CarUtils {

    private CarUtils() {
    }

    private static final BigDecimal _99_H = new BigDecimal("99999.99");
    private static final BigDecimal _149_H = new BigDecimal("149999.99");

    public static StoreEnum chooseStore(String price) {

        BigDecimal valor = convertToBigDecimal(price);

        if (valor == null) {
            return null;
        }

        if (isLojaPaulista(valor)) {
            return StoreEnum.STORE_AV_PAULISTA;
        }

        if (isLojaIbirapuera(valor)) {
            return StoreEnum.STORE_IBIRAPUERA;
        }

        if (isLojaJardins(valor)) {
            return StoreEnum.STORE_JARDINS;
        }

        return null;
    }

    public static BigDecimal calculateDiscount(String valorVenda) {

        BigDecimal valor = convertToBigDecimal(valorVenda);

        if (valor == null) {
            return null;
        }

        if (isLojaPaulista(valor)) {
            return new BigDecimal(PercentEnum.P_10.getPercent());
        }

        if (isLojaIbirapuera(valor)) {
            return new BigDecimal(PercentEnum.P_05.getPercent());
        }

        if (isLojaJardins(valor)) {
            return new BigDecimal(PercentEnum.P_02.getPercent());
        }

        return null;
    }

    public static BigDecimal calculatePrice(String valorCompra) {

        BigDecimal valor = convertToBigDecimal(valorCompra);

        if (valor == null) {
            return null;
        }

        if (isLojaPaulista(valor)) {
            return valor.add(valor.multiply(new BigDecimal(PercentEnum.P_25.getPercent())));
        }
        if (isLojaIbirapuera(valor)) {
            return valor.add(valor.multiply(new BigDecimal(PercentEnum.P_15.getPercent())));
        }
        if (isLojaJardins(valor)) {
            return valor.add(valor.multiply(new BigDecimal(PercentEnum.P_05.getPercent())));
        }
        return null;
    }

    private static boolean isLojaJardins(BigDecimal valor) {
        return valor.compareTo(_149_H) > 0;
    }

    private static boolean isLojaIbirapuera(BigDecimal valor) {
        return ((valor.compareTo(_149_H) == 0) || (valor.compareTo(_99_H) > 0) && (valor.compareTo(_149_H) < 0));
    }

    private static boolean isLojaPaulista(BigDecimal valor) {
        return valor.compareTo(_99_H) <= 0;
    }

    public static BigDecimal convertToBigDecimal(String valor) {
        try {
            return new BigDecimal(valor);
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer convertToInteger(String ano) {
        try {
            return Integer.parseInt(ano);
        } catch (Exception e) {
            return null;
        }
    }
}