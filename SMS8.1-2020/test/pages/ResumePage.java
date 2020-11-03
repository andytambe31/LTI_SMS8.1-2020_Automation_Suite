package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.utilityFunctions;

public class ResumePage {
	
	WebDriver driver;
	utilityFunctions utlFn;
	
	//Constructor
	public ResumePage(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
	}
	
	//Frames
	By mainFrame = By.id("mainFrame");
		
	//Inputs
	By contractNo = By.id("txtContractNo");
	By resumeDate = By.id("txtResumeDate");
	
	//Button
	By contractSearchButton = By.id("btnSearchContract");
	By addButton = By.id("btnAdd");
	By addAllButton = By.id("btnAddAll");
	By saveButton = By.id("btnSave");
	
	//Grids
	By unitsGrid = By.xpath("//table[@id='grdUnitsOnContract']/tbody/tr/td[2]");
	
	//Frame functions
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}
	
	//Input functions
	public void setTransactionDate(String date) {
		utlFn.fastType(driver.findElement(resumeDate), date);
	}
	
	//Button functions
	public void clickContractSearchButton() {
		driver.findElement(contractSearchButton).click();
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
}
