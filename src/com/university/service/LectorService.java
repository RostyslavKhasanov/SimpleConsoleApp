package com.university.service;

import com.university.entity.Lector;

import java.sql.SQLException;
import java.util.List;

public interface LectorService {

    void add(Lector lector) throws SQLException;

    List<Lector> getAll() throws SQLException;

    Lector getHeadOfDepartament(String name) throws SQLException;

    double getAverageSalary(String name) throws SQLException;

    int getCount(String name) throws SQLException;

    String getDepartamentStatistic(String name) throws SQLException;

    List<Lector> getByTemplate(String str) throws SQLException;
}
