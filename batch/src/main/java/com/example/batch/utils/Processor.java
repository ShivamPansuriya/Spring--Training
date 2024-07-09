package com.example.batch.utils;

import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        System.out.println("processing item");
        return item.toUpperCase();
    }
}
