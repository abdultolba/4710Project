package myPackage;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDB {
	private java.sql.Connection conn;

	public int createConn ( String dbName ) {
		try {
			Class.forName ( "com.mysql.jdbc.Driver" ).getDeclaredConstructor (  ).newInstance (  );
			conn = DriverManager.getConnection ( "jdbc:mysql://localhost:3306/" + dbName + "?allowMultiQueries=true&user=john&password=pass1234" );
		} catch ( Exception e ) {
			System.out.print ( "[ERROR][UpdateDB::createConn] - UnknownError Encountered: " + e + " while creating connection\n" );
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	public int endConn ( ) {
		try {
			conn.close ( );
			return 1;
		} catch ( SQLException e ) {
			System.out.print ( "[ERROR][UpdateDB::endConn] - MySQL Encountered: " + e + " while closing connection\n" );
			e.printStackTrace();
			return 0;
		}
	}

	// -------––---------------------------------------------------------------

	public Boolean runSQLStatement ( String sqlStatement ) {
		Boolean queryExecutionResult = false;

		try {
			System.out.print ( "[INFO][UpdateDB::runSQLStatement] - MySQL Statement: " + sqlStatement );

			PreparedStatement preparestatement = conn.prepareStatement ( sqlStatement );
			queryExecutionResult = preparestatement.execute ( sqlStatement );
		} catch ( SQLException e ) {
			System.out.print ( "[ERROR][UpdateDB::runSQLStatement] - Exception Encountered: " + e + "\n" );
			e.printStackTrace ();
		}

		return queryExecutionResult;
	}

	// -------––---------------------------------------------------------------

	public Boolean insertPaper ( String title , String abstr , String pdf ) {
		return runSQLStatement( "INSERT INTO paper(title, abstract, pdf) VALUES(" + title + ", " + abstr + ", " + pdf + ");" );
	}

	public Boolean deletePaper ( String paperID ) {
		return runSQLStatement( "DELETE FROM paper WHERE paperid = " + paperID );
	}

	public Boolean updatePaper ( String title , String abstr , String pdf , String paperID ) {
		return runSQLStatement( "UPDATE paper SET title = " + title + " abstract = " + abstr + " pdf = " + pdf + " WHERE paperid = " + paperID );
	}

	// -------––---------------------------------------------------------------

	public Boolean insertPCMember ( String email , String name ) {
		return runSQLStatement( "INSERT INTO pcmember(email, name) VALUES(" + email + ", " + name + ");" );
	}

	public Boolean deletePCMember ( String memberid ) {
		return runSQLStatement( "DELETE FROM pcmemeber WHERE memberid = " + memberid );
	}

	public Boolean updatePCMember ( String email , String name , String memberid ) {
		return runSQLStatement( "UPDATE pcmember SET email = " + email + " name = " + name + " WHERE memberid = " + memberid );
	}

	// -------––---------------------------------------------------------------

	public Boolean insertReview ( String sdate , String comm , String recommendation , String paperid , String email ) {
		return runSQLStatement( "INSERT INTO review(sdate, comm, recommendation, paperid, email) VALUES(" + sdate + ", " + comm + ", " + recommendation + ", " + paperid + ", " + email + ");" );
	}

	public Boolean deleteReview ( String reportid ) {
		return runSQLStatement( "DELETE FROM review WHERE reportid = " + reportid );
	}

	public Boolean updateReview ( String sdate , String comm , String recommendation , String paperid , String memberid , String reportid ) {
		return runSQLStatement( "UPDATE pcmember SET sdate = " + sdate + " comm = " + comm + "recommendation = " + recommendation + " paperid = " + paperid + " memberid = " + memberid + " WHERE reportid = " + reportid );
	}

	// -------––---------------------------------------------------------------
}
