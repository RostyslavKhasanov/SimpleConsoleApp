package com.university.service.impl;

import com.university.bl.Util;
import com.university.entity.Departament;
import com.university.service.DepartamentService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentServiceImpl extends Util implements DepartamentService {

    Connection connection;

    @Override
    public void add(Departament departament) throws SQLException{
        connection = getConnection();
        PreparedStatement preparedStatement = null;

        String sql = "insert into Departaments(name) values(?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, departament.getName());
            preparedStatement.executeUpdate();
            System.out.println("Departament created");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    @Override
    public List<Departament> getAll() throws SQLException {
        connection = getConnection();
        List<Departament> departaments = new ArrayList<>();
        String sql = "select id, name from Departaments";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
            }
            while (resultSet.next()) {
                Departament departament = new Departament();
                departament.setId(resultSet.getInt("id"));
                departament.setName(resultSet.getString("name"));
                departaments.add(departament);
            }
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return departaments;
    }


}
