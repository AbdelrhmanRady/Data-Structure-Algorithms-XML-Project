package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	

	HashMap<Integer,  ArrayList<Integer>> Users = new HashMap<Integer,  ArrayList<Integer>>();
	 
    
    void addUser(Integer ID) {
    	Users.putIfAbsent(ID, new ArrayList<Integer>());
    }
    void addConnection(Integer ID1, Integer ID2) {
    	Users.get(ID1).add(ID2);
    }
    HashMap getMap() {
    	return Users;
    }

}