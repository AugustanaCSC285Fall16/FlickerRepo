
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class AddConnectionGUI implements ActionListener {

	private JFrame frame;
	private JTextField baseName;
	private JTextField otherName;
	private JTextField date;
	private JTextField type;
	private JTextField location;
	private JTextField socialNotes;
	private JTextField bib;
	private JLabel baseNameLabel;
	private JLabel otherNameLabel;
	private JLabel dateLabel;
	private JLabel typeLabel;
	private JLabel locationLabel;
	private JLabel socialLabel;
	private JLabel bibLabel;
	JButton add;
	private JButton cancel;
	
	HomeScreenGUI home;

	public AddConnectionGUI(HomeScreenGUI home) {
		
		this.home = home;
		
		baseName = new JTextField(15);
		otherName = new JTextField(15);
		date = new JTextField(15);
		type = new JTextField(15);
		location = new JTextField(15);
		socialNotes = new JTextField(15);
		bib = new JTextField(15);
		add = new JButton("Add");
		cancel = new JButton("Cancel");

		baseNameLabel = new JLabel("Base Name:");
		otherNameLabel = new JLabel("Other Name:");
		dateLabel = new JLabel("Date:");
		typeLabel = new JLabel("Type:");
		locationLabel = new JLabel("Location:");
		socialLabel = new JLabel("Social Notes:");
		bibLabel = new JLabel("Bibliography:");

		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 400);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		frame.add(createCenterPanel(), BorderLayout.CENTER);
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.add(createWestPanel(), BorderLayout.WEST);

		add.addActionListener(this);
		cancel.addActionListener(this);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel(new GridLayout(7, 1));
		centerPanel.add(baseName);
		centerPanel.add(otherName);
		centerPanel.add(date);
		centerPanel.add(type);
		centerPanel.add(location);
		centerPanel.add(socialNotes);
		centerPanel.add(bib);
		return centerPanel;
	}

	private JPanel createWestPanel() {
		JPanel westPanel = new JPanel(new GridLayout(7, 1));
		westPanel.add(baseNameLabel);
		westPanel.add(otherNameLabel);
		westPanel.add(dateLabel);
		westPanel.add(typeLabel);
		westPanel.add(locationLabel);
		westPanel.add(socialLabel);
		westPanel.add(bibLabel);
		return westPanel;
	}

	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(add);
		southPanel.add(cancel);
		return southPanel;
	}
	void makeVisible(){
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == add) {
			// search things
			//ArrayList<String> ConnectionData = saveNewConnectionData();
			// send to filewriter to get appended to csv
			frame.dispose();
			home.actionPerformed(event);
		} else if (event.getSource() == cancel) {
			// reset fields
			frame.dispose();
		}
	}

}
