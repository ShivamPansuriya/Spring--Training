package com.example.spingpractice;

public class Course {
    private String courseName;
    private  int number;
    private String name;

    public Course( int number, String courseName, String name) {
        this.courseName = courseName;
        this.number = number;
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

    @Override
    public String toString() {
        return "Course [courseName=" + courseName + ", number=" + number + ", name=" + name + "]";
    }
}
