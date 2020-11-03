package scripts;

public class GetStartDate {

	public String returnStartDate() {

		TestParameters tp = new TestParameters();

		String month = tp.getOBillingMonth();
		String year = tp.getOBillingYear();
		String startDate;
		if (Integer.parseInt(month) > 9) {
			startDate = "01-" + month + "-" + year;
		} else {
			startDate = "01-0" + month + "-" + year;
		}

		return startDate;
		// System.out.println("Start Date :" + startDate);

	}

	public String returnMonthYear() {

		TestParameters tp = new TestParameters();

		String month = tp.getOBillingMonth();
		String year = tp.getOBillingYear();
		String startDate;
		if (Integer.parseInt(month) > 9) {
			startDate = "-" + month + "-" + year;
		} else {
			startDate = "-0" + month + "-" + year;
		}

		return startDate;
		// System.out.println("Start Date :" + startDate);

	}

}
