package com.moraes.newcarshopbatch.config.chuncklet;

import java.io.IOException;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import com.moraes.newcarshopbatch.api.model.Car;
import com.moraes.newcarshopbatch.api.repository.CarRepository;
import com.moraes.newcarshopbatch.util.CsvFileUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CarItemWriter implements ItemWriter<Car>, StepExecutionListener {

    private CsvFileUtils csvSavedCars;

    @Autowired
    private CarRepository carRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        this.csvSavedCars = new CsvFileUtils("savedCars", false);

        log.info("Finalizando o WRITER...");
    }

    @Override
    public void write(@NonNull Chunk<? extends Car> carOutList) throws Exception {
        List<? extends Car> savedCarList = this.carRepository.saveAll(carOutList);

        savedCarList.stream().forEach(car -> {
            try {
                this.csvSavedCars.writer(car);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        try {
            this.csvSavedCars.closeWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Finalizando o WRITER...");
        return ExitStatus.COMPLETED;
    }
}
