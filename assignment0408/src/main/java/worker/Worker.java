package worker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import applicationcontext.ApplicationContext;
import codefordb.WareHouse;
import pojo.Record;
import utility.SerialNo;

public class Worker implements Runnable{
	private String fileName;
	//WareHouse wh = new WareHouse();
	public Worker(String fileName){
		this.fileName=fileName;
	}
	public void run(){
		String line = "";
		String splitby = ",";
		int numOfNodes = ApplicationContext.numberOfFields.get(fileName);
		
		System.out.println("number of nodes is "+numOfNodes);
		String[] fields = new String[numOfNodes];
		File f = new File("D:\\threadassignment\\Process Folder\\"+this.fileName);
		
		
		
		
		try {
			FileReader fr = new FileReader("D:\\threadassignment\\Process Folder\\"+this.fileName);
			BufferedReader br = new BufferedReader(fr);
			try {
				while((line = br.readLine())!=null){
					fields = line.split(splitby);

					Record rec = new Record(fileName,f.lastModified());
					rec.setRecord(fields);
					synchronized(SerialNo.rowNo){
					rec.setRecordNo(++SerialNo.rowNo);
					}
					rec.setRecordString(line);
					

					checkIfRecordIsValid(rec);
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
	}
	private void checkIfRecordIsValid(Record rec) {
		int counter= 0;
		boolean passer = false;
		for(int i = 0; i<ApplicationContext.listOfInputFiles.size();i++){

			if(ApplicationContext.listOfInputFiles.get(i).equals(rec.getFileName())){
				break;
			}
			counter+=ApplicationContext.numberOfFields.get(ApplicationContext.listOfInputFiles.get(i));
			System.out.println("counter for "+rec.getFileName()+" is "+counter);
		}
		
		System.out.println("number of nodes "+ApplicationContext.numberOfFields.get(fileName));
		for(int i = 0; i<ApplicationContext.numberOfFields.get(fileName);i++){
			System.out.println("for file: "+fileName);
			System.out.println("examining counter "+counter);
			System.out.println("examining i counter "+i);
			if(ApplicationContext.typeOfData.get(counter).getType().equals("text")){
				System.out.println("supposed to be text");
				System.out.println("Record: "+rec.getRecord()[i]);
				passer = true;
				System.out.println(rec.getRecord()[i]+" passed");
			}
			//check if the data type of each column matches the XMl
			else if(ApplicationContext.typeOfData.get(counter).getType().equals("date")){
				System.out.println("supposed to be date");
				rec.setDate(rec.getRecord()[i]);
				try {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

					Date da = formatter.parse(rec.getRecord()[i]);

					
					System.out.println(rec.getRecord()[i]+" passed");
					passer = true;
				} catch (ParseException e) {
					System.out.println(rec.getRecord()[i]+" failed");
					passer = false;

					e.printStackTrace();
				}
			}else if(ApplicationContext.typeOfData.get(counter).getType().equals("double")){
				System.out.println("supposed to be double");
				try{
				double d = Double.parseDouble(rec.getRecord()[i]);

				passer = true;
				}catch(NumberFormatException e){
					e.printStackTrace();
					passer = false;
				}
			}
			
			else{
				System.out.println(rec.getRecord()[i]+" failed");
				passer = false;
			}
			counter+=1;
			
		}
		//insert code to push 
		if(passer){
			ApplicationContext.wh.validPush(rec);
			System.out.println("valid record "+rec.getRecordString()+" pushed");
		}else if(passer==false){
			ApplicationContext.wh.invalidPush(rec);
			System.out.println("invalid recor "+ rec.getRecordString()+" pushed");
		}
	}

}
