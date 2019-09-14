package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.Main;

public class VisualMain extends JFrame{

	Main m;
	
	MealyInputPanel mealyPanel;
	MooreInputPanel moorePanel;
	OptionsPanel options;
	OutputPanel output;
	MachineOptions machineOptions;
	
	public VisualMain() {
		m = new Main();
		
		setLayout(new BorderLayout());
		
		options = new OptionsPanel(this);
		output = new OutputPanel(this);
		mealyPanel = new MealyInputPanel();
		moorePanel = new MooreInputPanel();
		machineOptions = new MachineOptions(this);
		
		JPanel aux = new JPanel(new FlowLayout());
		aux.add(mealyPanel);
		aux.add(moorePanel);
		
		add(machineOptions, BorderLayout.NORTH);
		add(aux, BorderLayout.WEST);
		add(output, BorderLayout.EAST);
		add(options, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	public void initTable(String inputs, String type) {
		String[] symbols = inputs.split(",");
		switch(type) {
		case "Mealy machine":
			mealyPanel.init(symbols);
			moorePanel.setVisible(false);
			mealyPanel.setVisible(true);
			break;
		
		case "Moore machine":
			moorePanel.init(symbols);
			mealyPanel.setVisible(false);
			moorePanel.setVisible(true);
			break;
		}
		options.activateBtns();
		machineOptions.deactivateBtns();
		this.validate();
		pack();
	}
	
	public void reduce() {
		String[][] values;
		String[] inputs = machineOptions.getInputs().split(",");
		if(machineOptions.getMachineType().equals("Mealy machine")) {
			values = mealyPanel.getValues();
			m.init(values, m.MEALY, inputs);
			m.reduce();
		}
		else {
			values = moorePanel.getValues();
			m.init(values, m.MOORE, inputs);
			m.reduce();
		}
	}
	public void clear() {
		moorePanel.setVisible(false);
		mealyPanel.setVisible(false);
		machineOptions.activateBtns();
		options.deactivateBtns();
		validate();
		pack();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			VisualMain v = new VisualMain();
			v.setVisible(true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} 
	}
}
