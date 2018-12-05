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
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!-- Latest compiled and minified CSS -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <!-- jQuery library -->
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <!-- Latest compiled JavaScript -->
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  </head>
  <body>
      <banner id="banner">
          <h1>CSC 4710 Project</h1>
      </banner>

      <content id="content">

          <div id="beforeInit">
              <br>
              <!-- This section prompts the user to first initialize the database
                   before they continue to perform any other requests.             -->
              <form action="${pageContext.request.contextPath}/initDB" method="post">
                  <input type="submit" name="initDB" value="Initialize Database" />
              </form>
              <p>Before selecting any of the remaining options, you must initialize the database using the button above.</p>
          </div>

          <div id="afterInit">
              <!-- This section prompts the user to assign
                   up to 3 reviewers to a paper. -->
              <form action="${pageContext.request.contextPath}/Assign" method="post">
                  <%@ page import="java.sql.*" %>
                  <p>After initializing DB, select Paper ID and up to 3 Reviewers (using CTRL or CMD) to assign to it: </p>
                  <p>*Note: Assignment will fail if the pcmember already has 5 reviews assigned to them or the paper already has 3 reviewers.</p>

                  <!-- This select statement prompts the user for the paperid. -->
                  <select name="Assign">
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
                  </select>

                  <!-- This select statement prompts the user to choose three reviewers by their email. -->
                  <select multiple name="Assign2">
                      <%
                          try {
                              Class.forName("com.mysql.jdbc.Driver");
                              String url = "jdbc:mysql://localhost:3306/sampledb";
                              Connection conn = DriverManager.getConnection(url,"root","cookiejar");
                              Statement stmt = conn.createStatement();
                              ResultSet rs;
                              rs = stmt.executeQuery("SELECT email FROM pcmember");


                              while ( rs.next() ) { %>
                      <option> <%=rs.getString("email") %></option>
                      <%   }
                          conn.close();
                      } catch (Exception e) {
                          System.err.println("Got an exception! ");
                          System.err.println(e.getMessage());
                      }
                      %>
                  </select>
                  <input type="submit" name="Assign2" value="Assign"/>
              </form>
              <br>

              <!-- This section prompts the user to insert,
                   delete, or update an exiting paper. -->
              <form action="${pageContext.request.contextPath}/updatePaper" method="post">
                  <input type="submit" name="paper" value="Insert/Delete/Update a Paper"/>
              </form>

              <!-- This section prompts the user to insert,
                   delete, or update an exiting PC member. -->
              <form action="${pageContext.request.contextPath}/updatePCMember" method="post">
                  <input type="submit" name="pcMember" value="Insert/Delete/Update a PC Member"/>
              </form>

              <form action="${pageContext.request.contextPath}/updateReview" method="post">
                  <input type="submit" name="review" value="Insert/Delete/Update a Review Report"/>
              </form>

              <form action="${pageContext.request.contextPath}/Fotouhi1.jsp" method="post">
                  <input type="submit" name="Lu1" value="List all papers with Fotouhi as the single author"/>
              </form>

              <form action="${pageContext.request.contextPath}/Fotouhi2.jsp" method="post">
                  <input type="submit" name="Lu2" value="List all papers in which Fotouhi is the first author"/>
              </form>

              <form action="${pageContext.request.contextPath}/FotouhiLu.jsp" method="post">
                  <input type="submit" name="FotouhiLu" value="List all papers which Fotouhi and Lu have co-authored"/>
              </form>

              <form action="${pageContext.request.contextPath}/mostReviews.jsp" method="post">
                  <input type="submit" name="mostReviews" value="List the PC member(s) who have reviewed the most papers"/>
              </form>

              <form action="${pageContext.request.contextPath}/noReviews.jsp" method="post">
                  <input type="submit" name="noReviews" value="List the PC member(s) who have no reviews assigned to them"/>
              </form>

              <form action="${pageContext.request.contextPath}/MattJohnReject.jsp" method="post">
                  <input type="submit" name="MattJohnReject" value="List all papers rejected by both Matt and John"/>
              </form>

              <form action="${pageContext.request.contextPath}/viewAccepted.jsp" method="post">
                  <input type="submit" name="viewAccepted" value="View all accepted papers"/>
              </form>

          </div>

      </content>
  </body>
</html>
