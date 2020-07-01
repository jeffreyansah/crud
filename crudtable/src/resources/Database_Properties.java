package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database_Properties {

	private Database_Properties() {
	}
	private Connection conn=null;

	public Connection getOracleDBConnection() {
		try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");

	        conn = DriverManager.getConnection(
	        		"jdbc:oracle:thin:@localhost:1521:xe"
	        		,"JEFF"
	        		,"1234"
	        		);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        System.out.println("ERRORE");
	    }
	   
	 return  conn;
	}
	
	public static class Database_PropertiesSingleton{
		private static final Database_Properties INSTANCE= new Database_Properties();
	}
	public static Database_Properties getInstance() {
		return Database_PropertiesSingleton.INSTANCE;
	}
}
