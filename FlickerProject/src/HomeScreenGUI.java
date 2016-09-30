
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
	private JTabbedPane personsTab;
	//private JTabbedPane connectionsTab;
	private JButton export;
	private JPanel centerPanel;
	private JPanel southPanel;
	//private JTable tableDisplay;

	private SearchGUI searchGUI;
	private AddPersonGUI artistGUI;
	private AddConnectionGUI connectionGUI;
	private ExportGUI exportGUI;

	public HomeScreenGUI() throws IOException {
		search = new JButton("Search");
		add = new JButton("Add");
		edit = new JButton("Edit");
		personsTab = new JTabbedPane();
		//connectionsTab = new JTabbedPane();
		export = new JButton("Export");
		//tableDisplay = displayTable(mainStorage.getPersonHeaderRow(), mainStorage.getPeopleList());

		searchGUI = new SearchGUI(this);
		artistGUI = new AddPersonGUI(this);
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
//		personsTab.addActionListener(this);
//		connectionsTab.addActionListener(this);
		export.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	/**
	 * This method is used to create the table.
	 * It creates the table.
	 * @return JPanel This returns the completed table.
	 * @throws IOException
	 */

	private JTable displayTable(String[] columnNamesArray, Collection<? extends TableRowViewable> rowItems)
			throws IOException {

		Vector<String> columnNames = new Vector<>(Arrays.asList(columnNamesArray));

		Vector<Vector<String>> tableData = new Vector<>();

		
		for (TableRowViewable item : rowItems) {
			String[] nextRow = item.toTableRowArray();
			tableData.addElement(new Vector(Arrays.asList(nextRow)));
		}


		JTable table = new JTable(); //tableData, columnNames);
		DefaultTableModel nonEditableTableModel = new DefaultTableModel() {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }	    
		};
		nonEditableTableModel.setDataVector(tableData, columnNames);
		table.setModel(nonEditableTableModel);
		
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, centerRenderer);
//		table.setEnabled(false);
		return table;
	}

	/**
	 * This method is used to create the west panel.
	 * It adds the appropriate labels to the panel.
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
	 * This method is used to create the north panel.
	 * It adds the appropriate buttons to the panel.
	 * @return JPanel This returns the completed north panel.
	 */


//	private JPanel createNorthPanel() throws IOException {
//		JPanel northPanel = new JPanel(new GridLayout(1, 5));
//		personsTab.add("Persons Data", displayTable(mainStorage.getPersonHeaderRow(), mainStorage.getPeopleList()));
//		personsTab.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
//		personsTab.add("Connections Data", displayTable(mainStorage.getConnectionHeaderRow(), mainStorage.getConnectionList()));
//		northPanel.add(personsTab);
//		//northPanel.add(connectionsTab);
//		return northPanel;
//	}

	// creates tablePanel that displays the table of data
//	private JPanel createTablePanel() throws IOException {
//		JPanel tablePanel = new JPanel(new BorderLayout());
//		JScrollPane scrollPane = new JScrollPane(tableDisplay);
//		tableDisplay.setFillsViewportHeight(true);
//		tablePanel.add(scrollPane);
//		return tablePanel;
//	}

	private JPanel createCenterPanel() throws IOException {
		centerPanel = new JPanel(new BorderLayout());
		personsTab.add("Persons Data", displayTable(mainStorage.getPersonHeaderRow(), mainStorage.getPeopleList()));
		personsTab.add("Connections Data", displayTable(mainStorage.getConnectionHeaderRow(), mainStorage.getConnectionList()));
		centerPanel.add(personsTab);
		//centerPanel.add(connectionsTab);
		//centerPanel = new JPanel(new BorderLayout());
		//centerPanel.add(createNorthPanel(), BorderLayout.NORTH);
		//centerPanel.add(createTablePanel(), BorderLayout.CENTER);
		return centerPanel;
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == search) {
			//tableDisplay.setEnabled(false);
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
			southPanel.removeAll();
			centerPanel.add(southPanel, BorderLayout.SOUTH);
			centerPanel.revalidate();
			
			Object[] options = { "Add Artist", "Add Connection" };
			int val = JOptionPane.showOptionDialog(frame, "What would you like to add?", "Answer me",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (val == 0) { // if artist
				artistGUI.makeVisible();
				artistGUI.setDefault();
			} else if (val == 1) { // if connection
				connectionGUI.setDefault();
			}
		} else if (source == artistGUI.add) {
			// Need to figure out how to update table after adding a new artist.
			//tableDisplay.invalidate();
			System.out.println("are we getting here");
		} else if (source == connectionGUI.add) {
			System.out.println("successfully waited");
			// waiting for user to click add in the connectionGUI
		} else if (source == edit) {
			southPanel.removeAll();

			southPanel = new JPanel(new GridLayout(1, 2));
			JButton save = new JButton("Save Changes");

			southPanel.add(save);

			centerPanel.add(southPanel, BorderLayout.SOUTH);
			centerPanel.revalidate();

//		} else if (source == connections) {
//			connections.setEnabled(false);
//			artists.setEnabled(true);
//			try {
//				DataStorage mainStorage = DataStorage.getMainDataStorage();
//				tableDisplay = displayTable(mainStorage.getConnectionHeaderRow(), mainStorage.getConnectionList());
//								
//				centerPanel.add(createTablePanel(), BorderLayout.CENTER);
//				//centerPanel.invalidate();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			centerPanel.revalidate();
//		} else if (source == artists) {
//			artists.setEnabled(false);
//			connections.setEnabled(true);
//			try {
//				DataStorage mainStorage = DataStorage.getMainDataStorage();
//				tableDisplay.setEnabled(false);
//				tableDisplay = displayTable(mainStorage.getPersonHeaderRow(), mainStorage.getPeopleList());
//				centerPanel.add(createTablePanel(), BorderLayout.CENTER);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			centerPanel.revalidate();
//		} else if (source == export) {
//			exportGUI.makeVisible();
		}
	}
}
