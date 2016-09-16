import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class NewUser implements ActionListener {

	private JFrame frame;
	private JTextField fullName;
	private JTextField userName;
	private JPasswordField password;
	private JPasswordField confirm;
	private JButton create;
	private JButton cancel;

	public NewUser() {
		frame = new JFrame("New User");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		
		fullName = new JTextField(15);
		userName = new JTextField(15);
		password = new JPasswordField(15);
		confirm = new JPasswordField(15);
		create = new JButton("Create");
		cancel = new JButton("Cancel");
		
		JLabel label1 = new JLabel("Full Name:");
		JLabel label2 = new JLabel("Username:");
		JLabel label3 = new JLabel("Password:");
		JLabel label4 = new JLabel("Confirm Password:");
		
		JPanel centerPanel = new JPanel(new FlowLayout());
		centerPanel.add(label1);
		centerPanel.add(fullName);
		centerPanel.add(label2);
		centerPanel.add(userName);
		centerPanel.add(label3);
		centerPanel.add(password);
		centerPanel.add(label4);
		centerPanel.add(confirm);
		
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(create);
		southPanel.add(cancel);
		
		frame.add(centerPanel,BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		
		
		create.addActionListener(this);
		cancel.addActionListener(this);

		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == create){
			int option=JOptionPane.showConfirmDialog(frame,"Is this correct?");
			if(option==0){
				frame.dispose();
				try {
					HomeScreen homeScreen = new HomeScreen();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (event.getSource() == cancel){
			frame.dispose();
		}
	}

}
