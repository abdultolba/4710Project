<%--
  Created by IntelliJ IDEA.
  User: abdultolba
  Date: 11/1/18
  Time: 5:08 PM
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
    <%@ page import="java.sql.Connection" %>
    <%@ page import="java.sql.DriverManager" %>
    <%@ page import="java.sql.ResultSet" %>
    <%@ page import="java.sql.Statement" %>
    <p>Update, Delete, Add PC members: </p>

    <table align="center" border="2">
        <tr>
            <td>Member ID</td>
            <td>Email</td>
            <td>Name</td>
            <td>Actions:</td>
        </tr>

        <%
            try {
                Class.forName ( "com.mysql.jdbc.Driver" );
                String url = "jdbc:mysql://localhost:3306/sampledb";
                Connection conn = DriverManager.getConnection ( url , "john" , "pass1234" );
                Statement stmt = conn.createStatement ( );
                ResultSet rs;
                rs = stmt.executeQuery ( "SELECT * FROM pcmember" );
                while ( rs.next ( ) ) { %>
        <form action="${pageContext.request.contextPath}/updatePCMember" method="post">
            <tr>
                <td><input type="text" name="memberid" readonly="readonly" value="<%=rs.getString("memberid") %>"></td>
                <td><input type="text" name="email" value="<%=rs.getString("email") %>"></td>
                <td><input type="text" name="name" value="<%=rs.getString("name") %>"></td>
                <td><input type="submit" name="update" value="Update"/>
                    <input type="submit" name="delete" value="Delete"/>
                </td>
            </tr>
        </form>
        <%
            }
        %>
    </table>
    <br>

    <form action="${pageContext.request.contextPath}/updatePCMember" method="post">
        <tr>
            <td><input type="text" name="emailX" placeholder="Email"></td>
            <td><input type="text" name="nameX" placeholder="Name"></td>
            <td><input type="submit" name="addnew" value="Add New PC Member"/></td>
        </tr>
        <%
                rs.close ( );
                stmt.close ( );
                conn.close ( );
            } catch ( Exception e ) {
                e.printStackTrace ( );
            }
        %>
    </form>

    <form action="${pageContext.request.contextPath}/index.jsp#afterInit">
        <input type="submit" value="Return to Homepage">
    </form>
</content>

</body>
</html>
