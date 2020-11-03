package testCases;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.LoginPage;
import scripts.CreateDataForSmokeTest;
import scripts.GetStartDate;
//import pages.LoginPage;
import scripts.TestParameters;
import scripts.handleIESave;
import utility.QueryRunner;
import utility.ReadFromExcel;
//import utility.WindowSwitching;

public class PreSmokeTestSetupClass {

	String driverPath = "./test/resources/drivers/";
	String mainWindowHandle = null;
	String currentWindowHandle = null;
	String previousWindowHandle = null;

	String pathToExcelFile = "./test/resources/data/";
	String fileName = "preSmokeTestData.xlsx";
	boolean testStart = false;

	@DataProvider(name = "DP1")
	public Object[][] createDataForCreateBuilding() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData("./test/resources/data/preSmokeTestData.xlsx", "Data");
		return retObjArr;
	}

	@Test(priority = 0, dataProvider = "DP1")

	public void preSmokeTest(String Environment, String Link, String country, String Entity, String databaseURL,
			String dataBaseName, String loginId, String password, String IsPeformSmokeTest, String OBillingTestPDF,
			String OBillingReferencePDF, String TBillingTestPDF, String TBillingReferencePDF, String smokeTestMode,
			String printInvoiceURL) throws ClassNotFoundException, IOException, SQLException, InterruptedException {

		LoginPage loginPage;// = new LoginPage(driver);

		if (true) {
			if (IsPeformSmokeTest.equals("Yes")) {
				testStart = true;

				// Test Parameter page
				TestParameters tstPar = new TestParameters();

				// Create driver
				System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer2.53.1.exe");
				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true); //
				caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true); //
				// caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);//
				// -manas
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

				// Set entity and environment
				tstPar.setEntity(country);
				if (Entity.equals("Qatar QC")) {
					Entity = "QATAR QC";
				}
				tstPar.setCompany(Entity);
				tstPar.setEnvironment(Environment);
				tstPar.setOBillingReferencePDF(OBillingReferencePDF);
				tstPar.setOBillingTestPDF(OBillingTestPDF);
				tstPar.setDatabaseURL(databaseURL);
				tstPar.setDataBaseName(dataBaseName);
				tstPar.setPrintInvoiceURL(printInvoiceURL);
				tstPar.setSmokeTestMode(smokeTestMode);

				// handleIESave ieSave = new handleIESave();

				// ieSave.passFirfoxDownloadDialogUseRobot(driver);

				System.out.println("*******\tTest Started\t*******");

				switch (smokeTestMode) {
				case "InvoiceVerification":
					break;
				case "Complete":
					// case "demo":

					WebDriver driver = new InternetExplorerDriver(caps);
					loginPage = new LoginPage(driver);
					loginPage.makeFullscreen();

					if (!Environment.equals("Production")) {
						// Set Billing Calendar
						QueryRunner qrB = new QueryRunner();
						ReadFromExcel readFromeExcel = new ReadFromExcel();
						String query = readFromeExcel.getExcelData("./test/resources/data/Query.xlsx",
								"BillingMonth")[0][0];
						String[][] billingCalendar = qrB.runQuery(query, tstPar.getDataBaseName(),
								tstPar.getDatabaseURL()); // Uncomment
						tstPar.setOBillingMonth(billingCalendar[0][1]);
						tstPar.setOBillingYear(billingCalendar[0][0]);

						// Set Contract Calendar
						QueryRunner qrC = new QueryRunner();
						ReadFromExcel readFromeExcel1 = new ReadFromExcel();
						String query1 = readFromeExcel.getExcelData("./test/resources/data/Query.xlsx",
								"ContractMonth")[0][0];
						String[][] contractCalendar = qrC.runQuery(query, tstPar.getDataBaseName(),
								tstPar.getDatabaseURL());// Uncomment
						tstPar.setContractMonth(contractCalendar[0][1]);
						tstPar.setContractYear(contractCalendar[0][0]);
					}

					// Assign values to test parameters
					tstPar.setDriver(driver);

					// Set values for window handles
					mainWindowHandle = driver.getWindowHandle();// set window handles
					currentWindowHandle = mainWindowHandle;
					previousWindowHandle = null;

					// Passing value to TestParameter
					tstPar.setCurrentWindowHandle(currentWindowHandle);
					tstPar.setMainWindowHandle(mainWindowHandle);
					tstPar.setPreviousWindowHandle(previousWindowHandle);

					// Prepare Excel sheets
					CreateDataForSmokeTest obj = new CreateDataForSmokeTest();
					obj.writeDataInExcel();

					// Log In
					driver.navigate().to(Link + "Login.aspx?ContractEstimateId=");
					loginPage.loginToSMS(loginId, password, Entity);
					tstPar.setLogin(loginId);
					break;
				default:
					System.out.println("Invalid Mode");
					break;
				}

			}
		}
	}
}
