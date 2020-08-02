package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.security.Key;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.Connector;

import template.TemplateFrame;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = -2927345740624978388L;

	JFrame mainFrame;

	// JPanel loginFieldPanel = new JPanel(new GridBagLayout());

	JLabel usernameLabel = new JLabel("User Name");
	JTextField usernameField = new JTextField(20);

	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordField = new JPasswordField(20);

	JLabel noAccountLabel = new JLabel("Don't have account?");
	JButton registerButton = new JButton("Register!");

	JButton loginButton = new JButton("Login");

	KeyAdapter keyAdapter = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				doLogin();
			}
		};
	};

	ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(loginButton)) {
				doLogin();
			} else if (e.getSource().equals(registerButton)) {
				new RegisterPanel(mainFrame);
			}
		}
	};

	public LoginPanel(JFrame mainFrame) {

		this.mainFrame = mainFrame;
		this.mainFrame.setTitle("Login");

		setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		Component c = ((BorderLayout) this.mainFrame.getContentPane()
				.getLayout()).getLayoutComponent(BorderLayout.CENTER);
		if (c != null)
			this.mainFrame.remove(c);

		setLayout(new GridBagLayout());

		// because the size is enormous
		this.mainFrame.setSize(400, 200);

		// listen to this
		usernameField.addKeyListener(keyAdapter);
		passwordField.addKeyListener(keyAdapter);
		loginButton.addActionListener(actionListener);
		registerButton.addActionListener(actionListener);

		// giving constraints and adding panel as f to everywhere
		GridBagConstraints gbc = new GridBagConstraints();
		// user names
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		gbc.weighty = 0.1;
		add(usernameLabel, gbc);
		gbc.gridy = 1;
		add(usernameField, gbc);

		// passwords
		gbc.gridy = 2;
		add(passwordLabel, gbc);
		gbc.gridy = 3;
		add(passwordField, gbc);

		gbc.gridy = 4;
		add(loginButton, gbc);

		// register
		gbc.gridwidth = 1;
		gbc.gridy = 5;
		gbc.weighty = 0.5;
		add(noAccountLabel, gbc);
		gbc.gridx = 1;
		add(registerButton, gbc);

		this.mainFrame.add(this);

		usernameField.requestFocus();

		// Just why indeed
		this.mainFrame.setLocationRelativeTo(null);
		// revalidate();
		// repaint();
	}

	void doLogin() {
		try {
			
			if (usernameField.getText().isEmpty() || passwordField.getPassword().toString().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fill username and password!");
			}else{

				ResultSet rs = Connector
						.getInstance()
						.executeQuery(
								"SELECT * FROM user WHERE username='"
										+ usernameField.getText().trim()
										+ "' AND password='"
										+ new String(passwordField.getPassword()).trim() + "'");
				
				if (rs.next())
				{
					JOptionPane.showMessageDialog(null, "Login Success!");
					Connector.getInstance().setUserId(rs.getInt("id"));
					new MainPanel(mainFrame);
				}else{
					JOptionPane.showMessageDialog(null, "Wrong username and password!");
					usernameField.requestFocus();
				}
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No database available!");
			e.printStackTrace();
			
		}
	}

}
