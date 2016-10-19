
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class AddEditConnectionGUI implements ActionListener {

//	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.mm.dd");
	private static final String[] FIELDS = new String[]{"", "Type of Interaction","Location"};
	private static final int WIDTH = 350;
	private static final int HEIGHT = 300;

	// data fields
	private JFrame frame;
	private JComboBox<Object> baseName;
//	private JFormattedTextField date;
	private JTextField day;
	private JTextField month;
	private JTextField year;
	private JComboBox<Object> type;
	private JComboBox<Object> location;
	private JTextArea socialNotes;
	private JTextArea citation;
	private JComboBox<String> direction;
	private JComboBox<String> targetName;

	private JLabel baseNameLabel;
	private JLabel dateLabel;
	private JLabel typeLabel;
	private JLabel locationLabel;
	private JLabel socialLabel;
	private JLabel bibLabel;

	private JPanel centerPanel;
	private JPanel westPanel;
	private JPanel southPanel;

	private JPanel moreNamesPanel;
	private JPanel baseNamePanel;
	private JPanel namePanel;
	private JPanel datePanel;
	private JPanel typePanel;
	private JPanel locationPanel;
	private JPanel socialPanel;
	private JPanel bibPanel;

	private JScrollPane socialScroll;
	private JScrollPane bibScroll;

	JButton submitButton;
	private JButton cancel;
	private JButton moreNames;
	private JButton deleteButton;
	private int additionalNames;
	private ArrayList<JComboBox> targetNames;
	HomeScreenGUI home;

	private boolean editing;
	private boolean isSearch;
	DataStorage storage;


	// class that relates to controlled vocabulary
	ArrayList<Person> baseNameChoices;
	ArrayList<String> typeChoices;
	ArrayList<String> locationChoices;
	Vector<String> directionChoices;

	private Connection connectionEdited;

	/**
	 * Creates the add/edit connection GUI
	 * 
	 * @param home
	 *            - reference back to the home screen GUI
	 * @param person
	 *            - to be edited or null if we are adding a new connection
	 */
	public AddEditConnectionGUI(HomeScreenGUI home, Connection connection, boolean isSearch) {
		try {
			storage = DataStorage.getMainDataStorage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.connectionEdited = connection;
		this.home = home;
		this.isSearch = isSearch;

		additionalNames = 1;
		targetNames = new ArrayList<>();
		editing = false;

		baseNameChoices = storage.getPeopleArrayList();
		baseName = new JComboBox<>(baseNameChoices.toArray());
//		date = new JFormattedTextField(DATE_FORMAT);
//		date.setColumns(7);
//		date.setFocusLostBehavior(JFormattedTextField.PERSIST);
		day = new JTextField(2);
		month = new JTextField(2);
		year = new JTextField(4);
		typeChoices = storage.getInteractionTypes();
		type = new JComboBox<>(typeChoices.toArray());
		locationChoices = storage.getLocationTypes();
		location = new JComboBox<>(locationChoices.toArray());
		directionChoices = new Vector<String>(
				Arrays.asList("One-to-One", "One-to-Many", "Many-to-Many"));
		direction = new JComboBox<>(new String[] {"One-to-One", "One-to-Many", "Many-to-Many" });
		socialNotes = new JTextArea(2, 10);
		socialNotes.setLineWrap(true);
		citation = new JTextArea(2, 10);
		citation.setLineWrap(true);
		submitButton = new JButton("Submit");
		cancel = new JButton("Cancel");
		moreNames = new JButton("+");
		deleteButton = new JButton("Delete");

		baseNameLabel = new JLabel("Base Name:");
		dateLabel = new JLabel("Date:");
		typeLabel = new JLabel("Type of Interaction:");
		locationLabel = new JLabel("Location:");
		socialLabel = new JLabel("Social Notes:");
		bibLabel = new JLabel("Bibliography Citation:");

		baseNamePanel = new JPanel(new BorderLayout());
		namePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		moreNamesPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		datePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		typePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		locationPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		socialPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		bibPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

		socialScroll = new JScrollPane(socialNotes);
		socialScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		bibScroll = new JScrollPane(citation);
		bibScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		moreNamesPanel.add(moreNames);
		namePanel.add(baseName);
		baseNamePanel.add(namePanel, BorderLayout.CENTER);
		baseNamePanel.add(moreNamesPanel, BorderLayout.EAST);
//		datePanel.add(date);
		datePanel.add(day);
		datePanel.add(month);
		datePanel.add(year);
		typePanel.add(type);
		locationPanel.add(location);
		socialPanel.add(socialScroll);
		bibPanel.add(bibScroll);

/*
		try {
			MaskFormatter dateMask = new MaskFormatter("##/##/####");
			dateMask.install(date);
		} catch (ParseException ex) {
			Logger.getLogger(SearchGUI.class.getName()).log(Level.SEVERE, null, ex);
		}
*/

		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		frame.add(createCenterPanel(additionalNames), BorderLayout.CENTER);
		frame.add(createWestPanel(additionalNames), BorderLayout.WEST);
		frame.add(createSouthPanel(), BorderLayout.SOUTH);

		if (connectionEdited == null) {
			frame.add(createCenterPanel(additionalNames), BorderLayout.CENTER);
			frame.add(createWestPanel(additionalNames), BorderLayout.WEST);
			setDefault();
		} else {
			List<Person> peopleList = connectionEdited.getPeopleList();
			additionalNames = peopleList.size() - 1;
			refreshPanel();
			setConnectionData(connectionEdited);
		}

		submitButton.addActionListener(this);
		cancel.addActionListener(this);
		moreNames.addActionListener(this);
		deleteButton.addActionListener(this);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	/**
	 * This method is used to create the west panel. It adds the appropriate
	 * buttons to the panel.
	 * 
	 * @param numNames
	 *            The number of additional names need in the panel.
	 * @return JPanel This returns the completed center panel.
	 */
	private JPanel createCenterPanel(int numNames) {
		centerPanel = new JPanel(new GridLayout(6 + numNames, 1));
		centerPanel.add(baseNamePanel);
		for (int i = 0; i < targetNames.size(); i++) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			namePanel.add(targetNames.get(i));
			panel.add(namePanel);
			centerPanel.add(panel);
		}
		for (int i = targetNames.size(); i < numNames; i++) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			targetNames.add(new JComboBox<>(baseNameChoices.toArray()));
			JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			namePanel.add(targetNames.get(i));
			panel.add(namePanel);
			centerPanel.add(panel);
		}
		centerPanel.add(datePanel);
		centerPanel.add(typePanel);
		centerPanel.add(locationPanel);
		centerPanel.add(socialPanel);
		centerPanel.add(bibPanel);
		return centerPanel;
	}

	/**
	 * This method is used to create the west panel. It adds the appropriate
	 * labels to the panel.
	 * 
	 * @param numNames
	 *            The number of additional names needed in the panel.
	 * @return JPanel This returns the completed west panel.
	 */
	private JPanel createWestPanel(int numNames) {
		westPanel = new JPanel(new GridLayout(6 + numNames, 1));
		westPanel.add(baseNameLabel);
		if (numNames > 0) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			JPanel toFromPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			toFromPanel.add(direction);
			panel.add(toFromPanel);
			westPanel.add(panel);
		}
		for (int i = 1; i < numNames; i++) {
			JPanel tempPanel = new JPanel();
			westPanel.add(tempPanel);
		}
		westPanel.add(dateLabel);
		westPanel.add(typeLabel);
		westPanel.add(locationLabel);
		westPanel.add(socialLabel);
		westPanel.add(bibLabel);
		return westPanel;
	}

	/**
	 * Creates the south panel and adds the appropriate buttons.
	 * 
	 * @return JPanel This returns the finished south panel.
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
	 * Makes the frame visible.
	 */
	void makeVisible() {
		frame.setVisible(true);
	}

	/**
	 * Sets all data fields to the default value. Refreshes the frame by calling
	 * the refreshPanel() method.
	 */
	void setDefault() {
		additionalNames = 1;
		targetNames.clear();
		day.setText("");
		month.setText("");
		year.setText("");
		baseName.setSelectedIndex(0);
		type.setSelectedIndex(0);
		location.setSelectedIndex(0);
		socialNotes.setText("");
		citation.setText("");

		refreshPanel();
	}

	/**
	 * Sets the Options in the Edit connection GUI to the selected connection's
	 * data
	 * 
	 * @param connection
	 *            - the connection who's data will fill in the GUI
	 */
	
	void setConnectionData(Connection connection) {
		Connection connectionToEdit = connection;
		List<Person> peopleList = connectionToEdit.getPeopleList();
		baseName.setSelectedIndex(baseNameChoices.indexOf(peopleList.get(0)));

		for (int i = 0; i < additionalNames; i++) {
			targetNames.get(i).setSelectedIndex(baseNameChoices.indexOf(peopleList.get(i + 1)));
		}
		type.setSelectedIndex(typeChoices.indexOf(connectionToEdit.getTypeInteraction()));
		location.setSelectedIndex(locationChoices.indexOf(connectionToEdit.getLocation()));
		direction.setSelectedIndex(directionChoices.indexOf(connectionToEdit.getDirection()));
		socialNotes.setText(connectionToEdit.getSocialNotes());
		citation.setText(connectionToEdit.getCitation());
		day.setText(connectionToEdit.getDay());
		month.setText(connectionToEdit.getMonth());
		year.setText(connectionToEdit.getYear());
		refreshPanel();
	}



	/**
	 * Removes current panels from the search frame. Updates the frame's size
	 * and adds the panels back to the frame.
	 */
	private void refreshPanel() {
		frame.remove(centerPanel);
		frame.remove(westPanel);
		frame.remove(southPanel);

		frame.add(createCenterPanel(additionalNames), BorderLayout.CENTER);
		frame.add(createWestPanel(additionalNames), BorderLayout.WEST);
		frame.add(createSouthPanel(), BorderLayout.SOUTH);

		frame.setSize(WIDTH, HEIGHT + 40 * (additionalNames));
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
	 * Will set the edited connection's data to whatever was put into the GUI if
	 * it is already a connection filled in. Will Add a new connection with all
	 * of the data filled in the GUI if there was not a connection already
	 * selected. Saves the connection data
	 * 
	 * @throws IOException
	 */
	
	private void submitClicked() throws IOException {
		ArrayList<Person> personListForConn = new ArrayList<>();
		personListForConn.add(storage.getPersonListForConnection(baseName.getSelectedItem().toString()));

		for (int i = 0; i < targetNames.size(); i++) {
			personListForConn.add(storage.getPersonListForConnection(targetNames.get(i).getSelectedItem().toString()));
		}

		if (connectionEdited != null && isSearch == false) {
			connectionEdited.setPeopleList(personListForConn);
			connectionEdited.setTypeInteraction(type.getSelectedItem().toString());
			connectionEdited.setLocation(location.getSelectedItem().toString());
			connectionEdited.setDirection(direction.getSelectedItem().toString());
			connectionEdited.setCitation(citation.getText());
			connectionEdited.setSocialNotes(socialNotes.getText());
			connectionEdited.setDay(day.getText());
			connectionEdited.setMonth(month.getText());
			connectionEdited.setYear(year.getText());
			storage.saveConnections();
			frame.dispose();
			JOptionPane.showMessageDialog(frame, "Successfully Saved!");
			home.updateTable();
		} else if (connectionEdited == null && isSearch == false)  {
			int nextID = storage.incrementAndGetNextConnectionIdNum();

			Connection newConnection = new Connection(nextID, day.getText(), month.getText(), year.getText(), type.getSelectedItem().toString(),
					location.getSelectedItem().toString(), citation.getText(), socialNotes.getText(), personListForConn,
					direction.getSelectedItem().toString());
			storage.addConnection(newConnection);
			storage.saveConnections();
			frame.dispose();
			JOptionPane.showMessageDialog(frame, "Successfully Saved!");
			home.updateTable();
		} else {
			//search functionality
		}
	}


	/**
	 * Based on the source of the event, the method will choose what the
	 * ConnectionGUI will do next.
	 * 
	 * @param ActionEvent
	 *            - event from the Add/Edit ConnectionGUI
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == submitButton) {
			try {
				submitClicked();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "There was an Error Saving your Person! Please try again.");
			}
			// adding or editing
			home.actionPerformed(event);
		} else if (event.getSource() == cancel) {
			// reset fields
			frame.dispose();
		} else if (event.getSource() == moreNames) {
			additionalNames++;
			refreshPanel();
		} else if (event.getSource() == deleteButton){
			//delete this connection!
		}
	}

}
