import java.util.*;

public class Connection {
	// data fields
	private String date;
	private String typeInteraction;
	private String location;
	private String citation;
	private List<Person> peopleList;
	private String direction;
	
	// constructor
	public Connection(String date, String typeInteraction, String location, String citation, List<Person> people, String direction) {
		this.date = date;
		this.typeInteraction = typeInteraction;
		this.location = location;
		this.citation = citation;
		this.peopleList = people;
		this.direction = direction;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTypeInteraction() {
		return typeInteraction;
	}

	public void setTypeInteraction(String typeInteraction) {
		this.typeInteraction = typeInteraction;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCitation() {
		return citation;
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
	
}
