package scripts;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;

import utility.QueryRunner;
import utility.ReadFromExcel;
import utility.WriteToExcelFile;

public class CreateDataForSmokeTest {

	// paths
	String pathToExcelFile = "./test/resources/data/";
	String query;

	// Ids
	String buildingId;
	String customerId;
	String contactId;
	double unitPrice = 1000.000;
	int noOfUnits = 2;

	public void writeLoginIds() throws IOException {

		// Class references
		ReadFromExcel read = new ReadFromExcel();
		WriteToExcelFile write = new WriteToExcelFile();
		TestParameters tstParameters = new TestParameters();

		/*
		 * tstParameters.setCompany("UAE QC");
		 * tstParameters.setDataBaseName("SMS8KWTPR1.1");
		 * tstParameters.setDatabaseURL("//10.69.11.58");
		 */

		// Get available loginId for entity
		String[][] loginIdsPass = read.getExcelData(pathToExcelFile + "preSmokeTestData.xlsx",
				"loginIdForEntity_" + tstParameters.getCompany());

		// Product module access
		int row2 = 23;
		String loginId2 = loginIdsPass[7][0];
		String pass2 = loginIdsPass[7][1];
		while (row2 != 41) {
			{
				// Id
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { loginId2 }, row2, 0);

				// Pass
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { pass2 }, row2, 1);
			}
			row2++;
		}

		// Check if LSA user is present
		if (!loginIdsPass[0][0].equals("null")) {

			String adminId = loginIdsPass[0][0];
			String adminPass = loginIdsPass[0][1];

			int row = 1;
			while (row != 43) {

				// Id
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { adminId }, row, 0);

				// Pass
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { adminPass }, row, 1);
				row++;
			}

		} else {

			// Write Sales Engineer Id for contact,building,unit and customer smoke test

			int row = 5;
			while (row != 23) {

				String loginId = loginIdsPass[1][0];
				String pass = loginIdsPass[1][1];

				// Id
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { loginId }, row, 0);

				// Pass
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { pass }, row, 1);

				row++;
			}

			// Write AR Billing for contract smoke test

			row = 23;
			while (row != 28) {

				String loginId = loginIdsPass[5][0];
				String pass = loginIdsPass[5][1];

				// Id
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { loginId }, row, 0);

				// Pass
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { pass }, row, 1);

				row++;
			}

			// Write AR Billing for T Job smoke test
			row = 59;
			while (row != 61) {

				String loginId = loginIdsPass[5][0];
				String pass = loginIdsPass[5][1];

				// Id
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { loginId }, row, 0);

				// Pass
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { pass }, row, 1);

				row++;
			}

			// write Service Supervisor for Inquiry smoke test
			row = 28;
			String loginId = loginIdsPass[3][0];
			String pass = loginIdsPass[3][1];
			{
				// Id
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { loginId }, row, 0);

				// Pass
				write.writeToSpecificPosition(pathToExcelFile + "preSmokeTestData.xlsx", "LoginId",
						new String[] { pass }, row, 1);
			}
		}

	}

	public void writeDataInExcel() throws IOException, ClassNotFoundException, SQLException {

		// Class reference
		QueryRunner QR = new QueryRunner();
		ReadFromExcel read = new ReadFromExcel();
		WriteToExcelFile write = new WriteToExcelFile();
		TestParameters tstParameters = new TestParameters();

		// ****************** Generate test Login Id's *************
		if (!tstParameters.getEnvironment().equals("Production")) {
			this.writeLoginIds();
		}

		if (!tstParameters.getEnvironment().equals("Production")) {
			// Strings
			String[][] smokeTestData = read.getExcelData(pathToExcelFile + "Query.xlsx", "SmokeTestData");

			// ******************** Building Data *****************************

			query = smokeTestData[0][0]; // Query for building data stored at (1,0)

			// Run query
			String[][] buildingResultData = QR.runQuery(query, tstParameters.getDataBaseName(),
					tstParameters.getDatabaseURL());

			// Write data in Excel

			// Save BuildingId
			buildingId = buildingResultData[0][0];

			// Save branch
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][1] }, 1, 1);
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][1] }, 2, 1);

			// Save Building Type
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][2] }, 1, 3);
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][2] }, 2, 3);

			// Save Owner Type
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][3] }, 1, 4);
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][3] }, 2, 4);

			// Save Country
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][6] }, 1, 6);
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][6] }, 2, 6);

			// Save City
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][4] }, 1, 9);
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][4] }, 2, 9);

			// Save State
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][5] }, 1, 10);
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
					new String[] { buildingResultData[0][5] }, 2, 10);

			// Save Arabic Name
			if (buildingResultData[0][6].equals("0")) {
				write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
						new String[] { buildingResultData[0][7] }, 1, 7);
				write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
						new String[] { buildingResultData[0][7] }, 2, 7);
			} else {
				write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
						new String[] { "arabic name" }, 1, 7);
				write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "Data",
						new String[] { "arabic name" }, 2, 7);
			}

			// ******************** Customer Data *****************************

			query = smokeTestData[1][0]; // Query for customer data stored at (1,0)

			// Run query
			String[][] customerResultData = QR.runQuery(query, tstParameters.getDataBaseName(),
					tstParameters.getDatabaseURL());

			// Save CustomerId
			customerId = customerResultData[0][0];
			// tstParameters.setCustomerId(customerId);

			// Save branch
			write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
					new String[] { customerResultData[0][1] }, 1, 1);
			write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
					new String[] { customerResultData[0][1] }, 2, 1);

			// Save Customer Type
			write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
					new String[] { customerResultData[0][2] }, 1, 3);
			write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
					new String[] { customerResultData[0][2] }, 2, 3);

			// Save Country
			write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
					new String[] { customerResultData[0][5] }, 1, 6);
			write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
					new String[] { customerResultData[0][5] }, 2, 6);

			// Save City
			write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
					new String[] { customerResultData[0][3] }, 1, 9);
			write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
					new String[] { customerResultData[0][3] }, 2, 9);

			// Save State
			write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
					new String[] { customerResultData[0][4] }, 1, 10);
			write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
					new String[] { customerResultData[0][4] }, 2, 10);

			/*
			 * // Save Arabic Name if (customerResultData[0][6].equals("0")) {
			 * write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
			 * new String[] { customerResultData[0][6] }, 1, 7);
			 * write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
			 * new String[] { customerResultData[0][6] }, 2, 7); } else {
			 * write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
			 * new String[] { "arabic name" }, 1, 7);
			 * write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "Data",
			 * new String[] { "arabic name" }, 2, 7); }
			 */

			// ******************** Unit Data *****************************

			query = smokeTestData[2][0] + "'" + buildingId + "'"; // Query for customer data stored at (1,0)

			// Run query
			String[][] unitResultData = QR.runQuery(query, tstParameters.getDataBaseName(),
					tstParameters.getDatabaseURL());

			// Save Product and Sales Territory

			// Product
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "createUnit",
					new String[] { unitResultData[0][0] }, 1, 1);

			// Sales territory
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "createUnit",
					new String[] { unitResultData[0][1] }, 1, 2);

			// Save unit Type
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "editUnit",
					new String[] { unitResultData[0][2] }, 1, 1);

			// ************** Unit Data *******************

			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][0] }, 1, 2);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][0] }, 2, 2);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][0] }, 3, 2);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][0] }, 4, 2);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][0] }, 5, 2);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][0] }, 6, 2);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][0] }, 7, 2);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][0] }, 8, 2);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][0] }, 9, 2);

			// Sales territory

			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][1] }, 1, 3);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][1] }, 2, 3);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][1] }, 3, 3);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][1] }, 4, 3);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][1] }, 5, 3);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][1] }, 6, 3);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][1] }, 7, 3);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][1] }, 8, 3);
			write.writeToSpecificPosition(pathToExcelFile + "unitData.xlsx", "Data",
					new String[] { unitResultData[0][1] }, 9, 3);

			// ******************** Contract Data *****************************

			query = smokeTestData[3][0] + "'" + buildingId + "'"; // Query for customer data stored at (1,0)

			// Run query
			String[][] contractResultData = QR.runQuery(query, tstParameters.getDataBaseName(),
					tstParameters.getDatabaseURL());

			// Save Product and Sales Territory

			// Start Date
			GetStartDate sd = new GetStartDate();
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { sd.returnStartDate() }, 1, 4);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { sd.returnStartDate() }, 2, 4);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { sd.returnStartDate() }, 3, 4);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { sd.returnStartDate() }, 4, 4);

			// Bill from
			write.writeToSpecificPosition(pathToExcelFile + "obillingData.xlsx", "oManualInvoice",
					new String[] { sd.returnStartDate() }, 1, 3);
			write.writeToSpecificPosition(pathToExcelFile + "obillingData.xlsx", "oManualInvoice",
					new String[] { sd.returnStartDate() }, 2, 3);

			// Bill To
			String billTo = sd.returnStartDate();
			billTo = billTo.substring(2, billTo.length());
			billTo = "16" + billTo;
			write.writeToSpecificPosition(pathToExcelFile + "obillingData.xlsx", "oManualInvoice",
					new String[] { billTo }, 1, 4);
			write.writeToSpecificPosition(pathToExcelFile + "obillingData.xlsx", "oManualInvoice",
					new String[] { billTo }, 2, 4);

			// Sales Rep
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][0] }, 1, 5);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][0] }, 2, 5);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][0] }, 3, 5);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][0] }, 4, 5);

			// Exam Interval
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][1] }, 1, 10);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][1] }, 2, 10);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][1] }, 3, 10);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][1] }, 4, 10);

			// Exam Cycle
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][2] }, 1, 11);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][2] }, 2, 11);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][2] }, 3, 11);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][2] }, 4, 11);

			// Exam Day
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][3] }, 1, 12);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][3] }, 2, 12);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][3] }, 3, 12);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
					new String[] { contractResultData[0][3] }, 4, 12);

			// Price

			String price = String.valueOf(unitPrice);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data", new String[] { price }, 1, 6);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data", new String[] { "0" }, 2, 6);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data", new String[] { price }, 3, 6);
			write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data", new String[] { price }, 4, 6);

			// ******************** Inquiry Data *****************************
			/*
			 * // Run query String[][] inquiryResultData = QR.runQuery(query,
			 * tstParameters.getDataBaseName(), tstParameters.getDatabaseURL()); // Start
			 * Date GetStartDate gsd = new GetStartDate();
			 * write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
			 * new String[] { gsd.returnStartDate() }, 1, 3);
			 * write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
			 * new String[] { gsd.returnStartDate() }, 2, 3);
			 * write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
			 * new String[] { gsd.returnStartDate() }, 3, 3);
			 * write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
			 * new String[] { gsd.returnStartDate() }, 4, 3);
			 * 
			 * // Price
			 * 
			 * DecimalFormat dec = new DecimalFormat("#0.000"); price =
			 * String.valueOf(dec.format(unitPrice * noOfUnits));
			 * write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
			 * new String[] { dec.format(1500) }, 1, 5);
			 * write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
			 * new String[] { dec.format(0) }, 2, 5);
			 * write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
			 * new String[] { dec.format(0) }, 3, 5);
			 * write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
			 * new String[] { dec.format(1900) }, 4, 5);
			 */
			// ******************** Contact Data *****************************

			query = smokeTestData[4][0]; // Query for contact data stored at (1,0)

			// Run query
			String[][] contactResultData = QR.runQuery(query, tstParameters.getDataBaseName(),
					tstParameters.getDatabaseURL());

			// ContactID
			contactId = contactResultData[0][0];

			// Country
			write.writeToSpecificPosition(pathToExcelFile + "contactData.xlsx", "Data",
					new String[] { contactResultData[0][3] }, 1, 3);
			write.writeToSpecificPosition(pathToExcelFile + "contactData.xlsx", "Data",
					new String[] { contactResultData[0][3] }, 2, 3);

			// City
			write.writeToSpecificPosition(pathToExcelFile + "contactData.xlsx", "Data",
					new String[] { contactResultData[0][1] }, 1, 5);
			write.writeToSpecificPosition(pathToExcelFile + "contactData.xlsx", "Data",
					new String[] { contactResultData[0][1] }, 2, 5);

			// State
			write.writeToSpecificPosition(pathToExcelFile + "contactData.xlsx", "Data",
					new String[] { contactResultData[0][2] }, 1, 6);
			write.writeToSpecificPosition(pathToExcelFile + "contactData.xlsx", "Data",
					new String[] { contactResultData[0][2] }, 2, 6);

			// Tax Code

			if (tstParameters.getEntity().equals("UAE")) {
				write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data", new String[] { "VAT" }, 1,
						15);
				write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
						new String[] { "Business TAX" }, 3, 15);
			} else if (tstParameters.getEntity().equals("India")) {
				write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data", new String[] { "VAT" }, 1,
						15);
				write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
						new String[] { "Service Tax" }, 3, 15);
			} else if (tstParameters.getEntity().equals("China")) {
				write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
						new String[] { "Service Tax" }, 3, 15);
				write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
						new String[] { "Value Added Tax 17%" }, 1, 15);
			}

			// ******************** Transaction Data *****************************

			query = smokeTestData[5][0]; // Query for transaction data stored at (1,0)

			// Run query
			String[][] transactionResultData = QR.runQuery(query, tstParameters.getDataBaseName(),
					tstParameters.getDatabaseURL());

			// Suspend Reason
			write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
					new String[] { transactionResultData[0][0] }, 1, 3);

			// Renegotiate Billing Class
			write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
					new String[] { transactionResultData[0][1] }, 2, 6);

			// Cancellation Reason
			write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
					new String[] { transactionResultData[0][2] }, 4, 3);

			// Cancellation SUb Reason
			write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
					new String[] { transactionResultData[0][3] }, 4, 4);

			// Transacton Data
			write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
					new String[] { "01" + sd.returnMonthYear() }, 1, 2);
			write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
					new String[] { "16" + sd.returnMonthYear() }, 2, 2);
			write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
					new String[] { "16" + sd.returnMonthYear() }, 3, 2);
			write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
					new String[] { "16" + sd.returnMonthYear() }, 4, 2);
			write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
					new String[] { "16" + sd.returnMonthYear() }, 5, 2);

			// *********** Organization data *******************

			try {
				query = smokeTestData[6][0]; // Query for contact data stored at (1,0)

				// Run query
				String[][] orgResultData = QR.runQuery(query, tstParameters.getDataBaseName(),
						tstParameters.getDatabaseURL());

				// ******** Company data *******

				// Country
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createCompany",
						new String[] { orgResultData[0][0] }, 1, 6);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createCompany",
						new String[] { orgResultData[0][0] }, 2, 6);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "editCompany",
						new String[] { orgResultData[0][0] }, 1, 3);

				// Currency
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createCompany",
						new String[] { orgResultData[0][1] }, 1, 7);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createCompany",
						new String[] { orgResultData[0][1] }, 2, 7);

				// ****** Branch data ********
				// Country
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][0] }, 1, 12);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][0] }, 2, 12);

				// State
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][5] }, 1, 13);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][5] }, 2, 13);

				// City
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][6] }, 1, 14);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][6] }, 2, 14);

				// Currency
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][1] }, 1, 7);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][1] }, 2, 7);

				// TimeZone
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][3] }, 1, 8);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][3] }, 2, 8);

				// Rate
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][2] }, 1, 5);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][2] }, 2, 5);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][2] }, 1, 6);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createBranch",
						new String[] { orgResultData[0][2] }, 2, 6);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "editBranch",
						new String[] { orgResultData[0][2] }, 1, 3);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "editBranch",
						new String[] { orgResultData[0][2] }, 1, 4);
				// Role
				/*
				 * write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx",
				 * "createEmployee", new String[] { orgResultData[0][8] }, 1, 2);
				 * write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx",
				 * "createEmployee", new String[] { orgResultData[0][8] }, 2, 2);
				 */

				// ****** Territory data ********

				// Territory Type filter
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createTerritory",
						new String[] { orgResultData[0][7] }, 1, 2);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createTerritory",
						new String[] { orgResultData[0][7] }, 2, 2);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "editTerritory",
						new String[] { orgResultData[0][7] }, 1, 1);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "territorySideLinks",
						new String[] { orgResultData[0][7] }, 1, 1);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "deleteTerritory",
						new String[] { orgResultData[0][7] }, 1, 2);

				// Territory filter
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createTerritory",
						new String[] { orgResultData[0][7] }, 1, 5);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "createTerritory",
						new String[] { orgResultData[0][7] }, 2, 5);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "editTerritory",
						new String[] { orgResultData[0][7] }, 1, 6);
				write.writeToSpecificPosition(pathToExcelFile + "organizationData.xlsx", "territorySideLinks",
						new String[] { orgResultData[0][7] }, 1, 6);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		/*
		 * // T Billing TJob data write.writeToSpecificPosition(pathToExcelFile +
		 * "tJobData.xlsx", "Data", new String[] { "01" + sd.returnTBillingMonthYear()
		 * }, 1, 2); write.writeToSpecificPosition(pathToExcelFile + "tJobData.xlsx",
		 * "Data", new String[] { "22" + sd.returnTBillingMonthYear() }, 1, 3);
		 */

	}

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {

		CreateDataForSmokeTest cd = new CreateDataForSmokeTest();
		cd.writeDataInExcel();

	}

}
