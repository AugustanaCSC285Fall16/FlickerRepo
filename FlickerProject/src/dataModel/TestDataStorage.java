package dataModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.opencsv.CSVReader;

public class TestDataStorage {
	public Map<Integer, Person> personMap;
	private Map<Integer, Connection> connectionsMap;
	private String[] personHeaderRow = { "Id", "Name", "Node Name", "Occupation", "Gender", "Cultural ID",
			"Biographical Notes" };
	private String[] connectionHeaderRow = { "Edge", "Connecting Name List", "Date", "Type of Interaction", "Location",
			"Citation", "Social Notes", "Direction" };
	
	private static final String DATA_FOLDER = "DataFiles";
	private static final String TEST_PERSON_FILE_NAME = "TestPersonData.csv";
	private static final String TEST_CONNECTION_FILE_NAME = "TestConnectionData.csv";
	
	private static TestDataStorage testDataStorage = null;
	
	
	public static TestDataStorage getTestDataStorage() {
		if(testDataStorage == null) {
			try {
				testDataStorage.loadPeople();
				testDataStorage.loadConnections();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return testDataStorage;
	}
	
	 // constructor
	private TestDataStorage() throws IOException {
		personMap = new TreeMap<>();
		connectionsMap = new TreeMap<>();
	}
	
	
	public TestDataStorage personFilter(PersonQuery query) throws IOException {
		TestDataStorage filteredData = new TestDataStorage();
		for (Person person : testDataStorage.getPeopleList()) {
			if (query.accepts(person)) {
				filteredData.addPerson(person);
			}
		}
		return filteredData;
	}

	public TestDataStorage connectionFilter(ConnectionQuery query) throws IOException {
		TestDataStorage filteredData = new TestDataStorage();
		for (Connection connection : testDataStorage.getConnectionList()) {
			if (query.accepts(connection)) {
				for (Person person : connection.getPeopleList()) {
					filteredData.addPerson(person);
				}
				filteredData.addConnection(connection);
			}
		}
		return filteredData;
	}
	
	public void addPerson(Person person) {
		personMap.put(person.getID(), person);
	}
	
	public void addConnection(Connection connection) {
		connectionsMap.put(connection.getEdgeId(), connection);
	}
	
	void loadConnections() throws IOException {
		CSVReader reader = new CSVReader(
				new InputStreamReader(new FileInputStream((DATA_FOLDER + "/" + TEST_CONNECTION_FILE_NAME)), "UTF-8"));
		List<String[]> myRows = reader.readAll();
		connectionHeaderRow = myRows.remove(0);

		for (String[] row : myRows) {
			int edgeID = Integer.parseInt(row[0]);
			String baseIdListText = row[1];
			String dateText = row[2];
			String typeInteraction = row[3];
			String location = row[4];
			String citation = row[5];
			String socialNotes = row[6];
			String direction = row[7];

			String[] idArray = baseIdListText.split(":");
			List<Person> peopleConnectingNamesList = new ArrayList<Person>();

			String[] dateArray = dateText.split(":");
			// System.out.println(dateArray.toString());
			String month = dateArray[0];
			String day = dateArray[1];
			String year = dateArray[2];

			for (String idStr : idArray) {
				int id = Integer.parseInt(idStr);
				Person person = personMap.get(id);
				peopleConnectingNamesList.add(person);
			}

			addConnection(new Connection(edgeID, day, month, year, typeInteraction, location, citation, socialNotes,
					peopleConnectingNamesList, direction));

		}

	}
	
	void loadPeople() throws IOException {
		CSVReader reader = new CSVReader(
				new InputStreamReader(new FileInputStream((DATA_FOLDER + "/" + TEST_PERSON_FILE_NAME)), "UTF-8"));

		List<String[]> myRows = reader.readAll();
		personHeaderRow = myRows.remove(0); // remove header row

		for (String[] row : myRows) {
			addPerson(new Person(row));
		}
	}
	
	public ArrayList<Person> getPeopleArrayList() {
		ArrayList<Person> personList = new ArrayList<>(personMap.values());
		return personList;
	}
	
	public Collection<Person> getPeopleList() {
		return personMap.values();
	}
	
	public Collection<Connection> getConnectionList() {
		return connectionsMap.values();
	}
	
}
