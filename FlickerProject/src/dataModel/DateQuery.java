package dataModel;

public class DateQuery implements ConnectionQuery {

	// data fields
	private Date targetDate;
	private Date targetDate2;

	// constructor
	public DateQuery(Date targetDate, Date targetDate2) {
		this.targetDate = targetDate;
		this.targetDate2 = targetDate2;
	}

	/**
	 * Overrides the accepts method in the ConnectionQuery interface.
	 * 
	 * @return true if the connection contains the target Date or is 
	 * within the given Date range
	 */
	@Override
	public boolean accepts(Connection connection) {
		Date date = connection.getDate();
		if (targetDate2 == null) {
			return date.equals(targetDate);
		} else {

			return date.after(targetDate) && date.before(targetDate2);
		}

	}

}
