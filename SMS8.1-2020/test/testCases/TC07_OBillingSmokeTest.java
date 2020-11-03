package testCases;

import org.testng.annotations.Test;

import junit.framework.Assert;
import pages.GenerateOBillingPage;
import pages.CommonElements;
import pages.DisplayCrystalReportPage;
import pages.HomePage;
import pages.ManualOInvoice;
import pages.SMSConfirmPopUp;
import scripts.RenameFile;
import scripts.ResultWriter;
import scripts.Sikuli;
import scripts.TestParameters;
import scripts.TimeStamp;
import scripts.handleIESave;
import utility.ReadFromExcel;
import utility.TakeScreenShot;
import utility.WindowSwitching;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class TC12_OBillingSmokeTest {

	// Class objects
	TestParameters tstParameters = new TestParameters();
	TakeScreenShot takeSS;
	ResultWriter rw;
	CommonElements commonElements;
	TimeStamp ts = new TimeStamp();

	// Driver related
	WebDriver driver;
	String driverPath = "./test/resources/drivers/";
	String mainWindowHandle = null;
	String currentWindowHandle = null;
	String previousWindowHandle = null;

	// Excel related
	String fileResult = "SmokeTestResultSheet.xlsx";
	String pathToExcelFile = "./test/resources/data/";
	String fileName = "obillingData.xlsx";
	String screenshotPath = "./test/resources/screenshot/";

	// Misc
	boolean contractFlag = true;
	String methodName = null;
	int createCustomerFlag = 0;

	@DataProvider(name = "DP1")
	public Object[][] createManualOInvoice() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "oManualInvoice");
		return retObjArr;
	}

	/*@Test(priority = 0, dataProvider = "DP1")
	public void manualOInvoice(String description, String contractNumber, String invoiceAmount, String billFrom,
			String billTo) throws InterruptedException {

		methodName = "manualOInvoice" + "TS: " + ts.getCurrentTimeAndDate();

		// Clas reference
		HomePage homePage = new HomePage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		ManualOInvoice manualInvoice = new ManualOInvoice(driver);

		// Step 1. - Navigate to OBilling > Manual O Invoice
		homePage.clickOnManualOInvoice();

		// Step 2. - Enter Contract Number
		manualInvoice.setcontractNumber(contractNumber);

		// Step 3. - Set bill amount
		manualInvoice.setBillAmount(invoiceAmount);

		// Step 4. - Set Bill from
		manualInvoice.setBillFrom(billFrom);

		// Step 5. - Set Bill to
		manualInvoice.setBillTo(billTo);

		// Step 6. - Set description
		manualInvoice.setDescription(description);

		// Step 7. - Set invoice group
		manualInvoice.selectInvoiceGroup();

		// Step 8. - Click on <<Save>>
		manualInvoice.clickSaveButton();

		// Step 8. - Verify if Manual Invoice is added
		if (manualInvoice.checkIfManualInvoiceCreated(contractNumber, invoiceAmount)) {
			Assert.assertTrue(true);
		} else
			Assert.assertTrue(false);

	}*/
	

	@Test(priority = 1)
	public void generateBilling() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		GenerateOBillingPage billingPage = new GenerateOBillingPage(driver);
		DisplayCrystalReportPage crystal = new DisplayCrystalReportPage(driver);
		SMSConfirmPopUp confirmPopUp = new SMSConfirmPopUp(driver);
		handleIESave ieSave = new handleIESave();

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

		// Step 4. - Click on Invoice List 
		billingPage.clickOnInvoiceList();
		Thread.sleep(2000);
		ieSave.clickAltS(driver);
		ieSave.clickTab(driver);
		ieSave.clickEnter(driver);
		//ieSave.clickAltF4(driver);

		// Step 5. - Click on Print invoice //
		billingPage.clickOnPrintInvoice(); //
		System.out.println("Print Invoice"); // 
		ieSave.clickEnter(driver);

		// Thread.sleep(1000); //
		windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle); // { //
		mainWindowHandle = windowSwitching.getMainWindowHandle(); //
		currentWindowHandle = windowSwitching.getCurrentWindowHandle(); //
		previousWindowHandle = windowSwitching.getPreviousWindowHandle(); //
		System.out.println(mainWindowHandle); //
		System.out.println(currentWindowHandle); //
		System.out.println(previousWindowHandle); // }

	}

	@Test(priority = 1)
	public void printAndExportInvoice() throws InterruptedException, FindFailed {

		HomePage homePage = new HomePage(driver);
		WindowSwitching windowSwitching = new WindowSwitching(driver);
		GenerateOBillingPage billingPage = new GenerateOBillingPage(driver);
		DisplayCrystalReportPage crystal = new DisplayCrystalReportPage(driver);
		SMSConfirmPopUp confirmPopUp = new SMSConfirmPopUp(driver);
		handleIESave ieSave = new handleIESave();
		methodName = "printAndExportInvoice";
		RenameFile rf = new RenameFile();
		rf.delete();

		// ******************Crystal Report***********************
		System.out.println("***************Crystal Report*******************");
		driver.navigate().to(tstParameters.getPrintInvoiceURL());

		// Step 1. Click on Export button 
		crystal.switchToMainFrame();

		crystal.clickOnExportButton();

		// Step 2. - Click on Dropdown button 
		crystal.clickOnDropDown();

		// Step 3. - Select PDF 
		Thread.sleep(1000); 
		crystal.clickOnPDF();

		// Step 4. - Click on Export 
		crystal.clickOnExport();
		System.out.println("**********Export**********");

		Thread.sleep(5000);
		while (!rf.fileFound()) {
			ieSave.clickAltS(driver);
		}

		Thread.sleep(5000);
		rf.renameAndMove();

	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("*******	O Billing Smoke Test started	*******");

		// Get driver value from
		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		takeSS = new TakeScreenShot(driver);
		rw = new ResultWriter(driver, mainWindowHandle);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@BeforeMethod
	public void clickOnOBillingMenu() {

		HomePage homePage = new HomePage(driver);
		try {
			driver.switchTo().defaultContent();
			homePage.clickOBillingMenu();
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
