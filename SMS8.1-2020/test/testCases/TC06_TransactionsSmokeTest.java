package testCases;

import org.testng.annotations.Test;

//import pages.BuildingNotesPopUp;
//import pages.BuildingPage;
import pages.CommonElements;
//import pages.ContactNotesPopUp;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import pages.SMSConfirmPopUp;
import pages.SearchContractPage;
import pages.TransactionPage;
import scripts.AlertHandle;
import scripts.PreTestWindowCheck;
import scripts.ResultWriter;
import scripts.TestParameters;
import scripts.TimeStamp;
import utility.GetLoginIdForMethodName;
//import pages.SMSConfirmPopUp;
import utility.ReadFromExcel;
import utility.TakeScreenShot;
import utility.WindowSwitching;
import utility.WriteToExcelFile;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

@Test
public class TC06_TransactionsSmokeTest {

	TestParameters tstParameters = new TestParameters();
	CommonElements commonElements;
	GetLoginIdForMethodName loginId = new GetLoginIdForMethodName();
	TimeStamp ts = new TimeStamp();

	String driverPath = "./test/resources/drivers/";
	String mainWindowHandle = null;
	String currentWindowHandle = null;
	String previousWindowHandle = null;
	String methodName;

	ResultWriter rw;
	PreTestWindowCheck preTest;
	AlertHandle alert;

	WebDriver driver;
	String pathToExcelFile = "./test/resources/data/";
	String fileName = "TransactionData.xlsx";
	String fileResult = "/SmokeTestResultSheet.xlsx";

	WriteToExcelFile write = new WriteToExcelFile();

	// List of methodName
	List<String> methodNameList = new ArrayList<String>();
	int methodPointer = 0;

	@DataProvider(name = "DP1")
	public Object[][] createSpecificationGroup() throws IOException {

		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "Data");
		return retObjArr;

	}

	@Test(priority = 1, dataProvider = "DP1")
	public void transactionType(String contractNo, String transactionType, String transactionDate, String reason,
			String subReason, String newPrice, String billingClass, String type)
			throws InterruptedException, IOException {
		HomePage homePage = new HomePage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		TransactionPage transactionPage = new TransactionPage(driver);
		SearchContractPage searchContractPage = new SearchContractPage(driver);
		SMSConfirmPopUp smsConfirmPopUp = new SMSConfirmPopUp(driver);

		methodName = transactionType + "TS: " + ts.getCurrentTimeAndDate();
		if (tstParameters.getSmokeTestMode().equals("demo")) {
			Assert.assertTrue(true);
		}

		// Fail test if contract is not present
		if (contractNo.equals("")) {
			Assert.assertTrue(false);
		}

		// System.out.println(transactionType);
		// transactionType = "Resume";
		switch (transactionType) {

		case "Cancellation":
			homePage.clickCancellationContract();
			break;

		case "Suspend":
			homePage.clickSuspendContract();
			break;

		case "Resume":
			homePage.clickResumeContract();
			break;

		case "Renegotiate":
			homePage.clickRenegotiateContract();
			break;

		case "Price Adjustment":
			homePage.clickPriceAdjustmentContract();
			break;

		default:
			System.out.println("Transaction Type not found");
			break;

		}

		transactionPage.switchToMainFrame();
		transactionPage.clickContractSearchButton();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
		{

			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		searchContractPage.switchToFrameContainer();
		searchContractPage.searchAndSelectContract(contractNo);
		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}

		transactionPage.switchToMainFrame();

		switch (transactionType) {

		case "Cancellation":
			transactionPage.setCancelTransactionDate(transactionDate);
			break;

		case "Suspend":
			transactionPage.setSuspendTransactionDate(transactionDate);
			break;

		case "Resume":
			transactionPage.setResumeTransactionDate(transactionDate);
			break;

		case "Renegotiate":
			transactionPage.setRenegotiateTransactionDate(transactionDate);

			break;

		case "Price Adjustment":
			transactionPage.setPriceAdjustmentTransactionDate(transactionDate);

			break;

		default:
			System.out.println("Transaction Type not found");
			break;

		}

		Thread.sleep(1000);

		// suspendPage.setBothRadButton();

		if (transactionType.contentEquals("Renegotiate")) {
			Thread.sleep(1000);
			transactionPage.clickBillingClassButton();
			Thread.sleep(1000);
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			transactionPage.switchToAssociateFrame();
			transactionPage.selectBillingClass(billingClass);
			Thread.sleep(1000);
			transactionPage.clickSelectButton();
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			transactionPage.switchToMainFrame();

		}

		switch (transactionType) {

		case "Cancellation":
			transactionPage.clickReasonButton();
			Thread.sleep(1000);
			transactionPage.selectCancelReasonGrid(reason);
			Thread.sleep(1000);
			transactionPage.clickSubReasonButton();
			Thread.sleep(1000);
			transactionPage.selectCancelSubReasonGrid(subReason);
			break;

		case "Suspend":
			Thread.sleep(1000);
			transactionPage.clickReasonButton();
			Thread.sleep(1000);
			transactionPage.selectSuspendReasonGrid(reason);
			Thread.sleep(1000);
			break;

		case "Renegotiate":
			transactionPage.setRenegNewPrice(newPrice);
			Thread.sleep(1000);
			// transactionPage.setType(type);
			break;

		case "Price Adjustment":
			transactionPage.setNewPrice(newPrice);
			Thread.sleep(1000);
			transactionPage.clickNewPrice();
			Thread.sleep(1000);
			// transactionPage.setType(type);
			break;

		default:
			// System.out.println("Transaction Type not found");
			break;

		}

		if (!transactionType.equals("Renegotiate") && !transactionType.equals("Price Adjustment")) {
			Thread.sleep(1000);
			transactionPage.clickAddAllButton();
		}

		Thread.sleep(1000);

		while (true) {
			transactionPage.clickSaveButton();
			Thread.sleep(1000);

			if (transactionType.contentEquals("Renegotiate")) {
				windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
				{
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}
				smsConfirmPopUp.clickYesButton();
				windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
				{
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}
				transactionPage.switchToMainFrame();
			}
			if ((int) windowSwitching.handleCount() == 2) {
				break;
			}
		}

		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
		{
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		smsConfirmPopUp.clickOKButton();

		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		transactionPage.switchToMainFrame();

		try {
			String tran = null;
			int rowNum = 0;
			switch (transactionType) {

			case "Cancellation":
				tran = "Loss";
				rowNum = 9;
				break;

			case "Suspend":
				tran = "Suspend";
				rowNum = 3;
				break;

			case "Resume":
				tran = "Resume";
				rowNum = 5;
				break;

			case "Price Adjustment":
				tran = "Price Adjust";
				rowNum = 13;
				break;

			default:
				System.out.println("Transaction Type not found");
				break;

			}

			// contractNumber = contractType + unitNos;
			String unitNos = contractNo.substring(1, contractNo.length());
			String unit2, unit1 = unitNos;
			String unitCount = unitNos.replace(unitNos.substring(0, unitNos.length() - 2), "");
			unitCount = "0" + (Integer.parseInt(unitCount.substring(1, unitCount.length())) + 1);
			unitNos = unitNos.substring(0, unitNos.length() - 2);
			unitNos = unitNos + unitCount;
			unitCount = "0" + (Integer.parseInt(unitCount.substring(1, unitCount.length())) + 1);
			unit2 = unitNos;

			if (!transactionType.equals("Renegotiate")) {
				// ID
				write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx", "contractBilling",
						new String[] { tran + contractNo + unit1 }, rowNum, 1);
				write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx", "contractBilling",
						new String[] { tran + contractNo + unit2 }, rowNum + 1, 1);
				// flag
				write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx", "contractBilling",
						new String[] { "1" }, rowNum, 0);
				write.writeToSpecificPosition(pathToExcelFile + "contractBillingExpectedResult.xlsx", "contractBilling",
						new String[] { "1" }, rowNum + 1, 0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// transactionPage.checkSelectionType();
		methodPointer++;

	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("*******\tTransaction Smoke Test started\t*******");

		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		rw = new ResultWriter(driver, mainWindowHandle);
		preTest = new PreTestWindowCheck(driver);
		alert = new AlertHandle(driver);

		// populate the methodNameList
		methodNameList.add("suspendContracts");
		methodNameList.add("suspendContracts");
		methodNameList.add("suspendContracts");
		methodNameList.add("suspendContracts");
		methodNameList.add("suspendContracts");
	}

	@AfterClass
	public void afterClass() {
		// driver.quit();
	}

	@BeforeMethod
	public void clickOnContractMenu() throws IOException, InterruptedException {
		HomePage homePage = new HomePage(driver);
		WindowSwitching windowSwitch = new WindowSwitching(driver);
		SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);
		LoginPage loginPage = new LoginPage(driver);
		driver.switchTo().defaultContent();

		// Close all remaining windows
		preTest.closeWindows(mainWindowHandle);

		// Check if correct login Id is correct
		if (!loginId.checkIfLoginMatches(methodNameList.get(methodPointer).toString())) {
			Thread.sleep(1000);
			homePage.clickLogout();

			// Click on Yes
			windowSwitch.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
			{
				mainWindowHandle = windowSwitch.getMainWindowHandle();
				currentWindowHandle = windowSwitch.getCurrentWindowHandle();
				previousWindowHandle = windowSwitch.getPreviousWindowHandle();
			}

			smsCnfPopUp.clickYesButton();

			windowSwitch.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitch.getMainWindowHandle();
				currentWindowHandle = windowSwitch.getCurrentWindowHandle();
				previousWindowHandle = windowSwitch.getPreviousWindowHandle();
			}

			// Get correct loginIds;
			List<String> ls = loginId.getLogindId(methodNameList.get(methodPointer).toString());
			String loginID = ls.get(0);
			String password = ls.get(1);
			loginPage.loginToSMS2(loginID, password, tstParameters.getEntity());
			tstParameters.setLogin(loginID);

		}

		try {
			homePage.clickContractMenu();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@AfterMethod
	public void testCaseOver(ITestResult result) throws IOException {
		rw.setResults(result, methodName);
		commonElements = new CommonElements(driver);
		try {
			commonElements.clickOnHomeButton();
		} catch (UnhandledAlertException e) {
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
			commonElements.clickOnHomeButton();
		}

	}

}
