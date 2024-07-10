package com.example.library.config;

import com.hazelcast.config.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ObjectInputFilter;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Config hazelcastConfig()
    {
        var config = new Config();
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName("book")
                .setEvictionConfig(new EvictionConfig()
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE))
                .setTimeToLiveSeconds(10); // 1800 seconds = 30 minutes

        config.addMapConfig(mapConfig);

        return config;
    }
}
