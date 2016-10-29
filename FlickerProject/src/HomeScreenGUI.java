
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
	private JLabel filterOptionsLabel;
	private JComboBox<String> filterOptions;
	private JLabel newDataLabel;
	private JTextField newData;
	private JButton submit;
	private JTabbedPane databases;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JTable personTableDisplay;
	private JTable connectionTableDisplay;
	private JScrollPane personPane;
	private JScrollPane connectionPane;

	private ExportGUI exportGUI;

	public HomeScreenGUI() throws IOException {
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

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
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
		connectionTableDisplay = createDisplayTable(storage.getConnectionHeaderRow(),
				storage.getConnectionList());
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
		filterOptionsLabel = new JLabel("Filter option: ");
		filterOptions = new JComboBox<>(FIELDS);
		newDataLabel = new JLabel("Criteria: ");
		newData = new JTextField(15);
		submit = new JButton("Submit");
		resetFilter = new JButton("Reset");
		resetFilter.setEnabled(false);
		searchPanel.add(filterOptionsLabel);
		searchPanel.add(filterOptions);
		searchPanel.add(newDataLabel);
		searchPanel.add(newData);
		searchPanel.add(submit);
		searchPanel.add(resetFilter);
		return searchPanel;
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

	public DataStorage getFilteredStorage() {
		return filtered;
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
			AddEditPersonGUI personGUI = new AddEditPersonGUI(null, this, null);
			personGUI.makeVisible();
		} else if (val == 1) { // if connection
			AddEditConnectionGUI connectionGUI = new AddEditConnectionGUI(null, this, null);
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
				AddEditPersonGUI personGUI = new AddEditPersonGUI(null, this, mainStorage.getPersonFromID(personID));
				personGUI.addDeleteButton();
				personGUI.makeVisible();
			} else {
				JOptionPane.showMessageDialog(frame, "Click a row first!");
			}
		} else { // is connectionTableDisplay
			int selectedRow = connectionTableDisplay.getSelectedRow();
			if (selectedRow > -1) {
				String IDCellText = (String) connectionTableDisplay.getModel().getValueAt(selectedRow, 0);
				int connectionID = Integer.parseInt(IDCellText);
				AddEditConnectionGUI connectionGUI = new AddEditConnectionGUI(null, this,
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
			export.setEnabled(false);
			System.out.println(newData.getText());
			System.out.println(filterOptions.getSelectedItem().toString());
			PersonQuery personQuery = new ContainsQuery(newData.getText(), filterOptions.getSelectedItem().toString());
			filtered = mainStorage.personFilter(personQuery);
			System.out.println("update");
			updateTable(filtered);
		} else { // is connectionTableDisplay
			if (option == 5) {
				// ConnectionQuery connectionQuery = new DateQuery();
			} else {
				ConnectionQuery connectionQuery = new ContainsQuery(newData.getText(),
						filterOptions.getSelectedItem().toString());
				filtered = mainStorage.connectionFilter(connectionQuery);
				updateTable(filtered);
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
		} else if(source == resetFilter) {
			try {
				updateTable(mainStorage);
				newData.setText("");
				resetFilter.setEnabled(false);
				mainStorage.setFiltered(false);
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
			Export exportAll = new Export();
			try {
				exportAll.exportToPalladio(mainStorage.getConnectionList());
				exportAll.exportToGephiNodes();
				exportAll.exportToGephiEdges(mainStorage.getConnectionList());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Could not export the data.");
			}

		}
	}
}
