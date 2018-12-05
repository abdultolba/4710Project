<%--
  Created by IntelliJ IDEA.
  User: abdultolba
  Date: 12/3/18
  Time: 2:24 PM
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
    <h1>Papers which have been rejected by both Matt and John</h1>
    <form>
        <%@ page import="java.sql.*" %>
        <%
            try {
                String url = "jdbc:mysql://localhost:3306/sampledb";
                Connection conn = DriverManager.getConnection(url,"root","pass1234");
                Statement stmt = conn.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT paperid, title FROM paper WHERE paperid =(SELECT paperid FROM review WHERE memberid = (SELECT memberid FROM pcmember WHERE name='John' AND recommendation = 'r') AND paperid IN (SELECT paperid FROM review WHERE memberid = (SELECT memberid FROM pcmember WHERE name='Matt' AND recommendation='r')));");

                while ( rs.next() ) { %>
        <p>Paper ID:<input type= "text" value =<%=rs.getString("paperid") %> >Paper Title:<input type= "text" size="35" value ="<%=rs.getString("title") %>" ></p><br>
        <%   }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        %>
    </form>

    <form action="${pageContext.request.contextPath}/index.jsp#afterInit">
        <input type="submit" value="Return to Homepage">
    </form>

</content>

</body>
</html>
