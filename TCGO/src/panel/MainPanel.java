package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.Connector;

import template.TemplateFrame;

public class MainPanel extends JPanel {

	JFrame mainFrame;
	
	JPanel menuPanel = new JPanel(new GridLayout(1, 2));

	JPanel barPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JMenuBar menuBar = new JMenuBar();

	JMenu userMenu = new JMenu("Manage User");
	JMenuItem profileMenuItem = new JMenuItem("Manage Profile");
	JMenuItem logoutMenuItem = new JMenuItem("Logout");

	JMenu cardMenu = new JMenu("Manage Card");
	JMenuItem addDeckMenuItem = new JMenuItem("Add Deck");
	JMenuItem deckMenuItem = new JMenuItem("Manage Deck");
	JMenuItem packMenuItem = new JMenuItem("Challenge for Pack");

	JMenu shopMenu = new JMenu("Shop");
	JMenuItem browsePackMenuItem = new JMenuItem("Browse Pack");
	JMenuItem topUpMenuItem = new JMenuItem("Top Up Money");

	JPanel moneyPanel = new JPanel();
	JLabel greetingsLabel = new JLabel();
	JComboBox<String> amountComboBox = new JComboBox<String>();
	JButton topUpButton = new JButton("Top Up");
	
	ActionListener menuItemListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			Component c = ((BorderLayout) getLayout())
					.getLayoutComponent(BorderLayout.CENTER);
			JPanel panel = null;
			Object source = e.getSource();

			if (source.equals(profileMenuItem)) {
				panel = new ProfilePanel();
			} else if (source.equals(packMenuItem)) {

				try {
					ResultSet rs;
					int yourScore, theirScore, count;
					
					rs = Connector.getInstance().executeQuery(
							"SELECT * FROM user ORDER BY id DESC LIMIT 1");	
					rs.next();
					count = Integer.parseInt(rs.getString("id"));
					
					rs = Connector.getInstance().executeQuery(
							"SELECT u.id, " +
							"SUM(if(rarity = 'common',1,if(rarity = 'medium', 2,if(rarity = 'rare',3,4)))*quantity) as totalhp " +
							"FROM user u " +
							"JOIN carddetail cd ON u.id = cd.userid " +
							"JOIN card c ON c.id = cd.cardid " +
							"WHERE u.id = "+Connector.getInstance().getUserId());	
					rs.next();
					yourScore = Integer.parseInt(rs.getString("totalhp"));
					while(true){
						rs = Connector.getInstance().executeQuery(
								"SELECT u.id, " +
								"SUM(if(rarity = 'common',1,if(rarity = 'medium', 2,if(rarity = 'rare',3,4)))*quantity) as totalhp " +
								"FROM user u " +
								"JOIN carddetail cd ON u.id = cd.userid " +
								"JOIN card c ON c.id = cd.cardid " +
								"WHERE u.id = "+new Random().nextInt(count) 
								+ " AND u.id <> "+Connector.getInstance().getUserId());						
						if(rs.next()) {
							if (rs.getString("totalhp") == null) {
								continue;
							}else{
								theirScore = Integer.parseInt(rs.getString("totalhp"));
								break;								
							}
						}
					}

					if (yourScore < theirScore) {
						JOptionPane.showMessageDialog(null, "You lose!");
					}else{
						Connector.getInstance().executeUpdate("UPDATE user SET money = money + 250 " +
								"WHERE id = "+Connector.getInstance().getUserId());
						JOptionPane.showMessageDialog(null, "You won and get some money!");
						refreshGreetings();
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} else if (source.equals(deckMenuItem)) {
				panel = new DeckPanel(getMe());
			} else if (source.equals(logoutMenuItem)) {
				mainFrame.dispose();
				new LoginPanel(new TemplateFrame());
				return;
			} else if (source.equals(browsePackMenuItem)) {
				panel = new ShopPanel(getMe());
			} else if (source.equals(topUpMenuItem)) {
				panel = new TopUpPanel();
			} else if (source.equals(addDeckMenuItem)) {
				panel = new ManageDeckPanel(getMe(), null);
			}
			if (panel != null) {
				if (c != null) {
					remove(((BorderLayout) getLayout())
							.getLayoutComponent(BorderLayout.CENTER));
				}
				add(panel, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		}
	};

	
	public MainPanel(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		mainFrame.setSize(850,500);
		mainFrame.setTitle("TCG on the GO");

		setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		menuPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		barPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		moneyPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		amountComboBox.addItem("100");
		amountComboBox.addItem("500");
		amountComboBox.addItem("1000");
		
		Component c = ((BorderLayout) this.mainFrame.getContentPane()
				.getLayout()).getLayoutComponent(BorderLayout.CENTER);
		if (c != null)
			this.mainFrame.remove(c);

		setLayout(new BorderLayout());

		// listen this
		profileMenuItem.addActionListener(menuItemListener);
		logoutMenuItem.addActionListener(menuItemListener);
		deckMenuItem.addActionListener(menuItemListener);
		packMenuItem.addActionListener(menuItemListener);
		browsePackMenuItem.addActionListener(menuItemListener);
		topUpMenuItem.addActionListener(menuItemListener);
		addDeckMenuItem.addActionListener(menuItemListener);
		topUpButton.addActionListener(menuItemListener);

		userMenu.add(profileMenuItem);
		userMenu.add(logoutMenuItem);

		cardMenu.add(addDeckMenuItem);
		cardMenu.add(deckMenuItem);
		cardMenu.add(packMenuItem);

		shopMenu.add(browsePackMenuItem);
//		shopMenu.add(topUpMenuItem);

		menuBar.add(userMenu);
		menuBar.add(cardMenu);
		menuBar.add(shopMenu);

//		moneyPanel.add(greetingsLabel);
//		moneyPanel.add(amountComboBox);
//		moneyPanel.add(topUpButton);
		
		barPanel.add(menuBar);
		
		menuPanel.add(barPanel);
		menuPanel.add(greetingsLabel);

		add(menuPanel, BorderLayout.NORTH);

		mainFrame.add(this);

		refreshGreetings();
		
		mainFrame.setLocationRelativeTo(null);
		revalidate();
		repaint();
	}

	public void changePanel(JPanel panel){
		Component c = ((BorderLayout) getLayout())
				.getLayoutComponent(BorderLayout.CENTER);
		if (panel != null) {
			if (c != null) {
				remove(((BorderLayout) getLayout())
						.getLayoutComponent(BorderLayout.CENTER));
			}
			add(panel, BorderLayout.CENTER);
			revalidate();
			repaint();
		}
	}
	
	public void refreshGreetings(){
		try {
			ResultSet rs = Connector.getInstance().executeQuery("SELECT * FROM user WHERE id = "
					+ Connector.getInstance().getUserId());
			if (rs.next()) {
				greetingsLabel.setText("Hi, "+rs.getString("screenname")+". Money: "+ rs.getString("money"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public MainPanel getMe(){return this;}
	public static void main(String[] args) {
		new MainPanel(new TemplateFrame());
	}

}
