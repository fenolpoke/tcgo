package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.activation.MailcapCommandMap;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import database.Connector;

import template.TemplateFrame;

public class RegisterPanel extends JPanel {
	private static final long serialVersionUID = -5703663671081862354L;

	JFrame mainFrame;

	// the mother of panels (the only one)
	// JPanel mainPanel = new JPanel(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();

	// components in places
	JLabel titleLabel = new JLabel("ALL FIELDS ARE REQUIRED.");

	JLabel dOBLabel = new JLabel("Date of Birth (yyyy-MM-dd)");
	JTextField dOBField = new JTextField(20);

	JLabel usernameLabel = new JLabel("Username");
	JTextField usernameField = new JTextField(20);
	JButton checkUsernameAvailableButton = new JButton("Check Availability");

	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordField = new JPasswordField(20);

	JLabel confirmPasswordLabel = new JLabel("Confirm Password");
	JPasswordField confirmPasswordField = new JPasswordField(20);

	JLabel emailLabel = new JLabel("Email Address");
	JTextField emailField = new JTextField(20);

	JLabel confirmEmailLabel = new JLabel("Confirm Email");
	JTextField confirmEmailField = new JTextField(20);

	JCheckBox receiveMarketingCheckBox = new JCheckBox("<html>"
			+ "I would like to receive marketing email messages from <br>"
			+ "The Pokemon Company International.");

	JLabel displayProfileLabel = new JLabel("Do you want to display your "
			+ "Pokemon Trainer Club profile publicly?");
	JRadioButton yesRadioButton = new JRadioButton("Yes");
	JRadioButton noRadioButton = new JRadioButton("No");
	ButtonGroup displayProfileGroup = new ButtonGroup();

	JLabel screenNameLabel = new JLabel("Screen Name");
	JTextField screenNameField = new JTextField(20);
	JButton checkScreenNameAvailableButton = new JButton("Check Availability");

	JButton registerButton = new JButton("Continue");
	JButton backButton = new JButton("Back");

	ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(registerButton)) {
				if (registerButton.getText().equals("Continue")) {

					// legal age only
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
					Calendar now = Calendar.getInstance(), dob = Calendar
							.getInstance();

					try {
						dob.setTime(sdf.parse(dOBField.getText().trim()));
						if (Integer
								.parseInt(dOBField.getText().substring(5, 7)) > 12)
							throw new ParseException("", 0);
						if (Integer.parseInt(dOBField.getText()
								.substring(8, 10)) > 31)
							throw new ParseException("", 0);
					} catch (ParseException ee) {
						JOptionPane.showMessageDialog(null, "Wrong format!");
						dOBField.requestFocus();
						return;
					} catch (NumberFormatException ee) {
						JOptionPane.showMessageDialog(null, "Wrong format!");
						dOBField.requestFocus();
						return;
					}

					if (now.get(Calendar.YEAR) - dob.get(Calendar.YEAR) >= 11) {

						registerButton.setText("Register");

						removeAll();
						setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
						
						// field required label
						gbc.gridx = 0;
						gbc.gridy = 0;
						gbc.gridwidth = 3;
						add(titleLabel, gbc);

						gbc.gridwidth = 2;

						// user name
						gbc.gridy = 1;
						add(usernameLabel, gbc);
						gbc.gridx = 2;
						gbc.gridwidth = 1;
						add(usernameField, gbc);
						gbc.gridy = 2;
						add(checkUsernameAvailableButton, gbc);

						// password
						gbc.gridx = 0;
						gbc.gridy = 3;
						gbc.gridwidth = 2;
						add(passwordLabel, gbc);
						gbc.gridx = 2;
						gbc.gridwidth = 1;
						add(passwordField, gbc);

						// confirm password
						gbc.gridx = 0;
						gbc.gridy = 4;
						gbc.gridwidth = 2;
						add(confirmPasswordLabel, gbc);
						gbc.gridx = 2;
						gbc.gridwidth = 1;
						add(confirmPasswordField, gbc);

						// email
						gbc.gridx = 0;
						gbc.gridy = 5;
						gbc.gridwidth = 2;
						add(emailLabel, gbc);
						gbc.gridx = 2;
						gbc.gridwidth = 1;
						add(emailField, gbc);

						// confirm email
						gbc.gridx = 0;
						gbc.gridy = 6;
						gbc.gridwidth = 2;
						add(confirmEmailLabel, gbc);
						gbc.gridx = 2;
						gbc.gridwidth = 1;
						add(confirmEmailField, gbc);

						// agreement check box
						gbc.gridx = 0;
						gbc.gridy = 7;
						gbc.gridwidth = 3;
						gbc.gridheight = 2;
						add(receiveMarketingCheckBox, gbc);

						gbc.gridheight = 1;

						// display profile
						gbc.gridx = 0;
						gbc.gridy = 9;
						gbc.gridwidth = 3;
						add(displayProfileLabel, gbc);
						gbc.gridwidth = 1;
						gbc.gridy = 10;
						add(yesRadioButton, gbc);
						gbc.gridx = 1;
						add(noRadioButton, gbc);

						yesRadioButton.setSelected(true);

						// screen name
						gbc.gridx = 0;
						gbc.gridy = 11;
						gbc.gridwidth = 2;
						add(screenNameLabel, gbc);
						gbc.gridx = 2;
						gbc.gridwidth = 1;
						add(screenNameField, gbc);
						gbc.gridy = 12;
						add(checkScreenNameAvailableButton, gbc);

						// button button
						gbc.gridx = 0;
						gbc.gridy = 13;
						gbc.gridwidth = 2;
						add(backButton, gbc);
						gbc.gridx = 2;
						gbc.gridwidth = 1;
						add(registerButton, gbc);

						// because of no use
						// registerButton.removeActionListener(this);
						if (mainFrame != null) {
							mainFrame.setSize(400, 500);
							mainFrame.setLocationRelativeTo(null);
						}
						revalidate();
						repaint();

					} else {
						JOptionPane
								.showMessageDialog(null,
										"You must be at least 11 years old! Going back to login..");
						new LoginPanel(mainFrame);
					}
				} else if (registerButton.getText().equals("Register")) {
					if (usernameField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Fill username!");
					} else if (!checkUsernameAvailableButton.getText().equals(
							"Available")) {
						JOptionPane.showMessageDialog(null,
								"Check username availability!");
					} else if (new String(passwordField.getPassword())
							.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Fill password!");
					} else if (!Arrays.equals(passwordField.getPassword(),
							confirmPasswordField.getPassword())) {
						JOptionPane.showMessageDialog(null,
								"Password must be confirmed!");
					} else if (emailField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Fill email!");
					} else if (!checkEmail()) {
						JOptionPane.showMessageDialog(null, "Wrong email format!");
					} else if (!emailField.getText().trim()
							.equals(confirmEmailField.getText().trim())) {
						JOptionPane.showMessageDialog(null,
								"Email must be confirmed!");
					} else if (!receiveMarketingCheckBox.isSelected()) {
						JOptionPane.showMessageDialog(null,
								"You must receive marketing notification!");
					} else if (screenNameField.getText().isEmpty()) {
						JOptionPane
								.showMessageDialog(null, "Fill screen name!");
					} else if (!checkScreenNameAvailableButton.getText()
							.equals("Available")) {
						JOptionPane.showMessageDialog(null,
								"Check screen name availability!");
					} else {
						try {
							Connector.getInstance().executeUpdate(
									"INSERT INTO user VALUES "
											+ "(NULL,'"
											+ screenNameField.getText().trim()
											+ ""
											+ "',1000,'"
											+ usernameField.getText().trim()
											+ "','"
											+ new String(passwordField
													.getPassword()) + "','"
											+ dOBField.getText() + "','"
											+ emailField.getText().trim()
											+ "')");
							JOptionPane
									.showMessageDialog(null,
											"Register Success! Returning to login form");
							new LoginPanel(mainFrame);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			} else if (e.getSource().equals(backButton)) {
				new LoginPanel(mainFrame);
			} else if (e.getSource().equals(checkScreenNameAvailableButton)) {
				checkAvailability("screenname");
			} else if (e.getSource().equals(checkUsernameAvailableButton)) {
				checkAvailability("username");
			}
		}
	};

	KeyAdapter keyAdapter = new KeyAdapter() {
		public void keyTyped(KeyEvent e) {
			if (e.getSource().equals(screenNameField)) {
				checkScreenNameAvailableButton.setText("Check Availability");
			} else if (e.getSource().equals(usernameField)) {
				checkUsernameAvailableButton.setText("Check Availability");
			}
		};
	};

	public RegisterPanel() {

		setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		initializeComponents();
		revalidate();
		repaint();
	}

	public RegisterPanel(JFrame mainFrame) {

		this.mainFrame = mainFrame;
		this.mainFrame.setTitle("Register");

		Component c = ((BorderLayout) this.mainFrame.getContentPane()
				.getLayout()).getLayoutComponent(BorderLayout.CENTER);
		if (c != null)
			this.mainFrame.remove(c);

		initializeComponents();

		// time to shine
		mainFrame.setSize(400, 100);
		mainFrame.add(this, BorderLayout.CENTER);

		// because life sucks
		mainFrame.setLocationRelativeTo(null);
		revalidate();
		repaint();

	}

	public static void main(String[] args) {
		new RegisterPanel(new TemplateFrame());
	}

	public void initializeComponents() {

		setLayout(new GridBagLayout());
		setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		// reducing same code is necessary
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weighty = 0.3;

		// listen to me
		registerButton.addActionListener(actionListener);
		backButton.addActionListener(actionListener);
		checkUsernameAvailableButton.addActionListener(actionListener);
		checkScreenNameAvailableButton.addActionListener(actionListener);
		usernameField.addKeyListener(keyAdapter);
		screenNameField.addKeyListener(keyAdapter);

		// preparation
		displayProfileGroup.add(yesRadioButton);
		displayProfileGroup.add(noRadioButton);

		// putting it in the main panel

		// date of birth
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 10;
		add(dOBLabel, gbc);
		gbc.gridx = 1;
		add(dOBField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		add(backButton, gbc);
		gbc.gridx = 1;
		add(registerButton, gbc);

	}

	void checkAvailability(String type) {
		JButton button = null;
		JTextField textField = null;
		if (type.equals("screenname")) {
			button = checkScreenNameAvailableButton;
			textField = screenNameField;
		} else if (type.equals("username")) {
			button = checkUsernameAvailableButton;
			textField = usernameField;
		}

		if (textField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Fill screen name first!");
		} else {
			try {
				if (!Connector
						.getInstance()
						.executeQuery(
								"SELECT * FROM user WHERE " + type + " = '"
										+ textField.getText().trim() + "'")
						.next()) {
					button.setText("Available");
				} else {
					button.setText("Name is not available, change it");
					textField.requestFocus();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	boolean checkEmail() {
		return !emailField.getText().startsWith("@")
				&& emailField.getText().contains("@")
				&& emailField.getText().endsWith(".com")
				&& !emailField.getText().contains("@.com");

	}
}
