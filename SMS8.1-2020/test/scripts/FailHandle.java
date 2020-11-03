package scripts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.CommonElements;
import pages.SMSConfirmPopUp;
import utility.WindowSwitching;

public class FailHandle {

	WebDriver driver;
	WebDriverWait wait;

	// class objects
	CommonElements comm;
	SMSConfirmPopUp popUp;

	public FailHandle(WebDriver driver) {
		this.driver = driver;
		comm = new CommonElements(driver);
		popUp = new SMSConfirmPopUp(driver);
	}

	public void closeRemainingWindows(String mainWindowHndl) {

		Set<String> winHandles = new HashSet<String>();

		while(driver.getWindowHandles().size() != 1) {
			winHandles.clear();
			winHandles = driver.getWindowHandles();

			for (String e : winHandles) {
				if (!e.equals(mainWindowHndl)) {
					driver.switchTo().window(e);

					if (popUp.getMessage().equals("Proceed without saving?")) {
						popUp.clickYesButton2();
						driver.close();
					}

					else
						driver.close();
				}
			}
		}
	}

	public void switchToHomePage(String mainWindowHndl) {

		WindowSwitching ws = new WindowSwitching(driver);
		ws.setHandle(mainWindowHndl);
		ws.setMainWindowHandle(mainWindowHndl);
		ws.setCurrentWindowHandle(driver.getWindowHandle());
		ws.setPreviousWindowHandle(null);
	}

	public void failHandler(String mainWindowHndl) {

		this.closeRemainingWindows(mainWindowHndl);
		this.switchToHomePage(mainWindowHndl);
		comm.switchToMainFrame();
	}

}
