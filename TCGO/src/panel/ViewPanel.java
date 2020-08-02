package panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ViewPanel extends JPanel {

	enum VIEW_TYPE{LIST,GRID}
	
	JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	JButton listButton = new JButton("List View");
	JButton gridButton = new JButton("Grid View");
	
	JPanel viewPanel = new JPanel(new CardLayout());
	
	JPanel listPanel = new JPanel();
	JPanel gridPanel = new JPanel();
	JTable table;
	
	JTabbedPane gridTabbedPane = new JTabbedPane();
	JTabbedPane listTabbedPane = new JTabbedPane();
	
	JPanel pokemonPanel = new JPanel(new BorderLayout());
	JPanel trainerPanel = new JPanel(new BorderLayout());
	JPanel energyPanel = new JPanel(new BorderLayout());
	
	ActionListener actionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cl = (CardLayout) viewPanel.getLayout();
			if (e.getSource().equals(listButton)) {
				cl.show(viewPanel, VIEW_TYPE.LIST.name());
			}else if (e.getSource().equals(gridButton)) {
				cl.show(viewPanel, VIEW_TYPE.GRID.name());				
			}
		}
	};
	
	MouseAdapter mouseAdapter = new MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			if (table.getSelectedRow() >= 0) {
				
			}
		};
	};
	
	public ViewPanel(boolean showOption) {
		setLayout(new BorderLayout());				

		setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));

		pokemonPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		trainerPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		energyPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		gridTabbedPane.add("Pokemon", pokemonPanel);
		gridTabbedPane.add("Trainer", trainerPanel);
		gridTabbedPane.add("Energy", energyPanel);
		
		listButton.addActionListener(actionListener);
		gridButton.addActionListener(actionListener);
		
		listPanel.add(new JLabel("list"));
		
		gridPanel.add(new JLabel("grid"));
		
		viewPanel.add(gridPanel,VIEW_TYPE.GRID.name());		
		viewPanel.add(listPanel,VIEW_TYPE.LIST.name());
				
		optionPanel.add(listButton);
		optionPanel.add(gridButton);
		
//		if(showOption)add(optionPanel,BorderLayout.NORTH);
		if(!showOption)add(viewPanel,BorderLayout.CENTER);
		else add(gridTabbedPane,BorderLayout.CENTER);
	}
	
//	public void fillGridPanel(JPanel[] panels, int){
//		gridPanel.removeAll();
//		
//		if (panels.length < 1) {
//			gridPanel.add(new JLabel("Empty!"));
//			return;
//		}
//		
//		JPanel cardPanel = new JPanel(new GridLayout(0, 5,5,5));				
//		JPanel scrollPanel = new JPanel();
//				
//		for (JPanel jPanel : panels) {
//			cardPanel.add(jPanel);
//		}
//		scrollPanel.add(cardPanel);
//
//		JScrollPane pane = new JScrollPane(scrollPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		
//		gridPanel.add(pane);
//	}
	
	public void fillGridPanelHorizontally(JPanel[] panels){
		gridPanel.removeAll();
		
		if (panels.length < 1) {
			gridPanel.add(new JLabel("Empty!"));
			return;
		}
		
		JPanel cardPanel = new JPanel(new GridLayout(1, 0,5,5));				
		JPanel scrollPanel = new JPanel();
		
		
		cardPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		scrollPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		for (JPanel jPanel : panels) {
			cardPanel.add(jPanel);
		}
		scrollPanel.add(cardPanel);

		JScrollPane pane = new JScrollPane(scrollPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		pane.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		
		gridPanel.add(pane);
	}
	
	public void fillGridPanel(JPanel[] panels, int height, int column, int gap){
		gridPanel.removeAll();
		
		if (panels.length < 1) {
			gridPanel.add(new JLabel("Empty!"));
			return;
		}
		
		JPanel cardPanel = new JPanel(new GridLayout(0, column,gap,gap));				
		JPanel scrollPanel = new JPanel();

		cardPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		scrollPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		for (JPanel jPanel : panels) {
			cardPanel.add(jPanel);
		}
		scrollPanel.add(cardPanel);

		JScrollPane pane = new JScrollPane(scrollPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setPreferredSize(new Dimension(800, height > 0 ? height : 230));
		
		pane.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		gridPanel.add(pane);

	}
	public void fillListPanel(Vector<Vector<String>> data, Vector<String> columnNames){
		listPanel.removeAll();
		
		if (data.isEmpty()) {
			listPanel.add(new JLabel("Empty!"));
			return;
		}
		
		DefaultTableModel dtm =  new DefaultTableModel(data, columnNames);
		table = new JTable(dtm);
		
		
		JScrollPane pane = new JScrollPane(table);
		pane.setPreferredSize(new Dimension(780, 200));
		
		pane.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		listPanel.add(pane);

	}
	
	

	public void fillGridPanel(JPanel[] panels, int height, String type){
		JPanel currentPanel = null;
		if(type.equalsIgnoreCase("Pokemon")){
			currentPanel = pokemonPanel;
		}else if(type.equalsIgnoreCase("Trainer")){
			currentPanel = trainerPanel;
		}else if(type.equalsIgnoreCase("Energy")){
			currentPanel = energyPanel;
		}
		
		if (panels.length < 1) {
			currentPanel.add(new JLabel("Empty!"));
			return;
		}
		
		JPanel cardPanel = new JPanel(new GridLayout(0, 5,5,5));				
		JPanel scrollPanel = new JPanel();
		
		cardPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		scrollPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		
		for (JPanel jPanel : panels) {
			cardPanel.add(jPanel);
		}
		scrollPanel.add(cardPanel);

		JScrollPane pane = new JScrollPane(scrollPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setPreferredSize(new Dimension(800, height > 0 ? height : 230));
		
		pane.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		currentPanel.add(pane);

	}
	
}
