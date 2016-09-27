import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class DataStorage {
	//data fields
	private Map<String,Person> personMap;
	private List<Connection> connections;
	private String[] headerRow;
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
		headerRow = myRows.remove(0); // remove header row

		for (String[] row : myRows) {
			addPerson(new Person(row));
		}
	}
	
	public void addPerson(Person person) {
		personMap.put(person.getID(), person);
	}
	
	public void savePeople(String fileName) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName));
		writer.writeNext(headerRow);
		for (Person person : personMap.values()) {
		     writer.writeNext(person.toCSVRowArray());		     
		}
		 writer.close();	   
	}
	
//	public void loadConnections(String fileName) throws IOException {		
//		CSVReader reader = new CSVReader(new FileReader(fileName));		
//		List<String[]> myRows = reader.readAll();
//
//		// 9/16/2016,Old Main,0:3:7,Pelican=3:Green Heron=1
//		for (String[] row : myRows) {
//			String date = row[0];
//			String location= row[1];
//			String idListText=row[2];
//			String obsListText=row[3];
//			String[] idArray = idListText.split(":");
//			List<Person> peopleConnecting = new ArrayList<Person>();
//
//			for (String idStr : idArray) {
//				int id = Integer.parseInt(idStr);
//				Person watcher = personMap.get(id);
//				peopleConnecting.add(watcher); 
//			}
//			
//			List<Person> birdObsThisTrip = new ArrayList<>(); 
//			String[] obsListArray = obsListText.split(":");
//			for (String obsText : obsListArray) {
//				String[] pair = obsText.split("=");
//				String birdName = pair[0];
//				int birdCount = Integer.parseInt(pair[1]);
//				birdObsThisTrip.add(new BirdObservation(birdName, birdCount));
//			}
//			BirdingTrip trip = new BirdingTrip(location, watchersThisTrip, date, birdObsThisTrip);
//			trips.add(trip);
//			
//		}		
//	}
//
//	public void saveTrips(String fileName) {
//		
//	}
	
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
