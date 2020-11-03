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

import utility.ReadFromExcel;

public class StoreDataWithoutKeyFromInvoice {

	public Map<String, List> runQuery() throws ClassNotFoundException, SQLException, IOException {
		TestParameters tstParameters = new TestParameters();

		String driverPath = "./test/resources/drivers/";
		String pathToExcelFile = "./test/resources/data/";
		String fileName = "Query.xlsx";
		String key = null;
		String invoiceType = null;
		String dataBaseName = tstParameters.getDataBaseName() ;//
		String dataBaseURL = tstParameters.getDatabaseURL();// "//10.69.11.58\\qc"

		String[][] retObjArr1;
		// Object to read from excel
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		TestParameters tp = new TestParameters();
		// tp.setEntity("Bahrain");
		retObjArr1 = readFromeExcel.getExcelData(pathToExcelFile + fileName, "QueryWithoutKey_" + tp.getEntity());

		// Creating a HashMap
		Map<String, List> withoutKeyHMap = new HashMap<>();

		Map<String, List> withoutKeyData = new HashMap<>();

		// creating object of class ReturnEntitySpecificInvoiceNo
		ReturnEntitySpecificInvoiceNo rtrn = new ReturnEntitySpecificInvoiceNo();
		MoneyConverter mConv = new MoneyConverter();

		// Creating a list
		List temp = new ArrayList();

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl = "jdbc:sqlserver:" + dataBaseURL + ";database=" + dataBaseName+";integratedSecurity=true;";
		Connection con = DriverManager.getConnection(connectionUrl);//, "sms81qc", "81smsqc@81smsqc");

		// Create Statement Object
		Statement stmt = con.createStatement();

		for (int i = 0; i < retObjArr1.length; i++) {
			// Execute the SQL Query. Store results in ResultSet
			ResultSet rs = stmt.executeQuery(retObjArr1[i][0]);

			// While Loop to iterate through all data and print results

			while (rs.next()) {
				List ls = new ArrayList();
				for (i = 2; i <= rs.getMetaData().getColumnCount(); i++) {

					if (rs.getString(i).contains("-1")) {
						System.out.println();
					}
					// get the Key
					key = rs.getString(1);

					// get the InvoiceType
					invoiceType = rs.getString(2);

					String tem = rs.getString(i);
					if (((i == 9 || i == 10 || i == 12) && (tstParameters.getEntity().equals("Bahrain")))
							|| ((i == 8) && (tstParameters.getEntity().equals("Qatar"))) || ((i == 4 || i == 5|| i == 6) && (tstParameters.getEntity().equals("India")))) {// ||((i == 8 || i == 9 || i
																							// == 10 || i == 12) &&
																							// (!tstParameters.getEntity().equals("Bahrain"))))
																							// {
						tem = mConv.convertToMoney(tem);
						if (tstParameters.getEntity().equals("Qatar") && (i == 8) && (tem.contains("-"))) {
							tem = tem.replace("-", "");
							tem = "(" + tem + ")";
						}
					}

					// Enclose bill amount within round bracketts for UAE QC
					if (((tstParameters.getEntity().equals("UAE") && (i == 6 || i == 7 || i == 9))
							|| (tstParameters.getEntity().equals("Bahrain") && (i == 9 || i == 10 || i == 12))
							|| (tstParameters.getEntity().equals("QATAR") && (i == 8))) && (invoiceType.equals("2"))) {
						tem = mConv.encloseWithBracketts(tem);
					}

					if (tstParameters.getEntity().equals("Bahrain") && tem.equals("NULL")) {
						tem = "NULL";
					}

					// Stores the value in List
					ls.add(tem);
					// System.out.println(tem);

				}

				key = rtrn.invoiceTypeId(key, invoiceType);
				withoutKeyHMap.put(key, ls);

				ls = new ArrayList();

			}

		}

		// closing DB Connection
		con.close();

		// returns the HashMap
		return withoutKeyHMap;
	}

}
