package scripts;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utility.QueryRunner;
import utility.ReadFromExcel;
import utility.WriteToExcelFile;

public class ComparePreAndPostBilling {

	// Excel paths
	String pathToExcel = "./test/resources/data/";
	String fileName = "preMigrationData.xlsx";

	public boolean compareBilling() throws IOException, ClassNotFoundException, SQLException {
		// Class references
		ReadFromExcel read = new ReadFromExcel();
		WriteToExcelFile write = new WriteToExcelFile();
		QueryRunner qr = new QueryRunner();
		TestParameters tstParameters = new TestParameters();

		// Maps and List
		Map<String, List> pre = new HashMap<String, List>();
		List<String> preList = new ArrayList<String>();
		Map<String, List> post = new HashMap<String, List>();
		List<String> postList = new ArrayList<String>();
		List<List> parentResultList = new ArrayList<List>();
		List<String> childResultList = new ArrayList<String>();

		// Excel
		String sheetName = "preMigration";
		String sheetNameForQuery = "BillingQuery";
		if (tstParameters.getEntity().equals("India")) {
			sheetName = "preMigration_India";
			sheetNameForQuery = "BillingQuery_India";
		}

		// Flags
		boolean passFlag = false;
		boolean tripFlag = true;

		// Step 1. - Get the Pre Migration billing data
		String[][] preMigrationBillingData = read.getExcelData(pathToExcel + fileName, sheetName);

		// Create hMap of preMigration data
		for (int i = 0; i < preMigrationBillingData.length; i++) {
			// ID
			String id = preMigrationBillingData[i][0];
			preList = new ArrayList<String>();
			for (int j = 0; j < 7; j++) {
				preList.add(preMigrationBillingData[i][j]);
			}
			pre.put(id, preList);
		}

		// Step 2. - Get the Post Migration billing data
		String query = read.getExcelData(pathToExcel + "Query.xlsx", sheetNameForQuery)[0][0];
		String dbName = tstParameters.getDataBaseName();
		String serverLink = tstParameters.getDatabaseURL();

		// Run query
		String[][] postMigrationBillingData = qr.runQuery(query, dbName, serverLink);

		// Create hMap of postMigration data
		for (int i = 0; i < postMigrationBillingData.length; i++) {
			// ID
			String id = postMigrationBillingData[i][0];
			postList = new ArrayList<String>();
			for (int j = 0; j < 7; j++) {
				postList.add(postMigrationBillingData[i][j]);
			}
			post.put(id, postList);
		}

		// Step 3. - Compare

		// Get key set
		Set<String> idSet = pre.keySet();

		for (String id : idSet) {
			// System.out.println("***********************************");
			List<String> preMigration = pre.get(id);
			List<String> postMigration = post.get(id);

			// Store result
			childResultList = new ArrayList<String>();
			childResultList.add(id);
			childResultList.add(preMigration.get(1));
			childResultList.add(preMigration.get(2));
			childResultList.add(preMigration.get(3));
			childResultList.add(preMigration.get(4));
			childResultList.add(preMigration.get(5));
			childResultList.add(preMigration.get(6));
			childResultList.add(postMigration.get(2));
			childResultList.add(postMigration.get(3));
			childResultList.add(postMigration.get(4));
			childResultList.add(postMigration.get(5));
			childResultList.add(postMigration.get(6));

			// Comparison
			int failCounter = 0;
			double preMigAmount, postMigAmount;
			DecimalFormat df = new DecimalFormat("#.000");
			for (int j = 1; j < 7; j++) {

				if (j > 3) { // For Bill amount,Tax Percent and Tax Amount
					preMigAmount = Double.parseDouble(preMigration.get(j));
					postMigAmount = Double.parseDouble(postMigration.get(j));

					// Compare
					if (preMigAmount == postMigAmount) {
						System.out.println("Passed: " + preMigration.get(j));
						System.out.println("Passed: " + postMigration.get(j));
						passFlag = true;
					} else {
						System.out.println("Failed: " + preMigration.get(j));
						System.out.println("Failed: " + postMigration.get(j));
						tripFlag = false;
						passFlag = false;
						failCounter++;
					}

				} else {

					// Compare

					if (preMigration.get(j).equals(postMigration.get(j))) {
						System.out.println("Passed: " + preMigration.get(j));
						System.out.println("Passed: " + postMigration.get(j));
						passFlag = true;
					} else {
						System.out.println("Failed: " + preMigration.get(j));
						System.out.println("Failed: " + postMigration.get(j));
						tripFlag = false;
						passFlag = false;
						failCounter++;
					}
				}

			}

			if (passFlag == true && failCounter == 0) {
				// System.out.println("Passed!!");
				childResultList.add("PASS");
			} else if (failCounter > 0) {
				// System.out.println("Failed!!");
				childResultList.add("FAIL");
			}

			parentResultList.add(childResultList);

		}

		// Write result in sheet
		write.writeBulkData("./test/resources/data/SmokeTestResultSheet.xlsx", "pre and post migration Billing ",
				parentResultList, 4, "1 2 3 4 5 6 7 8 9 10 11 12 13");

		ExcelCreator.setFilters("./test/resources/data/SmokeTestResultSheet.xlsx", "pre and post migration Billing ", 3,
				1, 4, 13);

		return tripFlag;

	}

	public static void main(String args[]) throws ClassNotFoundException, IOException, SQLException {
		ComparePreAndPostBilling comp = new ComparePreAndPostBilling();
		comp.compareBilling();
	}

}
