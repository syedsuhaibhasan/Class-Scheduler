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
        st = con.createStatement();
        
        if(!tableExists(st,"studentdetails")){
          
        
        }
        if(!tableExists(st,"facultydetails")){
        
        }
        JOptionPane.showMessageDialog(null,"Tables Check/Created Successfully");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            try{
            if(con!=null){
            con.close();
            }
            if(st!=null){
            st.close();
            }
            }catch(Exception ex ){
            }
        }
    }
      
    public static boolean tableExists(Statement st, String tableName) throws Exception{
    ResultSet resultSet = st.executeQuery("SHOW TABLES LIKE '" +tableName+ "'");
    return resultSet.next();
    }
}

