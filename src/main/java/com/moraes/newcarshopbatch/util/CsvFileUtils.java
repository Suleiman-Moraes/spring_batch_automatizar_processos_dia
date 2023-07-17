package com.moraes.newcarshopbatch.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.moraes.newcarshopbatch.api.model.Car;
import com.moraes.newcarshopbatch.api.model.dto.CarDTO;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import lombok.SneakyThrows;

public class CsvFileUtils {

    private static final String PATH_INPUT_FILE = "csv/fileInput/";
    private static final String PATH_OUTPUT_FILE = "src/main/resources/csv/fileOutput/";
    private CSVReader csvReader;
    private CSVWriter csvWriter;
    private String fileName;
    private File file;
    private FileReader fileReader;
    private FileWriter fileWriter;

    public CsvFileUtils(String fileName, boolean isInput) {

        if (isInput) {
            this.fileName = PATH_INPUT_FILE + fileName + ".csv";
        } else {
            this.fileName = PATH_OUTPUT_FILE + fileName + "-" + DateUtils.getNow(DateUtils.YYYY_MM_DD_HH_MM_SS_TRACE)
                    + "-.csv";
        }
    }

    @SneakyThrows
    public CarDTO read() {
        if (this.csvReader == null) {
            getFileToRead();
            getFileReader();
            getCSVReader();
        }

        final String[] fields = this.csvReader.readNext();
        if (fields == null) {
            return null;
        }
        if (fields.length >= 6) {
            return CarDTO.builder()
                    .renavam(fields[0])
                    .brand(fields[1])
                    .model(fields[2])
                    .yearFabrication(fields[3])
                    .modelYear(fields[4])
                    .valueBuy(fields[5])
                    .build();
        }
        return CarDTO.builder().invalid(Arrays.asList(fields).toString()).build();
    }

    private void getFileToRead() {

        ClassLoader classLoader = this.getClass().getClassLoader();

        if (this.file == null) {
            String filePath = classLoader.getResource(this.fileName).getFile();
            this.file = new File(filePath);
        }
    }

    private void getFileReader() throws FileNotFoundException {
        if (this.fileReader == null) {
            this.fileReader = new FileReader(this.file);
        }
    }

    private void getCSVReader() {
        if (this.csvReader == null) {
            this.csvReader = new CSVReader(this.fileReader);
        }
    }

    public void closeReader() throws IOException {
        this.csvReader.close();
        this.fileReader.close();
    }

    public void writer(Car carro) throws IOException {

        if (this.csvWriter == null) {
            getFileToWrite();
            getFileWriter();
            getCSVWriter();
        }

        List<String> carroList = new ArrayList<>();
        carroList.add(carro.getId().toString());
        carroList.add(carro.getRenavam());
        carroList.add(carro.getBrand());
        carroList.add(carro.getModel());

        if (carro.getYearFabrication() != null) {
            carroList.add(carro.getYearFabrication().toString());
        }

        if (carro.getModelYear() != null) {
            carroList.add(carro.getModelYear().toString());
        }

        if (carro.getValueBuy() != null) {
            carroList.add(carro.getValueBuy().toString());
        }

        if (carro.getValueSale() != null) {
            carroList.add(carro.getValueSale().toString());
        }

        if (carro.getPercentMaximumDiscount() != null) {
            carroList.add(carro.getPercentMaximumDiscount().toString());
        }

        if (carro.getStore() != null) {
            carroList.add(carro.getStore().toString());
        }

        carroList.add(DateUtils.getNow());

        this.csvWriter.writeNext(carroList.stream().toArray(String[]::new));
    }

    public void writerError(String[] carroError) throws IOException {

        if (this.csvWriter == null) {
            getFileToWrite();
            getFileWriter();
            getCSVWriter();
        }

        this.csvWriter.writeNext(carroError);
    }

    private boolean getFileToWrite() throws IOException {
        if (this.file == null) {
            this.file = new File(this.fileName);
            return this.file.createNewFile();
        }
        return false;
    }

    private void getFileWriter() throws IOException {
        if (this.fileWriter == null) {
            this.fileWriter = new FileWriter(this.file, true);
        }
    }

    private void getCSVWriter() {
        if (this.csvWriter == null) {
            this.csvWriter = new CSVWriter(this.fileWriter);
        }
    }

    public void closeWriter() throws IOException {
        this.csvWriter.close();
    }
}
