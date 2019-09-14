package visual;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel implements ActionListener{
	
	public static final String REDUCE = "Reduce";
	public static final String CLEAR = "Clear";
	
	private JButton reduce;
	private JButton clear;
	
	private VisualMain m;

	public OptionsPanel(VisualMain visualMain) {
		m = visualMain;
		setLayout(new FlowLayout());
		
		reduce = new JButton(REDUCE);
		reduce.addActionListener(this);
		reduce.setActionCommand(REDUCE);
		
		clear = new JButton(CLEAR);
		clear.addActionListener(this);
		clear.setActionCommand(CLEAR);
		
		deactivateBtns();
		add(reduce);
		add(clear);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equals(REDUCE)) {
			m.reduce();
		}
		else if(action.equals(CLEAR)) {
			m.clear();
		}
	}
	
	public void deactivateBtns() {
		reduce.setEnabled(false);
		clear.setEnabled(false);
		
	}
	
	public void activateBtns() {
		reduce.setEnabled(true);
		clear.setEnabled(true);
	}

}
