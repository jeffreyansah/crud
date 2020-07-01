package cruddao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataSourceFactory {

	private  final Logger logger = Logger.getLogger(DataSourceFactory.class.getName());

	private DataSourceFactory() {
		
	}

	public static DataSourceFactory getInstance() {
		return SingletonHelper.INSTANCE;
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
	        logger.log(Level.SEVERE, "Error");
	    }
	   
	 return  conn;
	}
	private static class SingletonHelper {
		private  static final DataSourceFactory INSTANCE = new DataSourceFactory();
	}
}
