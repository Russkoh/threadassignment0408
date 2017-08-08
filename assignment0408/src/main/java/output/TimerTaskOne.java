package output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimerTask;

import applicationcontext.ApplicationContext;

public class TimerTaskOne extends TimerTask{

	static final String JDBC_Driver = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/sakila?useSSL=false";
	static final String USER = "root";
	static final String PASS = "C@pgemin!";
	
	public static void main(String[] args){
		ApplicationContext.init();
		TimerTask tm = new TimerTaskOne();
		tm.run();
		
	}
	@Override
	public void run() {
		
		for(int i = 0; i<ApplicationContext.listOfOutputFiles.size();i++){
			System.out.println("running "+ApplicationContext.listOfOutputFiles.size());
			if(outputExec(ApplicationContext.listOfOutputFiles.get(i))){
				   Connection conn = null;
				   Statement stmt = null;
				   BufferedWriter output = null;
				   try{
					  PreparedStatement statement = null;
					  
				      //STEP 2: Register JDBC driver
				      Class.forName(JDBC_Driver);

				      //STEP 3: Open a connection
				      System.out.println("Connecting to a selected database...");
				      conn = DriverManager.getConnection(DB_URL, USER, PASS);
				      
				      //STEP 4: Execute a query
				      stmt = conn.createStatement();
				      
				      String sql = "SELECT RecordNo, FileName, date, RecordString FROM outputreference";
				      ResultSet rs = stmt.executeQuery(sql);
				      
				      System.out.println("reached writing file ");
				         File file = new File("D:\\threadassignment\\Outputfolder\\validoutput.txt");
				         FileOutputStream is = new FileOutputStream(file);
				         OutputStreamWriter osw = new OutputStreamWriter(is);    
				         Writer w = new BufferedWriter(osw);
				      //STEP 5: Extract data from result set
				      while(rs.next()){
				         //Retrieve by column name
				         int recordNo  = rs.getInt("RecordNo");
				         String fileName = rs.getString("FileName");
				         String date = rs.getString("date");
				         String recordString = rs.getString("RecordString");

				         //Display values
				         
				        
				         w.write(recordString);
				         
				      rs.close();
				      }
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
					   
					}//end main
			}
		}
	

	private boolean outputExec(String str) {
		System.out.println(str);
		for(int i = 0; i<ApplicationContext.listOfInputFiles.size();i++){
			
			
			File inputfile = new File("D:\\threadassignment\\Process Folder\\"+ApplicationContext.listOfInputFiles.get(i));
			
			//if(inputfile.getName().equals(ApplicationContext.listOfOutputFiles.str)){
				return true;
			//}
			
		
		
		}
		return false;
	}

	
}
