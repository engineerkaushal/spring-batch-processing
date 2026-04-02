package com.engineer.batchprocessing.config;

import com.engineer.batchprocessing.entity.States;
import com.engineer.batchprocessing.repository.StateRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final StateRepository stateRepository;

    public SpringBatchConfig (JobRepository jobRepository, PlatformTransactionManager transactionManager, StateRepository stateRepository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.stateRepository = stateRepository;
    }

    @Bean
    public FlatFileItemReader<States> reader() {
        FlatFileItemReader<States> itemReader = new FlatFileItemReader<> ();
        itemReader.setResource (new FileSystemResource ("src/main/resources/states.csv"));
        itemReader.setName ("csvReader");
        itemReader.setLinesToSkip (1);
        itemReader.setLineMapper (lineMapper());

        return itemReader;
    }

    // The use of this method how to read the data from csv file or how to map data from csv file to States object
    private LineMapper<States> lineMapper () {
        DefaultLineMapper<States> lineMapper = new DefaultLineMapper<> ();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer ();
        lineTokenizer.setDelimiter (",");
        lineTokenizer.setStrict (false);
        lineTokenizer.setNames ("id", "name", "country_id", "country_code", "country_name");

        BeanWrapperFieldSetMapper<States>  fieldSetMapper = new BeanWrapperFieldSetMapper<> ();
        fieldSetMapper.setTargetType (States.class);

        lineMapper.setLineTokenizer (lineTokenizer);
        lineMapper.setFieldSetMapper (fieldSetMapper);

        return lineMapper;
    }


    @Bean
    public StateProcessor processor() {
        return new StateProcessor ();
    }

    @Bean
    public RepositoryItemWriter<States> writer() {

        RepositoryItemWriter<States> itemWriter = new RepositoryItemWriter<> ();
        itemWriter.setRepository (stateRepository);
        itemWriter.setMethodName ("save");

        return itemWriter;
    }


    @Bean
    public Step step1() {
        return new StepBuilder ("step1", jobRepository)
                .<States, States>chunk (10, transactionManager)
                .reader (reader ())
                .processor (processor ())
                .writer (writer ())
                .build ();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder ("stateJob", jobRepository)
                .start (step1 ())
                .build ();
    }
}
