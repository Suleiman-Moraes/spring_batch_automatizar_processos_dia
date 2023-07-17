package com.moraes.newcarshopbatch.factory;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@Component
public class JobBuilderFactory {

    private final JobRepository jobRepository;

    @SneakyThrows
    public <T> T get(Class<T> clazz, String name) {
        return clazz.getConstructor(String.class, JobRepository.class).newInstance(name, jobRepository);
    }
}
