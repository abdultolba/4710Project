package myPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;



//import com.opencsv.CSVReader; //b

import java.io.File; //b
import java.io.FileNotFoundException;
import java.io.FileReader; //b
import java.io.IOException; //b
import java.io.BufferedReader;



public class Init {
    //private class variable for connection to MySQL database
    private Connection conn;

    //function for creating connection to MySQL database
    public int createConn(String dbName){
        // Input: string containing the name of the database we want to connect to
        // Output: 1 if connection is successful, 0 if not successful
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName+"?allowMultiQueries=true&user=root&password=1234");
        } catch (Exception e){
            System.out.print("Error occurred: "+e+"\n");
            return 0;
        }
        return 1;
    }

    //function for ending connection to MySQL database
    public int endConn(){
        //inputs: none
        //outputs: 1 if successful, 0 if unsuccessful
        try{
            conn.close();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }

    //function for reading comma separated values from a file and storing them in
    //a multi-dimensional array
    public String[][] getFileContent(String tablename, int numAttributes){
        //inputs: the name of the MySQL table we are pulling in values for, the number of attributes associated with the table
        //output: a multi-dimensional array containing the values which were cotained in the CSV file
        System.out.print("Working on table "+tablename+"in getFileContent.\n");
        try{
            int size = 10;
            if (tablename == "paper"){
                size = 13;
            }
            if (tablename == "author"){
                size = 12;
            }
            if (tablename == "written"){
                size = 14;
            }
            if (tablename == "pcmember"){
                size = 14;
            }
            if (tablename == "review"){
                size = 12;
            }
            //read all with BufferedReader
            File myFile = new File(getClass().getResource(tablename+".csv").toURI());
            String[][] content = new String[size][numAttributes];
            String row = "";
            int i = 0;
            BufferedReader csv = new BufferedReader(new FileReader(myFile));


            while ((row = csv.readLine())!=null && i<size){
                content[i] = row.split(",");
                System.out.print(content[i][0]+"\n");
                i++;
            }
            return content;

        } catch(Exception e){
            System.out.print("Error encountered: "+e+"\n");
            return null;
        }
    }

    //function for creating and populating the paper table for the sampledb database
    public int initPaper(){
        //inputs: none
        //outputs: 1 if successful, 0 if unsuccessful
        String tablename = "paper";
        String createStatement = "CREATE TABLE IF NOT EXISTS paper(paperid INTEGER AUTO_INCREMENT, title VARCHAR(50), abstract VARCHAR(250), pdf VARCHAR(100), PRIMARY KEY (paperid));";
        //create table
        createTable(createStatement);
        //table paper has FOUR attributes
        String[][] paper = getFileContent(tablename, 4);
        System.out.print("PAPER: "+paper);
        if(paper == null){
            System.out.print("Error: Paper array empty\n");
            return 0;
        }
        else{
            for(int i=0; i<paper.length; i++){
                System.out.print("i="+i+"\n");
                System.out.print(paper[i][0]);
                try{
                    String sql = "INSERT INTO paper VALUES("+paper[i][0]+", '"+paper[i][1]+"', '"+paper[i][2]+"', '"+paper[i][3]+"');";
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

    //function for creating and populating the author table for the sampledb database
    public int initAuthor(){
        //inputs: none
        //outputs: 1 if successful, 0 if unsuccessful
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

    //function for creating and populating the written table for the sampledb database
    public int initWritten(){
        //inputs: none
        //outputs: 1 if successful, 0 if unsuccessful
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

    //function for creating and populating the pcmember table for the sampledb database
    public int initpcMember(){
        //inputs: none
        //outputs: 1 if successful, 0 if unsuccessful
        String tablename = "pcmember";
        String createStatement = "CREATE TABLE IF NOT EXISTS pcmember(memberid INTEGER NOT NULL AUTO_INCREMENT, email VARCHAR(100), name VARCHAR(20), PRIMARY KEY (memberid));";
        createTable(createStatement);
        //table pcMember has TWO attributes
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

    //function for creating and populating the review table for the sampledb database
    public int initReview(){
        //inputs: none
        //outputs: 1 if successful, 0 if unsuccessful
        endConn();
        createConn("sampledb");
        String tablename = "review";
        String createStatement = "CREATE TABLE IF NOT EXISTS review(reportid INTEGER AUTO_INCREMENT, sdate DATE, comm VARCHAR(250), recommendation CHAR(1), paperid INTEGER NOT NULL, memberid INTEGER NOT NULL, PRIMARY KEY(reportid), FOREIGN KEY (paperid) REFERENCES paper(paperid), FOREIGN KEY (memberid) REFERENCES pcmember(memberid));";
        System.out.print(createStatement);
        System.out.print(createTable(createStatement));
        //table review has SIX attributes
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

    //function for creating the sampledb database
    public int createDatabase(){
        //inputs: none
        //outputs: 1 if successful, 0 if unsuccessful
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

    //function for dropping the sampledb database
    public int dropDatabase(){
        //inputs: none
        //outputs: 1 if successful, 0 if unsuccessful
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

    //function for creating a table given a valid sql statement
    public int createTable(String createStatement){
        //input: a valid sql statement
        //output: 1 if successful, 0 if unsuccessful
        try{
            PreparedStatement preparestatement = conn.prepareStatement(createStatement);
            preparestatement.executeUpdate();
            return 1;
        } catch (Exception e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }


    // start beata
    //function for assigning pcmembers to review a paper given the pcmembers' email(s)
    public int assign(String paperid, String[] email){
        //input: string containing the paperid of the paper to be reviewed, array of string containing the email
        //addresses of pcmembers to be assigned
        //output: 1 if successful, 0 if unsuccessful
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
        //get memberids associated with emails
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




    // end beata
    //function for updating a row belonging to the paper table in the sampledb database
    public int updatePaper(String paperid, String title, String abstract1, String pdf){
        //inputs: strings containing the paperid, titile, abstract, and pdf of the row to be updated
        //output: 1 if successful, 0 if unsuccessful
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

    //function for deleting a row belongig to the paper table in the sampledb database
    public int deletePaper(String paperid, String title, String abstract1, String pdf){
        //inputs: strings containing the paperid, title, abstract, and pdf of row to be deleted
        //ouputs: 1 if successful, 0 if unsuccessful
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

    //function for adding a new row to the paper table in the sampledb database
    public int addPaper(String title, String abstract1, String pdf){
        //inputs: strings containing the title, abstract, and pdf of the new row
        //ouputs: 1 if successful, 0 if unsuccessful
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

    //function for updating a row of the pcmember table in the sampledb database
    public int updatePCMember(String memberid, String email, String name){
        //inputs: strings containing the memberid, email, and name of the row to be updated
        //ouputs: 1 if successful, 0 if unsuccessful
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

    //function for deleting a row of the pcmember table in the sampledb database
    public int deletePCMember(String memberid, String email, String name){
        //strings containing the memberid, email, and name associated with the
        //row to be deleted
        //outputs: 1 if successful, 0 if unsuccessful
        createConn("sampledb");
        String sql0 = "DELETE FROM author WHERE email='"+email+"';";
        String sql2 = "DELETE FROM written WHERE email='"+email+"';";
        String sql3 = "DELETE FROM review WHERE  memberid= (SELECT memberid FROM pcmember WHERE email = '"+email+"');";
        String sql = "DELETE FROM pcmember WHERE email='"+email+"';";
        try{
            PreparedStatement preparestatement0 = conn.prepareStatement(sql0);

            preparestatement0.executeUpdate();

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

    //function for adding a new row to the pcmember table in the sampledb database
    public int addPCMember(String email, String name){
        //inputs: strings containing the email and name of the new pc member to be added
        //outputs: 1 if successful, 0 if unsuccessful
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

    //function for updating a row of the review table in the sampledb database
    public int updateReview(String reportid, String sdate, String comm, String recommendation, String paperid, String memberid){
        //inputs: strings containing the reportid, sdate, comm, recommendation, paperid, and memberid of the
        //row to be updated
        //outputs: 1 if successful, 0 if unsuccessful
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

    //function for deleting a row from the review table in the sampledb database
    public int deleteReview(String reportid, String sdate, String comm, String recommendation, String paperid, String memberid){
        //inputs: strings containing the reportid, sdate, comm, recommendation, paperid, and memberid of the
        //row to be deleted
        //outputs: 1 if successful, 0 if unsuccessful
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

    //function for adding a new row to the review table in the sampledb database
    public int addReview(String sdate, String comm, String recommendation, String paperid, String memberid){
        //inputs: strings containing the sdate, comm, recommendation, paperid, and memberid of the new row
        //to be added
        //outputs: 1 if successful, 0 if unsuccessful
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

    //function to create a trigger which restricts pcmembers to 5 review assignments
    public int createTrigger(){
        //inputs: none
        //outputs: 1 if successful, 0 if unsuccessful
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

    //function for creating a view which contains accepted papers
    public int createView(){
        //inputs: none
        //outputs: 1 if successful, 0 if unsuccessful
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

    //function for initializing the sampledb database
    //creates connection, creates database and populates all tables
    //creates trigger and view
    public int initDatabase(){
        //inputs: none
        //outputs: 1 if successful, 0 if unsuccessful


        //establish connection
        if (createConn("") == 0){
            System.out.print("Connection could not be established.\n");
            return 0;
        }
        System.out.print("Connection successfully established.\n");
        //try dropping database if it exists
        if (dropDatabase()==1){
            System.out.print("Database Dropped.\n");
        }
        else{
            System.out.print("Database could not be initially dropped.\n");
            return 0;
        }
        //create database
        if (createDatabase() == 0){
            System.out.print("Database could not be created.\n");
            return 0;
        }
        System.out.print("Database successfully created.\n");
        //connect to db specifically
        endConn();
        if (createConn("sampledb") == 0){
            System.out.print("Connection could not be established.\n");
            return 0;
        }
        System.out.print("Successfully connected to sampledb.\n");
        //initialize each table individually
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
        if(createTrigger() == 0){
            System.out.print("Trigger could not be created.\n");
        }
        else{
            System.out.print("Trigger successfully created.\n");
        }
        if(createView() == 0){
            System.out.print("View could not be created.\n");
        }
        else{
            System.out.print("View successfully created.\n");
        }
        return 1;
    }

    //function for testing connection to the class
    public String test(){
        //inputs: none
        //outputs: test string
        return "I'm working";
    }
}