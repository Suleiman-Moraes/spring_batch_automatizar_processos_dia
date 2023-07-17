package com.moraes.newcarshopbatch.config.chuncklet;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;

import com.moraes.newcarshopbatch.api.model.dto.CarDTO;
import com.moraes.newcarshopbatch.util.ConstantUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CarItemReader implements ItemReader<CarDTO>, StepExecutionListener {

    private Iterator<CarDTO> carInIterator;

    @Override
    @SuppressWarnings({ "unchecked" })
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext ec = stepExecution.getJobExecution().getExecutionContext();
        List<CarDTO> cars = (List<CarDTO>) ec.get(ConstantUtils.CAR_IN_LIST);
        if (cars != null && !cars.isEmpty()) {
            this.carInIterator = cars.iterator();
        }
        log.info("Iniciando o READER...");
    }

    @Override
    public CarDTO read() {
        log.info("Call read");
        if (this.carInIterator != null && this.carInIterator.hasNext()) {
            return this.carInIterator.next();
        }
        return null;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Finalizando o READER...");
        return ExitStatus.COMPLETED;
    }
}
