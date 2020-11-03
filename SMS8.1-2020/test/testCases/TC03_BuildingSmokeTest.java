
package testCases;

import org.testng.annotations.Test;

import pages.BuildingNotesPopUp;
import pages.BuildingPage;
import pages.CommonElements;
import pages.ContactNotesPopUp;
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
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class TC03_BuildingSmokeTest {

	// Class references
	TestParameters tstParameters = new TestParameters();
	WriteToExcelFile write = new WriteToExcelFile();
	ReadFromExcel readFromeExcel = new ReadFromExcel();
	GetLoginIdForMethodName loginId = new GetLoginIdForMethodName();
	TimeStamp ts = new TimeStamp();
	CommonElements commonElements;
	HomePage homePage;
	ResultWriter rw;// = new ResultWriter();
	UnitPage unitInfPage;
	WindowSwitching windowSwitching;
	PreTestWindowCheck preTest;

	// driver variables
	String driverPath = "./test/resources/drivers/";
	String mainWindowHandle;// = null;//tstParameters.getMainWindowHandle();
	String currentWindowHandle;// = null;//tstParameters.getCurrentWindowHandle();
	String previousWindowHandle;// = null;//tstParameters.getPreviousWindowHandle();
	WebDriver driver;// = tstParameters.getDriver();

	// Excel path and file names
	String pathToExcelFile = "./test/resources/data/";
	String fileName = "BuildingData.xlsx";
	String fileResult = "SmokeTestResultSheet.xlsx";

	// Misc
	String methodName = null;
	int createBuildingFlag = 0;
	boolean writeToUnitExcelFile = true;

	// List of methodName
	List<String> methodNameList = new ArrayList<String>();
	int methodPointer = 0;

	@DataProvider(name = "DP1")
	public Object[][] createDataForCreateBuilding() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "Data");
		return retObjArr;
	}

	@DataProvider(name = "DP2")
	public Object[][] createDataForEditBuilding() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "editBuilding");
		return retObjArr;
	}

	@DataProvider(name = "DP3")
	public Object[][] createDataForDeleteUnit() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "deleteBuilding");
		return retObjArr;
	}

	@DataProvider(name = "DP4")
	public Object[][] createDataForDefineDataInSideLink() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "dataForSideLinks");
		return retObjArr;
	}

	@DataProvider(name = "DP5")
	public Object[][] createDataForContactInBuilding() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "addContact");
		return retObjArr;
	}

	@Test(priority = 0, dataProvider = "DP1")
	public void createBuilding(String buildingNumber, String branch, String buildingName, String buildingType,
			String ownerType, String addressLine1, String country, String ArabicName, String flag, String city,
			String state) throws Exception {

		// Set variable for Smoke Test result sheet
		methodName = "createBuilding" + "TS: " + ts.getCurrentTimeAndDate();
		createBuildingFlag++;

		HomePage homePage = new HomePage(driver);
		BuildingPage buildingInfPage = new BuildingPage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);

		// try {

		// Step 1. - Navigate to Building Information Screen
		homePage.clickBuildingMenu();
		buildingInfPage.switchToMainFrame();

		// Step 2. - Set Branch
		
		buildingInfPage.clickSearchBranchButton();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
																			// window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		buildingInfPage.switchToAssociateFrame(); // switch to associate grid

		buildingInfPage.setBranch(branch);
		Thread.sleep(1000);
		buildingInfPage.clickSelectButton();

		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		buildingInfPage.switchToMainFrame();

		// Step 3. - Set Building Name
		buildingInfPage.setBuildingName(buildingName);
		Thread.sleep(1000);

		// Step 4. - Set Building Type
		buildingInfPage.clickBuildingTypeButton();
		Thread.sleep(1000);
		buildingInfPage.setBuildingType(buildingType);
		Thread.sleep(1000);
		if (tstParameters.getEntity().equals("India")) {

			// if (flag.equals("1")) {
			buildingInfPage.setGSTNumber("GSTNOTAPPLICABL");
			// }
			/*
			 * else buildingInfPage.setGSTNumberDropDown("GSTNOTAPPLICABL");
			 */
		}
		Thread.sleep(1000);
		// Step 5. - Set Owner Type
		buildingInfPage.clickOwnerTypeButton();
		buildingInfPage.setOwnerType(ownerType);
		Thread.sleep(1000);

		// Step 6. - Set Address Line 1
		buildingInfPage.setAddressLine1(addressLine1);
		Thread.sleep(1000);

		// Step 7. - Set Country
		buildingInfPage.clickCountryButton();
		buildingInfPage.setCountry(country);
		Thread.sleep(1000);

		if (tstParameters.getEntity().equals("India") ) {

			// Set State
			buildingInfPage.setState(state);

			buildingInfPage.clickCityButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switchwindow
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			buildingInfPage.switchToAssociateFrame(); // switch to associate grid
			Thread.sleep(1000);

			// set City
			buildingInfPage.setCity(city);
			Thread.sleep(1000);
			buildingInfPage.clickSelectButton();

			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{ // For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			buildingInfPage.switchToMainFrame();

		}

		/*
		 * if (!ArabicName.equals("0")) { buildingInfPage.setArabicName(ArabicName); }
		 */
		// Step 8. - Click on Save button
		buildingInfPage.clickSaveButton();
		Thread.sleep(1000);

		// Step 9. - Save the Building ID in Excelsheet
		String[] buildingId = buildingInfPage.getBuildingId();

		if (buildingId.length == 0) {
			buildingId = new String[] { "" };
			Assert.assertTrue(false);
		}

		if (flag.equals("1")) {
			write.writeExcel(pathToExcelFile + fileName, "Data", buildingInfPage.getBuildingId());

			write.writeExcel(pathToExcelFile + fileName, "editBuilding", buildingId);
			write.writeExcel(pathToExcelFile + fileName, "dataForSideLinks", buildingId);
			write.writeToSpecificPosition(pathToExcelFile + fileName, "addContact", buildingId, 1, 1);

			while (writeToUnitExcelFile == true) {
				write.writeToSpecificPosition(pathToExcelFile + "/unitData.xlsx", "Data", buildingId, 1, 0);
				write.writeToSpecificPosition(pathToExcelFile + "/unitData.xlsx", "Data", buildingId, 2, 0);
				if (!tstParameters.getSmokeTestMode().equals("demo")) {
					write.writeToSpecificPosition(pathToExcelFile + "/unitData.xlsx", "Data", buildingId, 3, 0);
					write.writeToSpecificPosition(pathToExcelFile + "/unitData.xlsx", "Data", buildingId, 4, 0);
					write.writeToSpecificPosition(pathToExcelFile + "/unitData.xlsx", "Data", buildingId, 5, 0);
					write.writeToSpecificPosition(pathToExcelFile + "/unitData.xlsx", "Data", buildingId, 6, 0);
					write.writeToSpecificPosition(pathToExcelFile + "/unitData.xlsx", "Data", buildingId, 7, 0);
					write.writeToSpecificPosition(pathToExcelFile + "/unitData.xlsx", "Data", buildingId, 8, 0);
					write.writeToSpecificPosition(pathToExcelFile + "/unitData.xlsx", "Data", buildingId, 9, 0);
				}
				write.writeToSpecificPosition(pathToExcelFile + "/unitData.xlsx", "createUnit", buildingId, 1, 0);
				write.writeToSpecificPosition(pathToExcelFile + "/InquiryData.xlsx", "Data", buildingId, 1, 0);
				writeToUnitExcelFile = false;
			}
		} else {
			write.writeExcel(pathToExcelFile + fileName, "Data", buildingId);
			write.writeExcel(pathToExcelFile + fileName, "deleteBuilding", buildingId);
		}

		// Fail if building id is not generated
		if (buildingInfPage.getBuildingId()[0].isEmpty() && flag.equals("1")) {
			Assert.fail("BuildingId not generated or found");
		}

		/*
		 * } catch (Exception e) { TakeScreenShot takeSS = new TakeScreenShot(driver);
		 * takeSS.takeSnapShot(getClass().getName()); Assert.fail(e.getMessage()); }
		 * catch (AssertionError ae) { TakeScreenShot takeSS = new
		 * TakeScreenShot(driver); takeSS.takeSnapShot(getClass().getName());
		 * Assert.fail(ae.getMessage()); }
		 */
		methodPointer++;

	}

	@Test(priority = 1, dataProvider = "DP2")
	public void editBuilding(String buildingNumber, String addressLine1) throws Exception {

		// Set variable for Smoke Test result sheet
		methodName = "editBuilding" + "TS: " + ts.getCurrentTimeAndDate();
		;

		HomePage homePage = new HomePage(driver);
		BuildingPage buildingInfPage = new BuildingPage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);

		// Check if building is created then proceed
		if (buildingNumber.equals("")) {
			Assert.assertTrue(false);
		}

		try {
			// Step 1. - Navigate to Building Information Screen
			homePage.clickBuildingMenu();
			buildingInfPage.switchToMainFrame();

			// Step 2. - Search an existing Building
			// buildingInfPage.setSearchBuildingNumber(buildingNumber);
			buildingInfPage.clickSearchBuildingButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
			// window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			Thread.sleep(500);
			buildingInfPage.setBuildingId(buildingNumber);
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			// Wait for details to load
			buildingInfPage.waitForBuildingDetailsToLoad();

			// Step 3. - Change the building type
			buildingInfPage.switchToMainFrame();
			buildingInfPage.setAddressLine1(addressLine1);

			// Step 4. - Save the unit
			buildingInfPage.clickSaveButton();

			// Fail if building is not edited
			String edit = buildingInfPage.getAddressLine1();
			if (!edit.equals(addressLine1)) {
				Assert.assertTrue(false);
			}
		} catch (Exception e) {
			TakeScreenShot takeSS = new TakeScreenShot(driver);
			takeSS.takeSnapShot(getClass().getName());
			// Assert.assertTrue(false);
		}

		methodPointer++;
	}

	@Test(priority = 3, dataProvider = "DP3")
	public void deleteBuilding(String buildingNumber, String flag) throws Exception {

		// Set variable for Smoke Test result sheet
		methodName = "deleteBuilding" + "TS: " + ts.getCurrentTimeAndDate();
		;

		HomePage homePage = new HomePage(driver);
		BuildingPage buildingInfPage = new BuildingPage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);

		// Check if building is created then proceed
		if (buildingNumber.equals("")) {
			Assert.assertTrue(false);
		}

		try {
			// Step 1. - Navigate to Building Information Screen
			homePage.clickBuildingMenu();

			// Step 2. - Search a existing Building
			buildingInfPage.switchToMainFrame();
			buildingInfPage.clickSearchBuildingButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
			// window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			buildingInfPage.setBuildingId(buildingNumber);
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			// Wait for details to load
			buildingInfPage.waitForBuildingDetailsToLoad();

			// Step 3. - Click on <<Delete>> button
			buildingInfPage.switchToMainFrame();
			buildingInfPage.clickDeleteButton();

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
			buildingInfPage.switchToMainFrame();
		} catch (Exception e) {
			TakeScreenShot takeSS = new TakeScreenShot(driver);
			takeSS.takeSnapShot(getClass().getName());
			// Assert.fail(e.getMessage());
		}

		methodPointer++;
	}

	@Test(priority = 2, dataProvider = "DP4")
	public void defineDataInSideLinkBuilding(String buildingNumber, String sideLink, String subject, String note)
			throws InterruptedException {

		// Set variable for Smoke Test result sheet
		methodName = "defineDataInSideLinkBuilding" + "TS: " + ts.getCurrentTimeAndDate();
		;

		HomePage homePage = new HomePage(driver);
		BuildingPage buildingInfPage = new BuildingPage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		BuildingNotesPopUp buildingNotes = new BuildingNotesPopUp(driver);

		// Check if building is created then proceed
		if (buildingNumber.equals("")) {
			Assert.assertTrue(false);
		}

		// Step 1. - Navigate to Building Information Screen
		homePage.clickBuildingMenu();

		// Step 2. - Search a existing Building
		buildingInfPage.switchToMainFrame();
		buildingInfPage.clickSearchBuildingButton();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
		// window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		Thread.sleep(1000);
		buildingInfPage.setBuildingId(buildingNumber);
		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}

		// Step 3. - Click on Building note in the side links
		buildingInfPage.clickOnSideLink(sideLink);
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		buildingNotes.switchToFrameContainer();

		// Step5. - Enter note and subject
		buildingNotes.setNote(note);

		// Step 6. - Save and Close
		buildingNotes.clickOnSave();

		// Wait for note to be added
		buildingNotes.waitforNoteToBeAdded();
		Thread.sleep(1000);
		buildingNotes.clickOnClose();

		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}

		buildingInfPage.switchToMainFrame();
		methodPointer++;
	}

	/*
	 * @Test(priority = 2, dataProvider = "DP5") public void
	 * defineContactInBuilding(String contactId, String buildingNumber, String
	 * sideLink) throws InterruptedException {
	 * 
	 * methodName = "defineContactInBuilding" + "TS: " + ts.getCurrentTimeAndDate();
	 * 
	 * HomePage homePage = new HomePage(driver); BuildingPage buildingInfPage = new
	 * BuildingPage(driver); WindowSwitching windowSwitching = new
	 * WindowSwitching(driver); BuildingNotesPopUp buildingNotes = new
	 * BuildingNotesPopUp(driver); ContactNotesPopUp contactNotes = new
	 * ContactNotesPopUp(driver);
	 * 
	 * // Step 1. - Navigate to Building Information Screen
	 * homePage.clickBuildingMenu(); buildingInfPage.switchToMainFrame();
	 * 
	 * // Step 2. - Search a existing Unit buildingInfPage.switchToMainFrame();
	 * buildingInfPage.clickSearchBuildingButton();
	 * windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
	 * // window { // For getting window handles after switching mainWindowHandle =
	 * windowSwitching.getMainWindowHandle(); currentWindowHandle =
	 * windowSwitching.getCurrentWindowHandle(); previousWindowHandle =
	 * windowSwitching.getPreviousWindowHandle(); }
	 * buildingInfPage.setBuildingId(buildingNumber);
	 * windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle,
	 * mainWindowHandle);
	 * 
	 * { // For getting window handles after switching back mainWindowHandle =
	 * windowSwitching.getMainWindowHandle(); currentWindowHandle =
	 * windowSwitching.getCurrentWindowHandle(); previousWindowHandle =
	 * windowSwitching.getPreviousWindowHandle(); }
	 * 
	 * // Handles String buildingPage = currentWindowHandle;
	 * 
	 * // Step 3. - Click on Building note in the side links
	 * buildingInfPage.clickContacts(); Thread.sleep(2000);
	 * windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
	 * // window { // For getting window handles after switching mainWindowHandle =
	 * windowSwitching.getMainWindowHandle(); currentWindowHandle =
	 * windowSwitching.getCurrentWindowHandle(); previousWindowHandle =
	 * windowSwitching.getPreviousWindowHandle(); }
	 * buildingNotes.switchToFrameContainer();
	 * 
	 * contactNotes.clickSearchContactPersonButton();
	 * 
	 * windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
	 * window { // For getting window handles after switching mainWindowHandle =
	 * windowSwitching.getMainWindowHandle(); currentWindowHandle =
	 * windowSwitching.getCurrentWindowHandle(); previousWindowHandle =
	 * windowSwitching.getPreviousWindowHandle(); }
	 * buildingNotes.switchToFrameContainer();
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
	 * buildingNotes.switchToFrameContainer(); contactNotes.checkAuthorizedCaller();
	 * contactNotes.clickSaveButtonContact(); Thread.sleep(1000);
	 * contactNotes.clickCloseButton();
	 * 
	 * // Set contactPage as current page currentWindowHandle =
	 * windowSwitching.setHandle(buildingPage); mainWindowHandle =
	 * tstParameters.getMainWindowHandle(); previousWindowHandle = null;
	 * 
	 * // Switch to mainframe buildingInfPage.switchToMainFrame();
	 * 
	 * }
	 */

	@BeforeClass
	public void testCasesSetUp() {
		System.out.println("*******\tBuilding Smoke Test started\t*******");

		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		rw = new ResultWriter(driver, mainWindowHandle);
		preTest = new PreTestWindowCheck(driver);

		// populate the methodNameList
		methodNameList.add("createBuilding");
		methodNameList.add("createBuilding");
		methodNameList.add("editBuilding");
		methodNameList.add("defineDataInSideLinkBuilding");
		methodNameList.add("deleteBuilding");
	}

	@AfterClass
	public void testCaseEnd() {
		// driver.quit();
	}

	@BeforeMethod
	public void clickOnContractMenu() throws IOException, InterruptedException {
		// Close all remaining windows
		preTest.closeWindows(mainWindowHandle);

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
			homePage.clickContractMenu();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@AfterMethod
	public void testCaseOver(ITestResult result) throws IOException {
		rw.setResults(result, methodName);
		commonElements = new CommonElements(driver);
		//commonElements.clickOnHomeButton();
	}
}
