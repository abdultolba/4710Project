package myPackage;

import myPackage.CRUD_Driver;

public enum Table {
	private CRUD_Driver core;
	private tableName;
	private createCommand;

	Table ( String tableName , String create ) {
		this.tableName = tableName;
		this.createCommand = create;
	}

	public String getTableName ( ) {
		return this.tableName;
	}

	public String getCreateCommand ( ) {
		return this.createCommand;
	}

	/**
	 * @return - Returns a 1 if the table was initialized, 0 otherwise.
	 *
	 * @throws SQL Exception
	 * @brief Create and Load Data in Table <table-Name>
	 */
	public Boolean init_Table_OfType ( ) {
		log.info ( "[Table::init_Table_OfType][Progress] : Attempting to Create SQL Table %s and Insert Values" , this.tableName );
		return core.initTable ( getTableName ( ) , getCreateCommand ( ) );
	}
}

public class PCReviewLimitException extends Exception {
	public PCReviewLimitException ( ) {
		super ( "A Maximum of 3 Paper Can Be Assigned to Any Given PC Review Member" );
	}
}

public class Init {
	static final String SQL_5PCMAX_TRIGGER = "CREATE TRIGGER max5papers " +
			                                         "BEFORE INSERT ON review " +
			                                         "FOR EACH ROW BEGIN " +
			                                         "IF new.email IN (" +
			                                         "SELECT * FROM pcmember P WHERE 5 = (" +
			                                         "SELECT COUNT(*) " +
			                                         "FROM review R " +
			                                         "WHERE R.email = P.email) ) " +
			                                         "THEN SIGNAL SQLSTATE'45000'; " +
			                                         "END;";

	static final String SQL_CREATEVIEW_ACCEPTEDPAPERS = "CREATE VIEW acceptedPaper AS " +
			                                                    "SELECT paperid, title " +
			                                                    "FROM paper " +
			                                                    "WHERE paperid IN (" +
			                                                    "SELECT paperid " +
			                                                    "FROM review r1 " +
			                                                    "WHERE r1.recommendation='a' " +
			                                                    "GROUP BY paperid " +
			                                                    "HAVING COUNT(*)>=2" +
			                                                    ");";
	private static CRUD_Driver core;

	private final Table paper ( "paper","CREATE TABLE IF NOT EXISTS paper( "+
			                            "paperid INTEGER AUTO_INCREMENT, "+
			                            "title VARCHAR(50), "+
			                            "abstract VARCHAR(250), "+
			                            "pdf VARCHAR(100), "+
			                            "PRIMARY KEY (paperid) );"
	);

	private final Table author ( "author","CREATE TABLE IF NOT EXISTS author("+
			                             "email VARCHAR(100),"+
			                             "name VARCHAR(50), "+
			                             "affiliation VARCHAR(100), "+
			                             "PRIMARY KEY (email));"
	);

	private final Table written ( "written","CREATE TABLE IF NOT EXISTS written("+
			                              "id INTEGER AUTO_INCREMENT, "+
			                              "paperid INTEGER, email VARCHAR(100), "+
			                              "INTEGER, PRIMARY KEY(id), "+
			                              "FOREIGN KEY (paperid) REFERENCES paper(paperid), "+
			                              "FOREIGN KEY (email) REFERENCES author(email));",
	);

	private final Table pcMember ( "pcMember","CREATE TABLE IF NOT EXISTS written("+
			                               "id INTEGER AUTO_INCREMENT, "+
			                               "paperid INTEGER, "+
			                               "email VARCHAR(100), "+
			                               "significance INTEGER, "+
			                               "PRIMARY KEY(id), "+
			                               "FOREIGN KEY (paperid) REFERENCES paper(paperid), "+
			                               "FOREIGN KEY (email) REFERENCES author(email));",
	);

	private final Table review ( "review","CREATE TABLE IF NOT EXISTS review("+
			                             "reportid INTEGER AUTO_INCREMENT, "+
			                             "sdate DATE, "+
			                             "comm VARCHAR(250), "+
			                             "recommendation CHAR(1), "+
			                             "paperid INTEGER NOT NULL, "+
			                             "memberid INTEGER NOT NULL, "+
			                             "PRIMARY KEY(reportid), "+
			                             "FOREIGN KEY (paperid) REFERENCES paper(paperid), "+
			                             "FOREIGN KEY (memberid) REFERENCES pcmember(memberid));"
	);

	// ------------------------------------------------------------------------

	public int createDatabase ( ) throws SQLException {
		log.info ( "[Init::dropDatabase][Progress] : Creating Database Schema..." );
		core.executeSQLCommand ( "CREATE DATABASE IF NOT EXISTS sampledb; USE sampledb;" );
	}

	public int dropDatabase ( ) throws SQLException {
		log.info ( "[Init::dropDatabase][Progress] : Deleting Database Schema..." );
		core.executeSQLCommand ( "DROP DATABASE IF EXISTS sampledb" );
	}

	// ------------------------------------------------------------------------

	/**
	 * @see Table::init_Table_OfType
	 */

	public Boolean init_Paper ( ) throws SQLException { return paper ( ).init_Table_OfType ( ); }

	public Boolean init_Author ( ) throws SQLException { return author ( ).init_Table_OfType ( ); }

	public Boolean init_Written ( ) throws SQLException { return written ( ).init_Table_OfType ( ); }

	public Boolean init_PCMember ( ) throws SQLException { return pcMember ( ).init_Table_OfType ( ); }

	public Boolean init_Review ( ) throws SQLException { return review ( ).init_Table_OfType ( ); }

	// ------------------------------------------------------------------------

	/**
	 * @param paperid   The  paper's Paper ID
	 * @param title     The paper's Title
	 * @param abstract1 The paper's Abstract
	 * @param pdf       The paper's PDF
	 *
	 * @throws PCReviewLimitException (Max 5 Paper per PC Review), SQLException [int assign(String, String[])]
	 * @brief Paper Table CRUD Methods
	 */

	public int assignPaper ( String paperid , String[] email ) throws SQLException, PCReviewLimitException {
		if ( email.length - 1 > 3 ) throw PCReviewLimitException;

		ResultSet r = core.executeSQLQuery ( "SELECT paperid FROM review WHERE paperid = " + paperid + " AND paperid IN (SELECT paperid FROM review GROUP BY paperid HAVING COUNT(*)>=3);" );

		if ( ! r.next ( ) ) {
			for ( int i = 0 ; i < email.length - 1 ; i++ ) {
				r = core.executeSQLQuery ( "SELECT memberid FROM pcmember WHERE email = '" + email[ i ] + "' AND memberid NOT IN (SELECT memberid FROM review GROUP BY memberid HAVING COUNT(*) = 5);" );

				if ( r.next ( ) ) {
					core.executeSQLCommand ( "INSERT INTO review (paperid,memberid) VALUES (" + paperid + ", " + r.getString ( "memberid" ) + ");" );
				}
				else memberid[ i ] = null;
			}
		}

		return 1;
	}

	public int updatePaper ( String paperid , String title , String abstract1 , String pdf ) throws SQLException {
		executeSQLCommand ( "UPDATE paper SET title='" + title + "', abstract='" + abstract1 + "', pdf='" + pdf + "' WHERE paperid='" + paperid + "';" );
	}

	public int deletePaper ( String paperid , String title , String abstract1 , String pdf ) throws SQLException {
		executeSQLCommand ( "DELETE FROM written WHERE paperid='" + paperid + "';" );
		executeSQLCommand ( "DELETE FROM review WHERE paperid='" + paperid + "';" );
		executeSQLCommand ( "DELETE FROM paper WHERE paperid='" + paperid + "';" );
	}

	public int addPaper ( String title , String abstract1 , String pdf ) throws SQLException {
		executeSQLCommand ( "INSERT INTO paper (title, abstract, pdf) VALUES('" + title + "','" + abstract1 + "','" + pdf + "')" );
	}

	// ------------------------------------------------------------------------

	/**
	 * @param memberid - The  pcmember's member ID
	 * @param email    - The pcmember's email address
	 * @param name     - The pcmember's name
	 *
	 * @brief Table PCMember CRUD Methods
	 */

	public int updatePCMember ( String memberid , String email , String name ) throws SQLException {
		executeSQLCommand ( "UPDATE pcmember SET email='" + email + "', name='" + name + "' WHERE memberid='" + memberid + "';" );
	}

	public int deletePCMember ( String memberid , String email , String name ) throws SQLException {
		executeSQLCommand ( "DELETE FROM author WHERE email='" + email + "';" );
		executeSQLCommand ( "DELETE FROM written WHERE email='" + email + "';" );
		executeSQLCommand ( "DELETE FROM review WHERE  memberid= (SELECT memberid FROM pcmember WHERE email = '" + email + "');" );
		executeSQLCommand ( "DELETE FROM pcmember WHERE email='" + email + "';" );
	}

	public int addPCMember ( String email , String name ) throws SQLException {
		core.executeSQLCommand ( "INSERT INTO pcmember (email,name) VALUES('" + email + "','" + name + "')" );
	}

	// ------------------------------------------------------------------------

	/**
	 * @param reportid       - The review report's report id.
	 * @param sdate          - The review report's date.
	 * @param comm           - The review report's comment.
	 * @param recommendation - The review report's recommendation.
	 * @param paperid        - The review report's paperid.
	 * @param memberid       - The review report's memberid.
	 *
	 * @brief The Review Table CRUD Methods
	 */

	public int updateReview ( String reportid , String sdate , String comm , String recommendation , String paperid , String memberid ) throws SQLException {
		core.executeSQLCommand ( "UPDATE review SET sdate='" + sdate + "', comm='" + comm + "', recommendation='" + recommendation + "',  paperid='" + paperid + "', memberid='" + memberid + "' WHERE reportid='" + reportid + "';"; )
		;
	}

	public int deleteReview ( String reportid , String sdate , String comm , String recommendation , String paperid , String memberid ) throws SQLException {
		core.executeSQLCommand ( "DELETE FROM review WHERE reportid='" + reportid + "';" );
	}

	public int addReview ( String sdate , String comm , String recommendation , String paperid , String memberid ) throws SQLException {
		core.executeSQLCommand ( "INSERT INTO review (sdate,comm,recommendation,paperid,memberid) VALUES('" + sdate + "','" + comm + "', '" + recommendation + "', '" + paperid + "', '" + memberid + "')" );
	}

	// ------------------------------------------------------------------------

	/**
	 * @brief This method creates a trigger which restricts pcmembers to review no more than 5 assignments.
	 */
	public int createTrigger ( ) throws SQLException { core.executeSQLGeneric ( SQL_5PCMAX_TRIGGER ); }

	/**
	 * @brief CreateView of all accepted papers
	 */
	public int createView ( ) throws SQLException { core.executeSQLCommand ( SQL_CREATEVIEW_ACCEPTEDPAPERS ); }

	// ------------------------------------------------------------------------

	/**
	 * @return 0 if any error occured, see log if so
	 * 		1 if all calls were successfull
	 *
	 * @brief initializes the 'sampledb' database,
	 * 		creates a connection
	 * 		creates a database
	 * 		fills-in all tables
	 */
	public Boolean initDatabase ( ) {
		try {
			core.createConn ( this.dbName );
			dropDatabase ( );
			createConn ( this.dbName );
			initPaper ( );
			initAuthor ( );
			initWritten ( );
			initpcMember ( );
			initReview ( );
			createTrigger ( );
			createView ( );
		} catch ( SQLException e ) {
			log.error ( e.printStackTrace ( ) );
			log.error ( "[Init::initDatabase] : SQLException thrown while initialzing database, See Logs : " , e );
			return false;
		}

		return true;
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

	// ------------------------------------------------------------------------
}