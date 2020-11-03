package scripts;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import pages.HomePage;
import pages.LoginPage;

public class IsHomePage {

	WebDriver driver;
	
	public IsHomePage(WebDriver driver) {
		this.driver = driver;
	} 

/*	public static void main(String args[]) throws InterruptedException, NoSuchElementException {
		String driverPath = "./test/resources/drivers/";
		System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer2.4.exe");
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		driver = new InternetExplorerDriver(caps);

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		// Load SMS URL
		driver.navigate().to("http://10.69.11.99/smsqc/Login.aspx?ContractEstimateId=");
		IsHomePage hp = new IsHomePage();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginToSMS("administrator", "sms2219", "UAE QC");
		HomePage homePage = new HomePage(driver);
		homePage.clickContractMenu();
		homePage.clickBuildingMenu();
		System.out.println(hp.isElementPresent(By.id("lblEntity")));
		Thread.sleep(1000);
		driver.switchTo().frame(driver.findElement(By.id("mainFrame")));
		if(hp.isElementPresent(By.id("lblEntity")) == false) {
			driver.findElement(By.id("btnHome")).click();
		}
		System.out.println(hp.isElementPresent(By.id("lblEntity")));
		
		
	}
*/
	public boolean isElementPresent(By selector) {

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		boolean returnVal = true;
		try {
			driver.findElement(selector);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			returnVal = false;
		} finally {
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}
		return returnVal;
	}
}
