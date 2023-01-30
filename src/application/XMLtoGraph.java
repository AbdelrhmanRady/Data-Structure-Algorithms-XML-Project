package application;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLtoGraph {
	
	Graph graph = new Graph();
	
	public static String JSON;


	public Graph convert() throws IOException { //converts JSON file to a Graph of followers
		
		Frame frame = new Frame();
		FileInputStream fileInputStream = new FileInputStream(frame.returnXML());
		Tree tree = new Tree();
		tree.insertXML(fileInputStream);
		StringBuilder sb = new StringBuilder();

		XML2JSON xml2JSON = new XML2JSON(sb,0,frame.XML);
		xml2JSON.Convert(tree.getTreeRoot());
		xml2JSON.createJsonFile();

		XMLtoGraph gr = new XMLtoGraph();	
		JSON = sb.toString();
		
	    Pattern pattern1 = Pattern.compile("name");
	    Matcher matcher1 = pattern1.matcher(JSON);
	    
	    Pattern pattern2 = Pattern.compile("\"id\"");
	    Matcher matcher2 = pattern2.matcher(JSON);
	    
	    Pattern pattern3 = Pattern.compile("}\n");
	    Matcher matcher3 = pattern3.matcher(JSON);
	    
	    //patterns used for finding certain keywords 
	    
	    
	    boolean start = true;
	    int nameIndex = 0;
	    int idIndex = 0;
	    int firstID = 0;
	    
	    //indices for tracking matchers
	    
	    int followed;
	    while(matcher1.find()) {  //find "names"
	    	nameIndex = matcher1.start();
	    	idIndex = matcher1.start();
	    	int lastIndex = JSON.lastIndexOf("\"",nameIndex-2);
	    	followed = Integer.parseInt(JSON.substring(JSON.lastIndexOf("\"",lastIndex-1)+1,lastIndex));
	    	graph.addUser(followed); //add the id to the graph
	    	
	    	while(matcher2.find(idIndex)) { //find id tag
	    		if(start) { //to get the first index of id
		    		firstID = matcher2.start();
		    		start = false;
	    		}
	    		if(matcher3.find(firstID)) { //finds ending index for the followers id
	    			if(matcher2.start()<matcher3.start()) {
	    				int follower = Integer.parseInt(JSON.substring(matcher2.end()+2,JSON.indexOf("\"",matcher2.end()+2)));
	    				graph.addConnection(followed, follower);
	    			}
	    			else break;
	    		}
	    		idIndex= matcher2.start() +1;
	    	}
	    	start = true;
	    }
	    return graph;
	    
	}
	
	public HashMap<Integer, String> getName() {
		
		HashMap<Integer, String> Names = new HashMap<Integer, String>();
		
	    Pattern pattern1 = Pattern.compile("name");
	    Matcher matcher1 = pattern1.matcher(JSON);
	    
	    Pattern pattern2 = Pattern.compile("\"id\"");
	    Matcher matcher2 = pattern2.matcher(JSON);
	    
	    int nameIndex = 0;
	    int idIndex = 0;
	    int lastIndex = JSON.lastIndexOf("\"",nameIndex-2);
	    
	    while(matcher1.find()) {
	    	nameIndex = matcher1.start();
	    	idIndex = matcher1.start();
	    	lastIndex = JSON.lastIndexOf("\"",nameIndex-2);
	    	if(matcher2.find()) {
	    		int id = Integer.parseInt(JSON.substring(JSON.lastIndexOf("\"",lastIndex-1)+1,lastIndex));
	    		String name = JSON.substring(matcher1.end()+3,JSON.indexOf(("\""),matcher1.end()+3));
	    		Names.put(id, name.toLowerCase());
	    		
	    	}
	    	
	    }
	    return Names;	
	}
	
	public HashMap<String, Integer> getID() {
		
		HashMap<String, Integer> Names = new HashMap<String, Integer>();
		
	    Pattern pattern1 = Pattern.compile("name");
	    Matcher matcher1 = pattern1.matcher(JSON);
	    
	    Pattern pattern2 = Pattern.compile("\"id\"");
	    Matcher matcher2 = pattern2.matcher(JSON);
	    
	    int nameIndex = 0;
	    int idIndex = 0;
	    int lastIndex = JSON.lastIndexOf("\"",nameIndex-2);
	    
	    while(matcher1.find()) {
	    	nameIndex = matcher1.start();
	    	idIndex = matcher1.start();
	    	lastIndex = JSON.lastIndexOf("\"",nameIndex-2);
	    	if(matcher2.find()) {
	    		int id = Integer.parseInt(JSON.substring(JSON.lastIndexOf("\"",lastIndex-1)+1,lastIndex));
	    		String name = JSON.substring(matcher1.end()+3,JSON.indexOf(("\""),matcher1.end()+3));
	    		Names.put(name.toLowerCase(), id);
	    		
	    	}
	    	
	    }
	    return Names;	
	}

}