package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import dataModel.DataStorage;
import dataModel.User;
import dataModel.UserStorage;

public class NewUserGUI implements ActionListener {
	private UserStorage userStorage;
	private DataStorage dataStorage;
	private JFrame frame;
	private JTextField fullName;
	private JTextField userName;
	private JComboBox<String> permissions;
	private JPasswordField password;
	private JPasswordField confirm;
	private JButton create;
	private JButton cancel;
	private boolean adminApproved;

	/**
	 * Creates a new user GUI
	 */
	public NewUserGUI() {
		adminApproved = LoginGUI.getAdminApproved();
		try {
			userStorage = UserStorage.getMainUserStorage();
			dataStorage = DataStorage.getMainDataStorage();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not load data!");
		}

		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setTitle("New User");
		frame.setLayout(new BorderLayout());

		fullName = new JTextField(15);
		userName = new JTextField(15);
		password = new JPasswordField(15);
		confirm = new JPasswordField(15);
		permissions = new JComboBox<>(new String[] { "Admin", "Student" });
		create = new JButton("Create");
		cancel = new JButton("Cancel");

		JLabel fullNameLabel = new JLabel("Full Name:");
		JLabel userNameLabel = new JLabel("Username:");
		JLabel passwordLabel = new JLabel("Password:");
		JLabel confirmLabel = new JLabel("Confirm Password:");
		JLabel permissionLabel = new JLabel("Permission:");

		JPanel centerPanel = new JPanel(new GridLayout(5, 1));
		centerPanel.add(fullNameLabel);
		centerPanel.add(fullName);
		centerPanel.add(userNameLabel);
		centerPanel.add(userName);
		centerPanel.add(passwordLabel);
		centerPanel.add(password);
		centerPanel.add(confirmLabel);
		centerPanel.add(confirm);
		centerPanel.add(permissionLabel);
		centerPanel.add(permissions);

		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(create);
		southPanel.add(cancel);

		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);

		create.addActionListener(this);
		cancel.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}

	/**
	 * Checks to see if the user exists in the data already.
	 * 
	 * @param newUser
	 * @return boolean true if the user already exists.
	 */
	public boolean userExists(User newUser) {
		for (User user : userStorage.getUserArrayList()) {
			if (user.getUsername().equals(newUser.getUsername()) || user.getFullName().equals(newUser.getFullName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Makes, adds, and saves the new user.
	 * 
	 * @throws IOException
	 */
	public void addAndSaveUser() {
		try {
			int nextID = dataStorage.incrementAndGetNextUserIdNum();
			User newUser = new User(nextID, fullName.getText(), userName.getText(), password.getText(),
					permissions.getSelectedItem().toString());
			if (userExists(newUser)) {
				JOptionPane.showMessageDialog(frame, "User already exists in database!");
			} else {
				userStorage.addUser(newUser);
				JOptionPane.showMessageDialog(frame, "Successfully Saved!");
				userStorage.saveUsers();
				frame.dispose();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not add user");
		}
	}

	@Override
	/**
	 * Overrides the actionPerformed method from the ActionListener class.
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == create) {
			if (fullName.getText().equals("") || userName.getText().equals("") || password.getPassword().equals(null)
					|| confirm.getPassword().equals(null)) {
				JOptionPane.showMessageDialog(null, "Please fill out all of the fields!");
			} else {
				if (!Arrays.equals(password.getPassword(), confirm.getPassword())) {
					JOptionPane.showMessageDialog(null, "Passwords do not match");
				} else {
					adminApproved = LoginGUI.getAdminApproved();
					if (permissions.getSelectedIndex() == 0 && adminApproved == false) {
						LoginGUI adminLogin = new LoginGUI(true);
					} else {
						frame.dispose();
						addAndSaveUser();
						LoginGUI login = new LoginGUI(false);
					}
				}
			}
		} else if (event.getSource() == cancel) {
			frame.dispose();
			LoginGUI login = new LoginGUI(false);
		}
	}
}
