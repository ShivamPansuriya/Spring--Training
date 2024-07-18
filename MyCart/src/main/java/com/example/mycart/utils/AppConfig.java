package com.example.mycart.utils;

import com.hazelcast.config.*;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;


@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName("mycache")
                .setEvictionConfig(new EvictionConfig()
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setMaxSizePolicy(MaxSizePolicy.PER_PARTITION))
                .setTimeToLiveSeconds(60);

        config.addMapConfig(mapConfig).setInstanceName("hazelcast-instance");
        return config;
    }

    @Bean
    public KeyGenerator customKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName());
            sb.append(params[0].toString()).append("-");
            System.out.println(sb);
            return sb.toString();
        };
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor()
    {
        var executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(10);

        executor.setMaxPoolSize(50);

        executor.setQueueCapacity(500);

        executor.initialize();

        return executor;
    }

}
