package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import template.TemplateFrame;

public class TermsPanel extends JPanel{

	JFrame mainFrame;
	
	JScrollPane pane = new JScrollPane();
	JLabel agreementLabel = new JLabel("<html>" +
			"Agreement and condition<br>" +
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>"+
			"Lorem ipsum<br>");
	
	JPanel agreeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	JButton agreeButton = new JButton("Agree");
	JButton cancelButton = new JButton("Cancel");
	
	public TermsPanel(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		mainFrame.setTitle("Terms and Conditions");

		setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		setLayout(new BorderLayout());
		
		pane.setViewportView(agreementLabel);
		
		agreeButtonPanel.add(agreeButton);
		agreeButtonPanel.add(cancelButton);
		
		add(pane,BorderLayout.CENTER);
		add(agreeButtonPanel,BorderLayout.SOUTH);

		mainFrame.add(this);
		
		mainFrame.setLocationRelativeTo(null);
		
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	public static void main(String[] args) {
		new TermsPanel(new TemplateFrame());
	}
}
