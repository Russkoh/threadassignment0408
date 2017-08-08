package codefordb;

import java.util.Stack;

import applicationcontext.ApplicationContext;
import pojo.Record;

public class WareHouse {
	/*private Stack<Record> records;
	private Stack<Record> invalidRecords;*/

	public WareHouse(){
	
		//records = ApplicationContext.validRecords;
		//invalidRecords = ApplicationContext.invalidRecords;
	}

	 synchronized public void validPush(Record x){
		 synchronized(ApplicationContext.validRecords){
			 while(ApplicationContext.validRecords.size()==18){
				 try{
					 System.out.println("Stack is full, Worker valid threads will have to wait...");
					 ApplicationContext.validRecords.wait();
				 }
				 catch(Exception e){
					 e.printStackTrace();
				 }

			 }
			 ApplicationContext.validRecords.notify();
			 ApplicationContext.validRecords.push(x);
		 }
		 
		 
	}

	public Record validPop(){
		synchronized(ApplicationContext.validRecords){
			while(ApplicationContext.validRecords.isEmpty()){
				try{
					System.out.println("Stack is empty, Database valid threads will have to wait...");
					ApplicationContext.validRecords.wait();
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
			}
			ApplicationContext.validRecords.notify();

			return ApplicationContext.validRecords.pop();
		}
	}
	
	 synchronized public void invalidPush(Record x){
		 synchronized(ApplicationContext.invalidRecords){
			 while(ApplicationContext.invalidRecords.size()==18){
				 try{
					 System.out.println("Stack is full, Worker invalid threads will have to wait...");
					 ApplicationContext.invalidRecords.wait();
				 }
				 catch(Exception e){
					 e.printStackTrace();
				 }

			 }
			 ApplicationContext.invalidRecords.notify();
			 ApplicationContext.invalidRecords.push(x);
		 }
		 
		 
	}

	public Record invalidPop(){
		synchronized(ApplicationContext.invalidRecords){
			System.out.println("invalid records check");
			while(ApplicationContext.invalidRecords.isEmpty()){
				try{
					System.out.println("Stack is empty, Database invalid threads will have to wait...");
					ApplicationContext.invalidRecords.wait();
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
			}
			ApplicationContext.invalidRecords.notify();
			return ApplicationContext.invalidRecords.pop();
		}
	}

}
