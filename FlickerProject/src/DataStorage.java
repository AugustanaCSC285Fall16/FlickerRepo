import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class DataStorage {
	//data fields
	public Map<String,Person> personMap;
	private Map<String,Connection> connectionsMap;
	private String[] personHeaderRow;
	private String[] connectionHeaderRow;
	private int nextIdNum;
	private int nextConnNum;
	
	
	//constructor
	public DataStorage() {
		personMap = new TreeMap<>();
		connectionsMap = new TreeMap<>();
	}
	
	public void loadPeople(String fileName) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));

		List<String[]> myRows = reader.readAll();
		personHeaderRow = myRows.remove(0); // remove header row

		for (String[] row : myRows) {
			addPerson(new Person(row));
		}
	}
	
	public void addPerson(Person person) {
		personMap.put(person.getID(), person);
	}
	
	public void savePeople(String fileName) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName));
		writer.writeNext(personHeaderRow);
		for (Person person : personMap.values()) {
		     writer.writeNext(person.toCSVRowArray());		     
		}
		 writer.close();	   
	}
	
	public void loadConnections(String fileName) throws IOException {		
		CSVReader reader = new CSVReader(new FileReader(fileName));		
		List<String[]> myRows = reader.readAll();
		connectionHeaderRow = myRows.remove(0);

		// 1, 0, 1:2, 9/20/16, Letter, Olin, google.com, To
		for (String[] row : myRows) {
			String edgeNum = row[0];
			String baseIdListText = row[1];
			String date = row[2];
			String typeInteraction = row[3];
			String location = row[4];
			String citation = row[5];
			String direction = row[6];
			String[] idArray = baseIdListText.split(":");
			List<Person> peopleConnectingNamesList = new ArrayList<Person>();

			for (String idStr : idArray) {
				int id = Integer.parseInt(idStr);
				Person person = personMap.get(id);
				peopleConnectingNamesList.add(person); 
			}
			
			// make add connection method
			Connection connection = new Connection(date, typeInteraction, location, citation, peopleConnectingNamesList, direction);
			connectionsMap.put(connection.getEdgeId(), connection);
		}
	}	

//	public void saveConnections(String fileName) throws IOException {
//		CSVWriter writer = new CSVWriter(new FileWriter(fileName));
//		writer.writeNext(connectionHeaderRow);
//		for (Connection connection : connectionsMap.values()) {
//		    for(Object person: connection.getPeopleList()) {
//		    	String id = 
//		    }
//		}
//		 writer.close();
//	}
	
	public void loadIdAndConnNum(String fileName) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));
		 String [] nextLine;
	     while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	        nextIdNum = Integer.parseInt(nextLine[0]);
	        nextConnNum = Integer.parseInt(nextLine[1]);
	     }
	}
	
	public void saveIdAndConnNum(String fileName) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName));
		String [] entries = {Integer.toString(nextIdNum), Integer.toString(nextConnNum)};
		writer.writeNext(entries);
		writer.close();
	}
	
	public int getNextIdNum() {
		return nextIdNum;
	}
}
