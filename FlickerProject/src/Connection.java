import java.io.IOException;
import java.util.*;

public class Connection implements TableRowViewable {
	// data fields
	//private String date;
	private String day;
	private String month;
	private String year;
	private String typeInteraction;
	private String location;
	private String citation;
	private String socialNotes;
	private List<Person> peopleList;
	private String direction;
	private int edgeId;

	// default constructor
	public Connection(int edgeID, String day, String month, String year, String typeInteraction, String location, String citation,
			String socialNotes, List<Person> people, String direction) {
		this.edgeId = edgeID;
		this.day = day;
		this.month = month;
		this.year = year;
		this.typeInteraction = typeInteraction;
		this.location = location;
		this.citation = citation;
		this.socialNotes = socialNotes;
		this.peopleList = people;
		this.direction = direction;
	}

	/**
	 * Creates the string array that will be used in the CSV
	 * 
	 * @return String[] of the connection information
	 */
	public String[] toCSVRowArray() {
		String idListText = peopleListToIdText();
		String dateText = dateToCSVText();
		return new String[] { Integer.toString(edgeId), idListText, dateText, typeInteraction, location, citation,
				socialNotes, direction };
	}
	
	public String dateToCSVText() {
		String result = day + ":" + month + ":" +  year;
		return result;
	}

	/**
	 * Retrieves the ID for each person in a connection and returns it.
	 * 
	 * @return String
	 */
	public String peopleListToIdText() {
		String result = "";
		for (Person person : peopleList) {
			result += person.getID() + ":";
			// FIXME: Make it so there is not an extra colon at the end.
		}
		return result;
	}

	/**
	 * Creates an array of type string formatted to fill a row in the connection
	 * table and returns the array.
	 * 
	 * @return String array of table row connection
	 */
	public String[] toTableRowArray() {
		String date = day + "/" + month + "/" + year;
		return new String[] { Integer.toString(edgeId), peopleList.toString(), date, typeInteraction, location,
				citation, socialNotes, direction };
	}
	

	/**
	 * Retrieves and returns the list of people involved in the connection
	 * 
	 * @return list of type Person
	 */
	public List<Person> getPeopleList() {
		return peopleList;
	}

	/**
	 * Takes in a list of people and sets that list to the data field
	 * peopleList, which contains the list of people involved in the connection.
	 * 
	 * @param peopleList
	 */
	public void setPeopleList(List<Person> peopleList) {
		this.peopleList = peopleList;
	}

	/**
	 * Retrieves and returns the edge Id of the connection.
	 * 
	 * @return int edge id
	 */
	public int getEdgeId() {
		return edgeId;
	}

	/**
	 * Retrieves and returns the date of the connection as a string.
	 * 
	 * @return String date of connection
	 */
	public String getDay() {
		return day;
	}
	
	public String getMonth() {
		return month;
	}
	
	public String getYear() {
		return year;
	}

	/**
	 * Takes in a date of type String and sets the data field date, which is the
	 * date the interaction occurred.
	 * 
	 * @param date
	 */
	public void setDay(String day) {
		this.day = day;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Retrieves and returns the type of interaction as a String
	 * 
	 * @return interaction type as a String
	 */
	public String getTypeInteraction() {
		return typeInteraction;
	}

	/**
	 * Takes in a type of interaction of type String and sets the data field
	 * typeInteraction, which is the type of interaction that occured in the
	 * connection.
	 * 
	 * @param typeInteraction
	 */
	public void setTypeInteraction(String typeInteraction) {
		this.typeInteraction = typeInteraction;
	}

	/**
	 * Retrieves and returns the location of the interaction
	 * 
	 * @return location of interaction as type String
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Takes in a location of type String and sets the data field location,
	 * which is the location the interaction occurred.
	 * 
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Retrieves and returns the citation of the connection
	 * 
	 * @return citation of the interaction of type String
	 */
	public String getCitation() {
		return citation;
	}

	/**
	 * Takes in a String citation and sets the data field citation, which is the
	 * citation of the connection
	 * 
	 * @param citation
	 */
	public void setCitation(String citation) {
		this.citation = citation;
	}

	/**
	 * Retrieves and returns the direction of the interaction
	 * 
	 * @return direction of interaction as type String
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * Takes in a direction as type String and sets the data field direction,
	 * which corresponds to the direction of the interaction
	 * 
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * Retrieves and returns the social notes as type String
	 * 
	 * @return social notes of interaction as type String
	 */
	public String getSocialNotes() {
		return socialNotes;
	}

	/**
	 * Takes in social notes as type String and sets the data field socialNotes,
	 * which are the notes on the interaction
	 * 
	 * @param socialNotes
	 */
	public void setSocialNotes(String socialNotes) {
		this.socialNotes = socialNotes;
	}
	
	public String[] toPalladioArray(Person source, Person target) {
		return new String [] {source.getNodeName(), target.getNodeName()};
	}
	
	public String[] toGephiEdgeArray(Person source, Person target, int edgeId, String day, String month, String year, String location, String typeInteraction) {
		return new String[] {Integer.toString(source.getID()),Integer.toString(target.getID()), Integer.toString(edgeId), (day +  "/" + month + "/" + year), location, typeInteraction};
	}

}
