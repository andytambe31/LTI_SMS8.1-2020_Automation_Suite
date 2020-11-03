package scripts;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MoneyConverter {

	TestParameters tstParameters = new TestParameters();

	public String convertToMoney(String amount) {

		// String money = amount;
		String money = amount;
		if (tstParameters.getEntity().equals("Bahrain") || tstParameters.getEntity().equals("India") ||tstParameters.getEntity().equals("Qatar")) {
			double money_double = Double.parseDouble(amount);
			// Convert to money
			// NumberFormat myFormat = NumberFormat.getInstance();
			// myFormat.setGroupingUsed(true);
			DecimalFormat decimalFormat = null;
			if (tstParameters.getEntity().equals("Bahrain")) {
				decimalFormat = new DecimalFormat("#.000");
			} //else if (tstParameters.getEntity().equals("Qatar")) {
			else {	
				decimalFormat = new DecimalFormat("#.00");
			}

			decimalFormat.setGroupingUsed(true);
			decimalFormat.setGroupingSize(3);

			// myFormat.format(money);
			decimalFormat.format(money_double);
			// System.out.println("Number Format : " + decimalFormat.format(money));
			String newMoney = String.valueOf(decimalFormat.format(money_double));
			// System.out.println(newMoney);
			return newMoney;
		} else {
			String newMoney = money;// String.valueOf(money);
			return newMoney;
		}

	}

	public String encloseWithBracketts(String amount) {
		if (amount.contains("-")) {
			amount = amount.replaceAll("-", "");
			amount = "(" + amount + ")";
		}
		return amount;
	}
}
