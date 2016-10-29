import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class LoginGUI implements ActionListener {
	private JFrame frame;
	private JTextField username;
	private JPasswordField password;
	private JButton add;
	private JButton submit;
	private JButton about;
	private JLabel usernameLabel;
	private JLabel passwordLabel;

	public LoginGUI() {
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

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == add) {
			NewUser newUser = new NewUser();
		} else if (event.getSource() == about){
			try {
				AboutScreen aboutScreen = new AboutScreen();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error with About Screen!");
			}
		} else {
			try {
				//Need to check the UserData to see if the user is in the data. Might need to make a User Map like we did for persons. 
				HomeScreenGUI launchProgram = new HomeScreenGUI();
				frame.dispose();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Not a valid username or password!");
			}
		}
	}
}
