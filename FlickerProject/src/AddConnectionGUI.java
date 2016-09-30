
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class AddConnectionGUI implements ActionListener {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.mm.dd");

	private JFrame frame;
	private JComboBox<String> baseName;
	private JFormattedTextField date;
	private JComboBox<String> type;
	private JComboBox<String> location;
	private JTextArea socialNotes;
	private JTextArea bib;
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

	JButton add;
	private JButton cancel;
	private JButton moreNames;

	private int additionalNames;

	HomeScreenGUI home;

	public AddConnectionGUI(HomeScreenGUI home) {

		this.home = home;
		additionalNames = 0;

		baseName = new JComboBox<>(new String[] { "", "White", "Black" });
		date = new JFormattedTextField(DATE_FORMAT);
		date.setColumns(7);
		date.setFocusLostBehavior(JFormattedTextField.PERSIST);
		type = new JComboBox<>(new String[] { "", "White", "Black" });
		location = new JComboBox<>(new String[] { "", "White", "Black" });
		socialNotes = new JTextArea(2, 10);
		socialNotes.setLineWrap(true);
		bib = new JTextArea(2, 10);
		bib.setLineWrap(true);
		add = new JButton("Add");
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
		bibScroll = new JScrollPane(bib);
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
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.add(createWestPanel(additionalNames), BorderLayout.WEST);

		add.addActionListener(this);
		cancel.addActionListener(this);
		moreNames.addActionListener(this);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * This method is used to create the west panel.
	 * It adds the appropriate buttons to the panel.
	 * @param numNames The number of additional names need in the panel.
	 * @return JPanel This returns the completed center panel.
	 */

	private JPanel createCenterPanel(int numNames) {
		centerPanel = new JPanel(new GridLayout(6 + numNames, 1));
		centerPanel.add(baseNamePanel);
		for (int i = 0; i < numNames; i++) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			targetName = new JComboBox<>(new String[] { "", "White", "Black" });
			JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			namePanel.add(targetName);
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
	 * This method is used to create the west panel.
	 * It adds the appropriate labels to the panel.
	 * @param numNames The number of additional names needed in the panel.
	 * @return JPanel This returns the completed west panel.
	 */
	
	private JPanel createWestPanel(int numNames) {
		westPanel = new JPanel(new GridLayout(6 + numNames, 1));
		westPanel.add(baseNameLabel);
		if (numNames > 0) {
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
	 * Creates the south panel and adds the appropriate
	 * buttons.
	 * @return JPanel This returns the finished south panel.
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

	/**
	 * Sets all data fields to the default value. Refreshes the frame
	 * by calling the refreshPanel() method.
	 */
	
	void setDefault() {
		additionalNames = 0;

		baseName.setSelectedIndex(0);
		type.setSelectedIndex(0);
		location.setSelectedIndex(0);
		socialNotes.setText("");
		bib.setText("");

		refreshPanel();
	}
	
	/**
	 * Removes current panels from the search frame. Updates the frame's
	 * size and adds the panels back to the frame.
	 */

	private void refreshPanel() {
		frame.remove(centerPanel);
		frame.remove(westPanel);

		frame.add(createCenterPanel(additionalNames), BorderLayout.CENTER);
		frame.add(createWestPanel(additionalNames), BorderLayout.WEST);

		frame.setSize(250, 300 + 40 * (additionalNames));
		makeVisible();
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == add) {
			try {
				DataStorage storage = DataStorage.getMainDataStorage();
				int nextID = storage.incrementAndGetNextConnectionIdNum();
			
				List<Person> personListForConn = new ArrayList<>();
				personListForConn.add(storage.getPersonListForConnection(baseName.getSelectedItem().toString()));
				//this might be broken?
				personListForConn.add(storage.getPersonListForConnection(targetName.getSelectedItem().toString()));

				Connection newConnection = new Connection(nextID,
				date.getText(), type.getSelectedItem().toString(),
				location.getSelectedItem().toString(), bib.getText(),
				socialNotes.getText(), personListForConn,
				direction.getSelectedItem().toString() );
				storage.addConnection(newConnection);
				storage.saveConnections();

			} catch (IOException e) {
				e.printStackTrace();
			}
			frame.dispose();
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
