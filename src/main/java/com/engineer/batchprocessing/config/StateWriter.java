package com.engineer.batchprocessing.config;

import com.engineer.batchprocessing.entity.States;
import com.engineer.batchprocessing.repository.StateRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateWriter implements ItemWriter<States> {

    @Autowired
    public StateRepository stateRepository;

    @Override
    public void write (Chunk<? extends States> list) throws Exception {
        System.out.println ("Thread Name : " + Thread.currentThread ().getName () );
        stateRepository.saveAll (list);
    }
}
