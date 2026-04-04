package com.engineer.batchprocessing.listener;

import com.engineer.batchprocessing.entity.States;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class StepSkipListener implements SkipListener<States, Number> {
    Logger logger = LoggerFactory.getLogger (StepSkipListener.class);
    @Override
    public void onSkipInRead (Throwable t) {
        logger.info ("A failure on read {} ", t.getMessage ());
    }

    @Override
    public void onSkipInWrite (Number item, Throwable t) {
        logger.info ("A failure on write {}, {} ", t.getMessage (), item);
    }

    @Override
    public void onSkipInProcess (States item, Throwable t) {
        try {
            logger.info ("Item {} was skipped due to the exception {}", new ObjectMapper (  ).writeValueAsString (item), t.getMessage ());
        } catch (JsonProcessingException e) {
            throw new RuntimeException (e);
        }
    }
}
