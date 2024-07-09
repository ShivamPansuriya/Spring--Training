package com.example.batch.utils;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class Reader implements ItemReader<String> {
    static int counter = 0;
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("reading data...");
        counter++;
        if(counter==5)
            return null;
        return "read complete";
    }
}
