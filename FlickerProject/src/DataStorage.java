import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class DataStorage {
	//data fields
	private Map<String,Person> personMap;
	private List<Connection> connections;
	private String[] personHeaderRow;
	private String[] connectionHeaderRow;
	private int nextIdNum;
	private int nextConnNum;
	
	
	//constructor
	public DataStorage() {
		personMap = new TreeMap<>();
		connections = new ArrayList<>();
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
			String targetIdListText = row[2];
			String date = row[3];
			String typeInteraction = row[4];
			String location = row[5];
			String citation = row[6];
			String direction = row[7];
			String[] idArray = baseIdListText.split(":");
			List<Person> peopleConnecting = new ArrayList<Person>();

			for (String idStr : idArray) {
				int id = Integer.parseInt(idStr);
				Person person = personMap.get(id);
				peopleConnecting.add(person); 
			}
			
//			List<Person> targetPeople = new ArrayList<>(); 
//			String[] targetIdArray = targetIdListText.split(":");
//			for (String idText : targetIdArray) {
//				int id = Integer.parseInt(idText);
//				Person person = personMap.get(id);
//				targetPeople.add(person); 
//			}
			Connection newConnection = new Connection(date, typeInteraction, location, citation, peopleConnecting, direction);
			connections.add(newConnection);
			
		}		
	}

	public void saveConnections(String fileName) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName));
		writer.writeNext(connectionHeaderRow);
		for (Person person : personMap.values()) {
		     writer.writeNext(person.toCSVRowArray());		     
		}
		 writer.close();
	}
	
	public void loadIdAndConnNum(String fileName) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));
		 String [] nextLine;
	     while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	        nextIdNum = Integer.parseInt(nextLine[0]) + 1;
	        nextConnNum = Integer.parseInt(nextLine[1]) + 1;
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
