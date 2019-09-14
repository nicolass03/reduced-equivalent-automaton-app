package model;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

public class MealyState {

	private Hashtable<String, Tuple<String,String>> adj;
	private String name;
	private boolean vis;
	
	public MealyState(String name) {
		adj = new Hashtable<String, Tuple<String,String>>();
		this.name = name;
		vis = false;
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
	
	/**
	 * @return the vis
	 */
	public boolean isVis() {
		return vis;
	}

	/**
	 * @param vis the vis to set
	 */
	public void setVis(boolean vis) {
		this.vis = vis;
	}

	public Set getAdj() {
		HashSet h = new HashSet();
		for(Entry e : adj.entrySet()) {
			Tuple t = (Tuple) e.getValue();
			h.add(t.x);
		}
		return h;
	}
}
