import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import com.opencsv.CSVReader;

public class Table {
	// data fields
	//private static final int VECTOR_SIZE = 10;
	private Vector<String> data;
	private Vector<String> columns;
	private String fileName;
	private CSVReader reader;
	
	// constructor
	public Table(String file) throws IOException {
		reader = new CSVReader(new FileReader(file));
	    columns = createColumnNamesVector(reader);
	    data = createDataVector(reader);
		
	}
	
	public Vector<String> getData() {
		return data;
	}
	
	public Vector<String> getColumns() {
		return columns;
	}
	     
	public Vector<String> createDataVector(CSVReader reader) throws IOException {
		String [] nextLine = reader.readNext();
		Vector<String> data = new Vector<String>(10,10);
	     while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	        Vector<String> row = new Vector<String>(nextLine.length,10);
	        for(int i = 0; i < nextLine.length; i++) {
	        	row.add(nextLine[i]);
	        }
	        data.addAll(row);  
	     }
		return data;
	}
	
	public Vector<String> createColumnNamesVector(CSVReader reader) throws IOException {
	     String [] nextLine = reader.readNext();
	     Vector<String> columns = new Vector<String>(nextLine.length,10);
	     if((nextLine) != null) {
	    	 for(int i = 0; i < nextLine.length; i++) {
	    		 columns.add(nextLine[i]);
	    	 }
	     }
	     return columns;
	}
}
