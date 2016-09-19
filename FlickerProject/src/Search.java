
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Search implements ActionListener {

	private JFrame frame;
	private JTextField name;
	private JTextField date;
	private JTextField type;
	private JTextField culture;
	private JTextField gender;
	private JTextField occupation;
	private JTextField location;
	private JButton reset;
	private JButton search;

	public Search() {
		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 300);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		
		name = new JTextField(15);
		date = new JTextField(15);
		type = new JTextField(15);
		culture = new JTextField(15);
		gender = new JTextField(15);
		occupation = new JTextField(15);
		location = new JTextField(15);
		reset = new JButton("Reset");
		search = new JButton("Search");
		
		JLabel label1 = new JLabel("Name:");
		JLabel label2 = new JLabel("Date:");
		JLabel label3 = new JLabel("Type:");
		JLabel label4 = new JLabel("Culture:");
		JLabel label5 = new JLabel("Gender:");
		JLabel label6 = new JLabel("Occupation:");
		JLabel label7 = new JLabel("Location:");
		
		JPanel centerPanel = new JPanel(new GridLayout(7,1));
		centerPanel.add(name);
		centerPanel.add(date);
		centerPanel.add(type);
		centerPanel.add(culture);
		centerPanel.add(gender);
		centerPanel.add(occupation);
		centerPanel.add(location);
		
		JPanel westPanel = new JPanel(new GridLayout(7,1));
		westPanel.add(label1);
		westPanel.add(label2);
		westPanel.add(label3);
		westPanel.add(label4);
		westPanel.add(label5);
		westPanel.add(label6);
		westPanel.add(label7);
		
		
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(reset);
		southPanel.add(search);
		
		frame.add(centerPanel,BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.add(westPanel, BorderLayout.WEST);
		
		
		reset.addActionListener(this);
		search.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == search){
			//search things
			frame.dispose();
			
			
		} else if (event.getSource() == reset){
			//reset fields
			name.setText("");
			date.setText("");
			type.setText("");
			culture.setText("");
			gender.setText("");
			occupation.setText("");
			location.setText("");
		}
	}

}

