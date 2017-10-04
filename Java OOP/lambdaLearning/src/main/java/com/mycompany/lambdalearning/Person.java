/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lambdalearning;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class Person {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    Predicate<Person> oldEnoughToVote1 = (Person p) -> {
        return p.getAge() >= 18;
    };

    Predicate<Person> oldEnoughToVote = p -> p.getAge() >= 18;
    Consumer<Person> howOld = p -> System.out.println(p.getName() + " is " + p.getAge() + " years old.");

    ToIntFunction<Person> toAge1 = p -> p.getAge();
    ToIntFunction<Person> toAge = Person::getAge;
}
