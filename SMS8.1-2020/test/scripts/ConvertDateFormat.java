package scripts;

public class ConvertDateFormat {

	public String replaceChar(String str, char ch, int index) {
		return str.substring(0, index) + ch + str.substring(index + 1);
	}

	public String convertDate(String Date,int i) {

		if (Date.contains(" Jan ") || Date.contains(" Feb ") || Date.contains(" Mar ") || Date.contains(" Apr ")
				|| Date.contains(" May ") || Date.contains(" Jun ") || Date.contains(" Jul ") || Date.contains(" Aug ")
				|| Date.contains(" Sep ") || Date.contains(" Oct ") || Date.contains(" Nov ")
				|| Date.contains(" Dec ")) {

			ConvertDateFormat cd = new ConvertDateFormat();
			Date = cd.replaceChar(Date, '-', 2);
			Date = cd.replaceChar(Date, '-', 6);

		}

		return Date;
	}

	public String encloseWithinBracketts(String amount, String invoiceType) {

		String retVal = amount;
		if (invoiceType.equals("2")) {

			retVal = "(" + retVal + ")";
			return retVal;
		} else if (invoiceType.equals("1"))
			return retVal;
		return retVal;

	}
	
	public String convertToDDMonYY(String date) {
		
		//input date fromat must be dd-Mon-YYYY
		String year = date.substring(9,date.length());
		String temp = date.substring(0,7);
		return temp+year;
	}

}
