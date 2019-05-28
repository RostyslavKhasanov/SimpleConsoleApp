package com.university.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Lector {

    private int id;
    private String fullName;
    private int age;
    private String degree;
    private int departament;
    private Double salary;

    public Lector() {
    }

    public String getFullName() {
        return fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public String getDegree() {
        return degree;
    }

    public Double getSalary() {
        return salary;
    }

    public int getDepartament() {
        return departament;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setDepartament(int departament) {
        this.departament = departament;
    }

}
