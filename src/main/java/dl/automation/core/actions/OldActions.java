/**
 * 
 */
package dl.automation.core.actions;

import dl.automation.core.exceptions.AssertFailedException;
import dl.automation.core.executor.IDLWebdriver;
import dl.automation.core.model.TestDataObjectModel;
import dl.automation.core.types.enums.IdentifierType;
import org.openqa.selenium.WebElement;

import java.util.List;


public class OldActions extends BaseActions {

	/**
	 * 
	 */
	public OldActions() {
	}

	public void navigate(IDLWebdriver webDriver, TestDataObjectModel testData) throws AssertFailedException, InterruptedException {
		if (testData != null && testData.getDataValues() != null){
			int count = testData.getDataValues().size();
			if (count > 0){
				for (int i = 0; i < count -1; i++){
					List<WebElement> elements = webDriver.findElements(testData.getDataValues().get(i), IdentifierType.Xpath);
					super.click(webDriver.getWebDriver(), elements.get(0));
				}
				List<WebElement> elements = webDriver.findElements(testData.getDataValues().get(count-1), IdentifierType.Xpath);
				super.click(webDriver.getWebDriver(), elements.get(0));
			}
		}
	}

}
