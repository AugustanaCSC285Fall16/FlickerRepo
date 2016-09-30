import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;


public class LoginGUI implements ActionListener{
	private JFrame frame;
	private JTextField username;
	private JPasswordField password;
	private JButton add;
	private JButton submit;
	private JLabel label1;
	private JLabel label2;
	
	public LoginGUI(){
		username = new JTextField(15);
		password = new JPasswordField(15);
		add = new JButton("Add User");
		submit = new JButton("Submit");
		label1 = new JLabel("Username:");
		label2 = new JLabel("Password:");
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 125);
		frame.setTitle("Login");
		frame.setLayout(new BorderLayout());
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.add(createCenterPanel(), BorderLayout.CENTER);
		
		add.addActionListener(this);
		submit.addActionListener(this);
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	private JPanel createCenterPanel(){
		JPanel centerPanel = new JPanel(new FlowLayout());
		centerPanel.add(label1);
		centerPanel.add(username);
		centerPanel.add(label2);
		centerPanel.add(password);
		return centerPanel;
	}
	
	private JPanel createSouthPanel(){
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(submit);
		southPanel.add(add);
		return southPanel;
	}
	
	
	
	
	public void actionPerformed(ActionEvent event){
		if(event.getSource() == add){
			frame.dispose();
			NewUser newUser = new NewUser();
		} else {
			try {
				HomeScreenGUI launchProgram = new HomeScreenGUI();
				frame.dispose();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
}
