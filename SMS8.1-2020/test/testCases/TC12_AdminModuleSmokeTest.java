package testCases;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import pages.ApplicationMenuPermissionPage;
import pages.ApplicationParameterPage;
import pages.CommonElements;
import pages.HomePage;
import pages.LoginPage;
import pages.ManageUserPage;
import pages.SMSReportPermissionPage;
import scripts.ResultWriter;
import scripts.TestParameters;
import scripts.TimeStamp;
import utility.ReadFromExcel;
import utility.TakeScreenShot;
import utility.WindowSwitching;
import utility.WriteToExcelFile;
import utility.utilityFunctions;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class TC01_AdminModuleSmokeTest {

	// Class objects
	TestParameters tstParameters = new TestParameters();
	TakeScreenShot takeSS;
	ResultWriter rw;
	TimeStamp ts = new TimeStamp();
	CommonElements commonElements;

	String driverPath = "./test/resources/drivers/";
	// "D:\\SMS8.1-2019\\test\\resources\\drivers\\";
	String mainWindowHandle = null;
	String currentWindowHandle = null;
	String previousWindowHandle = null;

	String pathToExcel = "./test/resources/data/";
	String methodName = "";

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		System.out.println("*******	Admin Smoke Test started	*******");

		// Get driver value from
		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		takeSS = new TakeScreenShot(driver);
		rw = new ResultWriter(driver, mainWindowHandle);
	}

	@AfterClass
	public void browserClose() {
		//driver.quit();
	}

	@BeforeMethod
	public void clickOnAdminMenu() {
		HomePage homePage = new HomePage(driver);
		driver.switchTo().defaultContent();
		homePage.clickAdminMenu();
	}

	@AfterMethod
	public void testCaseOver(ITestResult result) throws IOException {
		rw.setResults(result, methodName);
		CommonElements commonElements = new CommonElements(driver);
		// Navigate to HomePage
		commonElements.clickOnHomeButton();
	}

	@DataProvider(name = "DP1")
	public Object[][] createDataForModifyApplicationParameter() throws IOException { // return new
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcel + "AdminData.xlsx", "AP");
		return retObjArr;

	}

	@DataProvider(name = "DP2")
	public Object[][] createDataForManageUser() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcel + "AdminData.xlsx", "US");
		return retObjArr;
	}

	@DataProvider(name = "DP3")
	public Object[][] createDataForVerifyMenuPermission() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcel + "AdminData.xlsx", "MN");
		return retObjArr;

	}

	@DataProvider(name = "DP4")
	public Object[][] createDataForVerifySMSReportPermission() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcel + "AdminData.xlsx", "RN");
		return retObjArr;
	}

	@Test(priority = 0, dataProvider = "DP1")
	public void modifyApplicationParameterAdmin(String parameterName, String parameterValue, String parameterModule,
			String parameterType) throws InterruptedException {

		methodName = "modifyApplicationParameterAdmin" + "TS: " + ts.getCurrentTimeAndDate();
		HomePage homePage = new HomePage(driver);
		ApplicationParameterPage applicationParameterPage = new ApplicationParameterPage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);

		// Step 1. - Navigate to Application parameter
		homePage.clickApplicationParameter();

		// Step 2. - Select Module
		applicationParameterPage.setModule(parameterModule);
		Thread.sleep(1000);

		// Step 3. - Verify the Enable Branch level ETA for callback parameter exists
		assertTrue(applicationParameterPage.applicationParameterExists(parameterName));

		// Step 4. - Change value of Enable Branch level ETA for callback to No

		if (parameterType.equals("Button")) {
			applicationParameterPage.clickApplicationParameterValueButton(parameterName);
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			Thread.sleep(1000);
			applicationParameterPage.setApplicationParameterValueButton(parameterValue);
			Thread.sleep(1000);
			applicationParameterPage.clickSelectButton();

			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			applicationParameterPage.switchToMainFrame();
		} else {
			applicationParameterPage.setApplicationParameterValueTextBox(parameterName, parameterValue);
		}

		// Step 5. - Save the value
		Thread.sleep(1000);
		applicationParameterPage.clickSaveButton();

		// Step 6. - Verify the value is saved as No.
		assertTrue(applicationParameterPage.verifyApplicationParameterValueButton(parameterName, parameterValue));
	}

	@Test(priority = 1, dataProvider = "DP2")
	public void manageUserAdmin(String loginId, String networkId, String securityGroup, String password,
			String confirmPassowrd) throws IOException, InterruptedException {
		HomePage homePage = new HomePage(driver);
		ManageUserPage manageUser = new ManageUserPage(driver);
		WriteToExcelFile write = new WriteToExcelFile();

		methodName = "manageUserAdmin" + "TS: " + ts.getCurrentTimeAndDate();

		// ********* Create new login id logic******************
		String count = loginId.replace("testUser", "");
		int counter = Integer.parseUnsignedInt(count);
		counter = counter + 1;
		loginId = "testUser" + String.valueOf(counter);
		networkId = loginId;

		write.writeToSpecificPosition(pathToExcel + "AdminData.xlsx", "US", new String[] { loginId }, 1, 0); // loginId
		write.writeToSpecificPosition(pathToExcel + "AdminData.xlsx", "US", new String[] { loginId }, 1, 1); // networdId

		// Step 1. - Navigate to Manage User
		homePage.clickManagerUser();
		Thread.sleep(1000);

		// Step 2. - Enter login id
		manageUser.setLoginId(loginId);
		Thread.sleep(1000);

		// Step 3. - Enter network id
		manageUser.setNetworkId(networkId);
		Thread.sleep(1000);

		// Step 4. - Select security group
		manageUser.clickOnSecurityGroupButton();
		Thread.sleep(1000);
		manageUser.setSecurityGroup(securityGroup);
		Thread.sleep(1000);

		// Step 5. - Enter password
		manageUser.setPassword(password);
		Thread.sleep(1000);

		// Step 6. - Enter confirm password
		manageUser.setConfirmPassword(confirmPassowrd);
		Thread.sleep(1000);

		// Step 7. - Save the user
		manageUser.clickOnSaveButton();

	}

	@Test(priority = 2, dataProvider = "DP3")
	public void verifyMenuPermissionAdmin(String submenu) throws InterruptedException {
		HomePage homePage = new HomePage(driver);
		ApplicationMenuPermissionPage applicationMenuPermissionPage = new ApplicationMenuPermissionPage(driver);

		methodName = "verifyMenuPermissionAdmin" + "TS: " + ts.getCurrentTimeAndDate();

		// Step 1. - Navigate to menu permission
		homePage.clickMenuPermission();
		Thread.sleep(1000);
		applicationMenuPermissionPage.switchToMainFrame();

		// Step 2. - Verify the menu New Contract exists
		assertTrue(applicationMenuPermissionPage.verifyMenu(submenu));

	}

	@Test(priority = 3, dataProvider = "DP4")
	public void verifySMSReportPermissionAdmin(String reportName) throws InterruptedException {
		HomePage homePage = new HomePage(driver);
		SMSReportPermissionPage smsReportPermissionPage = new SMSReportPermissionPage(driver);

		methodName = "verifySMSReportPermissionAdmin" + "TS: " + ts.getCurrentTimeAndDate();

		// Step 1. - Navigate to SMS report permission
		homePage.clickSMSReportPermission();
		Thread.sleep(1000);
		smsReportPermissionPage.switchToMainFrame();

		// Step 2. - Verify the menu New Contract exists
		assertTrue(smsReportPermissionPage.verifyReport(reportName));
	}
}
