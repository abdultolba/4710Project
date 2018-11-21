package myPackage;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDB {
    private java.sql.Connection conn;
    public int createConn(String dbName){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName+"?allowMultiQueries=true&user=john&password=pass1234");
        } catch (Exception e){
            System.out.print("Error occurred: "+e+"\n");
            return 0;
        }
        return 1;
    }
    public int endConn(){
        try{
            conn.close();
            return 1;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return 0;
        }
    }
    public int runSQLStatement(String sqlStatement){
        try{
            PreparedStatement preparestatement = conn.prepareStatement(sqlStatement);
            int result = preparestatement.executeUpdate();
            return result;
        } catch (SQLException e){
            System.out.print("Exception Encountered: "+e+"\n");
            return -1;
        }
    }
    public int insertPaper(String title, String abstr, String pdf){
        try{
            String sql = "INSERT INTO paper(title, abstract, pdf) VALUES("+title+", "+abstr+", "+pdf+");";
            runSQLStatement(sql);
            return 1;
        }catch(Exception e){
            return 0;
        }
    }
    public int deletePaper(String paperID){
        try{
            String sql = "DELETE FROM paper WHERE paperid = "+paperID;
            runSQLStatement(sql);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
    public int updatePaper(String title, String abstr, String pdf, String paperID){
        try{
            String sql = "UPDATE paper SET title = "+title+" abstract = "+abstr+" pdf = "+pdf+" WHERE paperid = "+paperID;
            runSQLStatement(sql);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
    public int insertPCMember(String email, String name){
        try{
            String sql = "INSERT INTO pcmember(email, name) VALUES("+email+", "+name+");";
            runSQLStatement(sql);
            return 1;
        }catch(Exception e){
            return 0;
        }

    }
    public int deletePCMember(String memberid){
        try{
            String sql = "DELETE FROM pcmemeber WHERE memberid = "+memberid;
            runSQLStatement(sql);
            return 1;
        }catch(Exception e){
            return 0;
        }
    }
    public int updatePCMember(String email, String name, String memberid){
        try{
            String sql = "UPDATE pcmember SET email = "+email+" name = "+name+" WHERE memberid = "+memberid;
            runSQLStatement(sql);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
    public int insertReview(String sdate, String comm, String recommendation, String paperid, String email){
        try{
            String sql = "INSERT INTO review(sdate, comm, recommendation, paperid, email) VALUES("+sdate+", "+comm+", "+recommendation+", "+paperid+", "+email+");";
            runSQLStatement(sql);
            return 1;
        }catch(Exception e){
            return 0;
        }
    }
    public int deleteReview(String reportid){
        try{
            String sql = "DELETE FROM review WHERE reportid = "+reportid;
            runSQLStatement(sql);
            return 1;
        }catch(Exception e){
            return 0;
        }
    }
    public int updateReview(String sdate, String comm, String recommendation, String paperid, String memberid, String reportid){
        try{
            String sql = "UPDATE pcmember SET sdate = "+sdate+" comm = "+comm+"recommendation = "+recommendation+" paperid = "+paperid+" memberid = "+memberid+" WHERE reportid = "+reportid;
            runSQLStatement(sql);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

}