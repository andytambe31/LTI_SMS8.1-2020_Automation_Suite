package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class utilityFunctions {
	WebDriver driver;
	JavascriptExecutor js;

	public utilityFunctions(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
	}

	public void fastType(WebElement we, String txt) {
		String str = txt;
		txt = str.substring(0, str.length() - 1);
		str = str.substring(str.length() - 1);
		js.executeScript("arguments[0].value='" + txt + "';", we);
		we.sendKeys("" + str + "" + Keys.TAB);
	}

	public boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 50) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}

	public String[] splitString(String str, String delimiters) {

		// String str = "a d, m, i.n";
		// String delimiters = "\\s+|,\\s*|\\.\\s*";

		// analyzing the string
		return str.split(delimiters);

		/*
		 * for(String strng: tokensVal) { System.out.println(strng); }
		 */
	}

	public boolean waitForDetailsToLoad(WebElement we, String attribute) {
		int tripPoint = 0;
		while (tripPoint != 100) {
			tripPoint++;
			try {

				String[] compare = { "" };

				switch (attribute) {
				case "value":
					compare = new String[] { we.getAttribute(attribute) };
					break;
				case "text":
					compare = new String[] { we.getText() };
					break;
				default:
					break;
				}

				if (compare[0].length() != 0) {
					System.out.println(compare[0]);
					return true;
				}
			} catch (Exception e) {
			}
		}

		return false;
	}
	
	
	public boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	public boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	public void checkIfButtonIsEnabled(WebElement button) {

		while (true) {
			try {
				if (button.getAttribute("disabled").toString().trim().equals("true")) {

				}
			} catch (Exception e) {
				break;
			}
		}

	}
}
