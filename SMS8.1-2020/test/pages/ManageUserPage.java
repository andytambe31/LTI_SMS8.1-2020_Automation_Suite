package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ManageUserPage {
	WebDriver driver;
	JavascriptExecutor js;
	
	By mainFrame = By.id("mainFrame");
	By loginIdTextBox = By.id("txtLoginId");
	By networkIdTextBox = By.id("txtNetworkId");
	By securityGroupButton = By.id("btnSecurityGroup");
	By securityGroupGrid = By.xpath("//table[@id='grdSecurityGroup']//tbody//tr");
	By passwordTextBox = By.id("txtPassword");
	By confirmPasswordTextBox = By.id("txtConfirmPassword");
	
	By saveButton = By.id("btnSave");
	
	public ManageUserPage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
	}

	public void switchToMainFrame(){
		driver.switchTo().frame(driver.findElement(mainFrame));
	}
	
	public void setLoginId(String login) {
		this.switchToMainFrame();
		js.executeScript("arguments[0].value='"+login+"';", driver.findElement(loginIdTextBox));	
	}
	
	public void setNetworkId(String networkId) {
		js.executeScript("arguments[0].value='"+networkId+"';", driver.findElement(networkIdTextBox));	
	}
	
	public void clickOnSecurityGroupButton() {
		driver.findElement(securityGroupButton).click();
	}
	
	public void setPassword(String password) {
		js.executeScript("arguments[0].value='"+password+"';", driver.findElement(passwordTextBox));	
	}
	
	public void setConfirmPassword(String confirmPassword) {
		js.executeScript("arguments[0].value='"+confirmPassword+"';", driver.findElement(confirmPasswordTextBox));	
	}
	
	public void setSecurityGroup(String securityGroup) {
		List<WebElement> ls =driver.findElements(securityGroupGrid);
			for(WebElement we:ls) {
				if(securityGroup.equals(we.getText())){
					we.findElement(By.xpath("td[2]")).click(); 
					break; 
			} 
		} 
	}
	
	public void clickOnSaveButton() {
		driver.findElement(saveButton).click();
	}
	
	
}
