package com.engineer.batchprocessing.config;

import com.engineer.batchprocessing.entity.States;
import org.springframework.batch.item.ItemProcessor;

public class StateProcessor implements ItemProcessor<States, States> {

    @Override
    public States process (States item) throws Exception {
        /*if ( item.getCountryName ().equalsIgnoreCase ("Anguilla") )
            return item;
        else
            return null;*/

        if ( item.getCountryCode ().equalsIgnoreCase ("KAUS")) {
            int i = Integer.parseInt (item.getCountryCode ( ));
        }
        return item;
    }
}
