package scripts;

import org.openqa.selenium.WebDriver;

import java.awt.Robot;
import java.awt.event.KeyEvent;;

public class handleIESave {
	public void passFirfoxDownloadDialogUseRobot(WebDriver driver, int num) {
		// WebDriver ffDriver = null;
		try {
			Robot robotObj = new Robot();

			// Press arrow down key to select save radio button.
			Thread.sleep(2000);
			robotObj.keyPress(KeyEvent.VK_DOWN);
			robotObj.keyRelease(KeyEvent.VK_DOWN);

			// Press tab key three time to navigate to Save button.
			for (int i = 0; i < num; i++) {
				Thread.sleep(2000);
				robotObj.keyPress(KeyEvent.VK_TAB);
			}

			// Press down Save button.
			Thread.sleep(2000);
			robotObj.keyPress(KeyEvent.VK_ENTER);

			// Release up Save button, download process start.
			Thread.sleep(2000);
			robotObj.keyRelease(KeyEvent.VK_ENTER);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void clickAltS(WebDriver driver) {
		try {
			Robot robot = new Robot();
			Thread.sleep(2000);

			// Press ALT + S
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_ALT);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_S);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void clickAltF4(WebDriver driver) {
		try {
			Robot robot = new Robot();

			// Press ALT + S
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_ALT);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_F4);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void clickAltC(WebDriver driver) {
		try {
			Robot robot = new Robot();

			// Press ALT + S
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_ALT);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_C);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void clickTab(WebDriver driver) {
		try {
			Robot robot = new Robot();

			// Press ALT + S
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(1000);
			robot.keyRelease(KeyEvent.VK_TAB);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void clickEnter(WebDriver driver) {
		try {
			Robot robot = new Robot();

			// Press ALT + S
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(1000);
			robot.keyRelease(KeyEvent.VK_ENTER);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
