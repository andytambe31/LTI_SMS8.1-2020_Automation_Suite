package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class LoginPage {

	WebDriver driver;
	JavascriptExecutor js;
	Actions builder;// = new Actions(driver);

	// Web objects
	By loginIdTextBox = By.id("txtLogin");
	By passwordTextBox = By.id("txtPassword");
	By entityButton = By.id("btnEntity");
	By loginButton = By.id("btnLogin");
	By parentEntityGrid = By.xpath("//table[@id='grdEntity']/tbody/tr/td[2]");
	// By selectedEntityFromGrid =
	// By.xpath("//table[@id='grdEntity']//tbody//tr["+i+"]//td[2]");

	// Methods
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		this.builder = new Actions(driver);
	}

	public void setLoginId(String loginId) {
		js.executeScript("arguments[0].value='" + loginId + "';", driver.findElement(loginIdTextBox));
	}

	public void setPassword(String password) {
		js.executeScript("arguments[0].value='" + password + "';", driver.findElement(passwordTextBox));
	}

	public void clickEntityButton() {
		driver.findElement(entityButton).click();
	}

	public void clickLoginButton() {
		driver.findElement(loginButton).click();
	}

	public void clickLoginButton2() {
		builder.doubleClick(driver.findElement(loginButton));
	}

	public void setEntity(String entity) {
		List<WebElement> ls = driver.findElements(parentEntityGrid);
		for (WebElement we : ls) {
			if (entity.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}
	
	public void makeFullscreen() {
		//driver.manage().window().maximize();
		Dimension d = new Dimension(1280,1080);
		//System.out.println(d.getWidth());
		driver.manage().window().setSize(d);
		//System.out.println(d.getWidth());
		//driver.findElement(By.xpath("//*")).sendKeys(Keys.F11);
		System.out.println(d.getWidth());
	}

	public void loginToSMS(String loginId, String password, String entity) throws InterruptedException {
		this.setLoginId(loginId);
		this.setPassword(password);
		this.clickEntityButton();
		this.setEntity(entity);
		Thread.sleep(1000);
		this.clickLoginButton();
	}

	public void loginToSMS2(String loginId, String password, String entity) throws InterruptedException {
		this.setLoginId(loginId);
		this.setPassword(password);
		//this.clickEntityButton();
		//this.setEntity(entity);
		Thread.sleep(1000);
		this.clickLoginButton();
	}
}
