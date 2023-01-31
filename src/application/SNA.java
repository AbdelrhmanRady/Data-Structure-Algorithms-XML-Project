package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.lang.Object;



public class SNA {
	
	XMLtoGraph gr;
	Graph graph;

	HashMap<Integer,  ArrayList<Integer>> Users;
	HashMap<Integer, String> Names;
	HashMap<String, Integer> IDs;
	ArrayList<Integer> NumberOfFollowers;
	
	
	
	
	public SNA() throws IOException {
		this.gr = new XMLtoGraph();
		this.graph = gr.convert();
		this.Names = gr.getName();
		this.IDs = gr.getID();
		this.Users = graph.getMap();
	}
	
	public static String capitalizeWord(String str){  
	    String words[]=str.split("\\s");  
	    String capitalizeWord="";  
	    for(String w:words){  
	        String first=w.substring(0,1);  
	        String afterfirst=w.substring(1);  
	        capitalizeWord+=first.toUpperCase()+afterfirst+" ";  
	    }  
	    return capitalizeWord.trim();  
	}  
	
	
	public void mostInfluencer() {
		int max = 0;
		int maxID = 0;
		int num;
		
		
		for ( Integer key : Users.keySet() ) {
			num = Users.get(key).size();
			if(num>max) {
				max = num;
				maxID = key;
			}
		}
		
		System.out.println(capitalizeWord("The most influencer user is: "+Names.get(maxID)
		+", with an id of "+maxID+", and number of followers: "+Users.get(maxID).size()));
		
	}
	
	public void mostActive() {	
		HashMap<Integer, Integer> Active = new HashMap<Integer, Integer>();
		int max = -1;
		int maxID = 0;
		
		for ( Integer key : Users.keySet() ) {
			Active.put(key,0);
		}
		
		for ( Integer key : Users.keySet() ) {
			for(int j=0;j<Users.get(key).size();j++) {
				int user = Users.get(key).get(j);
				if(Active.containsKey(user)){
					Active.put(user, Active.get(user)+1);
				}
			}
		}
		
		for ( Integer key : Users.keySet() ) {
			int num = Active.get(key);
			if(num>max) {
				max = num;
				maxID = key;
			}
		}
		
		System.out.println(capitalizeWord("The most active user is : "+Names.get(maxID)
		+", with an id of "+maxID+", following : "+max+" users"));
	}
	public void mutualFollowers(int id1,int id2) {
		
		
		if(!Users.containsKey(id1) || !Users.containsKey(id2)) {
			System.out.println(capitalizeWord("Please enter valid ids!"));
			return;
		}
		
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		
		for(int i = 0;i<Users.get(id1).size();i++) {
			list1.add(Users.get(id1).get(i));
		}
		for(int i = 0;i<Users.get(id2).size();i++) {
			list1.add(Users.get(id2).get(i));
		}
		
		list1.retainAll(list2);
		if(list1.isEmpty()) System.out.println("No Mutual Followers");
		else {
		System.out.println(capitalizeWord("Mutual followers between "+Names.get(id1)+" and "
				+ ""+Names.get(id2)+" Are:"));
		for(int i=0;i<list1.size();i++) {
			System.out.println(capitalizeWord(Names.get(list1.get(i))));
		}
		}
	}
	
	public void mutualFollowers(String name1,String name2) {
		

		name1 = name1.toLowerCase();
		name2 = name2.toLowerCase();
		
		if(!IDs.containsKey(name1) || !IDs.containsKey(name2)) {
			System.out.println(capitalizeWord("Please enter valid Names!"));
			return;
		}
		
		int id1 = IDs.get(name1);
		int id2 = IDs.get(name2);

		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		
		for(int i = 0;i<Users.get(id1).size();i++) {
			list1.add(Users.get(id1).get(i));
		}
		for(int i = 0;i<Users.get(id2).size();i++) {
			list1.add(Users.get(id2).get(i));
		}
		
		list1.retainAll(list2);
		if(list1.isEmpty()) System.out.println(capitalizeWord("No Mutual followers"));
		else {
		System.out.println(capitalizeWord("Mutual followers between "+name1+" and "+name2+" are:"));
		for(int i=0;i<list1.size();i++) {
			System.out.println(capitalizeWord(Names.get(list1.get(i))));
		}
		
		}
		
	}
	
	public void suggestUsers() {
		
		for ( Integer key : Users.keySet() ) {
			ArrayList<Integer> suggested = new ArrayList<Integer>();
			ArrayList<Integer> eliminate = new ArrayList<Integer>();
			
			eliminate.add(key);		
			ArrayList<Integer> compared = Users.get(key);
			for(int z=0;z<compared.size();z++) {
				eliminate.add(compared.get(z));
			}
			
			for(int j=0;j<compared.size();j++) {
				for(int k=0;k<Users.get(compared.get(j)).size();k++) {
					suggested.add(Users.get(compared.get(j)).get(k));
				}
			}
			suggested.removeAll(eliminate);
			if(suggested.isEmpty()) {
				System.out.println(capitalizeWord("No users to suggest for "+Names.get(key)));
				System.out.println("");
			}
			else {
				System.out.println(capitalizeWord("Users to suggest for "+Names.get(key)+" are: "));
				for(int h=0;h<suggested.size();h++) {
					System.out.println(capitalizeWord(Names.get(suggested.get(h))));
				}
				System.out.println("");
			}
		}
		
		
	}
	
	public void drawConnections() {
		
		String digraph = "";
		for ( Integer key : Users.keySet() ) {
			for(int j=0;j<Users.get(key).size();j++) {
				String followerName = capitalizeWord(Names.get(Users.get(key).get(j)));
				followerName = followerName.replace(" ", "_");
				String followingName = capitalizeWord(Names.get(key));
				followingName = followingName.replace(" ","_");
				String followerID = "_"+Integer.toString(Users.get(key).get(j));
				String followingID = "_"+Integer.toString(key);
				digraph += followerName+followerID+"->"+followingName+followingID+";";
			}
		}
		GraphViz.createDotGraph(digraph, "DotGraph");
		
        try  
        {  
        //constructor of file class having file as argument  
        File file = new File("./output.png");   
        if(!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not  
        {  
        System.out.println("not supported");  
        return;  
        }  
        Desktop desktop = Desktop.getDesktop();  
        if(file.exists())         //checks file exists or not  
        desktop.open(file);              //opens the specified file  
        }  
        catch(Exception e)  
        {  
        e.printStackTrace();  
        }  
		
	}
	
	
}
