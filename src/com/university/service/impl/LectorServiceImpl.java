package com.university.service.impl;

import com.university.bl.Util;
import com.university.entity.Lector;
import com.university.service.LectorService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LectorServiceImpl extends Util implements LectorService {

    Connection connection;

    @Override
    public void add(Lector lector) throws SQLException {

        connection = getConnection();
        PreparedStatement preparedStatement = null;

        String sql = "insert into Lectors(fullName, age, degree, departament, salary) values(?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, lector.getFullName());
            preparedStatement.setInt(2, lector.getAge());
            preparedStatement.setString(3, lector.getDegree());
            preparedStatement.setInt(4, lector.getDepartament());
            preparedStatement.setDouble(5, lector.getSalary());
            preparedStatement.executeUpdate();
            System.out.println("Lector created");
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
    public List<Lector> getAll() throws SQLException {
        connection = getConnection();
        List<Lector> lectors = new ArrayList<>();

        String sql = "select id, fullName, age, degree, departament, salary from Lectors";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Lector lector = new Lector();
                lector.setId(resultSet.getInt("id"));
                lector.setFullName(resultSet.getString("fullName"));
                lector.setAge(resultSet.getInt("age"));
                lector.setDegree(resultSet.getString("degree"));
                lector.setDepartament(resultSet.getInt("departament"));
                lector.setSalary(resultSet.getDouble("salary"));
                lectors.add(lector);
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
        return lectors;
    }

    @Override
    public Lector getHeadOfDepartament(String name) throws SQLException {
        connection = getConnection();
        PreparedStatement preparedStatement = null;

        String sql = "select l.id, l.fullName, l.age, l.departament, l.degree, l.salary from Lectors l join Departaments d on " +
                "d.id = l.departament where l.degree = 'Head' and d.name = ?";

        Lector lector = new Lector();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) throw new SQLException();
            lector.setId(resultSet.getInt("id"));
            lector.setAge(resultSet.getInt("age"));
            lector.setDepartament(resultSet.getInt("departament"));
            lector.setFullName(resultSet.getString("fullName"));
            lector.setDegree(resultSet.getString("degree"));
            lector.setSalary(resultSet.getDouble("salary"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
            return lector;
        }
    }

    @Override
    public double getAverageSalary(String name) throws SQLException {
        connection = getConnection();
        List<Lector> lectors = new ArrayList<>();
        double sum = 0;
        int count = 0;

        String sql = "select sum(l.salary) as sum_salary, count(l.id) as count_lectors from Lectors l join " +
                "Departaments d on d.id = l.departament where d.name = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            sum = resultSet.getDouble("sum_salary");
            count = resultSet.getInt("count_lectors");
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
            sum = sum/count;
        }
        return sum;
    }

    @Override
    public String getDepartamentStatistic(String name) throws SQLException {
        connection = getConnection();
        int countAssociateProfessors = 0;
        int countAssistants = 0;
        int countProfessors = 0;

        String sql1 = "select * from Lectors l join Departaments d on d.id = l.departament " +
                "where d.name = ?" ;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("degree").equals("Assistant")) {
                    countAssistants++;
                } else {
                    if (resultSet.getString("degree").equals("Professor")) {
                        countProfessors++;
                    } else {
                        if (resultSet.getString("degree").equals("Associate professor")) {
                            countAssociateProfessors++;
                        }
                    }
                }
            }
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return "Professors : " + countProfessors +
                "; Assistants : " + countAssistants +
                "; Associate professors : " + countAssociateProfessors ;
    }

    @Override
    public int getCount(String name) throws SQLException {
        connection = getConnection();
        int count = 0;

        String sql = "select count(l.id) as count_name from Lectors l join Departaments d on d.id = l.departament " +
                "where d.name = ? group by l.departament";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            count = resultSet.getInt("count_name");
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return count;
    }

    @Override
    public List<Lector> getByTemplate(String str) throws SQLException {
        connection = getConnection();
        List<Lector> lectors = new ArrayList<>();

        String sql = "select * from Lectors where fullName like ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + str + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Lector lector = new Lector();
                lector.setId(resultSet.getInt("id"));
                lector.setFullName(resultSet.getString("fullName"));
                lector.setAge(resultSet.getInt("age"));
                lector.setDegree(resultSet.getString("degree"));
                lector.setDepartament(resultSet.getInt("departament"));
                lector.setSalary(resultSet.getDouble("salary"));
                lectors.add(lector);
            }
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return lectors;
    }
}
