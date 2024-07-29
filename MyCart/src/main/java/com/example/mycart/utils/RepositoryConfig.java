package com.example.mycart.utils;

import com.example.mycart.repository.BaseRepositoryImpl;
import com.example.mycart.repository.CustomFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.mycart.repository",
        repositoryFactoryBeanClass = CustomFactoryBean.class)
public class RepositoryConfig {
}