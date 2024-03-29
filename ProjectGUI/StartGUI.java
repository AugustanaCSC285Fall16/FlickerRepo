import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class StartGUI implements ActionListener{
	private JFrame frame;
	private JTextField username;
	private JPasswordField password;
	private JButton add;
	private JButton submit;
	private JLabel label1;
	private JLabel label2;
	
	public StartGUI(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 125);
		frame.setTitle("Login");
		frame.setLayout(new BorderLayout());
		
		username = new JTextField(15);
		password = new JPasswordField(15);
		add = new JButton("Add User");
		submit = new JButton("Submit");
		label1 = new JLabel("Username:");
		label2 = new JLabel("Password:");
		
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(add);
		southPanel.add(submit);
		
		JPanel centerPanel = new JPanel(new FlowLayout());
		centerPanel.add(label1);
		centerPanel.add(username);
		centerPanel.add(label2);
		centerPanel.add(password);
		
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.add(centerPanel, BorderLayout.CENTER);
		
		add.addActionListener(this);
		submit.addActionListener(this);
		
		frame.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent event){
		if(event.getSource() == add){
			frame.dispose();
			NewUser newUser = new NewUser();
			
		}
	}
}
