import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class DataStorageQueryTest {
	
	private static DataStorage storage = DataStorage.getTestDataStorage();

	public static void main(String[] args) {
		testNameQuery();
		testNodeNameQuery();
		testGenderQuery();
	}
	
	public static void testNameQuery() {
		PersonQuery nameQuery = new ContainsQuery("Lauren", "Name");
		PersonQuery nameQuery2 = new ContainsQuery("megan", "Name");
		PersonQuery nameQuery3 = new ContainsQuery("ToNy", "Name");
		DataStorage filtered;
		try {
			filtered = storage.personFilter(nameQuery2);
			System.out.println(filtered.getPeopleArrayList().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void testNodeNameQuery() {
		PersonQuery nodeNameQuery = new ContainsQuery("LJ", "Node Name");
		PersonQuery nodeNameQuery2 = new ContainsQuery("mj", "Node Name");
		PersonQuery nodeNameQuery3 = new ContainsQuery("Tl", "Node Name");
		DataStorage filtered;
		try {
			filtered = storage.personFilter(nodeNameQuery2);
			System.out.println(filtered.getPeopleArrayList().toString());
			//assertEquals("[Andrew, Forrest, Obama, Vecna, Gary, Lary, Olga, Sandy, Michael]", filtered.getPeopleArrayList().toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testGenderQuery() {
		PersonQuery genderQuery = new ContainsQuery("female", "Gender");
		PersonQuery genderQuery2 = new ContainsQuery("MALE", "Gender");
		DataStorage filtered;
		try {
			filtered = storage.personFilter(genderQuery2);
			System.out.println(filtered.getPeopleArrayList().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
