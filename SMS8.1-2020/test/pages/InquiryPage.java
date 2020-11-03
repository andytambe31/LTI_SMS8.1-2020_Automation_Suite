package pages;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import scripts.DateConversion;
import testCases.TC03_Inquiry;

import java.text.ParseException;
import java.time.LocalDateTime;

import utility.ReadFromExcel;
import utility.utilityFunctions;

public class InquiryPage {

	WebDriver driver;
	JavascriptExecutor js;
	utilityFunctions utlFn;

	String verifyDetailsCPH = "contractType-=-txtContractType;contractStartDate-=-txtContractStartDate;contractEndDate-=-txtContractendDate;buildingId-=-txtBuildingId;buildingName-=-txtBuildingName;buildingAddress-=-txtBuildingAddress;billingClass-=-txtBillingClass;paClass-=-txtPAClass;currentUnitCount-=-txtCurrentUnits;currentUnitPrice-=-txtPrice;currentCommsCount-=-txtComms;currentCommsPrice-=-txtCurrentCommsPrice;billedUnitCount-=-txtBilledUnits;billedUnitPrice-=-txtBilledPrice;billedCommsCount-=-txtBilledComms;billedCommsPrice-=-txtBilledCommsPrice;contractPriceHistory-=-txtBuildingId;OSAmount-=-txtOSAmount;price1-=-//table[@id,'grdContractPriceHistoryGrid']/tbody/tr[1]/td[1];effectiveDate-=-//table[@id,'grdContractPriceHistoryGrid']/tbody/tr[1]/td[2];addedOn-=-//table[@id,'grdContractPriceHistoryGrid']/tbody/tr[1]/td[3];transactionType-=-//table[@id,'grdContractPriceHistoryGrid']/tbody/tr[1]/td[4];units/Comms-=-//table[@id,'grdContractPriceHistoryGrid']/tbody/tr[1]/td[5];";
	String verifyDetailsUnitBuilding = "supervisor-=-txtsupervisor;";
	
	//Checkbox
	By annualPriceCheckbox = By.id("chkAnnualPrice");
	
	By mainFrame = By.id("mainFrame");
	By associateFrame = By.id("associateFrame");
	By associateSearchTextBox = By.id("txtSearch");
	By frameContainer = By.id("frameContainer");
	By saveButton = By.id("btnSave");
	By deleteButton = By.id("btnDelete");
	By associateGrid = By.xpath("//table[@id='grdAssociate']//tr//td[2]");
	By selectButton = By.id("btnSelect");
	By contractNoTxt = By.id("txtContractNo");
	By customerIdTxt = By.id("txtCustomerId");
	By unitNoTxt = By.id("txtUnitNo");
	By buildingIdTxt = By.id("txtBuildingId");
	By txtPrice = By.id("txtPrice");
	By txtPrice1 = By.id("txtCurrentPrice");
	By startDate = By.id("txtContractStartDate");
	By buildingId = By.id("txtBuildingId");
	By buildingAdd = By.id("txtBuildingAddress");
	By buildingName = By.id("txtBuildingName");
	By unitName = By.id("txtUnitName");
	By customerName = By.id("txtCustomerName");

	By startDateGrid = By.xpath("//table[@id='grdUnitTransaction']//tr[1]//td[1]");
	By contractIdGrid = By.xpath("//table[@id='grdUnitTransaction']//tr[1]//td[5]");
	By priceGrid = By.xpath("//table[@id='grdUnitTransaction']//tr[1]//td[7]");
	By unitNoGrid = By.xpath("//table[@id='grdUnit']//tr[1]//td[1]");
	By contractNoGrid = By.xpath("//table[@id='grdUnit']//tr[1]//td[4]");
	By contractNoInvoiceGrid = By.xpath("//table[@id='grdInvoiceForCustomer']//tr[1]//td[8]");
	By priceInvoiceGrid = By.xpath("//table[@id='grdInvoiceForCustomer']//tr[1]//td[7]");
	By priceInvoiceContractGrid = By.xpath("//table[@id='grdInvoiceForContract']//tr[1]//td[8]");
	By contractNoInvoiceUnitGrid = By.xpath("//table[@id='grdInvoiceForUnit']//tr[1]//td[8]");
	By priceInvoiceUnitGrid = By.xpath("//table[@id='grdInvoiceForUnit']//tr[1]//td[7]");

	By startDateCustomerGrid = By.xpath("//table[@id='grdContracts']//tr[1]//td[3]");
	By contractNoCustomerGrid = By.xpath("//table[@id='grdContracts']//tr[1]//td[1]");
	By priceCustomerGrid = By.xpath("//table[@id='grdContracts']//tr[1]//td[6]");

	public InquiryPage(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
		this.js = (JavascriptExecutor) driver;
	}
	
	//checkbox functions
	public boolean isChecked() {
		return driver.findElement(annualPriceCheckbox).isSelected();
	}
	
	public void uncheckAnnualPriceCheckbox() {
		
		if(this.isChecked()) {
			driver.findElement(annualPriceCheckbox).click();
		}
	}

	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}

	public void switchToAssociateFrame() {
		// driver.switchTo().frame(driver.findElement(associateFrame));
		driver.switchTo().frame(0);
	}

	public void switchToFrameContainer() {
		driver.switchTo().frame(driver.findElement(frameContainer));
	}

	public void setAssociateSearch(String searchText) {
		js.executeScript("arguments[0].value='" + searchText + "';", driver.findElement(associateSearchTextBox));
	}

	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}

	public void clickSaveButton() {

		driver.findElement(saveButton).click();
	}

	public void clickDeleteButton() {

		driver.findElement(deleteButton).click();
	}

	public void setContractNumber(String contractNo) {

		driver.findElement(contractNoTxt).sendKeys(contractNo);
		driver.findElement(contractNoTxt).sendKeys(Keys.ENTER);

	}

	public void setCustomerNumber(String customerId) {

		driver.findElement(customerIdTxt).sendKeys(customerId);
		driver.findElement(customerIdTxt).sendKeys(Keys.ENTER);

	}

	public void setUnitNumber(String unitNo) {

		driver.findElement(unitNoTxt).sendKeys(unitNo);
		driver.findElement(unitNoTxt).sendKeys(Keys.ENTER);

	}

	public void setBuildingNumber(String buildingId) {

		driver.findElement(buildingIdTxt).sendKeys(buildingId);
		driver.findElement(buildingIdTxt).sendKeys(Keys.ENTER);

	}

	public String getPrice() {
		WebElement e = driver.findElement(txtPrice);

		String price = e.getAttribute("value");

		String[] price1 = price.split(" ");
		String[] price2 = price1[1].split("/");
		// System.out.println("price =" + price2[0]);
		return price2[0];

	}

	public String getPrice1() {
		WebElement e = driver.findElement(txtPrice1);

		String price = e.getAttribute("value");

		String[] price1 = price.split(" ");
		String[] price2 = price1[1].split("/");
		// System.out.println("price =" + price2[0]);
		return price2[0];

	}

	public String getStartDate() {
		WebElement e = driver.findElement(startDate);
		String startDate = e.getAttribute("value");
		return startDate;

	}

	public String getUnitTransStartDate() {
		WebElement e = driver.findElement(startDateGrid);
		String startDate = e.getText();
		return startDate;

	}

	public String getContractsForCustomerStartDate() {
		WebElement e = driver.findElement(startDateCustomerGrid);
		String startDate = e.getText();
		return startDate;

	}

	public String getContractsForCustomerContractNo() {
		WebElement e = driver.findElement(contractNoCustomerGrid);
		String contractNo = e.getText();
		return contractNo;

	}

	public String getContractsForCustomerPrice() {
		WebElement e = driver.findElement(priceCustomerGrid);
		String price = e.getText();
		String[] price1 = price.split(" ");
		String[] price2 = price1[1].split("/");
		// System.out.println("price =" + price2[0]);
		return price2[0];

	}

	public String getUnitTransStartContractId() {
		WebElement e = driver.findElement(contractIdGrid);
		String contractId = e.getText();
		return contractId;

	}

	public String getUnitTransactionPrice() {
		WebElement e = driver.findElement(priceGrid);
		String price = e.getText();
		String[] price1 = price.split(" ");
		String[] price2 = price1[1].split("/");
		// System.out.println("price =" + price2[0]);
		return price2[0];
	}

	public String getUnitBuildingUnitNo() {
		WebElement e = driver.findElement(unitNoGrid);
		String unitNo = e.getText();
		return unitNo;

	}

	public String getUnitBuildingContractNo() {
		WebElement e = driver.findElement(contractNoGrid);
		String contractNo = e.getText();
		return contractNo;

	}

	public String getInvoiceCustomerContractNo() {
		WebElement e = driver.findElement(contractNoInvoiceGrid);
		String contractNo = e.getText();
		return contractNo;

	}

	public String getInvoiceUnitContractNo() {
		WebElement e = driver.findElement(contractNoInvoiceUnitGrid);
		String contractNo = e.getText();
		return contractNo;

	}

	public String getInvoiceUnitPrice() {
		WebElement e = driver.findElement(priceInvoiceUnitGrid);
		String price = e.getText();
		String[] price1 = price.split("Y");
		String[] price2 = price1[1].split("/");
		// System.out.println("price =" + price2[0]);
		return price2[0];
	}

	public String getInvoiceCustomerPrice() {
		WebElement e = driver.findElement(priceInvoiceGrid);
		String price = e.getText();
		String[] price1 = price.split("Y");
		String[] price2 = price1[1].split("/");
		// System.out.println("price =" + price2[0]);
		return price2[0];
	}

	public String getInvoiceContractPrice() {
		WebElement e = driver.findElement(priceInvoiceContractGrid);
		String price = e.getText();
		String[] price1 = price.split("Y");
		String[] price2 = price1[1].split("/");
		// System.out.println("price =" + price2[0]);
		return price2[0];
	}

	public String getBuildingId() {
		WebElement e = driver.findElement(buildingId);
		String buildingId = e.getAttribute("value");
		return buildingId;

	}

	public void getBuildingId2() {
		utlFn.waitForDetailsToLoad(driver.findElement(buildingId), "value");
	}

	public void getBuildingAdd() {
		utlFn.waitForDetailsToLoad(driver.findElement(buildingAdd), "value");
	}

	public void getBuildingName() {
		utlFn.waitForDetailsToLoad(driver.findElement(buildingName), "value");
	}

	public void getUnitName() {
		utlFn.waitForDetailsToLoad(driver.findElement(unitName), "value");
	}

	public void getCustomerName() {
		utlFn.waitForDetailsToLoad(driver.findElement(customerName), "value");
	}

	// Misc

	public Map<String, String> splitDetails(String str) {
		String[] wordList = str.split(";");

		Map<String, String> mp = new HashMap<String, String>();

		for (String st : wordList) {
			String[] temp = st.split("-=-");
			mp.put(temp[0], temp[1]);
		}

		return mp;
	}

	public Map<String, String> mapOnScreenData(Map<String, String> mp, Map<String, String> dataFromExcel) {

		// Get keyset
		Set<String> keySet = mp.keySet();

		// Keyset of data from excel
		Set<String> dataExcelKeySet = dataFromExcel.keySet();

		// Map
		Map<String, String> retMap = new HashMap<String, String>();

		for (String key : keySet) {

			if (dataExcelKeySet.contains(key)) {
				String loc = mp.get(key);
				By byObj;

				if (loc.contains("/")) {
					byObj = By.xpath(loc);
				} else {
					byObj = By.id(loc);
				}

				//try {
					String act = driver.findElement(byObj).getAttribute("value");
					retMap.put(key, act.replaceAll("\r\n", ""));
				//} catch (Exception e) {
				//	System.out.println("Error");
				//}
			}
		}

		return retMap;
	}

	public String verifyData(String menuName, String searchText, String searchDetails)
			throws InterruptedException, ParseException {

		// Class objects
		TC03_Inquiry tc = new TC03_Inquiry();
		InquiryPage inquiryPage = new InquiryPage(driver);
		DateConversion dc = new DateConversion();

		// HashMap
		Map dataFromExcel = new HashMap();
		Map dataFromScreen = new HashMap();
		Map locatorsFromScreen = new HashMap();

		// Expected result
		dataFromExcel.putAll(this.splitDetails(searchDetails));
		System.out.println("        1. Expected data fetched");

		// Wait for details to load

		switch (menuName) {
		case "Units for a Contract":
			/// inquiryPage.getBuildingId2();
			// break;

		case "Unit Transactions":
			// inquiryPage.getUnitName();
			// break;

		case "Contracts For A Customer":
		case "Invoices For A Customer":
			// inquiryPage.getCustomerName();
			// break;

		case "Contract Price History":
			// inquiryPage.getBuildingId2();
			locatorsFromScreen.putAll(this.splitDetails(verifyDetailsCPH));
			break;

		case "Units for a Building":
			verifyDetailsCPH = "supervisor-=-txtsupervisor";
			locatorsFromScreen.putAll(this.splitDetails(verifyDetailsCPH));
			// inquiryPage.getBuildingAdd();
			break;

		case "Invoices For A Contract":
		case "Invoices For A Unit":
			// inquiryPage.getBuildingName();
			break;

		default:
			break;
		}

		// Get data on Screen
		dataFromScreen.putAll(this.mapOnScreenData(locatorsFromScreen, dataFromExcel));
		System.out.println("        2. Expected data fetched");

		// Get keyset
		Set<String> smsInquiryFeildNames = dataFromScreen.keySet();
		String ret = "";
		int count = 0;

		if (smsInquiryFeildNames.size() != dataFromExcel.size()) {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXX Field Count not matching XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			SoftAssert softAssert = new SoftAssert();
			softAssert.assertTrue(false);
			
		}
		for (String fieldName : smsInquiryFeildNames) {
			count++;
			// Get excel and on-screen data

			System.out.print("Value " + count + ": ");
			String excelData = (String) dataFromExcel.get(fieldName);
			String onScreenData = (String) dataFromScreen.get(fieldName);

			System.out.println(" Actual: " + onScreenData + "  Exp: " + excelData);
			if (this.match(onScreenData, excelData)) {
			} else {
				ret = "   " + ret + fieldName + "\n";
				// System.out.println(onScreenData + " doesn't Matches");
			}
			if (count == smsInquiryFeildNames.size() && ret.equals("")) {
				ret = "Pass";
			}
		}

		return ret;
	}

	public boolean match(String onScreenData, String excelData) {
		if (onScreenData.equals(excelData)) {

			return true;
		} else {

			return false;

		}
	}

}
