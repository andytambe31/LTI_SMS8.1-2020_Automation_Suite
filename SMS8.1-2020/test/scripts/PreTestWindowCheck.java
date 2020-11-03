package scripts;

import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.WebDriver;

public class PreTestWindowCheck {

	WebDriver driver;
	FailHandle failHndl;
	AlertHandle alrt;

	public PreTestWindowCheck(WebDriver driver) {
		this.driver = driver;
		failHndl = new FailHandle(driver);
		alrt = new AlertHandle(driver);
	}

	public void closeWindows(String mainWindowHndl) {

		Set<String> winHandles = new HashSet<String>();

		while (winHandles.size() > 1) {
			System.out.println("Window: "+winHandles.size());
			winHandles.clear();
			winHandles = driver.getWindowHandles();
			
			if (winHandles.size() > 1)
				failHndl.closeRemainingWindows(mainWindowHndl);
			if(alrt.isAlertPresent()){
				alrt.handleAlert();
			}	
		}

	}

}
