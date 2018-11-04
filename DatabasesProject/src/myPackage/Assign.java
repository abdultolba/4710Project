package myPackage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Assign
 */
@WebServlet(name="Assign", urlPatterns={"/Assign"})
public class Assign extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Assign() {
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

        String selection = request.getParameter("Assign");
        String[] selection2 = request.getParameterValues("Assign2");
        for(int i =0; i < selection2.length; i++){
            System.out.println(selection2[i]);
        }

        if (selection2.length - 1 > 3){
            String result = "Error: Assignment failure! You cannot assign more than 3 reviewers to a paper.";
            System.out.print(result);
            request.setAttribute("result", result);
            request.getRequestDispatcher("Assign.jsp").forward(request, response);
        }
        else{
            Init i = new Init();
            int res = i.assign(selection, selection2);

            if (res == 1){
                String result = "Assignment success!";
                request.setAttribute("result", result);
            }
            else{
                String result = "Error: Assignment failure.";
                request.setAttribute("result", result);
            }
            request.getRequestDispatcher("Assign.jsp").forward(request, response);
        }
    }

}