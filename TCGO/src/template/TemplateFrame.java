package template;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TemplateFrame extends JFrame{
	
	private static final long serialVersionUID = 310707543968984795L;

	//components
	JPanel menuBarPanel = new JPanel(new BorderLayout());
	JButton exitButton = new JButton("X");
	JPanel titlePanel = new JPanel(new FlowLayout());
	JLabel titleLabel = new JLabel("TCG On the GO");
	
	public TemplateFrame() {
				
		//main configurations
		setTitle("TCG On the GO");
		setSize(1000,500);		
		setBackground(Color.decode("#205a73"));
		setForeground(Color.decode("#eef6ff"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setResizable(false);
		
		//components configurations
		titlePanel.setBackground(Color.decode("#205a73"));
		titleLabel.setForeground(Color.decode("#eef6ff"));

		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);				
			}
		});
		
		//adding all things in each places
		titlePanel.add(titleLabel);		
		
		menuBarPanel.add(titlePanel,BorderLayout.CENTER);
		menuBarPanel.add(exitButton,BorderLayout.EAST);
				
		//default layout is border
		add(menuBarPanel,BorderLayout.NORTH);
		//final touch
		setVisible(true);
	}
//	@Override
//	public Component add(Component comp) {
//		Component c = super.add(comp);
//				add(comp,BorderLayout.CENTER);
//		return c;
//	}
	
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		super.setTitle(title);
		titleLabel.setText(title);
	}
}
