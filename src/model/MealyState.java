package model;

import java.util.Hashtable;

public class MealyState {

	private Hashtable<String, Tuple<String,String>> adj;
	private String name;
	
	public MealyState(String name) {
		this.name = name;
	}
	
	public void addAdj(String m, String symbol, String out) {
		adj.put(symbol, new Tuple<String, String>(m,out));
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
