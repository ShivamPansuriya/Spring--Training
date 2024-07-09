package com.example.spingpractice.stream;

public class user {
    String name;
    int age;

    user(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "user [name=" + name + ", age=" + age + "]";
    }
}
