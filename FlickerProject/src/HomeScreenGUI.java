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

	private JFrame frame;
	private JButton search;
	private JButton add;
	private JButton edit;
	private JButton artists;
	private JButton connections;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JTable tableDisplay;

	
	private SearchGUI searchGUI;
	private AddArtistGUI artistGUI;
	private AddConnectionGUI connectionGUI;

	public HomeScreenGUI() throws IOException {
		search = new JButton("Search");
		add = new JButton("Add");
		edit = new JButton("Edit");
		artists = new JButton("Artists");
		connections = new JButton("Connections");
		artists.setEnabled(false);
		tableDisplay = displayTable("DataFiles/PersonData.csv");

		
		searchGUI = new SearchGUI(this);
		artistGUI = new AddArtistGUI(this);
		connectionGUI = new AddConnectionGUI(this);
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
		artists.addActionListener(this);
		connections.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	/*
	 * Creates Table
	 */
	private JTable displayTable(String fileName) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));
	     String [] nextLine = reader.readNext();
	     Vector<String> columnNames = new Vector<String>(nextLine.length,10);
	     if((nextLine) != null) {
	    	 for(int i = 0; i < nextLine.length; i++) {
	    		 columnNames.add(nextLine[i]);
	    	 }
	     }
	     Vector<Object> data = new Vector<Object>(10,10);
	     while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	        Vector<Object> row = new Vector<Object>(nextLine.length,10);
	        for(int i = 0; i < nextLine.length; i++) {
	        	row.add(nextLine[i]);
	        }
	        data.add(row);
	     }
	    JTable table = new JTable(data, columnNames);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(Object.class, centerRenderer);
		table.setEnabled(false);
	    return table;
	}
	
	
	
	
	private JPanel createWestPanel(){
		JPanel westPanel = new JPanel(new GridLayout(3, 1));
		westPanel.add(search);
		westPanel.add(add);
		westPanel.add(edit);
		return westPanel;
	}
	
	private JPanel createNorthPanel(){
		JPanel northPanel = new JPanel(new GridLayout(1, 5));
		northPanel.add(artists);
		northPanel.add(connections);
		return northPanel;
	}

		// creates tablePanel that displays the table of data
	private JPanel createTablePanel() throws IOException{
		JPanel tablePanel = new JPanel(new BorderLayout()); 
		//ArtistTableModel tableModel = new ArtistTableModel("Edges.csv");
		//tableDisplay = new JTable(tableModel.getData(),tableModel.getColumns());
		JScrollPane scrollPane = new JScrollPane(tableDisplay);
		tableDisplay.setFillsViewportHeight(true);
		tablePanel.add(scrollPane);
    	return tablePanel;
	}
	
	private JPanel createCenterPanel() throws IOException{
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(createNorthPanel(), BorderLayout.NORTH);
		centerPanel.add(createTablePanel(), BorderLayout.CENTER);
		return centerPanel;
	}
	

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == search) {
			searchGUI.makeVisible();
		} else if(source == searchGUI.search) {
			southPanel.removeAll();

			southPanel = new JPanel(new GridLayout(1, 3));
			JButton clear = new JButton("Clear");
			JButton edit = new JButton("Edit");
			JButton export = new JButton("Export");

			southPanel.add(clear);
			southPanel.add(edit);
			southPanel.add(export);

			centerPanel.add(southPanel, BorderLayout.SOUTH);
			centerPanel.revalidate();
		} else if (source == add) {
			Object[] options = { "Add Artist", "Add Connection" };
			int val = JOptionPane.showOptionDialog(frame, "What would you like to add?", "Answer me",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (val == 0) { // if artist
				artistGUI.makeVisible();
			} else if (val == 1) { // if connection
				connectionGUI.makeVisible();
			}
		} else if (source == artistGUI.add){
			//Need to figure out how to update table after adding a  new artist.
			tableDisplay.invalidate();
			System.out.println("are we getting here");
		} else if (source == connectionGUI.add){
			System.out.println("successfully waited");
			//waiting for user to click add in the connectionGUI
		} else if (source == edit) {
			
			southPanel.removeAll();

			southPanel = new JPanel(new GridLayout(1, 2));
			JButton edit = new JButton("Edit");
			JButton searchEdit = new JButton("Search for Edit");

			southPanel.add(edit);
			southPanel.add(searchEdit);

			centerPanel.add(southPanel, BorderLayout.SOUTH);
			centerPanel.revalidate();

		} else if(source == connections) {
			connections.setEnabled(false);
			artists.setEnabled(true);
			try {
				tableDisplay = displayTable("DataFiles/ConnectionData.csv");
				centerPanel.add(createTablePanel(), BorderLayout.CENTER);
			} catch (IOException e) {
				e.printStackTrace();
			}
			centerPanel.revalidate();
		} else if(source == artists){
			artists.setEnabled(false);
			connections.setEnabled(true);
			try {
				tableDisplay = displayTable("DataFiles/PersonData.csv");
				centerPanel.add(createTablePanel(), BorderLayout.CENTER);
			} catch (IOException e) {
				e.printStackTrace();
			}
			centerPanel.revalidate();
		}
	}
}
