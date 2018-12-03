<%--
  Created by IntelliJ IDEA.
  User: abdultolba
  Date: 12/3/18
  Time: 1:52 PM
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
    <h1>Papers with Fotouhi as the single author</h1>
    <form>
        <%@ page import="java.sql.*" %>
        <%
            try {
                String url = "jdbc:mysql://localhost:3306/sampledb";
                Connection conn = DriverManager.getConnection(url,"root","cookiejar");
                Statement stmt = conn.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT * FROM paper WHERE paperid IN (SELECT paperid FROM written WHERE email = (SELECT email FROM author WHERE name='Fotouhi')) AND paperid NOT IN (SELECT paperid FROM written WHERE email <> (SELECT email FROM author WHERE name='Fotouhi'))");
                while ( rs.next() ) { %>
        <p>Paper ID:<input type= "text" value =<%=rs.getString("paperid") %> readonly> Paper Title:<input type= "text" value =<%=rs.getString("title") %> readonly></p>
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
