package myPackage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Servlet implementation class Assign
 */
@WebServlet ( name = "Assign", urlPatterns = { "/Assign" } )
public class Assign extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Assign ( ) { super ( ); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doGet ( HttpServletRequest request , HttpServletResponse response ) { }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doPost ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
		String executionResult = "";

		String selection = request.getParameter ( "Assign" );
		String[] selection2 = request.getParameterValues ( "Assign2" );

		System.out.println ( Arrays.toString ( selection2 ) );

		if ( selection2.length >= 4 )
			executionResult = "Assignment Unsuccessful.\n At most three reviewers can be assigned to one paper.";
		else if ( selection.isEmpty ( ) )
			executionResult = "Assignment Unsuccessful.\n Assign field is empty.";
		else
			executionResult = ( new Init ( ).assign ( selection , selection2 ) ) ? ( "Assignment was Successful" ) : ( "Assignment Unsuccessful. Unknown Error Occurred" );

		request.setAttribute ( "result" , executionResult );
		request.getRequestDispatcher ( "Assign.jsp" ).forward ( request , response );
	}
}
