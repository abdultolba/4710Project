<%--
  Created by IntelliJ IDEA.
  User: abdultolba
  Date: 10/31/18
  Time: 12:10 PM
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
          <h2>Menu</h2>
          <form action="${pageContext.request.contextPath}/initDB" method="post">
              <input type="submit" name="initDB" value="Initialize Database" />
          </form>
          <p>Before selecting any of the remaining options, you must initialize the database using the button above.</p>

          <form action="${pageContext.request.contextPath}/Assign" method="post">
              <%@ page import="java.sql.*" %>
              <p>After initializing DB, select Paper ID and up to 3 Reviewers (using CTRL or CMD) to assign to it: </p>
              <p>*Note: Assignment will fail if the pcmember already has 5 reviews assigned to them or the paper already has 3 reviewers.</p>
              <SELECT name="Assign">
                  <%
                      try {
                          Class.forName("com.mysql.jdbc.Driver");
                          String url = "jdbc:mysql://localhost:3306/sampledb";
                          Connection conn = DriverManager.getConnection(url,"root","cookiejar");
                          Statement stmt = conn.createStatement();
                          ResultSet rs;
                          rs = stmt.executeQuery("SELECT paperid FROM paper");

                          while ( rs.next() ) { %>
                            <Option> <%=rs.getString("paperid") %></Option>
                  <%   }
                      conn.close();
                  } catch (Exception e) {
                      System.err.println("Got an exception! ");
                      System.err.println(e.getMessage());
                  }
                  %>
              </SELECT>



              <SELECT multiple name="Assign2">
                  <%
                      try {
                          Class.forName("com.mysql.jdbc.Driver");
                          String url = "jdbc:mysql://localhost:3306/sampledb";
                          Connection conn = DriverManager.getConnection(url,"root","cookiejar");
                          Statement stmt = conn.createStatement();
                          ResultSet rs;
                          rs = stmt.executeQuery("SELECT email FROM pcmember");


                          while ( rs.next() ) { %>
                  <Option> <%=rs.getString("email") %></Option>
                  <%   }
                      conn.close();
                  } catch (Exception e) {
                      System.err.println("Got an exception! ");
                      System.err.println(e.getMessage());
                  }
                  %>
              </SELECT>
              <input type="submit" name="Assign2" value="Assign"/>
          </form>

          <br>

          <form action="${pageContext.request.contextPath}/updatePaper" method="post">
              <input type="submit" name="paper" value="Insert/Delete/Update a Paper"/>
          </form>

          <br>

          <form action="${pageContext.request.contextPath}/updatePCMember" method="post">
              <input type="submit" name="pcMember" value="Insert/Delete/Update a PC Member"/>
          </form>

      </content>
  </body>
</html>
