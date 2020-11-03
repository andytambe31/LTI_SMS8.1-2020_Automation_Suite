package testCases;

import org.testng.annotations.Test;

import junit.framework.Assert;
//import pages.CustomerNotesPopUp;
import pages.CustomerPage;
import pages.BuildingNotesPopUp;
import pages.BuildingPage;
import pages.CommonElements;
import pages.ContactNotesPopUp;
import pages.CustomerNotesPopUp;
import pages.HomePage;
import pages.LoginPage;
import pages.SMSConfirmPopUp;
import pages.UnitPage;
import scripts.PreTestWindowCheck;
import scripts.ResultWriter;
import scripts.TestParameters;
import scripts.TimeStamp;
import utility.GetLoginIdForMethodName;
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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class TC05_CustomerSmokeTest {

	// Class objects
	TestParameters tstParameters = new TestParameters();
	TakeScreenShot takeSS;
	WriteToExcelFile write = new WriteToExcelFile();
	ReadFromExcel readFromeExcel = new ReadFromExcel();
	GetLoginIdForMethodName loginId = new GetLoginIdForMethodName();
	CommonElements commonElements;
	HomePage homePage;
	TimeStamp ts = new TimeStamp();
	ResultWriter rw;// = new ResultWriter();
	UnitPage unitInfPage;
	WindowSwitching windowSwitching;
	PreTestWindowCheck preTest;

	// Driver related
	WebDriver driver;
	String driverPath = "./test/resources/drivers/";
	String mainWindowHandle = null;
	String currentWindowHandle = null;
	String previousWindowHandle = null;

	// Excel related
	String fileResult = "SmokeTestResultSheet.xlsx";
	String pathToExcelFile = "./test/resources/data/";
	String fileName = "CustomerData.xlsx";
	String screenshotPath = "./test/resources/screenshot/";

	// Misc
	boolean contractFlag = true;
	String methodName = null;
	int createCustomerFlag = 0;

	// List of methodName
	List<String> methodNameList = new ArrayList<String>();
	int methodPointer = 0;

	@DataProvider(name = "DP1")
	public Object[][] createDataForCreateCustomer() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "Data");
		return retObjArr;
	}

	@DataProvider(name = "DP2")
	public Object[][] createDataForEditCustomer() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "editCustomer");
		return retObjArr;
	}

	@DataProvider(name = "DP3")
	public Object[][] createDataForDeleteCustomer() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "deleteCustomer");
		return retObjArr;
	}

	@DataProvider(name = "DP4")
	public Object[][] createDataForDefineDataInSideLink() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "dataForSideLinks");
		return retObjArr;
	}

	@DataProvider(name = "DP5")
	public Object[][] createDataForContactInCustomer() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "addContact");
		return retObjArr;
	}

	@Test(priority = 0, dataProvider = "DP1")
	public void createCustomer(String CustomerNumber, String branch, String CustomerName, String CustomerType,
			String ownerType, String addressLine1, String country, String ArabicName, String flag, String city,
			String state) throws Exception {

		HomePage homePage = new HomePage(driver);
		CustomerPage CustomerInfPage = new CustomerPage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		methodName = "createCustomer" + "TS: " + ts.getCurrentTimeAndDate();

		// Step 1. - Navigate to Customer Information Screen
		homePage.clickCustomerMenu();
		CustomerInfPage.switchToMainFrame();

		// Step 2. - Set Branch
		CustomerInfPage.clickSearchBranchButton();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
																			// window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		CustomerInfPage.switchToAssociateFrame(); // switch to associate grid

		CustomerInfPage.setBranch(branch);

		CustomerInfPage.clickSelectButton();

		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		CustomerInfPage.switchToMainFrame();

		// Step 3. - Set Customer Name
		CustomerInfPage.setCustomerName(CustomerName);

		// set PAN and GST No
		if (tstParameters.getEntity().equals("India")) {

			// if (flag.equals("1")) {
			CustomerInfPage.setPANNumber("PANN0TAPPL");
			CustomerInfPage.setGSTNumber("GSTNOTAPPLICABL");

			// }
			/*
			 * else { CustomerInfPage.setPANNumberDropDown("PANN0TAPPL");
			 * CustomerInfPage.setGSTNumberDropDown("GSTNOTAPPLICABL"); }
			 */
		}

		// Step 4. - Set Address Line 1
		CustomerInfPage.setAddressLine1(addressLine1);

		// Step 5. - Set Country
		CustomerInfPage.clickCountryButton();
		CustomerInfPage.setCountry(country);

		if (tstParameters.getEntity().equals("India")) {

			// Set State
			CustomerInfPage.setState(state);

			CustomerInfPage.clickCityButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switchwindow
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			CustomerInfPage.switchToAssociateFrame(); // switch to associate grid
			Thread.sleep(1000);

			// set City
			CustomerInfPage.setCity(city);
			Thread.sleep(1000);
			CustomerInfPage.clickSelectButton();

			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{ // For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			CustomerInfPage.switchToMainFrame();

		}

		//if (!ArabicName.equals("0") && !tstParameters.getEntity().equals("China")) {
			CustomerInfPage.setArabicName(ArabicName);
		//}

		// Step 6. - Click on Save button
		CustomerInfPage.clickSaveButton();

		// Step 7. - Save the Customer ID in Excelsheet
		Thread.sleep(1000);

		if (flag.equals("1")) {
			if (contractFlag == true) {
				write.writeExcel(pathToExcelFile + "contractData.xlsx", "Data", CustomerInfPage.getCustomerId());
				write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
						CustomerInfPage.getCustomerId(), 1, 1);
				write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
						CustomerInfPage.getCustomerId(), 2, 1);
				write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
						CustomerInfPage.getCustomerId(), 3, 1);
				write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
						CustomerInfPage.getCustomerId(), 4, 1);
				contractFlag = false;
			}
			write.writeExcel(pathToExcelFile + fileName, "Data", CustomerInfPage.getCustomerId());
			write.writeExcel(pathToExcelFile + fileName, "editCustomer", CustomerInfPage.getCustomerId());
			write.writeExcel(pathToExcelFile + fileName, "dataForSideLinks", CustomerInfPage.getCustomerId());
			write.writeToSpecificPosition(pathToExcelFile + fileName, "addContact", CustomerInfPage.getCustomerId(), 1,
					1);
			//write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data", CustomerInfPage.getCustomerId(),
					//6, 1);
			//write.writeToSpecificPosition(pathToExcelFile + "InquiryData.xlsx", "Data", CustomerInfPage.getCustomerId(),
					//10, 1);
		} else {
			write.writeExcel(pathToExcelFile + fileName, "Data", CustomerInfPage.getCustomerId());
			write.writeExcel(pathToExcelFile + fileName, "deleteCustomer", CustomerInfPage.getCustomerId());
		}

		// Data for Smoke test result sheet

		createCustomerFlag++;
		methodPointer++;

	}

	@Test(priority = 1, dataProvider = "DP2")
	public void editCustomer(String CustomerNumber, String AddressLine1) {

		HomePage homePage = new HomePage(driver);
		CustomerPage CustomerInfPage = new CustomerPage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		// Smoke test result sheet

		methodName = "editCustomer" + "TS: " + ts.getCurrentTimeAndDate();

		// Check if customer is created then proceed
		if (CustomerNumber.equals("")) {
			Assert.assertTrue(false);
		}

		// Step 1. - Navigate to Customer Information Screen
		homePage.clickCustomerMenu();
		CustomerInfPage.switchToMainFrame();

		// Step 2. - Search an existing Customer
		// CustomerInfPage.setSearchCustomerNumber(CustomerNumber);
		CustomerInfPage.clickSearchCustomerButton();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
		// window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		CustomerInfPage.setCustomerId(CustomerNumber);
		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}

		// Step 3. - Change the Customer type
		CustomerInfPage.switchToMainFrame();
		CustomerInfPage.getBranch();
		CustomerInfPage.setAddressLine1(AddressLine1);

		// Step 4. - Save the unit
		CustomerInfPage.clickSaveButton();
		methodPointer++;
	}

	@Test(priority = 3, dataProvider = "DP3")
	public void deleteCustomer(String CustomerNumber, String flag) {

		HomePage homePage = new HomePage(driver);
		CustomerPage CustomerInfPage = new CustomerPage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);

		// Smoke test result sheet
		methodName = "deleteCustomer" + "TS: " + ts.getCurrentTimeAndDate();

		// Check if customer is created then proceed
		if (CustomerNumber.equals("")) {
			Assert.assertTrue(false);
		}

		// Step 1. - Navigate to Customer Information Screen
		homePage.clickCustomerMenu();

		// Step 2. - Search a existing Customer
		CustomerInfPage.switchToMainFrame();
		CustomerInfPage.clickSearchCustomerButton();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
		// window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		CustomerInfPage.setCustomerId(CustomerNumber);
		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}

		// Step 3. - Click on <<Delete>> button
		CustomerInfPage.switchToMainFrame();
		CustomerInfPage.clickDeleteButton();

		// Step 5. - Click on <<Yes>> button
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
																			// window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}

		smsCnfPopUp.clickYesButton();

		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		CustomerInfPage.switchToMainFrame();
		methodPointer++;
	}

	@Test(priority = 2, dataProvider = "DP4")
	public void defineDataInSideLinkCustomer(String CustomerNumber, String sideLink, String subject, String note)
			throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		CustomerPage CustomerInfPage = new CustomerPage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		CustomerNotesPopUp CustomerNotes = new CustomerNotesPopUp(driver);

		// Smoke test result sheet
		methodName = "defineDataInSideLinkCustomer" + "TS: " + ts.getCurrentTimeAndDate();

		// Check if customer is created then proceed
		if (CustomerNumber.equals("")) {
			Assert.assertTrue(false);
		}

		// Step 1. - Navigate to Customer Information Screen
		homePage.clickCustomerMenu();

		// Step 2. - Search a existing Unit
		CustomerInfPage.switchToMainFrame();
		CustomerInfPage.clickSearchCustomerButton();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
		// window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		CustomerInfPage.setCustomerId(CustomerNumber);
		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}

		// Step 3. - Click on Customer note in the side links
		CustomerInfPage.clickOnSideLink(sideLink);
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
																			// window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		CustomerNotes.switchToFrameContainer();

		// Step5. - Enter note and subject
		CustomerNotes.setSubject(subject);
		CustomerNotes.setNote(note);

		// Step 6. - Save and Close
		CustomerNotes.clickOnSave();
		Thread.sleep(1000);
		CustomerNotes.clickOnClose();

		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}

		CustomerInfPage.switchToMainFrame();
		methodPointer++;
	}

	/*
	 * @Test(priority = 2, dataProvider = "DP5") public void
	 * defineContactInCustomer(String contactId, String customerId, String sideLink)
	 * throws InterruptedException {
	 * 
	 * HomePage homePage = new HomePage(driver); CustomerPage customerPage = new
	 * CustomerPage(driver); WindowSwitching windowSwitching = new
	 * WindowSwitching(driver); ContactNotesPopUp contactNotes = new
	 * ContactNotesPopUp(driver);
	 * 
	 * // Step 1. - Navigate to Building Information Screen
	 * homePage.clickBuildingMenu();
	 * 
	 * // Step 2. - Search a existing Unit customerPage.switchToMainFrame();
	 * customerPage.clickSearchCustomerButton();
	 * windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
	 * // window { // For getting window handles after switching mainWindowHandle =
	 * windowSwitching.getMainWindowHandle(); currentWindowHandle =
	 * windowSwitching.getCurrentWindowHandle(); previousWindowHandle =
	 * windowSwitching.getPreviousWindowHandle(); }
	 * customerPage.setCustomerId(customerId);
	 * windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle,
	 * mainWindowHandle);
	 * 
	 * { // For getting window handles after switching back mainWindowHandle =
	 * windowSwitching.getMainWindowHandle(); currentWindowHandle =
	 * windowSwitching.getCurrentWindowHandle(); previousWindowHandle =
	 * windowSwitching.getPreviousWindowHandle(); }
	 * 
	 * // Handles String customerPageHndl = currentWindowHandle;
	 * 
	 * // Step 3. - Click on Building note in the side links
	 * 
	 * customerPage.clickContacts(); Thread.sleep(2000);
	 * windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
	 * // window { // For getting window handles after switching mainWindowHandle =
	 * windowSwitching.getMainWindowHandle(); currentWindowHandle =
	 * windowSwitching.getCurrentWindowHandle(); previousWindowHandle =
	 * windowSwitching.getPreviousWindowHandle(); }
	 * contactNotes.switchToFrameContainer();
	 * 
	 * contactNotes.clickSearchContactPersonButton();
	 * 
	 * windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
	 * window { // For getting window handles after switching mainWindowHandle =
	 * windowSwitching.getMainWindowHandle(); currentWindowHandle =
	 * windowSwitching.getCurrentWindowHandle(); previousWindowHandle =
	 * windowSwitching.getPreviousWindowHandle(); }
	 * contactNotes.switchToFrameContainer();
	 * 
	 * contactNotes.setSearchText(contactId); contactNotes.selectSearchBy();
	 * contactNotes.searchContactButton();
	 * contactNotes.selectContactGrid(contactId);
	 * contactNotes.clickSelectContactButton();
	 * windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle,
	 * mainWindowHandle);
	 * 
	 * { // For getting window handles after switching back mainWindowHandle =
	 * windowSwitching.getMainWindowHandle(); currentWindowHandle =
	 * windowSwitching.getCurrentWindowHandle(); previousWindowHandle =
	 * windowSwitching.getPreviousWindowHandle(); }
	 * contactNotes.switchToFrameContainer(); contactNotes.checkAuthorizedCaller();
	 * contactNotes.clickSaveButtonContact(); Thread.sleep(1000);
	 * contactNotes.clickCloseButton();
	 * 
	 * // Set contactPage as current page currentWindowHandle =
	 * windowSwitching.setHandle(customerPageHndl); mainWindowHandle =
	 * tstParameters.getMainWindowHandle(); previousWindowHandle = null;
	 * 
	 * // Switch to mainframe customerPage.switchToMainFrame();
	 * 
	 * }
	 */
	@BeforeClass
	public void beforeClass() {
		System.out.println("*******	Customer Smoke Test started	*******");

		// Get driver value from
		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		takeSS = new TakeScreenShot(driver);
		rw = new ResultWriter(driver, mainWindowHandle);
		preTest = new PreTestWindowCheck(driver);

		// populate the methodNameList
		methodNameList.add("createCustomer");
		methodNameList.add("createCustomer");
		methodNameList.add("editCustomer");
		methodNameList.add("defineDataInSideLinkCustomer");
		methodNameList.add("deleteCustomer");
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
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
