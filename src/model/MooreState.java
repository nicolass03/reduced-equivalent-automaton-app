package model;

import java.util.Hashtable;

public class MooreState {

	private String name;
	private Hashtable<String, String> adj;
	private String output;
	
	public MooreState(String n, String out) {
		name = n;
		output = out;
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
	 * @return the adj
	 */
	public Hashtable<String, String> getAdj() {
		return adj;
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
	
	
}
