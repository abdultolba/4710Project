import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CRUD_Driver {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public String dbName = "sampledb";
	private Connection dataBaseConnection = null;
	private String userName = "john";
	private String password = "pass1234";
	private String dbms = "mysql";
	private String serverName = "localhost";
	private String portNumber = "8080";

	/**
	 * @return A set containing what a query was given back by SQL
	 *
	 * @throws SQLException
	 * @brief Facilator Suite to execute CRUD SQL statements
	 */
	public static ResultSet executeSQLCommand ( String createStatement ) throws SQLException {
		log.info ( "[Init::executeSQLCommand] : Executing SQL Command : %s" , createStatement );
		this.establishConnection ( dbName );
		PreparedStatement preparestatement = dataBaseConnection.prepareStatement ( createStatement );
		return preparestatement.executeUpdate ( );
	}

	public static ResultSet executeSQLQuery ( String queryStatement ) throws SQLException {
		log.info ( "[Init::executeSQLQuery] : Executing SQL Query : %s" , queryStatement );
		this.establishConnection ( dbName );
		PreparedStatement preparestatement = dataBaseConnection.prepareStatement ( queryStatement );
		return preparestatement.executeQuery ( );
	}

	public static ResultSet executeSQLGeneric ( String queryStatement ) throws SQLException {
		log.info ( "[Init::executeSQLQuery] : Executing SQL Query : %s" , queryStatement );
		this.establishConnection ( dbName );
		PreparedStatement preparestatement = dataBaseConnection.prepareStatement ( queryStatement );
		return preparestatement.execute ( );
	}

	/**
	 * This method attempts to create an active MySQL Connection.
	 *
	 * @param dbName - the database name (ex: 'sampledb')
	 *
	 * @return Connection Object
	 */
	public Connection establishConnection ( String dbName ) throws SQLException {
		log.info ( "[Init::createdataBaseConnection()] : Attempting to establish connection with database..." );

		Class.forName ( JDBC_DRIVER ).getConstructor ( ).newInstance ( );
		Properties ConnectionProperties = new Properties ( );
		ConnectionProperties.put ( "user" , this.userName );
		ConnectionProperties.put ( "password" , this.password );

		dataBaseConnection = DriverManager.getConnection ( "jdbc:" + this.dbms + "://" +
				                                                   this.serverName +
				                                                   ":" + this.portNumber + "/" , ConnectionProperties );
		return dataBaseConnection;
	}

	/**
	 * This method attempts to terminate the current MySQL dataBaseConnectionection
	 *
	 * @return - Returns 1 if the dataBaseConnectionection was ended,
	 * 		0 if it could not end the dataBaseConnectionection.
	 */
	public void killConnection ( ) throws SQLException {
		log.info ( "[Init::killConnection()] : Attempting to End Connection with database..." );
		dataBaseConnection.close ( );
	}

	/**
	 * Parse CSV data into Map to be written in to respective SQL table
	 *
	 * @param tablename - a string containing the table's name (ex: 'paper')
	 *
	 * @return Map is key of the rownumber, and List with attribute values
	 */
	public Map<Integer, List<String>> paraeCSV ( String tablename ) throws IOException {
		log.info ( "[Init::paraeCSV(String, int) : Parsing Data for table %s]" , tablename );

		try {
			String row = "";
			Integer rowNumber = 0;

			File csvFileWithTableData = new File ( getClass ( ).getResource ( tablename + ".csv" ).toURI ( ) );
			BufferedReader csv = new BufferedReader ( new FileReader ( csvFileWithTableData ) );
			Map<Integer, List<String>> csvData;

			while ( ( row = csv.readLine ( ) ) != null ) {
				csvData.put ( rowNumber , row.split ( "," ) );
				rowNumber++;
			}

			return csvData;
		} catch ( IOException e ) {
			log.error ( "[Init::paraeCSV] : IOException thrown while attempting to read CSV" , e );
			throw e;
		} catch ( Exception e ) {
			log.error ( "[Init::paraeCSV] : Unknown Exception was thrown: " + e + "\n" );
			throw e;
		}
	}

	/**
	 * Insert any number of values in to any table
	 * <p>
	 * NOTE: This is really not the best way to do this in any partical
	 * application, but since we manuelly make the data it is fine
	 *
	 * @param tableName
	 *
	 * @return true , insertion was success, false otherwise
	 */
	private String makeSQLInsertCommand ( String tableName , HashMap dataMap ) {
		StringBuilder sql = new StringBuilder ( "INSERT INTO " ).append ( tableName ).append ( " (" );
		StringBuilder placeholders = new StringBuilder ( );

		for ( Iterator<String> iter = dataMap.keySet ( ).iterator ( ) ; iter.hasNext ( ) ; ) {
			sql.append ( iter.next ( ) );
			placeholders.append ( "?" );

			if ( iter.hasNext ( ) ) {
				sql.append ( "," );
				placeholders.append ( "," );
			}
		}

		return ( sql.append ( ") VALUES (" ).append ( placeholders ).append ( ");" ) ).toString ( );
	}

	private Boolean initTable ( String tableName , String createStatement ) {
		executeSQLCommand ( createStatement );
		HashMap<Integer, List<String>> table = getFileContent ( tableName );

		if ( table.isEmpty ( ) ) {
			log.error ( "[Init::initTable] : No data in map for tableName %s" , tableName );
			return false;
		}
		else {
			String sql = makeSQLInsertCommand ( tablename , table );
			executeSQLCommand ( sql );
			return true;
		}
	}
}
