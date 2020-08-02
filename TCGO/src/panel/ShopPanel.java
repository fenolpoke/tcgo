package panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.Connector;

public class ShopPanel extends JPanel {

	MouseAdapter mouseAdapter = new MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			System.out.println("o");
		};
	};
	
	ActionListener ac = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (((Component)e.getSource()).getParent() instanceof CardPanel) {
				CardPanel cardPanel =(CardPanel) ((Component) e.getSource()).getParent(); 
				if (JOptionPane.showConfirmDialog(null, "Are you sure to buy pack?") == JOptionPane.YES_OPTION) {
					try {
						
						ResultSet rs = Connector.getInstance().executeQuery(
								"SELECT * FROM " +
								"pack p JOIN packdetail pd " +
								"ON p.id = pd.packid WHERE packid = "+cardPanel.getId());
						String price = "";
						while(rs.next()){
							price = rs.getString("price");
							ResultSet rs1 = Connector.getInstance().executeQuery(
									"SELECT * FROM carddetail WHERE userid = " +
									Connector.getInstance().getUserId()+" AND cardid = "
									+rs.getString("cardid"));
							if (rs1.next()) {
								Connector.getInstance().executeUpdate(
										"UPDATE carddetail SET quantity = quantity + " +
										rs.getString("quantity")+" WHERE " +
										"userid = " +
										Connector.getInstance().getUserId()+" AND cardid = "
										+rs.getString("cardid"));
							}else{
								Connector.getInstance().executeUpdate(
										"INSERT INTO carddetail VALUES (" +
												Connector.getInstance().getUserId()+"," +
												rs.getString("cardid")+"," +
												rs.getString("quantity")+")");
							}
						}
						
						Connector.getInstance().executeUpdate("UPDATE user SET money = money - " +
								price+" WHERE id = "+Connector.getInstance().getUserId());						

						JOptionPane.showMessageDialog(null, "Thank you for buying!");
						mainPanel.refreshGreetings();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	};
	MainPanel mainPanel;
	public ShopPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;

		setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		setLayout(new GridLayout(0, 1));
				
		ViewPanel vp = new ViewPanel(false);

		vp.setBackground(Color.decode("#eef6ff"));
		ResultSet rs;
		try {
			ArrayList<JPanel> packs = new ArrayList<>();
			
			rs = Connector.getInstance().executeQuery("SELECT * FROM pack");
			
			while (rs.next()) {				
				
				CardPanel cp = new CardPanel(rs.getString("id"), false);
				cp.setBackground(Color.decode("#8ba4cd"));
				
				cp.addPack(rs.getString("name"),
						rs.getString("description"), rs.getString("price"), ac);
				
//				for (int i = 2; i <= rs.getMetaData().getColumnCount(); i++) {
//					String columnName =rs.getMetaData().getColumnName(i); 
//					cp.add(columnName, i, rs.getString(columnName),mouseAdapter,false);
//				}
				packs.add(cp);
			}
			vp.fillGridPanel(packs.toArray(new JPanel[]{}),420,3,15);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		add(vp);
	}

}
