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
	private ArrayList<String> interactionChoices;
	private ArrayList<String> locationChoices;
	private ArrayList<String> cultureChoices;
	private ArrayList<String> occupationChoices;
	
	private int nextIdNum;
	private int nextConnNum;

	private static final String DATA_FOLDER = "DataFiles";
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
		}
		return primaryDataStorage;
	}

	// constructor
	private DataStorage() throws IOException {
		personMap = new TreeMap<>();
		connectionsMap = new TreeMap<>();
		interactionChoices = new ArrayList<>();
		locationChoices = new ArrayList<>();
		cultureChoices = new ArrayList<>();
		occupationChoices = new ArrayList<>();
		loadPeople();
		loadConnections();
		loadIdAndConnNum();
		loadDataTypes(INTERACTION_CHOICES_FILE_NAME,interactionChoices);
		loadDataTypes(LOCATION_CHOICES_FILE_NAME,locationChoices);
		loadDataTypes(CULTURE_CHOICES_FILE_NAME,cultureChoices);
		loadDataTypes(OCCUPATION_CHOICES_FILE_NAME,occupationChoices);
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
			//System.out.println(dateArray.toString());
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
	    String [] nextLine;
	    while ((nextLine = reader.readNext()) != null) {
	       // nextLine[] is an array of values from the line
	    	String item = nextLine[0];
	       list.add(item);
	    }
	}
	
	// Could we make these next 4 methods into one and have the parameters be the file name 
	// and the ArrayList<String> that is being saved???
	public void saveOccupationControlledVocab() throws IOException{
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + OCCUPATION_CHOICES_FILE_NAME));
		List<String[]> choicesArray = new ArrayList<>();
		for(int i = 0; i < occupationChoices.size(); i++){
			choicesArray.add(this.toCSVControlledVocabArray(occupationChoices.get(i)));
		}
		writer.writeAll(choicesArray);
		writer.close();
	}
	
	public void saveCulturalIDControlledVocab() throws IOException{
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + CULTURE_CHOICES_FILE_NAME));
		List<String[]> choicesArray = new ArrayList<>();
		for(int i = 0; i < cultureChoices.size(); i++){
			choicesArray.add(this.toCSVControlledVocabArray(cultureChoices.get(i)));
		}
		writer.writeAll(choicesArray);
		writer.close();
	}
	
	public void saveLocationControlledVocab() throws IOException{
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + LOCATION_CHOICES_FILE_NAME));
		List<String[]> choicesArray = new ArrayList<>();
		for(int i = 0; i < locationChoices.size(); i++){
			choicesArray.add(this.toCSVControlledVocabArray(locationChoices.get(i)));
		}
		writer.writeAll(choicesArray);
		writer.close();
	}
	
	public void saveInteractionControlledVocab() throws IOException{
		CSVWriter writer = new CSVWriter(new FileWriter(DATA_FOLDER + "/" + INTERACTION_CHOICES_FILE_NAME));
		List<String[]> choicesArray = new ArrayList<>();
		for(int i = 0; i < interactionChoices.size(); i++){
			choicesArray.add(this.toCSVControlledVocabArray(interactionChoices.get(i)));
		}
		writer.writeAll(choicesArray);
		writer.close();
	}
	
	public String[] toCSVControlledVocabArray(String item){
		return new String[] {item};
	}
	
	public void addOccupationChoice(String item){
		occupationChoices.add(item);
	}
	
	public void addCulteralIdChoice(String item){
		cultureChoices.add(item);
	}
	
	public void addLocationChoice(String item){
		locationChoices.add(item);
	}
	
	public void addInteractionChoice(String item){
		interactionChoices.add(item);
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
