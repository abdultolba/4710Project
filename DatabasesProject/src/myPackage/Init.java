package myPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class Init {
    private Connection conn;            // Private class variable for connection to MySQL database

    /**
     * This method attempts to create an active MySQL connection.
     * @param dbName The database name (ex: 'sampledb')
     * @return 1 if the connection was created, 0 if it could not be established.
     */
    public int createConn(String dbName){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName+"?allowMultiQueries=true&user=root&password=pass1234");
        } catch (Exception e){
            System.out.print("Error occurred: "+e+"\n");
            return 0;
        }
        return 1;
    }

    /**
     * This method attempts to terminate the current MySQL connection
     * @return 1 if the connection was ended, 0 if it could not end the connection.
     */
    public int endConn(){
        try{
            conn.close();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This method reads comma separated values from a .csv
     * file and stores them in a multi-dimensional array
     * @param tablename - a string containing the table's name (ex: 'paper')
     * @param numAttributes - an integer representing the number of attributes associated with the table.
     * @return - A multi-dimensional array containing the values which were contained in the CSV file
     */
    public String[][] getFileContent(String tablename, int numAttributes){
        System.out.print("Working on table " + tablename + " in getFileContent.\n");
        try{
            int size = 10;
            if (tablename == "paper") size = 13;
            else if (tablename == "author") size = 12;
            else if (tablename == "written") size = 14;
            else if (tablename == "pcmember") size = 14;
            else if (tablename == "review") size = 12;

            File myFile = new File(getClass().getResource(tablename+".csv").toURI());
            String[][] content = new String[size][numAttributes];
            String row = "";
            int i = 0;
            BufferedReader csv = new BufferedReader(new FileReader(myFile));

            while ((row = csv.readLine()) != null && i < size){
                content[i] = row.split(",");
                System.out.print(content[i][0]+"\n");
                i++;
            }
            return content;
        } catch(Exception e){
            System.out.print("Error encountered: " + e + "\n");
            return null;
        }
    }

    /**
     * This function creates and populates the table 'paper' in
     * the sampledb database by executing the appropriate SQL query
     * @return - Returns a 1 if the table was initialized, 0 otherwise.
     */
    public int initPaper(){
        String tablename = "paper";
        String createStatement = "CREATE TABLE IF NOT EXISTS paper(\n" +
                "\tpaperid INTEGER AUTO_INCREMENT, \n" +
                "\ttitle VARCHAR(50), \n" +
                "\tabstract VARCHAR(250), \n" +
                "\tpdf VARCHAR(100), \n" +
                "\tPRIMARY KEY (paperid)\n" +
                ");";
        //create table
        createTable(createStatement);
        //table paper has FOUR attributes
        String[][] paper = getFileContent(tablename, 4);
        System.out.print("PAPER: " + paper);
        if(paper == null){
            System.out.print("Error: Paper array empty\n");
            return 0;
        }
        else{
            for(int i=0; i<paper.length; i++){
                System.out.print("i="+i+"\n");
                System.out.print(paper[i][0]);
                try{
                    String sql = "INSERT INTO paper VALUES("
                            + paper[i][0] + ", '"
                            + paper[i][1] + "', '"
                            + paper[i][2] + "', '"
                            + paper[i][3] + "');";
                    PreparedStatement preparestatement = conn.prepareStatement(sql);
                    preparestatement.executeUpdate();
                } catch (SQLException e){
                    System.out.print("Encountered exception: "+e+"\n");
                    return 0;
                }
            }
            return 1;
        }
    }

    /**
     * This function creates and populates the table 'author' in
     * the sampledb database by executing the appropriate SQL query
     * @return - Returns a 1 if the table was initialized, 0 otherwise.
     */
    public int initAuthor(){
        String tablename = "author";
        String createStatement = "CREATE TABLE IF NOT EXISTS author(email VARCHAR(100), name VARCHAR(50), affiliation VARCHAR(100), PRIMARY KEY (email));";
        createTable(createStatement);
        //table author has THREE attributes
        String[][] author = getFileContent(tablename, 3);
        if(author == null){
            System.out.print("Error: Author array empty\n");
            return 0;
        }
        else{
            for(int i=0; i<author.length; i++){
                try{
                    System.out.print(author[i][1]);
                    String sql = "INSERT INTO author VALUES("+author[i][1]+", "+author[i][0]+", "+author[i][2]+");";
                    PreparedStatement preparestatement = conn.prepareStatement(sql);
                    preparestatement.executeUpdate();
                } catch (SQLException e){
                    System.out.print("Encountered exception: "+e+"\n");
                    return 0;
                }
            }
            return 1;
        }
    }

    /**
     * This function creates and populates the table 'written' in
     * the sampledb database by executing the appropriate SQL query
     * @return - Returns a 1 if the table was initialized, 0 otherwise.
     */
    public int initWritten(){
        endConn();
        createConn("sampledb");
        String tablename = "written";
        String createStatement = "CREATE TABLE IF NOT EXISTS written(id INTEGER AUTO_INCREMENT, paperid INTEGER, email VARCHAR(100), significance INTEGER, PRIMARY KEY(id), FOREIGN KEY (paperid) REFERENCES paper(paperid), FOREIGN KEY (email) REFERENCES author(email));";
        createTable(createStatement);
        //table written has THREE attributes
        String[][] written = getFileContent(tablename, 3);
        if(written == null){
            System.out.print("Error: Written array empty\n");
            return 0;
        }
        else{
            for(int i=0; i<written.length; i++){
                try{
                    System.out.print(written[i][1]+"\n");
                    String sql = "INSERT INTO written(paperid, email, significance) VALUES("+written[i][0]+", "+written[i][1]+", "+written[i][2]+");";
                    System.out.print(sql+"\n");
                    PreparedStatement preparestatement = conn.prepareStatement(sql);
                    preparestatement.executeUpdate();
                } catch (SQLException e){
                    System.out.print("Encountered exception: "+e+"\n");
                    return 0;
                }
            }
            return 1;
        }
    }

    /**
     * This function creates and populates the table 'pcmember' in
     * the sampledb database by executing the appropriate SQL query
     * @return - Returns a 1 if the table was initialized, 0 otherwise.
     */
    public int initpcMember(){
        String tablename = "pcmember";
        String createStatement = "CREATE TABLE IF NOT EXISTS pcmember(memberid INTEGER NOT NULL AUTO_INCREMENT, email VARCHAR(100), name VARCHAR(20), PRIMARY KEY (memberid));";
        createTable(createStatement);

        String[][] pcmember = getFileContent(tablename, 2);
        if(pcmember == null){
            System.out.print("Error: Pcmember array empty\n");
            return 0;
        }
        else{
            for(int i=0; i<pcmember.length; i++){
                try{
                    String sql = "INSERT INTO pcmember(email, name) VALUES("+pcmember[i][1]+", "+pcmember[i][0]+");";
                    System.out.print(sql+"\n");
                    PreparedStatement preparestatement = conn.prepareStatement(sql);
                    preparestatement.executeUpdate();
                } catch (SQLException e){
                    System.out.print("Encountered exception: "+e+"\n");
                    return 0;
                }
            }
            return 1;
        }
    }

    /**
     * This function creates and populates the table 'review' in
     * the sampledb database by executing the appropriate SQL query
     * @return - Returns a 1 if the table was initialized, 0 otherwise.
     */
    public int initReview(){
        endConn();
        createConn("sampledb");
        String tablename = "review";
        String createStatement = "CREATE TABLE IF NOT EXISTS review(reportid INTEGER AUTO_INCREMENT, sdate DATE, comm VARCHAR(250), recommendation CHAR(1), paperid INTEGER NOT NULL, memberid INTEGER NOT NULL, PRIMARY KEY(reportid), FOREIGN KEY (paperid) REFERENCES paper(paperid), FOREIGN KEY (memberid) REFERENCES pcmember(memberid));";
        System.out.print(createStatement);
        System.out.print(createTable(createStatement));

        String[][] review = getFileContent(tablename, 6);
        if(review == null){
            System.out.print("Error: Review array empty\n");
            return 0;
        }
        else{
            for(int i=0; i<review.length; i++){
                try{
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                    java.util.Date date = df.parse(review[i][3]);
                    System.out.print("Date: "+date+"\n");
                    Date sqlDate =  new Date(date.getTime());
                    String sql = "INSERT INTO review VALUES("+review[i][2]+", '"+sqlDate.toString()+"', "+review[i][4]+", "+review[i][5]+", "+review[i][0]+", "+review[i][1]+");";
                    System.out.print(sql+"\n");
                    PreparedStatement preparestatement = conn.prepareStatement(sql);
                    preparestatement.executeUpdate();
                } catch (SQLException e){
                    System.out.print("Encountered exception: "+e+"\n");
                    return 0;
                } catch (ParseException e){
                    System.out.print("Encountered exception: "+e+"\n");
                }
            }
            return 1;
        }
    }

    /**
     * This function creates the sampledb database by
     * executing the appropriate "CREATE DATABASE" SQL query.
     * @return - Returns a 1 if the database was created, 0 otherwise.
     */
    public int createDatabase(){
        try{
            String sql = "CREATE DATABASE IF NOT EXISTS sampledb; USE sampledb;";
            PreparedStatement preparestatement = conn.prepareStatement(sql);
            preparestatement.executeUpdate();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This function deletes the sampledb database by
     * executing the appropriate "DROP DATABASE" SQL query.
     * @return - Returns a 1 if the database was dropped, 0 otherwise.
     */
    public int dropDatabase(){
        try{
            String sql = "DROP DATABASE IF EXISTS sampledb";
            PreparedStatement preparestatement = conn.prepareStatement(sql);
            preparestatement.executeUpdate();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This function creates and populates a table  in
     * the sampledb database by executing the appropriate SQL query
     * @param createStatement - the SQL query for creating a new table in the database
     * @return - Returns a 1 if the table was creates, 0 otherwise.
     */
    public int createTable(String createStatement){
        try{
            PreparedStatement preparestatement = conn.prepareStatement(createStatement);
            preparestatement.executeUpdate();
            return 1;
        } catch (Exception e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This method assigns pcmembers to review a paper given the pcmember's email(s).
     * @param paperid - The paper's paperid to be reviewed.
     * @param email - The pcmember's email address.
     * @return - Returns a 1 if the paper was assigned, 0 if otherwise.
     */
    public int assign(String paperid, String[] email){
        createConn("sampledb");
        //System.out.print(email.length+"\n");
        if (email.length-1 > 3){
            System.out.print("Exception Encountered: assigning over 3 reviewers to paper!");
            return 0;
        }

        String query1 = "SELECT paperid FROM review WHERE paperid = "+paperid+" AND paperid IN (SELECT paperid FROM review GROUP BY paperid HAVING COUNT(*)>=3);";
        try{
            PreparedStatement preparestatement = conn.prepareStatement(query1);
            ResultSet r = preparestatement.executeQuery();
            if (r.next()!= false){
                return 0;
            }

        } catch (Exception e){
            System.out.print("Exception Encountered (0): "+e+"\n");
            return 0;
        }


        String[] memberid = new String[email.length];
        // Get the memberid's associated with the emails
        for (int i = 0; i<email.length-1; i++){
            String query = "SELECT memberid FROM pcmember WHERE email = '"+email[i]+"' AND memberid NOT IN (SELECT memberid FROM review GROUP BY memberid HAVING COUNT(*) = 5);";
            try{
                PreparedStatement preparestatement = conn.prepareStatement(query);
                ResultSet r = preparestatement.executeQuery();
                if (r.next() == false){
                    memberid[i]=null;
                }
                else{
                    memberid[i]=r.getString("memberid");
                }

            } catch (Exception e){
                System.out.print("Exception Encountered (1): "+e+"\n");
                return 0;
            }

        }

        for (int i=0; i<memberid.length-1; i++) // for each email
        {
            if (memberid[i]!=null){
                String sql = "INSERT INTO review (paperid,memberid) VALUES ("+paperid+", "+memberid[i]+");";
                System.out.print(sql+"\n");

                try{
                    PreparedStatement preparestatement = conn.prepareStatement(sql);
                    preparestatement.executeUpdate();
                } catch (SQLException e){
                    System.out.print("Exception Encountered (2): "+e+"\n");
                } catch (Exception e){
                    System.out.print("Exception Encountered: "+e+"\n");
                }
            }
        }

        return 1;
    }

    /**
     * This method updates a row in the 'paper' table in the sampledb database.
     * @param paperid - The  paper's Paper ID
     * @param title - The paper's title
     * @param abstract1 - The paper's abstract
     * @param pdf - The paper's pdf
     * @return - Returns a 1 if the table 'paper' was updated, 0 otherwise.
     */
    public int updatePaper(String paperid, String title, String abstract1, String pdf){
        createConn("sampledb");
        String sql = "UPDATE paper SET title='"+title+"', abstract='"+abstract1+"', pdf='"+pdf+"' WHERE paperid='"+paperid+"';";

        try{
            PreparedStatement preparestatement = conn.prepareStatement(sql);
            preparestatement.executeUpdate();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        } catch (Exception e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This method deletes a row in the 'paper' table in the sampledb database.
     * @param paperid - The  paper's Paper ID
     * @param title - The paper's title
     * @param abstract1 - The paper's abstract
     * @param pdf - The paper's pdf
     * @return - Returns a 1 if the table 'paper' was deleted, 0 otherwise.
     */
    public int deletePaper(String paperid, String title, String abstract1, String pdf){
        createConn("sampledb");
        String sql2 = "DELETE FROM written WHERE paperid='"+paperid+"';";
        String sql3 = "DELETE FROM review WHERE paperid='"+paperid+"';";
        String sql = "DELETE FROM paper WHERE paperid='"+paperid+"';";
        try{
            PreparedStatement preparestatement3 = conn.prepareStatement(sql3);
            preparestatement3.executeUpdate();

            PreparedStatement preparestatement2 = conn.prepareStatement(sql2);
            preparestatement2.executeUpdate();

            PreparedStatement preparestatement = conn.prepareStatement(sql);
            preparestatement.executeUpdate();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        } catch (Exception e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This method adds a row in the 'paper' table in the sampledb database.
     * @param title - The paper's title
     * @param abstract1 - The paper's abstract
     * @param pdf - The paper's pdf
     * @return - Returns a 1 if the paper was added, 0 otherwise.
     */
    public int addPaper(String title, String abstract1, String pdf){
        createConn("sampledb");
        String sql = "INSERT INTO paper (title, abstract, pdf) VALUES('"+title+"','"+abstract1+"','"+pdf+"')";

        try{
            PreparedStatement preparestatement = conn.prepareStatement(sql);
            preparestatement.executeUpdate();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        } catch (Exception e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This method updates a row in the 'pcmember' table in the sampledb database.
     * @param memberid - The  pcmember's member ID
     * @param email - The pcmember's email address
     * @param name - The pcmember's name
     * @return - Returns a 1 if the table 'pcmember' was updated, 0 otherwise.
     */
    public int updatePCMember(String memberid, String email, String name){
        createConn("sampledb");
        String sql = "UPDATE pcmember SET email='"+email+"', name='"+name+"' WHERE memberid='"+memberid+"';";

        try{
            PreparedStatement preparestatement = conn.prepareStatement(sql);
            preparestatement.executeUpdate();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        } catch (Exception e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This method deletes a row in the 'pcmember' table in the sampledb database.
     * @param memberid - The  pcmember's member ID
     * @param email - The pcmember's email address
     * @param name - The pcmember's name
     * @return - Returns a 1 if the a pcmember was updated, 0 otherwise.
     */
    public int deletePCMember(String memberid, String email, String name){
        createConn("sampledb");
        String sql0 = "DELETE FROM author WHERE email='"+email+"';";
        String sql2 = "DELETE FROM written WHERE email='"+email+"';";
        String sql3 = "DELETE FROM review WHERE  memberid= (SELECT memberid FROM pcmember WHERE email = '"+email+"');";
        String sql = "DELETE FROM pcmember WHERE email='"+email+"';";
        try{
            PreparedStatement preparestatement2 = conn.prepareStatement(sql2);

            preparestatement2.executeUpdate();

            PreparedStatement preparestatement0 = conn.prepareStatement(sql0);

            preparestatement0.executeUpdate();

            PreparedStatement preparestatement3 = conn.prepareStatement(sql3);

            preparestatement3.executeUpdate();

            PreparedStatement preparestatement = conn.prepareStatement(sql);

            preparestatement.executeUpdate();

            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        } catch (Exception e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }

    }

    /**
     * This method adds a row in the 'pcmember' table in the sampledb database.
     * @param email - The pcmember's email address
     * @param name - The pcmember's name
     * @return - Returns a 1 if the a pcmember was added, 0 otherwise.
     */
    public int addPCMember(String email, String name){
        createConn("sampledb");
        String sql = "INSERT INTO pcmember (email,name) VALUES('"+email+"','"+name+"')";

        try{
            PreparedStatement preparestatement = conn.prepareStatement(sql);
            preparestatement.executeUpdate();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        } catch (Exception e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This function will update a review report in the table 'review'
     * @param reportid - The review report's report id.
     * @param sdate - The review report's date.
     * @param comm - The review report's comment.
     * @param recommendation - The review report's recommendation.
     * @param paperid - The review report's paperid.
     * @param memberid - The review report's memberid.
     * @return - Returns a 1 if 'review' was successfully updated, 0 otherwise.
     */
    public int updateReview(String reportid, String sdate, String comm, String recommendation, String paperid, String memberid){
        createConn("sampledb");
        String sql = "UPDATE review SET sdate='"+sdate+"', comm='"+comm+"', recommendation='"+recommendation+"',  paperid='"+paperid+"', memberid='"+memberid+"' WHERE reportid='"+reportid+"';";

        try{
            PreparedStatement preparestatement = conn.prepareStatement(sql);
            preparestatement.executeUpdate();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        } catch (Exception e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }

    }

    /**
     * This method deletes a new row to the review table
     * @param reportid - The review report's id.
     * @param sdate - The review report's date
     * @param comm - The review reports comment
     * @param recommendation - The review reports recommendation
     * @param paperid - The review report's paperid
     * @param memberid - The review report's memberid
     * @return - Returns a 1 if a review was successfully deleted, 0 otherwise.
     */
    public int deleteReview(String reportid, String sdate, String comm, String recommendation, String paperid, String memberid){
        createConn("sampledb");
        String sql = "DELETE FROM review WHERE reportid='"+reportid+"';";

        try{
            PreparedStatement preparestatement = conn.prepareStatement(sql);
            preparestatement.executeUpdate();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        } catch (Exception e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }

    }

    /**
     * This method adds a new row to the review table
     * @param sdate - The review report's date
     * @param comm - The review reports comment
     * @param recommendation - The review reports recommendation
     * @param paperid - The review report's paperid
     * @param memberid - The review report's memberid
     * @return - Returns a 1 if a review was successfully added, 0 otherwise.
     */
    public int addReview(String sdate, String comm, String recommendation, String paperid, String memberid){
        createConn("sampledb");
        String sql = "INSERT INTO review (sdate,comm,recommendation,paperid,memberid) VALUES('"+sdate+"','"+comm+"', '"+recommendation+"', '"+paperid+"', '"+memberid+"')";

        try{
            PreparedStatement preparestatement = conn.prepareStatement(sql);
            preparestatement.executeUpdate();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        } catch (Exception e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This method creates a trigger which restricts
     * pcmembers to review no more than 5 assignments.
     * @return - Returns a 1 if the trigger was created successfully, 0 otherwise.
     */
    public int createTrigger(){
        try{
            String trigger = "CREATE TRIGGER max5papers BEFORE INSERT ON review FOR EACH ROW BEGIN IF new.email IN (SELECT * FROM pcmember P WHERE 5 = (SELECT COUNT(*) FROM review R WHERE R.email = P.email)) THEN SIGNAL SQLSTATE'45000'; END;";
            System.out.print(trigger+"\n");
            PreparedStatement preparestatement = conn.prepareStatement(trigger);
            preparestatement.execute();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This method creates a view for the accepted papers
     * @return - Returns a 1 if the view was created successfully, 0 otherwise.
     */
    public int createView(){
        try{
            String view = "CREATE VIEW acceptedPaper AS SELECT paperid, title FROM paper WHERE paperid IN (SELECT paperid FROM review r1 WHERE r1.recommendation='a' GROUP BY paperid HAVING COUNT(*)>=2);";
            System.out.print(view+"\n");
            PreparedStatement preparestatement = conn.prepareStatement(view);
            preparestatement.execute();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    /**
     * This method initialized the 'sampledb' database,
     * creates a connection, creates a database, and populates all tables.
     * @return
     */
    public int initDatabase(){
        // Establish a new connection
        if (createConn("") == 0){
            System.out.print("Connection could not be established.\n");
            return 0;
        }
        System.out.print("Connection successfully established.\n");

        // Try dropping the database, if it exists
        if (dropDatabase()==1)
            System.out.print("Database Dropped.\n");
        else{
            System.out.print("Database could not be initially dropped.\n");
            return 0;
        }

        // Create the database 'sampledb'
        if (createDatabase() == 0){
            System.out.print("Database could not be created.\n");
            return 0;
        }
        System.out.print("Database successfully created.\n");

        // Connect to database
        endConn();
        if (createConn("sampledb") == 0){
            System.out.print("Connection could not be established.\n");
            return 0;
        }
        System.out.print("Successfully connected to sampledb.\n");

        // Initialize each table individually
        if (initPaper()==0 || initAuthor()==0 ||
                initWritten()==0 || initpcMember()==0 || initReview()==0){
            System.out.print("One or more tables could not be created. Dropping Database.\n");
            if (dropDatabase()==1){
                System.out.print("Database Dropped.\n");
                //add error checking
                createDatabase();
                return 0;
            }
            System.out.print("Database could not be dropped.\n");
            return 0;
        }
        System.out.print("Tables succesfully created.\n");

        if(createTrigger() == 0)
            System.out.print("Trigger could not be created.\n");
        else
            System.out.print("Trigger successfully created.\n");

        if(createView() == 0)
            System.out.print("View could not be created.\n");
        else
            System.out.print("View successfully created.\n");

        return 1;
    }

    /**
     * This function tests the connection to the Init
     * class by returning a statement to the calling method
     * @return - A test string
     */
    public String test(){
        return "I'm working";
    }
}