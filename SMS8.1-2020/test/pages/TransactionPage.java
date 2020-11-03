package pages;

import java.util.List;

import org.apache.poi.poifs.crypt.dsig.KeyInfoKeySelector;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import utility.utilityFunctions;

public class TransactionPage {

	WebDriver driver;
	JavascriptExecutor js;
	utilityFunctions utlFn;

	// Frames
	By mainFrame = By.id("mainFrame");

	// Inputs
	By contractNo = By.id("txtContractNo");
	By suspendDateTextBox = By.id("txtSuspendDate");
	By cancelDateTextBox = By.id("txtCanceldate");
	By resumeDateTextBox = By.id("txtResumeDate");
	By renegotiateDateTextBox = By.id("txtEffectiveDate");
	By priceAdjustmentDateTextBox = By.id("txtEffectiveDate");
	By newPriceTextbox = By.id("txtNewPrice");

	// Button
	By contractSearchButton = By.id("btnSearchContract");
	By reasonButton = By.id("btnReason");
	By subReasonButton = By.id("btnSubReason");
	By addButton = By.id("btnAdd");
	By addAllButton = By.id("btnAddAll");
	By saveButton = By.id("btnSave");
	By billingClassButton = By.id("btnBillingClass");
	By selectButton = By.id("btnSelect");
	By typeButton = By.id("BUTTON2");

	// Radio button
	By serviceSuspend = By.id("radService");
	By billingSuspend = By.id("radBilling");
	By bothSuspend = By.id("radBoth");

	// Grids
	By unitsGrid = By.xpath("//table[@id='grdUnitsOnContract']/tbody/tr/td[2]");
	By suspendReasonGrid = By.xpath("//table[@id='grdReasons']/tbody/tr/td[2]");
	By cancelReasonGrid = By.xpath("//table[@id='grdCancelReason']/tbody/tr/td[2]");
	By cancelSubReasonGrid = By.xpath("//table[@id='grdCancelSubReason']/tbody/tr/td[2]");
	By associateGrid = By.xpath("//table[@id='grdAssociate']//tr//td[2]");
	By typeGrid = By.xpath("//table[@id = 'grdType']/tbody/tr/td[2]");

	// Constructor
	public TransactionPage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		utlFn = new utilityFunctions(driver);
	}

	// Frame functions
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}

	// Input functions
	public void setContractNo(String contractNum) {
		driver.findElement(contractNo).sendKeys(contractNum);
	}

	public void setSuspendTransactionDate(String date) {
		utlFn.fastType(driver.findElement(suspendDateTextBox), date);
	}

	public void setCancelTransactionDate(String date) {
		utlFn.fastType(driver.findElement(cancelDateTextBox), date);
	}

	public void setResumeTransactionDate(String date) {
		utlFn.fastType(driver.findElement(resumeDateTextBox), date);
	}

	public void setRenegotiateTransactionDate(String date) {
		utlFn.fastType(driver.findElement(renegotiateDateTextBox), date);
	}
	
	public void setPriceAdjustmentTransactionDate(String date) {
		utlFn.fastType(driver.findElement(priceAdjustmentDateTextBox), date);
	}

	// Button functions
	public void clickContractSearchButton() {
		driver.findElement(contractSearchButton).click();
	}

	public void clickReasonButton() {
		driver.findElement(reasonButton).click();
	}

	public void clickSubReasonButton() {
		driver.findElement(subReasonButton).click();
	}

	public void clickAddButton() {
		driver.findElement(addButton).click();
	}

	public void clickAddAllButton() {
		driver.findElement(addAllButton).click();
	}

	public void clickSaveButton() {
		driver.findElement(saveButton).click();
	}

	public void clickBillingClassButton() {
		driver.findElement(billingClassButton).click();
	}

	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}

	public void clickType() {
		driver.findElement(typeButton).click();
	}

	// Radio button function
	public void setServiceRadButton() {
		driver.findElement(serviceSuspend).click();
	}

	public void setBillingRadButton() {
		driver.findElement(billingSuspend).click();
	}

	public void setBothRadButton() {
		driver.findElement(bothSuspend).click();
	}

	public void clearNewPrice() {
		driver.findElement(newPriceTextbox).clear();
	}

	public void setNewPrice(String newPrice) {
		// this.clearNewPrice();
		// js.executeScript("arguments[0].value='"+newPrice+"';",
		// driver.findElement(newPriceTextbox));
		driver.findElement(newPriceTextbox).sendKeys(Keys.chord(Keys.CONTROL, "a"));

		//driver.findElement(newPriceTextbox).sendKeys(newPrice);
		utlFn.fastType(driver.findElement(newPriceTextbox),newPrice);
	}
	public void setRenegNewPrice(String newPrice) {
		// this.clearNewPrice();
		// js.executeScript("arguments[0].value='"+newPrice+"';",
		// driver.findElement(newPriceTextbox));
		driver.findElement(newPriceTextbox).sendKeys(Keys.chord(Keys.CONTROL, "a"));

		driver.findElement(newPriceTextbox).sendKeys(newPrice+Keys.TAB);
		
		//utlFn.fastType(driver.findElement(newPriceTextbox),newPrice);
	}
	
	public void clickNewPrice() {
		driver.findElement(newPriceTextbox).click();
	
	}

	public void switchToAssociateFrame() {
		// driver.switchTo().frame(driver.findElement(associateFrame));
		driver.switchTo().frame(0);
	}

	// Grid functions
	public void selectUnitFromGrid(String unitNo) {
		List<WebElement> ls = driver.findElements(unitsGrid);
		for (WebElement we : ls) {
			if (unitNo.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void selectSuspendReasonGrid(String reason) {
		List<WebElement> ls = driver.findElements(suspendReasonGrid);
		for (WebElement we : ls) {
			if (reason.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void selectCancelSubReasonGrid(String reason) {
		List<WebElement> ls = driver.findElements(cancelSubReasonGrid);
		for (WebElement we : ls) {
			if (reason.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void selectCancelReasonGrid(String reason) {
		List<WebElement> ls = driver.findElements(cancelReasonGrid);
		for (WebElement we : ls) {
			if (reason.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void selectBillingClass(String billingClass) {
		// this.clickSearchModelTypeButton();
		List<WebElement> ls = driver.findElements(associateGrid);
		for (WebElement we : ls) {

			if (billingClass.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void setType(String type) {
		this.clickType();
		List<WebElement> ls = driver.findElements(typeGrid);
		for (WebElement we : ls) {
			if (type.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void checkSelectionType() {

		List<WebElement> ls = driver.findElements(By.xpath("//table"));

		SMSConfirmPopUp smsConfirmPopUp = new SMSConfirmPopUp(driver);

		for (WebElement we : ls) {

			// System.out.println("<"+we.getTagName()+" id =
			// '"+we.getAttribute("id")+"'>Text = "+we.getText()+"/Value =
			// "+we.getAttribute("value")+"");

			if ("Yes No".equals(we.getText())) {
				smsConfirmPopUp.clickYesButton();

			} else
				smsConfirmPopUp.clickOKButton();

		}

	}

}
