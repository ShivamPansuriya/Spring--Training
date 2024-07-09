package com.example.batch;

import com.example.batch.utils.Processor;
import com.example.batch.utils.Reader;
import com.example.batch.utils.Writer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;
    @Bean
    public Job job()
    {
        return new JobBuilder("job-builder",jobRepository).start(step1()).build();
    }

    @Bean
    public Step step1()
    {
        return new StepBuilder("step-1",jobRepository).<String ,String >chunk(3,transactionManager).reader(reader()).writer(writer()).processor(processor()).build();
    }

    @Bean
    public Processor processor() {
        return new Processor();
    }

    @Bean
    public Reader reader(){
        return new Reader();
    }

    @Bean
    public Writer writer(){
        return new Writer();
    }
}
