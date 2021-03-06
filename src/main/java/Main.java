import com.heroku.sdk.jdbc.DatabaseUrl;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import java.net.URI;
import java.net.URISyntaxException;
import com.google.gson.Gson;
import org.json.JSONObject;
import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");
    
    Object r = new messageTest();


    post("/signup", (req,res)->{
        Connection con = null;
        try{
            con = DatabaseUrl.extract().getConnection();
            JSONObject obj = new JSONObject(req.body());
            String username = obj.getString("Username");
            String email = obj.getString("Email");
            String password = obj.getString("Password");
            
            String sql = "INSERT INTO Usr (name,email,passwd) VALUES('"+username+"', '"+email+"','"+password+"') ";
            con = DatabaseUrl.extract().getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Usr");
            stmt.executeUpdate(sql);
            
            return req.body();
            
        }catch(Exception e){
            return e.getMessage();
        }finally{
            
        }
    });


    //GET XML
      get("/api/moodblog", (req, res) -> {

          Connection connection = null;
          // res.type("application/xml"); //Return as XML
          Map<String, Object> attributes = new HashMap<>();
          
          try {
              //Connect to Database and execute SQL Query
              connection = DatabaseUrl.extract().getConnection();
              Statement stmt = connection.createStatement();
              ResultSet rs = stmt.executeQuery("SELECT * FROM moodblog");


              String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
              xml += "<moodblog>";
              while (rs.next()) {
                xml += "<moods>";
			xml += "<id>"+rs.getInt("id")+"</id>";
			xml += "<username>"+rs.getString("name")+"</username>";
                        xml += "<year>"+rs.getInt("year")+"</year>";
                        xml += "<month>"+rs.getInt("month")+"</month>";
                        xml += "<day>"+rs.getInt("day")+"</day>";
                        xml += "<weather>"+rs.getString("weather")+"</weather>";
                        xml += "<location>"+rs.getString("location")+"</location>";
                        xml += "<event>"+rs.getString("event")+"</event>";
                        xml += "<withWho>"+rs.getString("withWho")+"</withWho>";
                        xml += "<mood>"+rs.getString("mood")+"</mood>";
						
                xml += "</moods>";
              }
              xml += "</moodblog>";
              res.type("text/xml");
              return xml;

          } catch (Exception e) {
              attributes.put("message", "There was an error: " + e);
              return attributes;
          } finally {
              if (connection != null) 
                  try{
                      connection.close();
                  } 
                  catch(SQLException e){}
          }
        });//End api/moodblog
    

    get("/db", (req, res) -> {
      Connection connection = null;
      Map<String, Object> attributes = new HashMap<>();
      try {
        connection = getConnection();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Usr (id int,name varchar(50),email varchar(100),passwd varchar(50))");
        //stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
        
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS moodblog(id int,name varchar(50),year int,month int,day int,weather varchar(500),location varchar(100),event varchar(500),withWho varchar(500),mood varchar(500))");
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM moodblog");

        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          output.add( "Read from DB: " + rs.getString("name"));
          output.add( "Read from DB: " + rs.getString("mood"));
        }

        attributes.put("results", output);
        return new ModelAndView(attributes, "db.ftl");
      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e);
        return new ModelAndView(attributes, "error.ftl");
      } finally {
        if (connection != null) try{connection.close();} catch(SQLException e){}
      }
  }, new FreeMarkerEngine());

  }

  private static Connection getConnection() throws URISyntaxException, SQLException {
    URI dbUri = new URI(System.getenv("DATABASE_URL"));
    int port = dbUri.getPort();
    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + port + dbUri.getPath();

    if (dbUri.getUserInfo() != null) {
      String username = dbUri.getUserInfo().split(":")[0];
      String password = dbUri.getUserInfo().split(":")[1];
      return DriverManager.getConnection(dbUrl, username, password);
    } else {
      return DriverManager.getConnection(dbUrl);
    }
  }

}