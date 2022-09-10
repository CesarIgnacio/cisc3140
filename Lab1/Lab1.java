import java.util.Calendar;
import java.util.GregorianCalendar;
 
/**
  * This application calculates the remaining years, months, days, hours, and minutes between now and the due date of
  * pre-established Lab dates (Lab1, Lab2, Lab3, and Lab4).
  * @author cesar
  *
  */
public class Lab1 {

	/**
	 * The four due dates stored as global constants.
	 */
	final static Calendar lab1 = new GregorianCalendar(2022, 8, 28, 15, 30);
	final static Calendar lab2 = new GregorianCalendar(2022, 9, 26, 15, 30);
	final static Calendar lab3 = new GregorianCalendar(2022, 10, 23, 15, 30);
	final static Calendar lab4 = new GregorianCalendar(2022, 11, 07, 15, 30);

	/**
	 * Main method. Creates an instance of Calendar with today's date and prints to console the remaining time for the labs
	 * @param args
	 */
	public static void main(String[] args) {

		Calendar today = Calendar.getInstance();
		
		System.out.println("Time remaining til due dates:\n");
		System.out.print("Lab1: " + remainingTime(today, lab1));
		System.out.print("Lab2: " + remainingTime(today, lab2));
		System.out.print("Lab3: " + remainingTime(today, lab3));
		System.out.print("Lab4: " + remainingTime(today, lab4));

	}

	/**
	 * This recursive method check if the values received are negatives. A negative year is the breaking point of this recursive
	 * method.
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param min
	 * @param monthBDD - This value is used to identify if the Month Before the Due Date has 28, 30 or 31 days.
	 * @return A formated String with the remaining days or a message saying that the due date has expired.
	 */
	private static String remainingTime(int year, int month, int day, int hour, int min, int monthBDD) {
		if (year < 0) {
			return "The lab's due date has expired.";
		} if (month < 0) {
			month = 12 + month;
			return remainingTime(--year, month, day, hour, min, monthBDD);
		} if (day < 0) {
			day = calcRemDays(monthBDD, day);
			return remainingTime(year, --month, day, hour, min, monthBDD);
		} if (hour < 0) {
			hour = 24 + hour;
			return remainingTime(year, month, --day, hour, min, monthBDD);
		} if (min < 0) {
			min = 60 + min;
			return remainingTime(year, month, day, --hour, min, monthBDD);
		}
		return String.format("%02d months, %02d days, %02d hours, and %02d minutes.\n", month, day , hour, min);
	}
	
	/**
	 * Checks which months have 28, 30, or 31 days.
	 * @param month
	 * @param day
	 * @return The remaining days
	 */
	private static int calcRemDays(int month, int day) {
		if (month == 3 || month == 5 || month == 8 || month == 10)
			return 30 + day;
		else if (month == 1)
			return 28 + day;
		else
			return 31 + day;
	}
	
	/**
	 * Extract the year, month, day, hour, and minute's numerical value from 'today' and 'labDay', subtract the values and
	 * send them to the recursive version of this method.
	 * @param today
	 * @param labDay
	 * @return Calls the recursive version of this method using the subtracted numbers as parameters.
	 */
	private static String remainingTime(Calendar today, Calendar labDay) {
		int year, month, day, hour, min;

		year = labDay.get(Calendar.YEAR) - today.get(Calendar.YEAR);
		month = labDay.get(Calendar.MONTH) - today.get(Calendar.MONTH);
		day = labDay.get(Calendar.DATE) - today.get(Calendar.DATE);
		hour = labDay.get(Calendar.HOUR_OF_DAY) - today.get(Calendar.HOUR_OF_DAY);
		min = labDay.get(Calendar.MINUTE) - today.get(Calendar.MINUTE);
		
		return remainingTime(year, month, day, hour, min, labDay.get(Calendar.MONTH) - 1);
	}

}
