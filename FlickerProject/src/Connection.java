import java.util.*;

public class Connection implements TableRowViewable {
	// data fields
	private String date;
	private String typeInteraction;
	private String location;
	private String citation;
	private String socialNotes;
	private List<Person> peopleList;
	private String direction;
	private int edgeId;
	
	// constructor
	public Connection(int edgeID, String date, String typeInteraction, String location, String citation, String socialNotes, List<Person> people, String direction) {
		this.edgeId = edgeID;
		this.date = date;
		this.typeInteraction = typeInteraction;
		this.location = location;
		this.citation = citation;
		this.socialNotes = socialNotes;
		this.peopleList = people;
		this.direction = direction;
	}
	
//	public String[] toCSVRowArray() {
//		//FIXME: needs completed
//		return new String[] { Integer.toString(edgeId), peopleList, date, typeInteraction, location, citation, direction };
//	}
	public String[] toTableRowArray() {
		return new String[] { Integer.toString(edgeId), peopleList.toString(), date, typeInteraction, location, citation, socialNotes, direction};
	}
	
	public List getPeopleList() {
		return peopleList;
	}
	
	public int getEdgeId() {
		return edgeId;
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
