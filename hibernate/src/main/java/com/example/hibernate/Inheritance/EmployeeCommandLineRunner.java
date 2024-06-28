package com.example.hibernate.Inheritance;

import com.example.hibernate.Inheritance.entity.Employee;
import com.example.hibernate.Inheritance.entity.FullTimeEmployee;
import com.example.hibernate.Inheritance.entity.PartTimeEmployee;
import com.example.hibernate.Inheritance.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//@Component
public class EmployeeCommandLineRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        employeeRepository.save(new FullTimeEmployee("Manish",new BigDecimal(10000)));
        employeeRepository.save(new PartTimeEmployee("Suresh",new BigDecimal(50)));

        logger.info("Employee --> {}", employeeRepository.findAll());
    }
}
