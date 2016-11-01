package dataModel;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


import java.io.IOException;

import org.junit.Test;

public class DataStorageQueryTest {

	private static DataStorage storage = DataStorage.getTestDataStorage();

	/**
	 * This method tests our query when searching for a name in both the 
	 * person data and connection data.
	 */
	@Test
	public void testNameQuery() {
		PersonQuery nameQuery = new ContainsQuery("Lauren", "Name");
		PersonQuery nameQuery2 = new ContainsQuery("megan", "Name");
		PersonQuery nameQuery3 = new ContainsQuery("ToNy", "Name");
		PersonQuery nameQuery4 = new ContainsQuery("", "Name");
		ConnectionQuery nameQuery5 = new ContainsQuery("andrew", "Name");
		DataStorage filtered;
		try {
			filtered = storage.personFilter(nameQuery);
			assertEquals("[Lauren]", filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(nameQuery2);
			assertEquals("[Megan]", filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(nameQuery3);
			assertEquals("[Tony]", filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(nameQuery4);
			assertEquals("[Lauren, Megan, Tony, Andrew, Forrest]", filtered.getPeopleArrayList().toString());
			filtered = storage.connectionFilter(nameQuery5);
			assertEquals(
					"[[Andrew, Forrest], 11/12/2016, paris, AIM, [Tony, Lauren, Megan, Andrew, Forrest], 6/20/1995, unknown, AIM]",
					filtered.getConnectionArrayList().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method tests our query when searching for a node name in both the 
	 * person data and connection data.
	 */
	@Test
	public void testNodeNameQuery() {
		PersonQuery nodeNameQuery = new ContainsQuery("LJ", "Node Name");
		PersonQuery nodeNameQuery2 = new ContainsQuery("mj", "Node Name");
		PersonQuery nodeNameQuery3 = new ContainsQuery("Tl", "Node Name");
		ConnectionQuery nodeNameQuery4 = new ContainsQuery("fs", "Node Name");
		ConnectionQuery nodeNameQuery5 = new ContainsQuery("AB", "Node Name");
		DataStorage filtered;
		try {
			filtered = storage.personFilter(nodeNameQuery);
			assertEquals("[Lauren]", filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(nodeNameQuery2);
			assertEquals("[Megan]", filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(nodeNameQuery3);
			assertEquals("[Tony]", filtered.getPeopleArrayList().toString());
			filtered = storage.connectionFilter(nodeNameQuery4);
			assertEquals(
					"[[Andrew, Forrest], 11/12/2016, paris, AIM, [Tony, Lauren, Megan, Andrew, Forrest], 6/20/1995, unknown, AIM, [Megan, Tony, Lauren, Forrest], 5/12/2015, paris, Email]",
					filtered.getConnectionArrayList().toString());
			filtered = storage.connectionFilter(nodeNameQuery5);
			assertEquals(
					"[[Andrew, Forrest], 11/12/2016, paris, AIM, [Tony, Lauren, Megan, Andrew, Forrest], 6/20/1995, unknown, AIM]",
					filtered.getConnectionArrayList().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method tests our query when searching for gender in both the 
	 * person data and connection data.
	 */
	@Test
	public void testGenderQuery() {
		PersonQuery genderQuery = new ContainsQuery("female", "Gender");
		PersonQuery genderQuery2 = new ContainsQuery("MALE", "Gender");
		ConnectionQuery genderQuery3 = new ContainsQuery("female", "Gender");
		ConnectionQuery genderQuery4 = new ContainsQuery("MALE", "Gender");
		DataStorage filtered;
		try {
			filtered = storage.personFilter(genderQuery);
			assertEquals("[Lauren, Megan]", filtered.getPeopleArrayList().toString());
			filtered = storage.personFilter(genderQuery2);
			assertEquals("[Tony, Andrew, Forrest]", filtered.getPeopleArrayList().toString());
			filtered = storage.connectionFilter(genderQuery3);
			assertEquals(
					"[[Tony, Lauren, Megan, Andrew, Forrest], 6/20/1995, unknown, AIM, [Megan, Tony, Lauren, Forrest], 5/12/2015, paris, Email]",
					filtered.getConnectionArrayList().toString());
			filtered = storage.connectionFilter(genderQuery4);
			assertEquals(
					"[[Andrew, Forrest], 11/12/2016, paris, AIM, [Tony, Lauren, Megan, Andrew, Forrest], 6/20/1995, unknown, AIM, [Megan, Tony, Lauren, Forrest], 5/12/2015, paris, Email]",
					filtered.getConnectionArrayList().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method tests our query when searching for a date or date range
	 *  in both the person data and connection data.
	 */
	@Test
	public void testDateQuery() {
		Date date1 = new Date(1995, 6, 20);
		Date date2 = new Date(2015, 4, 12);
		Date date3 = new Date(2016, 12, 12);
		ConnectionQuery dateQuery = new DateQuery(date1, null);
		ConnectionQuery dateRangeQuery = new DateQuery(date2, date3);
		DataStorage filtered;
		try {
			filtered = storage.connectionFilter(dateQuery);
			assertEquals(
					"[[Tony, Lauren, Megan, Andrew, Forrest], 6/20/1995, unknown, AIM]",
					filtered.getConnectionArrayList().toString());
			filtered = storage.connectionFilter(dateRangeQuery);
			assertEquals(
					"[[Andrew, Forrest], 11/12/2016, paris, AIM, [Megan, Tony, Lauren, Forrest], 5/12/2015, paris, Email]",
					filtered.getConnectionArrayList().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
