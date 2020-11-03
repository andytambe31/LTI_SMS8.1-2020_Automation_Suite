package pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import utility.utilityFunctions;

public class CancelPage {
	
	WebDriver driver;
	JavascriptExecutor js;
	
	utilityFunctions utlFn;
	DateTimeFormatter dtf;
	LocalDateTime now;
	
	public CancelPage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver; 
		utlFn = new utilityFunctions(driver);
	}

	//Frames
	By mainFrame = By.id("mainFrame");
	
	//TextBoxes
	By contractNo = By.id("txtContractNo");
	By dateTextBox = By.id("txtCanceldate");
	
	//Button
	By contractSearchButton = By.id("btnSearchContract");
	By reasonButton = By.id("btnReason");
	By subReasonButton = By.id("btnSubReason");
	
	By addButton = By.id("btnAdd");
	By addAllButton = By.id("btnAddAll");
	By saveButton = By.id("btnSave");
	
	//Grids
	By unitsGrid = By.xpath("//table[@id='grdUnitsOnContract']/tbody/tr/td[2]");
	By reasonGrid = By.xpath("//table[@id='grdCancelReason']/tbody/tr/td[2]");
	By subReasonGrid = By.xpath("//table[@id='grdCancelSubReason']/tbody/tr/td[2]");
	
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}
	
	//Textbox functions
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
}
