/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geocity;

import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public class GeoCity {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/?user=root";
   
   //  Database credentials
   static final String USER = "root";
   static final String PASS = "seecs@123";
   public static void main(String[] args) {
   Connection conn = null;
   PreparedStatement  stmt = null;
       int  databaseLimit = 0;
       String first = new String();
       String last = new String();

   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/geocity?" + "user=sohaib&password=seecs@123");


      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      String prepare = "INSERT INTO cities Values(?,'?','?','?','?','?','?','?','?');";

      stmt = conn.prepareStatement(prepare);
      Statement stmte = conn.createStatement();
      String sql = "CREATE TABLE IF NOT EXISTS`cities` (\n" +
"  `locId` int(11) NOT NULL,\n" +
"  `country` varchar(45) DEFAULT NULL,\n" +
"  `region` varchar(45) DEFAULT NULL,\n" +
"  `city` varchar(45) DEFAULT NULL,\n" +
"  `postalCode` varchar(45) DEFAULT NULL,\n" +
"  `latitude` varchar(45) DEFAULT NULL,\n" +
"  `longitude` varchar(45) DEFAULT NULL,\n" +
"  `metroCode` varchar(45) DEFAULT NULL,\n" +
"  `areaCode` varchar(45) DEFAULT NULL,\n" +
"  PRIMARY KEY (`locId`)\n" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
   
      stmte.executeUpdate(sql);
                String splitBy = ",";
        BufferedReader read = new BufferedReader(new FileReader("GeoLiteCity-Location.csv"));
					String line = null;
					while ((line = read.readLine()) != null) {
					String[] items= line.split(",",-1);
                                        for(int i = 0;i<9;i++){
                                        if(items[i].length() == 0){
                                        items[i] = "0";
                                        }
                                        }
                                        
  String insertSql = "INSERT INTO cities Values(" + items[0] + "," +items[1]+" ," +items[2]+"," +items[3]+"," +items[4]+",'" +items[5]+"','" +items[6]+"','" +items[7]+"','" +items[7]+"');";

        stmt.executeUpdate(insertSql);
        
databaseLimit++;

    
                System.out.println(databaseLimit);

                                               if(databaseLimit>300){
                                               break;
                                               } 
                                        }		
                         System.out.println("Input City");

Scanner newInput = new Scanner(System.in);
String inputStatement = newInput.nextLine();

String readSql = "SELECT latitude,longitude FROM cities Where city = \"" + inputStatement + "\";";
ResultSet rs = stmte.executeQuery(readSql);
                     while(rs.next()){
         //Retrieve by column name
          first = rs.getString("latitude");
          last = rs.getString("longitude");

         //Display values
         System.out.println("Requested Latitude is " + first);
         System.out.println("Requested Longitude is " + last);
      }
      float   newLat = (Float.parseFloat(first) + 20);
      float newLon = (Float.parseFloat(last) + 20);
      
      
      
      float oldLat = (Float.parseFloat(first) - 20);
      float oldLon = (Float.parseFloat(last) - 20);

      String sqloldLat = Float.toString(oldLat);
      String sqloldLon = Float.toString(oldLon);

      
      String sqlLat = Float.toString(newLat);
      String sqlLon = Float.toString(newLon);

      
      String distance = "SELECT city FROM cities  where latitude between " + sqloldLat + " and " + sqlLat + " and longitude between " +sqloldLon + " and " +sqlLon +";"; 
      ResultSet rs2 = stmte.executeQuery(distance);
               System.out.println("Nearby Cities are: ");

        while(rs2.next()){
         //Retrieve by column name
          String abc = rs2.getString("city");

         //Display values
         System.out.println(abc);
      }
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
}//end main
}//end FirstExample
