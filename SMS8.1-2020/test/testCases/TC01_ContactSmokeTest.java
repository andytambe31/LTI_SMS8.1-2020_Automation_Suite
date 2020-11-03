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
import org.testng.annotations.Test;

import pages.CommonElements;
import pages.ContactNotePage;
import pages.ContactPage;
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
import utility.WindowSwitching;
import utility.WriteToExcelFile;

public class TC03_ContactSmokeTest {

	// Class references
	TestParameters tstParameters = new TestParameters();
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

	// Driver
	String driverPath = "./test/resources/drivers";
	String mainWindowHandle = null;
	String currentWindowHandle = null;
	String previousWindowHandle = null;
	WebDriver driver;

	// Excel paths
	String pathToExcelFile = "./test/resources/data/";
	String fileName = "contactData.xlsx";

	// Misc
	String methodName = "";

	// List of methodName
	List<String> methodNameList = new ArrayList<String>();
	int methodPointer = 0;

	@DataProvider(name = "DP1")
	public Object[][] createDataForCreateContact() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "Data");
		return retObjArr;
	}

	@DataProvider(name = "DP2")
	public Object[][] createDataForEditAndDeleteContact() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "editContact");
		return retObjArr;
	}

	@DataProvider(name = "DP3")
	public Object[][] createDataForDataForSideLinks() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "dataForSideLinks");
		return retObjArr;
	}

	@DataProvider(name = "DP4")
	public Object[][] createDataForDeleteContact() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "deleteContact");
		return retObjArr;
	}

	@Test(priority = 0, dataProvider = "DP1")
	public void createContact(String firstName, String title, String language, String country, String flag, String city,
			String state) throws IOException, InterruptedException {
		HomePage homePage = new HomePage(driver);
		ContactPage contactPage = new ContactPage(driver);
		WriteToExcelFile write = new WriteToExcelFile();

		methodName = "createContact" + "TS: " + ts.getCurrentTimeAndDate();

		// Step 1. - Navigate to Contact Information Screen
		homePage.clickContacts();

		// Step 2. - Set First name
		contactPage.setFirstName(firstName);

		// Step 3. - Set title
		contactPage.setTitle(title);

		// Step 4. - Set language
		contactPage.setLanguage(language);

		// Step 5. - Set country
		contactPage.setCountry(country);

		if (tstParameters.getEntity().equals("India") || tstParameters.getEntity().equals("China")) {

			// Set State
			contactPage.setState(state);

			contactPage.clickCityButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			contactPage.switchToAssociateFrame(); // switch to associate grid
			Thread.sleep(1000);

			// set City
			contactPage.setCity(city);
			Thread.sleep(1000);
			contactPage.clickSelectButton();

			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			contactPage.switchToMainFrame();

		}

		// Step - Enter EmailID
		contactPage.setEmailId("test@test.com");

		// Step 6. - Save the contact
		contactPage.clickSave();

		String[] contactId = contactPage.getContactId();

		// Step 7. - Pass the created contact Id in excel sheet'

		if (flag.equals("1")) {
			write.writeExcel(pathToExcelFile + fileName, "editContact", contactId);
			write.writeExcel(pathToExcelFile + fileName, "dataForSideLinks", contactId);
			write.writeToSpecificPosition(pathToExcelFile + "BuildingData.xlsx", "addContact", contactId, 1, 0);
			write.writeToSpecificPosition(pathToExcelFile + "CustomerData.xlsx", "addContact", contactId, 1, 0);
		} else
			write.writeToSpecificPosition(pathToExcelFile + fileName, "deleteContact", contactId, 1, 0);

		methodPointer++;
	}

	@Test(priority = 1, dataProvider = "DP2")
	public void editContact(String contactId, String addressline1) throws InterruptedException {
		HomePage homePage = new HomePage(driver);

		ContactPage contactPage = new ContactPage(driver);
		WriteToExcelFile write = new WriteToExcelFile();

		methodName = "editContact" + "TS: " + ts.getCurrentTimeAndDate();

		if (!tstParameters.getSmokeTestMode().equals("demo")) {
			// Step 1. - Navigate to Unit Information Screen
			homePage.clickContacts();
			Thread.sleep(1000);

			// Step 2. - Search contact
			contactPage.setContactId(contactId);
			Thread.sleep(1000);

			// Wait for contact details to load
			contactPage.waitForContactdetailsToLoad();

			// Step 3. - Set address line
			contactPage.setAddressLine1(addressline1);
			Thread.sleep(1000);

			// Step 4 - Save the changes
			contactPage.clickSave();
		}
		methodPointer++;

	}

	@Test(priority = 3, dataProvider = "DP4")
	public void deleteContact(String contactId) throws InterruptedException {
		HomePage homePage = new HomePage(driver);
		WindowSwitching windowSwitch = new WindowSwitching(driver);
		ContactPage contactPage = new ContactPage(driver);
		SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);

		methodName = "deleteContact" + "TS: " + ts.getCurrentTimeAndDate();

		if (!tstParameters.getSmokeTestMode().equals("demo")) {
			// Step 1. - Navigate to Unit Information Screen
			homePage.clickContacts();
			Thread.sleep(1000);

			// Step 2. - Search contact
			contactPage.setContactId(contactId);
			Thread.sleep(1000);
			// Wait for contact details to load
			contactPage.waitForContactdetailsToLoad();

			// Step 3 - Delete the contact
			contactPage.clickDelete();
			Thread.sleep(1000);
			System.out.println("Delete clicked");
			// Step 4. - Click on <<Yes>> button
			windowSwitch.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
			{
				// For getting window handles after switching
				/*
				 * System.out.println(mainWindowHandle);
				 * System.out.println(currentWindowHandle);
				 * System.out.println(previousWindowHandle);
				 */
				mainWindowHandle = windowSwitch.getMainWindowHandle();
				currentWindowHandle = windowSwitch.getCurrentWindowHandle();
				previousWindowHandle = windowSwitch.getPreviousWindowHandle();
			}

			/*
			 * List<WebElement> ls = driver.findElements(By.xpath("//*")); for (WebElement
			 * we : ls) { System.out.println("<" + we.getTagName() + " if = '" +
			 * we.getAttribute("id") + "'>" + we.getText() + ""); }
			 */
			smsCnfPopUp.clickYesButton();

			windowSwitch.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitch.getMainWindowHandle();
				currentWindowHandle = windowSwitch.getCurrentWindowHandle();
				previousWindowHandle = windowSwitch.getPreviousWindowHandle();
			}
			contactPage.switchToMainFrame();
		}
		methodPointer++;

	}

	@Test(priority = 2, dataProvider = "DP3")
	public void defineDataInSideLinkContact(String contactId, String sideLink, String subject, String note)
			throws InterruptedException {
		HomePage homePage = new HomePage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		ContactPage contactPage = new ContactPage(driver);
		ContactNotePage contactNotePage = new ContactNotePage(driver);

		methodName = "defineDataInSideLinkContact" + "TS: " + ts.getCurrentTimeAndDate();

		if (!tstParameters.getSmokeTestMode().equals("demo")) {
			// Step 1. - Navigate to Unit Information Screen
			homePage.clickContacts();

			// Step 2. - Search contact
			contactPage.setContactId(contactId);
			Thread.sleep(1000);

			// Wait for contact details to load
			contactPage.waitForContactdetailsToLoad();

			// Step 3. - Select side links
			contactPage.clickOnSideLink(sideLink);

			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			contactNotePage.switchToFrameContainer();

			// Step5. - Enter note and subject
			contactNotePage.setSubject(subject);
			Thread.sleep(1000);
			contactNotePage.setNote(note);

			// Step 6. - Save and Close
			contactNotePage.clickOnSave();
			Thread.sleep(10000);

			// Wait for contact note to be saved
			contactNotePage.waitForContactNoteTobeSaved();

			// Step 7. - Close the window
			contactNotePage.clickOnClose();

			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			contactPage.switchToMainFrame();
		}
		methodPointer++;
	}

	@BeforeClass
	public void beforeClass() throws IOException {
		System.out.println("*******\tContact Smoke Test started\t*******");

		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		rw = new ResultWriter(driver, mainWindowHandle);
		windowSwitching = new WindowSwitching(driver);
		preTest = new PreTestWindowCheck(driver);

		// populate the methodNameList
		methodNameList.add("createContact");
		methodNameList.add("createContact");
		methodNameList.add("editContact");
		methodNameList.add("deleteContact");
		methodNameList.add("defineDataInSideLinkContact");
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
		commonElements.clickOnHomeButton();
	}
}
