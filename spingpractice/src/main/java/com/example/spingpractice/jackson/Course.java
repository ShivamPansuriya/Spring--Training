package com.example.spingpractice.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties(value = {"number"}, ignoreUnknown = true)
public class Course {
    private String courseName;
    @Id
    @GeneratedValue
//    @JsonIgnore
    private  int number;
    private String name;

    public Course(){}
    public Course( String courseName, String name) {
        this.courseName = courseName;
        this.name = name;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getName() {
        return name;
    }
    public int getNumber() {
        return number;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Course [courseName=" + courseName + ", number=" + number + ", name=" + name + "]";
    }
}
