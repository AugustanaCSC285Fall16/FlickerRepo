
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class AddArtistGUI implements ActionListener {

	private JFrame frame;
	private JComboBox<String> name;
	private JComboBox<String> culture;
	private JComboBox<String> gender;
	private JComboBox<String> occupation;
	private JTextField notes;
	private JPanel namePanel;
	private JPanel culturePanel;
	private JPanel genderPanel;
	private JPanel occupationPanel;
	private JPanel notesPanel;
	private JLabel nameLabel;
	private JLabel culturalLabel;
	private JLabel genderLabel;
	private JLabel occupationLabel;
	private JLabel biographyLabel;
	private JButton add;
	private JButton cancel;

	public AddArtistGUI() {

		name = new JComboBox<>(new String[] { "", "White", "Black" });
		culture = new JComboBox<>(new String[] { "", "White", "Black" });
		gender = new JComboBox<>(new String[] { "", "White", "Black" });
		occupation = new JComboBox<>(new String[] { "", "White", "Black" });
		notes = new JTextField(10);
		add = new JButton("Add");
		cancel = new JButton("Cancel");

		nameLabel = new JLabel("Artist Name:");
		culturalLabel = new JLabel("Cultural ID:");
		genderLabel = new JLabel("Gender:");
		occupationLabel = new JLabel("Occupation:");
		biographyLabel = new JLabel("Biography:");

		namePanel = new JPanel(new FlowLayout());
		culturePanel = new JPanel(new FlowLayout());
		genderPanel = new JPanel(new FlowLayout());
		occupationPanel = new JPanel(new FlowLayout());
		notesPanel = new JPanel(new FlowLayout());

		namePanel.add(name);
		culturePanel.add(culture);
		genderPanel.add(gender);
		occupationPanel.add(occupation);
		notesPanel.add(notes);

		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		frame.add(createCenterPanel(), BorderLayout.CENTER);
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.add(createWestPanel(), BorderLayout.WEST);

		add.addActionListener(this);
		cancel.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel(new GridLayout(5, 1));
		centerPanel.add(namePanel);
		centerPanel.add(culturePanel);
		centerPanel.add(genderPanel);
		centerPanel.add(occupationPanel);
		centerPanel.add(notesPanel);
		return centerPanel;
	}

	private JPanel createWestPanel() {
		JPanel westPanel = new JPanel(new GridLayout(5, 1));
		westPanel.add(nameLabel);
		westPanel.add(culturalLabel);
		westPanel.add(genderLabel);
		westPanel.add(occupationLabel);
		westPanel.add(biographyLabel);
		return westPanel;
	}

	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(add);
		southPanel.add(cancel);
		return southPanel;
	}

	private ArrayList<String> saveNewPersonData(){
		ArrayList<String> newPersonData = new ArrayList<String>();
		newPersonData.add((String)name.getSelectedItem().toString());
		newPersonData.add((String)culture.getSelectedItem().toString());
		newPersonData.add((String)gender.getSelectedItem().toString());
		newPersonData.add((String)occupation.getSelectedItem().toString());
		newPersonData.add(notes.getText());
		return newPersonData;
		
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == add) {
			// add things
			ArrayList<String> personData = saveNewPersonData();
			PersonList personList = new PersonList(personData);
			try {
				personList.writePersonData();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Send this arraylist to filewriter to append to end of csv
			frame.dispose();
		} else if (event.getSource() == cancel) {
			// reset fields
			frame.dispose();
		}
	}

}
