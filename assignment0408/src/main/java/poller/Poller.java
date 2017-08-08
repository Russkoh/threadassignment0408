package poller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;


import ExhibitMonitor.assignment0408.Main;
import applicationcontext.ApplicationContext;
import pojo.Record;
import worker.Worker;

public class Poller implements Runnable{
	
	public void run(){
		ApplicationContext.init();
		//Creating input stream
				File inputFolder = new File("D:\\threadassignment\\Inputfolder");
				String fileName = "";
				int counter =0;
				while(counter<1){
					String[] listOfAvailableFolders = inputFolder.list();
					
					for(int i = 0; i<listOfAvailableFolders.length;i++){
						fileName = listOfAvailableFolders[i];
						
						checker(fileName);
					}
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					counter++;
					System.out.println("valid record size "+ApplicationContext.validRecords.size());
					System.out.println("invalid record size "+ApplicationContext.invalidRecords.size());
					
				}
				
	}

	private void checker(String fileName) {
		if(isValidFile(fileName)){
			
					System.out.println("Spawning Worker Thread with "+fileName);
					File sendingFile = new File("D:\\threadassignment\\Inputfolder\\"+fileName);
					File destinationFile = new File("D:\\threadassignment\\Process Folder\\"+fileName);
					sendingFile.renameTo(destinationFile);
					
					Worker myWorker = new Worker(fileName);
					Thread workingThread = new Thread(myWorker);
					workingThread.start();
				
		}else{
			System.out.println("deleting file: "+fileName);
			File sendingFile = new File("D:\\threadassignment\\Inputfolder\\"+fileName);
			sendingFile.delete();
		}
	}

	private boolean isOnTime(String fileName) {
	    
		File f = new File("D:\\threadassignment\\Inputfolder\\"+fileName);
		//System.out.println("f.lastmod = " + f.lastModified());
		//System.out.println("map result = "+ ApplicationContext.expectedInputFiles.get(fileName));
		if(f.lastModified() <=  ApplicationContext.expectedInputFiles.get(fileName)){
			System.out.println("On Time!");
			return true;
			
		}
		System.out.println("Not on time!");
		return false;
	}

	private boolean isValidFile(String fileName) {
	
			for(int i = 0; i<ApplicationContext.listOfInputFiles.size();i++){
				
				if(fileName.equals(ApplicationContext.listOfInputFiles.get(i))){
					
					System.out.println("File is valid!");
					return !isDuplicate(fileName);
				}
			}
			System.out.println("File is not a valid file");
			return false;
	}

	private boolean isDuplicate( String fileName ) {
		for(int i = 0; i<ApplicationContext.FilesAlreadyProcessed.size();i++){
			
			if(fileName.equals(ApplicationContext.FilesAlreadyProcessed.get(i))){
				System.out.println("File is a duplicate!");
				return true;
			}
		}
		ApplicationContext.FilesAlreadyProcessed.add(fileName);
		
		System.out.println("file is not a duplicate");
		
		return !isOnTime(fileName);
	}
	
	

}
