package model;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Map.Entry;

public class MooreState {

	private String name;
	private Hashtable<String, String> adj;
	private String output;
	private boolean vis;
	
	public MooreState(String n, String out) {
		name = n;
		output = out;
		vis = false;
		adj = new Hashtable<String, String>();
	}
	
	public void addAdj(String m, String symbol) {
		adj.put(symbol,m);
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
	 * @param adj the adj to set
	 */
	public void setAdj(Hashtable<String, String> adj) {
		this.adj = adj;
	}

	/**
	 * @return the output
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(String output) {
		this.output = output;
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

	/**
	 * @return the adj
	 */
	public Set getAdj() {
		HashSet h = new HashSet();
		for(Entry e : adj.entrySet()) {
			h.add(e.getValue());
		}
		return h;
	}

	
}
