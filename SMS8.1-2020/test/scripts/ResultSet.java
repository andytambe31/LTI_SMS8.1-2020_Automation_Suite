package scripts;

public class ResultSet {

	// Row and Column of Smoke test result sheet
	int resultRow;
	int readRow;
	int readColumn;
	String readExcel = null;
	String readSheetName = null;

	// Contract scenario
	static int contractRow = 0;
	static int inquiryRow = 0;
	static int editContractRow = 0;
	static int taxedContractRow = 1;

	// Return excels row for result writing
	public int getResultRow(String methodName) {

		switch (methodName) {

		// 1. Building Smoke test
		case "createBuilding":
			return 15;
		case "editBuilding":
			return 16;
		case "deleteBuilding":
			return 17;
		case "defineDataInSideLinkBuilding":
			return 18;
		case "defineContactInBuilding":
			return 19;

		// 2. Unit Smoke test
		case "createMultipleUnits":
			return 21;
		case "editUnit":
			return 22;
		case "deleteUnit":
			return 23;
		case "defineDataInSideLinkForUnit":
			return 24;
		case "createCommercialUnit":
			return 25;

		// 3. Customer Smoke test
		case "createCustomer":
			return 27;
		case "editCustomer":
			return 28;
		case "deleteCustomer":
			return 29;
		case "defineDataInSideLinkCustomer":
			return 30;

		// 4. Contract smoke test
		case "createContracts":
			return 37;
		case "editContracts":
			return 38;
		case "noTaxedContracts":
			return 45;
		case "taxedContracts":
			return 46;

		// 5. Transaction smoke test
		case "Suspend":
			return 40;
		case "Renegotiate":
			return 44;
		case "Resume":
			return 41;
		case "Cancellation":
			return 42;
		case "Price Adjustment":
			return 43;

		// 6. O Billing smoke test
		case "generateBilling":
			return 48;

		// 7. Contact Smoke Test
		case "createContact":
			return 32;
		case "editContact":
			return 33;
		case "deleteContact":
			return 34;
		case "defineDataInSideLinkContact":
			return 35;

		// 8. Contract billing verification
		case "contractBillingVerfication":
			return 53;

		// 9 . Pre and post migration billing
		case "checkPreAndPostBilling_AnyEntity":
			return 48;

		// 10 . Admin Smoke Test
		case "modifyApplicationParameterAdmin":
			return 10;
		case "manageUserAdmin":
			return 11;
		case "verifyMenuPermissionAdmin":
			return 12;
		case "verifySMSReportPermissionAdmin":
			return 13;

		// 11. Organization Smoke test
		case "createCompany":
		case "createBranch":
		case "createTerritory":
			return 70;

		case "editCompany":
		case "editBranch":
		case "editTerritory":
			return 71;

		case "deleteCompany":
		case "deleteBranch":
		case "deleteTerritory":
			return 72;

		case "defineDataInSideLinkCompany":
		case "defineDataInSideLinkBranch":
		case "defineDataInSideLinkTerritory":
			return 73;

		// Inquiry
		case "checkUnitsForContract":
			return 39;

		// 11 . Product Smoke Test
		case "createSpecificationGroup":
			return 75;
		case "editSpecificationGroup":
			return 76;
		case "deleteSpecificationGroup":
			return 76;
		case "createSpecification":
			return 75;
		case "editSpecification":
			return 76;
		case "deleteSpecification":
			return 76;
		case "createProductGroup":
			return 75;
		case "editProductGroup":
			return 76;
		case "deleteProductGroup":
			return 76;
		case "createProductType":
			return 75;
		case "editProductType":
			return 76;
		case "deleteProductType":
			return 76;
		case "createModelType":
			return 75;
		case "editModelType":
			return 76;
		case "deleteModelType":
			return 76;
		case "createProduct":
			return 75;
		case "editProduct":
			return 76;
		case "deleteProduct":
			return 76;

		// 12 .Manual Invoice
		case "manualOInvoice":
			return 49;

		}

		return resultRow;

	}

	// Return excel file name
	public String getSourceSheetName(String methodName) {

		switch (methodName) {

		// 1. Building Smoke test
		case "createBuilding":
			return "Data";
		case "editBuilding":
			return "editBuilding";
		case "deleteBuilding":
			return "deleteBuilding";
		case "defineDataInSideLinkBuilding":
			return "dataForSideLinks";
		case "defineContactInBuilding":
			return "addContact";

		// 2. Unit Smoke test
		case "createMultipleUnits":
			return "Data";
		case "editUnit":
			return "editUnit";
		case "deleteUnit":
			return "deleteUnit";
		case "defineDataInSideLinkForUnit":
			return "dataForSideLinks";
		case "createCommercialUnit":
			return "commercialUnit";

		// 3. Customer Smoke test
		case "createCustomer":
			return "Data";
		case "editCustomer":
			return "editCustomer";
		case "deleteCustomer":
			return "deleteCustomer";
		case "defineDataInSideLinkCustomer":
			return "dataForSideLinks";

		// 4. Contract Smoke test
		case "createContracts":
			return "Data";
		case "editContracts":
			return "editData";
		case "noTaxedContracts":
			return "Data";
		case "taxedContracts":
			return "Data";

		// 5. Transaction Smoke test
		case "Suspend":
			return "Data";
		case "Renegotiate":
			return "Data";
		case "Resume":
			return "Data";
		case "Cancellation":
			return "Data";
		case "Price Adjustment":
			return "Data";

		// 6. O Billing smoke test
		case "generateBilling":
			return "Data";

		// 7. Contact Smoke Test
		case "createContact":
			return "editContact";
		case "editContact":
			return "editContact";
		case "deleteContact":
			return "deleteContact";
		case "defineDataInSideLinkContact":
			return "dataForSideLinks";

		// 8 . Admin Smoke Test
		/*
		 * case "modifyApplicationParameterAdmin": return "AP";
		 */
		case "manageUserAdmin":
			return "US";
		/*
		 * case "verifyMenuPermissionAdmin": return "MN"; case
		 * "verifySMSReportPermissionAdmin": return "RN";
		 */

		// 8. Contact Smoke Test
		case "checkUnitsForContract":
			return "Data";

		}

		return readSheetName;

	}

	// Return sheet name
	public String getExcel(String methodName) {

		if (methodName.contains("Building")) {
			return "BuildingData.xlsx";
		}
		if (methodName.contains("UnitsForContract")) {
			return "InquiryData.xlsx";
		}
		if (methodName.contains("Unit")) {
			return "unitData.xlsx";
		}

		if (methodName.contains("Customer")) {
			return "CustomerData.xlsx";
		}

		if (methodName.contains("Contract")) {
			return "contractData.xlsx";
		}

		if (methodName.equals("Suspend") || methodName.equals("Renegotiate") || methodName.equals("Resume")
				|| methodName.equals("Cancellation") || methodName.equals("Price Adjustment")) {
			return "TransactionData.xlsx";
		}

		if (methodName.contains("OBilling")) {
			return "obillingData.xlsx";
		}

		if (methodName.contains("Contact")) {
			return "contactData.xlsx";
		}

		if (methodName.contains("Admin")) {
			return "AdminData.xlsx";
		}

		return readExcel;

	}

	// Returns row for reading data
	public int getReadRow(String methodName) {

		switch (methodName) {
		// 1. Building Smoke test
		case "createBuilding":
			return 1;
		case "editBuilding":
			return 1;
		case "deleteBuilding":
			return 1;
		case "defineDataInSideLinkBuilding":
			return 1;
		case "defineContactInBuilding":
			return 1;

		// 2. Unit Smoke test
		case "createMultipleUnits":
			return 2;
		case "editUnit":
			return 1;
		case "deleteUnit":
			return 1;
		case "defineDataInSideLinkForUnit":
			return 1;
		case "createCommercialUnit":
			return 1;

		// 3. Customer Smoke test
		case "createCustomer":
			return 1;
		case "editCustomer":
			return 1;
		case "deleteCustomer":
			return 1;
		case "defineDataInSideLinkCustomer":
			return 1;

		// 4. Contract smoke test
		case "createContracts":
			contractRow = contractRow + 1;
			return contractRow;
		case "editContracts":
			editContractRow = editContractRow + 1;
			return editContractRow;
		case "noTaxedContracts":
			return 2;
		case "taxedContracts":
			readRow = taxedContractRow;
			taxedContractRow += 2;
			break;

		// 5. Transaction Smoke test
		case "Suspend":
			return 1;
		case "Renegotiate":
			return 2;
		case "Resume":
			return 3;
		case "Cancellation":
			return 4;
		case "Price Adjustment":
			return 5;

		// 6. O Billing smoke test
		case "generateBilling":
			return 1;

		// 7. Contact Smoke Test
		case "createContact":
			return 1;
		case "editContact":
			return 1;
		case "deleteContact":
			return 1;
		case "defineDataInSideLinkContact":
			return 1;

		// 8 . Admin Smoke Test
		case "manageUserAdmin":
			return 1;

		// 9. Inquiry smoke test
		case "checkUnitsForContract":
			return ++inquiryRow;
			
			//10. Inquiry smoke test
		case "manualOInvoice":
			return 49;
		}

		return readRow;

	}

	// Returns column for reading data
	public int getReadColumn(String methodName) {

		switch (methodName) {

		// 1. Building Smoke test
		case "createBuilding":
			return 0;
		case "editBuilding":
			return 0;
		case "deleteBuilding":
			return 0;
		case "defineDataInSideLinkBuilding":
			return 0;
		case "defineContactInBuilding":
			return 1;

		// 2. Unit Smoke test
		case "createMultipleUnits":
			return 1;
		case "editUnit":
			return 0;
		case "deleteUnit":
			return 0;
		case "defineDataInSideLinkForUnit":
			return 0;
		case "createCommercialUnit":
			return 0;

		// 3. Customer Smoke test
		case "createCustomer":
			return 0;
		case "editCustomer":
			return 0;
		case "deleteCustomer":
			return 0;
		case "defineDataInSideLinkCustomer":
			return 0;

		// 4. Contract smoke test
		case "createContracts":
			return 2;
		case "editContracts":
			return 0;
		case "noTaxedContracts":
			return 2;
		case "taxedContracts":
			return 2;

		// 5. Transaction Smoke test
		case "Suspend":
			return 0;
		case "Renegotiate":
			return 0;
		case "Resume":
			return 0;
		case "Cancellation":
			return 0;
		case "Price Adjustment":
			return 0;

		// 6. O Billing smoke test
		case "generateBilling":
			return 0;

		// 7. Contact Smoke Test
		case "createContact":
			return 0;
		case "editContact":
			return 0;
		case "deleteContact":
			return 0;
		case "defineDataInSideLinkContact":
			return 0;

		// 8 . Admin Smoke Test
		case "manageUserAdmin":
			return 0;

		case "checkUnitsForContract":
			return 0;
		}

		return readColumn;

	}

}
