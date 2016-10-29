
public class ContainsQuery implements PersonQuery, ConnectionQuery {
	private String target;
	private String fieldName;

	public ContainsQuery(String target, String fieldName) {
		this.target = target;
		this.fieldName = fieldName;
	}

	public boolean accepts(Person person) {
		if (fieldName.equals("Name")) {
			return person.getName().contains(target);
		} else if (fieldName.equals("CulturalID")) {
			return person.getCulturalId().equalsIgnoreCase(target);
		} else if (fieldName.equals("Node Name")) {
			return person.getNodeName().equalsIgnoreCase(target);
		} else if (fieldName.equals("Gender")) {
			return person.getGender().equalsIgnoreCase(target);
		} else if (fieldName.equals("Occupation")) {
			return person.getOccupation().equalsIgnoreCase(target);
		} else {
			throw new UnsupportedOperationException("Invalid query field");
		}
	}

	public boolean accepts(Connection connection) {
		if (fieldName.equals("Name") || fieldName.equals("Node Name") || fieldName.equals("CulturalID")
				|| fieldName.equals("Gender") || fieldName.equals("Occupation")) {
			for (Person person : connection.getPeopleList()) {
				if (accepts(person)) {
					return true;
				}
			}
			return false;
		} else if (fieldName.equals("Location")) {
			return connection.getLocation().contains(target);
		} else if (fieldName.equals("Type")) {
			return connection.getTypeInteraction().contains(target);
		} else {
			throw new UnsupportedOperationException("Invalid query field");
		}
	}
}
