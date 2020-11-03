package scripts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
	
	public String getCurrentTimeAndDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		String dateTime = df.format(dateobj);
		return dateTime;
	}

}
