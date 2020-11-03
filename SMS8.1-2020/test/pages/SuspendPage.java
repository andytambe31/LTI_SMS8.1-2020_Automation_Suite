package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.utilityFunctions;

public class SuspendPage {
	
	WebDriver driver;
	JavascriptExecutor js;
	utilityFunctions utlFn;
	
	//Frames
	By mainFrame = By.id("mainFrame");
	
	//Inputs
	By contractNo = By.id("txtContractNo");
	By dateTextBox = By.id("txtSuspendDate");
	
	//Button
	By contractSearchButton = By.id("btnSearchContract");
	By reasonButton = By.id("btnReason");
	By addButton = By.id("btnAdd");
	By addAllButton = By.id("btnAddAll");
	By saveButton = By.id("btnSave");
	
	
	//Radio button
	By serviceSuspend = By.id("radService");
	By billingSuspend = By.id("radBilling");
	By bothSuspend = By.id("radBoth");
	
	//Grids
	By unitsGrid = By.xpath("//table[@id='grdUnitsOnContract']/tbody/tr/td[2]");
	By reasonGrid = By.xpath("//table[@id='grdReasons']/tbody/tr/td[2]");
	
	//Constructor
	public SuspendPage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		utlFn = new utilityFunctions(driver);
	}
	
	//Frame functions
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}
	
	//Input functions
	public void setContractNo(String contractNum) {
		driver.findElement(contractNo).sendKeys(contractNum);
	}
	public void setTransactionDate(String date) {
		utlFn.fastType(driver.findElement(dateTextBox), date);
	}
	
	//Button functions
	public void clickContractSearchButton() {
		driver.findElement(contractSearchButton).click();
	}
	public void clickReasonButton() {
		driver.findElement(reasonButton).click();
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
	
	//Radio button function
	public void setServiceRadButton() {
		driver.findElement(serviceSuspend).click();
	}
	public void setBillingRadButton() {
		driver.findElement(billingSuspend).click();
	}
	public void setBothRadButton() {
		driver.findElement(bothSuspend).click();
	}
	
	//Grid functions
	public void selectUnitFromGrid(String unitNo) {
		List<WebElement> ls = driver.findElements(unitsGrid);
		for(WebElement we:ls) {
			if(unitNo.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}
	public void selectReasonGrid(String reason) {
		List<WebElement> ls = driver.findElements(reasonGrid);
		for(WebElement we:ls) {
			if(reason.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}
}
