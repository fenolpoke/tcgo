package panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.Connector;

public class ProfilePanel extends JPanel {

	JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10));

	JLabel dOBLabel = new JLabel("Date of Birth");
	JTextField dOBField = new JTextField(20);

	JLabel usernameLabel = new JLabel("Username");
	JTextField usernameField = new JTextField(20);
	JButton checkUsernameAvailableButton = new JButton("Check Availability");

	JLabel oldPasswordLabel = new JLabel("Old Password");
	JPasswordField oldPasswordField = new JPasswordField(20);

	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordField = new JPasswordField(20);

	JLabel confirmPasswordLabel = new JLabel("Confirm Password");
	JPasswordField confirmPasswordField = new JPasswordField(20);

	JLabel emailLabel = new JLabel("Email Address");
	JTextField emailField = new JTextField(20);

	JLabel confirmEmailLabel = new JLabel("Confirm Email");
	JTextField confirmEmailField = new JTextField(20);

	JLabel screenNameLabel = new JLabel("Screen Name");
	JTextField screenNameField = new JTextField(20);
	JButton checkScreenNameAvailableButton = new JButton("Check Availability");

	JButton updateButton = new JButton("Update Profile");

	ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(checkScreenNameAvailableButton)) {
				checkAvailability("screenname");
			} else if (e.getSource().equals(checkUsernameAvailableButton)) {
				checkAvailability("username");
			} else if (e.getSource().equals(updateButton)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
				Calendar now = Calendar.getInstance(), dob = Calendar
						.getInstance();

				try {
					dob.setTime(sdf.parse(dOBField.getText().trim()));
					if (Integer.parseInt(dOBField.getText().substring(5, 7)) > 12)
						throw new ParseException("", 0);
					if (Integer.parseInt(dOBField.getText().substring(8, 10)) > 31)
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

				if (now.get(Calendar.YEAR) - dob.get(Calendar.YEAR) < 11) {

					JOptionPane.showMessageDialog(null,
							"You must be at least 11 years old!");

				} else if (usernameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Fill username!");
				} else if (!checkUsernameAvailableButton.getText().equals(
						"Available")) {
					JOptionPane.showMessageDialog(null,
							"Check username availability!");
				} else if (!checkPassword()) {
					JOptionPane.showMessageDialog(null,
							"Doesn't match old password!");
				} else if (new String(passwordField.getPassword()).isEmpty()) {
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
				} else if (screenNameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Fill screen name!");
				} else if (!checkScreenNameAvailableButton.getText().equals(
						"Available")) {
					JOptionPane.showMessageDialog(null,
							"Check screen name availability!");
				} else {
					try {
						System.out.println("UPDATE `user` SET "
										+ "`screenname`='"
										+ screenNameField.getText().trim()
										+ "',`username`='"
										+ usernameField.getText().trim()
										+ "',`password`='"
										+ new String(passwordField
												.getPassword()) + "',`dob`='"
										+ dOBField.getText() + "',`email`='"
										+ emailField.getText().trim()
										+ "' WHERE `id`="
										+ Connector.getInstance().getUserId());
						Connector.getInstance().executeUpdate(
								"UPDATE `user` SET "
										+ "`screenname`='"
										+ screenNameField.getText().trim()
										+ "',`username`='"
										+ usernameField.getText().trim()
										+ "',`password`='"
										+ new String(passwordField
												.getPassword()) + "',`dob`='"
										+ dOBField.getText() + "',`email`='"
										+ emailField.getText().trim()
										+ "' WHERE `id`="
										+ Connector.getInstance().getUserId());
						JOptionPane.showMessageDialog(null, "Update success!");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

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

	public ProfilePanel() {

		setBackground(Color.decode("#eef6ff"));//new Color(255, 222, 0));
		
		// you got to be reminded of your self
		ResultSet rs;
		try {
			rs = Connector.getInstance().executeQuery(
					"SELECT * FROM user WHERE id = "
							+ Connector.getInstance().getUserId());
			if (rs.next()) {
				usernameField.setText(rs.getString("username"));
				screenNameField.setText(rs.getString("screenname"));
				dOBField.setText(rs.getString("dob"));
				emailField.setText(rs.getString("email"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// listen to me
		updateButton.addActionListener(actionListener);
		checkUsernameAvailableButton.addActionListener(actionListener);
		checkScreenNameAvailableButton.addActionListener(actionListener);
		usernameField.addKeyListener(keyAdapter);
		screenNameField.addKeyListener(keyAdapter);

		mainPanel.setBackground(Color.decode("#eef6ff"));
		mainPanel.add(dOBLabel);
		mainPanel.add(dOBField);

		mainPanel.add(usernameLabel);
		mainPanel.add(usernameField);

		mainPanel.add(new JLabel());
		mainPanel.add(checkUsernameAvailableButton);

		mainPanel.add(oldPasswordLabel);
		mainPanel.add(oldPasswordField);

		mainPanel.add(passwordLabel);
		mainPanel.add(passwordField);

		mainPanel.add(confirmPasswordLabel);
		mainPanel.add(confirmPasswordField);

		mainPanel.add(emailLabel);
		mainPanel.add(emailField);

		mainPanel.add(confirmEmailLabel);
		mainPanel.add(confirmEmailField);

		mainPanel.add(screenNameLabel);
		mainPanel.add(screenNameField);

		mainPanel.add(new JLabel());
		mainPanel.add(checkScreenNameAvailableButton);

		mainPanel.add(new JLabel());
		mainPanel.add(updateButton);

		add(mainPanel);
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
										+ textField.getText().trim()
										+ "' and id <> "
										+ Connector.getInstance().getUserId())
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

	boolean checkPassword() {
		try {
			ResultSet rs = Connector.getInstance().executeQuery(
					"SELECT password FROM user WHERE id = "
							+ Connector.getInstance().getUserId());
			if (rs.next()) {
				return rs.getString("password").equals(
						new String(oldPasswordField.getPassword()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	boolean checkEmail() {
		return !emailField.getText().startsWith("@")
				&& emailField.getText().contains("@")
				&& emailField.getText().endsWith(".com")
				&& !emailField.getText().contains("@.com");

	}
}
