import java.util.GregorianCalendar;

public class DateQuery implements ConnectionQuery{

	// data fields
	private String targetDay;
	private String targetMonth;
	private String targetYear;
	
	public DateQuery(String day, String month, String year) {
		targetDay = day;
		targetMonth = month;
		targetYear = year;
	}
	
	@Override
	public boolean accepts(Connection connection) {
		Date date = connection.getDate();
		System.out.println(date);
		Date targetDate = new Date(Integer.parseInt(targetYear),Integer.parseInt(targetMonth), Integer.parseInt(targetDay));
		System.out.println(targetDate);
		return date.equals(targetDate);
	}

}
