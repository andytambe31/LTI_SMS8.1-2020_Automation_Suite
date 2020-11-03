package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.utilityFunctions;

public class ApplicationParameterPage {
	WebDriver driver;
	String mainWindowHandle ;
	String currentWindowHandle ;
	String previousWindowHandle ;
	
	By mainFrame = By.id("mainFrame");
	By buttonFilterModule = By.id("btnFilterModule");
	By parentModuleGrid = By.xpath("//table[@id='grdModule']//tbody//tr");
	By applicationParameterParentGrid = By.xpath("//table[@id='grdApplicationAttribute']//tbody//tr");
//	grdApplicationAttribute__ctl57_txtAtributeValueName
	//Pop up
	//By associateFrame = By.id("associateFrame");
	By associateGrid = By.xpath("//table[@id='grdAssociate']//tbody//tr");
	By selectButton = By.id("btnSelect");
	By saveButton = By.id("btnSave");
	By closeButton = By.id("btnClose");
		
	public ApplicationParameterPage(WebDriver driver) {
		this.driver = driver;
		
	}
	//Switch to Main Frame
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}
	
	//Page actions
	public void clickButtonFilterModule() {
		driver.findElement(buttonFilterModule).click();
	}
	public void clickSaveButton() {
		driver.findElement(saveButton).click();
	}
	//Set module
	public void setModule(String module) { 
		this.switchToMainFrame();
		this.clickButtonFilterModule();
		List<WebElement> ls =driver.findElements(parentModuleGrid);
			for(WebElement we:ls) {
				if(module.equals(we.getText())){
					we.findElement(By.xpath("td[2]")).click(); 
					break; 
			} 
		} 
	}
	
	//Verify the application parameter exists
	public boolean applicationParameterExists(String applicationParameter) {
		List<WebElement> ls = driver.findElements(applicationParameterParentGrid);
		for(WebElement we:ls) {
			if(applicationParameter.equals(we.findElement(By.xpath("td[3]")).getText())) {
				return true;
			}
		}
		return false;
	}
	
	public void clickApplicationParameterValueButton(String applicationParameter) {
		List<WebElement> ls = driver.findElements(applicationParameterParentGrid);
		for(WebElement we:ls) {
			if(applicationParameter.equals(we.findElement(By.xpath("td[3]")).getText())) {	
				we.findElement(By.xpath("td[5]//button")).click();
				break;
			}
		}
	}
	
	public boolean verifyApplicationParameterValueButton(String applicationParameter,String applicationParameterValue) {
		List<WebElement> ls = driver.findElements(applicationParameterParentGrid);
		for(WebElement we:ls) {
			if(applicationParameter.equals(we.findElement(By.xpath("td[3]")).getText())) {
				if(applicationParameterValue.equals(we.findElement(By.xpath("td[5]//input[1]")).getAttribute("value"))) {
					return true;
				}
				break;
			}
		}
		return false;
	}
	
	
	//Pop up
	public void setApplicationParameterValueButton(String applicationParameterValue) {
		driver.switchTo().frame(0);
		List<WebElement> ls = driver.findElements(associateGrid);
		//int i = 1;
		for(WebElement we:ls) {
			if(applicationParameterValue.equals(we.getText())) {
				we.click();
				break;
			}	
		}
	}
	
	public void setApplicationParameterValueTextBox(String applicationParameterName,String applicationParameterValue) {
		utilityFunctions utilityFunction = new utilityFunctions(driver);
		
		List<WebElement> ls = driver.findElements(applicationParameterParentGrid);
		for(WebElement we:ls) {
			if(applicationParameterName.equals(we.findElement(By.xpath("td[3]")).getText())) {
				//we.findElement(By.xpath("td[5]//input[1]")).click();
				//we.findElement(By.xpath("td[5]//input[1]")).clear();
				utilityFunction.fastType(we.findElement(By.xpath("td[5]//input[1]")), applicationParameterValue);
				break;
			}	
		}
	}
	
	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}
	
	public void clickCloseButton() {
		driver.findElement(closeButton).click();
	}
}
