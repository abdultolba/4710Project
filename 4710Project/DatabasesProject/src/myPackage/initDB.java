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
	public initDB ( ) {
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
		// Create a new instance of the Initializer class
		Init i = new Init ( );
		String res;
		if ( i.initDatabase ( ) == 0 )      // If the initialization fails, print a failure message.
			res = "Failure. Could not initialize database.";
		else                            // If it succeeds, print a success message.
			res = "Success! Database has been initialized.";
		request.setAttribute ( "message" , "Processing Complete" );
		request.setAttribute ( "result" , res );
		request.getRequestDispatcher ( "initDB.jsp" ).forward ( request , response );
	}

}