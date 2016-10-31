import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class DataStorage {
	// data fields
	public Map<Integer, User> userMap;
	public Map<Integer, Person> personMap;
	private Map<Integer, Connection> connectionsMap;
	private String[] personHeaderRow = {"Id","Name","Node Name","Occupation","Gender","Cultural ID","Biographical Notes"};
	private String[] connectionHeaderRow = {"Edge","Connecting Name List","Date","Type of Interaction","Location","Citation","Social Notes","Direction"};
	private String[] userHeaderRow;
	private ArrayList<String> interactionChoices;
	private ArrayList<String> locationChoices;
	private ArrayList<String> cultureChoices;
	private ArrayList<String> occupationChoices;
	private boolean isFiltered = false;

	private int nextIdNum;
	private int nextConnNum;
	private int nextUserNum;

	private static final String DATA_FOLDER = "DataFiles";
	private static final String USER_DATA_FILE_NAME = "UserData.csv";
	private static final String PERSON_FILE_NAME = "PersonData.csv";
	private static final String CONNECTION_FILE_NAME = "ConnectionData.csv";
	private static final String NEXT_ID_FILE_NAME = "NodeAndEdgeNumber.csv";
	private static final String INTERACTION_CHOICES_FILE_NAME = "InteractionChoices.csv";
	private static final String LOCATION_CHOICES_FILE_NAME = "LocationChoices.csv";
	private static final String CULTURE_CHOICES_FILE_NAME = "CultureChoices.csv";
	private static final String OCCUPATION_CHOICES_FILE_NAME = "OccupationChoices.csv";

	private static DataStorage primaryDataStorage = null;

	/**
	 * Creates our main Data Storage Object that will be used all over in the
	 * program to call methods.
	 * 
	 * @return Data Storage object that will be our singleton
	 * @throws IOException
	 */
	public static DataStorage getMainDataStorage() throws IOException {
		if (primaryDataStorage == null) {
			primaryDataStorage = new DataStorage();
			primaryDataStorage.loadPeople();
			primaryDataStorage.loadConnections();
			primaryDataStorage.loadUsers();
			primaryDataStorage.loadIdAndConnNum();
		}
		return primaryDataStorage;
	}

	// constructor
	private DataStorage() throws IOException {
		personMap = new TreeMap<>();
		connectionsMap = new TreeMap<>();
		userMap = new TreeMap<>();
		interactionChoices = new ArrayList<>();
		locationChoices = new ArrayList<>();
		cultureChoices = new ArrayList<>();
		occupationChoices = new ArrayList<>();
		loadDataTypes(INTERACTION_CHOICES_FILE_NAME, interactionChoices);
		loadDataTypes(LOCATION_CHOICES_FILE_NAME, locationChoices);
		loadDataTypes(CULTURE_CHOICES_FILE_NAME, cultureChoices);
		loadDataTypes(OCCUPATION_CHOICES_FILE_NAME, occupationChoices);
	}

	private void loadUsers() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(DATA_FOLDER + "/" + USER_DATA_FILE_NAME));

		List<String[]> myRows = reader.readAll();
		userHeaderRow = myRows.remove(0); // remove header row

		for (String[] row : myRows) {
			addUser(new User(row));
		}
	}
	
	public void saveUsers() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + USER_DATA_FILE_NAME));
		writer.writeNext(userHeaderRow);
		for (User user : userMap.values()) {
			writer.writeNext(user.toCSVRowArray());
		}
		writer.close();
	}
	
	public void addUser(User user) {
		userMap.put(user.getId(), user);
	}
	
	public ArrayList<User> getUserArrayList() {
		ArrayList<User> userList = new ArrayList<>(userMap.values());
		return userList;
	}
	
	public Collection<User> getUserList() {
		return userMap.values();
	}
	
	
	/**
	 * 
	 * 
	 * @param query
	 * @return
	 * @throws IOException
	 */
	public User getUserFromFiltered(UserQuery query) throws IOException {
		for(User user: primaryDataStorage.getUserList()) {
			if(query.accepts(user)) {
				return user;
			}
		}
		return null;
	}
	
	public boolean userFilter(UserQuery query) throws IOException {
		for(User user: primaryDataStorage.getUserList()) {
			if(query.accepts(user)) {
				return true;
			}
		}
		return false;
	}
	
	public DataStorage personFilter(PersonQuery query) throws IOException {
		DataStorage filteredData = new DataStorage();
		for(Person person: primaryDataStorage.getPeopleList()) {
			if(query.accepts(person)) {
				filteredData.addPerson(person);
			}
		}
		return filteredData;
	}
	
	public DataStorage connectionFilter(ConnectionQuery query) throws IOException {
		DataStorage filteredData = new DataStorage();
		for(Connection connection: primaryDataStorage.getConnectionList()) {
			if(query.accepts(connection)) {
				for(Person person: connection.getPeopleList()) {
					filteredData.addPerson(person);
				}
				filteredData.addConnection(connection);
			}
		}
		return filteredData;
	}
	
	public void setFiltered(boolean operator) {
		isFiltered = operator;
	}
	
	public boolean isFiltered() {
		return isFiltered;
	}

	public String[] getPersonHeaderRow() {
		return personHeaderRow;
	}

	public Person getPersonFromID(int ID) {
		return personMap.get(ID);
	}

	public Collection<Person> getPeopleList() {
		return personMap.values();
	}

	public ArrayList<Person> getPeopleArrayList() {
		ArrayList<Person> personList = new ArrayList<>(personMap.values());
		return personList;
	}

	public void addPerson(Person person) {
		personMap.put(person.getID(), person);
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	void loadPeople() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(DATA_FOLDER + "/" + PERSON_FILE_NAME));

		List<String[]> myRows = reader.readAll();
		personHeaderRow = myRows.remove(0); // remove header row

		for (String[] row : myRows) {
			addPerson(new Person(row));
		}
	}

	public void savePeople() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + PERSON_FILE_NAME));
		writer.writeNext(personHeaderRow);
		for (Person person : personMap.values()) {
			writer.writeNext(person.toCSVRowArray());
		}
		writer.close();
	}

	void loadConnections() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(DATA_FOLDER + "/" + CONNECTION_FILE_NAME));
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
			String day = dateArray[0];
			String month = dateArray[1];
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

	public void addConnection(Connection connection) {
		connectionsMap.put(connection.getEdgeId(), connection);
	}

	public Connection getConnectionFromID(int ID) {
		return connectionsMap.get(ID);
	}

	public String[] getConnectionHeaderRow() {
		return connectionHeaderRow;
	}

	public Collection<Connection> getConnectionList() {
		return connectionsMap.values();
	}
	
	public ArrayList<Connection> getConnectionArrayList() {
		ArrayList<Connection> connectionList = new ArrayList<>(connectionsMap.values());
		return connectionList;
	}

	public Person getPersonListForConnection(String name) {
		for (Person person : personMap.values()) {
			if (name.equals(person.getName())) {
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

	private void loadDataTypes(String fileName, ArrayList<String> list) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(DATA_FOLDER + "/" + fileName));
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line
			String item = nextLine[0];
			list.add(item);
		}
	}

	// Could we make these next 4 methods into one and have the parameters be
	// the file name
	// and the ArrayList<String> that is being saved???
	public void saveOccupationControlledVocab() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + OCCUPATION_CHOICES_FILE_NAME));
		List<String[]> choicesArray = new ArrayList<>();
		for (int i = 0; i < occupationChoices.size(); i++) {
			choicesArray.add(this.toCSVControlledVocabArray(occupationChoices.get(i).toLowerCase()));
		}
		writer.writeAll(choicesArray);
		writer.close();
	}

	public void saveCulturalIDControlledVocab() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + CULTURE_CHOICES_FILE_NAME));
		List<String[]> choicesArray = new ArrayList<>();
		for (int i = 0; i < cultureChoices.size(); i++) {
			choicesArray.add(this.toCSVControlledVocabArray(cultureChoices.get(i).toLowerCase()));
		}
		writer.writeAll(choicesArray);
		writer.close();
	}

	public void saveLocationControlledVocab() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + LOCATION_CHOICES_FILE_NAME));
		List<String[]> choicesArray = new ArrayList<>();
		for (int i = 0; i < locationChoices.size(); i++) {
			choicesArray.add(this.toCSVControlledVocabArray(locationChoices.get(i).toLowerCase()));
		}
		writer.writeAll(choicesArray);
		writer.close();
	}

	public void saveInteractionControlledVocab() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + INTERACTION_CHOICES_FILE_NAME));
		List<String[]> choicesArray = new ArrayList<>();
		for (int i = 0; i < interactionChoices.size(); i++) {
			choicesArray.add(this.toCSVControlledVocabArray(interactionChoices.get(i).toLowerCase()));
		}
		writer.writeAll(choicesArray);
		writer.close();
	}

	public String[] toCSVControlledVocabArray(String item) {
		return new String[] { item };
	}
	
	public void addOccupationChoice(String item){
		occupationChoices.add(item.toLowerCase());
	}
	
	public void addCulteralIdChoice(String item){
		cultureChoices.add(item.toLowerCase());
	}
	
	public void addLocationChoice(String item){
		locationChoices.add(item.toLowerCase());
	}
	
	public void addInteractionChoice(String item){
		interactionChoices.add(item.toLowerCase());
	}

	public ArrayList<String> getInteractionTypes() {
		return interactionChoices;
	}

	public ArrayList<String> getLocationTypes() {
		return locationChoices;
	}

	public ArrayList<String> getCultureChoices() {
		return cultureChoices;
	}

	public ArrayList<String> getOccupationChoices() {
		return occupationChoices;
	}

	void loadIdAndConnNum() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(DATA_FOLDER + "/" + NEXT_ID_FILE_NAME));
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line
			nextIdNum = Integer.parseInt(nextLine[0]);
			nextConnNum = Integer.parseInt(nextLine[1]);
			nextUserNum = Integer.parseInt(nextLine[2]);
		}
	}

	private void saveIdAndConnNum() throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + NEXT_ID_FILE_NAME));
		String[] entries = { Integer.toString(nextIdNum), Integer.toString(nextConnNum), Integer.toString(nextUserNum) };
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
	
	/**
	 * .... also updates the CSV file that stores these ID numbers
	 * persistently...
	 * 
	 * @return the incremented User Id Number to be used.
	 */
	public int incrementAndGetNextUserIdNum() throws IOException {
		nextUserNum++;
		saveIdAndConnNum();

		return nextUserNum;
	}
}
