
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.*;

public class AddEditPersonGUI implements ActionListener {

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
	JButton submitButton;
	private JButton cancel;
	HomeScreenGUI home;
	private JScrollPane scroll;

	// class that relates to controlled vocabulary
	private Vector<String> cultureChoices;
	private Vector<String> genderChoices;
	private Vector<String> occupationChoices;

	private Person personEdited;

	/**
	 * Creates the add/edit person GUI
	 * 
	 * @param home
	 *            - reference back to the home screen GUI
	 * @param person
	 *            - to be edited or null if we are adding a new person
	 */
	public AddEditPersonGUI(HomeScreenGUI home, Person person) {
		this.personEdited = person;
		this.home = home;

		name = new JTextField(10);
		cultureChoices = new Vector<String>(Arrays.asList("None", "French", "American", "Italian", "German", "Other"));
		culture = new JComboBox<>(new String[] { "None", "French", "American", "Italian", "German", "Other" });
		genderChoices = new Vector<String>(Arrays.asList("", "Male", "Female", "Other"));
		gender = new JComboBox<>(new String[] { "None", "Male", "Female", "Other" });
		occupationChoices = new Vector<String>(Arrays.asList("", "Artist", "Bartender", "Other"));
		occupation = new JComboBox<>(new String[] { "None", "Artist", "Bartender", "Other" });
		notes = new JTextArea(2, 15);
		notes.setLineWrap(true);
		submitButton = new JButton("Submit");
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

		frame = new JFrame("Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		frame.add(createCenterPanel(), BorderLayout.CENTER);
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.add(createWestPanel(), BorderLayout.WEST);

		submitButton.addActionListener(this);
		cancel.addActionListener(this);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		if (personEdited == null) {
			setDefault();
		} else {
			setPersonData(personEdited);
		}

	}

	/**
	 * This method creates the center panel. It adds the appropriate buttons to
	 * the panel.
	 * 
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
	 * This method creates the west panel. It adds the appropriate buttons to
	 * the panel.
	 * 
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
	 * This method creates the south panel. It adds the appropriate buttons the
	 * panel.
	 * 
	 * @return JPanel This returns the completed south panel.
	 */
	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(submitButton);
		southPanel.add(cancel);
		return southPanel;
	}

	/**
	 * Makes the frame visible
	 */
	void makeVisible() {
		frame.setVisible(true);
	}

	/**
	 * Sets the edit GUI all back to blanks to fill in
	 */
	void setDefault() {
		name.setText("");
		culture.setSelectedIndex(0);
		gender.setSelectedIndex(0);
		occupation.setSelectedIndex(0);
		notes.setText("");
		refreshPanel();
	}

	/**
	 * Sets the Options in the Edit person GUI to the selected person's data
	 * 
	 * @param person
	 *            - the person who's data will fill in the GUI
	 */
	void setPersonData(Person person) {
		Person personToEdit = person;
		name.setText(personToEdit.getName());
		culture.setSelectedIndex(cultureChoices.indexOf(personToEdit.getCulturalId()));
		gender.setSelectedIndex(genderChoices.indexOf(personToEdit.getGender()));
		occupation.setSelectedIndex(occupationChoices.indexOf(personToEdit.getOccupation()));
		notes.setText(personToEdit.getBiographicalNotes());
		refreshPanel();
	}

	/**
	 * Refreshes the panel to show new data or a blank GUI to fill out
	 */
	private void refreshPanel() {
		frame.remove(centerPanel);
		frame.remove(westPanel);
		frame.add(createCenterPanel(), BorderLayout.CENTER);
		frame.add(createWestPanel(), BorderLayout.WEST);
		frame.setSize(260, 300 + 40);
		makeVisible();
	}

	/**
	 * Will set the edited person's data to whatever was put into the GUI if it
	 * is already a person filled in. Will Add a new person with all of the data
	 * filled in the GUI if there was not a person already selected. Saves the
	 * People data
	 * 
	 * @throws IOException
	 */
	private void submitClicked() throws IOException {
		DataStorage storage = DataStorage.getMainDataStorage();
		if (personEdited != null) {
			personEdited.setName(name.getText());
			personEdited.setOccupation(occupation.getSelectedItem().toString());
			personEdited.setGender(gender.getSelectedItem().toString());
			personEdited.setCulturalId(culture.getSelectedItem().toString());
			personEdited.setBiographicalNotes(notes.getText());
		} else {
			int nextID = storage.incrementAndGetNextPersonIdNum();

			Person newPerson = new Person(nextID, name.getText(), occupation.getSelectedItem().toString(),
					gender.getSelectedItem().toString(), culture.getSelectedItem().toString(), notes.getText());

			storage.addPerson(newPerson);
		}
		storage.savePeople();
		frame.dispose();
	}

	/**
	 * Based on the source of the event, the method will choose what the
	 * PersonGUI will do next.
	 * 
	 * @param ActionEvent
	 *            - event from the Add/Edit PersonGUI
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == submitButton) {
			try {
				submitClicked();
				home.updateTable();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "There was an Error Saving your Person! Please try again.");
			}
			home.actionPerformed(event);
		} else if (event.getSource() == cancel) {
			frame.dispose();
		}
	}
}
