package com.dawnyu.maple.test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtils {

    public static Connection createConnection(String url,String user,String password){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.
                    getConnection(url,
                            user,password);
            return connection;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
