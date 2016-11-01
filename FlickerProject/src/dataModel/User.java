package dataModel;

public class User {
	private int id;
	private String fullName;
	private String username;
	private String password;
	private String permissions;

	/**
	 * Creates a user based on the parameters
	 * 
	 * @param id
	 * @param fullName
	 * @param username
	 * @param password
	 * @param permissions
	 */
	public User(int id, String fullName, String username, String password, String permissions) {
		this.id = id;
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.permissions = permissions;
	}

	/**
	 * Creates a user based on the csv row data
	 * 
	 * @param csvRowData
	 */
	public User(String[] csvRowData) {
		this.id = Integer.parseInt(csvRowData[0]);
		this.fullName = csvRowData[1];
		this.username = csvRowData[2];
		this.password = csvRowData[3];
		this.permissions = csvRowData[4];
	}

	/**
	 * creates a String[] with the User's data field for the CSV
	 * 
	 * @return String []
	 */
	public String[] toCSVRowArray() {
		return new String[] { "" + id, fullName, username, password, permissions };
	}

	/**
	 * Retrieves and returns the id for the user
	 * 
	 * @return Integer ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Retrieves and returns the username for the user
	 * 
	 * @return String Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username for the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retrieves and returns the password for the user
	 * 
	 * @return String Password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password for the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Retrieves and returns the FullName for the user
	 * 
	 * @return String fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the Full Name for the user
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Retrieves and returns the permissions for the user
	 * 
	 * @return String permissions
	 */
	public String getPermissions() {
		return permissions;
	}

	/**
	 * Sets the permissions for the user
	 */
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

}
