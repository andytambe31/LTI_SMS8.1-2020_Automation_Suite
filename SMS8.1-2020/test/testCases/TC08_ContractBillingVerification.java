package testCases;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pages.CommonElements;
import pages.HomePage;
import pages.UnitPage;
import scripts.ExcelCreator;
import scripts.PreTestWindowCheck;
import scripts.ResultWriter;
import scripts.TestParameters;
import utility.QueryRunner;
import utility.ReadFromExcel;
import utility.WindowSwitching;
import utility.WriteToExcelFile;
import utility.utilityFunctions;

public class TC13_ContractBillingVerification {

	// Class references
	TestParameters tstParameters = new TestParameters();
	WriteToExcelFile write = new WriteToExcelFile();
	ReadFromExcel readFromeExcel = new ReadFromExcel();
	CommonElements commonElements;
	HomePage homePage;

	// Excel path and file names
	String pathToExcelFile = "./test/resources/data/";

	// Misc
	String methodName = null;
	String query;

	// Counters
	int passCounter = 0;
	int entryPassCounter = 0;
	int columnPassCounter = 0;

	// List
	List passRowEntries = new ArrayList();
	List failRowEntries = new ArrayList();

	@Test(priority = 0)
	public void contractBillingVerfication() throws IOException, ClassNotFoundException, SQLException {

		methodName = "contractBillingVerfication";

		// Class references
		QueryRunner qr = new QueryRunner();

		// **** Get expected billing result ****
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		String[][] expectedBillingResult = readFromeExcel
				.getExcelData(pathToExcelFile + "contractBillingExpectedResult.xlsx", "contractBilling");

		// Start comparison

		for (int i = 0; i < expectedBillingResult.length; i++) {

			// if (expectedBillingResult[i][0].equals("1")) {
			if (true) {

				// Contract Number
				String contractNumber = expectedBillingResult[i][2];

				// Run Query
				query = (readFromeExcel.getExcelData(pathToExcelFile + "Query.xlsx", "actualBillingQuery"))[0][0] + "'"
						+ (expectedBillingResult[i][2]) + "'";

				// Hashmap
				Map<String, List> actualBillingResultHMap = ArrayToHashMapMaker(
						qr.runQuery(query, tstParameters.getDataBaseName(), tstParameters.getDatabaseURL()), 2, 7, 1);

				// List
				List<String> actualBillingList = actualBillingResultHMap.get(expectedBillingResult[i][1]);

				// Compare
				for (int col = 2; col < 8; col++) {

					if (actualBillingList.get(col - 2).equals(expectedBillingResult[i][col])) {
						System.out.println(
								"Passed: " + actualBillingList.get(col - 2) + "  " + (expectedBillingResult[i][col]));
					} else
						System.out.println(
								"Failed: " + actualBillingList.get(col - 2) + "  " + (expectedBillingResult[i][col]));
				}

			} else
				System.out.println("Transaction not added");

		}

	}

	@AfterMethod
	public void testCaseOver(ITestResult result) throws IOException {
		ResultWriter rw = new ResultWriter(tstParameters.getDriver(), methodName);
		rw.setResults(result, methodName);

	}

	public static HashMap ArrayToHashMapMaker(String[][] array, int startCol, int endCol, int keyColumn) {

		// Hashmap
		Map<String, List> hmap = new HashMap<String, List>();

		for (int j = 0; j < array.length; j++) {

			// List
			List<String> ls = new ArrayList<String>();

			for (int i = startCol; i <= endCol; i++) {
				ls.add(array[j][i]);
			}

			hmap.put(array[j][keyColumn], ls);

		}

		return (HashMap) hmap;

	}
}
