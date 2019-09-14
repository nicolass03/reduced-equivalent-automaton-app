package model;

import java.util.HashSet;

public class Main {

	public static final int MEALY = 0;
	public static final int MOORE = 1;

	
	private HashSet states;
	private String[] inputs;
	
	public Main() {
		
	}
	
	public void init(String[][] table, int type, String[] inp) {
		inputs = inp;
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
					for (int j = 1; j < table[i].length; j++) {
						m.addAdj(table[i][j], inputs[j-1]);
					}					
				}
			}
			break;
		}
		
	}
}
