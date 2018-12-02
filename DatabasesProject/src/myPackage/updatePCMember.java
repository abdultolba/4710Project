package myPackage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class updatePCMember
 */
@WebServlet ( "/updatePCMember" )
public class updatePCMember extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public updatePCMember ( ) { super ( ); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doGet ( HttpServletRequest request , HttpServletResponse response ) { }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request , HttpServletResponse response)
	 */
	protected void doPost ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
		String memberid = request.getParameter ( "memberid" );
		String email = request.getParameter ( "email" );
		String name = request.getParameter ( "name" );

		if ( ! request.getParameter ( "update" ).isEmpty ( ) )
			new Init ( ).updatePCMember ( memberid , email , name );

		if ( ! request.getParameter ( "delete" ).isEmpty ( ) )
			new Init ( ).deletePCMember ( memberid , email , name );

		if ( ! request.getParameter ( "addnew" ).isEmpty ( ) ) {
			String emailX = request.getParameter ( "emailX" );
			String nameX = request.getParameter ( "nameX" );
			
			new Init ( ).addPCMember ( emailX , nameX );
		}

		request.getRequestDispatcher ( "updatePCMember.jsp" ).forward ( request , response );
	}
}
