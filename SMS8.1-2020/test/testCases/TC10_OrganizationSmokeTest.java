package testCases; //UAE

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.BranchNotesPopUp;
import pages.BranchPage;
import pages.CommonElements;
import pages.CompanyHoursPopUp;
import pages.HomePage;
import pages.LoginPage;
import pages.CompanyPage;
import pages.EmployeeNotesPage;
import pages.EmployeePage;
import pages.SMSConfirmPopUp;
import pages.TerritoryPage;
import pages.TerritoryPopUpPage;
import pages.UnitPage;
import scripts.PreTestWindowCheck;
import scripts.ResultWriter;
import scripts.TestParameters;
import scripts.TimeStamp;
import utility.ReadFromExcel;
import utility.WindowSwitching;
import utility.WriteToExcelFile;

public class TC10_OrganizationSmokeTest {

	// Class references
	TestParameters tstParameters = new TestParameters();
	WriteToExcelFile write = new WriteToExcelFile();
	ReadFromExcel readFromeExcel = new ReadFromExcel();
	CommonElements commonElements;
	HomePage homePage;
	TimeStamp ts = new TimeStamp();
	ResultWriter rw;// = new ResultWriter();
	UnitPage unitInfPage;
	WindowSwitching windowSwitching;
	PreTestWindowCheck preTest;

	String driverPath = "./test/resources/drivers";
	String mainWindowHandle = null;
	String currentWindowHandle = null;
	String previousWindowHandle = null;
	String methodName = "";

	String pathToExcelFile = "./test/resources/data/";
	String fileName = "organizationData.xlsx";
	WebDriver driver;

	// **************** Dataprovider ************************

	@DataProvider(name = "DP1")
	public Object[][] DataForCreateBranch() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "createBranch");
		return retObjArr;
	}

	@DataProvider(name = "DP2")
	public Object[][] DataEditBranch() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "editBranch");
		return retObjArr;
	}

	@DataProvider(name = "DP3")
	public Object[][] DataDeleteBranch() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "deleteBranch");
		return retObjArr;
	}

	@DataProvider(name = "DP4")
	public Object[][] DataForCreateCompany() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "createCompany");
		return retObjArr;
	}

	@DataProvider(name = "DP5")
	public Object[][] DataEditCompany() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "editCompany");
		return retObjArr;
	}

	@DataProvider(name = "DP6")
	public Object[][] DataDeleteCompany() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "deleteCompany");
		return retObjArr;
	}

	@DataProvider(name = "DP7")
	public Object[][] DataForCreateTerritory() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "createTerritory");
		return retObjArr;
	}

	@DataProvider(name = "DP8")
	public Object[][] DataEditTerritory() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "editTerritory");
		return retObjArr;
	}

	@DataProvider(name = "DP9")
	public Object[][] DataDeleteTerritory() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "deleteTerritory");
		return retObjArr;
	}

	@DataProvider(name = "DP10")
	public Object[][] createDataForCompanySideLink() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "CompanySideLinks");
		return retObjArr;

		// return new Object[][] { { "55000", "Hours", "Monday", "10:00", "19:00" } };

	}

	@DataProvider(name = "DP11")
	public Object[][] createDataForBranchSideLink() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "branchSideLinks");
		return retObjArr;
	}

	@DataProvider(name = "DP12")
	public Object[][] createDataForTerritorySideLink() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "territorySideLinks");
		return retObjArr;
	}

	@DataProvider(name = "DP13")
	public Object[][] DataForCreateEmployee() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "createEmployee");
		return retObjArr;
	}

	@DataProvider(name = "DP15")
	public Object[][] DataForDeleteEmployee() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "deleteEmployee");
		return retObjArr;
	}

	@DataProvider(name = "DP16")
	public Object[][] DataForSideLinkEmployee() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "dataInSideLinkEmployee");
		return retObjArr;
	}

	@Test(priority = 12, dataProvider = "DP15")
	public void deleteEmployee(String employeeId, String branchName, String role) throws InterruptedException {

		methodName = "createEmployee" + "TS: " + ts.getCurrentTimeAndDate();

		HomePage home = new HomePage(driver);
		TerritoryPage territoryPage = new TerritoryPage(driver);
		EmployeePage employee = new EmployeePage(driver);
		SMSConfirmPopUp popUp = new SMSConfirmPopUp(driver);

		// Step 1. - Navigate to Organization > Employee
		home.clickEmployeeMenu();
		employee.switchToMainFrame();

		// Step 2. - Search employee
		employee.clickSearchEmployeeButton();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		employee.setEmployeeId(employeeId);
		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		employee.switchToMainFrame();

		// Step 4. - Click on Delete
		employee.clickDeleteButton();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
		{ // For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		popUp.clickYesButton2();
		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{ // For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		employee.switchToMainFrame();

	}

	@Test(priority = 11, dataProvider = "DP16")
	public void dataInSideLinkEmployee(String employeeId, String sideLinks, String subject, String note,
			String expiryDate) throws InterruptedException {

		methodName = "createEmployee" + "TS: " + ts.getCurrentTimeAndDate();

		HomePage home = new HomePage(driver);
		TerritoryPage territoryPage = new TerritoryPage(driver);
		EmployeePage employee = new EmployeePage(driver);
		EmployeeNotesPage employeeNote = new EmployeeNotesPage(driver);
		SMSConfirmPopUp popUp = new SMSConfirmPopUp(driver);

		// Step 1. - Navigate to Organization > Employee
		home.clickEmployeeMenu();
		employee.switchToMainFrame();

		// Step 2. - Search employee
		employee.clickSearchEmployeeButton();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		employee.setEmployeeId(employeeId);
		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}

		// Step 3. - Click on Notes in SideLinks
		employee.clickOnNotes();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
		{
			// For getting window handles after switching
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		employee.switchToFrameContainer();

		// Step 4. - Set Subject, Note and Expiry date
		employeeNote.setSubject(subject);
		employeeNote.setNote(note);
		employeeNote.setExpiryDate(expiryDate);

		// Step 5. - Click on Save
		employeeNote.clickOnSave();

		// Wait till note is saved
		while (true) {
			if (employeeNote.noteSaved()) {
				break;
			}
		}

		// Step 6. - Click on Close
		employeeNote.clickOnClose();
		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{
			// For getting window handles after switching back
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		employee.switchToMainFrame();

	}

	@BeforeClass
	public void beforeClass() throws IOException, InterruptedException {
		System.out.println("*******\tOrganization Smoke Test started\t*******");

		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		windowSwitching = new WindowSwitching(driver);
		rw = new ResultWriter(driver, mainWindowHandle);
		preTest = new PreTestWindowCheck(driver);

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@BeforeMethod
	public void clickOnOrgMenu() {
		HomePage homePage = new HomePage(driver);
		driver.switchTo().defaultContent();
		try {
			homePage.clickOrganizationMenu();
		} catch (Exception e) {
		}
	}

	@AfterMethod
	public void testCaseOver(ITestResult result) throws IOException {
		rw.setResults(result, methodName);
		try {
			commonElements = new CommonElements(driver);
			commonElements.clickOnHomeButton();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
