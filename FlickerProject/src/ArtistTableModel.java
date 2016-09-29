import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import com.opencsv.CSVReader;

public class ArtistTableModel implements TableModel{
	// data fields
	//private static final int VECTOR_SIZE = 10;
	private Vector<Object> data;
	private Vector<Object> columns;
	private CSVReader reader;
	
	// constructor
	public ArtistTableModel(String file) throws IOException {
		reader = new CSVReader(new FileReader(file));
	    columns = createColumnNamesVector(reader);
	    data = createDataVector(reader);
		
	}
	
	public Vector<Object> getData() {
		return data;
	}
	
	public Vector<Object> getColumns() {
		return columns;
	}
	     
	private Vector<Object> createDataVector(CSVReader reader) throws IOException {
		String [] nextLine = reader.readNext();
		Vector<Object> data = new Vector<Object>(10,10);
	     while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	        Vector<Object> row = new Vector<Object>(nextLine.length,10);
	        for(int i = 0; i < nextLine.length; i++) {
	        	row.add(nextLine[i]);
	        }
	        data.addAll(row);  
	     }
		return data;
	}
	
	public Vector<Object> createColumnNamesVector(CSVReader reader) throws IOException {
	     String [] nextLine = reader.readNext();
	     Vector<Object> columns = new Vector<Object>(nextLine.length,10);
	     if((nextLine) != null) {
	    	 for(int i = 0; i < nextLine.length; i++) {
	    		 columns.add(nextLine[i]);
	    	 }
	     }
	     return columns;
	}

	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getColumnName(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
