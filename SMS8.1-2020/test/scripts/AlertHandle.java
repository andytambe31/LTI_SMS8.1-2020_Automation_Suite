package scripts;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

public class AlertHandle {
	
	WebDriver driver;
	
	public AlertHandle(WebDriver driver) {
		this.driver = driver;
	}

	public void handleAlert() {
		System.out.println("***************************"+driver.getWindowHandles().size());
		// Switch the control of 'driver' to the Alert from main window
		Alert promptAlert = driver.switchTo().alert();
		// Get the Text of Alert
		//promptAlert.dismiss();
		promptAlert.accept();
	}
	
	public boolean isAlertPresent() 
	{ 
	    try 
	    { 
	        driver.switchTo().alert(); 
	        return true; 
	    }   // try 
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }   // catch 
	}   // isAlertPresent()
}
