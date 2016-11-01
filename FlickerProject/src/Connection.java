import java.util.*;

public class Connection implements TableRowViewable {

	private String day;
	private String month;
	private String year;
	private Date date;
	private String typeInteraction;
	private String location;
	private String citation;
	private String socialNotes;
	private List<Person> peopleList;
	private String direction;
	private int edgeId;

	/**
	 * Creates a connection object with all of the parameters.
	 * 
	 * @param edgeID
	 * @param day
	 * @param month
	 * @param year
	 * @param typeInteraction
	 * @param location
	 * @param citation
	 * @param socialNotes
	 * @param people
	 * @param direction
	 */
	public Connection(int edgeID, String day, String month, String year, String typeInteraction, String location,
			String citation, String socialNotes, List<Person> people, String direction) {
		this.edgeId = edgeID;
		this.day = day;
		this.month = month;
		this.year = year;
		this.date = new Date(Integer.parseInt(this.year), Integer.parseInt(this.month), Integer.parseInt(this.day));
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

	/**
	 * This creates a String date that is to the CSV format
	 * 
	 * @return String - String that will be printed to CSV
	 */
	public String dateToCSVText() {
		String result = month + ":" + day + ":" + year;
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
		return new String[] { Integer.toString(edgeId), peopleList.toString(), date.toString(), typeInteraction,
				location, citation, socialNotes, direction };
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
	 * @return integer edge id
	 */
	public int getEdgeId() {
		return edgeId;
	}

	/**
	 * Retrieves and returns the day of the connection as a string.
	 * 
	 * @return String day of connection
	 */
	public String getDay() {
		return day;
	}

	/**
	 * Retrieves and returns the month of the connection as a string
	 * 
	 * @return String month of connection
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * Retrieves and returns the year of the connection as a string.
	 * 
	 * @return String year of connection
	 */
	public String getYear() {
		return year;
	}

	/**
	 * Retrieves and returns the date of the connection as a string.
	 * 
	 * @return String date of connection
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the data field day, which is the day the interaction occurred.
	 * 
	 * @param String
	 *            day
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * Sets the data field month, which is the month the interaction occurred.
	 * 
	 * @param String
	 *            month
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * Sets the data field year, which is the year the interaction occurred.
	 * 
	 * @param String
	 *            year
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Sets the data field date, which is the date the interaction occurred.
	 * 
	 * @param String
	 *            Date
	 */
	public void setDate() {
		date.setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
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
	 * typeInteraction, which is the type of interaction that occurred in the
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

	/**
	 * Creates a string array to be used with Palladio with the source and
	 * target person and returns it
	 * 
	 * @param Person
	 *            source
	 * @param Person
	 *            target
	 * @return String[] to be used in exporting the data to Palladio
	 */
	public String[] toPalladioArray(Person source, Person target) {
		return new String[] { source.getNodeName(), target.getNodeName() };
	}

	/**
	 * Creates a string array to be used with Gephi with all of the Parameters
	 * 
	 * @param Person
	 *            source
	 * @param Person
	 *            target
	 * @param integer
	 *            edgeId
	 * @param String
	 *            day
	 * @param String
	 *            month
	 * @param String
	 *            year
	 * @param String
	 *            location
	 * @param String
	 *            typeInteraction
	 * @return String [] to be used in exporting the data to Gephi
	 */
	public String[] toGephiEdgeArray(Person source, Person target, int edgeId, String day, String month, String year,
			String location, String typeInteraction) {
		return new String[] { Integer.toString(source.getID()), Integer.toString(target.getID()),
				Integer.toString(edgeId), (day + "/" + month + "/" + year), location, typeInteraction };
	}

}
