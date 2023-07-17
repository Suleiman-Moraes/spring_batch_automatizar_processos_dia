package com.moraes.newcarshopbatch.config.tasklet;

import java.util.LinkedList;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import com.moraes.newcarshopbatch.api.model.dto.CarDTO;
import com.moraes.newcarshopbatch.api.validate.CarValidate;
import com.moraes.newcarshopbatch.util.ConstantUtils;
import com.moraes.newcarshopbatch.util.CsvFileUtils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CarValidateTaskelt implements Tasklet, StepExecutionListener {

    private List<CarDTO> cars;
    private String fileName;

    public CarValidateTaskelt(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        cars = new LinkedList<>();
    }

    @Override
    @Nullable
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        CsvFileUtils csvIn = new CsvFileUtils(fileName, true);
        CarDTO dto = csvIn.read();

        while (dto != null) {
            cars.add(dto);
            dto = csvIn.read();
        }

        csvIn.closeReader();

        this.cars = CarValidate.validate(cars);

        if (CollectionUtils.isEmpty(cars)) {
            throw new RuntimeException("The list of cars is empty!");
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    @Nullable
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution
                .getJobExecution()
                .getExecutionContext()
                .put(ConstantUtils.CAR_IN_LIST, cars);
        return ExitStatus.COMPLETED;
    }
}
