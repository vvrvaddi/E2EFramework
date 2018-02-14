
/**
 * 
 */
package dl.automation.core.actions;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import dl.automation.core.exceptions.AssertFailedException;
import dl.automation.core.executor.IDLWebdriver;
import dl.automation.core.interfaces.IExecutionContext;
import dl.automation.core.interfaces.ISuiteRunnerConfig;
import dl.automation.core.model.TestDataObjectModel;
import dl.automation.core.types.config.ConfigurationFactory;
import dl.automation.core.utils.Log4jUtil;

public abstract class BaseActions {

	static Logger log = Log4jUtil.loadLogger(BaseActions.class);
	static String StoreValue = "";
	String year_range = "";
	String[] y_range = null;
	String ymin = "";
	String ymax = "";
	int year_min = 0;
	int year_max = 0;
	String[] mmmyy = null;
	String mon_web = "";
	String yy_web = "";
	String dd_web = "";
	boolean result = false;
	String field = "";
	String disabled = null;
	static ISuiteRunnerConfig suiteRunnerConfig = null;

	/**
	 * Method to Click on WebElement
	 * 
	 * @param element
	 * @throws InterruptedException
	 * @throws StaleElementReferenceException
	 */
	public void click(WebDriver webDriver, WebElement element) throws AssertFailedException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(webDriver, 30);
		element = wait.until(ExpectedConditions.elementToBeClickable(element));
		try {
			element.click();
		} catch (Exception e) {
			Thread.sleep(2000);
		}
		Thread.sleep(4000);
		log.info("clicked on the web element" + element);

	}

	/**
	 * Clear previous input text and Enter value into input field
	 * 
	 * @param element
	 * @param value
	 * @throws InterruptedException
	 */
	public void clearAndEnterInputValue(WebDriver webDriver, IExecutionContext context, WebElement element,
			String value) throws AssertFailedException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(webDriver, 30);
		element = wait.until(ExpectedConditions.visibilityOf(element));
		element.clear();

		element.sendKeys(value);
		Thread.sleep(1000);
		log.info("Entered input value by clearing on web element ==>> " + element);

	}

	/**
	 * Method to hover on WebElement
	 * 
	 * @param driver
	 * @param element
	 * @throws InterruptedException
	 */
	public void mouseOver(WebDriver driver, WebElement element) throws AssertFailedException, InterruptedException {

		mousemove();
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		Thread.sleep(1500);
		log.info("Mouse hovered on web element");

	}

	/**
	 * Refreshing browser
	 * 
	 * @param driver
	 */

	public void refreshBrowser(WebDriver driver) throws AssertFailedException {
		driver.navigate().refresh();
		log.info("Refreshing browser");
	}

	/**
	 * clearing input data
	 * 
	 * @param element
	 * @throws InterruptedException
	 */
	public void clear(WebElement element) throws AssertFailedException, InterruptedException {
		element.clear();
		Thread.sleep(1000);
		log.info("clearing the input data");
	}

	/**
	 * @param driver
	 *            on an element using Actions class
	 * @throws InterruptedException
	 */
	public void actionsclick(WebDriver driver, WebElement element) throws AssertFailedException, InterruptedException {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().perform();
		Thread.sleep(1000);
		log.info("Method to click on element using Actions Class ");

	}

	/**
	 * @param driver
	 *            drag and drop on an element using Actions class
	 */
	public void ActionsDragNDrop(WebDriver webdriver, WebElement element, String value) throws AssertFailedException {
		try {

			Actions builder = new Actions(webdriver);
			builder.clickAndHold(element).moveToElement(webdriver.findElement(By.xpath(value))).release();
			builder.moveToElement(element).build().perform();
			builder.moveToElement(webdriver.findElement(By.xpath(value))).build().perform();
			builder.dragAndDrop(element, webdriver.findElement(By.xpath(value))).build().perform();
			log.info("Method to perform Drag and drop");

		} catch (Exception e) {
			e.printStackTrace();
			new AssertFailedException("failed to drop ann element");
		}
	}

	/**
	 * @param driver
	 *            Click on an element using JavaScript class
	 * @throws InterruptedException
	 */
	public void jsclick(WebDriver driver, WebElement element) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String js = "arguments[0].style.visibility='visible'; arguments[0].click()";
		jse.executeScript(js, element);
		Thread.sleep(3000);
		log.info("Method to click on element using JavaScript");

	}

	public void jsEnter(WebDriver driver, IExecutionContext context, WebElement element, String value)
			throws AssertFailedException {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		element = wait.until(ExpectedConditions.visibilityOf(element));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", element);
		jse.executeScript("arguments[0].value='" + value + "'", element);
		log.info("Method to enter value on element using JavaScript");
	}

	/**
	 * @param element
	 * @param driver
	 *            clicking on an element using Actions class
	 */
	public void actionsDoubleClick(WebDriver driver, WebElement element) throws AssertFailedException {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		element = wait.until(ExpectedConditions.elementToBeClickable(element));
		Actions action = new Actions(driver);
		action.moveToElement(element).doubleClick().perform();
		log.info("Method to doubleclick on element");
	}

	public void clickandwait(WebElement element) throws AssertFailedException, InterruptedException {

		element.click();
		Thread.sleep(2000);
		log.info("Click and wait on element");
	}

	public void isdisabled(WebElement element) throws AssertFailedException {

		log.info("Result of ===>>   " + element.isEnabled());

		if (element.isEnabled()) {
			throw new AssertFailedException("Web Element is enabled");
		}

	}

	public void isenabled(WebElement element) throws AssertFailedException {

		log.info("Result of ===>>   " + element.isEnabled());

		if (!element.isEnabled()) {
			throw new AssertFailedException("Web Element is disabled");
		}
	}

	public void iselementdisplayed(WebElement element) throws AssertFailedException {

		log.info("Result of ===>>   " + element.isDisplayed());
		element.isDisplayed();
	}

	/***
	 * 
	 * -----------------------------------------------
	 * 
	 * @param data
	 * @param webDriver
	 * 
	 * 
	 * @param element
	 * @return
	 */
	public void iselementnotdisplayed(WebDriver webDriver, String value) throws AssertFailedException {

		// boolean isDisplayed = true;

		String xpath = value;
		log.info("Xpath expression is  ==> " + xpath);

		List<WebElement> elemntsSize = webDriver.findElements(By.xpath(xpath));
		System.out.println("Elements Size-->" + elemntsSize.size());

		if (elemntsSize.size() == 0) {
			log.info("Element is not displayed ");

		} else {

			if (elemntsSize.get(0).isDisplayed()) {
				throw new AssertFailedException("Element should not be displayed but diplaying on UI");
			}

		}

	}

	public void isselected(WebElement element) throws AssertFailedException, InterruptedException {
		element.isSelected();
		Thread.sleep(2000);
		log.info("verify the element is selected from dropdown");
	}

	public void check(WebDriver driver, WebElement element) throws AssertFailedException {

		if (!element.isSelected()) {
			element.click();
			log.info("check box value set as true");
		} else
			log.info("check box value already set as true");

		try {
			AlertAccept(driver);
			log.info("Clicked on OK in the alert");
		} catch (Exception e) {
			log.info("No alert present");
		}

	}

	public void uncheck(WebElement element) {

		if (element.isSelected()) {
			element.click();
			log.info("check box value set as false");
		} else
			log.info("check box value already set as false");
	}

	/**
	 * I have just added method not fail, as we are internally handling waits
	 * 
	 * @param element
	 * @throws AssertFailedException
	 * 
	 */
	public void waitForElement(WebDriver webDriver, WebElement element) throws AssertFailedException {

		try {

			// int value1=Integer.parseInt(value)/1000;
			boolean val = true;
			while (val) {
				WebDriverWait wait = new WebDriverWait(webDriver, 120);
				wait.until(ExpectedConditions.visibilityOf(element));
				log.info("Waited for the Visiblity of element==>");
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new AssertFailedException(element.toString() + " Not found on page");
		}
	}

	/**
	 * I have just added method not fail, as we are internally handling waits
	 * 
	 */
	public void waittime(String value) {

		try {
			int time = Integer.parseInt(value);
			Thread.sleep(time);
			log.info("Wait Time ==>>");
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	public void currentDate(WebDriver webDriver, WebElement element) {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String expected = dateFormat.format(date);
		String actual;
		try {
			actual = element.getAttribute("value").trim();
		} catch (Exception e) {
			actual = element.getText().trim();
		}

		System.out.println(expected + "--" + actual);
		if (expected.equalsIgnoreCase(actual)) {
			log.info("Dates are matched");
		} else {
			new AssertFailedException("Current Date is not displayed by default");
		}
	}

	/**
	 * This method is used to comeback to default frame
	 */
	public void switchTodefault(WebDriver driver) throws InterruptedException {
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		log.info("Switched to default frame");
	}

	/**
	 * This method is used to move element, as mouse hovering is improper.
	 */
	public void mousemove() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width - 100;
		// log.info("Mouse move to the location " + screenHeight + "======>>" +
		// screenWidth);

		try {
			Robot robot = new Robot();
			robot.mouseMove(screenWidth, screenHeight);

		} catch (AWTException e) {

			e.printStackTrace();
		}
	}

	/**
	 * This method is scroll to respective element
	 * 
	 * @param webDriver
	 * @param element
	 */
	public void scrollToViewAttention(WebDriver webDriver, WebElement element) {

		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
		waittime("1000");
	}

	/**
	 * Sending value using Actions class
	 * 
	 * @param webDriver
	 * @param element
	 * @param value
	 */
	public void actionsenter(WebDriver webDriver, WebElement element, String value) {

		Actions action = new Actions(webDriver);
		action.sendKeys(element, value);
		log.info("Entered " + value + " in the input field using Actions Class");
	}

	/**
	 * Method to close the pointed window
	 * 
	 * @param webDriver
	 */
	public void currentWindowClose(WebDriver webDriver) {
		webDriver.close();
		log.info("Closing the window");
	}

	/**
	 * Method to delete cookies
	 * 
	 * @param webDriver
	 */
	public void deleteCookies(WebDriver webDriver) {
		webDriver.manage().deleteAllCookies();
		log.info("Clearing all the cookies");
	}

	/**
	 * Method to upload files having input tag
	 * 
	 * @param webdriver
	 * @param element
	 * @param value
	 */
	public void uploadFile(WebDriver webdriver, String fileName) {
		// TODO Auto-generated method stub

		try {

			Thread.sleep(4000);
			String filePath = null;

			// filePath =
			// ConfigurationFactory.getCurrentAppConfig().getUploadsFolder();
			filePath = ConfigurationFactory.getCurrentAppConfig().getUploadsFolder() + "\\" + fileName;
			String dirpath = FilenameUtils.separatorsToSystem(filePath);
			log.info("Path is: " + dirpath);

			Robot robot = new Robot();
			StringSelection selection = new StringSelection(dirpath);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);

			String getOS = System.getProperty("os.name").toLowerCase();
			if (getOS.contains("mac") || getOS.contains("nix")) {
				/*
				 * robot.keyPress(KeyEvent.VK_META);
				 * robot.keyPress(KeyEvent.VK_TAB);
				 * robot.keyRelease(KeyEvent.VK_META);
				 * robot.keyRelease(KeyEvent.VK_TAB); robot.setAutoDelay(5000);
				 */

				robot.keyPress(KeyEvent.VK_META);
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_G);
				robot.keyRelease(KeyEvent.VK_META);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.keyRelease(KeyEvent.VK_G);
				robot.setAutoDelay(5000);

				robot.keyPress(KeyEvent.VK_META);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_META);
				robot.keyRelease(KeyEvent.VK_V);
				robot.setAutoDelay(5000);

				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.setAutoDelay(5000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			}
			if (getOS.contains("win")) {
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_V);

				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			}
			robot.setAutoDelay(2000);
			log.info("upload the file");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param webDriver
	 * @param webElementsort
	 * @param string
	 */
	public void selectFrame(WebDriver webDriver, WebElement ele) throws AssertFailedException {

		log.info("Switching between frames using Xpath ");

		webDriver.switchTo().frame(ele);

	}

	/**
	 * Method to select new window
	 * 
	 * @param webdriver
	 * @param value
	 */
	public void selectWindow(WebDriver webdriver, String title) {
		try {
			Set<String> windows = webdriver.getWindowHandles();

			Iterator<String> it = windows.iterator();
			while (it.hasNext()) {
				String w = webdriver.switchTo().window(it.next()).getTitle();
				if (w.contains(title)) {
					log.info("Switching to the window" + title);
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to verify the value not present
	 * 
	 * @param webdriver
	 * @param value
	 */
	public boolean verifynotpresent(WebDriver driver, WebElement element, String value) {
		boolean result = false;
		try {
			boolean isPresent = element.getText().toLowerCase().trim().equalsIgnoreCase(value);

			if (isPresent) {
				result = false;
				log.info(" Verification Failed ");
			} else {
				result = true;
				log.info(" Verification Passed ");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Method to verify the object presence
	 * 
	 * @param webdriver
	 * @param value
	 */
	public void verifyobject(WebElement element) throws AssertFailedException {
		boolean flag = element.isDisplayed();
		if (flag) {
			log.info(" Object is displayed ");
		} else {
			log.info(" Object is not displayed ");
			throw new AssertFailedException("Object is not displayed");
		}
	}

	/**
	 * Method to Assert the object value
	 * 
	 * @param webdriver
	 * @param value
	 */

	public void verify(WebElement element, String value) throws AssertFailedException {

		if (!StoreValue.equals("") && value.equals("")) {
			System.out.println("Actual is -->" + element.getText());
			System.out.println("Expected is -->" + value);
			boolean isPresent = element.getText().trim().equals(StoreValue);
			if (isPresent) {
				log.info("Verfication is Passed ");

			} else {
				log.info("Verfication is Failed ");
				throw new AssertFailedException(value + " Text Not Found");
			}
			// StoreValue="";
		} else if (!StoreValue.equals("") && value.equals("NV")) {
			System.out.println("Actual is -->" + element.getText());
			System.out.println("Expected is -->" + value);
			boolean isPresent = element.getText().trim().equals(StoreValue);
			if (isPresent) {
				log.info("Verfication is Passed ");

			} else {
				log.info("Verfication is Failed ");
				throw new AssertFailedException(value + " Text Not Found");
			}
		} else {
			System.out.println("Actual is -->" + element.getText());
			System.out.println("Expected is -->" + value);
			boolean isPresent = element.getText().trim().equals(value);
			if (isPresent) {
				log.info("Verfication is Passed ");

			} else {
				log.info("Verfication is Failed ");
				throw new AssertFailedException(value + " Text Not Found");
			}
		}
	}

	/**
	 * Method to Assert the object value contains the partial text
	 * 
	 * @param webdriver
	 * @param value
	 */

	public void verifycontains(WebElement element, String value) throws AssertFailedException {
		boolean isPresent;
		if (value.equals("NV")) {
			isPresent = element.getText().toLowerCase().contains(StoreValue);
			if (isPresent == false) {
				isPresent = element.getAttribute("value").toLowerCase().contains(StoreValue);
				System.out.println("Actual is -->" + element.getAttribute("value"));
				System.out.println("Expected is -->" + value.replaceFirst("'", ""));
			}
		} else {
			isPresent = element.getText().toLowerCase()
					.contains(value.replaceFirst("'", "").replace(";", ":").toLowerCase());
			if (isPresent == false) {
				isPresent = element.getAttribute("value").toLowerCase()
						.contains(value.replaceFirst("'", "").replace(";", ":").toLowerCase());
				System.out.println("Actual is -->" + element.getAttribute("value"));
				System.out.println("Expected is -->" + value.replaceFirst("'", ""));
			}
		}
		if (isPresent) {
			log.info("Verfication is Passed ");

		} else {
			log.info("Verfication is Failed ");
			throw new AssertFailedException(value + " Text Not Found");
		}

	}

	public void selectdropdown(WebDriver webDriver, IExecutionContext context, WebElement element, String Text)
			throws AssertFailedException {
		int index;
		String value;

		try {
			String value_search = Text;
			Select dropdown = new Select(element);
			if (Text.contains("index=")) {
				index = Integer.parseInt(Text.split("=")[1]);
				dropdown.selectByIndex(index);
			} else if (Text.contains("value=")) {
				value = Text.split("=")[1];
				dropdown.selectByValue(value);
			} else {
				System.out.println("");
				try {
					dropdown.selectByVisibleText(value_search);
				} catch (Exception e) {

				}
			}

		} catch (Exception e) {
			e.getMessage();
			throw new AssertFailedException("Failed to select the " + Text);
		}

	}

	public void defaultSelectedValue(IExecutionContext context, WebElement element, String value)
			throws AssertFailedException {

		try {
			String value_search = value;
			if (value_search.length() > 3) {
				if (value_search.substring(0, 3).equals("HMV")) {
					value_search = (String) context.getParentContext().getContextualData()
							.get(value_search.substring(3));

				}
			}
			Select dropdown = new Select(element);
			String selectedValue = dropdown.getFirstSelectedOption().getText();
			System.out.println("Exp->" + value_search + " Act->" + selectedValue);
			if (value_search.equals(selectedValue)) {
				log.info("seleted values are matched with the expected value");
			} else {
				throw new AssertFailedException(
						"Selected value is " + selectedValue + " and Expected Value is " + value_search);
			}

		} catch (Exception e) {
			throw new AssertFailedException("Verification failed");

		}
	}

	public void todayDate(WebDriver driver, WebElement element) throws InterruptedException, AWTException {

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
		Date date = new Date();
		String expected = dateFormat.format(date);
		try {
			element.click();
		} catch (Exception e) {
			System.out.println("already clicked on element");
		}
		Thread.sleep(2000);
		boolean flag = driver.findElements(By.id("JSCTXTINPUT")).size() > 0;
		if (flag) {
			driver.findElement(By.id("JSCTXTINPUT")).sendKeys(expected);
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} else {
			element.sendKeys(expected);
		}

	}

	public void futureDate(WebDriver driver, WebElement element, String val) throws InterruptedException, AWTException {

		int value = Integer.parseInt(val);
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, value);
		dt = c.getTime();
		String futuredate = dateFormat.format(dt);
		if (futuredate.contains("12/31")) {
			futuredate = "12/31/2017";
		}
		element.click();
		Thread.sleep(5000);
		boolean flag = driver.findElements(By.id("JSCTXTINPUT")).size() > 0;
		if (flag) {
			driver.findElement(By.id("JSCTXTINPUT")).sendKeys(futuredate);
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} else {
			element.sendKeys(futuredate);
		}

	}

	public void multiClick(WebElement element, String value) throws InterruptedException {

		int iterations = Integer.parseInt(value);
		for (int i = 1; i <= iterations; i++) {
			element.click();
			Thread.sleep(200);
		}
	}

	public void randomString(WebDriver webdriver, WebElement element, String value) {
		try {
			System.out.println("Entered into randomstring key");
			int len = value.length();
			final Random rand = new Random();
			System.out.println("The random string length is: " + len);
			String s = "abcdefghijklmnopqrstuvwxyz";
			StringBuilder str = new StringBuilder(len);
			for (int i = 1; i <= len; i++) {
				str.append(s.charAt(rand.nextInt(s.length())));
			}
			System.out.println("***And your Random String is Here***" + '\n' + "---->" + str);
			System.out.println(value + str);
			StoreValue = value + str;
			if (value.endsWith(".com")) {
				System.out.println("Enter into if condition");
				String[] val = value.split("@");
				String val1 = val[0];
				String val2 = val[1];
				StoreValue = val1 + str + "@" + val2;
				element.sendKeys(StoreValue);
			} else {
				element.sendKeys(StoreValue);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void VerifyAttributeColor(WebDriver webDriver, WebElement element, String exp_color)
			throws AssertFailedException {
		String strValue = element.getAttribute("style");

		if (strValue.contains(exp_color)) {
			log.info("Verify color of the webelement Pass");
		} else {
			log.info("Verify color of the webelement Fail");
		}

	}

	public void getCount(WebDriver webdriver, List<WebElement> element, IExecutionContext context,
			TestDataObjectModel data) {
		try {
			int n = element.size();
			log.info("rows count in this table: " + n);

			context.getParentContext().getContextualData().put(data.getDataValues().get(0), n);
			log.info("hash map key: " + data.getDataValues().get(0) + " ----> value: " + n);

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	/**
	 * Method to accept the popup
	 * 
	 * @param driver
	 * @param element
	 * @throws InterruptedException
	 */
	public void AlertAccept(WebDriver driver) throws AssertFailedException, InterruptedException {
		Alert alert = driver.switchTo().alert();
		alert.accept();
		Thread.sleep(1000);
		log.info("Clicking on OK button in popup");
	}

	/**
	 * Method cancel the popup
	 * 
	 * @param driver
	 * @param element
	 * @throws InterruptedException
	 */
	public void AlertDismiss(WebDriver driver) throws AssertFailedException, InterruptedException {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
		Thread.sleep(1000);
		log.info("Clicking on OK button in popup");
	}

	/**
	 * Method to quit all browsers
	 * 
	 * @param webdriver
	 * @param SqlQuery
	 */
	public void quit(IDLWebdriver idlWebdriver) {
		idlWebdriver.quit();
	}

	/**
	 * Method to run db queries
	 * 
	 * @param webdriver
	 * @param SqlQuery
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */

	public void runnableSqlQuery(WebDriver webdriver, String SqlQuery)
			throws InstantiationException, IllegalAccessException {
		String dbURL = ConfigurationFactory.getCurrentAppConfig().getDBUrl().toString();
		String userName = ConfigurationFactory.getCurrentAppConfig().getDBUname();
		String password = ConfigurationFactory.getCurrentAppConfig().getDBPassword();
		Connection connection;
		/*
		 * try { Class.forName("com.MySQL.jdbc.Driver").newInstance(); } catch
		 * (ClassNotFoundException e) { System.out.println(
		 * "SQL JDBC driver not found."); e.printStackTrace(); }
		 */

		try {
			connection = DriverManager.getConnection(dbURL, userName, password);
			if (connection != null) {
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(SqlQuery);
				while (rs.next()) {
					System.out.println(rs.getString(1));
				}
				rs.close();
			}
			connection.close();
		} catch (SQLException ex) {
			System.out.println("An error occurred. Maybe user/password is invalid");
			ex.printStackTrace();
		}
	}

	public void CalenderDate(WebDriver webDriver, WebElement element, String date) {
		mousemove();
		WebDriverWait wait = new WebDriverWait(webDriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(element));

		try {
			element.sendKeys(date);
			WebElement Calender = webDriver.findElement(By.id("JSCTXTINPUT"));
			Calender.sendKeys(date);
			log.info("Enter the date");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void NavigateToLink(WebDriver webDriver, WebElement tab, WebElement tabitem) {
		mousemove();

		WebDriverWait wait = new WebDriverWait(webDriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(tab));

		try {
			mouseOver(webDriver, tab);
			Thread.sleep(2000);
			tabitem.click();
			log.info("Navigating to the link");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void navigateUrl(WebDriver driver, String urlval) throws InterruptedException {
		log.info("Invoke URL");
		Thread.sleep(2000);
		String url = "";
		try {
			if (!urlval.contains("https:")) {
				url = "https:" + urlval;
				driver.get(url);
			} else
				driver.get(urlval);
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void generateNPI(WebDriver driver, WebElement element) throws InterruptedException {
		log.info("Generate NPI number randomly");

		String newNPI = "";
		Random rn = new Random();
		int range = 299999999 - 100000000 + 1;
		int randomNum = rn.nextInt(range) + 100000000;
		String base = Integer.toString(randomNum);
		int count = 0;
		int newCount = 0;
		if (!(Character.getNumericValue(base.charAt(0)) > 2) && !(Character.getNumericValue(base.charAt(0)) < 1)) {

			for (int i = base.length() - 1; i >= 0; i--) {
				if (count % 2 == 0) {
					int tempNumval = Character.getNumericValue(base.charAt(i));

					int tempNum = tempNumval * 2;
					if (tempNum >= 10) {
						int tempRemainder = tempNum % 10;
						newCount = newCount + tempRemainder + 1;
					} else {
						newCount = newCount + tempNum;
					}
				} else {
					newCount = newCount + Character.getNumericValue(base.charAt(i));

				}
				count++;
			}
			newCount = newCount + 24;
			int newMod = newCount % 10;
			int checkDigit = (10 - newMod) % 10;
			String chkdgt = Integer.toString(checkDigit);
			newNPI = base + chkdgt;
			element.sendKeys(newNPI);

			Thread.sleep(1000);
		}
	}

	public void emailverification(WebDriver driver) throws AssertFailedException, InterruptedException {
		String href = null;
		navigateUrl(driver, "https://mailhog.wptdev.com/");
		Thread.sleep(5000);
		driver.findElement(By.id("search")).sendKeys(StoreValue);

		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(5000);
		String actval = driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[1]"))
				.getText();

		if (StoreValue.equalsIgnoreCase(actval.trim())) {
			href = driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[1]"))
					.getAttribute("href");
			driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/div[1]")).click();
			Thread.sleep(5000);
			driver.switchTo().frame(driver.findElement(By.id("preview-html")));
			driver.findElement(By.xpath("html/body/table/tbody/tr[4]/td/a")).click();
		} else {
			System.out.println("Enter Valid email id");
		}
		Thread.sleep(15000);
		Set<String> s = driver.getWindowHandles();
		Iterator<String> it = s.iterator();
		while (it.hasNext()) {
			String wId = it.next();
			driver.switchTo().window(wId);
			String title = driver.getTitle();
			if (title.equalsIgnoreCase(href)) {
				System.out.println("Focus changed to " + title);
				break;
			}

		}

	}

	public void PageDown(WebDriver webdriver) throws AWTException {

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
	}

	public void pageup(WebDriver webdriver) throws AWTException {

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_UP);
		robot.keyRelease(KeyEvent.VK_PAGE_UP);

	}

	public void Desk_Enter() throws AWTException, InterruptedException {
		Robot robot = new Robot();
		Thread.sleep(5000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public boolean Compare_Text(WebDriver driver, WebElement element, String exp_text) {
		String act_text = element.getText();
		if (exp_text.equalsIgnoreCase(act_text)) {
			return true;
		} else {
			return false;
		}
	}

	public void Replace_text(WebDriver driver, WebElement element, String exp_text) {
		boolean flag = Compare_Text(driver, element, exp_text);
		if (flag == false) {
			element.clear();
			element.sendKeys(exp_text);
		}
	}

	public void Robot_Tab() throws InterruptedException, AWTException {
		Robot robot = new Robot();
		Thread.sleep(5000);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
	}

	public void RobotTextEntry(String text) throws AWTException {
		Robot robot = new Robot();
		StringSelection selection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);

		robot.setAutoDelay(2000);
	}

	public void robot_esc() throws InterruptedException, AWTException {
		Robot robot = new Robot();
		Thread.sleep(5000);
		robot.keyPress(KeyEvent.VK_ESCAPE);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
	}

	public void deleteClient(WebDriver driver, WebElement element) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(element));

		element.click();
		Thread.sleep(2000);
		boolean flag = driver.findElements(By.id("FNCONFIRMWORD")).size() > 0;
		if (flag) {
			driver.findElement(By.id("FNCONFIRMWORD")).sendKeys("INACTIVATE");

			log.info("Entered text into popup message");
		} else {
			log.info("Performed click on element");
		}

	}

	public void clicktablerow(WebDriver webDriver, String Text)
			throws InterruptedException, AssertFailedException, ArrayIndexOutOfBoundsException {

		String[] val = Text.split("-");
		String val1 = val[0];
		String val2 = val[1];
		String val3 = val[2];
		String val4 = val[3];

		WebElement table = webDriver.findElement(By.xpath("//div[@id='SCONTAINER']/table/tbody"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));

		for (int k = 2; k < rows.size(); k++) {
			String rawxpath = "//*[@id='CTR_" + k + "']";
			String str1 = webDriver.findElement(By.xpath(rawxpath)).getText().trim();
			/*
			 * int code3 = Integer.parseInt(str1); int code4 =
			 * Integer.parseInt(val1);
			 */

			if (str1.equalsIgnoreCase(val1)) {

				String rawxpath2 = "//*[@id='E_" + k + "_0']";
				webDriver.findElement(By.xpath(rawxpath2)).click();
				Thread.sleep(2000);
				String rawxpath3 = "//*[@id='SETFSTXT_" + k + "_0']";
				WebElement ele = webDriver.findElement(By.xpath(rawxpath3));
				ele.sendKeys(val2);
				Thread.sleep(1000);
				if (val3.equalsIgnoreCase("Fixed Rate")) {

					WebElement element = webDriver.findElement(By.xpath("//*[@id='RATE_TYPE']"));
					Select select = new Select(element);
					Thread.sleep(2000);
					select.selectByVisibleText(val3);

				} else {

					WebElement element = webDriver.findElement(By.xpath("//*[@id='RATE_TYPE']"));
					Select select = new Select(element);
					Thread.sleep(1000);
					select.selectByVisibleText(val3);
					WebElement element2 = webDriver.findElement(By.xpath("//*[@id='PER_VAL']"));
					element2.sendKeys(val4);
				}
				String rawxpath4 = "//*[@id='TDIX_" + k + "_0']/table/tbody/tr[7]/td/input[1]";
				webDriver.findElement(By.xpath(rawxpath4)).click(); // Save
																	// button
				break;
			}

		}

	}

	public void Robot_BackSpace() throws InterruptedException, AWTException {
		Robot robot = new Robot();
		Thread.sleep(5000);
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
		robot.keyRelease(KeyEvent.VK_BACK_SPACE);
	}

	public void Robot_DownArrow() throws InterruptedException, AWTException {
		Robot robot = new Robot();
		Thread.sleep(5000);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
	}

	public void Select_ByValue(WebDriver webdriver, WebElement element, String Text) {
		try {
			Select dropdown = new Select(element);
			dropdown.selectByValue(Text);
			log.info("Selected the item from dropdown " + Text);
		} catch (Exception e) {

		}

	}

	public void Time(WebDriver webdriver, WebElement element) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
		// System.out.println( sdf.format(cal.getTime()) );
		log.info("current time " + sdf.format(cal.getTime()));
		String t = sdf.format(cal.getTime());
		t = t.substring(0, 5) + t.substring(5, 7).toLowerCase();
		if (t.charAt(0) == '0') {
			t = t.substring(1, 7);
			element.sendKeys(t);
		} else {
			element.sendKeys(t);
		}
	}

	public void verifyvalue(WebDriver webdriver, WebElement element, String exp_val) throws AssertFailedException {
		boolean isPresent = false;
		String act_val = element.getAttribute("value");
		System.out.println("expval " + exp_val);
		System.out.println("actval " + act_val);
		if (!StoreValue.equals("") && exp_val.equals("")) {
			isPresent = element.getAttribute("value").trim().equalsIgnoreCase(StoreValue);
		} else if (!StoreValue.equals("") && exp_val.equals("NV")) {
			isPresent = element.getAttribute("value").trim().equalsIgnoreCase(StoreValue);
		}

		else {
			if (exp_val.contains(";")) {
				exp_val = exp_val.replace(";", ":");
			}
			isPresent = element.getAttribute("value").trim().equalsIgnoreCase(exp_val);
		}

		if (isPresent) {
			log.info("Verfication is Passed ");

		} else {
			log.info("Verfication is Failed ");
			throw new AssertFailedException(exp_val + " Text Not Found");
		}
	}

	public void select_autofill(WebElement element, String txt) {
		List<WebElement> options = element.findElements(By.tagName("div"));
		for (int i = 0; i < options.size(); i++) {
			List<WebElement> cols = options.get(i).findElement(By.tagName("table")).findElement(By.tagName("tr"))
					.findElements(By.tagName("td"));
			String act_txt = cols.get(0).getText();
			if (act_txt.trim().contains(txt)) {
				cols.get(0).click();
				log.info("Expected record available and it selected");
				break;
			} else {
				log.info("Expected record not available");
			}

		}

	}

	public void clientnamecheck(WebDriver driver, WebElement element, String txt) {
		List<WebElement> y = element.findElements(By.xpath("//p[contains(@id,'CL')]"));
		String[] x = txt.split(",");
		for (int i = 0; i < x.length; i++) {
			if (y.get(i).getText().contains(x[i])) {
				log.info("Verify successfull---> " + x[i]);
			}
		}
	}

	public void SelectDate(WebDriver webDriver, WebElement element, String val) {
		String a[] = val.split(",");
		int value = Integer.parseInt(a[0]);

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);

		c.add(Calendar.DATE, value);
		dt = c.getTime();

		if (a.length == 4) {
			String futuredate = dateFormat.format(dt) + " " + a[1] + ":" + a[2] + " " + a[3]; // date
																								// will
																								// be
																								// mm/dd/yyyy
																								// hh:mm
																								// AM/PM
			element.sendKeys(futuredate);
			log.info("Date entered as ==>> " + futuredate);
		} else if (a.length == 1) {

			String futuredate = dateFormat.format(dt);
			element.sendKeys(futuredate);
			log.info(element);
			log.info("Date entered as ==>> " + futuredate);

		}

	}

	public void verifyDate(WebElement element, String val) throws AssertFailedException, ParseException {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
		Date expdate = new Date();
		int value;
		Calendar c;
		String futuredate;
		String startDateString = "";
		if (val.equals("today")) {
			val = "0";
		} else if (val.contains("futuredate")) {
			String[] dateval = val.split(",");
			// String dateval1 = dateval[0];
			val = dateval[1];
		}

		value = Integer.parseInt(val);
		c = Calendar.getInstance();
		c.setTime(expdate);

		c.add(Calendar.DATE, value);
		expdate = c.getTime();
		futuredate = dateFormat.format(expdate);

		if (!(element.getAttribute("value") == null || element.getAttribute("value").equals("0"))) {
			startDateString = element.getAttribute("value");
		} else {
			startDateString = element.getText();
		}
		if (startDateString.length() < 10) {
			expdate = null;
			SimpleDateFormat yy = new SimpleDateFormat("MM/dd/yy");
			try {
				expdate = yy.parse(startDateString);
				startDateString = dateFormat.format(expdate);
			} catch (ParseException pe) {
				System.out.println("date conversion failed");
			}
		}

		boolean flag = startDateString.contains(futuredate);
		String txt = futuredate.replace(futuredate.substring(6, 8), "");
		if (txt.length() < 8) {
			txt = txt.substring(0, 3) + futuredate.substring(6, 8) + txt.substring(3, 6);
		}
		System.out.println(startDateString + "------" + txt + "---------" + futuredate);
		if (flag) {
			log.info("Verfication is Passed ");

		} else if (startDateString.contains(txt)) {
			log.info("Verfication is Passed ");
		} else {
			log.info("Verfication is Failed ");
			throw new AssertFailedException(value + " Text Not Found");
		}
	}

	public void verifyRadioSelection(WebElement element) throws AssertFailedException, InterruptedException {
		boolean Flag = element.isSelected();
		Thread.sleep(2000);

		if (Flag) {
			log.info("element is selected");
		} else {
			throw new AssertFailedException("element not selected");
		}

	}

	public void CheckBoxStatus(WebElement element, String exp) {
		String flag;
		if (element.isSelected()) {
			flag = "checked";
			log.info("Check box is in selected position -->" + element.getAttribute("value"));
		} else {
			flag = "unchecked";
			log.info("Check box is in unselected position -->" + element.getAttribute("value"));
		}
		if (flag.equalsIgnoreCase(exp.toLowerCase())) {
			log.info("Status check passed");
		} else {
			log.info("Status check Failed");
		}
	}

	public void deletefacility(WebDriver driver, WebElement element, String facilitytitle) throws InterruptedException {
		List<WebElement> rows = element.findElements(By.tagName("tr"));
		System.out.println(rows.size());
		for (int i = 0; i < rows.size(); i++) {
			List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
			String acttext = cols.get(0).getText();
			if (acttext.contains(facilitytitle)) {
				cols.get(3).findElement(By.tagName("img")).click();
				log.info("expected record available in the row----->" + (i + 1));
				Thread.sleep(5000);
				driver.findElement(By.id("FNCCONFIRMYESBUTTON")).click();
				Thread.sleep(7000);
				rows = element.findElements(By.tagName("tr"));
				i = 0;
				// break;
			}
		}
	}

	public void servicefacilityroomclick(WebElement element, String facilitytitle) {
		List<WebElement> rows = element.findElements(By.tagName("tr"));
		for (int i = 0; i < rows.size(); i++) {
			List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
			String acttext = cols.get(0).getText();
			if (acttext.contains(facilitytitle)) {
				cols.get(1).findElement(By.tagName("span")).click();
				log.info("expected record available and clicked");
				break;
			}
		}
	}

	public void getText(WebElement element) {
		String val = null;
		val = element.getText();
		if (val == null || val == "")
			val = element.getAttribute("value");
		StoreValue = val;
	}

	public void setText(WebElement element) throws InterruptedException {
		element.sendKeys(StoreValue);
		Thread.sleep(1000);
		// StoreValue="";
	}

	public void listverify(WebDriver driver, WebElement element, String txt) {
		List<WebElement> y = element.findElements(By.tagName("div"));

		for (int i = 0; i < y.size(); i++) {
			List<WebElement> cols = y.get(i).findElements(By.tagName("tr"));
			List<WebElement> col = cols.get(1).findElements(By.tagName("td"));
			String acttext = col.get(1).getText();
			System.out.println(acttext + "---------" + StoreValue);
			if (!StoreValue.equals("")) {
				if (acttext.equalsIgnoreCase(StoreValue)) {
					log.info("Email check successfull");
					List<WebElement> imgs = cols.get(0).findElements(By.tagName("img"));
					imgs.get(1).click();
					driver.findElement(
							By.xpath(".//*[@id='MYCONFIRMDIV']/table/tbody/tr/td/table/tbody/tr[2]/td/input[1]"))
							.click();
					// break;
				} else if (result) {
					acttext.equalsIgnoreCase(txt);
					log.info("Email check successfull");

					break;
				} else {
					log.info("Email check Failed");
				}

			}

		}

	}

	public void EDIFileContains(WebDriver webDriver, String value)
			throws InterruptedException, AssertFailedException, ArrayIndexOutOfBoundsException {
		String content = webDriver.findElement(By.xpath("//div[@id='MAIN_CONTENT']")).getText();
		if (content.contains(value.trim())) {
			log.info(value + "Value Present in EDI File");
		} else {
			log.info(value + "not present in EDI File");
		}
	}

	public void dayclick(WebDriver webdriver, WebElement element, String txt) {
		boolean flag = false;
		List<WebElement> rows = element.findElements(By.tagName("tr"));
		for (int i = 1; i < rows.size(); i++) {
			List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
			for (int j = 0; j < cols.size(); j++) {
				String acttxt = cols.get(j).getText();
				if (txt.equals(acttxt)) {
					flag = true;
					cols.get(j).click();
					log.info("Expected day clicked --->" + txt);
					break;
				}
			}
			if (flag) {
				break;
			}

		}
	}

	public void verifyclick(WebDriver webDriver, String text) throws AssertFailedException, InterruptedException {

		List<WebElement> ele = webDriver.findElements(By.xpath(text));
		int count = ele.size();
		if (count > 0) {
			log.info(" Object is displayed ");
			ele.get(0).click();
			Thread.sleep(3000);
		} else {
			log.info(" Object is not displayed ");
		}
	}

	public void weeklypatterncheck(WebDriver webdriver, WebElement element) throws AssertFailedException {

		String bg_clr = element.getAttribute("style");
		if (!bg_clr.contains("rgb(192, 192, 192)")) {
			element.click();
			log.info(" element selected  ");
		}

	}

	public void linkexistancecheck(WebDriver webdriver, String txt) {
		List<WebElement> links = webdriver.findElements(By.tagName("a"));
		for (int i = 0; i < links.size(); i++) {
			String name = links.get(i).getText();
			if (name.equalsIgnoreCase(txt)) {
				log.info("Extected link " + txt + " available");
			} else {
				log.info("Expected link " + txt + " not avaialble");
			}
		}
	}

	public void delete_client_provider(WebDriver webdriver, String txt) throws InterruptedException {
		{
			webdriver.findElement(By.xpath(".//*[@id='PSEARCH']")).sendKeys(txt);
			Thread.sleep(6000);
			boolean flag = webdriver
					.findElements(By.xpath("html/body/table/tbody/tr[3]/td[1]/div/div/table/tbody/tr/td/div"))
					.size() > 0;

			if (flag) {
				List<WebElement> rows = webdriver
						.findElements(By.xpath("html/body/table/tbody/tr[3]/td[1]/div/div/table/tbody/tr/td/div"));
				log.info("Entered into clientlist");
				webdriver.findElement(By.id("LISTVIEWTYPE_SMALL")).click();
				for (int i = 0; i < rows.size(); i++) {
					boolean flag1 = webdriver.findElements(By.xpath("//img[contains(@id,'DELETE')]")).size() > 0;
					if (flag1) {
						webdriver.findElement(By.xpath("//img[contains(@id,'DELETE')]")).click();
						Thread.sleep(3000);
						boolean bool = webdriver.findElements(By.id("FNCONFIRMWORD")).size() > 0;
						if (bool) {
							webdriver.findElement(By.id("FNCONFIRMWORD")).sendKeys("INACTIVATE");
							Thread.sleep(1000);
							log.info("Entered text into popup message");
						}
						webdriver.findElement(By.xpath("//input[@id='FNCCONFIRMYESBUTTON']")).click();
						Thread.sleep(3000);
					}
					webdriver
							.findElement(By.xpath("html/body/table/tbody/tr[3]/td[1]/div/div/table/tbody/tr[1]/td/div/table/tbody/tr[1]/td[4]/div/span[2]"))
							.click();
					Thread.sleep(3000);
					webdriver.findElement(By.xpath("//*[@id='FNCCONFIRMYESBUTTON']")).click();
					Thread.sleep(3000);

				}

			}

			else {

				WebElement table = webdriver.findElement(By.xpath(".//tbody[@id='MAINLIST']"));
				List<WebElement> providers = table.findElements(By.tagName("tr"));
				log.info("Entered into providerlist");
				for (int i = 1; i <= providers.size(); i++) {
					int count = webdriver.findElements(By.xpath("//img[contains(@id,'DLP_')]")).size();
					if (count > 0) {
						webdriver.findElement(By.xpath("//img[contains(@id,'DLP_')]")).click();
						Thread.sleep(2000);
						int countp = webdriver
								.findElements(By
										.xpath(".//*[@id='MYCONFIRMDIV']/table/tbody/tr/td/table/tbody/tr[2]/td/input[1]"))
								.size();
						if (countp == 0) {
							webdriver.switchTo().defaultContent();
							webdriver
									.findElement(By
											.xpath(".//*[@id='MYCONFIRMDIV']/table/tbody/tr/td/table/tbody/tr[2]/td/input[1]"))
									.click();
							Thread.sleep(2000);
							webdriver.switchTo().frame(webdriver.findElement(By.id("MAINCONTENT")));

						} else {
							webdriver
									.findElement(By
											.xpath(".//*[@id='MYCONFIRMDIV']/table/tbody/tr/td/table/tbody/tr[2]/td/input[1]"))
									.click();
							Thread.sleep(2000);
						}

					}

				}
				log.info("All the records deleted with the name--->" + txt);
			}

		}
	}

	public void ListOfElements_ThingsToDo(WebDriver webDriver, WebElement element, String elementToBeMatched)
			throws InterruptedException, AssertFailedException {
		String[] validate = elementToBeMatched.split(",");
		System.out.println(validate.length);
		List<WebElement> elementsSize = element.findElements(By.tagName("div"));
		System.out.println("Elements Size-->" + elementsSize.size());

		for (int i = 0; i < elementsSize.size(); i++) {
			String elementPresentInTable = elementsSize.get(i).getText();
			System.out.println(validate[0] + "-----" + validate[1]);
			if (elementPresentInTable.trim().contains(validate[0]) || validate[1].equals("PI")) {
				if (elementPresentInTable.trim().contains(validate[0])) {
					System.out.println("Entered into Public");
					List<WebElement> cols = elementsSize.get(i).findElements(By.tagName("img"));
					cols.get(0).click();
					break;
				}

			} else {
				if (elementPresentInTable.contains(validate[0])) {
					log.info("Element is displayed-----> " + validate[0]);
					if (elementPresentInTable.contains(validate[3])) {
						log.info("Element is displayed-----> " + validate[3]);
						List<WebElement> cols = elementsSize.get(i).findElements(By.tagName("td"));
						System.out.println(cols.size());
						boolean flag = cols.get(1).getText().contains(validate[1].replace("20", ""));
						System.out.println(cols.get(1).getText() + "----" + validate[1]);

						if (flag) {
							log.info("Element is displayed-----> " + validate[1]);
						} else {
							throw new AssertFailedException("Object is not displayed");
						}
						Thread.sleep(3000);
						List<WebElement> img = elementsSize.get(i).findElements(By.tagName("img"));
						if (img.get(0).isDisplayed() && img.get(1).isDisplayed()) {

							log.info("Edit and Mark as completed Elements are displayed-----> ");
							if (validate[2].equalsIgnoreCase("edit")) {
								log.info("Edit image clicked");
								img.get(0).click();
							} else if (validate[2].equalsIgnoreCase("mark as completed")) {
								log.info("Mark as completed image clicked");
								img.get(1).click();
							}

						}

						break;
					}
				}
			}
		}
	}

	public void alerttextverify(WebDriver driver, String exp) throws AssertFailedException {
		Alert alert = driver.switchTo().alert();
		String act = alert.getText();
		System.out.println(act + "-----------" + exp);
		if (exp.equalsIgnoreCase(act)) {
			log.info("verification Pass");
		} else {
			log.info("verification failed");
			throw new AssertFailedException(exp + " Text Not Found");
		}
	}

	public void RemoveTemplate(WebDriver webdriver, String formname)
			throws InterruptedException, AWTException, AssertFailedException {
		List<WebElement> rows = webdriver.findElements(By.xpath("html/body/div[1]/ul/li"));
		for (int i = 0; i < rows.size(); i++) {
			List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
			List<WebElement> spans = cols.get(0).findElements(By.tagName("span"));
			String form_name = spans.get(0).getText().trim();
			System.out.println(form_name);
			if (form_name.equalsIgnoreCase(formname)) {
				jsclick(webdriver, webdriver.findElement(By.xpath("//img[contains(@id,'DLTFRM')]")));
				Thread.sleep(2000);
				jsclick(webdriver, webdriver.findElement(
						By.xpath(".//*[@id='MYCONFIRMDIV']/table/tbody/tr/td/table/tbody/tr[2]/td/input[1]")));
				Thread.sleep(2000);
				rows = webdriver.findElements(By.xpath("html/body/div[1]/ul/li"));
				i = -1;
			}

		}

	}

	public void verifyAndDeleteTemplate(WebDriver webdriver, String formname)
			throws InterruptedException, AWTException, AssertFailedException {
		List<WebElement> rows = webdriver.findElements(By.xpath("html/body/div[1]/ul/li"));
		for (int i = 0; i < rows.size(); i++) {
			List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
			String actualformname = cols.get(0).getText().trim();
			System.out.println(actualformname);
			if (actualformname.contains(formname)) {
				log.info("Template name is updated on the list------->verification Passed");

				jsclick(webdriver, webdriver.findElement(By.xpath("//img[contains(@id,'DLTFRM')]")));
				break;
			}

		}

	}

	public void IntegrationKeyText(WebDriver driver, WebElement element)
			throws AssertFailedException, InterruptedException {
		String act_text = element.getText();
		System.out.println("act_text is " + element.getText());// ---->Integration
																// Key:
																// 769TB31565F62
		driver.findElement(By.xpath("//*[@id='WEBPTSETKEYDIV']/table/tbody/tr[7]/td/input[1]")).click();
		Thread.sleep(10000);
		String value = act_text.substring(17);
		System.out.println("Expected is -->" + value);

		String act = driver.findElement(By.xpath(".//*[@id='SF_TABLE']/tr[1]/td[2]")).getAttribute("textContent");

		System.out.println(act);
		boolean isPresent = driver.getPageSource().contains(value);
		if (isPresent) {
			log.info("Verfication is Passed ");

		} else {
			log.info("Verfication is Failed ");
			throw new AssertFailedException(value + " Text Not Found");
		}

	}

	public void actionsrightclick(WebDriver driver, WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).contextClick().build().perform();
	}

	public void TemplateStoreVerify(WebDriver driver, WebElement element) throws AWTException, InterruptedException {
		Robot_Tab();
		Robot_Tab();
		int n = 1;
		List<WebElement> y = element.findElements(By.tagName("div"));

		for (int i = 0; i < y.size(); i++) {
			if (n == 5) {
				PageDown(driver);
				n = 1;
			}
			List<WebElement> cols = y.get(i).findElements(By.tagName("tr"));

			List<WebElement> col = cols.get(0).findElements(By.tagName("td"));

			String acttext = col.get(0).findElement(By.tagName("td")).getText();

			if (!StoreValue.equals("")) {
				if (acttext.equalsIgnoreCase(StoreValue)) {
					log.info("Advertisement Header Verified");
					int x = i + 1;
					List<WebElement> check = driver
							.findElements(By.xpath("html/body/table/tbody/tr[2]/td/div/div[" + x + "]/table/tbody/tr"));
					System.out.println(check.size());
					List<WebElement> spans = driver
							.findElement(
									By.xpath("html/body/table/tbody/tr[2]/td/div/div[" + x + "]/table/tbody/tr/td[2]"))
							.findElements(By.tagName("span"));

					try {
						boolean flag = driver
								.findElement(By.xpath(
										"html/body/table/tbody/tr[2]/td/div/div[" + x + "]/table/tbody/tr[2]/td"))
								.isDisplayed();
					} catch (Exception e) {
						jsclick(driver, spans.get(1));
						break;
					}
				}
				n = n + 1;
				Thread.sleep(2000);
			}

		}
	}

	public void verifyobjectnotfound(WebElement element) throws AssertFailedException {
		boolean flag = element.isDisplayed();
		if (flag) {
			log.info(" Object is displayed ");
		} else {
			log.info(" Object is not displayed ");
		}
	}

	public void checkgreater(WebElement element, String exp) throws AssertFailedException {
		int intCount = Integer.parseInt(element.getText());
		int intExp = Integer.parseInt(exp);
		if (intCount > intExp) {
			log.info(intCount + " is greater than " + intExp);
		} else {
			log.info(intCount + " is not greater than " + intExp);
			throw new AssertFailedException("Object is not displayed");
		}
	}

	public void Insuranceclientlistcheck(WebElement element, String txt) throws AssertFailedException {
		boolean flag = false;
		String[] split = txt.split(",");
		List<WebElement> list = element.findElements(By.tagName("div"));
		for (int i = 0; i < list.size(); i++) {
			List<WebElement> cols = list.get(i).findElements(By.tagName("td"));
			String clientname = cols.get(0).getText();
			String Status = cols.get(1).getText();
			System.out.println(clientname + "------" + Status);
			System.out.println(split[0] + "----" + split[1]);
			WebElement Edit_Img = cols.get(2).findElement(By.tagName("img"));
			if (clientname.contains(split[0]) && Status.equalsIgnoreCase(split[1])) {
				log.info("Client avaialable in the Insurance card client list and the status -->" + split[0] + "------"
						+ split[1]);
				if (split[1].equalsIgnoreCase("active client")) {
					flag = true;
					Edit_Img.click();
				} else {
					flag = true;
					log.info("Client is Inactive");
				}
				break;
			}
		}
		if (flag == false) {
			log.info("Expected client not available---->" + split[0]);
			throw new AssertFailedException("Object is not displayed");
		}

	}

	public void clientbalancestatementcharges_listofelements(WebDriver webDriver, WebElement element,
			String elementToBeMatched) throws InterruptedException, AssertFailedException {
		String[] validate = elementToBeMatched.split(",");
		System.out.println(validate.length);
		List<WebElement> elementsSize = element.findElements(By.tagName("tr"));
		System.out.println("Elements Size-->" + elementsSize.size());
		if (validate[0].equalsIgnoreCase("SEP")) {
			elementsSize = webDriver
					.findElements(By.xpath("html/body/table/tbody/tr/td/table/tbody/tr[2]/td/div/table/tbody/tr"));
			for (int i = 2; i <= elementsSize.size(); i++) {
				List<WebElement> cols = webDriver
						.findElements(By.xpath("html/body/table/tbody/tr/td/table/tbody/tr[2]/td/div/table/tbody/tr["
								+ i + "]/td/table/tbody/tr/td"));

				boolean flag = cols.get(2).getText().contains(validate[1]);
				if (flag) {
					log.info(cols.get(2).getText() + " Verification Passed-----> " + validate[1]);
				} else {
					log.info(cols.get(2).getText() + " Verification Failed-----> " + validate[1]);
					throw new AssertFailedException(validate[1] + " Text Not Found");
				}
				flag = cols.get(5).getText().contains(validate[2]);
				if (flag) {
					log.info(cols.get(5).getText() + " Verification Passed-----> " + validate[2]);
				} else {
					log.info(cols.get(5).getText() + " Verification Failed-----> " + validate[2]);
					throw new AssertFailedException(validate[2] + " Text Not Found");
				}
			}

		} else {
			for (int i = 2; i < elementsSize.size(); i++) {
				String elementPresentInTable = elementsSize.get(i).getText();
				if (elementPresentInTable.contains(validate[0])) {
					log.info("Verification Passed-----> " + validate[0]);
					if (elementPresentInTable.contains(validate[1])) {
						log.info("Verification Passed-----> " + validate[1]);
						if (elementPresentInTable.contains(validate[2])) {
							log.info("Verification Passed-----> " + validate[2]);
							if (elementPresentInTable.contains(validate[3])) {
								List<WebElement> cols = elementsSize.get(i).findElements(By.tagName("span"));
								System.out.println("cols size-->" + cols.size());
								log.info("Verification Passed-----> " + validate[3]);
								String strValue = cols.get(0).getAttribute("style");
								System.out.println(strValue);
								if (strValue.contains("red")) {
									log.info("Verify color of the webelement Pass");
								} else {
									log.info("Verify color of the webelement Fail");
								}

							}
						} else {
							throw new AssertFailedException("Object is not displayed");
						}

					}

				}
			}
		}
	}

	public void Pdf_Read(WebDriver driver, String exp_txt) throws MalformedURLException, URISyntaxException {

		try {
			/*
			 * URL url = new URL(driver.getCurrentUrl()); File destination = new
			 * File("manager.php"); FileUtils.copyURLToFile(url, destination);
			 * File file = Paths.get(url.toURI()).toFile(); InputStream
			 * fileToParse = new FileInputStream(file); File file = new
			 * File("D:\\TLS\\TB-Automated-Framework\\Uploads\\ledger.pdf");
			 * PDFParser parser = new PDFParser(new FileInputStream(file));
			 */
			String expected = null;
			if (exp_txt.contains("today")) {
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
				Date date = new Date();
				expected = dateFormat.format(date);
			} else if (exp_txt.contains(";")) {
				expected = exp_txt.replace(";", ":");
			} else {
				expected = exp_txt;
			}

			URL url = new URL(driver.getCurrentUrl());
			BufferedInputStream fileToParse = new BufferedInputStream(url.openStream());
			// BufferedImage fileToParse = ImageIO.read(url);
			PDFParser parser = new PDFParser(fileToParse);
			parser.parse();

			String output = new PDFTextStripper().getText(parser.getPDDocument());
			System.out.println(output);

			if (output.contains(expected)) {
				System.out.println(expected);
			}
			parser.getPDDocument().close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void waitfortransmissionsuccessfull(WebDriver driver, String txt)
			throws InterruptedException, AssertFailedException, AWTException {
		String[] val = txt.split(",");

		if (val[0].equalsIgnoreCase("webpt")) {
			WebElement table = driver.findElement(By.xpath(
					".//*[contains(@id,'ext-gen')]/notag/table/tbody/tr/td/div[2]/table/tbody/tr/td[2]/table[2]"));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			System.out.println(+rows.size());

			for (int i = 3; i <= rows.size(); i++) {
				String xpath = ".//*[contains(@id,'ext-gen')]/notag/table/tbody/tr/td/div[2]/table/tbody/tr/td[2]/table[2]/tbody/tr["
						+ i + "]/td[4]";
				WebElement tabletext = driver.findElement(By.xpath(xpath));
				String exp = tabletext.getText();
				System.out.println(" " + exp);
				System.out.println(" " + val[1]);

				if (exp.contains(val[1])) {
					log.info("Verification passed");
				} else {

					for (int j = 1; j <= 5;) {
						Thread.sleep(60000);
						refreshBrowser(driver);
						Thread.sleep(3000);
						AlertAccept(driver);
						Thread.sleep(10000);
						String updatedxpath = ".//*[contains(@id,'ext-gen')]/notag/table/tbody/tr/td/div[2]/table/tbody/tr/td[2]/table[2]/tbody/tr["
								+ i + "]/td[4]";
						WebElement updatedtabletext = driver.findElement(By.xpath(updatedxpath));
						String updatedexp = updatedtabletext.getText();
						System.out.println(" " + val[1]);
						System.out.println(" " + updatedexp);
						if (!updatedexp.equalsIgnoreCase(val[1])) {
							j++;
						} else {
							break;
						}
					}
				}

			}

		}
	}

	public void enterDate(WebDriver driver, WebElement element, String date) {

		try {
			element.click();
			Thread.sleep(2000);
			boolean flag = driver.findElements(By.id("JSCTXTINPUT")).size() > 0;
			if (flag) {
				driver.findElement(By.id("JSCTXTINPUT")).sendKeys(date);
				Thread.sleep(2000);
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			} else {
				element.sendKeys(date);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insurancelistverify(WebDriver driver, WebElement element, String txt) {
		List<WebElement> y = element.findElements(By.tagName("div"));

		for (int i = 0; i < y.size(); i++) {
			List<WebElement> cols = y.get(i).findElements(By.tagName("tr"));
			List<WebElement> col = cols.get(0).findElements(By.tagName("td"));
			String acttext = col.get(0).getText();
			if (acttext.contains(txt)) {
				log.info("Client avaialble in the list---->" + txt);
				String activestatus = col.get(1).getText();
				if (activestatus.equalsIgnoreCase("active client")) {
					log.info("targetted client is active");
				} else {
					log.info("targetted client is inactive");
				}
				col.get(2).findElement(By.tagName("img")).click();
			}
		}

	}

	public boolean verifynullvalue(WebDriver driver, WebElement element) {
		boolean result = false;
		try {
			boolean isPresent = element.getText().isEmpty();

			if (isPresent) {
				result = true;
				log.info("Verification Passed-----> ");
			} else {
				result = false;
				log.info("Verification Failed-----> ");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;
	}

	public void Robot_UpArrow() throws InterruptedException, AWTException {
		Robot robot = new Robot();
		Thread.sleep(5000);
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_UP);
	}

	public void loginMessage(WebDriver driver) throws InterruptedException {

		boolean flag = driver.findElements(By.xpath(".//button[@class='eviction-option ok']")).size() > 0;
		if (flag) {
			driver.findElement(By.xpath(".//button[@class='eviction-option ok']")).click();
			Thread.sleep(3000);
		}

	}

	public void loadbundles_List(WebDriver webDriver, WebElement element, String value)
			throws InterruptedException, AssertFailedException {

		List<WebElement> elementsSize = element.findElements(By.tagName("div"));
		System.out.println("Elements Size-->" + elementsSize.size());
		for (int i = 0; i < elementsSize.size(); i++) {
			List<WebElement> cols = elementsSize.get(i).findElements(By.tagName("tr"));
			String elementPresentInTable = cols.get(0).getText();
			if (elementPresentInTable.contains(value)) {
				System.out.println("Existing Bundle available");
				List<WebElement> img = elementsSize.get(i).findElements(By.tagName("img"));
				img.get(1).click();
				Thread.sleep(5000);
				webDriver
						.findElement(
								By.xpath(".//*[@id='MYCONFIRMDIV']/table/tbody/tr/td/table/tbody/tr[2]/td/input[1]"))
						.click();
				Thread.sleep(7000);
				elementsSize = element.findElements(By.tagName("div"));
				i = 0;

			}

		}
	}

	public void loadbundleselect(WebDriver webDriver, WebElement element, String value)
			throws InterruptedException, AssertFailedException {
		List<WebElement> elementsSize = element.findElements(By.tagName("div"));
		System.out.println("Elements Size-->" + elementsSize.size());
		for (int i = 0; i <= elementsSize.size(); i++) {
			List<WebElement> ptags = elementsSize.get(i).findElements(By.tagName("p"));
			String elementPresentInTable = ptags.get(0).getText();
			if (elementPresentInTable.contains(value)) {
				System.out.println("" + elementPresentInTable);
				System.out.println("Bundle is available..." + value);
				Thread.sleep(5000);
				ptags.get(0).click();
				System.out.println("Bundle is selected");
				break;
			}
		}
	}

	public void EDIfilecontentverify(WebDriver webDriver, WebElement element)
			throws InterruptedException, AssertFailedException {
		String elementsSize = element.getText();
		int count = 0;
		String[] x = elementsSize.split("CLM");
		int len = x.length;
		System.out.println("CLM count is" + (len - 1));
		if (len > 2) {
			for (int i = 1; i < x.length; i++) {

				int index = 1;
				for (index = 1; index <= 7; index++) {
					boolean res = x[i].contains("LX*" + index);
					System.out.println("LX*" + index);
					if (res) {
						count = count + 1;
					}
				}
				if (count == 7) {
					log.info("Verification Passed");
					count = 0;

				} else {
					log.info("Verification Failed");
					throw new AssertFailedException("LX are not available as per the expectation");
				}

			}

		} else {
			log.info("Verification Failed");
			throw new AssertFailedException("CLM are not available as per the expectation");
		}

	}

	public void table_transmission(WebDriver driver, WebElement element)
			throws InterruptedException, AssertFailedException {
		// (//*[@class='wPTStyleTable'])[3]
		boolean result = true;
		List<WebElement> rows = element.findElements(By.tagName("tr"));
		System.out.println(rows.size());
		for (int i = 2; i < rows.size(); i++) {
			List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
			String transtype = cols.get(2).getText();
			if (transtype.contains("Financial")) {
				boolean flag = false;
				String statustxt = cols.get(3).getText();
				while (result) {

					flag = statustxt.contains("Transmission Successful");
					if (flag == false) {
						Thread.sleep(30000);
						refreshBrowser(driver);
						Thread.sleep(3000);
						AlertAccept(driver);
						Thread.sleep(7000);

					}

					if (flag == true) {
						break;
					}
					Thread.sleep(3000);
					element = driver.findElement(By.xpath("(//*[@class='wPTStyleTable'])[3]"));
					rows = element.findElements(By.tagName("tr"));
					cols = rows.get(i).findElements(By.tagName("td"));
					statustxt = cols.get(3).getText();
				}
				if (flag == true) {
					break;
				}
			}
		}
	}

	public void waitformail(WebDriver driver, WebElement element) throws AssertFailedException, InterruptedException {
		int count = 0;
		boolean flag = true;
		while (flag) {
			try {
				if (count == 1200) {
					throw new AssertFailedException("Time exceeded more than 20 minutes.Mail not recieved");

				}
				System.out.println(StoreValue);
				element.sendKeys(StoreValue);
				Thread.sleep(3000);
				Desk_Enter();
				Thread.sleep(3000);
				driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[2]/div[1]/div[1]/div/div")).click();
				flag = false;
				break;
			} catch (Exception e) {
				refreshBrowser(driver);
				Thread.sleep(30000);
				count = count + 30;
				element = driver.findElement(By.xpath(".//*[@id='search']"));
			}
		}

	}

	public void checkwordcount(WebElement element, String delimiter) throws AssertFailedException, IOException {
		int count = 0;

		String[] split1 = delimiter.split(",");
		System.out.println(split1[0] + "----" + split1[1]);

		String dirpath = FilenameUtils
				.separatorsToSystem(ConfigurationFactory.getCurrentAppConfig().getUploadsFolder());

		String FILENAME = dirpath + "\\testout.txt";
		FileWriter fw = new FileWriter(FILENAME);
		try (BufferedWriter bw = new BufferedWriter(fw)) {

			String content = element.getText();
			bw.write(content);
			bw.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
		fw.close();
		FileReader fr = new FileReader(FILENAME);
		BufferedReader br = new BufferedReader(fr);

		String line;

		while ((line = br.readLine()) != null) {
			System.out.println(line);
			if (line.contains(split1[0])) {
				count = count + 1;
			}
		}
		br.close();
		fr.close();

		File file = new File(FILENAME);
		if (file.delete()) {
			System.out.println("File deleted successfully");
		} else {
			System.out.println("Failed to delete the file");
		}

		if (count == Integer.parseInt(split1[1])) {
			System.out.println("Verification Passed");
		} else {
			System.out.println("Verification failed");
			throw new AssertFailedException("Count is not available as per the expectation");
		}
	}

	public void editTemplate(WebDriver webdriver, String formname)
			throws InterruptedException, AWTException, AssertFailedException {
		List<WebElement> rows = webdriver.findElements(By.xpath("html/body/div[1]/ul/li"));
		for (int i = 0; i < rows.size(); i++) {
			List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
			String actualformname = cols.get(0).getText().trim();
			System.out.println(actualformname);
			if (actualformname.contains(formname)) {
				jsclick(webdriver, webdriver.findElement(By.xpath("//img[contains(@id,'EDTFRM')]")));
				Thread.sleep(2000);
				break;
			}

		}

	}

	public void CalDayClick(WebDriver driver, WebElement element, String val)
			throws InterruptedException, AWTException {

		int value = Integer.parseInt(val);

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");

		Date dt = new Date();

		Calendar c = Calendar.getInstance();

		c.setTime(dt);

		c.add(Calendar.DATE, value);

		dt = c.getTime();
		System.out.println(dt);
		String futuredate = dateFormat.format(dt);
		if (futuredate.contains("12/31")) {
			String[] split = futuredate.split("/");
			futuredate = split[0] + "/" + split[1] + "/" + Integer.toString((Integer.parseInt(split[2]) - 1));
		}
		System.out.println(futuredate);
		Thread.sleep(2000);
		try {
			element.click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		String[] split = futuredate.split("/");

		String month = split[0];

		String day = split[1];

		String year = split[2];

		System.out.println(month + "-----" + day + "----" + year);

		WebElement yearDrop = driver.findElement(By.id("JSCYEARSELECTOR"));

		WebElement monthDrop = driver.findElement(By.id("JSCMONTHSELECTOR"));

		Select y = new Select(yearDrop);

		y.selectByVisibleText(year);

		Select m = new Select(monthDrop);

		if (split[0].equals("01"))

		{

			m.selectByVisibleText("Jan");

		}

		else if (split[0].equals("02"))

		{

			m.selectByVisibleText("Feb");

		}

		else if (split[0].equals("03"))

		{

			m.selectByVisibleText("Mar");

		}

		else if (split[0].equals("04")) {

			m.selectByVisibleText("Apr");

		}

		else if (split[0].equals("05")) {

			m.selectByVisibleText("May");

		}

		else if (split[0].equals("06")) {

			m.selectByVisibleText("Jun");

		}

		else if (split[0].equals("07")) {

			m.selectByVisibleText("Jul");

		}

		else if (split[0].equals("08")) {

			m.selectByVisibleText("Aug");

		}

		else if (split[0].equals("09")) {

			m.selectByVisibleText("Sep");

		}

		else if (split[0].equals("10")) {

			m.selectByVisibleText("Oct");

		}

		else if (split[0].equals("11")) {

			m.selectByVisibleText("Nov");

		}

		else if (split[0].equals("12")) {

			m.selectByVisibleText("Dec");

		}
		Thread.sleep(5000);
		String d = day.replaceFirst("0", "");
		dayclick(driver, driver.findElement(By.xpath("//*[@id='JSCCALENDARHOLDER']/div/table/tbody")), d);

	}

	public void weekdaypicker(WebDriver driver, WebElement element) throws InterruptedException, AWTException {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		String futuredate = dateFormat.format(dt);
		System.out.println(futuredate);

		String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dt);

		System.out.println(dayOfWeek);
		if (dayOfWeek.equalsIgnoreCase("monday")) {
			c.add(Calendar.DATE, 5);
		} else if (dayOfWeek.equalsIgnoreCase("tuesday")) {
			c.add(Calendar.DATE, 4);
		} else if (dayOfWeek.equalsIgnoreCase("wednesday")) {
			c.add(Calendar.DATE, 3);
		} else if (dayOfWeek.equalsIgnoreCase("thursday")) {
			c.add(Calendar.DATE, 2);
		} else if (dayOfWeek.equalsIgnoreCase("friday")) {
			c.add(Calendar.DATE, 1);
		} else if (dayOfWeek.equalsIgnoreCase("saturday")) {
			c.add(Calendar.DATE, 7);
		} else if (dayOfWeek.equalsIgnoreCase("sunday")) {
			c.add(Calendar.DATE, 6);
		}
		dt = c.getTime();
		futuredate = dateFormat.format(dt);
		System.out.println(futuredate);

		element.click();
		Thread.sleep(2000);
		boolean flag = driver.findElements(By.id("JSCTXTINPUT")).size() > 0;
		if (flag) {
			driver.findElement(By.id("JSCTXTINPUT")).sendKeys(futuredate);
			Thread.sleep(2000);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} else {
			element.sendKeys(futuredate);
		}
	}

	public void randomnumber(WebElement element) {
		Random random = new Random();
		int x = 0;
		boolean loop = true;
		while (loop) {
			x = random.nextInt();
			if (Integer.toString(x).length() == 10 && !Integer.toString(x).startsWith("-")) {
				loop = false;
			}
		}
		StoreValue = Integer.toString(x);
		System.out.println(StoreValue);
		element.clear();
		element.sendKeys(StoreValue);
	}

	public void Announcements_Close(WebDriver driver) throws InterruptedException, AssertFailedException {
		int count = driver.findElements(By.xpath("//*[@id='USERANNOUNCEMENTDIV']/p/span[contains(text(),'Close')]"))
				.size();
		if (count > 0) {
			driver.findElement(By.xpath("//*[@id='USERANNOUNCEMENTDIV']/p/span[contains(text(),'Close')]")).click();
			Thread.sleep(2000);
		}
	}

	public void configurablewaitmethod(String val) throws InterruptedException {
		String v = val;
		long v1 = Long.parseLong(v);
		long v2 = v1 * 60 * 1000;
		Thread.sleep(v2);
	}

	public void selectProvider(WebDriver driver, String providername) throws InterruptedException {

		driver.findElement(By.xpath("//td[contains(text(),'" + providername + "')]/..//input")).click();

		Thread.sleep(3000);

	}

	public void Paymentclass_Delete(WebDriver driver, WebElement element, String exptxt) throws AssertFailedException {
		// exptxt=CodeOfPaymentClasses,verify/edit/delete
		boolean flag = false;
		String[] split = exptxt.split(",");
		List<WebElement> table = element.findElements(By.tagName("table"));
		System.out.println(table.size());
		for (int i = 0; i < table.size(); i++) {
			List<WebElement> cols = table.get(i).findElement(By.tagName("tr")).findElements(By.tagName("td"));
			String acttxt = cols.get(0).getText();
			if (split[0].equalsIgnoreCase(acttxt)) {
				System.out.println(split[0] + "----" + acttxt);
				flag = true;
				log.info("Verification Passed");
				if (split[1].equalsIgnoreCase("edit")) {
					cols.get(2).findElement(By.tagName("img")).click();
					log.info("Clicked on edit");
					break;
				} else if (split[1].equalsIgnoreCase("delete")) {
					cols.get(3).findElement(By.tagName("img")).click();
					driver.findElement(By.id("FNCCONFIRMYESBUTTON")).click();
					log.info("Clicked on delete");
					break;
				} else if (split[1].equalsIgnoreCase("verify")) {
					log.info("Verification Passed");
					log.info("Blue pencil icon existence " + cols.get(2).findElement(By.tagName("img")).isDisplayed());
					log.info("Delete icon existence " + cols.get(3).findElement(By.tagName("img")).isDisplayed());
					break;
				}
			}
		}

		if (flag == false && !split[1].equalsIgnoreCase("delete")) {
			log.info("Verification Failed");
			throw new AssertFailedException("Expected payment classes code not available");
		}
	}
	
	public void Delete_Emails(WebDriver driver,String exptxt) throws InterruptedException, AWTException
	{
	driver.findElement(By.xpath(".//*[@id='search']")).clear();
	driver.findElement(By.xpath(".//*[@id='search']")).sendKeys(exptxt);
	Desk_Enter();
	Thread.sleep(5000);	
	
	//driver.findElement(By.xpath("html/body/div[1]/div/div[2]/div[1]/span[2]")).click();	
	//Thread.sleep(2000);
	WebElement block=driver.findElement(By.xpath("html/body/div/div/div[2]/div[2]"));
	List<WebElement> emails=block.findElements(By.tagName("div"));
	if (emails.size() > 0) 
	{
	for (int i = 0; i < emails.size(); i++) 
	{
	emails.get(i).click();
	if (emails.size()>=35)
	{
	PageDown(driver);
	PageDown(driver);
	PageDown(driver);
	PageDown(driver);
	}
	else if (emails.size()>=25)
	{
	PageDown(driver);
	PageDown(driver);
	PageDown(driver);
	}
	else if (emails.size()>=15) {
	PageDown(driver);
	PageDown(driver); 
	}
	else
	{
	PageDown(driver);
	}
	Thread.sleep(3000);
	driver.findElement(By.xpath("html/body/div/div/div[2]/div[3]/button[2]")).click();
	Thread.sleep(3000);
	block=driver.findElement(By.xpath("html/body/div/div/div[2]/div[2]"));
	emails=block.findElements(By.tagName("div"));
	if (emails.size()==0)
	{
	break;
	}
	else
	{
	i=0;
	}
	}
	}
	}
	
	public void alertIsPresent(WebDriver driver) {

		try 
		{ WebElement alertpopup=driver.findElement(By.xpath("//div[@id='FNCCONFIRMDIV']"));
		WebElement yesButton=driver.findElement(By.xpath("//input[@id='FNCCONFIRMYESBUTTON']"));
		if(alertpopup.isDisplayed()){
		yesButton.click();
		Thread.sleep(2000);
		log.info("Clicked Yes in alert popup");

		}
		else{
		log.info("No alert is present");
		}

		} 
		catch (Exception e)
		{
		e.printStackTrace();
		}


		}

		public void verifyList(WebDriver webDriver,WebElement element) throws InterruptedException, AssertFailedException {


		List<WebElement> rows=element.findElements(By.tagName("tr"));
		System.out.println("Total no of rows :"+rows.size());
		if (rows.size()>0) 
		{
		log.info("Payment List is more than one....Verification passed.");

		}
		else
		{
		log.info("Payment List is less than one....Verification failed.");
		}
		}
	
}
