import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;

import com.opencsv.CSVReader;

public class Main {

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame();
		frame.setSize(1000, 1000);
		
		JTable table = createTable();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(Object.class, centerRenderer);
		
		JScrollPane scrollPane = new JScrollPane(table);
	    table.setFillsViewportHeight(true);
	    frame.add(scrollPane); 
		
//		JPanel panel = new JPanel();
//		panel.setLayout(new BorderLayout());
//		panel.add(table.getTableHeader(), BorderLayout.PAGE_START);
//		panel.add(table, BorderLayout.CENTER);
//		frame.add(panel);
		
		frame.setVisible(true);
	     
	}
	
	public static JTable createTable() throws IOException {
		CSVReader reader = new CSVReader(new FileReader("Edges.csv"));
	     String [] nextLine = reader.readNext();
	     Vector columnNames = new Vector(nextLine.length,10);
	     if((nextLine) != null) {
	    	 for(int i = 0; i < nextLine.length; i++) {
	    		 columnNames.add(nextLine[i]);
	    	 }
	    	// System.out.println(columnNames);
	     }
	     Vector data = new Vector(10,10);
	     while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	        Vector row = new Vector(nextLine.length,10);
	        for(int i = 0; i < nextLine.length; i++) {
	        	row.add(nextLine[i]);
	        }
	        data.add(row);
	     }
		//System.out.println(data);
	     
	     JTable table = new JTable(data, columnNames);
	     return table;
	}

}
