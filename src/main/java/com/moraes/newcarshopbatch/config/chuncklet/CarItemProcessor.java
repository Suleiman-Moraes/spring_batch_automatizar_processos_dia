package com.moraes.newcarshopbatch.config.chuncklet;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.moraes.newcarshopbatch.api.converter.CarConverter;
import com.moraes.newcarshopbatch.api.model.Car;
import com.moraes.newcarshopbatch.api.model.dto.CarDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CarItemProcessor implements ItemProcessor<CarDTO, Car>, StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Iniciando o PROCESSOR...");
    }

    @Override
    @Nullable
    public Car process(@NonNull CarDTO item) throws Exception {
        return CarConverter.getCarro(item);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Finalizando o PROCESSOR...");
        return ExitStatus.COMPLETED;
    }
}
