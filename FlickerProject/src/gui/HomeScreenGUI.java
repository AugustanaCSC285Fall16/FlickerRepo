package gui;

/*
 * Creates the home screen for the program. From here,
 * a user can navigate to other screens to access the 
 * data stored in a file. The data is then displayed
 * on the larger part of the screen for the user to
 * browse through.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dataModel.ConnectionQuery;
import dataModel.ContainsQuery;
import dataModel.DataStorage;
import dataModel.Date;
import dataModel.DateQuery;
import dataModel.PersonQuery;
import dataModel.TableRowViewable;

public class HomeScreenGUI implements ActionListener {
	private DataStorage mainStorage = DataStorage.getMainDataStorage();
	private DataStorage filtered;
	private ExportGUI exportGUI;

	private static final String[] FIELDS = new String[] { "Name", "Node Name", "Cultural ID", "Gender", "Occupation",
			"Date", "Location", "Interaction Type" };

	private JFrame frame;
	private JButton add;
	private JButton edit;
	private JButton resetFilter;
	private JButton export;
	private JButton submit;
	private JButton dateRange;

	private JPanel searchPanel;
	private JPanel criteriaPanel;
	private JPanel centerPanel;

	private JLabel filterOptionsLabel;
	private JLabel newDataLabel;

	private JTextField newData;
	private JTextField searchDay;
	private JTextField searchMonth;
	private JTextField searchYear;
	private JTextField searchDay2;
	private JTextField searchMonth2;
	private JTextField searchYear2;

	private JTabbedPane databases;
	private JScrollPane personPane;
	private JScrollPane connectionPane;

	private JTable personTableDisplay;
	private JTable connectionTableDisplay;

	private JComboBox<String> filterOptions;
	private boolean isDateRange = false;

	/**
	 * Creates a home screen GUI and depending on the permission passed in, will
	 * enable or disable add/edit
	 * 
	 * @param permission
	 *            if "Student" then buttons disabled
	 * @throws IOException
	 */
	public HomeScreenGUI(String permission) throws IOException {

		add = new JButton("Add");
		add.setBackground(new Color(255, 153, 153));
		edit = new JButton("Edit");
		edit.setBackground(new Color(255, 204, 153));
		export = new JButton("Export");
		export.setBackground(new Color(204, 255, 204));
		dateRange = new JButton("Date Range");
		databases = new JTabbedPane();
		databases.setBackground(Color.LIGHT_GRAY);
		
		exportGUI = new ExportGUI(this);

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 650);
		frame.setTitle("Home");
		frame.setLayout(new BorderLayout());
		frame.add(createWestPanel(), BorderLayout.WEST);
		frame.add(createSearchPanel(), BorderLayout.NORTH);
		frame.add(createCenterPanel(mainStorage), BorderLayout.CENTER);

		add.addActionListener(this);
		edit.addActionListener(this);
		resetFilter.addActionListener(this);
		export.addActionListener(this);
		submit.addActionListener(this);
		resetFilter.addActionListener(this);
		filterOptions.addActionListener(this);
		dateRange.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		if (permission.equals("Student")) {
			add.setEnabled(false);
			edit.setEnabled(false);
		}
	}

	/**
	 * This method creates a table to be displayed. Also overrides the table
	 * model to make it non Editable. Centers the data.
	 * 
	 * @param columnNamesArray
	 *            - String Array of column titles
	 * @param rowItems
	 *            - What will be in the rows. Could be for Person or Connection
	 * @return JTable - Table that is all filled out with the correct data
	 * @throws IOException
	 */
	private JTable createDisplayTable(String[] columnNamesArray, Collection<? extends TableRowViewable> rowItems)
			throws IOException {
		Vector<String> columnNames = new Vector<>(Arrays.asList(columnNamesArray));
		Vector<Vector<String>> tableData = new Vector<>();

		for (TableRowViewable item : rowItems) {
			String[] nextRow = item.toTableRowArray();
			tableData.addElement(new Vector(Arrays.asList(nextRow)));
		}

		JTable table = new JTable();
		DefaultTableModel nonEditableTableModel = new DefaultTableModel() {
			@Override
			// makes all cells unable to be edited
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		nonEditableTableModel.setDataVector(tableData, columnNames);
		table.setModel(nonEditableTableModel);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, centerRenderer);
		return table;
	}

	/**
	 * This method is used to create the west panel. It adds the appropriate
	 * labels to the panel.
	 * 
	 * @return JPanel This returns the completed west panel.
	 */
	private JPanel createWestPanel() {
		JPanel westPanel = new JPanel(new GridLayout(3, 1));
		westPanel.add(add);
		westPanel.add(edit);
		westPanel.add(export);
		return westPanel;
	}

	/**
	 * Creates the center panel and adds the appropriate display tables to it.
	 * 
	 * @return JPanel a completed CenterPanel.
	 * @throws IOException
	 */
	private JPanel createCenterPanel(DataStorage storage) throws IOException {
		centerPanel = new JPanel(new BorderLayout());
		personTableDisplay = createDisplayTable(storage.getPersonHeaderRow(), storage.getPeopleList());
		connectionTableDisplay = createDisplayTable(storage.getConnectionHeaderRow(), storage.getConnectionList());
		personPane = new JScrollPane();
		connectionPane = new JScrollPane();
		personPane.getViewport().add(personTableDisplay);
		connectionPane.getViewport().add(connectionTableDisplay);
		databases.add("Persons Data", personPane);
		databases.add("Connections Data", connectionPane);
		centerPanel.add(databases, BorderLayout.CENTER);
		return centerPanel;
	}

	/**
	 * Creates the search panel and adds the appropriate panels and labels
	 * 
	 * @return JPanel completed search panel
	 */
	private JPanel createSearchPanel() {
		searchPanel = new JPanel(new FlowLayout());
		submit = new JButton("Submit");
		resetFilter = new JButton("Reset");
		resetFilter.setEnabled(false);
		searchPanel.add(createCriteriaPanel());
		searchPanel.add(submit);
		searchPanel.add(resetFilter);
		return searchPanel;
	}

	/**
	 * Creates a criteria panel and adds the appropriate panels and labels
	 * 
	 * @return JPanel a completed criteria panel
	 */
	public JPanel createCriteriaPanel() {
		criteriaPanel = new JPanel();
		filterOptionsLabel = new JLabel("Filter option: ");
		filterOptions = new JComboBox<>(FIELDS);
		newDataLabel = new JLabel("Criteria: ");
		newData = new JTextField(15);
		criteriaPanel.add(filterOptionsLabel);
		criteriaPanel.add(filterOptions);
		criteriaPanel.add(newDataLabel);
		criteriaPanel.add(newData);
		return criteriaPanel;
	}

	/**
	 * changes the search panel based on if the option selected is date or not
	 */
	private void changeSearchPanel() {
		int option = filterOptions.getSelectedIndex();
		isDateRange = false;
		criteriaPanel.removeAll();
		criteriaPanel.add(filterOptionsLabel);
		criteriaPanel.add(filterOptions);
		criteriaPanel.add(newDataLabel);
		if (option == 5) {
			searchDay = new JTextField(2);
			searchMonth = new JTextField(2);
			searchYear = new JTextField(4);
			criteriaPanel.add(searchMonth);
			criteriaPanel.add(searchDay);
			criteriaPanel.add(searchYear);
			criteriaPanel.add(dateRange);
			frame.revalidate();
		} else {
			criteriaPanel.add(newData);
			newData.setText("");
			filterOptions.setSelectedIndex(option);
			frame.revalidate();
		}
	}

	/**
	 * Adds the new fields for the date range
	 */
	public void addDateRangeData() {
		criteriaPanel.remove(dateRange);
		searchDay2 = new JTextField(2);
		searchMonth2 = new JTextField(2);
		searchYear2 = new JTextField(4);
		JLabel toLabel = new JLabel("to");
		criteriaPanel.add(toLabel);
		criteriaPanel.add(searchMonth2);
		criteriaPanel.add(searchDay2);
		criteriaPanel.add(searchYear2);
		frame.revalidate();
	}

	/**
	 * Updates the table to see new data that was added.
	 * 
	 * @throws IOException
	 */
	public void updateTable(DataStorage storage) throws IOException {
		databases.removeAll();
		centerPanel.removeAll();
		frame.remove(centerPanel);
		frame.add(createCenterPanel(storage), BorderLayout.CENTER);
		frame.revalidate();
	}

	/**
	 * Retrieves and returns the filtered storage.
	 * 
	 * @return a data storage object of just the filtered data.
	 */
	public DataStorage getStorage() {
		if (mainStorage.isFiltered()) {
			return filtered;
		} else {
			return mainStorage;
		}
	}

	/**
	 * A message dialog will first pop up asking what the user would like to add
	 * then PopUp will open based on what button they choose.
	 */
	public void addClicked() {
		Object[] options = { "Add Person", "Add Connection", "Add Controlled Vocab" };
		int val = JOptionPane.showOptionDialog(frame, "What would you like to add?", "Add...",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (val == 0) { // if artist
			AddEditPersonGUI personGUI = new AddEditPersonGUI(this, null);
			personGUI.makeVisible();
		} else if (val == 1) { // if connection
			AddEditConnectionGUI connectionGUI = new AddEditConnectionGUI(this, null);
			connectionGUI.makeVisible();
		} else if (val == 2) { // if controlled vocabulary
			AddDataGUI vocabGUI = new AddDataGUI();
		}
	}

	/**
	 * A Popup with either the a person information filled in or a connection
	 * information filled in will pop up. If no row is selected, a message will
	 * pop up informing the user to click a row to edit.
	 */
	public void editClicked() {
		if (databases.getSelectedComponent() == personPane) {
			int selectedRow = personTableDisplay.getSelectedRow();
			if (selectedRow > -1) {
				personTableDisplay.getModel().getValueAt(selectedRow, 0);
				String IDCellText = (String) personTableDisplay.getModel().getValueAt(selectedRow, 0);
				int personID = Integer.parseInt(IDCellText);
				AddEditPersonGUI personGUI = new AddEditPersonGUI(this, mainStorage.getPersonFromID(personID));
				personGUI.makeVisible();
			} else {
				JOptionPane.showMessageDialog(frame, "Click a row first!");
			}
		} else { // is connectionTableDisplay
			int selectedRow = connectionTableDisplay.getSelectedRow();
			if (selectedRow > -1) {
				String IDCellText = (String) connectionTableDisplay.getModel().getValueAt(selectedRow, 0);
				int connectionID = Integer.parseInt(IDCellText);
				AddEditConnectionGUI connectionGUI = new AddEditConnectionGUI(this,
						mainStorage.getConnectionFromID(connectionID));
				connectionGUI.makeVisible();
			} else {
				JOptionPane.showMessageDialog(frame, "Click a row first!");
			}
		}
	}

	/**
	 * Checks to see if any of the date fields are blank. Alerts user if some
	 * left blank.
	 * 
	 * @return boolean true if all the fields have something in them.
	 */
	public boolean checkIfFilledDate() {
		if (searchDay.getText().equals("") || searchMonth.getText().equals("") || searchYear.getText().equals("")
				|| searchDay2.getText().equals("") || searchMonth2.getText().equals("")
				|| searchYear2.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter a full date for both dates");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks to see if the date is in the right format and updates the table
	 * with data from that data or date range.
	 * 
	 * @throws IOException
	 */

	public void dateQuery() throws IOException {
		if (isDateRange) {
			if (checkIfFilledDate()) {
				Date targetDate = new Date(Integer.parseInt(searchYear.getText()),
						Integer.parseInt(searchMonth.getText()), Integer.parseInt(searchDay.getText()));
				Date targetDate2 = new Date(Integer.parseInt(searchYear2.getText()),
						Integer.parseInt(searchMonth2.getText()), Integer.parseInt(searchDay2.getText()));
				if (!targetDate.isValidDate() || !targetDate2.isValidDate()) {
					JOptionPane.showMessageDialog(null, "Invalid date \n(i.e. m/d/yyyy)");
				} else {
					resetFilter.setEnabled(true);
					ConnectionQuery connectionQuery = new DateQuery(targetDate, targetDate2);
					filtered = mainStorage.connectionFilter(connectionQuery);
					if (!filtered.getConnectionArrayList().isEmpty()) {
						updateTable(filtered);
					} else {
						JOptionPane.showMessageDialog(null, "No results found");
					}
				}
			}
		} else {
			if (searchDay.getText().equals("") || searchMonth.getText().equals("") || searchYear.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter a full date");
			} else {
				Date targetDate = new Date(Integer.parseInt(searchYear.getText()),
						Integer.parseInt(searchMonth.getText()), Integer.parseInt(searchDay.getText()));
				if (!targetDate.isValidDate()) {
					JOptionPane.showMessageDialog(null, "Invalid date \n(i.e. m/d/yyyy)");
				} else {
					resetFilter.setEnabled(true);
					ConnectionQuery connectionQuery = new DateQuery(targetDate, null);
					filtered = mainStorage.connectionFilter(connectionQuery);
					if (!filtered.getConnectionArrayList().isEmpty()) {
						updateTable(filtered);
					} else {
						JOptionPane.showMessageDialog(null, "No results found");
					}
				}
			}
		}
	}

	/**
	 * When submit in search is clicked, checks to see if the user is in the
	 * right tab for the data they are searching for and filters/displays the
	 * data for the criteria
	 * 
	 * @throws IOException
	 */
	public void submitSearch() throws IOException {
		int option = filterOptions.getSelectedIndex();
		if (databases.getSelectedComponent() == personPane) {
			if (option == 5 || option == 6 || option == 7) { // date, location,
																// type
																// Interaction
				JOptionPane.showMessageDialog(null,
						"Invalid query option. \nPlease move to connections tab or pick a new query option.");
			} else {
				if (newData.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter search criteria");
				} else {
					resetFilter.setEnabled(true);
					export.setEnabled(false);
					PersonQuery personQuery = new ContainsQuery(newData.getText(),
							filterOptions.getSelectedItem().toString());
					filtered = mainStorage.personFilter(personQuery);
					if(!filtered.getPeopleArrayList().isEmpty()) {
						updateTable(filtered);
					} else {
						JOptionPane.showMessageDialog(null, "No results found");
					}
					
				}
			}
		} else { // is connectionTableDisplay
			export.setEnabled(true);
			if (option == 5) { // date
				dateQuery();
			} else {
				if (newData.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter search criteria");
				} else {
					resetFilter.setEnabled(true);
					ConnectionQuery connectionQuery = new ContainsQuery(newData.getText(),
							filterOptions.getSelectedItem().toString());
					filtered = mainStorage.connectionFilter(connectionQuery);
					if (!filtered.getConnectionArrayList().isEmpty()) {
						updateTable(filtered);
					} else {
						JOptionPane.showMessageDialog(null, "No results found");
					}
				}
			}
		}
	}

	/**
	 * Based on the source of the event, the method will choose what the GUI
	 * will do next.
	 * 
	 * @param ActionEvent
	 *            - event from the HomeScreenGUI
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			Object source = event.getSource();
			if (source == submit) {
				submitSearch();
				mainStorage.setFiltered(true);
			} else if (source == resetFilter) {
				updateTable(mainStorage);
				resetFilter.setEnabled(false);
				mainStorage.setFiltered(false);
				export.setEnabled(true);
			} else if (source == add) {
				addClicked();
			} else if (source == edit) {
				editClicked();
			} else if (source == export) {
				exportGUI.makeVisible();
			} else if (source == filterOptions) {
				changeSearchPanel();
			} else if (source == dateRange) {
				isDateRange = true;
				addDateRangeData();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An Error has occured!");
		}
	}
}
