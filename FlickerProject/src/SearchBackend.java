import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

public class SearchBackend {
	// data fields
	private Map<Integer, Person> personSearchResultsMap;
	private Map<Integer, Connection> connectionSearchResultsMap;
	private DataStorage storage;
	private ArrayList<Person> personList;
	private ArrayList<Connection> connectionList;
	
	// constructor
	public SearchBackend() throws IOException {
		personSearchResultsMap = new TreeMap<>();
		connectionSearchResultsMap = new TreeMap<>();
		storage = DataStorage.getMainDataStorage();
		personList = storage.getPeopleArrayList();
		connectionList = storage.getConnectionArrayList();
	}
	
	public Collection<Person> getPersonCollection() {
		return personSearchResultsMap.values();
	}
	
	public Collection<Connection> getConnectionCollection() {
		return connectionSearchResultsMap.values();
	}
	
	public void addPersonResult(Person person) {
		personSearchResultsMap.put(person.getID(), person);
	}
	
	public void addConnectionResult(Connection connection) {
		connectionSearchResultsMap.put(connection.getEdgeId(), connection);
	}
	
	public void searchByName(String name) {
		for(Person person: personList) {
			if(person.getName().equalsIgnoreCase(name)) {
				addPersonResult(person);
				for(Connection connection: connectionList) {
					if(connection.getPeopleList().contains(person)) {
						addConnectionResult(connection);
					}
				}
			} 
		}
		
	}
	
}
