package com.university.bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static final String DB_URL = "jdbc:h2:D://testConsoleProject/db/Departament";
    public static final String DB_Driver = "org.h2.Driver";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_Driver);
            connection = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }
        return connection;
    }
}
