package com.moraes.newcarshopbatch.api.validate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.moraes.newcarshopbatch.api.converter.CarConverter;
import com.moraes.newcarshopbatch.api.model.dto.CarDTO;
import com.moraes.newcarshopbatch.util.CsvFileUtils;

public class CarValidate {

    public static List<CarDTO> validate(List<CarDTO> carroDtoList) throws IOException {

        List<CarDTO> validList = new ArrayList<>();
        List<CarDTO> invalidList = new ArrayList<>();

        carroDtoList.stream().forEach(dto -> {

            if (isValid(dto)) {
                validList.add(dto);
            } else {
                invalidList.add(dto);
            }
        });

        createCsvToInvalidList(invalidList);
        return validList;
    }

    private static void createCsvToInvalidList(List<CarDTO> invalidList) throws IOException {

        if (!invalidList.isEmpty()) {

            CsvFileUtils csvOutInvalid = new CsvFileUtils("invalid-import", false);

            invalidList.stream().forEach(carro -> {
                try {
                    csvOutInvalid.writerError(CarConverter.carToStringArray(carro));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            csvOutInvalid.closeWriter();
        }
    }

    public void emptyFile() throws IOException {
        CsvFileUtils emptyCSV = new CsvFileUtils("empty-file", false);

        try {
            emptyCSV.writerError(CarConverter.getErrorToStringArray("Tentativa de Import de arquivo vazio!"));
            emptyCSV.closeWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValid(CarDTO carroDto) {
        return StringUtils.hasText(carroDto.getRenavam())
                && StringUtils.hasText(carroDto.getBrand())
                && StringUtils.hasText(carroDto.getModel())
                && StringUtils.hasText(carroDto.getYearFabrication())
                && StringUtils.hasText(carroDto.getModelYear())
                && StringUtils.hasText(carroDto.getValueBuy());
    }
}
