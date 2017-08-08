package pojo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Record {

	String fileName;
	Date dater;
	String date;
	int recordNo;
	String[] record;
	String recordString;
	public String getRecordString() {
		return recordString;
	}

	public void setRecordString(String recordString) {
		this.recordString = recordString;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Record(String filename, Long date){
		this.fileName=filename;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		this.dater = calendar.getTime();
		
	}
	
	public int getRecordNo() {
		return recordNo;
	}
	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}
	public String[] getRecord() {
		return record;
	}
	public void setRecord(String[] record) {
		this.record = record;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		
		this.date = date;
	}
	
}
