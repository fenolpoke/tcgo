package panel;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopUpPanel extends JPanel {
	
	JLabel amountLabel = new JLabel("Amount of money:");
	JComboBox<String> amountComboBox = new JComboBox<String>();	
	
	public TopUpPanel() {
		
	}
}
