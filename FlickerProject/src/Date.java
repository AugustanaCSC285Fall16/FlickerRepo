public class Date {
	// constants
	private static final int[] DAYS_IN_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final int FEB_DAYS_LEAP_YEAR = 29;

	public static final int JANUARY = 1;
	public static final int FEBRUARY = 2;
	public static final int MARCH = 3;
	public static final int APRIL = 4;
	public static final int MAY = 5;
	public static final int JUNE = 6;
	public static final int JULY = 7;
	public static final int AUGUST = 8;
	public static final int SEPTEMBER = 9;
	public static final int OCTOBER = 10;
	public static final int NOVEMBER = 11;
	public static final int DECEMBER = 12;

	// fields
	private int year;
	private int month;
	private int day;

	// constructor
	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * Checks to see if the date is valid
	 * 
	 * @return boolean true if valid date
	 */
	public boolean isValidDate() {
		boolean leapYear = isLeapYear(year);
		if (day < 1) {
			return false;
		}
		if (month < JANUARY || month > DECEMBER) {
			return false;
		}
		if (leapYear) {
			if (month == FEBRUARY && day > FEB_DAYS_LEAP_YEAR) {
				return false;
			} else if (month != FEBRUARY && day > DAYS_IN_MONTH[month - 1]) {
				return false;
			}
		} else {
			if (day > DAYS_IN_MONTH[month - 1]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Retrieves and returns the data for year
	 * 
	 * @return integer for the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Retrieves and returns the data for month
	 * 
	 * @return integer for the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Retrieves and returns the data for day
	 * 
	 * @return integer for the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Sets the year, month, and day to the parameters
	 * 
	 * @param integer
	 *            year
	 * @param integer
	 *            month
	 * @param integer
	 *            day
	 */
	public void setDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * Checks to see if the dates are equal
	 * 
	 * @param Object
	 *            date
	 * @return true if the implicit parameter has the same data fields as the
	 *         formal parameter
	 */
	public boolean equals(Object date) {
		if (date instanceof Date) {
			Date other = (Date) date;
			return (year == other.year && month == other.month && day == other.day);
		} else {
			return false;
		}
	}

	/**
	 * Checks to see if the date is before the formal parameter
	 * 
	 * @param Date
	 *            date
	 * @return returns true if the implicit parameter comes before the formal
	 *         parameter on a calendar
	 */
	public boolean before(Date date) {
		if (year < date.year) {
			return true;
		} else if (year == date.year) {
			if (month < date.month) {
				return true;
			} else if (month == date.month) {
				if (day < date.day) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks to see if the date if after the formal parameter
	 * 
	 * @param Date
	 *            date
	 * @return true if the implicit parameter comes after the formal parameter
	 *         on a calendar
	 */
	public boolean after(Date date) {
		if (year > date.year) {
			return true;
		} else if (year == date.year) {
			if (month > date.month) {
				return true;
			} else if (month == date.month) {
				if (day > date.day) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Makes the day, month, year into a formated String
	 * 
	 * @return String representation of the date
	 */
	public String toString() {
		String returnValue = month + "/" + day + "/" + year;
		return returnValue;
	}

	/**
	 * Checks to see if the passed in year is a leap year
	 * 
	 * @param year
	 * @return true if leap year based on criteria
	 */
	private boolean isLeapYear(int year) {
		if (year % 400 == 0) {
			return true;
		} else if (year % 100 == 0) {
			return false;
		} else if (year % 4 == 0) {
			return true;
		} else {
			return false;
		}
	}
}