import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;;

public class SearchResultsGUI implements ActionListener{

	// data fields
	private JFrame frame;
	private JTabbedPane databases;
	private JPanel mainPanel;
	private JPanel personResultsPanel;
	private JPanel connectionResultsPanel;
	private JTable personResultsTable;
	private JTable connectionResultsTable;
	private JButton export;
	private JButton edit;
	private JScrollPane personScrollPane;
	private JScrollPane connectionScrollPane;
	private JPanel buttonPanel;
	private JPanel personTab;
	private JPanel connectionTab;
	private String[] personColumnNames;
	private String [] connectionColumnNames;
	private Collection<? extends TableRowViewable> personData;
	private Collection<? extends TableRowViewable> connectionData;
	DataStorage storage;
	SearchBackend search;
	HomeScreenGUI home;
	SearchGUI searchGUI;
	
	public SearchResultsGUI() {
		
	}

	public SearchResultsGUI(HomeScreenGUI home, String[] personColumnNamesArray, Collection<? extends TableRowViewable> personResultsData,
			String[] connectionColumnNamesArray, Collection<? extends TableRowViewable> connectionResultsData)
			throws IOException {
		this.searchGUI = searchGUI;
		this.home = home;
		personColumnNames = personColumnNamesArray;
		connectionColumnNames = connectionColumnNamesArray;
		personData = personResultsData;
		connectionData = connectionResultsData;
		databases = new JTabbedPane();
		personTab = new JPanel();
		connectionTab = new JPanel();
		export = new JButton("Export");
		edit = new JButton("Edit");
		
		frame = new JFrame();
		
		frame.setSize(1000, 500);
		frame.setTitle("Search Results");

		storage = DataStorage.getMainDataStorage();
		search = new SearchBackend();
		
		edit.addActionListener(this);
		export.addActionListener(this);

		frame.add(createMainPanel());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	public JPanel createMainPanel() throws IOException {
		mainPanel = new JPanel(new BorderLayout());
		personTab = createPersonResultsTab();
		connectionTab = createConnectionResultsTab();
		databases.addTab("Person Results", personTab);
		databases.addTab("Connection Results", connectionTab);
		mainPanel.add(databases, BorderLayout.CENTER);
		mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
		return mainPanel;
	}

	
	public JPanel createButtonPanel() {
		buttonPanel = new JPanel(new GridLayout(1,2));
		buttonPanel.add(edit);
		buttonPanel.add(export);
		return buttonPanel;
	}

	public JPanel createPersonResultsTab() throws IOException {
		personResultsPanel = new JPanel(new BorderLayout());
		personResultsTable = createDisplayTable(personColumnNames, personData);
		personScrollPane = new JScrollPane();
		personScrollPane.getViewport().add(personResultsTable);
		personScrollPane.setVisible(true);
		personScrollPane.repaint();
		personResultsPanel.add(personScrollPane, BorderLayout.CENTER);
		return personResultsPanel;
	}

	public JPanel createConnectionResultsTab() throws IOException {
		connectionResultsPanel = new JPanel(new BorderLayout());
		connectionResultsTable = createDisplayTable(connectionColumnNames, connectionData);
		connectionScrollPane = new JScrollPane();
		connectionScrollPane.getViewport().add(connectionResultsTable);
		connectionScrollPane.setVisible(true);
		connectionScrollPane.repaint();
		connectionResultsPanel.add(connectionScrollPane);
		return connectionResultsPanel;
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
		table.setFillsViewportHeight(false);
		return table;
	}
	
	/**
	 * A Popup with either the a person information filled in or a connection
	 * information filled in will pop up. If no row is selected, a message will
	 * pop up informing the user to click a row to edit.
	 * 
	 */
	public void editClicked() {
		if (databases.getSelectedComponent() == personTab) {
			int selectedRow = personResultsTable.getSelectedRow();
			if (selectedRow > -1) {
				personResultsTable.getModel().getValueAt(selectedRow, 0);
				String IDCellText = (String) personResultsTable.getModel().getValueAt(selectedRow, 0);
				int personID = Integer.parseInt(IDCellText);
				AddEditPersonGUI personGUI = new AddEditPersonGUI(home,storage.getPersonFromID(personID));
				personGUI.addDeleteButton();
				personGUI.makeVisible();
			} else {
				JOptionPane.showMessageDialog(frame, "Click a row first!");
			}
		} else { // is connectionTableDisplay
			int selectedRow = connectionResultsTable.getSelectedRow();
			if (selectedRow > -1) {
				String IDCellText = (String) connectionResultsTable.getModel().getValueAt(selectedRow, 0);
				int connectionID = Integer.parseInt(IDCellText);
				AddEditConnectionGUI connectionGUI = new AddEditConnectionGUI(home,
						storage.getConnectionFromID(connectionID));
				connectionGUI.addDeleteButton();
				connectionGUI.makeVisible();
			} else {
				JOptionPane.showMessageDialog(frame, "Click a row first!");
			}
		}

	}
	
	/**
	 * Updates the table to see new data that was added.
	 * 
	 * @throws IOException
	 */
	public void updateTable() throws IOException {
		mainPanel.removeAll();
		frame.remove(mainPanel);
		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == edit) {
			editClicked();

		}
		
		
	}

}
