package model;

import java.util.HashSet;
import java.util.Iterator;

public class Main {

	public static final int MEALY = 0;
	public static final int MOORE = 1;


	private HashSet states;
	private String[] inputs;
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
	public void deleteUnreachableMealy() {
		dfsMealy((MealyState) states.iterator().next());
		for(Object o : states) {
			MealyState n = (MealyState) o;
			if(!n.isVis()) {
				states.remove(n);
			}
		}
	}
	
	public void deleteUnreachableMoore() {
		dfsMoore((MooreState) states.iterator().next());
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
