package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateFunctions {

	public int getNoOfDaysInMonth(String currentMonth, String currentYear) {
		Calendar calendar = Calendar.getInstance();
		// We'll set the date of the calendar to the following
		// date. We can use constant variable in the calendar
		// for months value (JANUARY - DECEMBER). Be informed that
		// month in Java started from 0 instead of 1.
		int year = Integer.parseInt(currentYear);
		int month = Integer.parseInt(currentMonth);
		int date = 1;
		// We have a new date of 2017-02-01
		calendar.set(year, month, date);
		int min = calendar.getMinimum(Calendar.DAY_OF_MONTH);
		// Here we get the maximum days for the date specified
		// in the calendar. In this case we want to get the number
		// of days for february 2017
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		//System.out.println("Max Day: " + maxDay);
		int totalNoOfDays = maxDay - min + 1;
		// System.out.println("Min-Max"+ (totalNoOfDays));

		// System.out.println("Max Day: " + maxDay);
		return totalNoOfDays;
	}

	public void getLastdateOfMonth(String currentMonth, String currentYear) {
		/*
		 * Calendar calendar = Calendar.getInstance(); int year =
		 * Integer.parseInt(currentYear); int month = Integer.parseInt(currentMonth);
		 * int date = 1; // We have a new date of 2017-02-01 calendar.set(year, month,
		 * date);
		 */

		String date = "0" + currentMonth + "/01" + "/" + currentYear;
		LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		convertedDate = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
		// int maxDay = calendar.(arg0)
		// System.out.println("Max Day: " + maxDay);
		// int totalNoOfDays=maxDay-min+1;
		// System.out.println("Min-Max"+ (totalNoOfDays));

		// System.out.println("Max Day: " + maxDay);
		// return totalNoOfDays;
	}

	public String addDaysToDate(String date, int days) {

		// Given Date in String format
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();

		try {
			// Setting the date to the given date
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Number of Days to add
		c.add(Calendar.DAY_OF_MONTH, days);
		String newDate = sdf.format(c.getTime());

		return newDate;
	}

	public int getNoOfDaysBetweenDates(String date_1, String date_2) {
		SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
		int days = 0;
		try {
			Date date1 = myFormat.parse(date_1);
			Date date2 = myFormat.parse(date_2);
			long diff = date2.getTime() - date1.getTime();
			days = (int) (diff / (1000 * 60 * 60 * 24));
			//System.out.println("Days: " + days);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return (int) days;
	}

	public String getMonth(String date_) throws ParseException {
		/*
		 * Date date = new Date(date_); LocalDate localDate =
		 * date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); int month =
		 * localDate.getMonthValue(); return Integer.toString(month);
		 */

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date parse = sdf.parse(date_);
		Calendar c = Calendar.getInstance();
		c.setTime(parse);
		//System.out.println(c.get(Calendar.MONTH) + c.get(Calendar.DATE) + c.get(Calendar.YEAR));
		return Integer.toString(c.get(Calendar.MONTH));
	}

	public void convertToDate(String date) throws ParseException {
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
	}

}
