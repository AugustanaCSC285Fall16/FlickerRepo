import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class DataStorage {
	public Map<Integer, Person> personMap;
	private Map<Integer, Connection> connectionsMap;

	private String[] personHeaderRow;
	private String[] connectionHeaderRow;

	private int nextIdNum;
	private int nextConnNum;
	private int nextUserNum;

	public static final String DATA_FOLDER = "DataFiles";
	private static final String PERSON_FILE_NAME = "PersonData.csv";
	private static final String CONNECTION_FILE_NAME = "ConnectionData.csv";
	private static final String NEXT_ID_FILE_NAME = "NodeAndEdgeNumber.csv";

	private boolean isFiltered = false;
	private static DataStorage primaryDataStorage = null;

	/**
	 * Creates our main Data Storage Object that will be used all over in the
	 * program to call methods related to the data of connections and persons
	 * 
	 * @return Data Storage object that will be a singleton
	 * @throws IOException
	 */
	public static DataStorage getMainDataStorage() throws IOException {
		if (primaryDataStorage == null) {
			primaryDataStorage = new DataStorage();
			primaryDataStorage.loadPeople();
			primaryDataStorage.loadConnections();
			primaryDataStorage.loadIdConnUserNum();
		}
		return primaryDataStorage;
	}

	/**
	 * The Private constructor only Initializes the data fields but doesn't fill
	 * them in This allows for the singleton pattern
	 * 
	 * @throws IOException
	 */
	private DataStorage() throws IOException {
		personMap = new TreeMap<>();
		connectionsMap = new TreeMap<>();
	}

	
	/**
	 * Checks to see if the Person is in the data
	 * 
	 * @param query
	 * @return DataStorage - a new data storage object with just the filtered data
	 * @throws IOException
	 */
	public DataStorage personFilter(PersonQuery query) throws IOException {
		DataStorage filteredData = new DataStorage();
		for (Person person : primaryDataStorage.getPeopleList()) {
			if (query.accepts(person)) {
				filteredData.addPerson(person);
			}
		}
		return filteredData;
	}

	/**
	 * Checks to see if the connection is in the data
	 * 
	 * @param query
	 * @return DataStorage - new data storage object with the filtered data
	 * @throws IOException
	 */
	public DataStorage connectionFilter(ConnectionQuery query) throws IOException {
		DataStorage filteredData = new DataStorage();
		for (Connection connection : primaryDataStorage.getConnectionList()) {
			if (query.accepts(connection)) {
				for (Person person : connection.getPeopleList()) {
					filteredData.addPerson(person);
				}
				filteredData.addConnection(connection);
			}
		}
		return filteredData;
	}

	/**
	 * Sets the isFiltered operator to the parameter
	 * 
	 * @param operator
	 */
	public void setFiltered(boolean operator) {
		isFiltered = operator;
	}

	/**
	 * Retrieves and returns the isFiltered boolean
	 * 
	 * @return boolean isFiltered
	 */
	public boolean isFiltered() {
		return isFiltered;
	}

	/**
	 * Retrieves and returns the PersonHeaderRow
	 * 
	 * @return String []
	 */
	public String[] getPersonHeaderRow() {
		return personHeaderRow;
	}

	/**
	 * Retrieves and returns the Person that matches the parameterId
	 * 
	 * @param ID
	 * @return Person
	 */
	public Person getPersonFromID(int ID) {
		return personMap.get(ID);
	}

	/**
	 * Converts the PersonMap to a collection of type Person
	 * @return Collection<Person>
	 */
	public Collection<Person> getPeopleList() {
		return personMap.values();
	}

	/**
	 * Converts the personMap to an arrayList of type Person
	 * @return ArrayList<Person>
	 */
	public ArrayList<Person> getPeopleArrayList() {
		ArrayList<Person> personList = new ArrayList<>(personMap.values());
		return personList;
	}

	/**
	 * Adds a person to the personMap
	 * @param person
	 */
	public void addPerson(Person person) {
		personMap.put(person.getID(), person);
	}

	/**
	 * Loads all of the people from a csv and stores them in the personMap
	 * @throws IOException
	 */
	void loadPeople() throws IOException {
		CSVReader reader = new CSVReader(
				new InputStreamReader(new FileInputStream((DATA_FOLDER + "/" + PERSON_FILE_NAME)), "UTF-8"));

		List<String[]> myRows = reader.readAll();
		personHeaderRow = myRows.remove(0); // remove header row

		for (String[] row : myRows) {
			addPerson(new Person(row));
		}
	}

	/**
	 * Writes the personMap to the csv
	 * @throws IOException
	 */
	public void savePeople() throws IOException {
		CSVWriter writer = new CSVWriter(
				new OutputStreamWriter(new FileOutputStream((DATA_FOLDER + "/" + PERSON_FILE_NAME)), "UTF-8"));
		writer.writeNext(personHeaderRow);
		for (Person person : personMap.values()) {
			writer.writeNext(person.toCSVRowArray());
		}
		writer.close();
	}

	/**
	 * Loads the connections from the csv and stores them in ConnectionMap
	 * @throws IOException
	 */
	void loadConnections() throws IOException {
		CSVReader reader = new CSVReader(
				new InputStreamReader(new FileInputStream((DATA_FOLDER + "/" + CONNECTION_FILE_NAME)), "UTF-8"));
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

	/**
	 * Adds a connection to the connectionMap
	 * @param connection
	 */
	public void addConnection(Connection connection) {
		connectionsMap.put(connection.getEdgeId(), connection);
	}

	/**
	 * Retrieves and Returns the Connection from the ID parameter
	 * @param ID
	 * @return Connection 
	 */
	public Connection getConnectionFromID(int ID) {
		return connectionsMap.get(ID);
	}

	/**
	 * Retrieves and returns the HeaderRow for the connection csv
	 * 
	 * @return String[] of header
	 */
	public String[] getConnectionHeaderRow() {
		return connectionHeaderRow;
	}

	/**
	 * Converts the connectionsMap to a Collection of type connection
	 * @return Collection<Connection>
	 */
	public Collection<Connection> getConnectionList() {
		return connectionsMap.values();
	}

	/**
	 * Converts the connectionMap to an arrayList of type Connection
	 * @return Collection<Connetion>
	 */
	public ArrayList<Connection> getConnectionArrayList() {
		ArrayList<Connection> connectionList = new ArrayList<>(connectionsMap.values());
		return connectionList;
	}

	/**
	 * Returns a person based on if the parameter name is in the prersonMap. 
	 * @param name
	 * @return Person 
	 */
	public Person getPersonListForConnection(String name) {
		for (Person person : personMap.values()) {
			if (name.equals(person.getName())) {
				int id = person.getID();
				return personMap.get(id);
			}
		}
		return null;
	}

	/**
	 * Writes the connectionMap to the csv
	 * @throws IOException
	 */
	public void saveConnections() throws IOException {
		CSVWriter writer = new CSVWriter(
				new OutputStreamWriter(new FileOutputStream((DATA_FOLDER + "/" + CONNECTION_FILE_NAME)), "UTF-8"));
		writer.writeNext(connectionHeaderRow);
		for (Connection connection : connectionsMap.values()) {
			writer.writeNext(connection.toCSVRowArray());
		}
		writer.close();
	}

	/**
	 * Reads in the ID, connection number, and user number
	 * @throws IOException
	 */
	void loadIdConnUserNum() throws IOException {
		CSVReader reader = new CSVReader(
				new InputStreamReader(new FileInputStream((DATA_FOLDER + "/" + NEXT_ID_FILE_NAME)), "UTF-8"));
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line
			nextIdNum = Integer.parseInt(nextLine[0]);
			nextConnNum = Integer.parseInt(nextLine[1]);
			nextUserNum = Integer.parseInt(nextLine[2]);
		}
	}

	/**
	 * Writes the ID, connection number, and user number to the csv. 
	 * @throws IOException
	 */
	private void saveIdConnUserNum() throws IOException {
		CSVWriter writer = new CSVWriter(
				new OutputStreamWriter(new FileOutputStream((DATA_FOLDER + "/" + NEXT_ID_FILE_NAME)), "UTF-8"));
		String[] entries = { Integer.toString(nextIdNum), Integer.toString(nextConnNum),
				Integer.toString(nextUserNum) };
		writer.writeNext(entries);
		writer.close();
	}

	/**
	 * .... also updates the CSV file that stores these ID numbers
	 * persistently...
	 * 
	 * @return the incremented Person Id number to be used
	 */
	public int incrementAndGetNextPersonIdNum() throws IOException {
		nextIdNum++;
		saveIdConnUserNum();

		return nextIdNum;
	}

	/**
	 * .... also updates the CSV file that stores these ID numbers
	 * persistently...
	 * 
	 * @return the incremented Connection Id Number to be used.
	 */
	public int incrementAndGetNextConnectionIdNum() throws IOException {
		nextConnNum++;
		saveIdConnUserNum();

		return nextConnNum;
	}

	/**
	 * .... also updates the CSV file that stores these ID numbers
	 * persistently...
	 * 
	 * @return the incremented User Id Number to be used.
	 */
	public int incrementAndGetNextUserIdNum() throws IOException {
		nextUserNum++;
		saveIdConnUserNum();

		return nextUserNum;
	}
}
