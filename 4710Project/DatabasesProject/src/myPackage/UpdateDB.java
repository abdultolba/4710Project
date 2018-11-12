package myPackage;

import src.myPackage.CRUD_Driver;

public class UpdateDB {
	private static CRUD_Driver core;
	private java.sql.Connection conn;

	public int insertPaper ( String title , String abstr , String pdf ) {
		String sql = "INSERT INTO paper(title, abstract, pdf) VALUES(" + title + ", " + abstr + ", " + pdf + ");";
		core.executeSQLCommand ( sql );
	}

	public int deletePaper ( String paperID ) {
		String sql = "DELETE FROM paper WHERE paperid = " + paperID;
		core.executeSQLCommand ( sql );
	}

	public int updatePaper ( String title , String abstr , String pdf , String paperID ) {
		String sql = "UPDATE paper SET title = " + title + " abstract = " + abstr + " pdf = " + pdf + " WHERE paperid = " + paperID;
		core.executeSQLCommand ( sql );
	}

	public int insertPCMember ( String email , String name ) {
		String sql = "INSERT INTO pcmember(email, name) VALUES(" + email + ", " + name + ");";
		core.executeSQLCommand ( sql );
	}

	public int deletePCMember ( String memberid ) {
		String sql = "DELETE FROM pcmemeber WHERE memberid = " + memberid;
		core.executeSQLCommand ( sql );
	}

	public int updatePCMember ( String email , String name , String memberid ) {
		String sql = "UPDATE pcmember SET email = " + email + " name = " + name + " WHERE memberid = " + memberid;
		core.executeSQLCommand ( sql );
	}

	public int insertReview ( String sdate , String comm , String recommendation , String paperid , String email ) {
		String sql = "INSERT INTO review(sdate, comm, recommendation, paperid, email) VALUES(" + sdate + ", " + comm + ", " + recommendation + ", " + paperid + ", " + email + ");";
		core.executeSQLCommand ( sql );
	}

	public int deleteReview ( String reportid ) {
		String sql = "DELETE FROM review WHERE reportid = " + reportid;
		core.executeSQLCommand ( sql );
	}

	public int updateReview ( String sdate , String comm , String recommendation , String paperid , String memberid , String reportid ) {
		String sql = "UPDATE pcmember SET sdate = " + sdate + " comm = " + comm + "recommendation = " + recommendation + " paperid = " + paperid + " memberid = " + memberid + " WHERE reportid = " + reportid;
		core.executeSQLCommand ( sql );
	}
}