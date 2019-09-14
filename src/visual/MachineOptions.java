package visual;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MachineOptions extends JPanel implements ActionListener{
	
	public static final String[] CASES = {"Mealy machine","Moore machine"};
	public static final String CREATE = "Create";
	
	private VisualMain m;
	
	private JComboBox<String> select;
	private JTextField inputs;
	private JButton create;
	
	public MachineOptions(VisualMain vis) {
		
		m = vis;
		
		setLayout(new FlowLayout());
		
		select = new JComboBox<String>(CASES);
		select.setSelectedIndex(0);
		JLabel lbl = new JLabel("Select machine type:");
		
		inputs = new JTextField();
		inputs.setPreferredSize(new Dimension(125,20));
		JLabel lblTxt = new JLabel("Set the input symbols:");
		
		create = new JButton(CREATE);
		create.setActionCommand(CREATE);
		create.addActionListener(this);
		add(lbl);
		add(select);
		add(lblTxt);
		add(inputs);
		add(create);
		
	}

	public String getInputs() {
		return inputs.getText();
	}
	
	public String getMachineType() {
		return (String) select.getSelectedItem();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().endsWith(CREATE)) {
			m.initTable(getInputs(), getMachineType());
		}
	}
}
