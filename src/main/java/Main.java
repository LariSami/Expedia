import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static javax.measure.unit.SI.KILOGRAM;
import javax.measure.quantity.Mass;
import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;

import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

  public static void main(String[] args) 
  {
	
    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    get("/hello", (req, res) -> {
    	return "HELLO";
//    	try 
//    	{
//    		URL url = new URL("https://offersvc.expedia.com/offers/v2/getOffers?scenario=deal-finder&page=foo&uid=foo&productType=Hotel");
//    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//    		conn.setRequestMethod("GET");
//    		conn.setRequestProperty("Accept", "application/json");
//    		
//    		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//    		
//    		String output;
//    		System.out.println("Output from Server .... \n");
//    		while ((output = br.readLine()) != null) {
//    			output =  output;
//    		}
//
//    		conn.disconnect();
//    		return output;
//
//    	} 
//    	catch (MalformedURLException e1)
//    	{
//    		e1.printStackTrace();
//    	} 
//    	catch (IOException e) 
//    	{
//    		e.printStackTrace();
//    	}
//    	return "nop";
    	
//    	RelativisticModel.select();
//        Amount<Mass> m = Amount.valueOf("12 GeV").to(KILOGRAM);
//        return "E=mc^2: 12 GeV = " + m.toString();
      });

    get("/", (request, response) -> {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("message", "Hello World!");

        return new ModelAndView(attributes, "index.ftl");
    }, new FreeMarkerEngine());

    HikariConfig config = new  HikariConfig();
    config.setJdbcUrl(System.getenv("JDBC_DATABASE_URL"));
    final HikariDataSource dataSource = (config.getJdbcUrl() != null) ?
      new HikariDataSource(config) : new HikariDataSource();

    get("/db", (req, res) -> {
      Map<String, Object> attributes = new HashMap<>();
      try(Connection connection = dataSource.getConnection()) {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
        stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
        ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          output.add( "Read from DB: " + rs.getTimestamp("tick"));
        }

        attributes.put("results", output);
        return new ModelAndView(attributes, "db.ftl");
      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e);
        return new ModelAndView(attributes, "error.ftl");
      }
    }, new FreeMarkerEngine());

  }

}
