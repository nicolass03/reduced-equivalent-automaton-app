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
			partitioning(firstPartition());
			break;
		case MOORE:
			deleteUnreachableMoore();
			partitioning(firstPartition());
			break;
		}
	}
	
	public ArrayList<ArrayList> partitioning(ArrayList<ArrayList> arr) {
		//nueva particion
		ArrayList<ArrayList> newPartition = new ArrayList<>();
		//aqui agrego los que elimino para luego ver si forman nuevas particiones
		ArrayList garbage = new ArrayList();
		switch(type) {
		case MEALY:	
			//recorro cada bloque de la particion
			for(int k = 0; k < arr.size(); k++) {
				ArrayList currentBlock = arr.get(k);
				//saco mi primer estado y lo agrego a la primera particion
				MealyState current = (MealyState) currentBlock.get(0);
				newPartition.get(k).add(current);
				//recorro todos los estados de mi bloque
					for(int i = 1; i < currentBlock.size()-1; i++) {
						MealyState next = (MealyState)currentBlock.get(i);
						//hago el algoritmo para ver si pertenecen al mismo bloque
						boolean sameBlock = together(current, next, arr);
						//si pertenece
						if(sameBlock) {
							//lo agrego a mi particion
							newPartition.get(k).add(next);
							//hago current como el siguiente
							current = next;
						}
						//si no, lo agrego a mi coleccion de eliminados para su posterior evaluacion
						else {
							garbage.add(next);
						}
				}
			}
			//hasta este punto, tengo en newPartition los estados que pertenecen, ahora voy a evaluar la coleccion garbage
			for (int i = 0; i < garbage.size()-1; i++) {
				ArrayList newBlock = new ArrayList();
				MealyState ms = (MealyState) garbage.get(i);
				newBlock.add(ms);
				MealyState next = (MealyState) garbage.get(i+1);

				if(together(ms, next, arr)) {
					newBlock.add(next);
				}
				else {
					
				}
			}
				
			break;
			
		case MOORE:
			break;
		}
		if(newPartition == arr)
			return newPartition;
		else
			return partitioning(newPartition);
	}
	
	/**
	 * Este metodo evalua si pertenecen a la misma particion
	 * @param current - estado 1
	 * @param object - estado 2
	 * @param arr - particion actual
	 * @return - true or false 
	 */
	private boolean together(MealyState current, MealyState object, ArrayList<ArrayList> arr) {
		boolean same = false;
		// TODO Auto-generated method stub
		return same;
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
