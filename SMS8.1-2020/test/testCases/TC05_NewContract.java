package testCases;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.annotations.Until;

import pages.AddContractLineItemPage;
import pages.CancelPage;
import pages.CommonElements;
import pages.ContactPage;
import pages.EditContractAgreementPage;
import pages.EditContractPage;
import pages.HomePage;
import pages.LoginPage;
import pages.NewContractPage;
import pages.ResumePage;
import pages.SMSConfirmPopUp;
import pages.ScheduleBillingPage;
import pages.SearchContractPage;
import pages.SuspendPage;
import pages.UnitPage;
import scripts.AlertHandle;
import scripts.CreateContractNumberIndia;
import scripts.PreTestWindowCheck;
import scripts.ResultWriter;
import scripts.TestParameters;
import scripts.TimeStamp;
import utility.PopUpHandle;
import utility.ReadFromExcel;
import utility.TakeScreenShot;
import utility.WindowSwitching;
import utility.WriteToExcelFile;

public class TC04_NewContract {

	// Class references
	TakeScreenShot takeSS;
	TestParameters tstParameters = new TestParameters();
	WriteToExcelFile write = new WriteToExcelFile();
	TimeStamp ts = new TimeStamp();
	CommonElements commonElements;
	ResultWriter rw;// = new ResultWriter(driver, mainWindowHandle);
	UnitPage unitInfPage;
	HomePage homePage;
	NewContractPage contractPage;
	AddContractLineItemPage contractLinePage;
	SMSConfirmPopUp smscnf;
	PopUpHandle popHndle;
	PreTestWindowCheck preTest;
	AlertHandle alert;

	// Excel path and file names
	String fileResult = "SmokeTestResultSheet.xlsx";
	String pathToExcelFile = "./test/resources/data/";
	String fileName = "contractData.xlsx";

	// driver variables
	String driverPath = "./test/resources/drivers/";
	String mainWindowHandle = null;// tstParameters.getMainWindowHandle();
	String currentWindowHandle = null;// tstParameters.getCurrentWindowHandle();
	String previousWindowHandle = null;// tstParameters.getPreviousWindowHandle();
	WebDriver driver;

	// Misc
	String screenshotPath = "./test/resources/screenshot/";
	String methodName = null;
	boolean LTypeContractCreated = false;
	boolean FTypeContractCreated = false;
	boolean MTypeContractCreated = false;
	boolean PTypeContractCreated = false;

	@DataProvider(name = "DP1")
	public Object[][] createDataForCreateData() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "Data");
		return retObjArr;
	}

	@DataProvider(name = "DP2")
	public Object[][] createDataForEditContract() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "editData");
		return retObjArr;

	}

	@DataProvider(name = "DP3")
	public Object[][] createDataForTaxedContract() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		String[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "Data");

		String[][] taxedContractType = new String[2][1];

		taxedContractType[0][0] = retObjArr[0][3];
		taxedContractType[1][0] = retObjArr[2][3];

		return taxedContractType;
	}

	@Test(priority = 1, dataProvider = "DP1")
	public void createContracts(String unitNos, String customer, String contractNumber, String contractType,
			String date, String salesTerritory, String price, String tenure, String roTenure, String tenureInDays,
			String examInterval, String examCycle, String examDay, String examHours, String otcbHours, String taxCode)
			throws InterruptedException, IOException {
		try {
			// Method name
			methodName = "createContracts" + "TS: " + ts.getCurrentTimeAndDate();

			// Check if necessary data is present for contract creation is present
			if (unitNos.equals("") && customer.equals("")) {
				Assert.assertTrue(false);
			}

			// Intializations
			HomePage homePage = new HomePage(driver);
			NewContractPage contractPage = new NewContractPage(driver);
			AddContractLineItemPage contractLinePage = new AddContractLineItemPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);
			SMSConfirmPopUp smscnf = new SMSConfirmPopUp(driver);
			WriteToExcelFile write = new WriteToExcelFile();
			EditContractAgreementPage editContarctAggPage = new EditContractAgreementPage(driver);
			SMSConfirmPopUp popUp;

			boolean flag = true;
			String unitCount = null;
			String unit1, unit2;

			// Storing unit nos in List
			List<String> unitNo = new ArrayList<String>();
			unitNo.add(unitNos);
			unit1 = unitNos;
			// System.out.println("1st Unit: " + unitNos);
			if (tstParameters.getEntity().equals("India")) {
				// Code

				CreateContractNumberIndia cNumber = new CreateContractNumberIndia();
				String contract = cNumber.contractNumber();
				contractNumber = contractType + contract;

			} else {
				contractNumber = contractType + unitNos;
			}

			// contractNumber = contractType + unitNos;
			unitCount = unitNos.replace(unitNos.substring(0, unitNos.length() - 2), "");
			unitCount = "0" + (Integer.parseInt(unitCount.substring(1, unitCount.length())) + 1);
			unitNos = unitNos.substring(0, unitNos.length() - 2);
			String amt = unitNos.replace("AT", "");
			unitNos = unitNos + unitCount;
			unitCount = "0" + (Integer.parseInt(unitCount.substring(1, unitCount.length())) + 1);
			// System.out.println("2nd Unit: " + unitNos);
			unitNo.add(unitNos);
			unit2 = unitNos;

			// Step 1. - Navigate to New Contract
			homePage.clickNewContractMenu();

			// Step 2. - Enter Contract no.s
			contractPage.setContractNumber(contractNumber);
			Thread.sleep(5000);

			// Check if loaded
			int tripPoint = 0;
			while (tripPoint != 100) {
				tripPoint++;
				try {
					if (contractPage.returnTransactionType() != 0) {
						break;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			// Step 3. - Enter date
			Thread.sleep(1000);
			contractPage.setDate(date);

			// Check if Contract Group is supposed to be entered
			if (true) {
				// btnContractGroup
				contractPage.clickContractGroupButton();
				contractPage.selectContractGroup(contractType);
			}

			// Step 4. - Enter unit No.
			Thread.sleep(1000);
			contractPage.setUnitNo(unitNo.get(0));

			// Wait for unit details to load
			contractPage.waitForBuildingDetails();

			// Step 5. - Set the schedule billing
			Thread.sleep(1000);
			if (tstParameters.getEntity().equals("UAE")) {
				// contractPage.setScheduleBilling();
				Thread.sleep(1000);
			}

			// Step 6. - Click on Sales territory button
			Thread.sleep(1000);
			contractPage.clickSalesTerritoryButton();
			Thread.sleep(1000);
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			contractPage.switchToAssociateFrame(); // switch to associate grid

			// Step 7. - Set the Sales Territory
			Thread.sleep(1000);
			contractPage.setSalesTerritory(salesTerritory);
			Thread.sleep(1000);
			contractPage.clickSelectButton();

			Thread.sleep(1000);

			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			contractPage.switchToMainFrame();

			// Step 8. - Set customer
			Thread.sleep(1000);
			contractPage.setCustomer(customer);

			// Set tax
			if (!taxCode.equals("")) {
				try {
					if (!contractPage.checkTaxableCheckBox()) {
						contractPage.checkTaxable();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				Thread.sleep(1000);
				contractPage.clickTaxButton();
				Thread.sleep(500);
				contractPage.selectTax(taxCode);
			} else if (contractPage.checkTaxableCheckBox()) {
				Thread.sleep(1000);
				contractPage.checkTaxable();
			}

			// Step 9. - Click on Save
			Thread.sleep(1000);
			contractPage.clickSaveButton();

			// Step 10. - Click on Ok
			Thread.sleep(1000);
			popUp = new SMSConfirmPopUp(driver);

			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			if (popUp.getMessage().contains(
					"Warning! You have entered a transaction date that is more than 11 months before current date.")) {
				popUp.clickYesButton2();
				windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
				{
					// For getting window handles after switching back
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}
				windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
				{
					// For getting window handles after switching
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}
			}

			contractPage.clickOKButton();
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			if (tstParameters.getEntity().equals("India")) {
				windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
				{
					// For getting window handles after switching
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}
				editContarctAggPage.switchToFrameContainer();
				// click on close button
				editContarctAggPage.clickCloseButton();

				windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
				{
					// For getting window handles after switching back
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}

			}

			// Step 11. - Select unit

			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			contractLinePage.switchToFrameContainer();

			// Select Unit nos
			contractLinePage.checkAllUnits();
			for (String str : unitNo) {
				// Step 11. - Select Unit
				contractLinePage.selectUnit(str);
				Thread.sleep(1000);
			}

			// Step 12. - Enter price
			contractLinePage.setPrice(price);

			// Step 13. - Set tenure
			contractLinePage.setTenure(tenure);

			// Step 14. - Set tenure
			contractLinePage.setRolloverTenure(roTenure);

			// parameter based so surrounding it in try/catch
			try {
				// Step 15. - Set tenure in days
				contractLinePage.setTenureInDays(tenureInDays);
			} catch (Exception e) {
				// TODO: handle exception
			}

			// Step 16. - Set exam interval
			if (!tstParameters.getCompany().equals("UAE Staging")) {
				contractLinePage.setExamInterval(examInterval);
				if (flag) {
					// if (windowSwitching.windowCount(2)) {
					windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
					{
						// For getting window handles after switching
						mainWindowHandle = windowSwitching.getMainWindowHandle();
						currentWindowHandle = windowSwitching.getCurrentWindowHandle();
						previousWindowHandle = windowSwitching.getPreviousWindowHandle();
					}
					smscnf.clickOKButton();
					windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
					{
						// For getting window handles after switching back
						mainWindowHandle = windowSwitching.getMainWindowHandle();
						currentWindowHandle = windowSwitching.getCurrentWindowHandle();
						previousWindowHandle = windowSwitching.getPreviousWindowHandle();
					}

					contractLinePage.switchToFrameContainer();
					flag = false;
				}
				// Step 17. - Set exam cycle
				contractLinePage.clickExamCycleButton();
				windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
				{
					// For getting window handles after switching
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}
				contractLinePage.setExamCycle(examCycle);
				contractLinePage.clickSelectButton();
				windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
				{
					// For getting window handles after switching back
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}

				// Step 18. - Set exam day
				Thread.sleep(1000);
				contractLinePage.setExamDay(examDay);
			}
			// Step 19. - Set Exam Hours
			contractLinePage.setExamHours(examHours);

			// Step 20. - Set OTCB Hours
			contractLinePage.setOTCBHours(otcbHours);
			try {
				if (tstParameters.getEntity().equals("India")) {
					contractLinePage.clickTypeButton();
					Thread.sleep(1000);
					contractLinePage.selectType("Standard");
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

			// Step21. - Click on Save
			contractLinePage.clickSaveButton();
			Thread.sleep(1000);

			String addContractLineItemHandle = currentWindowHandle;
			if (contractType.equals("F")) {
				// NIS allowance price screen
				addContractLineItemHandle = currentWindowHandle;

				windowSwitching.thirdWindowChange(currentWindowHandle, mainWindowHandle);// switch window
				{
					// For getting window handles after switching
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}

				// contractLinePage.switchToFrameContainer();
				contractLinePage.clickCloseButton();

				windowSwitching.fourWindowHandle(currentWindowHandle, mainWindowHandle, addContractLineItemHandle);// switch
																													// window
				{ // For getting window handles after switching
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}

				smscnf.clickYesButton2();
				currentWindowHandle = windowSwitching.setHandle(addContractLineItemHandle);
				contractLinePage.switchToFrameContainer();
				Thread.sleep(1000);

			}

			// Window handles set:
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = addContractLineItemHandle;

			// Contract saved successfully
			windowSwitching.thirdWindowChange(currentWindowHandle, mainWindowHandle);// switch window
			{ // For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			Thread.sleep(1000);
			contractLinePage.clickOKButton();
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{ // For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			// Step 22. - Close the window
			Thread.sleep(1000);

			contractLinePage.clickCloseButton();

			Thread.sleep(2000);
			// Handle pop up

			try {
				switch (contractType) {

				case "L":
					LTypeContractCreated = true;
					write.writeToSpecificPosition(pathToExcelFile + fileName, "Data", new String[] { contractNumber },
							1, 2);
					write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
							new String[] { contractNumber }, 1, 0);
					write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
							new String[] { contractNumber }, 2, 0);
					write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
							new String[] { contractNumber }, 3, 0);
					write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
							new String[] { contractNumber }, 1, 0);
					write.writeToSpecificPosition(pathToExcelFile + fileName, "editData",
							new String[] { contractNumber }, 1, 0);
					write.writeToSpecificPosition(pathToExcelFile + fileName, "editData",
							new String[] { contractNumber }, 1, 1);
					write.writeToSpecificPosition(pathToExcelFile + "obillingData.xlsx", "oManualInvoice",
							new String[] { contractNumber }, 1, 1);

					// Contract Billing verification
					int i = 1;
					while (i < 7) {
						write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
								"contractBilling", new String[] { contractNumber }, i, 2);
						i++;
						if (tstParameters.getEntity().equals("demo") && i == 2) {
							break;
						}
					}

					// ID
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "Gain" + contractNumber + unit1 }, 1, 1);
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "Gain" + contractNumber + unit2 }, 2, 1);
					// flag
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "1" }, 1, 0);
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "1" }, 2, 0);

					break;

				case "F":
					FTypeContractCreated = true;
					write.writeToSpecificPosition(pathToExcelFile + fileName, "Data", new String[] { contractNumber },
							2, 2);
					write.writeToSpecificPosition(pathToExcelFile + fileName, "editData",
							new String[] { contractNumber }, 2, 0);
					write.writeToSpecificPosition(pathToExcelFile + fileName, "editData",
							new String[] { contractNumber }, 2, 1);
					write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
							new String[] { contractNumber }, 2, 0);
					break;
				case "M":
					MTypeContractCreated = true;
					write.writeToSpecificPosition(pathToExcelFile + fileName, "Data", new String[] { contractNumber },
							3, 2);
					write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
							new String[] { contractNumber }, 4, 0);
					write.writeToSpecificPosition(pathToExcelFile + fileName, "editData",
							new String[] { contractNumber }, 3, 0);
					write.writeToSpecificPosition(pathToExcelFile + fileName, "editData",
							new String[] { contractNumber }, 3, 1);
					write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
							new String[] { contractNumber }, 3, 0);
					write.writeToSpecificPosition(pathToExcelFile + "obillingData.xlsx", "oManualInvoice",
							new String[] { contractNumber }, 2, 1);

					i = 7;
					while (i < 11) {
						write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
								"contractBilling", new String[] { contractNumber }, i, 2);
						i++;
					}

					// ID
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "Gain" + contractNumber + unit1 }, 7, 1);
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "Gain" + contractNumber + unit2 }, 8, 1);
					// flag
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "1" }, 7, 0);
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "1" }, 8, 0);
					break;
				case "P":
					PTypeContractCreated = true;
					write.writeToSpecificPosition(pathToExcelFile + fileName, "Data", new String[] { contractNumber },
							4, 2);
					write.writeToSpecificPosition(pathToExcelFile + "TransactionData.xlsx", "Data",
							new String[] { contractNumber }, 5, 0);

					write.writeToSpecificPosition(pathToExcelFile + fileName, "editData",
							new String[] { contractNumber }, 4, 0);
					write.writeToSpecificPosition(pathToExcelFile + fileName, "editData",
							new String[] { contractNumber }, 4, 1);

					write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
							new String[] { contractNumber }, 4, 0);

					i = 11;
					while (i < 15) {
						write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
								"contractBilling", new String[] { contractNumber }, i, 2);
						i++;
					}

					// ID
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "Gain" + contractNumber + unit1 }, 11, 1);
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "Gain" + contractNumber + unit2 }, 12, 1);
					// flag
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "1" }, 11, 0);
					write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx",
							"contractBilling", new String[] { "1" }, 12, 0);
					write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data",
							new String[] { contractNumber }, 4, 0);
					break;
				default:
					System.out.println("Contact Type not found ");
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			// Set window handles to New contract page
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.setHandle(mainWindowHandle);
			previousWindowHandle = null;

			contractPage.switchToMainFrame();

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 1, dataProvider = "DP2")
	public void editContracts(String contractNo, String customerInvoiceAttentn) throws InterruptedException {
		HomePage homePage = new HomePage(driver);
		EditContractPage editContractPage = new EditContractPage(driver);
		SearchContractPage searchContractPage = new SearchContractPage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		SMSConfirmPopUp smscnf = new SMSConfirmPopUp(driver);

		methodName = "editContracts" + "TS: " + ts.getCurrentTimeAndDate();

		if (!tstParameters.getSmokeTestMode().equals("demo")) {

			// Step 1. - Navigate to Edit contract
			homePage.clickEditContract();

			// Step 2. - Click on Contract search
			editContractPage.switchToMainFrame();
			Thread.sleep(1000);
			editContractPage.clickContractSearchButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			// Step 3. - Enter contract No
			searchContractPage.switchToFrameContainer();
			searchContractPage.searchAndSelectContract(contractNo);
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			// Step 4. - Edit the contract
			editContractPage.switchToMainFrame();
			editContractPage.setCustomerInvoiceAttention(customerInvoiceAttentn);
			// editContractPage.clickPaymentTypeButton();
			// editContractPage.selectPaymentTypeGrid(paymentType);

			Thread.sleep(1000);
			// Step 5. - Save
			editContractPage.clickSaveButton();
		}

	}

	@Test(priority = 2, dataProvider = "DP3")
	public void taxedContracts(String contractType) {

		methodName = "taxedContracts" + "TS: " + ts.getCurrentTimeAndDate();

		if (!tstParameters.getSmokeTestMode().equals("demo")) {

			// Get type of contract
			if (contractType.equals("L")) {
				if (LTypeContractCreated) {
					Assert.assertTrue(true);
				} else
					Assert.assertTrue(false);
			} else {
				if (MTypeContractCreated) {
					Assert.assertTrue(true);
				} else
					Assert.assertTrue(false);
			}
		}

	}

	@Test(priority = 1)
	public void noTaxedContracts() {

		methodName = "noTaxedContracts" + "TS: " + ts.getCurrentTimeAndDate();

		if (!tstParameters.getSmokeTestMode().equals("demo")) {

			if (PTypeContractCreated) {
				Assert.assertTrue(true);
			} else
				Assert.assertTrue(false);
		}

	}

	@BeforeClass
	public void setUp() {
		System.out.println("*******\tContract Smoke Test started\t*******");

		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		takeSS = new TakeScreenShot(driver);
		rw = new ResultWriter(driver, mainWindowHandle);
		preTest = new PreTestWindowCheck(driver);
		alert = new AlertHandle(driver);

	}

	@AfterClass
	public void browserClose() {
	}

	@BeforeMethod
	public void clickOnContractMenu() {
		// Close all remaining windows
		preTest.closeWindows(mainWindowHandle);

		// Click on Menu
		HomePage homePage = new HomePage(driver);
		driver.switchTo().defaultContent();
		homePage.clickContractMenu();
	}

	@AfterMethod
	public void testCaseOver(ITestResult result) throws IOException {
		rw.setResults(result, methodName);
		commonElements = new CommonElements(driver);
		try {
			commonElements.clickOnHomeButton();
		} catch (Exception e) {
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
			// commonElements.clickOnHomeButton();
		}

	}
}
