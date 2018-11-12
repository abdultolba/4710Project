package myPackage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class updatePaper
 */
@WebServlet ( name = "/updatePaper", urlPatterns = { "/updatePaper" } )
public class updatePaper extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public updatePaper ( ) {
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
		String paperid = request.getParameter ( "paperid" );
		String title = request.getParameter ( "title" );
		String abstract1 = request.getParameter ( "abstract" );
		String pdf = request.getParameter ( "pdf" );

		String titleX = request.getParameter ( "titleX" );
		String abstract1X = request.getParameter ( "abstractX" );
		String pdfX = request.getParameter ( "pdfX" );

		if ( request.getParameter ( "update" ) != null ) {
			Init i = new Init ( );
			int x = i.updatePaper ( paperid , title , abstract1 , pdf );
			System.out.print ( x );
		}
		if ( request.getParameter ( "delete" ) != null ) {
			Init b = new Init ( );
			int y = b.deletePaper ( paperid , title , abstract1 , pdf );
			System.out.print ( y );
		}
		if ( request.getParameter ( "addnew" ) != null ) {
			Init c = new Init ( );
			int z = c.addPaper ( titleX , abstract1X , pdfX );
			System.out.print ( title );
		}
		request.getRequestDispatcher ( "updatePaper.jsp" ).forward ( request , response );

	}

}