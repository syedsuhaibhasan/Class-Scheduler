/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Syeds
 */

public class connectionprovider {
    private static final String DB_NAME = "ClassschedulerJFramebd";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456";
    
    public static Connection getCon(){
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String params = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        Connection con = DriverManager.getConnection(
        DB_URL + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
        DB_USERNAME,
        DB_PASSWORD
        );

        if(!databaseexists(con, DB_NAME)){
        createDatabase(con, DB_NAME);}
        con = DriverManager.getConnection(DB_URL+DB_NAME+"?useSSL=false",DB_USERNAME,DB_PASSWORD);
        return con;
           }catch(Exception ex){
              System.err.println("Error in getCon(): " + ex.getMessage());
        return null;
    }
}
    
    private static boolean databaseexists(Connection con, String dbName) throws Exception {
         Statement stmt = con.createStatement();
        return stmt.executeQuery("SHOW DATABASES LIKE '"+ dbName + "'").next();
         
    }
    
    private static void createDatabase(Connection con, String dbName) throws Exception {
    Statement stmt = con.createStatement();
    stmt.executeUpdate("CREATE DATABASE " + dbName);
    System.out.println("Database '" + dbName + "' created successfully");
}
       
}
