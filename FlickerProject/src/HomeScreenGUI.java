
/*
 * Creates the home screen for the program. From here,
 * a user can navigate to other screens to access the 
 * data stored in a file. The data is then displayed
 * on the larger part of the screen for the user to
 * browse through.
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import com.opencsv.CSVReader;

public class HomeScreenGUI implements ActionListener {
	DataStorage mainStorage = DataStorage.getMainDataStorage();
	DataStorage filtered;

	private static final String[] FIELDS = new String[] { "Name", "Node Name", "Cultural ID", "Gender", "Occupation",
			"Date", "Location", "Type" };

	private JFrame frame;
	private JButton add;
	private JButton edit;
	private JButton save;
	private JButton clear;
	private JButton resetFilter;
	private JButton export;
	private JPanel searchPanel;
	private JPanel criteriaPanel;
	private JLabel filterOptionsLabel;
	private JComboBox<String> filterOptions;
	private JLabel newDataLabel;
	private JTextField newData;
	private JTextField searchDay;
	private JTextField searchMonth;
	private JTextField searchYear;
	private JButton submit;
	private JTabbedPane databases;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JTable personTableDisplay;
	private JTable connectionTableDisplay;
	private JScrollPane personPane;
	private JScrollPane connectionPane;

	private ExportGUI exportGUI;

	public HomeScreenGUI(String permission) throws IOException {
		
		save = new JButton("Save Changes");
		add = new JButton("Add");
		edit = new JButton("Edit");
		databases = new JTabbedPane();
		export = new JButton("Export");

		exportGUI = new ExportGUI(this);
		southPanel = new JPanel();

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setTitle("Home");
		frame.setLayout(new BorderLayout());
		frame.add(createWestPanel(), BorderLayout.WEST);
		frame.add(createSearchPanel(), BorderLayout.NORTH);
		frame.add(createCenterPanel(mainStorage), BorderLayout.CENTER);

		add.addActionListener(this);
		edit.addActionListener(this);
		resetFilter.addActionListener(this);
		export.addActionListener(this);
		save.addActionListener(this);
		submit.addActionListener(this);
		resetFilter.addActionListener(this);
		filterOptions.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		if (permission.equals("Student")){
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

		JTable table = new JTable(); // tableData, columnNames);
		DefaultTableModel nonEditableTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		nonEditableTableModel.setDataVector(tableData, columnNames);
		table.setModel(nonEditableTableModel);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
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
	 * @return JPanel Returns a completed CenterPanel.
	 * @throws IOException
	 */
	private JPanel createCenterPanel(DataStorage storage) throws IOException {
		centerPanel = new JPanel(new BorderLayout());
		System.out.println(storage.getPeopleArrayList());
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

	private void changeSearchPanel() {
		int option = filterOptions.getSelectedIndex();
		if (option == 5) {
			criteriaPanel.remove(newData);
			searchDay = new JTextField(2);
			searchMonth = new JTextField(2);
			searchYear = new JTextField(4);
			criteriaPanel.add(searchMonth);
			criteriaPanel.add(searchDay);
			criteriaPanel.add(searchYear);
			frame.revalidate();
		} else {
			criteriaPanel.removeAll();
			criteriaPanel.add(filterOptionsLabel);
			criteriaPanel.add(filterOptions);
			criteriaPanel.add(newDataLabel);
			criteriaPanel.add(newData);
			filterOptions.setSelectedIndex(option);
			frame.revalidate();
		}
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

	public DataStorage getStorage() {
		if(mainStorage.isFiltered()){
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
		int val = JOptionPane.showOptionDialog(frame, "What would you like to add?", "Answer me",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (val == 0) { // if artist
			AddEditPersonGUI personGUI = new AddEditPersonGUI(this, null);
			personGUI.makeVisible();
		} else if (val == 1) { // if connection
			AddEditConnectionGUI connectionGUI = new AddEditConnectionGUI(this, null);
			connectionGUI.makeVisible();
		} else if (val == 2) { // if controlled vocabulary
			AddData vocabGUI = new AddData();
			// vocabGUI.makeVisible();
		}
	}

	/**
	 * A Popup with either the a person information filled in or a connection
	 * information filled in will pop up. If no row is selected, a message will
	 * pop up informing the user to click a row to edit.
	 * 
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
				connectionGUI.addDeleteButton();
				connectionGUI.makeVisible();
			} else {
				JOptionPane.showMessageDialog(frame, "Click a row first!");
			}
		}

	}

	public void submitSearch() throws IOException {
		int option = filterOptions.getSelectedIndex();
		if (databases.getSelectedComponent() == personPane) {
			if (option == 5 || option == 6 || option == 7) {
				JOptionPane.showMessageDialog(null, "Invalid query option");
			} else {
				if (newData.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter search criteria");
				}
				export.setEnabled(false);
				PersonQuery personQuery = new ContainsQuery(newData.getText(),
						filterOptions.getSelectedItem().toString());
				filtered = mainStorage.personFilter(personQuery);
				System.out.println("update");
				updateTable(filtered);
			}

		} else { // is connectionTableDisplay
			export.setEnabled(true);
			if (option == 5) {
				if (searchDay.getText().equals("") || searchMonth.getText().equals("")
						|| searchYear.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter a full date");
				} else {
					Date targetDate = new Date(Integer.parseInt(searchYear.getText()),
							Integer.parseInt(searchMonth.getText()), Integer.parseInt(searchDay.getText()));
					if (!targetDate.isValidDate()) {
						JOptionPane.showMessageDialog(null, "Invalid date \n(i.e. m/d/yyyy)");
					} else {
						ConnectionQuery connectionQuery = new DateQuery(targetDate);
						filtered = mainStorage.connectionFilter(connectionQuery);
						if (!filtered.getConnectionArrayList().isEmpty()) {
							updateTable(filtered);
						} else {
							JOptionPane.showMessageDialog(null, "No results found");
						}
					}
				}
			} else {
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

	/**
	 * Based on the source of the event, the method will choose what the GUI
	 * will do next.
	 * 
	 * @param ActionEvent
	 *            - event from the HomeScreenGUI
	 */
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == submit) {
			try {
				System.out.println("Submit");
				resetFilter.setEnabled(true);
				submitSearch();
				mainStorage.setFiltered(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (source == resetFilter) {
			try {
				updateTable(mainStorage);
				newData.setText("");
				resetFilter.setEnabled(false);
				mainStorage.setFiltered(false);
				export.setEnabled(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (source == add) {
			addClicked();
		} else if (source == edit) {
			editClicked();
		} else if (source == export) {
			ExportGUI exportGui = new ExportGUI(this);
			exportGUI.makeVisible();

		} else if (source == filterOptions) {
			changeSearchPanel();
		}
	}
}
