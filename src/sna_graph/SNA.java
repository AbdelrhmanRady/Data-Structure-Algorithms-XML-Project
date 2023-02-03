package sna_graph;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.lang.Object;



public class SNA {
	
	XMLtoGraph gr;
	Graph graph;

	HashMap<Integer,  ArrayList<Integer>> Users;
	HashMap<Integer, String> Names;
	HashMap<String, Integer> IDs;
	ArrayList<Integer> NumberOfFollowers;
	
	
	
	
	public SNA(String path, String XML) throws IOException {
		this.gr = new XMLtoGraph();
		this.graph = gr.convert(path, XML);
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
	
	
	public ArrayList<String> mostInfluencer() {
		int max = 0;
		int maxID = 0;
		int num;
		boolean same = false;
		ArrayList<String> s = new ArrayList<String>();
		
		
		for ( Integer key : Users.keySet() ) {
			num = Users.get(key).size();
			if(num>max) {
				max = num;
				maxID = key;
			}
		}
		
		if(max == 0) {
			System.out.println("All Users have no followers");
			System.out.println("");
			s.add("All Users have no followers");
			return s;
		}
		
		for ( Integer key : Users.keySet() ) {
			if(Users.get(key).size() == max) same = true;
			else {
				same = false;
				break;
			}
		}
		
		if(same == true) {
			System.out.println("All Users Have The Same Influence (Same Number Of Followers) ");
			System.out.println("");
			s.add("All Users Have The Same Influence (Same Number Of Followers) ");
			return s;
		}
		
		System.out.println(capitalizeWord("The most influencer user(s) Are/is: "));
		System.out.println("");
		s.add(capitalizeWord("The most influencer user(s) Are/is: "));
		for ( Integer key : Users.keySet() ) {
			
			if(Users.get(key).size() == max) {
				System.out.println(capitalizeWord(Names.get(key)+", with an id of "+key+", and number of followers: "+Users.get(key).size()));
				s.add("\n"+capitalizeWord(Names.get(key)+", with an id of "+key+", and number of followers: "+Users.get(key).size()));
			}
			
		}
		
		System.out.println("");
		return s;
	}
	
	public ArrayList<String> mostActive() {

		HashMap<Integer, Integer> Active = new HashMap<Integer, Integer>();
		ArrayList<String> s = new ArrayList<String>();
		
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
		
		if(max == 0) {
			System.out.println("All Users don't follow someone");
			System.out.println("");
			s.add("All Users don't follow someone");
			return s;
		}
		
		Set<Integer> values = new HashSet<Integer>(Active.values());
		boolean isUnique = values.size() == 1;
		
		if(isUnique) {
			System.out.println("All Users have The Same Activity (Same Number Of Follows)");
			s.add("All Users have The Same Activity (Same Number Of Follows)");
			return s;
		}
		
		System.out.println(capitalizeWord("The most active user(s) Are/is:"));
		System.out.println("");
		s.add(capitalizeWord("The most active user(s) Are/is:"));
		for ( Integer key : Active.keySet() ) {
			
			if(Active.get(key) == max) {
				System.out.println(capitalizeWord(Names.get(key)+", with an id of "+key+", following : "+max+" users"));
				s.add("\n"+capitalizeWord(Names.get(key)+", with an id of "+key+", following : "+max+" users"));
			}
			
		}
		System.out.println("");
		return s;
	}
	
	public ArrayList<String> mutualFollowers(int id1,int id2) {
		ArrayList<String> s= new ArrayList<String>();
		
		if(!Users.containsKey(id1) || !Users.containsKey(id2)) {
			System.out.println(capitalizeWord("Please enter valid ids!"));
			s.add(capitalizeWord("Please enter valid ids!"));
			return s;
		}
		
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		
		for(int i = 0;i<Users.get(id1).size();i++) {
			list1.add(Users.get(id1).get(i));
		}
		for(int i = 0;i<Users.get(id2).size();i++) {
			list2.add(Users.get(id2).get(i));
		}
		
		list1.retainAll(list2);
		if(list1.isEmpty()) {
			System.out.println("No Mutual Followers");
			s.add("No Mutual Followers");
			return s;
		}
		else {
		System.out.println(capitalizeWord("Mutual followers between "+Names.get(id1)+" and "
				+ ""+Names.get(id2)+" Are:"));
		s.add(capitalizeWord("Mutual followers between "+Names.get(id1)+" and "
				+ ""+Names.get(id2)+" Are:"));
		for(int i=0;i<list1.size();i++) {
			System.out.println(capitalizeWord(Names.get(list1.get(i))));
			s.add("\n"+capitalizeWord(Names.get(list1.get(i))));
		}
		}
		System.out.println("");
		return s;
	}
	
	public ArrayList<String> mutualFollowers(String name1,String name2) {
		ArrayList<String> s= new ArrayList<String>();
		name1 = name1.toLowerCase();
		name2 = name2.toLowerCase();
		
		if(!IDs.containsKey(name1) || !IDs.containsKey(name2)) {
			System.out.println(capitalizeWord("Please enter valid Names!"));
			s.add(capitalizeWord("Please enter valid Names!"));
			return s;
		}
		
		int id1 = IDs.get(name1);
		int id2 = IDs.get(name2);

		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		
		for(int i = 0;i<Users.get(id1).size();i++) {
			list1.add(Users.get(id1).get(i));
		}
		for(int i = 0;i<Users.get(id2).size();i++) {
			list2.add(Users.get(id2).get(i));
		}
		
		list1.retainAll(list2);
		if(list1.isEmpty()) {
			System.out.println(capitalizeWord("No Mutual followers"));
			s.add(capitalizeWord("No Mutual followers"));
			return s;
		}
		else {
		System.out.println(capitalizeWord("Mutual followers between "+name1+" and "+name2+" are:"));
		s.add(capitalizeWord("Mutual followers between "+name1+" and "+name2+" are:"));
		for(int i=0;i<list1.size();i++) {
			System.out.println(capitalizeWord(Names.get(list1.get(i))));
			s.add("\n"+capitalizeWord(Names.get(list1.get(i))));
		}
		
		}
		System.out.println("");
		return s;
		
	}
	
	public ArrayList<String> suggestUsers() {
		ArrayList<String> s = new ArrayList<String>();
		for ( Integer key : Users.keySet() ) {
			ArrayList<Integer> suggested = new ArrayList<Integer>();
			ArrayList<Integer> eliminate = new ArrayList<Integer>();
			
			
			eliminate.add(key);		
			ArrayList<Integer> compared = Users.get(key);
//			for(int z=0;z<compared.size();z++) {
//				eliminate.add(compared.get(z));
//			}
			
			for(int j=0;j<compared.size();j++) {
				for(int k=0;k<Users.get(compared.get(j)).size();k++) {
					if(Users.get(Users.get(compared.get(j)).get(k)).contains(key)) {
						continue;
					}
					suggested.add(Users.get(compared.get(j)).get(k));
				}
			}
			suggested.removeAll(eliminate);
			if(suggested.isEmpty()) {
				System.out.println(capitalizeWord("No users to suggest for "+Names.get(key)));
				System.out.println("");
				s.add(capitalizeWord("No users to suggest for "+Names.get(key)));
				s.add("\n");
			}
			else {
				System.out.println(capitalizeWord("Users to suggest for "+Names.get(key)+" are: "));
				s.add(capitalizeWord("Users to suggest for "+Names.get(key)+" are: "));
				for(int h=0;h<suggested.size();h++) {
					System.out.println(capitalizeWord(Names.get(suggested.get(h))));
					s.add("\n"+capitalizeWord(Names.get(suggested.get(h)))+"  ");
				}
				System.out.println("");s.add("\n");
			}
			System.out.println("");
			s.add("\n");
		}
		return s;
		
		
	}
	
	public void drawConnections() {
		
		String digraph = "";
		
		for ( Integer key : Users.keySet() ) {
			if(Users.get(key).size()== 0) {
				
				for ( Integer keyy : Users.keySet() ) {
					if(Users.get(keyy).contains(key)) {
						break;
					}
					else {
						String followingName = capitalizeWord(Names.get(key));
						followingName = followingName.replace(" ","_");
						digraph+= followingName+"_"+Integer.toString(key)+";";
					}
				}
	
			}
			else {
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