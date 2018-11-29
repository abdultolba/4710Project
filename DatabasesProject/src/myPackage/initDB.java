package myPackage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class initDB
 */
@WebServlet ( name = "initDB", urlPatterns = { "/initDB" } )
public class initDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public initDB ( ) { super ( ); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doGet ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException { }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doPost ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
		String executionResult = "";

		executionResult = ( new Init ( ).initDatabase ( ) ) ?
				                  ( "Database Initialization Unsuccessful." ) :
				                  ( "Database Initialization was Successful." );

		request.setAttribute ( "message" , "Processing Complete." );

		request.setAttribute ( "result" , executionResult );
		request.getRequestDispatcher ( "initDB.jsp" ).forward ( request , response );
	}
}
