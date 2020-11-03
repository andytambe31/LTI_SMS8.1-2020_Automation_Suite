package pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.utilityFunctions;

public class RenegPage {
	
	WebDriver driver;
	JavascriptExecutor js;
	DateTimeFormatter dtf;
	LocalDateTime now;
	utilityFunctions utlFn;
	
	//Web objects
		By contractNoTextBox = By.id("txtContractNo");
		By typeButton = By.id("BUTTON2");
		By typeGrid = By.xpath("//table[@id = 'grdTitle']/tbody/tr/td[2]");
		By effectiveDateTextbox = By.id("btnShowCal");
		By newPriceTextbox = By.id("txtNewPrice");
		By saveButton = By.id("btnSave");
		
	// Methods
	public RenegPage(WebDriver driver)
	{
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		this.utlFn= new utilityFunctions (driver);
		dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		now = LocalDateTime.now();
	}
	
	public void setContractNo(String contractNo) 
	{
		//js.executeScript("arguments[0].value='"+contractNo+"';", driver.findElement(contractNoTextBox));
		utlFn.fastType(driver.findElement(contractNoTextBox), contractNo);
	}
	
	public String[] getContractNo() {
		String contractId[] = {dtf.format(now),driver.findElement(contractNoTextBox).getAttribute("value")};
		return contractId;
	}
		
	// click on type drop-down list
	public void clickType() {
		driver.findElement(typeButton).click();
	}
	
	// Select type from dropdown
	public void setType(String type) 
	{
		this.clickType();
		List<WebElement> ls =driver.findElements(typeGrid);  
		  for(WebElement we:ls) {
			  if(type.equals(we.getText()))
			  {
				  we.click(); 
				  break; 
			  }
			  } 
	}
		
	// Set Effective Date in textbox
	public void setEffectiveDate(String effectiveDate)
	{
				js.executeScript("arguments[0].value='"+effectiveDate+"';", driver.findElement(effectiveDateTextbox));
	}
	
	// Set new Price in textbox
	public void setNewPrice(String newPrice) 
	{
		
		js.executeScript("arguments[0].value='"+newPrice+"';", driver.findElement(newPriceTextbox));
		
	}
	public void clickSave() {
		driver.findElement(saveButton).click();
	}
  

}
