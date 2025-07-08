package dao;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.JOptionPane;
/**
 *
 * @author Syeds
 */
public class Tables {
    
    public static void main(String [] args){
        Connection con = null;
        Statement st = null;
        try{
            con = connectionprovider.getCon();
            
            if(con == null){
                JOptionPane.showMessageDialog(null, "Failed to connect to database. Please check your MySQL server and credentials.");
                return;
            }
            
            st = con.createStatement();
            
            // Create studentdetails table if it doesn't exist
            if(!tableExists(st,"studentdetails")){
                String createStudentTable = "CREATE TABLE studentdetails (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL, " +
                    "password VARCHAR(100) NOT NULL, " +
                    "course VARCHAR(50), " +
                    "semester VARCHAR(10), " +
                    "phone VARCHAR(15), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
                st.executeUpdate(createStudentTable);
                System.out.println("Student details table created successfully.");
            }
            
            // Create facultydetails table if it doesn't exist
            if(!tableExists(st,"facultydetails")){
                String createFacultyTable = "CREATE TABLE facultydetails (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL, " +
                    "password VARCHAR(100) NOT NULL, " +
                    "department VARCHAR(50), " +
                    "phone VARCHAR(15), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
                st.executeUpdate(createFacultyTable);
                System.out.println("Faculty details table created successfully.");
            }
            
            JOptionPane.showMessageDialog(null,"Tables Check/Created Successfully");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }finally{
            try{
                if(st!=null){
                    st.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch(Exception ex){
                System.err.println("Error closing resources: " + ex.getMessage());
            }
        }
    }
      
    public static boolean tableExists(Statement st, String tableName) throws Exception{
        ResultSet resultSet = st.executeQuery("SHOW TABLES LIKE '" +tableName+ "'");
        return resultSet.next();
    }
}
