
public class Person {
	// data fields
	private String name;
	private String occupation;
	private String gender;
	private String culturalId;
	private String biographicalNotes;
	
	public Person(String name, String occupation, String gender, String culturalId, String biographicalNotes) {
		super();
		this.name = name;
		this.occupation = occupation;
		this.gender = gender;
		this.culturalId = culturalId;
		this.biographicalNotes = biographicalNotes;
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
