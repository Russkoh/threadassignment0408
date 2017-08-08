package ExhibitMonitor.assignment0408;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import applicationcontext.ApplicationContext;
import codefordb.InvalidDatebase;
import codefordb.ValidDatabase;
import output.TimerTaskOne;
import poller.Poller;
import worker.Worker;

public class Main {

	public static void main(String[] args){
		
		Poller myPoller = new Poller();
		Thread pollingThread = new Thread(myPoller);
		
		pollingThread.start();
		
		ValidDatabase vdb = new ValidDatabase();
		
		InvalidDatebase ivdb = new InvalidDatebase();
		
		Thread tvdb = new Thread(vdb);
		Thread tivdb = new Thread(ivdb);
		
		tvdb.start();
		tivdb.start();
	
		Timer timer = new Timer();
		TimerTask timertask = new TimerTaskOne();
		Calendar deadline = Calendar.getInstance();
		/*
		deadline.set(Calendar.HOUR_OF_DAY,17);*/
		deadline.set(Calendar.MINUTE,9);
		deadline.set(Calendar.SECOND,5);
		
		timer.schedule(timertask, deadline.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
		
		
		
	}
}
