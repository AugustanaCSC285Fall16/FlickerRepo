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

public class HomeScreen implements ActionListener {

	private JFrame frame;
	private JButton search;
	private JButton add;
	private JButton edit;
	private JButton artists;
	private JButton connections;
	private JPanel bigPanel;
	private JTable tableDisplay;

	public HomeScreen() throws IOException {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setTitle("Home");
		frame.setLayout(new BorderLayout());

		search = new JButton("Search");
		add = new JButton("Add");
		edit = new JButton("Edit");
		artists = new JButton("Artists");
		connections = new JButton("Connections");
		artists.setEnabled(false);

		JPanel westPanel = new JPanel(new GridLayout(3, 1));
		westPanel.add(search);
		westPanel.add(add);
		westPanel.add(edit);

		JPanel northPanel = new JPanel(new GridLayout(1, 5));
		northPanel.add(artists);
		northPanel.add(connections);

		// creates centerPanel that displays the table of data
		JPanel centerPanel = new JPanel(new BorderLayout()); 
		tableDisplay = displayTable();
		//ArtistTableModel tableModel = new ArtistTableModel("Edges.csv");
		//tableDisplay = new JTable(tableModel.getData(),tableModel.getColumns());
		JScrollPane scrollPane = new JScrollPane(tableDisplay);
		tableDisplay.setFillsViewportHeight(true);
	    centerPanel.add(scrollPane);
		
		bigPanel = new JPanel(new BorderLayout());
		bigPanel.add(northPanel, BorderLayout.NORTH);
		bigPanel.add(centerPanel, BorderLayout.CENTER);

		frame.add(westPanel, BorderLayout.WEST);
		frame.add(bigPanel, BorderLayout.CENTER);

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
	private JTable displayTable() throws IOException {
		CSVReader reader = new CSVReader(new FileReader("Edges.csv"));
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
	    return table;
	}


	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == search) {
			Search search = new Search();
			
			//wait here
			
			JPanel southPanel = new JPanel(new GridLayout(1, 3));
			JButton clear = new JButton("Clear");
			JButton edit = new JButton("Edit");
			JButton export = new JButton("Export");

			southPanel.add(clear);
			southPanel.add(edit);
			southPanel.add(export);

			bigPanel.add(southPanel, BorderLayout.SOUTH);
			bigPanel.revalidate();

		} else if (source == add) {
			Object[] options = { "Add Artist", "Add Connection" };
			int val = JOptionPane.showOptionDialog(frame, "What would you like to add?", "Answer me",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (val == 0) { // if artist
				artists.setPressedIcon(new ImageIcon());
				AddArtist newArtist = new AddArtist();
			} else { // if connection
				connections.setPressedIcon(new ImageIcon());
				AddConnection newConnection = new AddConnection();
			}
		} else if (source == edit) {

			JPanel southPanel = new JPanel(new GridLayout(1, 2));
			JButton edit = new JButton("Edit");
			JButton searchEdit = new JButton("Search for Edit");

			southPanel.add(edit);
			southPanel.add(searchEdit);

			bigPanel.add(southPanel, BorderLayout.SOUTH);
			bigPanel.revalidate();

		} else if(source == connections) {
			connections.setEnabled(false);
			artists.setEnabled(true);
			bigPanel.revalidate();
		} else {
			artists.setEnabled(false);
			connections.setEnabled(true);
			bigPanel.revalidate();
		}
	}
}
