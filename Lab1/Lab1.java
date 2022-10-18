import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * This application calculates the remaining years, months, days, hours, and
 * minutes between now and the due date of pre-established Lab dates (Lab1,
 * Lab2, Lab3, and Lab4).
 * 
 * @author cesar
 *
 */
public class Lab1 {

	/**
	 * The four due dates stored as global constants. Uses
	 * java.util.GregorianCalendar
	 */
	final static Calendar LAB1 = new GregorianCalendar(2022, 8, 28, 15, 30);
	final static Calendar LAB2 = new GregorianCalendar(2022, 9, 26, 15, 30);
	final static Calendar LAB3 = new GregorianCalendar(2022, 10, 23, 15, 30);
	final static Calendar LAB4 = new GregorianCalendar(2022, 11, 07, 15, 30);
	final static String FORMAT1 = "yyyy-MM-dd";
	final static String FORMAT2 = "yyyy-MM";

	/*
	 * input format yyyy-mm
	 * 
	 * stage
	 */

	/**
	 * Main method. Creates an instance of Calendar with today's date and prints to
	 * console the remaining time for the labs
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		char choice = 0;
		boolean stop = false;

		do {
			menu();
			choice = scan.nextLine().charAt(0);
			switch (choice) {
			case 'A':
			case 'a':
				compareTwoDates(scan);
				break;
			case 'S':
			case 's':
				todayUntilDate(scan);
				break;
			case 'D':
			case 'd':
				chooseDateGuided(scan);
				break;
			case 'F':
			case 'f':
				compareTwoDatesOptions(scan);
				break;
			case 'G':
			case 'g':
				firstVersion();
				break;
			case 'Q':
			case 'q':
				stop = true;
				System.out.println("Good bye!");
				break;
			default:
				System.out.println("Wrong option, try again.");
				break;
			}

		} while (!stop);

	}

	/**
	 * This recursive method check if the values received are negatives. A negative
	 * year is the breaking point of this recursive method.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param min
	 * @param monthBDD - This value is used to identify if the Month Before the Due
	 *                 Date has 28, 30 or 31 days.
	 * @return A formated String with the remaining days or a message saying that
	 *         the due date has expired.
	 */
	private static String remainingTime(int year, int month, int day, int hour, int min, int monthBDD) {
		if (year < 0) {
			return "The lab's due date has expired.\n";
		}
		if (month < 0) {
			month = 12 + month;
			return remainingTime(--year, month, day, hour, min, monthBDD);
		}
		if (day < 0) {
			day = calcRemDays(monthBDD, day);
			return remainingTime(year, --month, day, hour, min, monthBDD);
		}
		if (hour < 0) {
			hour = 24 + hour;
			return remainingTime(year, month, --day, hour, min, monthBDD);
		}
		if (min < 0) {
			min = 60 + min;
			return remainingTime(year, month, day, --hour, min, monthBDD);
		}
		return String.format("%02d months, %02d days, %02d hours, and %02d minutes.\n", month, day, hour, min);
	}

	/**
	 * Checks which months have 28, 30, or 31 days.
	 * 
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
	 * Extract the year, month, day, hour, and minute's numerical value from 'today'
	 * and 'labDay', subtract the values and send them to the recursive version of
	 * this method.
	 * 
	 * @param today
	 * @param labDay
	 * @return Calls the recursive version of this method using the subtracted
	 *         numbers as parameters.
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

	/**
	 * Creates an instance of java.util.Calendar with today's date and prints to
	 * console the remaining time for the labs.
	 */
	private static void firstVersion() {
		Calendar today = Calendar.getInstance();

		System.out.println("Time remaining til due dates:\n");
		System.out.print("Lab1: " + remainingTime(today, LAB1));
		System.out.print("Lab2: " + remainingTime(today, LAB2));
		System.out.print("Lab3: " + remainingTime(today, LAB3));
		System.out.print("Lab4: " + remainingTime(today, LAB4));
		System.out.println();
	}

	/**
	 * Prints the option menu.
	 */
	public static void menu() {
		System.out.println("Welcome to the time calculator app.");
		System.out.println(
				"Type one of the following options:\n" + "A - number of days in between date 'one' and 'two'.\n"
						+ "S - number of days from now to a specific date (manualy inserted).\n"
						+ "D - number of days from now to a specific date (using guiding tool).\n"
						+ "F - number of days form now to one of our preselected dates.\n"
						+ "G - remaining time for the Labs (original version).\n" + "Q - to Exit program.");
	}

	/**
	 * Compares two console input dates (format: 'yyyy-mm-dd'). Output in console
	 * the number of days in between.
	 * 
	 * @param scan
	 */
	public static void compareTwoDates(Scanner scan) {
		boolean itWorks = false;
		do {
			System.out.println("Insert first date (ex.: 2020-02-22)");
			String date1 = scan.nextLine();
			System.out.println("Insert second date (ex.: 2021-05-25)");
			String date2 = scan.nextLine();
			// Check if the two dates have the first acceptable format
			if (isValidFormat(FORMAT1, date1) && isValidFormat(FORMAT1, date2)) {
				LocalDate formatDate1 = LocalDate.parse(date1);
				LocalDate formatDate2 = LocalDate.parse(date2);
				// Calculates the remaining days between formatDate1 and formatDate2
				long days = formatDate1.until(formatDate2, ChronoUnit.DAYS);
				// Prints the result
				System.out.println(
						"Between '" + formatDate1 + "' and '" + formatDate2 + "' there are '" + days + "' day(s).\n");
				itWorks = true;
				// Check if the two dates have the second acceptable format
			} else if (isValidFormat(FORMAT2, date1) && isValidFormat(FORMAT2, date2)) {
				String auxDate1 = date1 + "-01";
				String auxDate2 = date2 + "-01";
				LocalDate formatDate1 = LocalDate.parse(auxDate1);
				LocalDate formatDate2 = LocalDate.parse(auxDate2);
				// Calculates the remaining days between formatDate1 and formatDate2
				long days = formatDate1.until(formatDate2, ChronoUnit.DAYS);
				// Prints the result
				System.out.println("Between '" + formatDate1.getYear() + "-" + formatDate1.getMonthValue() + "' and '"
						+ formatDate2.getYear() + "-" + formatDate2.getMonthValue() + "' there are '" + days
						+ "' day(s).\n");
				itWorks = true;
				// Prints a wrong format message otherwise
			} else {
				System.out.println("Wrong date format, try again.");
			}
		} while (!itWorks); // It loops until 'itWorks' is true
	}

	/**
	 * Validates the date format (yyyy-MM-dd).
	 * 
	 * @param format
	 * @param value
	 * @return boolean: If the date format is correct or not
	 */
	public static boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			// ex.printStackTrace();
		}
		return date != null;
	}

	/**
	 * Compares today with a future date entered by the user using the format
	 * (yyyy-MM-dd). Output in console the numbers of days in between.
	 * 
	 * @param scan
	 */
	public static void todayUntilDate(Scanner scan) {
		boolean itWorks = false;
		LocalDate today = LocalDate.now();
		long days = -1;
		do {
			System.out.println("Enter a future date (ex.: 2023-02-25)");
			String date = scan.nextLine();
			// If the date has the format yyyy-MM-dd
			if (isValidFormat(FORMAT1, date)) {
				LocalDate formatDate = LocalDate.parse(date);
				days = today.until(formatDate, ChronoUnit.DAYS);
				System.out.println("Between today and '" + formatDate + "' there are '" + days + "' day(s).\n");
				itWorks = true;
				// If the date has the format yyyy-MM
			} else if (isValidFormat(FORMAT2, date)) {
				date += "-01";
				LocalDate formatDate = LocalDate.parse(date);
				days = today.until(formatDate, ChronoUnit.DAYS);
				System.out.println("Between today and '" + formatDate.getYear() + "-" + formatDate.getMonthValue()
						+ "' there are '" + days + "' day(s).\n");
				itWorks = true;
				// Prints a wrong format message
			} else {
				System.out.println("Wrong date format, try again.");
			}
		} while (!itWorks); // Keeps looping until the format entered is the correct one.
	}

	/**
	 * Prints the number of days from today to a specified date (choosing from the
	 * options.
	 * 
	 * @param scan
	 */
	public static void compareTwoDatesOptions(Scanner scan) {
		System.out.println("See how many days there are from 'today' to one of the following upcoming dates:");
		boolean corectOption = true;
		do {
			try {
				System.out.println("Type a option choosing from 1 - 6");
				System.out.println("1 - Halloween\n2 - Thanksgiving\n3 - Christmas\n"
						+ "4 - New Year's Day\n5 - Valentine's Day\n6 - Saint Patrick's Day");
				String option = scan.nextLine();
				int index = Integer.parseInt(option.charAt(0) + "");
				LocalDate now = LocalDate.now();
				LocalDate date = LocalDate.now();
				String dateName = "now";
				switch (index) {
				case 1: // Halloween
					date = LocalDate.of(2022, 10, 31);
					dateName = "Halloween";
					break;
				case 2: // Thanksgiving
					date = LocalDate.of(2022, 11, 24);
					dateName = "Thanksgiving";
					break;
				case 3: // Christmas
					date = LocalDate.of(2022, 12, 25);
					dateName = "Christmas";
					break;
				case 4: // New Year's Day
					date = LocalDate.of(2023, 01, 01);
					dateName = "New Year's Day";
					break;
				case 5: // Valentine's Day
					date = LocalDate.of(2023, 02, 14);
					dateName = "Valentine's Day";
					break;
				case 6: // Saint Patrick's Day
					date = LocalDate.of(2023, 03, 17);
					dateName = "Saint Patrick's Day";
					break;
				default:
					corectOption = false;
					break;
				}
				long days = now.until(date, ChronoUnit.DAYS);
				if (days > 0) {
					System.out.println("From now to " + dateName + ", there are " + days + " days.");
				} else {
					System.out.println("It seams that you chose the wrong option, try again");
				}
			} catch (NumberFormatException ex) {
				System.out.println("Wrong input, only numbers are allowed, try again.");
				corectOption = false;
			}
		} while (!corectOption);
		System.out.println();
	}

	public static void chooseDateGuided(Scanner scan) {
		boolean corectOption = true;
		LocalDate today = LocalDate.now();
		do {
			try {
				int year = 2022;
				int month = 10;
				int day = 01;
				// Starts by asking for the year
				System.out.println("Select one option\nChoose the year");
				System.out.println("1 - 2022\n2 - 2023\n3 - 2024");
				// Scans the line and converts the input in an integer
				String input = scan.nextLine();
				input = input.charAt(0) + "";
				int option = Integer.parseInt(input);
				// Allocates the correct value
				switch (option) {
				case 1:
					break;
				case 2:
					year = 2023;
					break;
				case 3:
					year = 2024;
					break;
				default:
					throw new NumberFormatException();
				}
				// Continues by asking for the month
				System.out.println("Choose the month");
				System.out.println(" 1 - January\n 2 - February\n 3 - March\n 4 - April\n 5 - May\n 6 - June"
						+ "\n 7 - July\n 8 - August\n 9 - September\n10 - October\n11 - November\n12 - December");
				// Scans the line and converts the input in an integer
				input = scan.nextLine();
				input = input.charAt(0) + "";
				option = Integer.parseInt(input);
				// Allocates the correct value
				if (option > 0 && option < 13) {
					month = option;
				} else {
					throw new NumberFormatException();
				}
				// Finally, asks for the day
				System.out.println("Choose the day");
				// Scans the line and converts the input in an integer
				input = scan.nextLine();
				input = input.substring(0, 2);
				option = Integer.parseInt(input);
				// Allocates the correct value
				if (option > 0 && option < 29) {
					day = option;
				} else if (option > 28 && option < 32 && month != 2) {
					day = option;
				} else {
					throw new NumberFormatException();
				}
				LocalDate date = LocalDate.of(year, month, day);
				long days = today.until(date, ChronoUnit.DAYS);
				System.out.println("There are " + days + " from today until " + date);
			} catch (NumberFormatException ex) {
				System.out.println("Wrong input, try again.");
				corectOption = false;
			}
		} while (!corectOption);
	}

}
