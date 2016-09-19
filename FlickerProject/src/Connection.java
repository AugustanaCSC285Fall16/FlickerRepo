import java.util.*;

public class Connection {
	// data fields
	private Date date;
	private String typeInteraction;
	private String location;
	private String citation;
	private Person person1;
	private Person person2;
	private int direction;
	
	// constructor
	public Connection(Date date, String typeInteraction, String location, String citation, Person person1,
			Person person2, int direction) {
		super();
		this.date = date;
		this.typeInteraction = typeInteraction;
		this.location = location;
		this.citation = citation;
		this.person1 = person1;
		this.person2 = person2;
		this.direction = direction;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public Person getPerson1() {
		return person1;
	}

	public void setPerson1(Person person1) {
		this.person1 = person1;
	}

	public Person getPerson2() {
		return person2;
	}

	public void setPerson2(Person person2) {
		this.person2 = person2;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	
	
}
