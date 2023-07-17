package com.moraes.newcarshopbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.moraes.newcarshopbatch.api.model.Car;
import com.moraes.newcarshopbatch.api.model.dto.CarDTO;
import com.moraes.newcarshopbatch.config.chuncklet.CarItemProcessor;
import com.moraes.newcarshopbatch.config.chuncklet.CarItemReader;
import com.moraes.newcarshopbatch.config.chuncklet.CarItemWriter;
import com.moraes.newcarshopbatch.config.tasklet.CarValidateTaskelt;
import com.moraes.newcarshopbatch.factory.JobBuilderFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class BatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private PlatformTransactionManager transactionManager;

    @Bean
    Job job() {
        return jobBuilderFactory.get(JobBuilder.class, "carsJob")
                .start(carValidateTaskeltStep())
                .next(carEnrichmentChunkletStep(carItemReader(), carItemProcessor(), carItemWriter()))
                .build();
    }

    @Bean
    Step carValidateTaskeltStep() {
        return jobBuilderFactory.get(StepBuilder.class, "carValidateTaskeltStep")
                .tasklet(new CarValidateTaskelt("cars-import"), transactionManager)
                .build();
    }

    @Bean
    Step carEnrichmentChunkletStep(ItemReader<CarDTO> carItemReader,
            ItemProcessor<CarDTO, Car> carItemProcessor, ItemWriter<Car> carItemWriter) {
        return jobBuilderFactory.get(StepBuilder.class, "carEnrichmentChunkletStep")
                .<CarDTO, Car>chunk(5, transactionManager)
                .reader(carItemReader)
                .processor(carItemProcessor)
                .writer(carItemWriter)
                .build();
    }

    @Bean
    ItemReader<CarDTO> carItemReader() {
        return new CarItemReader();
    }

    @Bean
    ItemProcessor<CarDTO, Car> carItemProcessor() {
        return new CarItemProcessor();
    }

    @Bean
    ItemWriter<Car> carItemWriter() {
        return new CarItemWriter();
    }
}
