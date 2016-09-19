

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
	private JButton add;
	private JButton cancel;

	public AddConnection() {
		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 400);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		
		baseName = new JTextField(15);
		otherName = new JTextField(15);
		date = new JTextField(15);
		type = new JTextField(15);
		location = new JTextField(15);
		socialNotes = new JTextField(15);
		bib = new JTextField(15);
		add = new JButton("Add");
		cancel = new JButton("Cancel");
		
		JLabel label1 = new JLabel("Base Name:");
		JLabel label2 = new JLabel("Other Name:");
		JLabel label3 = new JLabel("Date:");
		JLabel label4 = new JLabel("Type:");
		JLabel label5 = new JLabel("Location:");
		JLabel label6 = new JLabel("Social Notes:");
		JLabel label7 = new JLabel("Bibliography:");
		
		JPanel centerPanel = new JPanel(new GridLayout(7,1));
		centerPanel.add(baseName);
		centerPanel.add(otherName);
		centerPanel.add(date);
		centerPanel.add(type);
		centerPanel.add(location);
		centerPanel.add(socialNotes);
		centerPanel.add(bib);
		
		JPanel westPanel = new JPanel(new GridLayout(7,1));
		westPanel.add(label1);
		westPanel.add(label2);
		westPanel.add(label3);
		westPanel.add(label4);
		westPanel.add(label5);
		westPanel.add(label6);
		westPanel.add(label7);
		
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(add);
		southPanel.add(cancel);
		
		frame.add(centerPanel,BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.add(westPanel, BorderLayout.WEST);
		
		add.addActionListener(this);
		cancel.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == add){
			//search things
			frame.dispose();
		} else if (event.getSource() == cancel){
			//reset fields
			frame.dispose();
		}
	}

}


