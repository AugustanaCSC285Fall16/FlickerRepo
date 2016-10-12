
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
	private JTabbedPane databases;
	private JButton export;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JTable personTableDisplay;
	private JTable connectionTableDisplay;

	private SearchGUI searchGUI;
	private ExportGUI exportGUI;

	public HomeScreenGUI() throws IOException {
		save = new JButton("Save Changes");
		search = new JButton("Search");
		add = new JButton("Add");
		edit = new JButton("Edit");
		databases = new JTabbedPane();
		export = new JButton("Export");

		searchGUI = new SearchGUI(this);
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
		export.addActionListener(this);
		save.addActionListener(this);

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
		westPanel.add(search);
		westPanel.add(add);
		westPanel.add(edit);
		return westPanel;
	}

	/**
	 * Creates the center panel and adds the appropriate display tables to it.
	 * 
	 * @return JPanel Returns a completed CenterPanel.
	 * @throws IOException
	 */
	private JPanel createCenterPanel() throws IOException {
		centerPanel = new JPanel(new BorderLayout());
		personTableDisplay = createDisplayTable(mainStorage.getPersonHeaderRow(), mainStorage.getPeopleList());
		connectionTableDisplay = createDisplayTable(mainStorage.getConnectionHeaderRow(),
				mainStorage.getConnectionList());
		databases.add("Persons Data", personTableDisplay);
		databases.add("Connections Data", connectionTableDisplay);
		centerPanel.add(databases);
		return centerPanel;
	}

	/**
	 * Updates the table to see new data that was added.
	 * 
	 * @throws IOException
	 */
	public void updateTable() throws IOException {
		databases.removeAll();
		centerPanel.removeAll();
		frame.remove(centerPanel);
		frame.add(createCenterPanel(), BorderLayout.CENTER);
		frame.revalidate();
	}

	/**
	 * A search PopUp will open and the user will be able to fill it out to
	 * search for their criteria.
	 */
	public void searchClicked() {
		southPanel.removeAll();
		southPanel = new JPanel(new GridLayout(1, 2));
		JButton clear = new JButton("Clear");
		southPanel.add(clear);
		southPanel.add(export);
		centerPanel.add(southPanel, BorderLayout.SOUTH);
		centerPanel.revalidate();
	}

	/**
	 * A message dialog will first pop up asking what the user would like to add
	 * then PopUp will open based on what button they choose.
	 */
	public void addClicked() {
		Object[] options = { "Add Artist", "Add Connection" };
		int val = JOptionPane.showOptionDialog(frame, "What would you like to add?", "Answer me",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (val == 0) { // if artist
			AddEditPersonGUI personGUI = new AddEditPersonGUI(this, null);
			personGUI.makeVisible();
		} else if (val == 1) { // if connection
			AddEditConnectionGUI connectionGUI = new AddEditConnectionGUI(this, null);
			connectionGUI.makeVisible();
		}
	}

	/**
	 * A Popup with either the a person information filled in or a connection
	 * information filled in will pop up. If no row is selected, a message will
	 * pop up informing the user to click a row to edit.
	 * 
	 */
	public void editClicked() {
		if (databases.getSelectedComponent() == personTableDisplay) {
			int selectedRow = personTableDisplay.getSelectedRow();
			if (selectedRow > -1) {
				personTableDisplay.getModel().getValueAt(selectedRow, 0);
				String IDCellText = (String) personTableDisplay.getModel().getValueAt(selectedRow, 0);
				int personID = Integer.parseInt(IDCellText);
				AddEditPersonGUI personGUI = new AddEditPersonGUI(this, mainStorage.getPersonFromID(personID));
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
				AddEditConnectionGUI connectionGUI = new AddEditConnectionGUI(this,
						mainStorage.getConnectionFromID(connectionID));
				connectionGUI.addDeleteButton();
				connectionGUI.makeVisible();
			} else {
				JOptionPane.showMessageDialog(frame, "Click a row first!");
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
		if (source == search) {
			searchGUI.setDefault();
			searchGUI.makeVisible();
		} else if (source == searchGUI.search) {
			searchClicked();
		} else if (source == add) {
			addClicked();
		} else if (source == edit) {
			editClicked();
		}
	}
}
