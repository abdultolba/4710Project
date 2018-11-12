<%--
  Created by IntelliJ IDEA.
  User: abdultolba
  Date: 11/1/18
  Time: 3:15 PM
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

    <!--  <p>PaperID: ${paperid}</p>
		<p>PC Members: ${email1}, ${email2}, ${email3}</p> -->
    <p>
        ${result}
    </p>

    <form action="${pageContext.request.contextPath}/index.jsp#afterInit">
        <input type="submit" value="Return to Homepage">
    </form>
</content>
</body>
</html>
