package myPackage;

import myPackage.UpdateDB;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class updatePCMember
 */
@WebServlet("/updatePCMember")
public class updatePCMember extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public updatePCMember() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String memberid = request.getParameter("memberid");
        String email = request.getParameter("email");
        String name = request.getParameter("name");

        String emailX = request.getParameter("emailX");
        String nameX = request.getParameter("nameX");

        if(request.getParameter("update") != null){
            Init i = new Init();
            int x = i.updatePCMember(memberid, email, name);
            System.out.print(x);
        }
        if(request.getParameter("delete") != null){
            Init b = new Init();
            int y = b.deletePCMember(memberid, email, name);
            System.out.print(y);
        }
        if(request.getParameter("addnew") != null){
            Init c = new Init();
            int z = c.addPCMember(emailX, nameX);
            System.out.print(z);
        }
        request.getRequestDispatcher("updatePCMember.jsp").forward(request, response);
    }
}