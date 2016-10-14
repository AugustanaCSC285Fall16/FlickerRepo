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

	// default constructor
	public Person(int id, String name, String nodeName, String occupation, String gender, String culturalId, String biographicalNotes)
			throws IOException {
		this.id = id;
		this.name = name;
		this.nodeName = nodeName;
		this.occupation = occupation;
		this.gender = gender;
		this.culturalId = culturalId;
		this.biographicalNotes = biographicalNotes;
	}

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

	public String[] toTableRowArray() {
		return new String[] { Integer.toString(id), name, nodeName, occupation, gender, culturalId, biographicalNotes };
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCulturalId() {
		return culturalId;
	}

	public void setCulturalId(String culturalId) {
		this.culturalId = culturalId;
	}

	public String getBiographicalNotes() {
		return biographicalNotes;
	}

	public void setBiographicalNotes(String biographicalNotes) {
		this.biographicalNotes = biographicalNotes;
	}

	@Override
	public String toString() {
		return name;
	}

}
