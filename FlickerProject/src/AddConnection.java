

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
		
		JLabel baseNameLabel = new JLabel("Base Name:");
		JLabel otherNameLabel = new JLabel("Other Name:");
		JLabel dateLabel = new JLabel("Date:");
		JLabel typeLabel = new JLabel("Type:");
		JLabel locationLabel = new JLabel("Location:");
		JLabel noteLabel = new JLabel("Social Notes:");
		JLabel biblioLabel = new JLabel("Bibliography:");
		
		JPanel centerPanel = new JPanel(new GridLayout(7,1));
		centerPanel.add(baseName);
		centerPanel.add(otherName);
		centerPanel.add(date);
		centerPanel.add(type);
		centerPanel.add(location);
		centerPanel.add(socialNotes);
		centerPanel.add(bib);
		
		JPanel westPanel = new JPanel(new GridLayout(7,1));
		westPanel.add(baseNameLabel);
		westPanel.add(otherNameLabel);
		westPanel.add(dateLabel);
		westPanel.add(typeLabel);
		westPanel.add(locationLabel);
		westPanel.add(noteLabel);
		westPanel.add(biblioLabel);
		
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


