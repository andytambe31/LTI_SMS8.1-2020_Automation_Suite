package testCases;

import org.testng.annotations.Test;

//import pages.BuildingNotesPopUp;
//import pages.BuildingPage;
import pages.CommonElements;
//import pages.ContactNotesPopUp;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import pages.SMSConfirmPopUp;
import scripts.AlertHandle;
import scripts.PreTestWindowCheck;
import scripts.ResultWriter;
import scripts.TestParameters;
import scripts.TimeStamp;
import utility.GetLoginIdForMethodName;
//import pages.SMSConfirmPopUp;
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

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class TC03_ProductSmokeTest {

	// Class references
	TestParameters tstParameters = new TestParameters();
	TimeStamp ts = new TimeStamp();
	TakeScreenShot takeSS;
	ResultWriter rw;
	WindowSwitching windowSwitching;
	CommonElements commonElements;
	AlertHandle alert;
	WriteToExcelFile write = new WriteToExcelFile();
	PreTestWindowCheck preTest;
	GetLoginIdForMethodName loginId = new GetLoginIdForMethodName();

	String driverPath = "./test/resources/drivers/";
	String mainWindowHandle = null;
	String currentWindowHandle = null;
	String previousWindowHandle = null;
	WebDriver driver;

	List<String> methodNameList = new ArrayList<String>();
	int methodPointer = 0;

	String pathToExcelFile = "./test/resources/data/";
	String fileName = "ProductData.xlsx";
	// String loginId;
	String specGroupName;
	String specShortName;
	String screenshotPath = "./test/resources/screenshot/";

	// Misc
	String methodName = null;

	@DataProvider(name = "DP1")
	public Object[][] createSpecificationGroup() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "CreateSpecificationGroup");
		return retObjArr;
	}

	@DataProvider(name = "DP2")
	public Object[][] editSpecificationGroup() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "EditSpecificationGroup");
		return retObjArr;
	}

	@DataProvider(name = "DP3")
	public Object[][] deleteSpecificationGroup() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "DeleteSpecificationGroup");
		return retObjArr;
	}

	@DataProvider(name = "DP4")
	public Object[][] createSpecification() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "CreateSpecification");
		return retObjArr;
	}

	@DataProvider(name = "DP5")
	public Object[][] editSpecification() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "EditSpecification");
		return retObjArr;
	}

	@DataProvider(name = "DP6")
	public Object[][] deleteSpecification() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "DeleteSpecification");
		return retObjArr;
	}

	@DataProvider(name = "DP7")
	public Object[][] createProduct() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "CreateProduct");
		return retObjArr;
	}

	@DataProvider(name = "DP8")
	public Object[][] editProduct() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "EditProduct");
		return retObjArr;
	}

	@DataProvider(name = "DP9")
	public Object[][] deleteProduct() throws IOException {
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + fileName, "DeleteProduct");
		return retObjArr;
	}

	@Test(priority = 1, dataProvider = "DP1")
	public void createSpecificationGroup(String groupName, String shortName, String flag)
			throws IOException, InterruptedException {
		try {
			methodName = "createSpecificationGroup";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// ********* Create new specification Group logic******************
			String count = groupName.replace("-test", "");
			int counter = Integer.parseUnsignedInt(count);
			counter = counter + 1;
			groupName = String.valueOf(counter) + "-test";

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickSpecificationGroup();

			productPage.switchToMainFrame();

			productPage.setSpecificationGroup(groupName);
			productPage.setSpecificationGroupShortName(shortName);
			productPage.clickSaveButton();

			if (flag.equals("1")) {
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateSpecificationGroup",
						new String[] { groupName }, 1, 0);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateSpecificationGroup",
						new String[] { groupName }, 1, 1);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "EditSpecificationGroup",
						new String[] { groupName }, 1, 0);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateSpecification",
						new String[] { groupName }, 1, 0);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateSpecification",
						new String[] { groupName }, 2, 0);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "EditSpecification",
						new String[] { groupName }, 1, 0);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "DeleteSpecification",
						new String[] { groupName }, 1, 0);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateProduct",
						new String[] { groupName }, 1, 0);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateProduct",
						new String[] { groupName }, 1, 1);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateProduct",
						new String[] { groupName }, 1, 2);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateProduct",
						new String[] { groupName }, 1, 4);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateProduct",
						new String[] { groupName }, 2, 0);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateProduct",
						new String[] { groupName }, 2, 1);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateProduct",
						new String[] { groupName }, 2, 2);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateProduct",
						new String[] { "test" }, 2, 4);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateProduct",
						new String[] { groupName + "_01" }, 1, 3);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateProduct",
						new String[] { groupName + "_02" }, 2, 3);

			} else {
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateSpecificationGroup",
						new String[] { groupName }, 2, 0);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "CreateSpecificationGroup",
						new String[] { groupName }, 1, 1);
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "DeleteSpecificationGroup",
						new String[] { groupName }, 1, 0);
			}

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 2, dataProvider = "DP2")
	public void editSpecificationGroup(String groupName, String shortName, String editGroupName, String editShortName)
			throws IOException, InterruptedException {

		try {
			methodName = "editSpecificationGroup";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickSpecificationGroup();

			productPage.switchToMainFrame();

			productPage.selectSpecificationGroupGrid(groupName);
			// productPage.clearSpecificationGroup();
			// productPage.setSpecificationGroup(editGroupName);
			// productPage.clearSpecificationGroupShortName();
			productPage.setSpecificationGroupShortName(editShortName);
			productPage.clickSaveButton();

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 3, dataProvider = "DP3")
	public void deleteSpecificationGroup(String groupName) throws IOException, InterruptedException {

		try {
			methodName = "deleteSpecificationGroup";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);
			SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickSpecificationGroup();

			productPage.switchToMainFrame();

			productPage.selectSpecificationGroupGrid(groupName);
			productPage.clickDeleteButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
			// window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			/*
			 * List<WebElement> ls = driver.findElements(By.xpath("//*")); for(WebElement
			 * we:ls) {
			 * System.out.println("<"+we.getTagName()+"id='"+we.getAttribute("id")+"'>"+we.
			 * getText()+""); }
			 */

			smsCnfPopUp.clickYesButton();
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			productPage.switchToMainFrame();
		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 4, dataProvider = "DP4")
	public void createSpecification(String groupName, String specName, String specValue)
			throws IOException, InterruptedException {

		try {
			methodName = "createSpecification";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickSpecifications();
			productPage.switchToMainFrame();
			productPage.selectSpecificationGroup(groupName);
			productPage.selectSpecificationGroupCombo(groupName);
			productPage.setSpecificationName(specName);
			Thread.sleep(1000);
			productPage.setSpecificationValue(specValue);
			productPage.clickSaveButton();
		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 5, dataProvider = "DP5")
	public void editSpecification(String groupName, String specName, String editSpecName, String editSpecValue)
			throws IOException, InterruptedException {

		try {
			methodName = "editSpecification";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickSpecifications();
			productPage.switchToMainFrame();
			productPage.selectSpecificationGroup(groupName);
			productPage.selectSpecificationGrid(specName);
			productPage.setSpecificationName(editSpecName);
			productPage.setSpecificationValue(editSpecValue);
			productPage.clickSaveButton();
		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 6, dataProvider = "DP6")
	public void deleteSpecification(String groupName, String specName) throws IOException, InterruptedException {

		try {
			methodName = "deleteSpecification";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);
			SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickSpecifications();
			productPage.switchToMainFrame();
			productPage.selectSpecificationGroup(groupName);
			productPage.selectSpecificationGrid(specName);
			productPage.clickDeleteButton();
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
			productPage.switchToMainFrame();
			productPage.clickSaveButton();
		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 7, dataProvider = "DP1")
	public void createProductGroup(String groupName, String shortName, String flag)
			throws IOException, InterruptedException {

		try {
			methodName = "createProductGroup";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickProductGroup();

			productPage.switchToMainFrame();

			productPage.setProductGroup(groupName);
			productPage.setProductGroupShortName(shortName);
			productPage.clickSaveButton();
		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 8, dataProvider = "DP2")
	public void editProductGroup(String groupName, String shortName, String editGroupName, String editShortName)
			throws IOException, InterruptedException {

		try {
			methodName = "editProductGroup";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickProductGroup();

			productPage.switchToMainFrame();

			productPage.selectProductGroupGrid(groupName);
			// productPage.clearProductGroup();
			// productPage.setProductGroup(editGroupName);
			// productPage.clearProductGroupShortName();
			productPage.setProductGroupShortName(editShortName);
			productPage.clickSaveButton();

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 9, dataProvider = "DP3")
	public void deleteProductGroup(String groupName) throws IOException, InterruptedException {

		try {
			methodName = "deleteProductGroup";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);
			SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickProductGroup();

			productPage.switchToMainFrame();

			productPage.selectProductGroupGrid(groupName);
			productPage.clickDeleteButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
			// window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			/*
			 * List<WebElement> ls = driver.findElements(By.xpath("//*")); for(WebElement
			 * we:ls) {
			 * System.out.println("<"+we.getTagName()+"id='"+we.getAttribute("id")+"'>"+we.
			 * getText()+""); }
			 */

			smsCnfPopUp.clickYesButton();
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			productPage.switchToMainFrame();

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 10, dataProvider = "DP1")
	public void createProductType(String groupName, String shortName, String flag)
			throws IOException, InterruptedException {

		try {
			methodName = "createProductType";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickProductType();

			productPage.switchToMainFrame();

			productPage.setProductType(groupName);
			productPage.setProductGroupShortName(shortName);
			productPage.clickSaveButton();
		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 11, dataProvider = "DP2")
	public void editProductType(String groupName, String shortName, String editGroupName, String editShortName)
			throws IOException, InterruptedException {

		try {
			methodName = "editProductType";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickProductType();

			productPage.switchToMainFrame();

			productPage.selectProductTypeGrid(groupName);
			// productPage.clearProductType();
			// productPage.setProductType(editGroupName);
			// productPage.clearProductGroupShortName();
			productPage.setProductGroupShortName(editShortName);
			productPage.clickSaveButton();

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 12, dataProvider = "DP3")
	public void deleteProductType(String groupName) throws IOException, InterruptedException {

		try {
			methodName = "deleteProductType";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);
			SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickProductType();

			productPage.switchToMainFrame();

			productPage.selectProductTypeGrid(groupName);
			productPage.clickDeleteButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
			// window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			/*
			 * List<WebElement> ls = driver.findElements(By.xpath("//*")); for(WebElement
			 * we:ls) {
			 * System.out.println("<"+we.getTagName()+"id='"+we.getAttribute("id")+"'>"+we.
			 * getText()+""); }
			 */

			smsCnfPopUp.clickYesButton();
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			productPage.switchToMainFrame();

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 13, dataProvider = "DP1")
	public void createModelType(String groupName, String shortName, String flag)
			throws IOException, InterruptedException {

		try {
			methodName = "createModelType";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickModelType();

			productPage.switchToMainFrame();

			productPage.setModelType(groupName);
			productPage.setProductGroupShortName(shortName);
			productPage.clickSaveButton();

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 14, dataProvider = "DP2")
	public void editModelType(String groupName, String shortName, String editGroupName, String editShortName)
			throws IOException, InterruptedException {

		try {
			methodName = "editModelType";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickModelType();

			productPage.switchToMainFrame();

			productPage.selectModelTypeGrid(groupName);
			// productPage.clearModelType();
			// productPage.setModelType(editGroupName);
			// productPage.clearProductGroupShortName();
			productPage.setProductGroupShortName(editShortName);
			productPage.clickSaveButton();

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 15, dataProvider = "DP3")
	public void deleteModelType(String groupName) throws IOException, InterruptedException {

		try {
			methodName = "deleteModelType";
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);
			SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickModelType();

			productPage.switchToMainFrame();

			productPage.selectModelTypeGrid(groupName);
			productPage.clickDeleteButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
			// window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			/*
			 * List<WebElement> ls = driver.findElements(By.xpath("//*")); for(WebElement
			 * we:ls) {
			 * System.out.println("<"+we.getTagName()+"id='"+we.getAttribute("id")+"'>"+we.
			 * getText()+""); }
			 */

			smsCnfPopUp.clickYesButton();
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			productPage.switchToMainFrame();

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 16, dataProvider = "DP7")
	public void createProduct(String modelType, String productType, String productGroup, String txtProductName,
			String shortName, String flag) throws IOException, InterruptedException {

		try {
			methodName = "createProduct" + "TS: " + ts.getCurrentTimeAndDate();
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickProduct();

			productPage.switchToMainFrame();
			productPage.clickSearchModelTypeButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			productPage.switchToAssociateFrame(); // switch to associate grid
			productPage.selectModelType(modelType);
			productPage.clickSelectButton();
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			productPage.switchToMainFrame();
			productPage.clickSearchProductTypeButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			productPage.switchToAssociateFrame();
			productPage.selectProductType(productType);
			productPage.clickSelectButton();
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			productPage.switchToMainFrame();
			productPage.selectProductGroup(productGroup);
			productPage.clearProductName();
			productPage.setProductName(txtProductName);
			productPage.setProductGroupShortName(shortName);
			productPage.clickSaveButton();

			/*
			 * String[] productName = productPage.getProductName();
			 * write.writeExcel(pathToExcelFile + fileName, "DeleteProduct",
			 * productPage.getProductName());
			 */

			if (flag.equals("1")) {
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "EditProduct",
						new String[] { txtProductName }, 1, 0);

			} else {
				write.writeToSpecificPosition(pathToExcelFile + "ProductData.xlsx", "DeleteProduct",
						new String[] { txtProductName }, 1, 0);
			}

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 17, dataProvider = "DP8")
	public void editProduct(String productName, String editProductGroup) throws IOException, InterruptedException {

		try {
			methodName = "editProduct" + "TS: " + ts.getCurrentTimeAndDate();
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickProduct();

			productPage.switchToMainFrame();
			productPage.selectProductGrid(productName);
			productPage.selectProductGroup(editProductGroup);
			productPage.clickSaveButton();

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@Test(priority = 18, dataProvider = "DP9")
	public void deleteProduct(String productName) throws IOException, InterruptedException {

		try {
			methodName = "deleteProduct" + "TS: " + ts.getCurrentTimeAndDate();
			HomePage homePage = new HomePage(driver);
			ProductPage productPage = new ProductPage(driver);
			WindowSwitching windowSwitching = new WindowSwitching(driver);
			SMSConfirmPopUp smsCnfPopUp = new SMSConfirmPopUp(driver);

			// Step 1. - Navigate to Unit Information Screen
			homePage.clickProduct();

			productPage.switchToMainFrame();

			productPage.selectProductGrid(productName);
			productPage.clickDeleteButton();
			windowSwitching.changeWindow(currentWindowHandle, mainWindowHandle);// switch
			// window
			{
				// For getting window handles after switching
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}

			/*
			 * List<WebElement> ls = driver.findElements(By.xpath("//*")); for(WebElement
			 * we:ls) {
			 * System.out.println("<"+we.getTagName()+"id='"+we.getAttribute("id")+"'>"+we.
			 * getText()+""); }
			 */

			smsCnfPopUp.clickYesButton();
			windowSwitching.switchWindowBack(previousWindowHandle, currentWindowHandle, mainWindowHandle);
			{
				// For getting window handles after switching back
				mainWindowHandle = windowSwitching.getMainWindowHandle();
				currentWindowHandle = windowSwitching.getCurrentWindowHandle();
				previousWindowHandle = windowSwitching.getPreviousWindowHandle();
			}
			productPage.switchToMainFrame();

		} catch (UnhandledAlertException e) {
			// TODO: handle exception
			if (alert.isAlertPresent()) {
				alert.handleAlert();
			}
		}

	}

	@BeforeClass
	public void beforeClass() {

		System.out.println("*******\tProduct Smoke Test started\t*******");

		driver = tstParameters.getDriver();
		mainWindowHandle = tstParameters.getMainWindowHandle();
		currentWindowHandle = tstParameters.getCurrentWindowHandle();
		previousWindowHandle = tstParameters.getPreviousWindowHandle();

		takeSS = new TakeScreenShot(driver);
		preTest = new PreTestWindowCheck(driver);
		rw = new ResultWriter(driver, mainWindowHandle);

		// populate the methodNameList
		methodNameList.add("createSpecificationGroup");
		methodNameList.add("createSpecificationGroup");
		methodNameList.add("editSpecificationGroup");
		methodNameList.add("deleteSpecificationGroup");
		methodNameList.add("createSpecification");
		methodNameList.add("createSpecification");
		methodNameList.add("editSpecification");
		methodNameList.add("deleteSpecification");
		methodNameList.add("createProductGroup");
		methodNameList.add("createProductGroup");
		methodNameList.add("editProductGroup");
		methodNameList.add("deleteProductGroup");
		methodNameList.add("createProductType");
		methodNameList.add("createProductType");
		methodNameList.add("editProductType");
		methodNameList.add("deleteProductType");
		methodNameList.add("createModelType");
		methodNameList.add("createModelType");
		methodNameList.add("editModelType");
		methodNameList.add("deleteModelType");
		methodNameList.add("createProduct");
		methodNameList.add("createProduct");
		methodNameList.add("editProduct");
		methodNameList.add("deleteProduct");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
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
			homePage.clickProductMenu();
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
