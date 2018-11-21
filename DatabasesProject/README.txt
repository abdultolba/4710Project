************************************************************************************************************************
                            CSC 4710 - Introduction to Database Management Systems
                                            Course Project - Part 1
************************************************************************************************************************


Consider the design of the following database system for managing a particular conference: a collection of papers are
submitted to the conference, each of which has a unique paper ID, a list of authors (names, affiliations, emails) in the
order of contribution significance, title, abstract, and a PDF file for its content. The conference has a list of
program committee (PC) members to review the papers. To ensure review quality, each paper is assigned to 3 PC members
for review. To avoid overloading, each PC member is assigned with at most 5 papers, assuming that there are enough PC
members. Each review report consists of a report ID, a description of review comment, a final recommendation
(accept, reject), and the date the review report is submitted. A PC member can submit at most one review report for the
paper that is assigned to him/her.

Part 1:

Design  the  database  using  the  ER  approach.  Then  using  Java  and  SQL, implement the following functionality:
1.  Implement a button called “Initialize Database”. When a user clicks it, all necessary tables will  be  created  (or  recreated)
automatically,  with  each  table  be  populated  with  at  least  10 tuples so that each query below will return some
results. All students should use the database name “sampledb”, username “john”, and password “pass1234”.
2.  Create the functionality to assign three reviewers to a paper. Your system must be web-based. Some simple GUI
interfaces are required for each functionality. All functionality must be performed via the interface of your system,
direct SQL statement execution via any tools (MySQL workbench) is not allowed.

Group Members:

1.)
Name:       Abdulrahman Tolba
Email:      abdultolba@wayne.edu --or-- ga4237@wayne.edu
AccessID:   ga4237
Roles:      Project planning, SQL query development & execution, development of Java Servlet Pages and Java classes
necessary to drive the web application, website design and functionality.

2.)
Name:       Anmol Multani
Email:      multani.anmol@wayne.edu
Roles:      Worked on the initDB java Servlet that references the Init.java class which initializes the database.

Instructions:

This project was built using IntelliJ IDEA Ultimate, so you may need this software to deploy.
Ensure that the TomCat server is properly configured in order for the web app to deploy. Start your MySQL sever before
building and running the app. Make sure your MySQL username and password are 'john' and 'pass1234', respectively. Once
everything is properly configured, go into the 'run' menu in IntelliJ, and click run. The TomCat server should start and
the project should deploy, opening the application in your default browser.




