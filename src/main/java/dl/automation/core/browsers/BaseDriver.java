/**
 * 
 */
package dl.automation.core.browsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import dl.automation.core.executor.IDLWebdriver;
import dl.automation.core.model.TestStepModel;
import dl.automation.core.providers.ProviderFactory;
import dl.automation.core.types.enums.IdentifierType;
import dl.automation.core.utils.AdditionalConditions;
import dl.automation.core.utils.Log4jUtil;

abstract class BaseDriver implements IDLWebdriver {

	static Logger log = Log4jUtil.loadLogger(BaseDriver.class);
	private WebDriver webDriver;
	private Wait<WebDriver> webDriverWait;
	protected boolean isRemote = false;
	protected URL url;
	protected String urlPath;
	private static final String TASKLIST = "tasklist";
	private static final String KILL = "taskkill /F /IM ";

	/**
	 * 
	 */
	public BaseDriver() {
	}

	public BaseDriver(boolean isRemote) {
		this.isRemote = isRemote;
	}

	public BaseDriver(URL url) {
		this.url = url;
	}

	/**
	 * @param webDriver
	 */
	protected void initilaize(WebDriver webDriver) {
		this.setWebDriver(webDriver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dl.automation.core.executor.IDLWebdriver#run(java.lang.String)
	 */
	@Override
	public void run(String baseUrl) {
		this.get(baseUrl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * dl.automation.core.executor.IDLWebdriver#executeAction(java.lang.String,
	 * java.lang.String, dl.automation.core.model.TestStepModel)
	 */
	@Override
	public void executeStep(String action, String actionType, TestStepModel testStepModel) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * dl.automation.core.executor.IDLWebdriver#executeAction(dl.automation.core
	 * .model.TestStepModel)
	 */
	@Override
	public void executeStep(TestStepModel testStepModel) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dl.automation.core.executor.IDLWebdriver#cleanup()
	 */
	@Override
	public void cleanup() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#get(java.lang.String)
	 */
	@Override
	public void get(String url) {
		// this.webDriver.manage().window().maximize();
		this.webDriver.get(url);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getCurrentUrl()
	 */
	@Override
	public String getCurrentUrl() {
		return this.webDriver.getCurrentUrl();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getTitle()
	 */
	@Override
	public String getTitle() {
		return this.webDriver.getTitle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements(By by) {
		return this.webDriver.findElements(by);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#findElement(org.openqa.selenium.By)
	 */
	@Override
	public WebElement findElement(By by) {

		// log.info("==>>find web element on UI ==>");

		return this.getWebDriverWait().until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				if (AdditionalConditions.waitForLoadBarInvisibility().apply(driver)) {

					WebDriverWait wait = new WebDriverWait(driver, 30);

					WebElement element = null;
					try {

						// log.info("Checking visibility of element ===>> " +
						// element);

						element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));

						if (ExpectedConditions.stalenessOf(element).apply(driver)) {
							return null;
						}
						return element;

					} catch (Exception e) {

						log.info(e.getMessage());

						log.info("Visisblity of web Element failed ");

						if (element != null && element.isEnabled()) {
							return element;
						} else if (element == null) {
							return driver.findElement(by);
						}

					}
				}

				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getPageSource()
	 */
	@Override
	public String getPageSource() {
		return this.webDriver.getPageSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#close()
	 */
	@Override
	public void close() {
		this.webDriver.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#quit()
	 */
	@Override
	public void quit() {
		this.webDriver.quit();

		/*
		 * try { if(isProcessRunning("geckodriver.exe")) {
		 * killProcess("geckodriver.exe"); } else
		 * if(isProcessRunning("geckodriver")) { killProcess("geckodriver"); }
		 * 
		 * else if(isProcessRunning("chrome.exe")) { killProcess("chrome.exe");
		 * } else if(isProcessRunning("chrome")) { killProcess("chrome"); } }
		 * catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 */
	@Override
	public Set<String> getWindowHandles() {
		return this.webDriver.getWindowHandles();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#getWindowHandle()
	 */
	@Override
	public String getWindowHandle() {
		return this.webDriver.getWindowHandle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#switchTo()
	 */
	@Override
	public TargetLocator switchTo() {
		return this.webDriver.switchTo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#navigate()
	 */
	@Override
	public Navigation navigate() {
		return this.webDriver.navigate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.WebDriver#manage()
	 */
	@Override
	public Options manage() {
		return this.webDriver.manage();
	}

	/**
	 * @return the webDriver
	 */
	@Override
	public WebDriver getWebDriver() {
		return webDriver;
	}

	/**
	 * @param webDriver
	 *            the webDriver to set
	 */
	@Override
	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * dl.automation.core.executor.IDLWebdriver#findByXpath(java.lang.String)
	 */
	@Override
	public WebElement findByXpath(String xpath) {
		return this.findElement(By.xpath(xpath));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * dl.automation.core.executor.IDLWebdriver#findByCssSelector(java.lang.
	 * String)
	 */
	@Override
	public WebElement findByCssSelector(String cssSelector) {
		return this.findElement(By.cssSelector(cssSelector));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dl.automation.core.executor.IDLWebdriver#findById(java.lang.String)
	 */
	@Override
	public WebElement findById(String idAttribute) {
		return this.findElement(By.id(idAttribute));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * dl.automation.core.executor.IDLWebdriver#findByName(java.lang.String)
	 */
	@Override
	public WebElement findByName(String nameAttribute) {
		return this.findElement(By.name(nameAttribute));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dl.automation.core.executor.IDLWebdriver#findByClassName(java.lang.
	 * String)
	 */
	@Override
	public WebElement findByClassName(String classNameAttribute) {
		return this.findElement(By.className(classNameAttribute));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * dl.automation.core.executor.IDLWebdriver#findByLinkText(java.lang.String)
	 */
	@Override
	public WebElement findByLinkText(String linkText) {
		return this.findElement(By.linkText(linkText));
	}

	@Override
	public String screenShot(TakesScreenshot screenShotDriver) throws IOException {
		return ProviderFactory.getScreenShotProvider()
				.saveScreenShot(screenShotDriver.getScreenshotAs(OutputType.FILE));
	}

	/**
	 * @param identifier
	 * @param identifierType
	 * @return
	 */

	public WebElement findElement(String identifier, IdentifierType identifierType) {
		List<WebElement> elements = this.findElements(identifier, identifierType);
		if (elements.size() > 0) {
			return elements.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<WebElement> findElements(String identifier, IdentifierType identifierType) {
		List<WebElement> elements = new ArrayList<>();
		switch (identifierType) {
		case Xpath:
			elements.add(this.findByXpath(identifier));
			break;
		case Css:
			elements.add(this.findByCssSelector(identifier));
			break;
		case Id:
			elements.add(this.findById(identifier));
			break;
		case Name:
			elements.add(this.findByName(identifier));
			break;
		case LinkText:
			elements.add(this.findByLinkText(identifier));
			break;
		case None:
			break;
		default:
			break;
		}
		return elements;
	}

	/**
	 * @return
	 */
	private Wait<WebDriver> getWebDriverWait() {
		if (this.webDriverWait == null) {
			this.webDriverWait = new FluentWait<WebDriver>(this.webDriver).withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(100, TimeUnit.MILLISECONDS)

					.ignoring(StaleElementReferenceException.class);
		}
		return this.webDriverWait;

	}

	public static boolean isProcessRunning(String serviceName) throws Exception {
		serviceName = serviceName.toLowerCase();
		Process p = Runtime.getRuntime().exec(TASKLIST);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			line = line.toLowerCase();
			if (line.contains(serviceName)) {
				return true;
			}
		}
		return false;
	}

	public static void killProcess(String serviceName) throws Exception {
		Runtime.getRuntime().exec(KILL + serviceName);
	}

}
