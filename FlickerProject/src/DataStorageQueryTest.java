import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class DataStorageQueryTest {
	
	private static DataStorage storage = DataStorage.getTestDataStorage();
	
	@Test
	public void testNameQuery() {
		PersonQuery nameQuery = new ContainsQuery("Lauren", "Name");
		PersonQuery nameQuery2 = new ContainsQuery("megan", "Name");
		PersonQuery nameQuery3 = new ContainsQuery("ToNy", "Name");
		PersonQuery nameQuery4 = new ContainsQuery("", "Name");
		DataStorage filtered;
		try {
			filtered = storage.personFilter(nameQuery);
			assertEquals("[Lauren]",filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(nameQuery2);
			assertEquals("[Megan]",filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(nameQuery3);
			assertEquals("[Tony]",filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(nameQuery4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testNodeNameQuery() {
		PersonQuery nodeNameQuery = new ContainsQuery("LJ", "Node Name");
		PersonQuery nodeNameQuery2 = new ContainsQuery("mj", "Node Name");
		PersonQuery nodeNameQuery3 = new ContainsQuery("Tl", "Node Name");
		DataStorage filtered;
		try {
			filtered = storage.personFilter(nodeNameQuery);
			assertEquals("[Lauren]",filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(nodeNameQuery2);
			assertEquals("[Megan]",filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(nodeNameQuery3);
			assertEquals("[Tony]",filtered.getPeopleArrayList().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testGenderQuery() {
		PersonQuery genderQuery = new ContainsQuery("female", "Gender");
		PersonQuery genderQuery2 = new ContainsQuery("MALE", "Gender");
		DataStorage filtered;
		try {
			filtered = storage.personFilter(genderQuery);
			assertEquals("[Lauren, Megan]", filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(genderQuery2);
			assertEquals("[Tony, Andrew, Forrest]", filtered.getPeopleArrayList().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
