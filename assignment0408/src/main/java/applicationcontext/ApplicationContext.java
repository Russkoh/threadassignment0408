package applicationcontext;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import codefordb.WareHouse;
import pojo.DataStruc;
import pojo.Record;



public class ApplicationContext {

	public static Map<String, Long> expectedInputFiles = new HashMap<String,Long>();
	public static List<String> listOfInputFiles = new ArrayList<String>();
	public static List<String> FilesAlreadyProcessed = new ArrayList<String>();
	public static Map<String,Integer> numberOfFields = new HashMap<String, Integer>();
	public static List<DataStruc> typeOfData = new ArrayList<DataStruc>();
	public static Stack<Record> validRecords = new  Stack<Record>();
	public static Stack<Record> invalidRecords = new  Stack<Record>();
	public static WareHouse wh = new WareHouse();
	public static Map<String, Long> outputTime = new HashMap<String, Long>();
	public static List<String> listOfOutputFiles = new ArrayList<String>();
	public static Map<String,List<String>> outputDependencies = new HashMap<String,List<String>>();
	
	/*public static void main(String[] args){
		init();
		System.out.println(typeOfData.get(5).getType());
	}*/
    
    public static void init(){
    	
    	try {
			storeExpectedInputFiles(readSetupXml());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	static Document readSetupXml() throws Exception{
		 File inputFile = new File("Setup.xml");
       DocumentBuilderFactory dbFactory 
          = DocumentBuilderFactory.newInstance();
       DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       Document doc = dBuilder.parse(inputFile);
       doc.getDocumentElement().normalize();
       return doc;
	}
	
	@SuppressWarnings("deprecation")
	static void  storeExpectedInputFiles(Document doc) {
		 
		 NodeList iList = doc.getElementsByTagName("inputfile");
		
        for (int temp1 = 0; temp1 < iList.getLength(); temp1++) {
       	 
	            Node iNode = iList.item(temp1);
	            if (iNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element iElement = (Element) iNode;
	                String nameOfFile = iElement.getAttribute("name");
	                String expectedTime = iElement.getAttribute("time");
	                String gracePeriod = iElement.getAttribute("grace-period");
	                String[] timings = expectedTime.split(":");
	                String hour = timings[0];
	                String minute = timings[1];
	                int intHour = Integer.parseInt(hour);
	                int intMin = Integer.parseInt(minute);
	                int intgp = Integer.parseInt(gracePeriod);
	                intMin = intMin + intgp;
	                if(intMin>60){
	                	intHour+=1;
	                	intMin -= 60;
	                }
	                Calendar cal = Calendar.getInstance();
	                cal.set(Calendar.HOUR_OF_DAY, intHour);
	                cal.set(Calendar.MINUTE,intMin);

	                expectedInputFiles.put(nameOfFile, cal.getTimeInMillis());
	                
	                listOfInputFiles.add(nameOfFile);
	                System.out.println("added input files "+listOfInputFiles);
	                numberOfFields.put(nameOfFile, (iNode.getChildNodes().item(1).getChildNodes().getLength()-1)/2);
	              
	                
		          }
	           
	            }
        
        NodeList cList = doc.getElementsByTagName("field");
        for (int temp1 = 0; temp1 < cList.getLength(); temp1++) {
          	 
            Node iNode = cList.item(temp1);
            if (iNode.getNodeType() == Node.ELEMENT_NODE) {
                Element iElement = (Element) iNode;
                String nameOfField = iElement.getAttribute("name");
                String typeOfField = iElement.getAttribute("type");
                DataStruc ds = new DataStruc(iNode.getParentNode().getParentNode().getAttributes().item(1).getNodeValue(),nameOfField,typeOfField);
                
                typeOfData.add(ds);
                
               
            }
        }
        NodeList oList = doc.getElementsByTagName("outputfile");
        for (int temp1 = 0; temp1 < oList.getLength(); temp1++) {
          	 
            Node iNode = oList.item(temp1);
            if (iNode.getNodeType() == Node.ELEMENT_NODE) {
                Element iElement = (Element) iNode;
                String nameOfFile = iElement.getAttribute("name");
                String time = iElement.getAttribute("time");
                String[] hoursnmin = time.split(":");
                String hour = hoursnmin[0];
                String minute = hoursnmin[1];
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
                cal.set(Calendar.MINUTE,Integer.parseInt(minute));
                //System.out.println("name of output file is "+ nameOfFile+" and time of output is "+ time);
                outputTime.put(nameOfFile, cal.getTimeInMillis());
              
                listOfOutputFiles.add(nameOfFile);
            }
        }
        NodeList dList = doc.getElementsByTagName("dependency");
        for (int temp1 = 0; temp1 < dList.getLength(); temp1++) {
        	 List<String> dependencies = new ArrayList<String>();
        	 String fileName = "";
            Node iNode = dList.item(temp1);
            if (iNode.getNodeType() == Node.ELEMENT_NODE) {
                Element iElement = (Element) iNode;
                String nameOfField = iElement.getAttribute("file");
                fileName = (iElement.getParentNode().getAttributes().item(0)).getNodeValue().toString();
                dependencies.add(nameOfField);
            
            }
           // System.out.println(fileName +" is the file's name");
            outputDependencies.put(fileName, dependencies);
        }
                
	}

	

	/*static ActionList storeChildActions(Node aNode) {
		ActionList al = new ActionList();
		 Element aeElement = (Element) aNode;
		 
		 Map<String, String> attMap = new HashMap<String, String>();

        for (int temp5 = 0; temp5 < aNode.getChildNodes().getLength(); temp5++) {
        	
	          if(temp5%2==1){
	        	 
		                attMap.put(aeElement.getChildNodes().item(temp5).getNodeName(), aeElement.getChildNodes().item(temp5).getAttributes().item(0).getNodeValue());
	          }
	         
        }
        
        al.setAttributeMap(attMap);
        al.setName(aeElement.getNodeName());
        return al;
		
	}*/
	
}
