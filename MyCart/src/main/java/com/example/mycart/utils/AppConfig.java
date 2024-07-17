package com.example.mycart.utils;

import com.hazelcast.config.*;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
//@EnableCaching
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Config hazelcastConfig()
    {
        Config config = new Config("hazelcast-instance");
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName("test")
                .setEvictionConfig(new EvictionConfig()
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setMaxSizePolicy(MaxSizePolicy.PER_PARTITION))
                .setTimeToLiveSeconds(60);

        config.addMapConfig(mapConfig);
        return config;
    }
}
