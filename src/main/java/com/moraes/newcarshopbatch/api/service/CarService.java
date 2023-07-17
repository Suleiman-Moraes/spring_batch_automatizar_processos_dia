package com.moraes.newcarshopbatch.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.moraes.newcarshopbatch.api.model.Car;
import com.moraes.newcarshopbatch.api.repository.CarRepository;
import com.moraes.newcarshopbatch.util.DateUtils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class CarService implements ICarService {

    private CarRepository repository;

    private JobLauncher jobLauncher;

    private Job job;

    private static final String A_CADA_10_SEG = "*/10 * * * * *";
    private static final String A_CADA_30_SEG = "*/30 * * * * *";
    private static final String A_CADA_60_SEG = "*/60 * * * * *";
    private static final String AS_8_E_AS_9_HRS_T_OS_DIAS = "0 0 8-9 * * *";
    private static final String A_CADA_HR_T_OS_DIAS = "0 0 * * * *";
    private static final String AS_6_E_AS_18_HRS_T_OS_DIAS = "0 0 6,18 * * *";
    private static final String NO_NATAL_25_DEZ_TODOS_OS_ANOS = "0 0 0 25 12 ?";
    private static final String A_CADA_30_MIN_ENTRE_7_E_11_HRS_T_OS_DIAS = "0 0/30 8-10 * * *";
    private static final String A_CADA_HR_DAS_8_AS_17_HRS_DE_SEGUNDA_A_SEXTA = "0 0 8-17 * * MON-FRI";

    @Override
    public List<Car> findAll() {
        return repository.findAll();
    }

    // @Scheduled(cron = "ss mm HH dd MM ?")
    @Scheduled(cron = "* 28 19 * * ?")
    @Override
    public Object batchExecute() {
        log.info("Iniciou o Job " + DateUtils.getNow());

        Map<String, JobParameter<?>> map = new HashMap<>();
        map.put("time", new JobParameter<>(System.currentTimeMillis(), Long.class));

        try {
            JobExecution jobExecution = jobLauncher.run(job, new JobParameters(map));

            while (jobExecution.isRunning()) {
                log.info("Job em execução...");
            }

            log.info(DateUtils.getNow());
            return jobExecution.getStatus();
        } catch (Exception e) {
            log.error("Falha ao tentar executar JOB " + e.getMessage());
            return BatchStatus.FAILED;
        }
    }
}
