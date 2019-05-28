package com.university.entity;

import java.util.Objects;

public class Departament {

    private int id;
    private String name;

    public Departament() {
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
