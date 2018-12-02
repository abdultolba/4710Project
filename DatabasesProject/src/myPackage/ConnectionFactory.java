package myPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class ConnectionFactory {
	private Connection conn;
	private static final Logger logger = Logger.getLogger ( ConnectionFactory.class.getName ( ) );

	public static final String URL = "jdbc:mysql://localhost:3306/testdb";
	private static final String USER = "testuser";
	private static final String PASS = "testpass";
	private static final String DBNAME = "sampledb";

	public Connection getConnection ( ) {
		try {
			Class.forName ( "com.mysql.jdbc.Driver" ).getDeclaredConstructor ( ).newInstance ( );
			return DriverManager.getConnection ( URL , USER , PASS );
		} catch ( SQLException exception ) {
			logger.log ( SEVERE , "Connection to " + DBNAME + " not able to made." , exception );
		} catch ( ClassNotFoundException exception ) {
			logger.log ( SEVERE , "Unable to load JDBC/ODBC driver." , exception );
		} catch ( Exception exception ) {
			logger.log ( SEVERE , "Unknown exception encountered." , exception );
		}

		return null;
	}

	public static int killConnection ( Connection conn ) {
		try {
			conn.close ( );
			return 1;
		} catch ( SQLException e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		}
	}
}
