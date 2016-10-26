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
		for (Person person : personList) {
			if (person.getName().equalsIgnoreCase(name)) {
				addPersonResult(person);
				for (Connection connection : connectionList) {
					if (connection.getPeopleList().contains(person)) {
						addConnectionResult(connection);
					}
				}
			}
		}

	}

	public void searchByCulturalID(String culturalID) {
		for (Person person : personList) {
			if (person.getCulturalId().equalsIgnoreCase(culturalID)) {
				addPersonResult(person);
				for (Connection connection : connectionList) {
					if (connection.getPeopleList().contains(person)) {
						addConnectionResult(connection);
					}
				}
			}
		}
	}

	public void searchByGender(String gender) {
		for (Person person : personList) {
			if (person.getGender().equalsIgnoreCase(gender)) {
				addPersonResult(person);
				for (Connection connection : connectionList) {
					if (connection.getPeopleList().contains(person)) {
						addConnectionResult(connection);
					}
				}
			}
		}
	}

	public void searchByOccupation(String occupation) {
		for (Person person : personList) {
			if (person.getOccupation().equalsIgnoreCase(occupation)) {
				addPersonResult(person);
				for (Connection connection : connectionList) {
					if (connection.getPeopleList().contains(person)) {
						addConnectionResult(connection);
					}
				}
			}
		}
	}

	public void searchByDate(String date) {
		for (Connection connection : connectionList) {
			if (connection.getDate().equalsIgnoreCase(date)) {
				addConnectionResult(connection);
			}
		}
	}
	
	public void searchByLocation(String location) {
		for (Connection connection : connectionList) {
			if (connection.getLocation().equalsIgnoreCase(location)) {
				addConnectionResult(connection);
			}
		}
	}
	
	public void searchByInteraction(String interaction) {
		for (Connection connection : connectionList) {
			if (connection.getTypeInteraction().equalsIgnoreCase(interaction)) {
				addConnectionResult(connection);
			}
		}
	}
	
}
