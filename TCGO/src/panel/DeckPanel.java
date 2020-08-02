package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Connector;

public class DeckPanel extends JPanel {

	MainPanel mainPanel;

	MouseAdapter mouseAdapter = new MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			CardPanel cardPanel;

			if (((Component) e.getSource()).getParent() instanceof CardPanel) {
				cardPanel = (CardPanel) ((Component) e.getSource()).getParent();
			} else if (((Component) e.getSource()) instanceof CardPanel) {
				cardPanel = (CardPanel) ((Component) e.getSource());
			} else
				return;

			mainPanel.changePanel(new ManageDeckPanel(mainPanel, cardPanel.getId()));

			revalidate();
			repaint();
		}

	};

	public DeckPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;

		setBackground(new Color(255, 222, 0));
		setLayout(new GridLayout(0, 1));
		ViewPanel vp = new ViewPanel(false);

		try {
			ResultSet rs = Connector.getInstance().executeQuery(
					"SELECT DISTINCT id,name,description,image "
							+ "FROM deck d JOIN deckdetail dd ON "
							+ "d.id = dd.deckid"
							+ " JOIN deckuser du ON d.id = du.deckid"
							+ " WHERE userid = "
							+ Connector.getInstance().getUserId());
			ArrayList<JPanel> cards = new ArrayList<>();
			Vector<Vector<String>> vData = new Vector<>();
			Vector<String> vColumnNames = new Vector<>();

			while (rs.next()) {
				// String[] columnNames = { "Name", "HP", "Element", "Rarity",
				// "Stage","Quantity" };

				CardPanel cardPanel = new CardPanel(rs.getString("id"), false);
				cardPanel.setBackground(Color.decode("#8ba4cd"));
				cardPanel.add(null, 0, new ImageIcon(rs.getBytes("image")),
						mouseAdapter, false);
				Vector<String> row = new Vector<String>();

				for (int i = 2; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getMetaData().getColumnName(i)
							.equalsIgnoreCase("image")) {
						continue;
					}
					if (vColumnNames.size() < rs.getMetaData().getColumnCount()) {
						vColumnNames.add(rs.getMetaData().getColumnName(i));
					}
					row.add(rs.getString(rs.getMetaData().getColumnName(i)));

					cardPanel.add(rs.getMetaData().getColumnName(i), i,
							rs.getString(rs.getMetaData().getColumnName(i)),
							mouseAdapter, false);
				}

				cardPanel.addMouseListener(mouseAdapter);

				cards.add(cardPanel);

				vData.add(row);
			}
//			if (cards.isEmpty()) {
//				JPanel p = new JPanel(new BorderLayout());
//				p.add(new JLabel("Nothing!"), BorderLayout.EAST);
//				cards.add(p);
//			}
			vp.fillGridPanel(cards.toArray(new JPanel[] {}), 420,4,15);
			vp.fillListPanel(vData, vColumnNames);
			revalidate();
			repaint();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		add(vp);
	}
}
