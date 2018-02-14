/**
 * 
 */
package dl.automation.core.executor;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import dl.automation.core.model.TestStepModel;
import dl.automation.core.types.enums.IdentifierType;


public interface IDLWebdriver extends WebDriver {
	public void initilaize();
	public void run(String baseUrl);
	public void executeStep(String action, String actionType, TestStepModel testStepModel);
	public void executeStep(TestStepModel testStepModel);
	public void cleanup();
	public WebElement findByXpath(String xpath);
	public WebElement findByCssSelector(String cssSelector);
	public WebElement findById(String idAttribute);
	public WebElement findByName(String nameAttribute);
	public WebElement findByClassName(String classNameAttribute);
	public WebElement findByLinkText(String linkText);
	public String screenShot(TakesScreenshot driver) throws IOException;
	public WebDriver getWebDriver();
	public void setWebDriver(WebDriver driver);
	/**
	 * @param identifier
	 * @param identifierType
	 * @return
	 */
	public List<WebElement> findElements(String identifier, IdentifierType identifierType);
	/**
	 * @param identifier
	 * @param identifierType
	 * @return
	 */
	public WebElement findElement(String identifier, IdentifierType identifierType);
}
