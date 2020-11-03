package utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class WindowSwitching {

	WebDriver driver;
	WebDriverWait wait;
	String mainWindowHandle = null;
	String currentWindowHandle = null;
	String previousWindowHandle = null;

	public String getMainWindowHandle() {
		return mainWindowHandle;
	}

	public void setMainWindowHandle(String mainWindowHandle) {
		this.mainWindowHandle = mainWindowHandle;
	}

	public String getCurrentWindowHandle() {
		return currentWindowHandle;
	}

	public void setCurrentWindowHandle(String currentWindowHandle) {
		this.currentWindowHandle = currentWindowHandle;
	}

	public String getPreviousWindowHandle() {
		return previousWindowHandle;
	}

	public void setPreviousWindowHandle(String previousWindowHandle) {
		this.previousWindowHandle = previousWindowHandle;
	}

	public WindowSwitching(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 20);
	}

	public void changeWindow(String currentWindowHndl, String mainWindowHndl) {

		Set<String> winHandles = new HashSet<String>();
		List<String> winHandlesList = new ArrayList<String>();
		int tripPoint = 0;
		// Boolean isElementsLoaded = false;

		while (true) {
			winHandles.clear();
			winHandles = driver.getWindowHandles();
			winHandlesList = new ArrayList<String>();
			winHandlesList.addAll(winHandles);
			if (winHandlesList.size() >= 2) {
				if (tripPoint == 100) {
					// System.out.println("Timeout!!");
					break;
				}
				try {

					// if (driver.findElements(By.xpath("//*")).size() != 0) {
					if (!driver.getPageSource().isEmpty()) {
						break;
					}
				} catch (Exception e) {
					// System.out.println("Window not switched");
					tripPoint++;
				}
			} else
				tripPoint++;
		}

		winHandles.clear();
		winHandles = driver.getWindowHandles();
		// System.out.println("No of Windows : " + winHandles.size());

		for (String e : winHandles) {
			// System.out.println(e);
			if (!e.equals(mainWindowHndl) & !e.equals(currentWindowHndl)) {
				driver.switchTo().window(e);
				break;
			}
		}
		this.setMainWindowHandle(mainWindowHndl);
		this.setPreviousWindowHandle(currentWindowHndl);
		this.setCurrentWindowHandle(driver.getWindowHandle());
		// System.out.println("Window changed");
	}

	public boolean windowCount(int openWindowCount) throws InterruptedException {
		Thread.sleep(5000);
		if (driver.getWindowHandles().size() != openWindowCount) {
			return true;
		}
		return false;

	}

	public void thirdWindowChange(String currentWindowHndl, String mainWindowHndl) {

		Set<String> winHandles = new HashSet<String>();
		List<String> winHandlesList = new ArrayList<String>();
		int tripPoint = 0;
		// Boolean isElementsLoaded = false;

		while (true) {
			winHandles.clear();
			winHandles = driver.getWindowHandles();
			winHandlesList = new ArrayList<String>();
			winHandlesList.addAll(winHandles);
			if (winHandlesList.size() > 2) {
				if (tripPoint == 100) {
					// System.out.println("Timeout!!");
					break;
				}
				try {

					// if (driver.findElements(By.xpath("//*")).size() != 0) {
					if (!driver.getPageSource().isEmpty()) {
						break;
					}
				} catch (Exception e) {
					// System.out.println("Window not switched");
					tripPoint++;
				}
			} else
				tripPoint++;
		}

		winHandles.clear();
		winHandles = driver.getWindowHandles();

		System.out.println("No of Windows : " + winHandles.size());

		for (String e : winHandles) {
			// System.out.println(e);
			if (!e.equals(mainWindowHndl) & !e.equals(currentWindowHndl)) {
				driver.switchTo().window(e);
				break;
			}
		}
		this.setMainWindowHandle(mainWindowHndl);
		this.setPreviousWindowHandle(currentWindowHndl);
		this.setCurrentWindowHandle(driver.getWindowHandle());
		// System.out.println("Window changed");

		while (true) {
			winHandles.clear();
			winHandles = driver.getWindowHandles();
			winHandlesList.addAll(winHandles);
			if (winHandlesList.size() > 2) {
				if (tripPoint == 100) {
					// System.out.println("Timeout!!");
					break;
				}
				try {

					// if (driver.findElements(By.xpath("//*")).size() != 0) {
					if (!driver.getPageSource().isEmpty()) {
						break;
					}
				} catch (Exception e) {
					// System.out.println("Window not switched");
					tripPoint++;
				}
			} else
				tripPoint++;

		}
	}

	public void switchWindowBack(String previousWindowHndl, String currentWindowHndl, String mainWindowHndl) {

		Set<String> winHandles = new HashSet<String>();
		List<String> winHandlesList = new ArrayList<String>();

		int tripPoint = 0;

		while (true) {
			winHandles.clear();
			winHandles = driver.getWindowHandles();
			winHandlesList.addAll(winHandles); // System.out.println(tripPoint);
			if (tripPoint == 300) {
				// System.out.println("Break point reached");
				break;
			}
			if (winHandlesList.size() > 2) {
				// System.out.println("Window still open");
			} else if (winHandlesList.size() <= 2) {
				// System.out.println("Window closed");
				break;
			}
			tripPoint++;
		}

		driver.switchTo().window(previousWindowHndl);

		this.setMainWindowHandle(mainWindowHndl);
		this.setPreviousWindowHandle(currentWindowHndl);
		this.setCurrentWindowHandle(driver.getWindowHandle());
		// System.out.println("Window switched");
	}

	public void fourWindowHandle(String currentWindowHndl, String mainWindowHndl, String secondWindowHndl) {

		Set<String> winHandles = new HashSet<String>();
		List<String> winHandlesList = new ArrayList<String>();
		int tripPoint = 0;
		// Boolean isElementsLoaded = false;

		while (true) {
			winHandles.clear();
			winHandles = driver.getWindowHandles();
			winHandlesList = new ArrayList<String>();
			winHandlesList.addAll(winHandles);
			if (winHandlesList.size() >= 2) {
				if (tripPoint == 100) {
					// System.out.println("Timeout!!");
					break;
				}
				try {

					// if (driver.findElements(By.xpath("//*")).size() != 0) {
					if (!driver.getPageSource().isEmpty()) {
						break;
					}
				} catch (Exception e) {
					// System.out.println("Window not switched");
					tripPoint++;
				}
			} else
				tripPoint++;
		}

		winHandles.clear();
		winHandles = driver.getWindowHandles();
		// System.out.println("No of Windows : " + winHandles.size());

		for (String e : winHandles) {
			// System.out.println(e);

			if (!e.equals(mainWindowHndl) & !e.equals(currentWindowHndl) & !e.equals(secondWindowHndl)) {
				driver.switchTo().window(e);
				break;
			}
		}
		this.setMainWindowHandle(mainWindowHndl);
		this.setPreviousWindowHandle(currentWindowHndl);
		this.setCurrentWindowHandle(driver.getWindowHandle());
		// System.out.println("Window changed");
	}

	public String setHandle(String handle) {
		driver.switchTo().window(handle);
		return driver.getWindowHandle();
	}

	public int handleCount() {
		return driver.getWindowHandles().size();
	}

	public void switchToRemainingWindow(String handle) {

		Set<String> st = driver.getWindowHandles();

		for (String str : st) {
			if (!str.equals(handle)) {
				driver.switchTo().window(str);
			}
		}

	}

	public void switchToCrystalReport(String mainWindowHndl, String currentWindowHndl) {
		Set<String> winHandles = new HashSet<String>();
		List<String> winHandlesList = new ArrayList<String>();
		int tripPoint = 0;
		/*
		 * Selenium sel = new WebDriverBackedSelenium(driver, driver.getCurrentUrl());
		 * 
		 * 
		 * for(String str:sel.getAllWindowTitles()) { System.out.println(str); }
		 */
		// Boolean isElementsLoaded = false;
		boolean flag = true;
		while (true) {
			winHandles.clear();
			winHandles = driver.getWindowHandles();
			winHandlesList = new ArrayList<>();
			winHandlesList.addAll(winHandles);
			int windowSize = winHandlesList.size();

			if (windowSize > 1) {
				flag = true;
				System.out.println(">1: " + winHandlesList);
				try {
					// if (driver.findElements(By.xpath("//*")).size() != 0) {
					if (!driver.getPageSource().isEmpty()) {
						break;
					}
					for (String e : winHandles) {
						System.out.println("Main window: " + mainWindowHndl);
						System.out.println(e);
						if (!e.equals(mainWindowHndl) & !e.equals(currentWindowHndl)) {
							driver.switchTo().window((String) e);
							System.out.println("URL: " + driver.getCurrentUrl());
							winHandles.clear();
							winHandles = driver.getWindowHandles();
							winHandlesList = new ArrayList<>();
							winHandlesList.addAll(winHandles);
							// System.out.println("SMS window:"+mainWindowHndl);
							System.out.println("Open windows" + winHandlesList);
							System.out.println("All window: " + winHandles.size());
							if (winHandles.size() == 1) {
								break;
							}
						}
					}

				} catch (Exception e) {
					// System.out.println("Window not switched");
					System.out.println("Catch");
				}
			} else {
				flag = false;
				break;
			}
		}
		/*
		 * while (true) { winHandles.clear(); winHandles = driver.getWindowHandles();
		 * winHandlesList = new ArrayList<>(); winHandlesList.addAll(winHandles); int
		 * windowSize = winHandlesList.size();
		 * 
		 * for (String str : winHandlesList) { System.out.println("Main win Handle: " +
		 * mainWindowHndl); System.out.println(str); }
		 * 
		 * }
		 */

		if (flag == false) {

		}

		// System.out.println("No of Windows : " + winHandles.size());
		/*
		 * for (String e : winHandles) { System.out.println("Main window: " +
		 * mainWindowHndl); System.out.println(e); if (!e.equals(mainWindowHndl) &
		 * !e.equals(currentWindowHndl)) { driver.switchTo().window((String) e); break;
		 * } }
		 */
		this.setMainWindowHandle(mainWindowHndl);
		this.setPreviousWindowHandle(currentWindowHndl);
		// this.setCurrentWindowHandle(driver.getWindowHandle()); //
		// System.out.println("Window changed");
	}

	public void waitForBillingGeneration(String mainWindowHndl, String currentWindowHndl) {
		Set<String> winHandles = new HashSet<String>();
		List<String> winHandlesList = new ArrayList<String>();
		int tripPoint = 0;
		// Boolean isElementsLoaded = false;

		while (true) {
			winHandles.clear();
			winHandles = driver.getWindowHandles();
			winHandlesList = new ArrayList<String>();
			winHandlesList.addAll(winHandles);
			if (winHandlesList.size() >= 2) {
				/*
				 * if (tripPoint == 100) { // System.out.println("Timeout!!"); break; }
				 */
				try {

					// if (driver.findElements(By.xpath("//*")).size() != 0) {
					if (!driver.getPageSource().isEmpty()) {
						break;
					}
				} catch (Exception e) {
					// System.out.println("Window not switched");
					tripPoint++;
				}
			} else
				tripPoint++;
		}

		winHandles.clear();
		winHandles = driver.getWindowHandles();
		// System.out.println("No of Windows : "+winHandles.size());

		for (String e : winHandles) {
			// System.out.println(e);
			if (!e.equals(mainWindowHndl) & !e.equals(currentWindowHndl)) {
				driver.switchTo().window(e);
				break;
			}
		}
		this.setMainWindowHandle(mainWindowHndl);
		this.setPreviousWindowHandle(currentWindowHndl);
		this.setCurrentWindowHandle(driver.getWindowHandle());
		// System.out.println("Window changed");
	}
}
