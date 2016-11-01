
public class DateQuery implements ConnectionQuery {

	// data fields
	private Date targetDate;
	private Date targetDate2;

	public DateQuery(Date targetDate, Date targetDate2) {
		this.targetDate = targetDate;
		this.targetDate2 = targetDate2;
	}

	@Override
	public boolean accepts(Connection connection) {
		Date date = connection.getDate();
		if(targetDate2 == null) {
			return date.equals(targetDate);
		} else {
			
			return date.after(targetDate) && date.before(targetDate2);
		}
		
	}

}
