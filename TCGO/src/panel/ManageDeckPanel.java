package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import database.Connector;

public class ManageDeckPanel extends JPanel {

	ViewPanel viewCards = new ViewPanel(true);
	JPanel panel = new JPanel(new BorderLayout(10, 10));
	JPanel chooseImagePanel = new JPanel(new BorderLayout(5, 5));

	JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JButton saveButton = new JButton("Save");
	JButton deleteButton = new JButton("Delete");
	JLabel nameLabel = new JLabel("Deck Name:");
	JTextField nameField = new JTextField(15);
	JLabel descLabel = new JLabel("Description:");
	JTextField descField = new JTextField(25);
	

	JPanel selectedCardPanel = new JPanel();

	JPanel imagePanel = new JPanel();
	JButton chooseImage = new JButton("Choose Deck Cover");
	JLabel image = new JLabel();

	JScrollPane selectedScrollPane = new JScrollPane(selectedCardPanel,
			JScrollPane.VERTICAL_SCROLLBAR_NEVER,
			JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

	ArrayList<CardPanel> cardPanels = new ArrayList<>();
	ArrayList<Integer> selectedCardIds = new ArrayList<>();

	int total = 0;
	boolean chosenImage = false;
	BufferedImage bi = null;
	
	MouseAdapter mouseAdapter = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {

			CardPanel cardPanel;

			if (((Component) e.getSource()).getParent() instanceof CardPanel) {
				cardPanel = (CardPanel) ((Component) e.getSource()).getParent();
			} else if (((Component) e.getSource()) instanceof CardPanel) {
				cardPanel = (CardPanel) ((Component) e.getSource());
			} else
				return;

			for (CardPanel cp : cardPanels) {
//				System.out.println(cp.getId().equals(cardPanel.getId()));
				if (cp.getId().equals(cardPanel.getId())) {
					if (selectedCardIds.contains(Integer.parseInt(cp.getId()))) {
						if (Integer.parseInt(cp.getQuantity()) >= Integer
								.parseInt(cardPanel.getQuantity())) {
							JOptionPane
									.showMessageDialog(null, "No card left!");
							return;
						} else
							cp.setQuantity((Integer.parseInt(cp.getQuantity()) + 1)
									+ "");
					} else {
						cp.setQuantity("1");
						cp.setImageSize(50, 50);
						cp.setPreferredSize(new Dimension(100,85));
						selectedCardPanel.add(cp);
						selectedCardIds.add(Integer.parseInt(cp.getId()));
					}
					total++;
					revalidate();
					repaint();

					return;
				}
			}

		};
	};
	MouseAdapter copyMouseAdapter = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {

			CardPanel cardPanel;

			if (((Component) e.getSource()).getParent() instanceof CardPanel) {
				cardPanel = (CardPanel) ((Component) e.getSource()).getParent();
			} else if (((Component) e.getSource()) instanceof CardPanel) {
				cardPanel = (CardPanel) ((Component) e.getSource());
			} else
				return;

			for (CardPanel cp : cardPanels) {
				if (cp.getId().equals(cardPanel.getId())) {
					if (selectedCardIds.contains(Integer.parseInt(cp.getId()))) {
						System.out.println("quantity:`"+cp.getQuantity()+"`");
						if (Integer.parseInt(cp.getQuantity()) <= 1) {
							selectedCardPanel.remove(cp);
							selectedCardIds.remove(selectedCardIds
									.indexOf(Integer.parseInt(cp.getId())));
						} else
							cp.setQuantity((Integer.parseInt(cp.getQuantity()) - 1)
									+ "");
					}
					total--;
					revalidate();
					repaint();
					return;
				}
			}

		};
	};

	ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(chooseImage)) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter(
						"Image files", ImageIO.getReaderFileSuffixes()));
				fileChooser.setMultiSelectionEnabled(false);
				if (fileChooser.showDialog(null, "Choose Deck Cover") == JFileChooser.APPROVE_OPTION) {
					try {
						bi = ImageIO.read(fileChooser
								.getSelectedFile());
						image.setIcon(new ImageIcon(bi.getScaledInstance(75,
								75, BufferedImage.SCALE_SMOOTH)));
						chosenImage = true;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}else if (e.getSource().equals(saveButton)) {
				System.out.println(((ImageIcon)image.getIcon()).getImage());
				if (total != 10) {
					JOptionPane.showMessageDialog(null, "Pick exactly 10 cards!");
				}else if(nameField.getText().trim().length() > 10 || nameField.getText().trim().length() < 1){
					JOptionPane.showMessageDialog(null, "Name must be between 1-15 characters!");
				}else if(descField.getText().trim().length() > 25 || descField.getText().trim().length() < 1){
					JOptionPane.showMessageDialog(null, "Description must be between 1-25 characters!");
				}else if(!chosenImage){
					JOptionPane.showMessageDialog(null, "Choose image!");
				}else{
					try {
						String deckid = "";
						if (id == null) {
							
							
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							ImageIO.write(bi, "jpg", baos);
							
							PreparedStatement st = Connector.getInstance().getPreparedStatement("INSERT INTO deck VALUES(null,?,?,?)");
							st.setString(1, nameField.getText().trim());
							st.setString(2, descField.getText().trim());
							st.setBlob(3, new ByteArrayInputStream(baos.toByteArray()));
							st.execute();
							
							ResultSet rs = Connector.getInstance().executeQuery("SELECT * FROM deck ORDER BY id DESC LIMIT 1");
							
							if(rs.next())deckid = rs.getString("id");

							Connector.getInstance().executeUpdate("INSERT INTO deckuser VALUES(" +
									deckid+"," +
											Connector.getInstance().getUserId()+")");

							
							
						}else{
							deckid = id;
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							ImageIO.write(bi, "jpg", baos);
							
							PreparedStatement st = Connector.getInstance().getPreparedStatement("UPDATE deck " +
									"SET name = ?, description = ?, image = ? WHERE id = ?");
							st.setString(1, nameField.getText().trim());
							st.setString(2, descField.getText().trim());
							st.setBlob(3, new ByteArrayInputStream(baos.toByteArray()));
							st.setInt(4, Integer.parseInt(deckid));
							st.execute();
							Connector.getInstance().executeUpdate("DELETE FROM deckdetail WHERE deckid = " + deckid);
						}
						for (int i = 0; i < selectedCardPanel.getComponentCount(); i++) {
							if (selectedCardPanel.getComponent(i) instanceof CardPanel) {
								CardPanel cp = (CardPanel) selectedCardPanel.getComponent(i);
								Connector.getInstance().executeUpdate("INSERT INTO deckdetail VALUES(" +
										deckid+"," +
												cp.getId()+"," +
														cp.getQuantity()+")");
							}
						}
						
						
						JOptionPane.showMessageDialog(null, id == null ? "Add": "Update" + " deck success!");
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e11) {
						// TODO Auto-generated catch block
						e11.printStackTrace();
					}
					
				}
			}else if (e.getSource().equals(deleteButton)) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure to delete deck?") == JOptionPane.YES_OPTION) {
					try {
						Connector.getInstance().executeUpdate("DELETE FROM deck WHERE id = " + id);						
						JOptionPane.showMessageDialog(null, "Delete deck success!");
						mainPanel.changePanel(new ManageDeckPanel(mainPanel, null));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		}
	};

	String id;
	MainPanel mainPanel;
	
	public ManageDeckPanel(MainPanel mainPanel, String id) {
		this.id = id;
		this.mainPanel = mainPanel;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		selectedCardPanel.setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		// viewCards.setPreferredSize(new Dimension(600, 550));

		selectedScrollPane.setViewportView(selectedCardPanel);
		// selectedScrollPane.setPreferredSize(new Dimension(0, 200));

		imagePanel.setBorder(new LineBorder(Color.black));
		imagePanel.setBackground(Color.decode("#eef6ff"));
		
		chooseImage.addActionListener(actionListener);
		saveButton.addActionListener(actionListener);
		deleteButton.addActionListener(actionListener);

		image.setIcon(new ImageIcon(new BufferedImage(75, 75,
				BufferedImage.TYPE_4BYTE_ABGR)));

		fillCard();

		imagePanel.setBackground(Color.decode("#eef6ff"));
		imagePanel.add(image);

		optionPanel.setBackground(Color.decode("#eef6ff"));
		
		optionPanel.add(nameLabel);
		optionPanel.add(nameField);
		optionPanel.add(descLabel);
		optionPanel.add(descField);
		optionPanel.add(saveButton);
		if (id != null) {
			optionPanel.add(deleteButton);			
		}
		optionPanel.add(new JLabel("       "));

		
		chooseImagePanel.setBackground(Color.decode("#eef6ff"));
		chooseImagePanel.add(chooseImage, BorderLayout.NORTH);
		chooseImagePanel.add(imagePanel, BorderLayout.CENTER);
		

		panel.setBackground(Color.decode("#eef6ff"));
		panel.add(chooseImagePanel, BorderLayout.WEST);
		panel.add(selectedScrollPane, BorderLayout.CENTER);

		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.decode("#eef6ff"));
		p.add(new JLabel("           "), BorderLayout.EAST);
		p.add(panel, BorderLayout.CENTER);
		p.add(new JLabel("           "), BorderLayout.WEST);

		add(optionPanel);
		add(p);
		add(Box.createVerticalStrut(10));
		add(viewCards);

	}

	void fillCard() {

		try {
			ResultSet rs = null;
			String [] types = {"Pokemon","Trainer","Energy"};
			
			for (int j = 0; j < types.length; j++) {
				
			
			
			rs = Connector
					.getInstance()
					.executeQuery(
							"SELECT id, name, image, hp, element, rarity, stage, quantity "
									+ "FROM card c JOIN carddetail cd ON c.id = cd.cardid "
									+ "WHERE type = '" +
									types[j]+"' AND userid = "
									+ Connector.getInstance().getUserId());
			ArrayList<JPanel> cards = new ArrayList<>();
			Vector<Vector<String>> vData = new Vector<>();
			Vector<String> vColumnNames = new Vector<>();

			while (rs.next()) {
				String[] columnNames = { "Name", "HP", "Element", "Rarity",
						"Stage", "Quantity" };

				CardPanel cardPanel = new CardPanel(rs.getString("id"), false);
				CardPanel copyCardPanel = new CardPanel(rs.getString("id"),
						true);
				
				cardPanel.setBackground(Color.decode("#8ba4cd"));
				copyCardPanel.setBackground(Color.decode("#8ba4cd"));
				cardPanel.add(null, 0, new ImageIcon(rs.getBytes("image")),
						mouseAdapter,true);
				copyCardPanel.add(null, 0, new ImageIcon(rs.getBytes("image")),
						copyMouseAdapter,true);

				// JLabel l = new JLabel();
				// JLabel l1 = new JLabel();
				// l.setIcon(new ImageIcon(rs.getBytes("image")));
				// l1.setIcon(new ImageIcon(rs.getBytes("image")));
				// gbc.gridx = 0;
				// gbc.gridy = 0;
				// gbc.gridwidth = 3;
				// cardPanel.add(l, gbc);
				// cardPanel1.add(l1, gbc);
				// gbc.gridwidth = 1;

				Vector<String> row = new Vector<String>();

				for (int i = 0; i < columnNames.length; i++) {
					if (vColumnNames.size() < columnNames.length) {
						vColumnNames.add(columnNames[i]);
					}
					row.add(rs.getString(columnNames[i]));

					cardPanel.add(columnNames[i], i,
							rs.getString(columnNames[i]), mouseAdapter,true);
					// copyCardPanel.add(columnNames[i], i,
					// rs.getString(columnNames[i]), copyMouseAdapter);

					// JLabel name = new JLabel("  " + columnNames[i]);
					// JLabel dot = new JLabel(":");
					// JLabel result = new JLabel(rs.getString(columnNames[i]));
					//
					// JLabel name1 = new JLabel("  " + columnNames[i]);
					// JLabel dot1 = new JLabel(":");
					// JLabel result1 = new
					// JLabel(rs.getString(columnNames[i]));
					//
					// gbc.gridx = 0;
					// gbc.gridy = i + 1;
					// cardPanel.add(name, gbc);
					// cardPanel1.add(name1, gbc);
					// gbc.gridx = 1;
					// cardPanel.add(dot, gbc);
					// cardPanel1.add(dot1, gbc);
					// gbc.gridx = 2;
					// cardPanel.add(result, gbc);
					// cardPanel1.add(result1, gbc);
					//
					// name.addMouseListener(mouseAdapter);
					// dot.addMouseListener(mouseAdapter);
					// result.addMouseListener(mouseAdapter);
				}
				// gbc.gridy = columnNames.length + 1;
				// cardPanel.add(new JLabel(""), gbc);
				// cardPanel1.add(new JLabel(""), gbc);
				//
				// cardPanel.setBackground(Color.LIGHT_GRAY);
				// cardPanel1.setBackground(Color.LIGHT_GRAY);
				// cardPanel.addMouseListener(mouseAdapter);
				// l.addMouseListener(mouseAdapter);

				cardPanel.setPreferredSize(new Dimension(150, 200));
				copyCardPanel.setPreferredSize(new Dimension(150, 200));
				
				cardPanel.addMouseListener(mouseAdapter);
				copyCardPanel.addMouseListener(copyMouseAdapter);
				copyCardPanel.setQuantityListener(copyMouseAdapter);

				cards.add(cardPanel);
				cardPanels.add(copyCardPanel);

				vData.add(row);
			}
//			viewCards.fillGridPanel(cards.toArray(new JPanel[] {}),0);
//			viewCards.fillListPanel(vData, vColumnNames);
			viewCards.fillGridPanel(cards.toArray(new JPanel[] {}),0,types[j]);
		}
			if (id != null) {
				
				rs = Connector
						.getInstance()
						.executeQuery(
								"SELECT * "
										+ "FROM deck d "
										+ "WHERE id = "
										+ id);
				rs.next();

				ImageIcon ic = new ImageIcon(rs.getBytes("image"));
								
				image.setIcon(new ImageIcon(
						ic.getImage().getScaledInstance(75, 75, BufferedImage.SCALE_SMOOTH)));
				chosenImage = true;
				nameField.setText(rs.getString("name"));
				descField.setText(rs.getString("description"));
				
				rs = Connector
						.getInstance()
						.executeQuery(
								"SELECT id, image, dd.quantity as quantity "
										+ "FROM card c JOIN deckdetail dd ON c.id = dd.cardid "
										+ "WHERE deckid = "
										+ id);
				while(rs.next()){
//					CardPanel cardPanel = new CardPanel(
//							rs.getString("id"),
//							true);
//					cardPanel.add(null, 0, new ImageIcon(rs.getBytes("image")),						
//							copyMouseAdapter,true);
//					
//					cardPanel.setPreferredSize(new Dimension(150, 200));
//					cardPanel.addMouseListener(copyMouseAdapter);
//					cardPanel.setQuantityListener(copyMouseAdapter);

					for (CardPanel cp : cardPanels) {
						if (cp.getId().equals(rs.getString("id"))) {

							cp.setQuantity(rs.getString("quantity"));
							total+=rs.getInt("quantity");
							cp.setImageSize(50, 50);
							cp.setPreferredSize(new Dimension(100,85));
							selectedCardPanel.add(cp);
							selectedCardIds.add(Integer.parseInt(cp.getId()));
						}
					}

				}
			}

			revalidate();
			repaint();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
