package myPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

public class Init {
	enum tableType {
		AUTHOR ( 12 , 3 ),
		PAPER ( 13 , 4 ),
		PCMEMBER ( 14 , 2 ),
		REVIEW ( 12 , 6 ),
		WRITTEN ( 14 , 3 );

		private int size;
		private int attr;

		tableType ( int size , int attr ) {
			this.size = size;
			this.attr = attr;
		}

		public int getSize ( ) { return size; }

		public int getAttr ( ) {
			return attr;
		}

		@Override
		public String toString ( ) { return super.toString ( ).toLowerCase ( ); }
	}

	private Connection conn;
	private static final Logger logger = Logger.getLogger ( Init.class.getName ( ) );

//	/**
//	 * This method attempts to create an active MySQL connection.
//	 *
//	 * @param dbName The database name (ex: 'sampledb')
//	 *
//	 * @return 1 if the connection was created, 0 if it could not be established.
//	 */
//	public int createConn ( String dbName ) {
//		try {
//			Class.forName ( "com.mysql.jdbc.Driver" ).getDeclaredConstructor ( ).newInstance ( );
//			conn = DriverManager.getConnection ( "jdbc:mysql://localhost:3306/" + dbName + "?allowMultiQueries=true&user=john&password=pass1234" );
//		} catch ( SQLException e ) {
//			logger.log ( SEVERE , "Connection to " + dbName + " not able to made" , e );
//		} catch ( Exception e ) {
//			logger.log ( SEVERE , "An exception was encountered while making a connection " + dbName , e );
//			return 0;
//		}
//		return 1;
//	}
//
//	/**
//	 * This method attempts to terminate the current MySQL connection
//	 *
//	 * @return 1 if the connection was ended, 0 if it could not end the connection.
//	 */
//	public int endConn ( ) {
//		try {
//			conn.close ();
//			return 1;
//		} catch ( SQLException e ) {
//			System.out.print ( "Exception Encountered: " + e + "\n" );
//			return 0;
//		}
//	}
	/**
	 * This method reads comma separated values from a .csv
	 * file and stores them in a multi-dimensional array
	 *
	 * @param tablename     - a tableType (enum) containing the table's name (ex: 'paper') and csv size
	 * @return - A multi-dimensional array containing the values which were contained in the CSV file
	 */
	public String[][] getFileContent ( tableType tablename ) {
		logger.info ( "Working on table " + tablename + " in getFileContent.\n" );

		try {
			File myFile = new File ( getClass ( ).getResource ( tablename.toString ( ) + ".csv" ).toURI ( ) );
			String[][] content = new String[ tablename.getSize ( ) ][ tablename.getAttr ( ) ];
			String row = "";
			int i = 0;
			BufferedReader csv = new BufferedReader ( new FileReader ( myFile ) );

			while ( ( row = csv.readLine ( ) ) != null && i < tablename.getSize ( ) ) {
				content[ i ] = row.split ( "," );
				logger.info ( content[ i ][ 0 ] + "\n" );
				i++;
			}
			return content;
		} catch ( Exception e ) {
			System.out.print ( "Error encountered: " + e + "\n" );
			return null;
		}
	}

	/**
	 * This function creates and populates the table 'paper' in
	 * the sampledb database by executing the appropriate SQL query
	 *
	 * @return - Returns a 1 if the table was initialized, 0 otherwise.
	 */
	public int initPaper ( ) {
		String createStatement = "CREATE TABLE IF NOT EXISTS paper(\n" +
				                         "\tpaperid INTEGER AUTO_INCREMENT, \n" +
				                         "\ttitle VARCHAR(50), \n" +
				                         "\tabstract VARCHAR(250), \n" +
				                         "\tpdf VARCHAR(100), \n" +
				                         "\tPRIMARY KEY (paperid)\n" +
				                         ");";
		//create table
		createTable ( createStatement );
		String[][] paper = getFileContent ( tableType.PAPER );

		if ( paper == null ) {
			System.out.print ( "Error: Paper array empty\n" );
			return 0;
		}
		else {
			for ( int i = 0 ; i < paper.length ; i++ ) {
				new UpdateDB ( ).insertPaper (
						paper[ i ][ 0 ] ,
						paper[ i ][ 1 ] ,
						paper[ i ][ 2 ] ,
						paper[ i ][ 3 ] );
			}
			return 1;
		}
	}

	/**
	 * This function creates and populates the table 'author' in
	 * the sampledb database by executing the appropriate SQL query
	 *
	 * @return - Returns a 1 if the table was initialized, 0 otherwise.
	 */
	public int initAuthor ( ) {
		String createStatement = "CREATE TABLE IF NOT EXISTS author(email VARCHAR(100), name VARCHAR(50), affiliation VARCHAR(100), PRIMARY KEY (email));";

		createTable ( createStatement );

		String[][] author = getFileContent ( tableType.AUTHOR );
		if ( author == null ) {
			System.out.print ( "Error: Author array empty\n" );
			return 0;
		}
		else {
			for ( int i = 0 ; i < author.length ; i++ ) {
				String sql = "INSERT INTO author VALUES("
						             + author[ i ][ 1 ] + ", "
						             + author[ i ][ 0 ] + ", "
						             + author[ i ][ 2 ] + ");";
				updateTable ( sql );
			}
			return 1;
		}
	}

	/**
	 * This function creates and populates the table 'written' in
	 * the sampledb database by executing the appropriate SQL query
	 *
	 * @return - Returns a 1 if the table was initialized, 0 otherwise.
	 */
	public int initWritten ( ) {
		String tablename = "written";
		String createStatement = "CREATE TABLE IF NOT EXISTS written(id INTEGER AUTO_INCREMENT, paperid INTEGER, email VARCHAR(100), significance INTEGER, PRIMARY KEY(id), FOREIGN KEY (paperid) REFERENCES paper(paperid), FOREIGN KEY (email) REFERENCES author(email));";
		createTable ( createStatement );
		//table written has THREE attributes
		String[][] written = getFileContent ( tableType.WRITTEN );

		if ( written == null ) {
			System.out.print ( "Error: Written array empty\n" );
			return 0;
		}
		else {
			for ( int i = 0 ; i < written.length ; i++ ) {
				String sql = "INSERT INTO written(paperid, email, significance) VALUES("
						             + written[ i ][ 0 ] + ", "
						             + written[ i ][ 1 ] + ", "
						             + written[ i ][ 2 ] + ");";

				updateTable ( sql );
			}
			return 1;
		}
	}

	/**
	 * This function creates and populates the table 'pcmember' in
	 * the sampledb database by executing the appropriate SQL query
	 *
	 * @return - Returns a 1 if the table was initialized, 0 otherwise.
	 */
	public int initpcMember ( ) {
		String createStatement = "CREATE TABLE IF NOT EXISTS pcmember(memberid INTEGER NOT NULL AUTO_INCREMENT, email VARCHAR(100), name VARCHAR(20), PRIMARY KEY (memberid));";
		createTable ( createStatement );

		String[][] pcmember = getFileContent ( tableType.PCMEMBER );
		if ( pcmember == null ) {
			System.out.print ( "Error: Pcmember array empty\n" );
			return 0;
		}
		else {
			for ( int i = 0 ; i < pcmember.length ; i++ ) {
				new UpdateDB ( ).insertPCMember ( pcmember[ i ][ 1 ] , pcmember[ i ][ 0 ] );
			}
			return 1;
		}
	}

	/**
	 * This function creates and populates the table 'review' in
	 * the sampledb database by executing the appropriate SQL query
	 *
	 * @return - Returns a 1 if the table was initialized, 0 otherwise.
	 */
	public int initReview ( ) {
		String createStatement = "CREATE TABLE IF NOT EXISTS review(reportid INTEGER AUTO_INCREMENT, sdate DATE, comm VARCHAR(250), recommendation CHAR(1), paperid INTEGER NOT NULL, memberid INTEGER NOT NULL, PRIMARY KEY(reportid), FOREIGN KEY (paperid) REFERENCES paper(paperid), FOREIGN KEY (memberid) REFERENCES pcmember(memberid));";
		System.out.print ( createStatement );
		System.out.print ( createTable ( createStatement ) );

		String[][] review = getFileContent ( tableType.REVIEW );
		if ( review == null ) {
			System.out.print ( "Error: Review array empty\n" );
			return 0;
		}
		else {
			for ( int i = 0 ; i < review.length ; i++ ) {
				try {
					SimpleDateFormat df = new SimpleDateFormat ( "dd/MM/yy" );
					java.util.Date date = df.parse ( review[ i ][ 3 ] );

					Date sqlDate = new Date ( date.getTime ( ) );

					new UpdateDB ( ).insertReview ( review[ i ][ 2 ] ,
							sqlDate.toString ( ) ,
							review[ i ][ 4 ] ,
							review[ i ][ 5 ] ,
							review[ i ][ 0 ] ,
							review[ i ][ 1 ] );
				} catch ( ParseException e ) {
					System.out.print ( "Encountered exception: " + e + "\n" );
				}
			}
			return 1;
		}
	}

	/**
	 * This function creates the sampledb database by
	 * executing the appropriate "CREATE DATABASE" SQL query.
	 *
	 * @return - Returns a 1 if the database was created, 0 otherwise.
	 */
	public int createDatabase ( ) {
		try {
			String sql = "CREATE DATABASE IF NOT EXISTS sampledb; USE sampledb;";
			PreparedStatement preparestatement = new ConnectionFactory ( ).getConnection ( ).prepareStatement ( sql );
			preparestatement.executeUpdate ( );
			return 1;
		} catch ( SQLException e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		}
	}

	/**
	 * This function deletes the sampledb database by
	 * executing the appropriate "DROP DATABASE" SQL query.
	 *
	 * @return - Returns a 1 if the database was dropped, 0 otherwise.
	 */
	public int dropDatabase ( ) {
		try {
			String sql = "DROP DATABASE IF EXISTS sampledb";
			PreparedStatement preparestatement = new ConnectionFactory ( ).getConnection ( ).prepareStatement ( sql );
			preparestatement.executeUpdate ( );
			return 1;
		} catch ( SQLException e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		}
	}

	/**
	 * This function creates and populates a table  in
	 * the sampledb database by executing the appropriate SQL query
	 *
	 * @param createStatement - the SQL query for creating a new table in the database
	 *
	 * @return - Returns a 1 if the table was creates, 0 otherwise.
	 */
	public int createTable ( String createStatement ) {
		try {
			PreparedStatement preparedStatement = new ConnectionFactory ( ).getConnection ( ).prepareStatement ( createStatement );
			preparedStatement.executeUpdate ( );
			return 1;
		} catch ( Exception e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		}
	}

	/**
	 * This method assigns pcmembers to review a paper given the pcmember's email(s).
	 *
	 * @param paperid - The paper's paperid to be reviewed.
	 * @param email   - The pcmember's email address.
	 *
	 * @return - Returns a 1 if the paper was assigned, 0 if otherwise.
	 */
	public Boolean assign ( String paperid , String[] email ) {


		if ( email.length - 1 > 3 ) {
			System.out.print ( "Exception Encountered: assigning over 3 reviewers to paper!" );
			return false;
		}

		String query1 = "SELECT paperid FROM review WHERE paperid = " + paperid + " AND paperid IN (SELECT paperid FROM review GROUP BY paperid HAVING COUNT(*)>=3);";

		try {
			PreparedStatement preparestatement = new ConnectionFactory ( ).getConnection ( ).prepareStatement ( query1 );
			ResultSet r = preparestatement.executeQuery ( );
			if ( r.next ( ) ) {
				return false;
			}

		} catch ( Exception e ) {
			System.out.print ( "Exception Encountered (0): " + e + "\n" );
			return false;
		}


		String[] memberid = new String[ email.length ];

		// Get the memberid's associated with the emails
		for ( int i = 0 ; i < email.length - 1 ; i++ ) {
			String query = "SELECT memberid FROM pcmember WHERE email = '" + email[ i ] + "' AND memberid NOT IN (SELECT memberid FROM review GROUP BY memberid HAVING COUNT(*) = 5);";
			try {
				PreparedStatement preparestatement = new ConnectionFactory ( ).getConnection ( ).prepareStatement ( query );
				ResultSet r = preparestatement.executeQuery ( );
				if ( r.next ( ) == false ) {
					memberid[ i ] = null;
				}
				else {
					memberid[ i ] = r.getString ( "memberid" );
				}

			} catch ( Exception e ) {
				System.out.print ( "Exception Encountered (1): " + e + "\n" );
				return false;
			}

		}

		for ( int i = 0 ; i < memberid.length - 1 ; i++ ) // for each email
		{
			if ( memberid[ i ] != null ) {
				String sql = "INSERT INTO review (paperid,memberid) VALUES (" + paperid + ", " + memberid[ i ] + ");";
				System.out.print ( sql + "\n" );
				updateTable ( sql );
			}
		}

		return true;
	}

	/**
	 * This method updates a row in the 'paper' table in the sampledb database.
	 *
	 * @param paperid   - The  paper's Paper ID
	 * @param title     - The paper's title
	 * @param abstract1 - The paper's abstract
	 * @param pdf       - The paper's pdf
	 *
	 * @return - Returns a 1 if the table 'paper' was updated, 0 otherwise.
	 */
	public int updatePaper ( String paperid , String title , String abstract1 , String pdf ) {

		String sql = "UPDATE paper SET title='" + title + "', abstract='" + abstract1 + "', pdf='" + pdf + "' WHERE paperid='" + paperid + "';";

		return updateTable ( sql );
	}

	/**
	 * This method deletes a row in the 'paper' table in the sampledb database.
	 *
	 * @param paperid   - The  paper's Paper ID
	 * @param title     - The paper's title
	 * @param abstract1 - The paper's abstract
	 * @param pdf       - The paper's pdf
	 *
	 * @return - Returns a 1 if the table 'paper' was deleted, 0 otherwise.
	 */
	public int deletePaper ( String paperid , String title , String abstract1 , String pdf ) {
		String sql2 = "DELETE FROM written WHERE paperid='" + paperid + "';";
		String sql3 = "DELETE FROM review WHERE paperid='" + paperid + "';";
		String sql = "DELETE FROM paper WHERE paperid='" + paperid + "';";
		try {
			PreparedStatement preparestatement3 = conn.prepareStatement ( sql3 );
			preparestatement3.executeUpdate ( );

			PreparedStatement preparestatement2 = conn.prepareStatement ( sql2 );
			preparestatement2.executeUpdate ( );

			PreparedStatement preparestatement = conn.prepareStatement ( sql );
			preparestatement.executeUpdate ( );
			return 1;
		} catch ( SQLException e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		} catch ( Exception e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		}
	}

	/**
	 * This method adds a row in the 'paper' table in the sampledb database.
	 *
	 * @param title     - The paper's title
	 * @param abstract1 - The paper's abstract
	 * @param pdf       - The paper's pdf
	 *
	 * @return - Returns a 1 if the paper was added, 0 otherwise.
	 */
	public int addPaper ( String title , String abstract1 , String pdf ) {
		String sql = "INSERT INTO paper (title, abstract, pdf) VALUES('" + title + "','" + abstract1 + "','" + pdf + "')";

		return updateTable ( sql );
	}

	/**
	 * This method updates a row in the 'pcmember' table in the sampledb database.
	 *
	 * @param memberid - The  pcmember's member ID
	 * @param email    - The pcmember's email address
	 * @param name     - The pcmember's name
	 *
	 * @return - Returns a 1 if the table 'pcmember' was updated, 0 otherwise.
	 */
	public int updatePCMember ( String memberid , String email , String name ) {
		String sql = "UPDATE pcmember SET email='" + email + "', name='" + name + "' WHERE memberid='" + memberid + "';";

		return updateTable ( sql );
	}

	private int updateTable ( String preparedStatement ) {
		try {
			PreparedStatement preparedstatement = conn.prepareStatement ( preparedStatement );
			preparedstatement.executeUpdate ( );
			return 1;
		} catch ( SQLException e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		} catch ( Exception e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		}
	}

	/**
	 * This method deletes a row in the 'pcmember' table in the sampledb database.
	 *
	 * @param memberid - The  pcmember's member ID
	 * @param email    - The pcmember's email address
	 * @param name     - The pcmember's name
	 *
	 * @return - Returns a 1 if the a pcmember was updated, 0 otherwise.
	 */
	public int deletePCMember ( String memberid , String email , String name ) {
		String sql0 = "DELETE FROM author WHERE email='" + email + "';";
		String sql2 = "DELETE FROM written WHERE email='" + email + "';";
		String sql3 = "DELETE FROM review WHERE  memberid= (SELECT memberid FROM pcmember WHERE email = '" + email + "');";
		String sql = "DELETE FROM pcmember WHERE email='" + email + "';";
		try {
			PreparedStatement preparestatement2 = conn.prepareStatement ( sql2 );

			preparestatement2.executeUpdate ( );

			PreparedStatement preparestatement0 = conn.prepareStatement ( sql0 );

			preparestatement0.executeUpdate ( );

			PreparedStatement preparestatement3 = conn.prepareStatement ( sql3 );

			preparestatement3.executeUpdate ( );

			PreparedStatement preparestatement = conn.prepareStatement ( sql );

			preparestatement.executeUpdate ( );

			return 1;
		} catch ( SQLException e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		} catch ( Exception e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		}

	}

	/**
	 * This method adds a row in the 'pcmember' table in the sampledb database.
	 *
	 * @param email - The pcmember's email address
	 * @param name  - The pcmember's name
	 *
	 * @return - Returns a 1 if the a pcmember was added, 0 otherwise.
	 */
	public int addPCMember ( String email , String name ) {
		String sql = "INSERT INTO pcmember (email,name) VALUES('" + email + "','" + name + "')";

		return updateTable ( sql );
	}

	/**
	 * This function will update a review report in the table 'review'
	 *
	 * @param reportid       - The review report's report id.
	 * @param sdate          - The review report's date.
	 * @param comm           - The review report's comment.
	 * @param recommendation - The review report's recommendation.
	 * @param paperid        - The review report's paperid.
	 * @param memberid       - The review report's memberid.
	 *
	 * @return - Returns a 1 if 'review' was successfully updated, 0 otherwise.
	 */
	public int updateReview ( String reportid , String sdate , String comm , String recommendation , String paperid , String memberid ) {
		String sql = "UPDATE review SET sdate='" + sdate + "', comm='" + comm + "', recommendation='" + recommendation + "',  paperid='" + paperid + "', memberid='" + memberid + "' WHERE reportid='" + reportid + "';";

		return updateTable ( sql );

	}

	/**
	 * This method deletes a new row to the review table
	 *
	 * @param reportid       - The review report's id.
	 * @param sdate          - The review report's date
	 * @param comm           - The review reports comment
	 * @param recommendation - The review reports recommendation
	 * @param paperid        - The review report's paperid
	 * @param memberid       - The review report's memberid
	 *
	 * @return - Returns a 1 if a review was successfully deleted, 0 otherwise.
	 */
	public int deleteReview ( String reportid , String sdate , String comm , String recommendation , String paperid , String memberid ) {
		String sql = "DELETE FROM review WHERE reportid='" + reportid + "';";

		return updateTable ( sql );

	}

	/**
	 * This method adds a new row to the review table
	 *
	 * @param sdate          - The review report's date
	 * @param comm           - The review reports comment
	 * @param recommendation - The review reports recommendation
	 * @param paperid        - The review report's paperid
	 * @param memberid       - The review report's memberid
	 *
	 * @return - Returns a 1 if a review was successfully added, 0 otherwise.
	 */
	public int addReview ( String sdate , String comm , String recommendation , String paperid , String memberid ) {
		String sql = "INSERT INTO review (sdate,comm,recommendation,paperid,memberid) VALUES('" + sdate + "','" + comm + "', '" + recommendation + "', '" + paperid + "', '" + memberid + "')";

		return updateTable ( sql );
	}

	/**
	 * This method creates a trigger which restricts
	 * pcmembers to review no more than 5 assignments.
	 *
	 * @return - Returns a 1 if the trigger was created successfully, 0 otherwise.
	 */
	public int createTrigger ( ) {
		try {
			String trigger = "CREATE TRIGGER max5papers BEFORE INSERT ON review FOR EACH ROW BEGIN IF new.email IN (SELECT * FROM pcmember P WHERE 5 = (SELECT COUNT(*) FROM review R WHERE R.email = P.email)) THEN SIGNAL SQLSTATE'45000'; END;";
			System.out.print ( trigger + "\n" );
			PreparedStatement preparestatement = conn.prepareStatement ( trigger );
			preparestatement.execute ( );
			return 1;
		} catch ( SQLException e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		}
	}

	/**
	 * This method creates a view for the accepted papers
	 *
	 * @return - Returns a 1 if the view was created successfully, 0 otherwise.
	 */
	public int createView ( ) {
		try {
			String view = "CREATE VIEW acceptedPaper AS SELECT paperid, title FROM paper WHERE paperid IN (SELECT paperid FROM review r1 WHERE r1.recommendation='a' GROUP BY paperid HAVING COUNT(*)>=2);";
			System.out.print ( view + "\n" );
			PreparedStatement preparestatement = conn.prepareStatement ( view );
			preparestatement.execute ( );
			return 1;
		} catch ( SQLException e ) {
			System.out.print ( "Exception Encountered: " + e + "\n" );
			return 0;
		}
	}

	/**
	 * This method initialized the 'sampledb' database,
	 * creates a connection, creates a database, and populates all tables.
	 *
	 * @return
	 */
	public Boolean initDatabase ( ) {
		// Establish a new connection
//		if ( createConn ( "" ) == 0 ) {
//			System.out.print ( "Connection could not be established.\n" );
//			return false;
//		}
//		System.out.print ( "Connection successfully established.\n" );
		// Connect to database
		conn = new ConnectionFactory ( ).getConnection ( );

		// Try dropping the database, if it exists
		if ( dropDatabase ( ) == 1 )
			System.out.print ( "Database Dropped.\n" );
		else {
			System.out.print ( "Database could not be initially dropped.\n" );
			return false;
		}

		// Create the database 'sampledb'
		if ( createDatabase ( ) == 0 ) {
			System.out.print ( "Database could not be created.\n" );
			return false;
		}
		System.out.print ( "Database successfully created.\n" );

		// Initialize each table individually
		if ( initPaper ( ) == 0 || initAuthor ( ) == 0 ||
				     initWritten ( ) == 0 || initpcMember ( ) == 0 || initReview ( ) == 0 ) {
			System.out.print ( "One or more tables could not be created. Dropping Database.\n" );
			if ( dropDatabase ( ) == 1 ) {
				System.out.print ( "Database Dropped.\n" );
				//add error checking
				createDatabase ( );
				return false;
			}
			System.out.print ( "Database could not be dropped.\n" );
			return false;
		}
		System.out.print ( "Tables succesfully created.\n" );

		if ( createTrigger ( ) == 0 )
			System.out.print ( "Trigger could not be created.\n" );
		else
			System.out.print ( "Trigger successfully created.\n" );

		if ( createView ( ) == 0 )
			System.out.print ( "View could not be created.\n" );
		else
			System.out.print ( "View successfully created.\n" );

		return ( true );
	}

	/**
	 * This function tests the connection to the Init
	 * class by returning a statement to the calling method
	 *
	 * @return - A test string
	 */
	public String test ( ) {
		return "I'm working";
	}
}