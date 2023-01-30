package application;

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
		for(int i =1;i<=Users.size();i++) {
			num = Users.get(i).size();
			if(num>max) {
				max = num;
				maxID = i;
			}
		}
		System.out.println(capitalizeWord("The most influencer user is: "+Names.get(maxID)
		+", with an id of "+maxID+", and number of followers: "+Users.get(maxID).size()));
	}
	
	public void mostActive() {	
		HashMap<Integer, Integer> Active = new HashMap<Integer, Integer>();
		int max = -1;
		int maxID = 0;
		
		for(int i=1;i<=Users.size();i++) {
			Active.put(i, 0);
		}
		for(int i =1;i<=Users.size();i++) {
			for(int j=0;j<Users.get(i).size();j++) {
				int user = Users.get(i).get(j);
				if(Active.containsKey(user)){
					Active.put(user, Active.get(user)+1);
				}
			}
		}
		for(int i =1;i<=Active.size();i++) {
			int num = Active.get(i);
			if(num>max) {
				max = num;
				maxID = i;
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
		
		ArrayList<Integer> list1 = Users.get(id1);
		ArrayList<Integer> list2 = Users.get(id2);
		
		list1.retainAll(list2);
		if(list1.isEmpty()) System.out.println("No Common Followers");
		else {
		System.out.println("Mutual followers Are:");
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

		ArrayList<Integer> list1 = Users.get(id1);
		ArrayList<Integer> list2 = Users.get(id2);
		
		list1.retainAll(list2);
		if(list1.isEmpty()) System.out.println(capitalizeWord("No common followers"));
		else {
		System.out.println(capitalizeWord("Mutual followers between "+name1+" and "+name2+" are:"));
		for(int i=0;i<list1.size();i++) {
			System.out.println(capitalizeWord(Names.get(list1.get(i))));
		}
		}
		
	}
	
	public void suggestUsers() {
		
		
		
		for(int i=1;i<=Users.size();i++) {
			
			ArrayList<Integer> suggested = new ArrayList<Integer>();
			ArrayList<Integer> eliminate = new ArrayList<Integer>();
			
			eliminate.add(i);		
			ArrayList<Integer> compared = Users.get(i);
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
				System.out.println(capitalizeWord("No users to suggest for "+Names.get(i)));
				System.out.println("");
			}
			else {
				System.out.println(capitalizeWord("Users to suggest for "+Names.get(i)+" are: "));
				for(int h=0;h<suggested.size();h++) {
					System.out.println(capitalizeWord(Names.get(suggested.get(h))));
				}
				System.out.println("");
			}
			
		}
		
	}
	
	
}
