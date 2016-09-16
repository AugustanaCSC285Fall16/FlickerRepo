
import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
		frame.setSize(200, 400);
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
		
		JPanel centerPanel = new JPanel(new FlowLayout());
		centerPanel.add(label1);
		centerPanel.add(name);
		centerPanel.add(label2);
		centerPanel.add(date);
		centerPanel.add(label3);
		centerPanel.add(type);
		centerPanel.add(label4);
		centerPanel.add(culture);
		centerPanel.add(label5);
		centerPanel.add(gender);
		centerPanel.add(label6);
		centerPanel.add(occupation);
		centerPanel.add(label7);
		centerPanel.add(location);
		
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(reset);
		southPanel.add(search);
		
		frame.add(centerPanel,BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		
		
		reset.addActionListener(this);
		search.addActionListener(this);

		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == search){
			//search things
			frame.dispose();
			
			
		} else if (event.getSource() == reset){
			//reset fields
		}
	}

}

