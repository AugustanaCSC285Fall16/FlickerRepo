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

	private static final int WIDTH = 350;
	private static final int HEIGHT = 300;

	private JFrame frame;
	private JTextField name;
	private JTextField nodeName;
	private JComboBox<Object> culture;
	private JComboBox<String> gender;
	private JComboBox<Object> occupation;
	private JTextArea notes;

	private JPanel namePanel;
	private JPanel nodeNamePanel;
	private JPanel culturePanel;
	private JPanel genderPanel;
	private JPanel occupationPanel;
	private JPanel notesPanel;
	private JPanel centerPanel;
	private JPanel westPanel;
	private JPanel southPanel;

	private JLabel nameLabel;
	private JLabel nodeNameLabel;
	private JLabel culturalLabel;
	private JLabel genderLabel;
	private JLabel occupationLabel;
	private JLabel biographyLabel;

	private JButton submitButton;
	private JButton resetButton;
	private JButton cancel;
	private HomeScreenGUI home;
	private JScrollPane scroll;

	private DataStorage dataStorage;
	private VocabStorage vocabStorage;
	private boolean editing;
	private Person personEdited;

	private ArrayList<String> cultureChoices;
	private Vector<String> genderChoices;
	private ArrayList<String> occupationChoices;

	/**
	 * Creates the add/edit person GUI
	 * 
	 * @param home
	 *            - reference back to the home screen GUI
	 * @param person
	 *            - to be edited or null if we are adding a new person
	 */
	public AddEditPersonGUI(HomeScreenGUI home, Person person) {
		try {
			dataStorage = DataStorage.getMainDataStorage();
			vocabStorage = VocabStorage.getMainVocabStorage();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not load Data!");
		}
		
		this.personEdited = person;
		this.home = home;
		
		if (person == null) {
			editing = false;
		} else {
			editing = true;
		}

		name = new JTextField(15);
		nodeName = new JTextField(15);
		cultureChoices = vocabStorage.getCultureChoices();
		culture = new JComboBox<>(cultureChoices.toArray());
		genderChoices = new Vector<String>(Arrays.asList("Unknown", "Male", "Female"));
		gender = new JComboBox<>(new String[] { "Unknown", "Male", "Female" });
		occupationChoices = vocabStorage.getOccupationChoices();
		occupation = new JComboBox<>(occupationChoices.toArray());
		notes = new JTextArea(2, 15);
		notes.setLineWrap(true);
		submitButton = new JButton("Submit");
		cancel = new JButton("Cancel");
		resetButton = new JButton("Reset");

		nameLabel = new JLabel("Person Name:");
		nodeNameLabel = new JLabel("Node Name:");
		culturalLabel = new JLabel("Cultural ID:");
		genderLabel = new JLabel("Gender:");
		occupationLabel = new JLabel("Occupation:");
		biographyLabel = new JLabel("Biography:");

		namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nodeNamePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		culturePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		genderPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		occupationPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		notesPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

		scroll = new JScrollPane(notes);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		namePanel.add(name);
		nodeNamePanel.add(nodeName);
		culturePanel.add(culture);
		genderPanel.add(gender);
		occupationPanel.add(occupation);
		notesPanel.add(scroll);

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle("Person");
		frame.setLayout(new BorderLayout());
		frame.add(createCenterPanel(), BorderLayout.CENTER);
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.add(createWestPanel(), BorderLayout.WEST);

		submitButton.addActionListener(this);
		cancel.addActionListener(this);
		resetButton.addActionListener(this);

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
		centerPanel = new JPanel(new GridLayout(6, 1));
		centerPanel.add(namePanel);
		centerPanel.add(nodeNamePanel);
		centerPanel.add(culturePanel);
		centerPanel.add(genderPanel);
		centerPanel.add(occupationPanel);
		centerPanel.add(notesPanel);
		return centerPanel;
	}

	/**
	 * creates a panel with the label and adds it to the passed in panel
	 * 
	 * @param panel
	 *            - panel to add the new panel with label to
	 * @param label
	 *            - new label to add to new panel.
	 */
	private void createPanelsInsideWest(JPanel panel, JLabel label) {
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		labelPanel.add(label);
		panel.add(labelPanel);
	}

	/**
	 * This method creates the west panel. It adds the appropriate buttons to
	 * the panel.
	 * 
	 * @return JPanel This returns the completed west panel.
	 */
	private JPanel createWestPanel() {
		westPanel = new JPanel(new GridLayout(6, 1));
		createPanelsInsideWest(westPanel, nameLabel);
		createPanelsInsideWest(westPanel, nodeNameLabel);
		createPanelsInsideWest(westPanel, culturalLabel);
		createPanelsInsideWest(westPanel, genderLabel);
		createPanelsInsideWest(westPanel, occupationLabel);
		createPanelsInsideWest(westPanel, biographyLabel);
		return westPanel;
	}

	/**
	 * This method creates the south panel. It adds the appropriate buttons the
	 * panel.
	 * 
	 * @return JPanel This returns the completed south panel.
	 */
	private JPanel createSouthPanel() {
		southPanel = new JPanel(new FlowLayout());
		southPanel.add(submitButton);
		southPanel.add(cancel);
		southPanel.add(resetButton);
		return southPanel;
	}

	/**
	 * Makes the frame visible This is used in our home screen GUI
	 */
	public void makeVisible() {
		frame.setVisible(true);
	}

	/**
	 * Sets the edit GUI all back to blanks to be filled in
	 *
	 */
	private void setDefault() {
		if (editing) {
			setPersonData(personEdited);
		} else {
			name.setText("");
			nodeName.setText("");
			culture.setSelectedIndex(0);
			gender.setSelectedIndex(0);
			occupation.setSelectedIndex(0);
			notes.setText("");
		}
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
		nodeName.setText(personToEdit.getNodeName());
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
		frame.remove(southPanel);
		frame.add(createCenterPanel(), BorderLayout.CENTER);
		frame.add(createWestPanel(), BorderLayout.WEST);
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.setSize(WIDTH, HEIGHT + 40);
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
		if (personEdited != null) {
			personEdited.setName(name.getText());
			personEdited.setNodeName(nodeName.getText());
			personEdited.setOccupation(occupation.getSelectedItem().toString());
			personEdited.setGender(gender.getSelectedItem().toString());
			personEdited.setCulturalId(culture.getSelectedItem().toString());
			personEdited.setBiographicalNotes(notes.getText());
			JOptionPane.showMessageDialog(frame, "Successfully Saved!");
			dataStorage.savePeople();
			frame.dispose();
		} else {
			int nextID = dataStorage.incrementAndGetNextPersonIdNum();
			if (name.getText().equals("") || nodeName.getText().equals("")) {
				JOptionPane.showMessageDialog(frame, "Name and Node Name have to be filled in");
			} else {
				Person newPerson = new Person(nextID, name.getText(), nodeName.getText(),
						occupation.getSelectedItem().toString(), gender.getSelectedItem().toString(),
						culture.getSelectedItem().toString(), notes.getText());
				if (personExists(newPerson)) {
					JOptionPane.showMessageDialog(frame, "Person already exists in database!");
				} else {
					dataStorage.addPerson(newPerson);
					JOptionPane.showMessageDialog(frame, "Successfully Saved!");
					dataStorage.savePeople();
					frame.dispose();
				}
			}
		}
	}

	/**
	 * Checks to see if a person exists in our data. Either Name or node name.
	 * 
	 * @param newPerson
	 * @return true if the person already exists in our data
	 */
	public boolean personExists(Person newPerson) {
		for (Person person : dataStorage.getPeopleArrayList()) {
			if (person.getName().equals(newPerson.getName()) || person.getNodeName().equals(newPerson.getNodeName())) {
				return true;
			}
		}
		return false;
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
				if (dataStorage.isFiltered()) {
					home.updateTable(home.getFilteredStorage());
				} else {
					home.updateTable(dataStorage);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "There was an Error Saving your Person! Please try again.");
			}
			home.actionPerformed(event);
		} else if (event.getSource() == resetButton) {
			setDefault();
		} else { // cancel
			frame.dispose();
		}
	}
}
