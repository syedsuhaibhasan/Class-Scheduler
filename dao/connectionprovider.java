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
        
        // First connect to MySQL server without specifying database
        Connection tempCon = DriverManager.getConnection(
            DB_URL + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            DB_USERNAME,
            DB_PASSWORD
        );
        
        // Check if database exists, create it if it doesn't
        if(!databaseexists(tempCon, DB_NAME)){
            createDatabase(tempCon, DB_NAME);
        }
        tempCon.close();
        
        // Now connect to the specific database
        Connection con = DriverManager.getConnection(
            DB_URL + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            DB_USERNAME,
            DB_PASSWORD
        );
        
        return con;
           }catch(Exception ex){
              System.err.println("Error in getCon(): " + ex.getMessage());
              ex.printStackTrace();
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