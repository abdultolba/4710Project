package myPackage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class updateReview
 */
@WebServlet ( "/updateReview" )
public class updateReview extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public updateReview ( ) { super ( ); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doGet ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException { }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doPost ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
		if ( !request.getParameter ( "addnew" ).isEmpty () ) {
			String sdateX = request.getParameter ( "sdateX" );
			String commX = request.getParameter ( "commX" );
			String recommendationX = request.getParameter ( "recommendationX" );
			String paperidX = request.getParameter ( "paperidX" );
			String memberidX = request.getParameter ( "memberidX" );

			new Init ().addReview ( sdateX , commX , recommendationX , paperidX , memberidX );
		} else {
			String reportid = request.getParameter ( "reportid" );
			String sdate = request.getParameter ( "sdate" );
			String comm = request.getParameter ( "comm" );
			String recommendation = request.getParameter ( "recommendation" );
			String paperid = request.getParameter ( "paperid" );
			String memberid = request.getParameter ( "memberid" );

			if ( !request.getParameter ( "update" ).isEmpty () )
				new Init ().updateReview ( reportid , sdate , comm , recommendation , paperid , memberid );

			if ( !request.getParameter ( "delete" ).isEmpty () )
				new Init ().deleteReview ( reportid , sdate , comm , recommendation , paperid , memberid );
		}

		request.getRequestDispatcher ( "updateReview.jsp" ).forward ( request , response );
	}
}
