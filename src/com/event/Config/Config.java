package com.event.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Config {

    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mydb";

    public static Connection createConnection() {

        Connection connection = null;


        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, "root", "1234");

        } catch (SQLException ex) {
            System.out.println("The following error has occured: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return connection;
    }
}


