package com.example.hibernate.Inheritance.entity;

import java.math.BigDecimal;

public class PartTimeEmployee extends Employee {
    private BigDecimal wages;

    protected PartTimeEmployee() {}

    public PartTimeEmployee(String name, BigDecimal wages) {
        super(name);
        this.wages = wages;
    }
    public BigDecimal getWages() {
        return this.wages;
    }

    @Override
    public String toString() {
        return "PartTimeEmployee{" +
                "wages=" + this.wages +
                '}';
    }
}
