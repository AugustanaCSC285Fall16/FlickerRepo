import java.io.IOException;

public class Person {
	// data fields
	private String id;
	private String name;
	private String occupation;
	private String gender;
	private String culturalId;
	private String biographicalNotes;

	public Person(String name, String occupation, String gender, String culturalId, String biographicalNotes) throws IOException {
		DataStorage storage = new DataStorage();
		storage.loadIdAndConnNum("DataFiles/NodeAndEdgeNumber.csv");
		this.id = Integer.toString(storage.getNextIdNum() + 1);
		this.name = name;
		this.occupation = occupation;
		this.gender = gender;
		this.culturalId = culturalId;
		this.biographicalNotes = biographicalNotes;
		storage.saveIdAndConnNum("DataFiles/NodeAndEdgeNumber.csv");
	}
	
	public Person(String[] csvRowData) {
		this.id = csvRowData[0];
		this.name = csvRowData[1];
		this.occupation = csvRowData[2];
		this.gender = csvRowData[3];
		this.culturalId = csvRowData[4];
		this.biographicalNotes = csvRowData[5];
	}

	public String[] toCSVRowArray() {
		return new String[] { id, name, occupation, gender, culturalId, biographicalNotes };
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
