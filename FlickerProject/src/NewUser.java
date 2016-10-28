import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.*;

import com.opencsv.CSVWriter;

public class NewUser implements ActionListener {

	private JFrame frame;
	private JTextField fullName;
	private JTextField userName;
	private JComboBox<String> permissions;
	private JPasswordField password;
	private JPasswordField confirm;
	private JButton create;
	private JButton cancel;

	public NewUser() {
		frame = new JFrame("New User");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setTitle("Frame");
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

	// move to datastorage
	public void addUser() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter("DataFiles/UserData.csv"));
		DataStorage storage = DataStorage.getMainDataStorage();
		writer.writeNext(new String[] {"Id","Fullname", "Username", "Password", "Permissions" });
		User newUser = new User(storage.incrementAndGetNextUserIdNum(), fullName.getText(), userName.getText(), password.getText(),
				permissions.getSelectedItem().toString());
		writer.writeNext(newUser.toCSVRowArray());
		writer.close();
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == create) {
			if (Arrays.equals(password.getPassword(), confirm.getPassword())) {
				if (permissions.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Cannot Create a new Admin");
					// make it so if making a new admin, will have
				} else {
					frame.dispose();
					try {
						addUser();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Could not add user");
					}
					LoginGUI login = new LoginGUI();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Passwords do not match");
			}
		} else if (event.getSource() == cancel) {
			frame.dispose();
			LoginGUI login = new LoginGUI();

		}
	}

}
