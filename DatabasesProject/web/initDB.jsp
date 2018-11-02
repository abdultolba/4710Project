<%--
  Created by IntelliJ IDEA.
  User: abdultolba
  Date: 11/1/18
  Time: 3:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Initialize Database</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <banner id="banner">
        <h1>CSC 4710 Project</h1>
    </banner>

    <content id="content">
        <h2 style="color:black;">${message}</h2>
        <p>Result: ${result}</p>
        <form action="${pageContext.request.contextPath}/index.jsp">
            <input type="submit" value="Return to Homepage">
        </form>
    </content>

</body>
</html>
