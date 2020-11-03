package testCases;

import org.testng.annotations.Test;

//import pages.BuildingNotesPopUp;
//import pages.BuildingPage;
import pages.CommonElements;
//import pages.ContactNotesPopUp;
import pages.HomePage;
import pages.InquiryPage;
import pages.LoginPage;
import pages.ProductPage;
import pages.SMSConfirmPopUp;
import pages.UnitPage;
import scripts.AlertHandle;
import scripts.DateConversion;
import scripts.ResultWriter;
import scripts.TestParameters;
import scripts.TimeStamp;
import utility.GetLoginIdForMethodName;
//import pages.SMSConfirmPopUp;
import utility.ReadFromExcel;
import utility.WindowSwitching;
import utility.WriteToExcelFile;
import utility.utilityFunctions;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class TC03_Inquiry {

	// Class references
	TestParameters tstParameters = new TestParameters();
	WriteToExcelFile write = new WriteToExcelFile();
	ReadFromExcel readFromeExcel = new ReadFromExcel();
	TimeStamp ts = new TimeStamp();
	// utilityFunctions uf = new utilityFunctions(driver);
	CommonElements commonElements;
	HomePage homePage;
	ResultWriter rw;// = new ResultWriter();
	UnitPage unitInfPage;
	WindowSwitching windowSwitching;
	AlertHandle alert;
	List<String> methodNameList = new ArrayList<String>();
	GetLoginIdForMethodName loginId = new GetLoginIdForMethodName();
	int methodPointer = 0;
	public static int rowCount;
	int rowNum = 1;

	// Driver
	String driverPath = "./test/resources/drivers";
	String mainWindowHandle = null;
	String currentWindowHandle = null;
	String previousWindowHandle = null;
	WebDriver driver;

	// Excel paths
	String pathToExcelFile = "./test/resources/data/";
	String fileName = "InquiryData.xlsx";

	// Misc
	String methodName = null;

	@DataProvider(name = "DP1")
	public Object[][] createSpecificationGroup() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		String[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "Data");
		rowCount = retObjArr.length;
		return retObjArr;
	}

	@Test(priority = 1, dataProvider = "DP1")
	public void checkUnitsForContract(String menuName, String searchText, String searchDetails)
			throws IOException, InterruptedException, ParseException {

		try {
			// Method name
			methodPointer++;
			methodName = "checkUnitsForContract" + "TS: " + ts.getCurrentTimeAndDate();
			HomePage homePage = new HomePage(driver);
			TC03_Inquiry tc = new TC03_Inquiry();
			InquiryPage inquiryPage = new InquiryPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);
			DateConversion dc = new DateConversion();
			int priceInExcel = 0;
			
			menuName = "Units for a Building";

			// Step 1. - Navigate to the selected menu name
			System.out.print("Step 1: ");
			System.out.println("-----------------------" + menuName + "-----------------------");

			switch (menuName) {

			case "Units for a Contract":
				homePage.clickUnitsForContract();
				break;

			case "Unit Transactions":
				homePage.clickUnitTransactions();
				break;

			case "Units for a Building":
				homePage.clickUnitsForBuilding();
				break;

			case "Contracts For A Customer":
				homePage.clickContractsForCustomer();
				break;

			case "Contract Price History":
				homePage.clickContractPriceHistory();
				break;

			case "Invoices For A Customer":
				homePage.clickInvoicesForCustomer();
				break;

			case "Invoices For A Contract":
				homePage.clickInvoicesForContract();
				break;

			case "Invoices For A Unit":
				homePage.clickInvoicesForUnit();
				break;

			default:
				System.out.println("Menu Name not found");
			}

			inquiryPage.switchToMainFrame();
			Thread.sleep(1000);

			// Strp 2 Select on the basis of Search Type
			System.out.print("Step 2: ");
			System.out.println("-----------------------" + "Searching by search text..." + "-----------------------");

			switch (menuName) {

			case "Units for a Contract":
			case "Contract Price History":
			case "Invoices For A Contract":
				inquiryPage.setContractNumber(searchText);

				// Uncheck annual price checkbox
				Thread.sleep(5000);
				inquiryPage.uncheckAnnualPriceCheckbox();
				break;

			case "Contracts For A Customer":
			case "Invoices For A Customer":
				inquiryPage.setCustomerNumber(searchText);
				break;

			case "Unit Transactions":
			case "Invoices For A Unit":
				inquiryPage.setUnitNumber(searchText);
				break;

			case "Units for a Building":
				inquiryPage.setBuildingNumber(searchText);
				break;

			}

			// Step 3. - Verification of data
			System.out.print("Step 3: ");
			System.out.println("-----------------------" + "Verification of data..." + "-----------------------");
			String result = inquiryPage.verifyData(menuName, searchText, searchDetails);

			write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data", new String[] { result }, rowNum,
					3);
			rowNum++;

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@BeforeClass
	public void beforeClass() throws IOException {
		System.out.println("*******\tContact Smoke Test started\t*******");

		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		methodNameList.add("checkUnitsForContract");
		methodNameList.add("checkUnitsForContract");
		methodNameList.add("checkUnitsForContract");
		methodNameList.add("checkUnitsForContract");
		methodNameList.add("checkUnitsForContract");
		methodNameList.add("checkUnitsForContract");
		methodNameList.add("checkUnitsForContract");
		methodNameList.add("checkUnitsForContract");
		methodNameList.add("checkUnitsForContract");
		methodNameList.add("checkUnitsForContract");
		methodNameList.add("checkUnitsForContract");

		rw = new ResultWriter(driver, mainWindowHandle);
		windowSwitching = new WindowSwitching(driver);

	}

	@AfterClass
	public void afterClass() {
		// driver.quit();
	}

	@BeforeMethod
	public void clickOnInquiryMenu() throws IOException, InterruptedException {

		HomePage homePage = new HomePage(driver);
		WindowSwitching windowSwitch = new WindowSwitching(driver);
		SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);
		LoginPage loginPage = new LoginPage(driver);
		driver.switchTo().defaultContent();

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
			homePage.clickInquiryMenu();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@AfterMethod
	public void testCaseOver(ITestResult result) throws IOException {
		rw.setResults(result, methodName);
		commonElements = new CommonElements(driver);
		// commonElements.clickOnHomeButton();
	}

}
