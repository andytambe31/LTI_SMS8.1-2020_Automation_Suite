package scripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import utility.utilityFunctions;

public class TestParameters {

	// Class references
	static utilityFunctions utflnFnc;// = new utilityFunctions(driver);

	// Test parameters
	static String mainWindowHandle;
	static String currentWindowHandle;
	static String previousWindowHandle;
	static String entity;
	static String company;
	static String environment;
	static String databaseURL;
	static String dataBaseName;
	static String OBillingTestPDF;
	static String OBillingReferencePDF;
	static String OBillingReferenceOInvoice;
	static String OBillingReferenceOCN;
	static String OBillingReferenceManualOInvoice;
	static String OBillingReferenceManualOCN;
	static String smokeTestMode;
	static String OBillingMonth;
	static String OBillingYear;
	static String ContractYear;
	static String ContractMonth;
	static String login;
	static String PrintInvoiceURL;
	
	
	public static String getCompany() {
		return company;
	}

	public static void setCompany(String company) {
		TestParameters.company = company;
	}
	

	public static String getPrintInvoiceURL() {
		return PrintInvoiceURL;
	}

	public static void setPrintInvoiceURL(String printInvoiceURL) {
		PrintInvoiceURL = printInvoiceURL;
	}

	public static String getLogin() {
		return login;
	}

	public static void setLogin(String login) {
		TestParameters.login = login;
	}

	public static String getContractYear() {
		return ContractYear;
	}

	public static void setContractYear(String contractYear) {
		ContractYear = contractYear;
	}

	public static String getContractMonth() {
		return ContractMonth;
	}

	public static void setContractMonth(String contractMonth) {
		ContractMonth = contractMonth;
	}

	public static String getOBillingMonth() {
		return OBillingMonth;
	}

	public static void setOBillingMonth(String oBillingMonth) {
		OBillingMonth = oBillingMonth;
	}

	public static String getOBillingYear() {
		return OBillingYear;
	}

	public static void setOBillingYear(String oBillingYear) {
		OBillingYear = oBillingYear;
	}

	public static String getSmokeTestMode() {
		return smokeTestMode;
	}

	public static void setSmokeTestMode(String smokeTestMode) {
		TestParameters.smokeTestMode = smokeTestMode;
	}

	public static String getDatabaseURL() {
		return databaseURL;
	}

	public static void setDatabaseURL(String databaseURL) {
		TestParameters.databaseURL = databaseURL;
	}

	public static String getDataBaseName() {
		return dataBaseName;
	}

	public static void setDataBaseName(String dataBaseName) {
		TestParameters.dataBaseName = dataBaseName;
	}

	public static String getOBillingReferenceOInvoice() {
		return OBillingReferenceOInvoice;
	}

	public static String getOBillingReferenceOCN() {
		return OBillingReferenceOCN;
	}

	public static String getOBillingReferenceManualOInvoice() {
		return OBillingReferenceManualOInvoice;
	}

	public static String getOBillingReferenceManualOCN() {
		return OBillingReferenceManualOCN;
	}

	public static String getOBillingTestPDF() {
		return OBillingTestPDF;
	}

	public static void setOBillingTestPDF(String oBillingTestPDF) {
		OBillingTestPDF = oBillingTestPDF;
	}

	public static String getOBillingReferencePDF() {
		return OBillingReferencePDF;
	}

	public static void setOBillingReferencePDF(String oBillingReferencePDF) {
		OBillingReferencePDF = oBillingReferencePDF;

		// For setting invoice references
		utflnFnc = new utilityFunctions(driver);

		// Comma separated values entered in Excel file
		String[] refs = utflnFnc.splitString(OBillingReferencePDF, ",");

		OBillingReferenceOInvoice = refs[0];
		OBillingReferenceOCN = refs[1];
		OBillingReferenceManualOInvoice = refs[2];
		OBillingReferenceManualOCN = refs[3];

	}

	public static String getEntity() {
		return entity;
	}

	public static void setEntity(String entity) {
		TestParameters.entity = entity;
	}

	public static String getEnvironment() {
		return environment;
	}

	public static void setEnvironment(String environment) {
		TestParameters.environment = environment;
	}

	static WebDriver driver;

	public String getMainWindowHandle() {
		return mainWindowHandle;
	}

	public void setMainWindowHandle(String mainWindowHandle) {
		this.mainWindowHandle = mainWindowHandle;
	}

	public String getCurrentWindowHandle() {
		return currentWindowHandle;
	}

	public void setCurrentWindowHandle(String currentWindowHandle) {
		this.currentWindowHandle = currentWindowHandle;
	}

	public String getPreviousWindowHandle() {
		return previousWindowHandle;
	}

	public void setPreviousWindowHandle(String previousWindowHandle) {
		this.previousWindowHandle = previousWindowHandle;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
		this.driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

}
