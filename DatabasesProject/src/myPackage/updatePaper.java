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
	public updatePaper ( ) { super ( ); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doGet ( HttpServletRequest request , HttpServletResponse response ) { }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doPost ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
		String paperid = request.getParameter ( "paperid" );
		String title = request.getParameter ( "title" );
		String abstract1 = request.getParameter ( "abstract" );
		String pdf = request.getParameter ( "pdf" );

		if ( ! request.getParameter ( "update" ).isEmpty ( ) )
			new Init ( ).updatePaper ( paperid , title , abstract1 , pdf );

		if ( ! request.getParameter ( "delete" ).isEmpty ( ) )
			new Init ( ).deletePaper ( paperid , title , abstract1 , pdf );

		if ( ! request.getParameter ( "addnew" ).isEmpty ( ) ) {
			String titleX = request.getParameter ( "titleX" );
			String abstract1X = request.getParameter ( "abstractX" );
			String pdfX = request.getParameter ( "pdfX" );

			new Init ( ).addPaper ( titleX , abstract1X , pdfX );
		}

		request.getRequestDispatcher ( "updatePaper.jsp" ).forward ( request , response );
	}
}
