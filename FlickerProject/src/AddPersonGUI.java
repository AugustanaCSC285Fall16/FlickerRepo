
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class AddPersonGUI implements ActionListener {

	private JFrame frame;
	private JTextField name;
	private JComboBox<String> culture;
	private JComboBox<String> gender;
	private JComboBox<String> occupation;
	private JTextArea notes;
	private JPanel namePanel;
	private JPanel culturePanel;
	private JPanel genderPanel;
	private JPanel occupationPanel;
	private JPanel notesPanel;
	private JPanel centerPanel;
	private JPanel westPanel;
	private JLabel nameLabel;
	private JLabel culturalLabel;
	private JLabel genderLabel;
	private JLabel occupationLabel;
	private JLabel biographyLabel;
	JButton add;
	private JButton cancel;
	HomeScreenGUI home;
	private JScrollPane scroll;

	public AddPersonGUI(HomeScreenGUI home) {

		this.home = home;

		name = new JTextField(10);
		culture = new JComboBox<>(new String[] { "", "White", "Black" });
		gender = new JComboBox<>(new String[] { "", "White", "Black" });
		occupation = new JComboBox<>(new String[] { "", "White", "Black" });
		notes = new JTextArea(2, 15);
		notes.setLineWrap(true);
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

		scroll = new JScrollPane(notes);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		namePanel.add(name);
		culturePanel.add(culture);
		genderPanel.add(gender);
		occupationPanel.add(occupation);
		notesPanel.add(scroll);

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

		frame.setVisible(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	
	/**
	 * This method creates the center panel.
	 * It adds the appropriate buttons to the panel.
	 * @return JPanel This returns the completed center panel.
	 */

	private JPanel createCenterPanel() {
		centerPanel = new JPanel(new GridLayout(5, 1));
		centerPanel.add(namePanel);
		centerPanel.add(culturePanel);
		centerPanel.add(genderPanel);
		centerPanel.add(occupationPanel);
		centerPanel.add(notesPanel);
		return centerPanel;
	}

	/**
	 * This method creates the west panel.
	 * It adds the appropriate buttons to the panel.
	 * @return JPanel This returns the completed west panel.
	 */
	
	private JPanel createWestPanel() {
		westPanel = new JPanel(new GridLayout(5, 1));
		westPanel.add(nameLabel);
		westPanel.add(culturalLabel);
		westPanel.add(genderLabel);
		westPanel.add(occupationLabel);
		westPanel.add(biographyLabel);
		return westPanel;
	}

	/**
	 * This method creates the south panel.
	 * It adds the appropriate buttons the panel.
	 * @return JPanel This returns the completed south panel.
	 */
	
	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(add);
		southPanel.add(cancel);
		return southPanel;
	}

	/**
	 * Makes the frame visible.
	 */
	
	void makeVisible() {
		frame.setVisible(true);
	}
	
	void setDefault() {
		name.setText("");
		culture.setSelectedIndex(0);
		gender.setSelectedIndex(0);
		notes.setText("");
		refreshPanel();
	}
	
	private void refreshPanel() {
		frame.remove(centerPanel);
		frame.remove(westPanel);

		frame.add(createCenterPanel(), BorderLayout.CENTER);
		frame.add(createWestPanel(), BorderLayout.WEST);
		
		frame.setSize(260, 300 + 40);

		makeVisible();
	}

	// public ArrayList<String> saveNewPersonData(){
	// ArrayList<String> newPersonData = new ArrayList<String>();
	// newPersonData.add((String)name.getSelectedItem().toString());
	// newPersonData.add((String)culture.getSelectedItem().toString());
	// newPersonData.add((String)gender.getSelectedItem().toString());
	// newPersonData.add((String)occupation.getSelectedItem().toString());
	// newPersonData.add(notes.getText());
	// return newPersonData;
	//
	// }

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == add) {
			// ArrayList<String> personData = saveNewPersonData();
			// PersonList personList = new PersonList(personData);
			try {
				DataStorage storage = DataStorage.getMainDataStorage();
				int nextID = storage.incrementAndGetNextPersonIdNum();
				
				Person newPerson = new Person(nextID, name.getText(), occupation.getSelectedItem().toString(),
						gender.getSelectedItem().toString(), culture.getSelectedItem().toString(), notes.getText());
				
				storage.addPerson(newPerson);
				storage.savePeople();
			} catch (IOException e) {
				e.printStackTrace();
			}
			frame.dispose();
			home.actionPerformed(event);
		} else if (event.getSource() == cancel) {
			// reset fields
			frame.dispose();
		}
	}		
}

