************************************************************************************************************************
                            CSC 4710 - Introduction to Database Management Systems
                                            Course Project - Part 1
************************************************************************************************************************

GitHub Link: https://github.com/abdultolba/4710Project

# Setup

Step 1) Import project folder/.war file into IntelliJ IDEA Ultimate. <br><br>
Step 1.1) Ensure JRE version is 1.8. <br><br>
Step 2) Configure a Tomcat 7 server in Eclipse. <br><br>
Step 2.1) Ensure Tomcat 7 library is on the projects build path. <br><br>
Step 3) Ensure your MySQL server is running, and that your username and password are 'john' and 'pass1234', respectively. <br><br>
Step 4) Build the project and click 'Run TomCat 7.0.xx' which will run the index.jsp file. <br><br>
Step 5) The TomCat server should start and the project should deploy, opening the application in your default browser. <br>


# Group Members:

1.) <br>
Name:       Abdulrahman Tolba <br>
Email:      abdultolba@wayne.edu --or-- ga4237@wayne.edu <br>
AccessID:   ga4237 <br>
Roles:      Project planning, SQL query development & execution, development of Java Servlet Pages and Java classes
necessary to drive the web application, website design and functionality. <br>
<br>
2.) <br>
Name:       Anmol Multani <br>
Email:      multani.anmol@wayne.edu <br>
Roles:      Worked on the initDB java Servlet that references the Init.java class which initializes the database. <br>

# Using the Database/Web Application

## Part 1 Requirements: <br>

1.) Initialize the database by clicking the "Initialize Database" button. This deletes all tables in the database - if they exist. It then creates the tables and populates them. <br>

2.) To assign reviewers to a paper, select a Paper ID from the drop down menu and choose up to three PC Members (using CTRL or CMD). If the PCMember already has 5 reviews assigned to them or the paper already has 3 reviewers, assignment will fail. <br> <br>

## Part 2 Requirements: <br>

1.) To insert, delete or update a paper: Click the 'Insert/Delete/Update a Paper' button on the homepage. A table with the fields PaperID, Title	Abstract, PDF, and the possible actions will appear. To delete a paper, simply click the delete button in the actions column and it will be delete. To update a paper, change the fields you want (except for PaperID which can't be changed) and click update. If you wish to add an entirely new paper, enter the title, abstract, and pdf at the bottom in the empty fields and then click 'Add New Paper'. When you are done, click 'Return to Homepage'. <br>

2.) To insert, delete or update a pc member: Click the 'Insert/Delete/Update a Paper' button on the homepage. A table with the fields MemberID, email, name, and the possible actions will appear. To delete a PC Member, simply click the delete button in the actions column and it will be delete. To update a PC Member, change the fields you want (except for MemberID which can't be changed) and click update. If you wish to add an entirely new PC Member, enter the email and name at the bottom in the empty fields and then click 'Add New PC Member'. When you are done, click 'Return to Homepage'. <br>

3.) To insert, delete, or update a review report: Click the 'Insert/Delete/Update a Review Report' button on the homepage. A table with the fields ReportID, start date, comment, recommendation, paper id, member id, and the possible actions will appear. To delete a review report, simply click the delete button in the actions column and it will be delete. To update a review report, change the fields you want (except for ReportID, PaperID, and MemberID which can't be changed) and click update. If you wish to add an entirely new review report, enter the attributes required at the bottom in the empty fields and then click 'Add New Review'. When you are done, click 'Return to Homepage'. <br>

4.) To list all papers with Fotouhi as the single author: Click 'List all papers with Fotouhi as the single author' on the homepage. The web app will then execute a SQL query to find all the papers with only Fotouhi as their author. <br>

5.) To list all papers with Fotouhi as the first author: Click 'List all papers with Fotouhi as the first author' on the homepage. The web app will then execute a SQL query to find all the papers with Fotouhi as the most significant author. <br>

6.) To list all papers which Fotouhi and Lu have co-authored: Click 'List all papers which Fotouhi and Lu have co-authored' on the homepage. The web app will then execute a SQL query to find all the papers with Fotouhi and Lu as their authors. <br>

7.) To list the PC Member(s) who have reviewed the most papers: Click 'List the PC Member(s) who have reviewed the most papers' on the homepage. The web app will then execute a SQL query to find the PC Member who reviewed the most papers, or members if there is a tie. <br>

8.) To list the PC Members who are not reviewing any papers, Click 'List the PC member(s) who have no reviews assigned to them' on the homepage. The web app will then execute a SQL query to check which PC Members have not been assigned to review any papers. <br>

9.) To list all papers which have been rejected by both Matt and John: Click 'List all papers which have been rejected by both Matt and John' on the homepage. <br>

10.) To view all the accepted papers: Click 'View all accepted papers' at the bottom of the homepage. This will trigger a SQL Query to show all papers which have been accepted by atleast 2 or more PC Members.




