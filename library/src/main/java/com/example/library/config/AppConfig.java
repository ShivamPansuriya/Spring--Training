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
        Config config = new Config();
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName("data")
                .setEvictionConfig(new EvictionConfig()
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setMaxSizePolicy(MaxSizePolicy.PER_PARTITION))
                .setTimeToLiveSeconds(60);

        config.addMapConfig(mapConfig).setInstanceName("hazelcast-instance");
        return config;
    }
}
