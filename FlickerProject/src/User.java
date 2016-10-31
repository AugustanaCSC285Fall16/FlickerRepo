
public class User {
	// data fields
	private int id;
	private String fullName;
	private String username;
	private String password;
	private String permissions;

	// constructor
	public User(int id, String fullName, String username, String password, String permissions) {
		this.id = id;
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.permissions = permissions;
	}

	public User(String[] csvRowData) {
		this.id = Integer.parseInt(csvRowData[0]);
		this.fullName = csvRowData[1];
		this.username = csvRowData[2];
		this.password = csvRowData[3];
		this.permissions = csvRowData[4];
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String[] toCSVRowArray() {
		return new String[] { "" + id, fullName, username, password, permissions };
	}

}
