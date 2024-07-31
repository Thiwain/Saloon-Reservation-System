package com.ruzzz.nemo.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQL {

    private static Connection connection;

 
    private static final String username = "dbmasteruser";
    private static final String password = ",%>:lM,8.ov3c~s,AeHGO(s;uChHEitG";
    private static final String database = "saloon_nemo";
    private static final String url = "ls-30ce19b36dcfd4adb024b2d7cda912aa59933775.cr2a06owmr1t.ap-southeast-1.rds.amazonaws.com";

    static {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + url + ":3306/" + database, username, password);
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
