package com.engineer.batchprocessing.config;

import com.engineer.batchprocessing.entity.States;
import com.engineer.batchprocessing.listener.StepSkipListener;
import com.engineer.batchprocessing.partition.ColumnRangePartitioner;
import com.engineer.batchprocessing.repository.StateRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final StateRepository stateRepository;

    private final StateWriter stateWriter;

    public SpringBatchConfig (JobRepository jobRepository, PlatformTransactionManager transactionManager, StateRepository stateRepository, StateWriter stateWriter) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.stateRepository = stateRepository;
        this.stateWriter = stateWriter;
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
        lineTokenizer.setNames ("name", "country_id", "country_code", "country_name");

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


    // This writer will use if will save each record one by one because we are use save method only
    @Bean
    public RepositoryItemWriter<States> writer() {

        RepositoryItemWriter<States> itemWriter = new RepositoryItemWriter<> ();
        itemWriter.setRepository (stateRepository);
        itemWriter.setMethodName ("save");

        return itemWriter;
    }


    @Bean
    public ColumnRangePartitioner partitioner() {
        return new ColumnRangePartitioner ();
    }

    @Bean
    public PartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler ();
        partitionHandler.setGridSize (2);
        partitionHandler.setTaskExecutor (taskExecutor ());
        partitionHandler.setStep (slaveStep());

        return partitionHandler;
    }


    @Bean
    public SkipPolicy skipPolicy() {
        return new SkipFaultTolerant ();
    }

    @Bean
    public SkipListener skipListener() {
        return new StepSkipListener ();
    }


    @Bean
    public Step slaveStep() {
        return new StepBuilder ("slaveStep", jobRepository)
                .<States, States>chunk (10, transactionManager) // If u r not using partitioner so use chunk side Like: 10 or 50 as u wish, now i am going to use 500 because in partitioner i have set gridSize 2 it means 1 thread execute 1 to 500 and 2nd thread execute 501 to 1000 because csv data size is 1000
                .reader (reader ())
                .processor (processor ())
                //.writer (stateWriter) // If you want to save record one by one without using Partitioner so use writer () method
                .writer (writer ())
                //.taskExecutor (taskExecutor ())// Please uncomment this line if u r not using Partitioner
                .faultTolerant ()
                .skipLimit (100)
                //.skip (NumberFormatException.class)
                //.noSkip (IllegalArgumentException.class)
                .listener (skipListener ())
                .skipPolicy (skipPolicy ())
                .build ();
    }

    @Bean
    public Step masterStep() {
        return new StepBuilder ("masterStep", jobRepository)
                .partitioner (slaveStep ().getName (), partitioner ())
                .partitionHandler (partitionHandler ())
                .build ();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder ("stateJob", jobRepository)
                //.start (masterStep ())
                .start (slaveStep ())
                .build ();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        // If you are not using Partitioner so you can use commented code and pass slaveStep() to runJob() method
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor ( );
        taskExecutor.setConcurrencyLimit (10);
        return taskExecutor;

        /*ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor ();
        taskExecutor.setMaxPoolSize (4);
        taskExecutor.setCorePoolSize (4);
        taskExecutor.setQueueCapacity (4);

        return taskExecutor;*/
    }

}
