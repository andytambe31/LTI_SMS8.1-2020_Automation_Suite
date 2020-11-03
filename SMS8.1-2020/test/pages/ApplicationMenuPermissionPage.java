package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ApplicationMenuPermissionPage {

	WebDriver driver;
	By mainFrame = By.id("mainFrame");
	By menuGrid = By.xpath("//table[@id = 'grdMenu']//tbody//tr");
	
	public ApplicationMenuPermissionPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}
	
	public boolean verifyMenu(String submenu) {
		List<WebElement> ls = driver.findElements(menuGrid);
		for(WebElement we:ls) {
			if(submenu.equals(we.findElement(By.xpath("td[6]")).getText())) {
				return true;
			}
		}
		return false;
	}
	
}
