
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
	private static final String[] FIELDS = new String[]{"", "Cultural ID", "Gender", "Occupation"};

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
	private JPanel cultureFlowPanel;
	private JPanel newCultureFlowPanel;
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
	
	JButton submitButton;
	JButton deleteButton;
	private JButton cancel;
	HomeScreenGUI home;
	private JScrollPane scroll;
	
	DataStorage storage;
	private boolean editing;

	// class that relates to controlled vocabulary
	private ArrayList<String> cultureChoices;
	private Vector<String> genderChoices;
	private ArrayList<String> occupationChoices;

	private Person personEdited;
	private boolean isSearch;

	/**
	 * Creates the add/edit person GUI
	 * 
	 * @param home
	 *            - reference back to the home screen GUI
	 * @param person
	 *            - to be edited or null if we are adding a new person
	 */
	public AddEditPersonGUI(HomeScreenGUI home, Person person, boolean isSearch) {
		this.personEdited = person;
		this.home = home;
		this.isSearch = isSearch;
		
		try {
			storage = DataStorage.getMainDataStorage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editing = false;

		name = new JTextField(15);
		nodeName = new JTextField(15);
		cultureChoices = storage.getCultureChoices();
		culture = new JComboBox<>(cultureChoices.toArray());
		genderChoices = new Vector<String>(Arrays.asList("Unknown", "Male", "Female"));
		gender = new JComboBox<>(new String[] { "Unknown", "Male", "Female"});
		occupationChoices = storage.getOccupationChoices();
		occupation = new JComboBox<>(occupationChoices.toArray());
		notes = new JTextArea(2, 15);
		notes.setLineWrap(true);
		submitButton = new JButton("Submit");
		cancel = new JButton("Cancel");
		deleteButton = new JButton("Delete");
		
		nameLabel = new JLabel("Person Name:");
		nodeNameLabel = new JLabel("Node Name:");
		culturalLabel = new JLabel("Cultural ID:");
		genderLabel = new JLabel("Gender:");
		occupationLabel = new JLabel("Occupation:");
		biographyLabel = new JLabel("Biography:");

		namePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
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

		frame = new JFrame("Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		frame.add(createCenterPanel(), BorderLayout.CENTER);
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.add(createWestPanel(), BorderLayout.WEST);

		submitButton.addActionListener(this);
		cancel.addActionListener(this);
		deleteButton.addActionListener(this);

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
	 * This method creates the west panel. It adds the appropriate buttons to
	 * the panel.
	 * 
	 * @return JPanel This returns the completed west panel.
	 */
	private JPanel createWestPanel() {
		westPanel = new JPanel(new GridLayout(6, 1));
		westPanel.add(nameLabel);
		westPanel.add(nodeNameLabel);
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
		southPanel = new JPanel(new FlowLayout());
		southPanel.add(submitButton);
		southPanel.add(cancel);
		if(editing){
			southPanel.add(deleteButton);
		}
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
		nodeName.setText("");
		culture.setSelectedIndex(0);
		gender.setSelectedIndex(0);
		occupation.setSelectedIndex(0);
		notes.setText("");
		editing = false;
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
	 * This method will add a delete button to the frame when the edit button
	 * is clicked from within the home screen. It will only be displayed when
	 * that button is clicked.
	 */
	
	public void addDeleteButton(){
		editing = true;
		refreshPanel();
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
		if (personEdited != null && isSearch == false) {
			personEdited.setName(name.getText());
			personEdited.setNodeName(nodeName.getText());
			personEdited.setOccupation(occupation.getSelectedItem().toString());
			personEdited.setGender(gender.getSelectedItem().toString());
			personEdited.setCulturalId(culture.getSelectedItem().toString());
			personEdited.setBiographicalNotes(notes.getText());
			JOptionPane.showMessageDialog(frame, "Successfully Saved!");
			storage.savePeople();
			frame.dispose();
		} else if (personEdited == null && isSearch == false) {
			int nextID = storage.incrementAndGetNextPersonIdNum();
			if(name.getText().equals("") || nodeName.getText().equals("")){
				JOptionPane.showMessageDialog(frame, "Name and Node Name have to be filled in");
			} else {
			Person newPerson = new Person(nextID, name.getText(), nodeName.getText(), occupation.getSelectedItem().toString(),
					gender.getSelectedItem().toString(), culture.getSelectedItem().toString(), notes.getText());

			storage.addPerson(newPerson);
			JOptionPane.showMessageDialog(frame, "Successfully Saved!");
			storage.savePeople();
			frame.dispose();
			}
		} else{
			//search functionality. 
		}
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
		} else if (event.getSource() == deleteButton){
			//delete this person...why tho?
		}
	}
}
