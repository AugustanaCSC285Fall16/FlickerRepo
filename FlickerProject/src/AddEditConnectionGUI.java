
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

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.mm.dd");

	// data fields
	private JFrame frame;
	private JComboBox<String> baseName;
	private JFormattedTextField date;
	private JComboBox<String> type;
	private JComboBox<String> location;
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
	private int additionalNames;
	private ArrayList<JComboBox> targetNames;
	HomeScreenGUI home;

	// class that relates to controlled vocabulary
	Vector<String> baseNameChoices;
	Vector<String> typeChoices;
	Vector<String> locationChoices;
	Vector<String> directionChoices;

	private Connection connectionEdited;

	public AddEditConnectionGUI(HomeScreenGUI home, Connection connection) {
		this.connectionEdited = connection;
		this.home = home;

		additionalNames = 0;
		targetNames = new ArrayList<>();

		baseNameChoices = new Vector<String>(
				Arrays.asList("", "Lauren", "Megan", "Tony", "Andrew", "Forrest", "White"));
		baseName = new JComboBox<>(new String[] { "", "Lauren", "Megan", "Tony", "Andrew", "Forrest", "White" });
		date = new JFormattedTextField(DATE_FORMAT);
		date.setColumns(7);
		date.setFocusLostBehavior(JFormattedTextField.PERSIST);
		typeChoices = new Vector<String>(Arrays.asList("", "Journal", "Letter", "Other"));
		type = new JComboBox<>(new String[] { "", "Journal", "Letter", "Other" });
		locationChoices = new Vector<String>(Arrays.asList("", "Paris", "Other"));
		location = new JComboBox<>(new String[] { "", "Paris", "Other" });
		socialNotes = new JTextArea(2, 10);
		socialNotes.setLineWrap(true);
		citation = new JTextArea(2, 10);
		citation.setLineWrap(true);
		submitButton = new JButton("Submit");
		cancel = new JButton("Cancel");
		moreNames = new JButton("+");

		baseNameLabel = new JLabel("Base Name:");
		dateLabel = new JLabel("Date:");
		typeLabel = new JLabel("Type:");
		locationLabel = new JLabel("Location:");
		socialLabel = new JLabel("Social Notes:");
		bibLabel = new JLabel("Bibliography:");

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
		datePanel.add(date);
		typePanel.add(type);
		locationPanel.add(location);
		socialPanel.add(socialScroll);
		bibPanel.add(bibScroll);

		try {
			MaskFormatter dateMask = new MaskFormatter("##/##/####");
			dateMask.install(date);
		} catch (ParseException ex) {
			Logger.getLogger(SearchGUI.class.getName()).log(Level.SEVERE, null, ex);
		}

		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250, 300);
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
			targetNames
					.add(new JComboBox<>(new String[] { "", "Lauren", "Megan", "Tony", "Andrew", "Forrest", "White" }));
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
			directionChoices = new Vector<String>(Arrays.asList("", "To", "From"));
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			direction = new JComboBox<>(new String[] { "", "To", "From" });
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
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(submitButton);
		southPanel.add(cancel);
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
		additionalNames = 0;
		targetNames.clear();
		date.setText("");
		baseName.setSelectedIndex(0);
		type.setSelectedIndex(0);
		location.setSelectedIndex(0);
		socialNotes.setText("");
		citation.setText("");

		refreshPanel();
	}

	void setConnectionData(Connection connection) {
		Connection connectionToEdit = connection;
		List<Person> peopleList = connectionToEdit.getPeopleList();
		baseName.setSelectedIndex(baseNameChoices.indexOf(peopleList.get(0).toString()));
		for (int i = 0; i < additionalNames; i++) {
			targetNames.get(i).setSelectedIndex(baseNameChoices.indexOf(peopleList.get(i + 1).toString()));
		}
		type.setSelectedIndex(typeChoices.indexOf(connectionToEdit.getTypeInteraction()));
		location.setSelectedIndex(locationChoices.indexOf(connectionToEdit.getLocation()));
		direction.setSelectedIndex(directionChoices.indexOf(connectionToEdit.getDirection()));
		socialNotes.setText(connectionToEdit.getSocialNotes());
		citation.setText(connectionToEdit.getCitation());
		// date.setText(connectionToEdit.getDate());
		//date.setValue(connectionToEdit.getDate());
		refreshPanel();
	}

	/**
	 * Removes current panels from the search frame. Updates the frame's size
	 * and adds the panels back to the frame.
	 */
	private void refreshPanel() {
		frame.remove(centerPanel);
		frame.remove(westPanel);

		frame.add(createCenterPanel(additionalNames), BorderLayout.CENTER);
		frame.add(createWestPanel(additionalNames), BorderLayout.WEST);

		frame.setSize(250, 300 + 40 * (additionalNames));
		makeVisible();
	}

	private void submitClicked() throws IOException {
		DataStorage storage = DataStorage.getMainDataStorage();
		ArrayList<Person> personListForConn = new ArrayList<>();
		personListForConn.add(storage.getPersonListForConnection(baseName.getSelectedItem().toString()));

		for (int i = 0; i < targetNames.size(); i++) {
			personListForConn.add(storage.getPersonListForConnection(targetNames.get(i).getSelectedItem().toString()));
		}

		if (connectionEdited != null) {
			connectionEdited.setPeopleList(personListForConn);
			connectionEdited.setTypeInteraction(type.getSelectedItem().toString());
			connectionEdited.setLocation(location.getSelectedItem().toString());
			connectionEdited.setDirection(direction.getSelectedItem().toString());
			connectionEdited.setCitation(citation.getText());
			connectionEdited.setSocialNotes(socialNotes.getText());
			connectionEdited.setDate(date.getText());
		} else {
			int nextID = storage.incrementAndGetNextConnectionIdNum();

			Connection newConnection = new Connection(nextID, date.getText(), type.getSelectedItem().toString(),
					location.getSelectedItem().toString(), citation.getText(), socialNotes.getText(), personListForConn,
					direction.getSelectedItem().toString());

			storage.addConnection(newConnection);
		}
		storage.saveConnections();
		frame.dispose();
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == submitButton) {
			try {
				submitClicked();
				home.updateTable();
			} catch (IOException e) {
				// TODO: show message dialog about error saving data?
				e.printStackTrace();
			}
			// adding or editing
			home.actionPerformed(event);
		} else if (event.getSource() == cancel) {
			// reset fields
			frame.dispose();
		} else if (event.getSource() == moreNames) {
			additionalNames++;
			refreshPanel();
		}
	}

}
