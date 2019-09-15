package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;

public class Main {

	public static final int MEALY = 0;
	public static final int MOORE = 1;


	private HashSet states;
	private String[] inputs;
	private Object initState;
	private int type;

	public Main() {
		states = new HashSet();
	}

	public void init(String[][] table, int type, String[] inp) {
		inputs = inp;
		this.type = type;
		switch(type) {
		case MEALY:
			for (int i = 0; i < table.length; i++) {
				if(table[i][0] == null || table[i][0].equals("")) {
					break;
				}
				else {
					String name = table[i][0];
					MealyState m = new MealyState(name);
					if(initState == null)
						initState = m;
					for (int j = 1; j < table[i].length; j++) {
						String[] data = table[i][j].split(",");
						m.addAdj(data[0], inputs[j-1], data[1]);
					}
					states.add(m);
				}
			}
			break;
		case MOORE:
			for (int i = 0; i < table.length; i++) {
				if(table[i][0] == null || table[i][0].equals("")) {
					break;
				}
				else {
					String name = table[i][0];
					String out = table[i][table[i].length-1];
					MooreState m = new MooreState(name, out);
					if(initState == null)
						initState = m;
					for (int j = 1; j < table[i].length-1; j++) {
						m.addAdj(table[i][j], inputs[j-1]);
					}
					states.add(m);
				}
			}
			break;
		}

	}

	public void reduce() {
		switch(type) {
		case MEALY:
			deleteUnreachableMealy();
			break;
		case MOORE:
			deleteUnreachableMoore();
			break;
		}
	}
	
	public void partitioning() {
		
	}
	
	public ArrayList<ArrayList> firstPartition(){
		Hashtable<String,ArrayList> auxFirst = new Hashtable<String,ArrayList>();
		HashSet aux = (HashSet) states.clone();
		switch(type) {
		case MEALY:
			for (Object o : aux) {
				MealyState m = (MealyState) o;
				if(auxFirst.isEmpty() || !(auxFirst.keySet().contains(m.getOutputs()))) {
					ArrayList block = new ArrayList();
					block.add(m);
					auxFirst.put(m.getOutputs(), block);
					aux.remove(m);
				}
				else {
					auxFirst.get(m.getOutputs()).add(m);
					aux.remove(m);
				}
			}
			break;
		case MOORE:
			for (Object o : aux) {
				MooreState m = (MooreState) o;
				if(auxFirst.isEmpty() || !(auxFirst.keySet().contains(m.getOutput()))) {
					ArrayList block = new ArrayList();
					block.add(m);
					auxFirst.put(m.getOutput(), block);
					aux.remove(m);
				}
				else {
					auxFirst.get(m.getOutput()).add(m);
					aux.remove(m);
				}
			}
			break;
		}
		ArrayList<ArrayList> first = new ArrayList<ArrayList>();
		for(Entry<String, ArrayList> e : auxFirst.entrySet())
			first.add(e.getValue());
		return first;
	}
	
	public void deleteUnreachableMealy() {
		dfsMealy((MealyState) initState);
		for(Object o : states) {
			MealyState n = (MealyState) o;
			if(!n.isVis()) {
				states.remove(n);
			}
		}
	}
	
	public void deleteUnreachableMoore() {
		dfsMoore((MooreState) initState);
		for(Object o : states) {
			MooreState n = (MooreState) o;
			if(!n.isVis()) {
				states.remove(n);
			}
		}
	}

	public void dfsMealy(MealyState current) { 
		// Mark the current node as visited and print it 
		current.setVis(true);
		System.out.print(current.getName()+" "); 

		// Recur for all the vertices adjacent to this vertex 
		for (MealyState o : getAdjacentsMealy(current)) { 
			if (!o.isVis()) 
				dfsMealy(o); 
		} 
	} 
	
	public void dfsMoore(MooreState current) { 
		// Mark the current node as visited and print it 
		current.setVis(true);
		System.out.print(current.getName()+" "); 

		// Recur for all the vertices adjacent to this vertex 
		for (MooreState o : getAdjacentsMoore(current)) { 
			if (!o.isVis()) 
				dfsMoore(o); 
		} 
	} 
	public HashSet<MealyState> getAdjacentsMealy(MealyState n) {
		HashSet<MealyState> adj = new HashSet<>();
		for(Object m : n.getAdj()) {
			String na = (String) m;
			for(Object o : states) {
				MealyState s = (MealyState) o;
				if(s.getName().equals(na)) {
					adj.add(s);
					break;
				}
			}
		}
		return adj;
	}

	public HashSet<MooreState> getAdjacentsMoore(MooreState n) {
		HashSet<MooreState> adj = new HashSet<>();
		for(Object m : n.getAdj()) {
			String na = (String) m;
			for(Object o : states) {
				MooreState s = (MooreState) o;
				if(s.getName().equals(na)) {
					adj.add(s);
					break;
				}
			}
		}
		return adj;
	}
}
