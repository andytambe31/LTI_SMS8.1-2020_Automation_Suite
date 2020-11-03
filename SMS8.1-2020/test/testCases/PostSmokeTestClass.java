package testCases;

import java.io.IOException;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import scripts.TestParameters;
import utility.ReadFromExcel;
import utility.WriteToExcelFile;

public class PostSmokeTestClass {

	 TestParameters tstParameters = new TestParameters();

	String mainWindowHandle = null;// tstParameters.getMainWindowHandle();
	String currentWindowHandle = null;// tstParameters.getCurrentWindowHandle();
	String previousWindowHandle = null;// tstParameters.getPreviousWindowHandle();

	/*
	 * String pathToExcelFile = "./test/resources/data/"; String fileName =
	 * "SmokeTestResultSheet.xlsx";
	 */

	WebDriver driver;// = tstParameters.getDriver();


	// ReadFromExcel readFromeExcel = new ReadFromExcel();

	@BeforeClass
	public void testCasesSetUp() {
		
		  mainWindowHandle = tstParameters.getMainWindowHandle(); currentWindowHandle =
		  tstParameters.getCurrentWindowHandle(); previousWindowHandle =
		  tstParameters.getPreviousWindowHandle();
		  
		  driver = tstParameters.getDriver();
		 
	}

	@AfterClass
	public void testCaseEnd() throws IOException {
		System.out.println("*******\tTest Ended\t*******");

	}

	@Test
	public void closeWindow() throws IOException {
		driver.quit();
	}
}
