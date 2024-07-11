package com.example.ecommerce.config;

import com.hazelcast.config.*;
import org.apache.catalina.webresources.Cache;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }



}

