import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.expedia.entities.BigOffer;
import com.expedia.entities.Hotel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {

  public static void main(String[] args) 
  {
	StringBuilder sb = new StringBuilder();
    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    get("/hello", (req, res) -> 
    {
    	HttpURLConnection conn = null;
    	try 
    	{
    		URL url= null;
    		JFrame frame = new JFrame("jFrame");
    		String cityName = JOptionPane.showInputDialog(frame, "What's your destination city?  eg. Budapest");
    		if (cityName != null && !cityName.trim().isEmpty())
    		{
    			url = new URL("https://offersvc.expedia.com/offers/v2/getOffers?scenario=deal-finder&page=foo&uid=foo&productType=Hotel&destinationCity="+ cityName);
    		}
    		else
    		{
    			url = new URL("https://offersvc.expedia.com/offers/v2/getOffers?scenario=deal-finder&page=foo&uid=foo&productType=Hotel");
    		}
    		conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("GET");
    		conn.setRequestProperty("Accept", "application/json");
    		if (conn.getResponseCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
    		}
    		
    		BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    		StringBuilder sBuilder = new StringBuilder();
            String line;
            while ((line = bReader.readLine()) != null) {
            	sBuilder.append(line+"\n");
            }
            bReader.close();
            
            
    		Gson gson = new GsonBuilder().create();
    		BigOffer bigOffer = gson.fromJson(sBuilder.toString(), BigOffer.class);
    		
    		displayOffers(bigOffer, sb);
    		return sb.toString(); 		
    	} 
    	catch (MalformedURLException e1)
    	{
    		e1.printStackTrace();
    	} 
    	catch (IOException e) 
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		conn.disconnect();
    	}
    	
    	return "Something went wrong!";
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
  
  private static void displayOffers(BigOffer bigOffer, StringBuilder sb)
  {
	  Hotel[] hotels = bigOffer.getOffers().getHotel();
	  if (hotels != null)
	  {
	  for (int i = 0; i < hotels.length; i++) 
	  {
		  Hotel hotel = hotels[i];
		  int offerNo = i + 1;
		  sb.append("<h3><b><u>Offer " + offerNo + "</u></b></h3>");
		  displayOfferDateRange(hotel,sb);
		  displayDestination(hotel, sb);
		  displayHotelInfo(hotel, sb);
		  displayHotelUrgencyInfo(hotel, sb);
		  displayHotelPricingInfo(hotel, sb);
		  displayHotelUrls(hotel, sb);
		  displayHotelScores(hotel, sb);
		  sb.append("<b><hr><hr></b>");
	  }
	  }
	  else
	  {
		  sb.append("Sorry.. No Offers");
	  }
  }
  private static void displayHotelScores(Hotel hotel, StringBuilder sb)
  {
	  if (hotel.getHotelScores() != null)
	  {
		  sb.append("<br/><br/><b>Hotel Scores</b><br/>");
		  sb.append("Raw Appeal Score : " + hotel.getHotelScores().getRawAppealScore() + "<br/>");
		  sb.append("Moving Average Score : " + hotel.getHotelScores().getMovingAverageScore() + "<br/>");
	  }
  }
  private static void displayHotelUrls(Hotel hotel, StringBuilder sb)
  {
	  if (hotel.getHotelUrls() != null)
	  {
		  sb.append("<br/><br/><b>Hotel Urls</b><br/>");
		  sb.append("Hotel Info Site Url : " + hotel.getHotelUrls().getHotelInfositeUrl() + "<br/>");
		  sb.append("Hotel Search Result Url : " + hotel.getHotelUrls().getHotelSearchResultUrl() + "<br/>");
	  }
  }
  private static void displayHotelPricingInfo(Hotel hotel, StringBuilder sb)
  {
	  if (hotel.getHotelPricingInfo() != null)
	  {
		  sb.append("<br/><br/><b>Hotel Pricing Info</b><br/>");
		  sb.append("Average Price Value : " + hotel.getHotelPricingInfo().getAveragePriceValue() + "<br/>");
		  sb.append("Total Price Value : " + hotel.getHotelPricingInfo().getTotalPriceValue() + "<br/>");
		  sb.append("Original Price Per Night : " + hotel.getHotelPricingInfo().getOriginalPricePerNight() + "<br/>");
		  sb.append("Hotel Total Base Rate : " + hotel.getHotelPricingInfo().getHotelTotalBaseRate() + "<br/>");
		  sb.append("Hotel Total Taxes and Fees : " + hotel.getHotelPricingInfo().getHotelTotalTaxesAndFees() + "<br/>");
		  sb.append("Currency : " + hotel.getHotelPricingInfo().getCurrency() + "<br/>");
		  sb.append("Hotel Total Mandatory Taxes and Fees : " + hotel.getHotelPricingInfo().getHotelTotalMandatoryTaxesAndFees() + "<br/>");
		  sb.append("Percent Savings : " + hotel.getHotelPricingInfo().getPercentSavings() + "<br/>");
		  sb.append("Drr : " + hotel.getHotelPricingInfo().isDrr() + "<br/>");
	  }
  }
  
  private static void displayHotelUrgencyInfo(Hotel hotel, StringBuilder sb)
  {
	  if (hotel.getHotelUrgencyInfo() != null )
	  {
		  sb.append("<br/><br/><b>Hotel Urgency Info</b><br/>");
		  sb.append("Air Attach Remaining Time : " + hotel.getHotelUrgencyInfo().getAirAttachRemainingTime() + "<br/>");
		  sb.append("Number of People Viewing : " + hotel.getHotelUrgencyInfo().getNumberOfPeopleViewing() + "<br/>");
		  sb.append("Number of People Booked : " + hotel.getHotelUrgencyInfo().getNumberOfPeopleBooked() + "<br/>");
		  sb.append("Number of Rooms Left : " + hotel.getHotelUrgencyInfo().getNumberOfRoomsLeft() + "<br/>");
		  sb.append("Last Booked Time : " + hotel.getHotelUrgencyInfo().getLastBookedTime() + "<br/>");
		  sb.append("Almost Sold Status : " + hotel.getHotelUrgencyInfo().getAlmostSoldStatus() + "<br/>");
		  sb.append("Link : " + hotel.getHotelUrgencyInfo().getLink() + "<br/>");
		  String[] almostSoldOutRoomTypeInfoCollection = hotel.getHotelUrgencyInfo().getAlmostSoldOutRoomTypeInfoCollection();
		  List<String> asList = Arrays.asList(almostSoldOutRoomTypeInfoCollection);
		  sb.append("Almost Sold Out Room Type Info Collection : " + asList + "<br/>");
		  sb.append("Air Attach Enabled : " + hotel.getHotelUrgencyInfo().isAirAttachEnabled() + "<br/>");
	  }
  }
  
  private static void displayHotelInfo(Hotel hotel, StringBuilder sb)
  {
	  if (hotel.getHotelInfo() != null)
	  {
		  sb.append("<br/><br/><b>Hotel Info</b><br/>");
		  sb.append("Hotel ID : " + hotel.getHotelInfo().getHotelId() + "<br/>");
		  sb.append("Hotel Name : " + hotel.getHotelInfo().getHotelName() + "<br/>");
		  sb.append("Hotel Destination : " + hotel.getHotelInfo().getHotelDestination() + "<br/>");
		  sb.append("Hotel Destination Region ID : " + hotel.getHotelInfo().getHotelDestinationRegionID() + "<br/>");
		  sb.append("Hotel Long Destination : " + hotel.getHotelInfo().getHotelLongDestination() + "<br/>");
		  sb.append("Hotel City : " + hotel.getHotelInfo().getHotelCity() + "<br/>");
		  sb.append("Hotel Province : " + hotel.getHotelInfo().getHotelProvince() + "<br/>");
		  sb.append("Hotel Country Code : " + hotel.getHotelInfo().getHotelCountryCode() + "<br/>");
		  sb.append("Hotel Location : " + hotel.getHotelInfo().getHotelLocation() + "<br/>");
		  sb.append("Hotel Latitude : " + hotel.getHotelInfo().getHotelLatitude() + "<br/>");
		  sb.append("Hotel Longitude : " + hotel.getHotelInfo().getHotelLongitude() + "<br/>");
		  sb.append("Hotel Star Rating : " + hotel.getHotelInfo().getHotelStarRating() + "<br/>");
		  sb.append("Hotel Guest Review Rating : " + hotel.getHotelInfo().getHotelGuestReviewRating() + "<br/>");
		  sb.append("Travel Start Date : " + hotel.getHotelInfo().getTravelStartDate() + "<br/>");
		  sb.append("TravelEndDate : " + hotel.getHotelInfo().getTravelEndDate() + "<br/>");
		  sb.append("Hotel Image Url : " + hotel.getHotelInfo().getHotelImageUrl() + "<br/>");
		  sb.append("Car Package Score : " + hotel.getHotelInfo().getCarPackageScore() + "<br/>");
		  sb.append("Description : " + hotel.getHotelInfo().getDescription() + "<br/>");
		  sb.append("Distance From User : " + hotel.getHotelInfo().getDistanceFromUser() + "<br/>");
		  sb.append("Language : " + hotel.getHotelInfo().getLanguage() + "<br/>");
		  sb.append("Moving Average Score : " + hotel.getHotelInfo().getMovingAverageScore() + "<br/>");
		  sb.append("Promotion Amount : " + hotel.getHotelInfo().getPromotionAmount() + "<br/>");
		  sb.append("Promotion Description : " + hotel.getHotelInfo().getPromotionDescription() + "<br/>");
		  sb.append("Promotion Tag : " + hotel.getHotelInfo().getPromotionTag() + "<br/>");
		  sb.append("Raw Appeal Score : " + hotel.getHotelInfo().getRawAppealScore() + "<br/>");
		  sb.append("Relevance Score : " + hotel.getHotelInfo().getRelevanceScore() + "<br/>");
		  sb.append("Status Code : " + hotel.getHotelInfo().getStatusCode() + "<br/>");
		  sb.append("Status Description : " + hotel.getHotelInfo().getStatusDescription() + "<br/>");
		  sb.append("Car Package : " + hotel.getHotelInfo().isCarPackage() + "<br/>");
		  sb.append("All Inclusive : " + hotel.getHotelInfo().isAllInclusive() + "<br/>");
	  }
  }
  
  private static void displayDestination(Hotel hotel, StringBuilder sb)
  {
	  if (hotel.getDestination() != null)
	  {
		sb.append("<br/><b>Destination</b><br/>");
		sb.append("Region ID : " + hotel.getDestination().getRegionID() + "<br/>");
		sb.append("Long Name : " + hotel.getDestination().getLongName() + "<br/>");
		sb.append("Country : " + hotel.getDestination().getCountry() + "<br/>");
		sb.append("Province : " + hotel.getDestination().getProvince() + "<br/>");
		sb.append("City : " + hotel.getDestination().getCity());
	  }
  }
  private static void displayOfferDateRange(Hotel hotel, StringBuilder sb)
  {
	  if (hotel.getOfferDateRange() != null)
	  {
		sb.append("<b>Offer Date Range</b><br/>");
		sb.append("Travel Start Date : " + dateArrayValuesToString(hotel.getOfferDateRange().getTravelStartDate()) + "<br/>");
		sb.append("Travel End Date : " + dateArrayValuesToString(hotel.getOfferDateRange().getTravelEndDate()) + "<br/>");
		sb.append("lengthOfStay : " + hotel.getOfferDateRange().getLengthOfStay() + "<br/>");
	  }
  }
  
  private static String dateArrayValuesToString(int[] array)
  {
	  StringBuilder s = new StringBuilder();
	  for (int i = 0; i < array.length ; i++) 
	  {
		  s.append(array[i]);
		  if (array[i] != array[array.length - 1])
		  {
			  s.append("/");
		  }
	  }
	  return s.toString();
  }

}
