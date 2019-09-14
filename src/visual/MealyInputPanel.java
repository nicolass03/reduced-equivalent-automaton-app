package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MealyInputPanel extends JPanel{

	private JTable table;
	private JScrollPane scroll;
	
	public MealyInputPanel() {
		setLayout(new FlowLayout());
	}
	
	public void init(String[] sym) {
		
		if(scroll != null)
			remove(scroll);
		
		String[] col = new String[sym.length+1]; 
		col[0] = "States";
		for(int i = 0; i < sym.length; i++) {
			col[i+1] = sym[i];
		}
		
		DefaultTableModel model = new DefaultTableModel(col,15);
		table = new JTable(model);
		table.setShowGrid(true);
		scroll = new JScrollPane(table);
		add(scroll, BorderLayout.CENTER);
		repaint();
		validate();
	}
	
	public String[][] getValues(){
		String[][] values = new String[table.getRowCount()][table.getColumnCount()];
		boolean done = false;
		for(int i = 0; i < table.getRowCount(); i++) {
			for(int j = 0; j < table.getColumnCount(); j++) {
				if(table.getValueAt(i, j) == null || table.getValueAt(i, j).equals("")) {
					done = true;
					break;					
				}
				else {
					values[i][j] = (String) table.getValueAt(i, j);
				}
			}
			if(done)
				break;
		}
		return values;
	}

}
