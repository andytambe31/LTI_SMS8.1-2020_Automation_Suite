package scripts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversion {
	
	public String convertToDate(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date varDate=dateFormat.parse(date);
		dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
	    //System.out.println("Date :"+dateFormat.format(varDate));
	    String dateChange = dateFormat.format(varDate);
	    return dateChange;
	} 

	
}
