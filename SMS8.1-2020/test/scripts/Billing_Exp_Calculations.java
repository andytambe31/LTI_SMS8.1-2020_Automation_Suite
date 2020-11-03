package scripts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.text.DefaultEditorKit.CutAction;

import utility.DateFunctions;

public class Billing_Exp_Calculations {

	// Billing Parameters
	String currentBillingMonth = "3";// tstParameters.getBillingCalendar();
	String currentBillingYear = "2019";

	String startDate = "01-03-2019";
	double taxPercentage = 10.0;
	double unitPrice = 1000.1111;
	int noOfUnits = 2;
	// String transaction1= ;
	// String trans1Date;
	String transaction2 = "Suspend";
	String trans2Date = "12-03-2019";
	/*
	 * String transaction3; String trans3Date; String transaction4; String
	 * trans4Date;
	 */
	String billAmt;
	String calculatedTax;
	String totalAmt;

	/*
	 * String cGST; String iGST, String utGST; String sGST;
	 */

	public void calculations() throws ParseException {
		// Class ref.
		TestParameters tstParameters = new TestParameters();
		DateFunctions dateFunc = new DateFunctions();

		// Stores- Transaction Level Data;
		List transList = new ArrayList();
		List allTransList = new ArrayList();

		// ********************Gain-Transaction***********************
		for (int i = 1; i <= noOfUnits; i++) {

			// Column 1 - Contract Number
			transList.add("LContract");

			// Column 2 - Bill From
			String billFrom = startDate;
			transList.add(startDate);

			// Column 3 - Bill To

			String billTo = dateFunc.addDaysToDate(startDate,
					dateFunc.getNoOfDaysInMonth(currentBillingMonth, currentBillingYear));
			// String billTo = "16-03-2019";
			transList.add(billTo);

			// Column 4. - Amount
			double billAmount = this.calculateBillAmount(billFrom, billTo, unitPrice);
			transList.add(billAmount);

			// Column 5. - Tax
			transList.add(taxPercentage);

			// Column 6. - TaxAmount
			transList.add(this.calculateTaxAmount(billAmount, taxPercentage));

			// Gain transaction details added to alltransList
			allTransList.add(transList);
			transList = new ArrayList();
		}
		System.out.println(allTransList);

		// *************** Suspend - Transction ****************
		
		
	}

	public double calculateBillAmount(String billFrom, String billTo, double unitPrice) throws ParseException {

		// Class references
		DateFunctions dateFunc = new DateFunctions();

		// Get no days between billFrom and billTo
		long daysBetBillFromAndTo = dateFunc.getNoOfDaysBetweenDates(billFrom, billTo) + 1;

		// Get month days
		long daysInMonth = dateFunc.getNoOfDaysInMonth(dateFunc.getMonth(billFrom), currentBillingYear);

		// Calculate billAmount
		double billAmount = (double) ((1.0 * unitPrice) * (1.0 * daysBetBillFromAndTo / daysInMonth));

		Double toBeTruncated = new Double(billAmount);

		Double truncatedDouble = BigDecimal.valueOf(toBeTruncated).setScale(3, RoundingMode.HALF_UP).doubleValue();

		return truncatedDouble;

	}

	public double calculateTaxAmount(double billAmount, double taxPercentage) {

		Double toBeTruncated = new Double((billAmount * taxPercentage) / (100));

		Double truncatedDouble = BigDecimal.valueOf(toBeTruncated).setScale(3, RoundingMode.HALF_UP).doubleValue();

		return truncatedDouble;
	}

	public static void main(String[] args) throws ParseException {

		Billing_Exp_Calculations bec = new Billing_Exp_Calculations();
		// bec.calculateBillAmount("01-03-2019", "31-03-2019", 1000);

		bec.calculations();
	}
}
