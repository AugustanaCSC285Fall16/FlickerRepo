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

	public boolean isValidDate() {
		boolean leapYear = isLeapYear(year);
		if(day == 0 && month == 0 && year == 0000) {
			return true;
		}
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
	
	public boolean isUnknownDate() {
		if(day == 0 && month == 0 && year == 0000) {
			return true;
		} else {
			return false;
		}
	}

	// accessor methods
	// returns year
	public int getYear() {
		return year;
	}

	// returns month
	public int getMonth() {
		return month;
	}

	// return day
	public int getDay() {
		return day;
	}

	public void setDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	// other instance methods
	// returns true if the implicit parameter has the same data fields as the
	// formal parameter, false otherwise
	public boolean equals(Object date) {
		if (date instanceof Date) {
			Date other = (Date) date;
			return (year == other.year && month == other.month && day == other.day);
		} else {
			return false;
		}
	}

	// returns true if the implicit parameter comes before the formal parameter
	// on a calendar, false otherwise
	public boolean before(Date other) {
		if (year < other.year) {
			return true;
		} else if (year == other.year) {
			if (month < other.month) {
				return true;
			} else if (month == other.month) {
				if (day < other.day) {
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

	// returns true if the implicit parameter comes after the formal parameter
	// on a calendar, false otherwise
	public boolean after(Date other) {
		if (year > other.year) {
			return true;
		} else if (year == other.year) {
			if (month > other.month) {
				return true;
			} else if (month == other.month) {
				if (day > other.day) {
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

	// returns string representation of date
	public String toString() {
		String returnValue = month + "/" + day + "/" + year;
		return returnValue;
	}

	// returns true if leap year based on criteria, false otherwise
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