package testCases;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pages.CommonElements;
import pages.DisplayCrystalReportPage;
import pages.GenerateOBillingPage;
import pages.HomePage;
import pages.SMSConfirmPopUp;
import pages.UnitPage;
import scripts.ComparePreAndPostBilling;
import scripts.ResultWriter;
import scripts.TestParameters;
import scripts.TimeStamp;
import scripts.handleIESave;
import utility.ReadFromExcel;
import utility.WindowSwitching;
import utility.WriteToExcelFile;

public class TC00_PreAndPostBilling {

	// Class references
	TestParameters tstParameters = new TestParameters();
	WriteToExcelFile write = new WriteToExcelFile();
	ReadFromExcel readFromeExcel = new ReadFromExcel();
	ResultWriter rw;// = new ResultWriter();
	WindowSwitching windowSwitching;
	TimeStamp ts = new TimeStamp();
	HomePage homePage;

	// Driver
	String driverPath = "./test/resources/drivers";
	String mainWindowHandle = null;
	String currentWindowHandle = null;
	String previousWindowHandle = null;
	WebDriver driver;

	// Misc
	String methodName = "";

	@Test
	public void checkPreAndPostBilling_AnyEntity()
			throws InterruptedException, ClassNotFoundException, IOException, SQLException {
		HomePage homePage = new HomePage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		GenerateOBillingPage billingPage = new GenerateOBillingPage(driver);
		DisplayCrystalReportPage crystal = new DisplayCrystalReportPage(driver);
		SMSConfirmPopUp confirmPopUp = new SMSConfirmPopUp(driver);
		handleIESave ieSave = new handleIESave();
		ComparePreAndPostBilling comp = new ComparePreAndPostBilling();

		methodName = "checkPreAndPostBilling_AnyEntity" + "TS: " + ts.getCurrentTimeAndDate();

		// ***************Generate Billing*******************

		System.out.println("***************Generate Billing*******************");

		// Step 1. - Navigate to Generate O Billing
		homePage.clickOnGenerateOBilling();
		Thread.sleep(1000);

		// Step 2. - Click on Discard Billing if enabled

		billingPage.switchToMainFrame();
		if (billingPage.checkIfDiscardButtonIsEnabled() == true) {
			billingPage.clickOnDiscardBilling();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);
			{
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			confirmPopUp.clickYesButton();
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{ // For getting window handles after switching back

				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			billingPage.switchToMainFrame();
		}

		// Step 3. - Click on Generate Billing button

		billingPage.clickOnGenerateBilling();
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);
		{
			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		confirmPopUp.clickOKButton();
		windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
		{ // For getting window handles after switching back

			mainWindowHandle = windowSwitching.getMainWindowHandle();
			currentWindowHandle = windowSwitching.getCurrentWindowHandle();
			previousWindowHandle = windowSwitching.getPreviousWindowHandle();
		}
		billingPage.switchToMainFrame();

		// Compare pre and Post migration
		if (comp.compareBilling()) {
			Assert.assertTrue(true);
		} else
			Assert.assertTrue(false);
	}

	/*
	 * @Test public void checkPreAndPostBilling_India() { }
	 */

	@BeforeMethod
	public void clickOnContractMenu() {
		homePage = new HomePage(driver);
		driver.switchTo().defaultContent();
		homePage.clickOBillingMenu();
	}

	@AfterMethod
	public void testCaseOver(ITestResult result) throws IOException {
		rw.setResults(result, methodName);
	}

	@BeforeClass
	public void setUp() throws IOException {
		System.out.println("*******\tPre and Post Migration Billing test started\t*******");

		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		rw = new ResultWriter(driver, mainWindowHandle);

	}

	@AfterClass
	public void closeWindow() {
		// driver.quit();
	}
}
