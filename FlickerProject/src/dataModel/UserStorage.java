package dataModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class UserStorage {

	public Map<Integer, User> userMap;
	private String[] userHeaderRow;
	private static final String USER_DATA_FILE_NAME = "UserData.csv";
	private static UserStorage primaryUserStorage = null;

	/**
	 * Creates our main User Storage Object that will be used all over in the
	 * program to call methods related to the data of users
	 * 
	 * @return User Storage object that will be a singleton
	 * @throws IOException
	 */
	public static UserStorage getMainUserStorage() throws IOException {
		if (primaryUserStorage == null) {
			primaryUserStorage = new UserStorage();
			primaryUserStorage.loadUsers();
		}
		return primaryUserStorage;
	}

	/**
	 * The Private constructor only Initializes the data fields but doesn't fill
	 * them in This allows for the singleton pattern
	 * 
	 * @throws IOException
	 */
	private UserStorage() throws IOException {
		userMap = new TreeMap<>();
	}

	/**
	 * loads the Users from the csv file
	 * 
	 * @throws IOException
	 */
	private void loadUsers() throws IOException {
		CSVReader reader = new CSVReader(new InputStreamReader(
				new FileInputStream((DataStorage.DATA_FOLDER + "/" + USER_DATA_FILE_NAME)), "UTF-8"));

		List<String[]> myRows = reader.readAll();
		userHeaderRow = myRows.remove(0); // remove header row

		for (String[] row : myRows) {
			addUser(new User(row));
		}
	}

	/**
	 * Saves the users to the csv file
	 * 
	 * @throws IOException
	 */
	public void saveUsers() throws IOException {
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(
				new FileOutputStream((DataStorage.DATA_FOLDER + "/" + USER_DATA_FILE_NAME)), "UTF-8"));
		writer.writeNext(userHeaderRow);
		for (User user : userMap.values()) {
			writer.writeNext(user.toCSVRowArray());
		}
		writer.close();
	}

	/**
	 * Adds the user parameter to the user map.
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		userMap.put(user.getId(), user);
	}

	/**
	 * Converts the userMap to an arrayList
	 * 
	 * @return ArrayList<User>
	 */
	public ArrayList<User> getUserArrayList() {
		ArrayList<User> userList = new ArrayList<>(userMap.values());
		return userList;
	}

	/**
	 * Converts the UserMap to a collection of type user
	 * 
	 * @return Collection<User>
	 */
	public Collection<User> getUserList() {
		return userMap.values();
	}

	/**
	 * Checks to see if the user is in the data and then returns the user
	 * 
	 * @param query
	 * @return user in data
	 * @throws IOException
	 */
	public User getUserFromFiltered(UserQuery query) throws IOException {
		for (User user : primaryUserStorage.getUserList()) {
			if (query.accepts(user)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Checks to see if the user is in the data.
	 * 
	 * @param query
	 * @return boolean true if in data
	 * @throws IOException
	 */
	public boolean userFilter(UserQuery query) throws IOException {
		for (User user : primaryUserStorage.getUserList()) {
			if (query.accepts(user)) {
				return true;
			}
		}
		return false;
	}

}
