package scripts;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scripts.TestParameters;
import utility.ReadFromExcel;

public class StoreDataWithKeyFromInvoice {

	public Map<String, List> runQueryWithKey() throws ClassNotFoundException, SQLException, IOException {

		// Class references
		TestParameters tstParameter = new TestParameters();

		String driverPath = "./test/resources/drivers/";
		String pathToExcelFile = "./test/resources/data/";
		String fileName = "Query.xlsx";
		String key = null;
		String invoiceType = null;
		String dataBaseName =tstParameter.getDataBaseName() + ";";//
		String dataBaseURL = tstParameter.getDatabaseURL();

		String[][] retObjArr1;

		String[][] retObjArr3;

		ConvertDateFormat cd = new ConvertDateFormat();
		MoneyConverter moneycon = new MoneyConverter();
		TestParameters tp = new TestParameters();
		// Object to read from excel
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		retObjArr1 = readFromeExcel.getExcelData(pathToExcelFile + fileName, "QueryWithKey_" + tp.getEntity());
		retObjArr3 = readFromeExcel.getExcelData(pathToExcelFile + "pdfData.xlsx",
				"Format" + tstParameter.getEntity() + "Key");

		// Creating a HashMap
		Map<String, List> withKeyHMap = new HashMap<>();

		// creating object of class ReturnEntitySpecificInvoiceNo
		ReturnEntitySpecificInvoiceNo rtrn = new ReturnEntitySpecificInvoiceNo();

		// Creating a list
		List temp = new ArrayList();

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl = "jdbc:sqlserver:" + dataBaseURL + ";database=" + dataBaseName;//+";integratedSecurity=true;";
		Connection con = DriverManager.getConnection(connectionUrl, "sms81qc", "81smsqc@81smsqc");

		// Create Statement Object
		Statement stmt = con.createStatement();

		for (int i = 0; i < retObjArr1.length; i++) {
			// Execute the SQL Query. Store results in ResultSet
			ResultSet rs = stmt.executeQuery(retObjArr1[i][0]);

			// Select Keys for dataWithKey
			int k = 1, l = 0;
			int colNum = 3;

			// Count forData values
			for (int j = 0; j < retObjArr3.length; j++) {
				if (((String) retObjArr3[j][colNum]).equals("1")) {
					k++;
				}
			}

			// To Create array according to number of keys marked in excel as 1
			String[][] retObjArr2 = new String[k - 1][1];
			k = 0;

			for (int j = 0; j < retObjArr3.length; j++) {
				if (((String) retObjArr3[j][colNum]).equals("1")) {
					retObjArr2[k][l] = (String) retObjArr3[j][0];
					k++;
				}
			}

			// While Loop to iterate through all data and print results
			// if (!tstParameter.getEntity().equals("Bahrain")) {
			while (rs.next()) {
				List ls = new ArrayList();
				for (i = 3; i <= rs.getMetaData().getColumnCount(); i++) {

					// get the Key
					key = rs.getString(1);

					// get the InvoiceType
					invoiceType = rs.getString(2);

					// Stores the value in List
					if ((tstParameter.getEntity().equals("Bahrain") && i == 7)
							|| (tstParameter.getEntity().equals("Qatar") && i == 8)) {

						if (rs.getString(i).contains("-")) {
							String amt = rs.getString(i).replaceAll("-", "");
							amt = moneycon.convertToMoney(amt);
							amt = cd.encloseWithinBracketts(amt, rs.getString(2));
							ls.add(retObjArr2[i - 3][0] + amt);
						} else {
							ls.add(retObjArr2[i - 3][0] + cd
									.encloseWithinBracketts(moneycon.convertToMoney(rs.getString(i)), rs.getString(2)));
						}
					} else
						try {
							if ((tstParameter.getEntity().equals("Qatar") && (i == 3 || i == 6 || i == 7))) {

								ls.add(retObjArr2[i - 3][0] + cd.convertToDDMonYY(rs.getString(i)));

							} else
								ls.add(retObjArr2[i - 3][0] + cd.convertDate(rs.getString(i), i));
						} catch (java.lang.NullPointerException e) {
							// TODO: handle exception]
							if (retObjArr2[i - 3][0].contains("Contract Value")
									&& tstParameter.getEntity().equals("UAE")) {
								ls.add(retObjArr2[i - 3][0] + "0");
							} else
								ls.add("NULL");

						}

					// System.out.println(ls);

				}
				key = rtrn.invoiceTypeId(key, invoiceType);
				// System.out.println(key);
				withKeyHMap.put(key, ls);

				ls = new ArrayList();

			}
		} /*
			 * else { while (rs.next()) { List ls = new ArrayList();
			 * 
			 * // get the Key key = rs.getString(1);
			 * 
			 * // get the InvoiceType invoiceType = rs.getString(2);
			 * 
			 * for (int j = 0; j < retObjArr3.length; j++) {
			 * 
			 * if (!((String) retObjArr3[j][colNum]).equals("0")) {
			 * 
			 * int colNo = Integer.parseInt((String)retObjArr3[j][colNum]);
			 * 
			 * // Stores the value in List if (tstParameter.getEntity().equals("Bahrain") &&
			 * i == 7) { ls.add(retObjArr2[j][0] + cd
			 * .encloseWithinBracketts(moneycon.convertToMoney(rs.getString(j)),
			 * rs.getString(2))); } else ls.add(retObjArr2[j][0] +
			 * cd.convertDate(rs.getString(), i));
			 * 
			 * } //retObjArr2[k][l] ;
			 * 
			 * } } }
			 */

		// System.out.println(withoutKeyHMap);
		// closing DB Connection
		con.close();

		// returns the HashMap
		// System.out.println("----------------WithKey Data----------------------"+
		// withKeyHMap);
		return withKeyHMap;

	}

}
