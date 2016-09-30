import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class DataStorage {
	// data fields
	public Map<Integer, Person> personMap;
	private Map<Integer, Connection> connectionsMap;
	private String[] personHeaderRow;

	private String[] connectionHeaderRow;

	private int nextIdNum;
	private int nextConnNum;

	private static final String DATA_FOLDER = "DataFiles";
	private static final String PERSON_FILE_NAME = "PersonData.csv";
	private static final String CONNECTION_FILE_NAME = "ConnectionData.csv";
	private static final String NEXT_ID_FILE_NAME = "NodeAndEdgeNumber.csv";

	private static DataStorage primaryDataStorage = null;

	/**
	 * Creates our main Data Storage Object that will be used all over in the
	 * program to call methods.
	 * 
	 * @return Data Storage object that will be our simpleton
	 * @throws IOException
	 */
	public static DataStorage getMainDataStorage() throws IOException {
		if (primaryDataStorage == null) {
			primaryDataStorage = new DataStorage();
		}
		return primaryDataStorage;
	}

	// constructor
	private DataStorage() throws IOException {
		personMap = new TreeMap<>();
		connectionsMap = new TreeMap<>();
		loadPeople();
		loadConnections();
		loadIdAndConnNum();
	}

	/**
	 * 
	 * @throws IOException
	 */
	private void loadPeople() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(DATA_FOLDER + "/" + PERSON_FILE_NAME));

		List<String[]> myRows = reader.readAll();
		personHeaderRow = myRows.remove(0); // remove header row

		for (String[] row : myRows) {
			addPerson(new Person(row));
		}
	}

	public String[] getPersonHeaderRow() {
		return personHeaderRow;
	}

	public Person getPersonFromID(int ID){
		return personMap.get(ID);
	}
	
	public Collection<Person> getPeopleList() {
		return personMap.values();
	}

	public void addPerson(Person person) {
		personMap.put(person.getID(), person);
	}

	public void savePeople() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + PERSON_FILE_NAME));
		writer.writeNext(personHeaderRow);
		for (Person person : personMap.values()) {
			writer.writeNext(person.toCSVRowArray());
		}
		writer.close();
	}

	private void loadConnections() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(DATA_FOLDER + "/" + CONNECTION_FILE_NAME));
		List<String[]> myRows = reader.readAll();
		connectionHeaderRow = myRows.remove(0);

		// 1, 0, 1:2, 9/20/16, Letter, Olin, google.com, To
		for (String[] row : myRows) {
			int edgeID = Integer.parseInt(row[0]);
			String baseIdListText = row[1];
			String date = row[2];
			String typeInteraction = row[3];
			String location = row[4];
			String citation = row[5];
			String socialNotes = row[6];
			String direction = row[7];

			String[] idArray = baseIdListText.split(":");
			List<Person> peopleConnectingNamesList = new ArrayList<Person>();

			for (String idStr : idArray) {
				int id = Integer.parseInt(idStr);
				Person person = personMap.get(id);
				peopleConnectingNamesList.add(person);
			}

			addConnection(new Connection(edgeID, date, typeInteraction, location, citation, socialNotes,
					peopleConnectingNamesList, direction));

		}

	}

	public void addConnection(Connection connection) {
		connectionsMap.put(connection.getEdgeId(), connection);
	}

	public String[] getConnectionHeaderRow() {
		return connectionHeaderRow;
	}

	public Collection<Connection> getConnectionList() {
		return connectionsMap.values();
	}
	
	public Person getPersonListForConnection(String name){
		for (Person person: personMap.values()){
			if (name.equals(person.getName())){
				int id = person.getID();
				return personMap.get(id);
			}
		}
		return null;
	}

	public void saveConnections() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + CONNECTION_FILE_NAME));
		writer.writeNext(connectionHeaderRow);
		for (Connection connection : connectionsMap.values()) {
			writer.writeNext(connection.toCSVRowArray());
		}
		writer.close();
	}

	private void loadIdAndConnNum() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(DATA_FOLDER + "/" + NEXT_ID_FILE_NAME));
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line
			nextIdNum = Integer.parseInt(nextLine[0]);
			nextConnNum = Integer.parseInt(nextLine[1]);
		}
	}

	private void saveIdAndConnNum() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + NEXT_ID_FILE_NAME));
		String[] entries = { Integer.toString(nextIdNum), Integer.toString(nextConnNum) };
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
		saveIdAndConnNum();

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
		saveIdAndConnNum();

		return nextConnNum;
	}
}
