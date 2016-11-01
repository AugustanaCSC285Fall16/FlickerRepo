package dataModel;

public class ContainsQuery implements PersonQuery, ConnectionQuery, UserQuery {
	private String target;
	private String fieldName;

	public ContainsQuery(String target, String fieldName) {
		this.target = target;
		this.fieldName = fieldName;
	}

	@Override
	public boolean accepts(Person person) {
		if (fieldName.equals("Name")) {
			return person.getName().toLowerCase().contains(target.toLowerCase());
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

	@Override
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
		} else if (fieldName.equals("Interaction Type")) {
			return connection.getTypeInteraction().toLowerCase().contains(target.toLowerCase());
		} else {
			throw new UnsupportedOperationException("Invalid query field");
		}
	}

	@Override
	public boolean accepts(User user) {
		if (fieldName.equals("Username")) {
			return user.getUsername().equalsIgnoreCase(target);
		} else if (fieldName.equals("Password")) {
			return user.getPassword().equalsIgnoreCase(target);
		} else {
			throw new UnsupportedOperationException("Invalid Username or password");
		}
	}
}
