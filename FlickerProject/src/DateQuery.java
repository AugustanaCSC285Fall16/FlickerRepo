
public class DateQuery implements ConnectionQuery {

	// data fields
	private Date targetDate;

	public DateQuery(Date targetDate) {
		this.targetDate = targetDate;
	}

	@Override
	public boolean accepts(Connection connection) {
		Date date = connection.getDate();
		return date.equals(targetDate);
	}

}
