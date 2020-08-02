package panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardPanel extends JPanel {

	String id;
	
	GridBagConstraints gbc  = new GridBagConstraints();
	
	JLabel quantity = new JLabel("");
	JLabel quantityLabel = new JLabel("Quantity");
	JLabel dotLabel = new JLabel(":");
	
	JLabel l = new JLabel();
	
	public CardPanel(String id, boolean isSelected) {
		this.id = id;
		setLayout(new GridBagLayout());
		setBackground(Color.LIGHT_GRAY);

		if (isSelected) {
			gbc.gridx = 0;
			gbc.gridy = 100;
			gbc.gridwidth = 1;
			add(quantityLabel,gbc);
			gbc.gridx = 1;
			add(dotLabel,gbc);
			gbc.gridx = 2;
			add(quantity,gbc);
			
		}
		
				
	}
	
	public void addPack(String name, String desc, String price, ActionListener ac){
		
		JLabel nameLabel = new JLabel(name);
		JLabel descLabel = new JLabel(desc);
		JLabel priceLabel = new JLabel("Price: "+price);
		JButton buyButton = new JButton("Buy");

		buyButton.addActionListener(ac);
		nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
		
		if (desc.length() > 30) {
			String s = "<html>";
			
			int idx = 0, w = 30;
			while(true){
				if(idx + w > desc.length()){
					s+=desc.substring(idx);
					break;
				}else{
					s+=desc.substring(idx, idx+w)+"<br> ";							
					idx+=w;
				}
			}
			descLabel.setText(s);
			
		}
		
		gbc.gridy = 1;
		add(descLabel,gbc);
		gbc.gridy = 2;
		add(priceLabel,gbc);
		gbc.gridy = 3; gbc.weightx = 0.5;
		add(buyButton,gbc);
		gbc.gridx = 0; gbc.gridy = 0;gbc.insets = new Insets(10, 10, 10, 10);
		add(nameLabel,gbc);
				
	}
	
	public void add(String columnName, int i, Object value, MouseListener mouseListener, boolean inLine){
		if(i < 0 || columnName == null){
			
			ImageIcon img = (ImageIcon) value;
//			JLabel l = new JLabel();		
			
			if(img.getImage().getHeight(null) > 125 && img.getImage().getWidth(null) > 100){
				l.setIcon(new ImageIcon(
					img.getImage().getScaledInstance(100, 125, BufferedImage.SCALE_SMOOTH)));
			}else l.setIcon(img);
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 3;
			
			add(l, gbc);
			l.addMouseListener(mouseListener);
		}else{
			gbc.gridwidth = 1;
			JLabel name = new JLabel(columnName.substring(0,1).toUpperCase()+columnName.substring(1));
			JLabel dot = new JLabel(":");
			JLabel result = new JLabel((String) value);

			
			if(inLine){				
				gbc.gridx = 0;
				gbc.gridy = i + 1;
				add(name, gbc);
				gbc.gridx = 1;
				add(dot, gbc);
				gbc.gridx = 2;
				add(result, gbc);	
				name.addMouseListener(mouseListener);
				dot.addMouseListener(mouseListener);
				result.addMouseListener(mouseListener);
			}else{
				if (result.getText().length() > 30 || columnName.equalsIgnoreCase("description")) {
					String s = "<html>";
					
					int idx = 0, w = 30;
					while(true){
						if(idx + w > result.getText().length()){
							s+=result.getText().substring(idx);
							break;
						}else{
							s+=result.getText().substring(idx, idx+w)+"<br> ";							
							idx+=w;
						}
					}
					result.setText(s);
					System.out.println(s);
//					result.setPreferredSize(new Dimension(130, 100));				
//					setAlignmentX(JComponent.CENTER_ALIGNMENT);
					

				}

				
				gbc.gridx = 0;
				gbc.gridy = (i*2) + 1;
//				name.setText(name.getText().substring(0,1).toUpperCase()+name.getText().substring(1));
//				add(name, gbc);
//				gbc.gridx = 1;
//				add(dot, gbc);
				gbc.gridy = (i+1)*2;
				gbc.gridx = 0; //gbc.gridwidth = 3;
				add(result, gbc);
				name.addMouseListener(mouseListener);
				dot.addMouseListener(mouseListener);
				result.addMouseListener(mouseListener);
			}
			
			
			
			if (columnName.equalsIgnoreCase("Quantity")) {
				quantity.setText((String) value);
			}
		}

	}
	
	public void setImageSize(int w, int h){
		l.setIcon(new ImageIcon(((ImageIcon)l.getIcon()).getImage().getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH)));
	}
	public String getId(){return id;}
	public String getQuantity(){return quantity.getText();}
	public void setQuantity(String quantity){this.quantity.setText(quantity);}
	public void setQuantityListener(MouseListener mouseListener){
		quantity.addMouseListener(mouseListener);
		quantityLabel.addMouseListener(mouseListener);
		dotLabel.addMouseListener(mouseListener);
	}
}
