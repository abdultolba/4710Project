<%--
  Created by IntelliJ IDEA.
  User: abdultolba
  Date: 11/1/18
  Time: 4:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CSC 4710 Project</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <banner id="banner">
        <h1>CSC 4710 Project</h1>
    </banner>
    <content id="content">
            <%@ page import="java.sql.*" %>
            <p>Update, Delete, & Add papers: </p>

            <table align="center" border="2">
                <tr>
                    <td>Paper ID</td>
                    <td>Title</td>
                    <td>Abstract</td>
                    <td>PDF</td>
                    <td>Actions:</td>
                </tr>
                <%
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/sampledb";
                        Connection conn = DriverManager.getConnection(url,"root","pass1234");
                        Statement stmt = conn.createStatement();
                        ResultSet rs;
                        rs = stmt.executeQuery("SELECT * FROM paper");


                        while ( rs.next() ) { %>
                <form action="${pageContext.request.contextPath}/updatePaper" method="post">
                    <tr>
                        <td><input type="text" name="paperid" readonly="readonly" value="<%=rs.getString("paperid") %>"></td>
                        <td><input type="text" name="title" value="<%=rs.getString("title") %>"></td>
                        <td><input type="text" name="abstract" value="<%=rs.getString("abstract") %>"></td>
                        <td><input type="text" name="pdf" value="<%=rs.getString("pdf") %>"></td>
                        <td>
                            <input type="submit" name="update" value="Update"/>
                            <input type="submit" name="delete" value="Delete"/>
                        </td>
                    </tr>


                </form>
                <%
                    }
                %>
            </table>
        <br>
            <form action="${pageContext.request.contextPath}/updatePaper" method="post"><tr>
                <td><input type="text" name="titleX" placeholder="Title"></td>
                <td><input type="text" name="abstractX" placeholder="Abstract"></td>
                <td><input type="text" name="pdfX" placeholder="PDF"></td>
                <td><input type="submit" name="addnew" value="Add New Paper"/>
                </td></tr>
                <%
                        rs.close();
                        stmt.close();
                        conn.close();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                %>
            </form>

            <form action="${pageContext.request.contextPath}/index.jsp">
                <input type="submit" value="Return to Homepage">
            </form>

    </content>
</body>
</html>
