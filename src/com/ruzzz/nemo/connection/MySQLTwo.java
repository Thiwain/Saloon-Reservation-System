/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ruzzz.nemo.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Acer
 */
public class MySQLTwo {

    private static Connection connection;

    private static final String username = "root";
    private static final String password = "2005@Thiwain";
    private static final String database = "saloon_nemo";

    static {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ResultSet execute(String query) throws Exception {
        Statement statement = connection.createStatement();
        if (query.startsWith("SELECT")) {
            return statement.executeQuery(query);
        } else {
            statement.executeUpdate(query);
            return null;
        }
    }

}
