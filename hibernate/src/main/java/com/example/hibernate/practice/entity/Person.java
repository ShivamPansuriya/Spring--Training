package com.example.hibernate.practice.entity;

import jakarta.persistence.*;

import java.util.Date;

//@Entity
@NamedQuery(name = "find_all_person", query = "select p from Person p")
//@Table(name = "person") --> if we want to mapping with different table name than class name(person)
public class Person {

    @Id
    @GeneratedValue
    private int id;

//    @Column(unique=true, name = "name")--> if we want to mapping of filed with different column name than field name(name)
    private String name;
    private String location;
    private Date birthDate;

    public Person() {

    }

    public Person(int id, String name, String location, Date birthDate) {
        super();
        this.id = id;
        this.name = name;
        this.location = location;
        this.birthDate = birthDate;
    }

    public Person(String name, String location, Date birthDate) {
        super();
        this.name = name;
        this.location = location;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return String.format("\nPerson [id=%s, name=%s, location=%s, birthDate=%s]", id, name, location, birthDate);
    }
}
