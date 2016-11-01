import java.io.IOException;

public class Person implements TableRowViewable {
	// data fields
	private int id;
	private String name;
	private String nodeName;
	private String occupation;
	private String gender;
	private String culturalId;
	private String biographicalNotes;

	/**
	 * Creates a person based on the parameters
	 * 
	 * @param Integer
	 *            id
	 * @param String
	 *            name
	 * @param String
	 *            nodeName
	 * @param String
	 *            occupation
	 * @param String
	 *            gender
	 * @param String
	 *            culturalId
	 * @param String
	 *            biographicalNotes
	 * @throws IOException
	 */
	public Person(int id, String name, String nodeName, String occupation, String gender, String culturalId,
			String biographicalNotes) throws IOException {
		this.id = id;
		this.name = name;
		this.nodeName = nodeName;
		this.occupation = occupation;
		this.gender = gender;
		this.culturalId = culturalId;
		this.biographicalNotes = biographicalNotes;
	}

	/**
	 * Creates a person based on the data pulled from the CSV
	 * 
	 * @param String
	 *            [] csvRowData
	 */
	public Person(String[] csvRowData) {
		this.id = Integer.parseInt(csvRowData[0]);
		this.name = csvRowData[1];
		this.nodeName = csvRowData[2];
		this.occupation = csvRowData[3];
		this.gender = csvRowData[4];
		this.culturalId = csvRowData[5];
		this.biographicalNotes = csvRowData[6];
	}

	/**
	 * Creates the string array that will be used in the CSV
	 * 
	 * @return String[] of the person information
	 */
	public String[] toCSVRowArray() {
		return new String[] { Integer.toString(id), name, nodeName, occupation, gender, culturalId, biographicalNotes };
	}

	/**
	 * Creates the string array that will be used in the JTable display
	 * 
	 * @return String[] of the person information
	 */
	public String[] toTableRowArray() {
		return new String[] { Integer.toString(id), name, nodeName, occupation, gender, culturalId, biographicalNotes };
	}

	/**
	 * Creates the string array that will be used in the Gephi exporting
	 * 
	 * @return String[] of the person information
	 */
	public String[] toGephiNodeArray() {
		return new String[] { Integer.toString(this.getID()), this.getNodeName() };
	}

	/**
	 * Retrieves and returns the ID number of the person
	 * 
	 * @return integer ID
	 */
	public int getID() {
		return id;
	}

	/**
	 * Retrieves and returns the name of the person
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the person to the parameter
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves and returns the Node name of the person
	 * 
	 * @return String Node name
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * Sets the node name of the person
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * Retrieves and returns the occupation of the person
	 * 
	 * @return String occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * Sets the occupation of the person
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * Retrieves and returns the gender of the person
	 * 
	 * @return String Gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the person
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Retrieves and returns the Cultural Id of the person
	 * 
	 * @return String CulturalID
	 */
	public String getCulturalId() {
		return culturalId;
	}

	/**
	 * Sets the Cultural Id of the person
	 * 
	 * @return String CulturalID
	 */
	public void setCulturalId(String culturalId) {
		this.culturalId = culturalId;
	}

	/**
	 * Retrieves and returns the BioNotes of the person
	 * 
	 * @return String BioNotes
	 */
	public String getBiographicalNotes() {
		return biographicalNotes;
	}

	/**
	 * Sets the BioNotes of the person
	 * 
	 * @return String BioNotes
	 */
	public void setBiographicalNotes(String biographicalNotes) {
		this.biographicalNotes = biographicalNotes;
	}

	/**
	 * Makes the object to a string.
	 * 
	 * @return String name
	 */
	@Override
	public String toString() {
		return name;
	}
}
