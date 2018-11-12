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
	public updateReview ( ) {
		super ( );
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doGet ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doPost ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
		// TODO Auto-generated method stub


		// (update) get params
		String reportid = request.getParameter ( "reportid" );
		//int paperid = Integer.parseInt(paperid2);
		String sdate = request.getParameter ( "sdate" );
		String comm = request.getParameter ( "comm" );
		String recommendation = request.getParameter ( "recommendation" );
		String paperid = request.getParameter ( "paperid" );

		String memberid = request.getParameter ( "memberid" );

		String sdateX = request.getParameter ( "sdateX" );
		String commX = request.getParameter ( "commX" );
		String recommendationX = request.getParameter ( "recommendationX" );
		String paperidX = request.getParameter ( "paperidX" );

		String memberidX = request.getParameter ( "memberidX" );


		if ( request.getParameter ( "update" ) != null ) {
			Init i = new Init ( );
			int x = i.updateReview ( reportid , sdate , comm , recommendation , paperid , memberid );

			System.out.print ( x );
		}


		if ( request.getParameter ( "delete" ) != null ) {
			Init b = new Init ( );
			int y = b.deleteReview ( reportid , sdate , comm , recommendation , paperid , memberid );

			System.out.print ( y );

		}

		if ( request.getParameter ( "addnew" ) != null ) {
			Init c = new Init ( );
			int z = c.addReview ( sdateX , commX , recommendationX , paperidX , memberidX );

			System.out.print ( z );
		}

		request.getRequestDispatcher ( "updateReview.jsp" ).forward ( request , response );

	}

}
