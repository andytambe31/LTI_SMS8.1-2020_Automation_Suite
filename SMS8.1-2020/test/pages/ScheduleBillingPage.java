package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.utilityFunctions;

public class ScheduleBillingPage {

	WebDriver driver;
	JavascriptExecutor js;
	
	utilityFunctions utlFn; 
	
	//frames
	By frameContainer = By.id("frameContainer");
	
	//Buttons
	By generateButton = By.id("btnGenerate");
	By saveButton = By.id("btnSave");	
	By closeButton = By.id("btnClose");
	By newButton = By.id("btnNew");
	
	//radio button
	By absoluteRadioButton = By.id("rdoAbsolute");
	
	//Month checkbox
	By checkJan = By.id("chkJan");
	By checkFeb = By.id("chkFeb");
	
	//Month amount
	By janAmountTextbox = By.id("txtJanAmount");
	By febAmountTextbox = By.id("txtFabAmount");
	
	//Textboxes
	By invoiceDateTextBox = By.id("txtInvoiceDate");
	By billAmountTextBox = By.id("txtBillAmount");
	By billFromTextBox = By.id("txtBillFrom");
	By billToTextBox = By.id("txtBillTo");
	By descriptionTextBox = By.id("txtDescription1");
	
	
	
	public ScheduleBillingPage(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
		this.js = (JavascriptExecutor) driver;
	}
	
	//Frame functions
	public void switchToFrameContainer() {
		driver.switchTo().frame(driver.findElement(frameContainer));
	}
	
	//Button functions
	public void clickOnGenerateButton() {
		driver.findElement(generateButton).click();
	}
	public void clickOnClose() {
		driver.findElement(closeButton).click();
	}
	public void clickOnSave() {
		driver.findElement(saveButton).click();
	}
	public void clickOnNew() {
		driver.findElement(newButton).click();
	}
	
	//Radio button functions
	public void clickOnAbsoluteRadioButton() {
		driver.findElement(absoluteRadioButton).click();
	}
	
	//Month checkbox functions
	public void checkJan() {
		driver.findElement(checkJan).click();
		//driver.findElement(checkJan).sendKeys(Keys.TAB);
	}
	
	public void checkFeb() {
		driver.findElement(checkFeb).click();
	}
	
	//Month amount functions
	public void setJanAmount(String amount) {
		//js.executeScript("arguments[0].value='"+amount+"';", driver.findElement(janAmountTextbox));
		System.out.println(driver);
		utlFn.fastType(driver.findElement(By.xpath("//*")),amount);
		
	}
	
	public void setFebAmount(String amount) {
		js.executeScript("arguments[0].value='"+amount+"';", driver.findElement(febAmountTextbox));
	}
	
	//Textbox functions
	public void setInvoiceDate(String invoiceDate) {
		js.executeScript("arguments[0].value='"+invoiceDate+"';", driver.findElement(invoiceDateTextBox));
	}
	
	public void setBillAmount(String billAmount) {
		js.executeScript("arguments[0].value='"+billAmount+"';", driver.findElement(billAmountTextBox));
	}
	
	public void setBillFrom(String billFrom) {
		js.executeScript("arguments[0].value='"+billFrom+"';", driver.findElement(billFromTextBox));
	}
	
	public void setBillTo(String billTo) {
		js.executeScript("arguments[0].value='"+billTo+"';", driver.findElement(billToTextBox));
	}
	
	public void setDescription(String description) {
		//js.executeScript("arguments[0].value='"+description+"';", driver.findElement(descriptionTextBox));
		utlFn.fastType(driver.findElement(descriptionTextBox),description);
	}
}
