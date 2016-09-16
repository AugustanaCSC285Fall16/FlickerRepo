

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AddConnection implements ActionListener {

	private JFrame frame;
	private JTextField baseName;
	private JTextField otherName;
	private JTextField date;
	private JTextField type;
	private JTextField location;
	private JTextField socialNotes;
	private JTextField bib;
	private JButton reset;
	private JButton search;

	public AddConnection() {
		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 400);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		
		baseName = new JTextField(15);
		otherName = new JTextField(15);
		date = new JTextField(15);
		type = new JTextField(15);
		location = new JTextField(15);
		socialNotes = new JTextField(15);
		bib = new JTextField(15);
		reset = new JButton("Reset");
		search = new JButton("Search");
		
		JLabel label1 = new JLabel("Base Name:");
		JLabel label2 = new JLabel("Other Name:");
		JLabel label3 = new JLabel("Date:");
		JLabel label4 = new JLabel("Type:");
		JLabel label5 = new JLabel("Location:");
		JLabel label6 = new JLabel("Social Notes:");
		JLabel label7 = new JLabel("Bibliography:");
		
		JPanel centerPanel = new JPanel(new FlowLayout());
		centerPanel.add(label1);
		centerPanel.add(baseName);
		centerPanel.add(label2);
		centerPanel.add(otherName);
		centerPanel.add(label3);
		centerPanel.add(date);
		centerPanel.add(label4);
		centerPanel.add(type);
		centerPanel.add(label5);
		centerPanel.add(location);
		centerPanel.add(label6);
		centerPanel.add(socialNotes);
		centerPanel.add(label7);
		centerPanel.add(bib);
		
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
		if(event.getSource() == reset){
			//search things
			frame.dispose();
		} else if (event.getSource() == search){
			//reset fields
			frame.dispose();
		}
	}

}


