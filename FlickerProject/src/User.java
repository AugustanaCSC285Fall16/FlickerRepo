
public class User {
	// data fields
	private String fullName;
	private String username;
	private String password;
	private String permissions;
	
	// constructor
	public User(String fullName, String username, String password, String permissions) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.permissions = permissions;
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

	public String isPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	public String[] toCSVRowArray(){
		return new String[] {fullName, username, password, permissions};
	}
	
	
}
