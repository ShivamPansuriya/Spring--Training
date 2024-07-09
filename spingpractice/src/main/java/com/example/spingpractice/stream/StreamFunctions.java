package com.example.spingpractice.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamFunctions {

//    Given a list of integers, use streams to find the sum of all even numbers.
    void question1()
    {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        var sum = list.stream().filter(num -> num%2==0).reduce(0, Integer::sum);
//                                          OR
//      var sum = list.stream().filter(num -> num%2==0).reduce(0,(midResult,num) -> midResult + num);

        System.out.println(sum);
    }

//    Create a stream of strings and convert all strings to uppercase.
    void question2()
    {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("Java");
        list.add("Python");
        list.add("C++");

        var modifiedList = list.stream().map(str-> str.toUpperCase()).collect(Collectors.toList());
        System.out.println(modifiedList);
    }

//    Filter a list of names to only include those starting with the letter 'A'.
    void question3()
    {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("Ashok");
        list.add("Ashish");
        list.add("C++");

        var modifiedList = list.stream().filter(str-> str.startsWith("A")).collect(Collectors.toList());
        System.out.println(modifiedList);
    }

//    Given a list of integers, use map() to create a new list with each number squared.
    void question4()
    {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        var squareList = list.stream().map(num-> num*num).collect(Collectors.toList());
        System.out.println(squareList);
    }

//    Use flatMap() to flatten a list of lists into a single list.
    void question5()
    {
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list1.add(i);
        }
        List<Integer> list2 = new ArrayList<>();
        for (int i = 10; i < 20; i++) {
            list2.add(i);
        }
        var finalList = new ArrayList<List<Integer>>();
        finalList.add(list1);
        finalList.add(list2);

        var modifiedList = finalList.stream().flatMap(list -> list.stream()).collect(Collectors.toList());
        System.out.println(modifiedList);
    }

//    Sort a list of strings based on their length using the sorted() method.
    void question6()
    {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("Java");
        list.add("Python");
        list.add("C++");

        var modifiedList = list.stream().sorted((str1,str2)-> str1.length()-str2.length()).collect(Collectors.toList());
        System.out.println(modifiedList);
    }

//    Use reduce() to find the product of all numbers in a list.
    void question7()
    {
        List<Long> list1 = new ArrayList<>();
        for (long i = 0; i < 10; i++) {
            list1.add(i);
        }

        var product = list1.stream().reduce(1L,(midResult,num) -> midResult * num);
        System.out.println(product);
    }

//    Implement collect() to group a list of words by their first letter.
    void question8()
    {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("Ashok");
        list.add("Ashish");
        list.add("C++");

        var map = list.stream().collect(Collectors.groupingBy(str -> str.charAt(0)));
        System.out.println(map);
    }

//    Convert a stream of strings to a stream of integers representing their lengths.
//    Use Collectors.joining() to concatenate a list of strings with a delimiter.
    void question9()
    {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("Ashok");
        list.add("Ashish");
        list.add("C++");

        var lengthList = list.stream().map(str -> str.length()).collect(Collectors.toList());
        var lengthMap = list.stream().collect(Collectors.groupingBy(str -> str.length()));
        System.out.println(lengthList);
        System.out.println(lengthMap);

        var joinList = list.stream().collect(Collectors.joining(", "));
        System.out.println(joinList);

    }

//Create a class Person with attributes name and age. Use streams to:
//
//Filter persons above a certain age.
//Find the average age of all persons.
//Group persons by age.
//find the top 3 young users and return their names.
//Use findFirst() or findAny() with filter() to find a specific element in a stream.
    void question10()
    {
        List<user> list = new ArrayList<>();
        list.add(new user("manoj",21));
        list.add(new user("suresh",33));
        list.add(new user("ketan",21));
        list.add(new user("shivam",22));
        list.add(new user("raju",25));

        var belowLimit = list.stream().filter(user -> user.getAge()<25).collect(Collectors.toList());
        System.out.println("users below age limit" + belowLimit);

        var ageSum = list.stream().reduce(0,(midResult, user) -> midResult + user.getAge(), Integer::sum);
        System.out.println("average age of users: " + ageSum/list.size());

        var ageGroup = list.stream().collect(Collectors.groupingBy(user -> user.getAge()));
        System.out.println("grouping by user age: " + ageGroup);

        var top3YoungUsers = list.stream().sorted((user1,user2)-> user1.getAge() - user2.getAge()).limit(3).toList();
        System.out.println("top 3 young users: " + top3YoungUsers);

        var findFirst = list.stream().findFirst();
        System.out.println("first user: " + findFirst.get());

        var findAny = list.stream().findAny();
        System.out.println("any user: " + findAny.get());
    }

}
