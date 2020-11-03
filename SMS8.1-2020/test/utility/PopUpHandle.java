package utility;

import org.openqa.selenium.WebDriver;

public class PopUpHandle {
	
	WebDriver driver;
	WindowSwitching ws;
	
	public PopUpHandle(WebDriver driver) {
		this.driver = driver;
		ws = new WindowSwitching(driver);
	}
	
	public void contractPopUp(String handle) throws InterruptedException {
		
		ws.switchToRemainingWindow(handle);
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
	}
	
}
