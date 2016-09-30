
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

	private JFrame frame;
	private JButton search;
	private JButton add;
	private JButton edit;
	private JButton save;
	private JTabbedPane personsTab;
	// private JTabbedPane connectionsTab;
	private JButton export;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JTable personTableDisplay;
	private JTable connectionTableDisplay;

	private SearchGUI searchGUI;
	private AddConnectionGUI connectionGUI;
	private ExportGUI exportGUI;

	public HomeScreenGUI() throws IOException {
		save = new JButton("Save Changes");
		search = new JButton("Search");
		add = new JButton("Add");
		edit = new JButton("Edit");
		personsTab = new JTabbedPane();
		// connectionsTab = new JTabbedPane();
		export = new JButton("Export");
		// tableDisplay = displayTable(mainStorage.getPersonHeaderRow(),
		// mainStorage.getPeopleList());

		searchGUI = new SearchGUI(this);
		connectionGUI = new AddConnectionGUI(this);
		exportGUI = new ExportGUI(this);
		southPanel = new JPanel();

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setTitle("Home");
		frame.setLayout(new BorderLayout());
		frame.add(createWestPanel(), BorderLayout.WEST);
		frame.add(createCenterPanel(), BorderLayout.CENTER);

		search.addActionListener(this);
		add.addActionListener(this);
		edit.addActionListener(this);
		// personsTab.addActionListener(this);
		// connectionsTab.addActionListener(this);
		export.addActionListener(this);
		save.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * This method is used to create the table. It creates the table.
	 * 
	 * @return JPanel This returns the completed table.
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
		// table.setEnabled(false);
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
		westPanel.add(search);
		westPanel.add(add);
		westPanel.add(edit);
		return westPanel;
	}

	/**
	 * This method is used to create the north panel. It adds the appropriate
	 * buttons to the panel.
	 * 
	 * @return JPanel This returns the completed north panel.
	 */

	// private JPanel createNorthPanel() throws IOException {
	// JPanel northPanel = new JPanel(new GridLayout(1, 5));
	// personsTab.add("Persons Data",
	// displayTable(mainStorage.getPersonHeaderRow(),
	// mainStorage.getPeopleList()));
	// personsTab.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
	// personsTab.add("Connections Data",
	// displayTable(mainStorage.getConnectionHeaderRow(),
	// mainStorage.getConnectionList()));
	// northPanel.add(personsTab);
	// //northPanel.add(connectionsTab);
	// return northPanel;
	// }

	// creates tablePanel that displays the table of data
	// private JPanel createTablePanel() throws IOException {
	// JPanel tablePanel = new JPanel(new BorderLayout());
	// JScrollPane scrollPane = new JScrollPane(tableDisplay);
	// tableDisplay.setFillsViewportHeight(true);
	// tablePanel.add(scrollPane);
	// return tablePanel;
	// }

	private JPanel createCenterPanel() throws IOException {
		centerPanel = new JPanel(new BorderLayout());
		personTableDisplay = createDisplayTable(mainStorage.getPersonHeaderRow(), mainStorage.getPeopleList());
		connectionTableDisplay = createDisplayTable(mainStorage.getConnectionHeaderRow(),
				mainStorage.getConnectionList());
		personsTab.add("Persons Data", personTableDisplay);
		personsTab.add("Connections Data", connectionTableDisplay);
		centerPanel.add(personsTab);
		// centerPanel.add(connectionsTab);
		// centerPanel = new JPanel(new BorderLayout());
		// centerPanel.add(createNorthPanel(), BorderLayout.NORTH);
		// centerPanel.add(createTablePanel(), BorderLayout.CENTER);
		return centerPanel;
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == search) {
			// tableDisplay.setEnabled(false);
			searchGUI.setDefault();
			searchGUI.makeVisible();
		} else if (source == searchGUI.search) {

			southPanel.removeAll();

			southPanel = new JPanel(new GridLayout(1, 2));
			JButton clear = new JButton("Clear");
			JButton edit = new JButton("Edit");

			southPanel.add(clear);
			southPanel.add(export);

			centerPanel.add(southPanel, BorderLayout.SOUTH);
			centerPanel.revalidate();

		} else if (source == add) {
			// TODO: this code seems overly messy, and may not be needed any
			// more? check and clean up later...
			southPanel.removeAll();
			centerPanel.add(southPanel, BorderLayout.SOUTH);
			centerPanel.revalidate();

			Object[] options = { "Add Artist", "Add Connection" };
			int val = JOptionPane.showOptionDialog(frame, "What would you like to add?", "Answer me",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (val == 0) { // if artist
				AddEditPersonGUI personGUI = new AddEditPersonGUI(this, null);
				personGUI.makeVisible();
			} else if (val == 1) { // if connection
				
				connectionGUI.setDefault();
			}
		} else if (source == edit) {
			southPanel.removeAll();

			southPanel = new JPanel(new GridLayout(1, 2));

			southPanel.add(save);

			centerPanel.add(southPanel, BorderLayout.SOUTH);
			centerPanel.revalidate();

			if (personsTab.getSelectedComponent() == personTableDisplay) {
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
					connectionGUI.setConnectionData(mainStorage.getConnectionFromID(connectionID));
				} else {
					JOptionPane.showMessageDialog(frame, "Click a row first!");
				}
			}

		} else if (source == save) {
			if (personsTab.getSelectedComponent() == personTableDisplay) {
				// Set the person that was selected to the new data that was
				// selected.

			} else {

			}
		}
	}
}
