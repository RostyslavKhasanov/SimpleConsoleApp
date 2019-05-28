package com.university.service;

import com.university.entity.Departament;

import java.sql.SQLException;
import java.util.List;

public interface DepartamentService {

    void add(Departament departament) throws SQLException;

    List<Departament> getAll() throws SQLException;
}
