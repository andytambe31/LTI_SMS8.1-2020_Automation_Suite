package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.utilityFunctions;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class ContactPage {
	WebDriver driver;
	JavascriptExecutor js;

	utilityFunctions utlFn;
	DateTimeFormatter dtf;
	LocalDateTime now;

	By mainFrame = By.id("mainFrame");
	By sideLinksSubSection = By.xpath("//tbody[@id='subSection']//tr//td[1]");
	By firstNameTextbox = By.id("txtFirstName");

	By titleButton = By.id("btnTitle");
	By titleGrid = By.xpath("//table[@id = 'grdTitle']/tbody/tr/td[2]");

	By languageButton = By.id("btnLanguage");
	By languageGrid = By.xpath("//table[@id = 'grdLanguage']/tbody/tr/td[2]");
	By languageGrid2 = By.xpath("//table[@id = 'grdLanguage']/tbody/tr[1]/td[2]");

	By countryButton = By.id("btnCountry");
	By stateButton = By.id("btnState");
	By countryGrid = By.xpath("//table[@id = 'grdCountry']/tbody/tr/td[2]");
	By stateGrid = By.xpath("//table[@id='grdState']/tbody/tr/td[2]");
	By addressLine1Textbox = By.id("txtAddress1");

	By newButton = By.id("btnNew");
	By saveButton = By.id("btnSave");
	By deleteButton = By.id("btnDelete");
	By cityButton = By.id("btnCity");
	By selectButton = By.id("btnSelect");
	By deleteButtonName = By.name("btnDelete");

	By associateSearchTextBox = By.id("txtSearch");
	By associateSearchButton = By.id("btnSearch");
	By associateGrid = By.xpath("//table[@id='grdAssociate']//tr//td[2]");
	By associateGridCity = By.xpath("//table[@id='grdAssociate']/tbody/tr/td[2]");

	By contactIdTextBox = By.id("txtContactId");
	By emailIdTextGrid = By.xpath("//table[@id = 'grdContactComm']/tbody/tr");
	// table[@id = 'grdContactComm']/tbody/tr/td[3]

	// table[@id = 'grdContactComm']/tbody/tr/td[5]/input

	public ContactPage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		utlFn = new utilityFunctions(driver);
		dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		now = LocalDateTime.now();
	}

	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}

	public void setContactId(String contactId) {
		this.switchToMainFrame();
		utlFn.fastType(driver.findElement(contactIdTextBox), contactId);
		// driver.findElement(contactIdTextBox).sendKeys(contactId);
	}

	public void switchToAssociateFrame() {
		// driver.switchTo().frame(driver.findElement(associateFrame));
		driver.switchTo().frame(0);
	}

	public String[] getContactId() {
		String contactId[] = { driver.findElement(contactIdTextBox).getAttribute("value") };
		return contactId;
	}

	public void setFirstName(String firstName) {
		this.switchToMainFrame();
		js.executeScript("arguments[0].value='" + firstName + "';", driver.findElement(firstNameTextbox));
	}

	public void setAddressLine1(String addressline1) {
		utlFn.fastType(driver.findElement(addressLine1Textbox), addressline1);
		// js.executeScript("arguments[0].value='"+addressline1+"';",
		// driver.findElement(addressLine1Textbox));
	}

	public void clickTitle() {
		driver.findElement(titleButton).click();
	}

	public void setTitle(String title) {
		this.clickTitle();
		List<WebElement> ls = driver.findElements(titleGrid);
		for (WebElement we : ls) {
			if (title.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void clickLanguage() {
		driver.findElement(languageButton).click();
	}

	public void setLanguage(String language) {
		this.clickLanguage();
		/*
		 * List<WebElement> ls = driver.findElements(languageGrid); for (WebElement we :
		 * ls) { if (language.equals(we.getText())) { we.click(); break; } }
		 */
		driver.findElement(languageGrid2).click();
	}

	public void clickCountry() {
		driver.findElement(countryButton).click();
	}

	public void setCountry(String country) {
		this.clickCountry();
		List<WebElement> ls = driver.findElements(countryGrid);
		for (WebElement we : ls) {
			//if (country.equals(we.getText())) {
				we.click();
				break;
			//}
		}
	}

	public void clickOnSideLink(String sideLink) {
		driver.switchTo().defaultContent();
		List<WebElement> ls = driver.findElements(sideLinksSubSection);
		for (WebElement we : ls) {
			try {
				if (sideLink.equals(we.getText())) {
					we.click();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}

	public void clickStateButton() {
		driver.findElement(stateButton).click();
	}

	public void clickCityButton() {
		driver.findElement(cityButton).click();
	}

	public void setState(String state) {
		this.clickStateButton();
		List<WebElement> ls = driver.findElements(stateGrid);
		for (WebElement we : ls) {
			if (state.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void clickSave() {
		driver.findElement(saveButton).click();
	}

	public void setCity(String city) {
		List<WebElement> ls = driver.findElements(associateGridCity);
		for (WebElement we : ls) {
			if (city.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void clickAssociateSearchButton() {
		driver.findElement(associateSearchButton).click();
	}

	public void setAssociateSearch(String searchText) {
		js.executeScript("arguments[0].value='" + searchText + "';", driver.findElement(associateSearchTextBox));
	}

	/*
	 * public void clickDelete() {
	 * 
	 * //js.executeScript("arguments[0].click();",
	 * driver.findElement(deleteButton)); driver.findElement(deleteButton).click();
	 * }
	 */

	public void clickDelete() {
		driver.findElement(deleteButton).click();
		driver.findElement(deleteButtonName).click();
	}

	public void setEmailId(String email) {

		List<WebElement> ls = driver.findElements(emailIdTextGrid);
		for (WebElement we : ls) {

			String temp = we.findElement(By.xpath("td[3]")).getText();
			if (temp.contains("Email") || temp.contains("E-mail") || temp.contains("E-Mail") || temp.contains("EMail")
					|| temp.contains("e-mail") || temp.contains("email") || temp.contains("e-Mail")) {
				we.findElement(By.xpath("td[5]/input")).sendKeys(email);
				break;
			}

		}

		//driver.findElement(emailIdTextGrid).sendKeys(email);
	}
	
	public void waitForContactdetailsToLoad() {
		utlFn.waitForDetailsToLoad(driver.findElement(firstNameTextbox), "value");
	}
}
