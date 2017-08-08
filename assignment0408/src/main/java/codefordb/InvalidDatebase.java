package codefordb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import applicationcontext.ApplicationContext;
import pojo.Record;

public class InvalidDatebase implements Runnable{
	
	//ValidDatabase vdb = new ValidDatabase();
	static final String JDBC_Driver = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/sakila?useSSL=false";
	static final String USER = "root";
	static final String PASS = "C@pgemin!";
	String tableName= ""; 
	   
	   public void run(){
		   //edit to use a method that adds values to an invalid table 
		   	   while(true){
			   addToDatabase(ApplicationContext.wh.invalidPop(), "invalid");
		   	   }
		   
	   }
	   public void addToDatabase(Record record, String validity){
		   Connection conn = null;
		   Statement stmt = null;
		   try{
			  PreparedStatement statement = null;
			  
		      //STEP 2: Register JDBC driver
		      Class.forName(JDBC_Driver);

		      //STEP 3: Open a connection
		      System.out.println("Connecting to a selected database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);

		      
		      //STEP 4: Execute a query
		      stmt = conn.createStatement();
		      
		      if(validity.equals("valid")){
		    	  tableName = "outputreference";
		      }
		      else{
		    	  tableName = "invalidoutputreference";
		      }
		      System.out.println("connected to " + tableName);
		      //specify table name in general method that is used to add items to the database  
		      statement = conn.prepareStatement("INSERT INTO "+tableName+"(RecordNo, FileName, date, RecordString) VALUES(?,?,?,?)");
		      statement.setInt(1, record.getRecordNo());
		      statement.setString(2, record.getFileName());
		      statement.setString(3, record.getDate());
		      statement.setString(4, record.getRecordString());
		      
		      statement.executeUpdate();
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
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Goodbye!"+tableName);
		}
	
}
