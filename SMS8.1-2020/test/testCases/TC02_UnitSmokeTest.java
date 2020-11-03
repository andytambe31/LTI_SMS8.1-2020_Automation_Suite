package testCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pages.CommonElements;
import pages.HomePage;
import pages.LoginPage;
import pages.SMSConfirmPopUp;
import pages.UnitNotesPopUp;
import pages.UnitPage;
import scripts.PreTestWindowCheck;
import scripts.ResultWriter;
import scripts.TestParameters;
import scripts.TimeStamp;
import utility.GetLoginIdForMethodName;
import utility.ReadFromExcel;
import utility.WindowSwitching;
import utility.WriteToExcelFile;
import utility.utilityFunctions;

public class TC02_UnitSmokeTest {

	// Class references
	TestParameters tstParameters = new TestParameters();
	WriteToExcelFile write = new WriteToExcelFile();
	GetLoginIdForMethodName loginId = new GetLoginIdForMethodName();
	CommonElements commonElements;
	HomePage homePage;
	TimeStamp ts = new TimeStamp();
	ResultWriter rw;// = new ResultWriter();
	UnitPage unitInfPage;
	WindowSwitching windowSwitching;
	PreTestWindowCheck preTest;

	// driver variables
	String driverPath = "./test/resources/drivers/";
	String mainWindowHandle = null;// tstParameters.getMainWindowHandle();
	String currentWindowHandle = null;// tstParameters.getCurrentWindowHandle();
	String previousWindowHandle = null;// tstParameters.getPreviousWindowHandle();
	WebDriver driver;

	// Excel path and file names
	String pathToExcelFile = "./test/resources/data/";
	String fileName = "unitData.xlsx";
	String fileResult = "/SmokeTestResultSheet.xlsx";

	// Write to excel variable
	String methodName = null;

	// Misc
	String unitCount = "01";
	int flagForUnitCreation = 0;
	int count = 1;
	int contractCounterForMutiple = 0;
	boolean contractFlag = true;
	boolean addUnitForDeleteAndEditFlag = true;
	boolean commercialUnitCreateFlag = false;
	String unitNumber = null;

	// List of methodName
	List<String> methodNameList = new ArrayList<String>();
	int methodPointer = 0;

	@DataProvider(name = "DP1")
	public Object[][] createDataForCreateData() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "createUnit");
		return retObjArr;
	}

	@DataProvider(name = "DP2")
	public Object[][] createDataForEditUnit() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "editUnit");
		return retObjArr;
	}

	@DataProvider(name = "DP3")
	public Object[][] createDataForDeleteUnit() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "deleteUnit");
		return retObjArr;
	}

	@DataProvider(name = "DP4")
	public Object[][] createDataForDefineDataInSideLink() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "dataForSideLinks");
		return retObjArr;
	}

	@Test(priority = 2, dataProvider = "DP2")
	public void editUnit(String unitNumber, String unitType) {

		HomePage homePage = new HomePage(driver);
		UnitPage unitInfPage = new UnitPage(driver);

		// Smoke test result sheet
		methodName = "editUnit" + "TS: " + ts.getCurrentTimeAndDate();

		if (!tstParameters.getEntity().equals("demo")) {
			// Check if unit is created , then proceed
			if (unitNumber.equals("")) {
				Assert.assertTrue(false);
			}

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickUnitMenu();

			// Step 2. - Search a existing Unit
			unitInfPage.setSearchUnitNumber(unitNumber);
			// Wait for the building details to load
			unitInfPage.waitForBuildingDetailsToLoad();

			// Step 3. - Select the unit
			unitInfPage.selectUnitFromGrid(unitNumber);
			// Wait for the building Id to load
			unitInfPage.waitForBuildingIdToLoad();

			// Step 4. - Change the unit type
			unitInfPage.setUnitTypeEdit();

			// Step 5. - Save the unit
			unitInfPage.clickSaveButton();

		}
		methodPointer++;

	}

	@Test(priority = 4, dataProvider = "DP3")
	public void deleteUnit(String unitNumber, String flag) {

		HomePage homePage = new HomePage(driver);
		UnitPage unitInfPage = new UnitPage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);

		// Smoke test result sheet
		methodName = "deleteUnit" + "TS: " + ts.getCurrentTimeAndDate();

		if (!tstParameters.getEntity().equals("demo")) {

			// Check if unit is created , then proceed
			if (unitNumber.equals("")) {
				Assert.assertTrue(false);
			}

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickUnitMenu();

			// Step 2. - Search a existing Unit
			unitInfPage.setSearchUnitNumber(unitNumber);
			// Wait for the building details to load
			unitInfPage.waitForBuildingDetailsToLoad();

			// Step 3. - Select the unit
			unitInfPage.selectUnitFromGrid(unitNumber);
			// Wait for the building Id to load
			unitInfPage.waitForBuildingIdToLoad();

			// Step 4. - Click on <<Delete>> button
			unitInfPage.clickDeleteButton();

			// Step 5. - Click on <<Yes>> button
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
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
			unitInfPage.switchToMainFrame();
		}
		methodPointer++;

	}

	@Test(priority = 3, dataProvider = "DP4")
	public void defineDataInSideLinkForUnit(String unitNumber, String sideLink, String subject, String note)
			throws InterruptedException {

		// Smoke test result sheet
		methodName = "defineDataInSideLinkForUnit" + "TS: " + ts.getCurrentTimeAndDate();
		if (!tstParameters.getEntity().equals("demo")) {
			HomePage homePage = new HomePage(driver);
			UnitPage unitInfPage = new UnitPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);
			UnitNotesPopUp unitNotes = new UnitNotesPopUp(driver);

			// Check if unit is created , then proceed
			if (unitNumber.equals("")) {
				Assert.assertTrue(false);
			}

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickUnitMenu();

			// Step 2. - Search a existing Unit
			unitInfPage.setSearchUnitNumber(unitNumber);

			// Wait for building details to load
			unitInfPage.waitForBuildingDetailsToLoad();

			// Step 3. - Select the unit
			unitInfPage.selectUnitFromGrid(unitNumber);

			// Wait for building details to load
			unitInfPage.waitForBuildingIdToLoad();

			// Step 4. - Click on Unit note in the side links
			unitInfPage.clickOnSideLink(sideLink);
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			unitNotes.switchToFrameContainer();

			// Step5. - Enter note and subject
			Thread.sleep(1000);
			unitNotes.setSubject(subject);
			Thread.sleep(1000);
			unitNotes.setNote(note);

			// Step 6. - Save and Close
			unitNotes.clickOnSave();
			// Wait for note to be added
			unitNotes.waitForNoteToBeAdded();

			// Step 7. - Click on Close
			unitNotes.clickOnClose();

			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			unitInfPage.switchToMainFrame();
		}
		methodPointer++;
	}

	@Test(priority = 0, dataProvider = "DP1")
	public void createMultipleUnits(String buildingId, String product, String salesTerritory)
			throws InterruptedException, IOException {

		// Smoke test result sheet
		methodName = "createMultipleUnits" + "TS: " + ts.getCurrentTimeAndDate();

		// Page reference
		homePage = new HomePage(driver);
		unitInfPage = new UnitPage(driver);
		windowSwitching = new WindowSwitching(driver);

		// Variables
		String unitName;

		int NoOfUnit = 0;

		// Check if building is created , then proceed
		if (buildingId.equals("")) {
			Assert.assertTrue(false);
		}

		// Step 1. - Navigate to Unit Information Screen
		homePage.clickUnitMenu();

		// Step 2. - Enter building id
		unitInfPage.setBuildingId(buildingId);

		// Wait for the building details to load
		unitInfPage.waitForBuildingDetailsToLoad();

		/*
		 * // Wait for building details to load int tripPoint = 0; while (tripPoint !=
		 * 300) { tripPoint++; try { if (unitInfPage.getBuildingId().length() != 0) {
		 * break; } } catch (Exception e) { // TODO: handle exception } }
		 */

		while (NoOfUnit != 13) {
			// Close all remaining windows
			preTest.closeWindows(mainWindowHandle);

			// Create a unit Number
			unitNumber = buildingId + "AT" + unitCount;
			unitCount = "0" + (Integer.parseInt(unitCount.substring(1, unitCount.length())) + 1);
			// unitName = "COM";
			Thread.sleep(2000);

			// Step 3. - Enter unit number & unit name for non commercial units
			// if (!product.contains("COM")) {
			unitName = unitNumber;
			unitInfPage.setUnitNumber(unitNumber);
			if (NoOfUnit == 0) {
				unitInfPage.setUnitName(unitName);

				// }

				// Step 4. - Set product
				unitInfPage.clickProductButton();
				windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
				{
					// For getting window handles after switching
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}
				unitInfPage.switchToAssociateFrame(); // switch to associate grid

				unitInfPage.setProduct(product);
				Thread.sleep(1000);
				unitInfPage.clickSelectButton();

				windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
				{
					// For getting window handles after switching back
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}
				unitInfPage.switchToMainFrame();
			}

			// Step 5. - Set maintenance territory
			unitInfPage.clickSalesTerritoryButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			unitInfPage.switchToAssociateFrame(); // switch to associate grid
			Thread.sleep(1000);
			unitInfPage.setSalesTerritory(salesTerritory);
			Thread.sleep(1000);
			unitInfPage.clickSelectButton();

			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			unitInfPage.switchToMainFrame();

			// For china set tier
			if (tstParameters.getEntity().equals("China")) {

				// Click on Tier button
				unitInfPage.clickTierButton();

				// Switch the windows
				windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
				{
					// For getting window handles after switching
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}

				unitInfPage.switchToAssociateFrame(); // switch to associate grid
				Thread.sleep(1000);
				unitInfPage.selectTierFromGrid();
				Thread.sleep(1000);
				unitInfPage.clickSelectButton();

				windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
				{
					// For getting window handles after switching back
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}
				unitInfPage.switchToMainFrame();

				// Set Vertical
				unitInfPage.setVertical();

			}

			// Step 8. - Save the unit
			Thread.sleep(1000);
			unitInfPage.clickSaveButton();

			// String unitNum[] = { unitNumber };

			// Fail test if unitNumber is not generated
			if (unitInfPage.getUnitNumber().length == 0) {
				Assert.assertTrue(false);
			}

			// if (flag.equals("1")) {
			write.writeToSpecificPosition(pathToExcelFile + fileName, "Data", unitInfPage.getUnitNumber(), NoOfUnit + 1,
					1);
			if (addUnitForDeleteAndEditFlag == true) {
				write.writeToSpecificPosition(pathToExcelFile + fileName, "editUnit", unitInfPage.getUnitNumber(), 1,
						0);
				write.writeToSpecificPosition(pathToExcelFile + fileName, "dataForSideLinks",
						unitInfPage.getUnitNumber(), 1, 0);
				write.writeToSpecificPosition(pathToExcelFile + fileName, "deleteUnit", unitInfPage.getUnitNumber(), 1,
						0);
				addUnitForDeleteAndEditFlag = false;
			}

			if (contractCounterForMutiple % 2 == 1) {
				write.writeToSpecificPosition(pathToExcelFile + "contractData.xlsx", "Data",
						unitInfPage.getUnitNumber(), count, 0);
				contractCounterForMutiple++;
				count++;
			} else
				contractCounterForMutiple++;

			// Select unit from grid
			unitInfPage.selectUnitFromGrid(unitNumber);

			// Click on New Button
			Thread.sleep(1000);
			unitInfPage.clickNewButton();

			NoOfUnit++;
			if (tstParameters.getSmokeTestMode().equals("demo") && (NoOfUnit == 3)) {
				break;
			}

		}

		if (!tstParameters.getEntity().equals("demo")) {
			// Select unit from grid
			unitInfPage.selectUnitFromGrid(unitNumber);

			// Click on New Button
			Thread.sleep(2000);
			unitInfPage.clickNewButton();

			// Set product as Com-Com-Com
			unitInfPage.clickProductButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch

			{ // For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			unitInfPage.switchToAssociateFrame(); // switch to associate grid

			unitInfPage.setProduct("COM-COM");
			unitInfPage.clickSelectButton();

			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{ // For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			unitInfPage.switchToMainFrame();

			// Step 5. - Set maintenance territory
			unitInfPage.clickSalesTerritoryButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
			{ // For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			unitInfPage.switchToAssociateFrame(); // switch to associate grid

			unitInfPage.setSalesTerritory(salesTerritory);
			unitInfPage.clickSelectButton();

			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{ // For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			unitInfPage.switchToMainFrame();

			// Error found
			unitInfPage.setUnitName("COM00" + unitNumber);
			Thread.sleep(1000);
			unitInfPage.setUnitNumber("COM00" + unitNumber);

			// For china set tier
			if (tstParameters.getEntity().equals("China")) {

				// Click on Tier button
				unitInfPage.clickTierButton();

				// Switch the windows
				windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
				{
					// For getting window handles after switching
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}

				unitInfPage.switchToAssociateFrame(); // switch to associate grid
				Thread.sleep(1000);
				unitInfPage.selectTierFromGrid();
				Thread.sleep(1000);
				unitInfPage.clickSelectButton();

				windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
				{
					// For getting window handles after switching back
					mainWindowHandle = windowSwitching.getMainWindowHandle();
					currentWindowHandle = windowSwitching.getCurrentWindowHandle();
					previousWindowHandle = windowSwitching.getPreviousWindowHandle();
				}
				unitInfPage.switchToMainFrame();

				// Set Vertical
				unitInfPage.setVertical();

			}

			// Step 8. - Save the commercial unit
			unitInfPage.clickSaveButton();

			// Fail test if unitNumber is not generated
			Thread.sleep(1000);

			write.writeToSpecificPosition(pathToExcelFile + fileName, "commercialUnit", unitInfPage.getUnitNumber(), 1,
					0);

			if (unitInfPage.getUnitNumber().length == 0) {
				Assert.assertTrue(false);

			} else
				commercialUnitCreateFlag = true;
		}
		methodPointer++;
	}

	@Test(priority = 1, dataProvider = "DP1")
	public void createCommercialUnit(String buildingId, String product, String salesTerritory)
			throws InterruptedException, IOException {
		// Create Commercial unit
		methodName = "createCommercialUnit" + "TS: " + ts.getCurrentTimeAndDate();
		if (!tstParameters.getEntity().equals("demo")) {
			if (commercialUnitCreateFlag == true)
				Assert.assertTrue(true);
			else
				Assert.assertTrue(false);
		}
		methodPointer++;
	}

	@BeforeClass
	public void testCasesSetUp() {
		// Get driver value from
		System.out.println("*******\tUnit Smoke Test started\t*******");
		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		rw = new ResultWriter(driver, mainWindowHandle);
		preTest = new PreTestWindowCheck(driver);

		// populate the methodNameList
		methodNameList.add("createMultipleUnits");
		methodNameList.add("createCommercialUnit");
		methodNameList.add("editContact");
		methodNameList.add("defineDataInSideLinkForUnit");
		methodNameList.add("deleteUnit");
	}

	@AfterClass
	public void testCaseEnd() {
		// driver.quit();
	}

	@BeforeMethod
	public void clickOnContractMenu() throws InterruptedException, IOException {
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
		if (!methodName.contains("createCommercialUnit")) {
			commonElements.clickOnHomeButton();
		}
	}

}
