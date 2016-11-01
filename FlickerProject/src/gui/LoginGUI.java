package gui;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import dataModel.ContainsQuery;
import dataModel.User;
import dataModel.UserQuery;
import dataModel.UserStorage;

public class LoginGUI implements ActionListener {
	private JFrame frame;
	private JTextField username;
	private JPasswordField password;

	private JButton add;
	private JButton submit;
	private JButton about;

	private JLabel usernameLabel;
	private JLabel passwordLabel;

	private boolean isAdmin;
	private static boolean adminApproved;

	/**
	 * Makes a Login GUI. Will disable buttons based on parameter.
	 * 
	 * @param isAdmin
	 *            true - will disable the add and about buttons.
	 */
	public LoginGUI(boolean isAdmin) {
		this.isAdmin = isAdmin;
		this.adminApproved = false;
		username = new JTextField(15);
		password = new JPasswordField(15);
		add = new JButton("Add User");
		submit = new JButton("Submit");
		about = new JButton("About");
		usernameLabel = new JLabel("Username:");
		passwordLabel = new JLabel("Password:");

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 125);
		frame.setTitle("Login");
		frame.setLayout(new BorderLayout());
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.add(createCenterPanel(), BorderLayout.CENTER);

		add.addActionListener(this);
		submit.addActionListener(this);
		about.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		if (isAdmin) {
			add.setEnabled(false);
			about.setEnabled(false);
		}
	}

	/**
	 * This method is used to create the center panel. It adds the appropriate
	 * labels to the panel.
	 * 
	 * @return JPanel This returns the completed center panel.
	 */
	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel(new FlowLayout());
		centerPanel.add(usernameLabel);
		centerPanel.add(username);
		centerPanel.add(passwordLabel);
		centerPanel.add(password);
		return centerPanel;
	}

	/**
	 * This method is used to create the south panel. It adds the appropriate
	 * labels to the panel.
	 * 
	 * @return JPanel This returns the completed south panel.
	 */
	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(submit);
		southPanel.add(add);
		southPanel.add(about);
		return southPanel;
	}

	/**
	 * Checks to see if the User name and password are in the data and and
	 * starts the program. If isAdmin is true, and the username and password are
	 * correct, will approve the making of an admin.
	 * 
	 * @throws IOException
	 */
	private void isCorrectUsernamePassword() throws IOException {
		UserStorage storage = UserStorage.getMainUserStorage();
		UserQuery usernameQuery = new ContainsQuery(username.getText(), "Username");
		UserQuery passwordQuery = new ContainsQuery(password.getText(), "Password");
		if (storage.userFilter(usernameQuery) && storage.userFilter(passwordQuery)) { 
			User loginUser = storage.getUserFromFiltered(usernameQuery);
			if (loginUser.getPassword().equals(password.getText())){
				if (isAdmin) {
					setAdminApproved(true);
					frame.dispose();
				} else {
					HomeScreenGUI launchProgram = new HomeScreenGUI(loginUser.getPermissions());
					frame.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Username and Password do not match up!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Not a valid username or password!");
		}
	}

	/**
	 * Retrieves and returns the adminApproved boolean
	 */
	public static boolean getAdminApproved() {
		return adminApproved;
	}

	/**
	 * Sets the adminApproved boolean to the parameter
	 * 
	 * @param adminApproved
	 */
	public static void setAdminApproved(boolean adminApproved) {
		LoginGUI.adminApproved = adminApproved;
	}

	@Override
	/**
	 * overides the action performed method from ActionListener
	 */
	public void actionPerformed(ActionEvent event) {
		try {
			if (event.getSource() == add) {
				NewUserGUI newUserGUI = new NewUserGUI();
			} else if (event.getSource() == about) {
				AboutScreenGUI aboutScreenGUI = new AboutScreenGUI();
			} else {
				isCorrectUsernamePassword();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An Error Occured!");
		}
	}
}
