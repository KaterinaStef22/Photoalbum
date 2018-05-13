package ergasia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcCon {
	Connection connection = null;
	 Statement statement;
	 ResultSet resultSet;
	
	
	public JdbcCon() throws ClassNotFoundException{
		 Class.forName("org.sqlite.JDBC");
		  
		  try {
			 connection = DriverManager.getConnection("jdbc:sqlite:demo.db");
			 statement = connection.createStatement();
		     statement.setQueryTimeout(30); 
		  } catch (SQLException e) {
			 System.err.println(e.getMessage());
		  }
	}
	
	
	public boolean CheckUser(String user, String pass) {
		System.out.println("Check user...");
          try {
        	resultSet = statement.executeQuery("SELECT * from users WHERE username='"+user+"' AND password='"+pass+"'");
        	int lenght=0;
			while(resultSet.next()){
				lenght= lenght +1;
			  }
			if (lenght==1) {
				return true;
			}
		} catch (SQLException e) {
			System.err.println(e); 
			return false;
		}
          return false;
	}
	
	public boolean SavePhoto(String name, String encodedImage) {
          try {
        	resultSet = statement.executeQuery("INSERT INTO photos(name,byte) VALUES ('"+name+"','"+encodedImage+"')");
        	return true;
		} catch (SQLException e) {
			System.err.println(e); 
			return false;
		}
	}
	
	
	public void CloseConnection() {
		try {
		      if(connection != null)
		         connection.close();
		      }
		catch(SQLException e) {       
		   System.err.println(e); 
		 }
      
	}
	

}