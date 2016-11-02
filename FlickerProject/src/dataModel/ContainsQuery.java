package dataModel;

public class ContainsQuery implements PersonQuery, ConnectionQuery, UserQuery {
	// data fields
	private String target;
	private String fieldName;

	// constructor
	public ContainsQuery(String target, String fieldName) {
		this.target = target;
		this.fieldName = fieldName;
	}

	/**
	 * Overrides the accepts method in the PersonQuery interface.
	 * 
	 * @return true if the person contains the target String in the specified
	 * field
	 * 
	 * @throws UnsupportedOperationException if the field is invalid
	 */
	@Override
	public boolean accepts(Person person) {
		if (fieldName.equals("Name")) {
			return person.getName().toLowerCase().contains(target.toLowerCase());
		} else if (fieldName.equals("Cultural ID")) {
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

	/**
	 * Overrides the accepts method in the ConnectionQuery interface.
	 * 
	 * @return true if the connection contains the target String in the specified
	 * field
	 * 
	 * @throws UnsupportedOperationException if the field is invalid
	 */
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

	/**
	 * Overrides the accepts method in the UserQuery interface.
	 * 
	 * @return true if the user contains the target String in the specified
	 * field
	 * 
	 * @throws UnsupportedOperationException if the field is invalid
	 */
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
