/**
 * 
 */
package dl.automation.core.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AdditionalConditions {

	static Logger log = Log4jUtil.loadLogger(AdditionalConditions.class);

	public static ExpectedCondition<Boolean> angularHasFinishedProcessing() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return Boolean.valueOf(((JavascriptExecutor) driver)
						.executeScript(
								"return (window.angular !== undefined) && (angular.element(document).injector() !== undefined) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)")
						.toString());
			}
		};
	}

	public static ExpectedCondition<Boolean> waitForLoadBarInvisibility() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webdriver) {
				boolean val = true;
			while (val) {

					WebDriverWait wait = new WebDriverWait(webdriver, 60);

					//log.info("======>>>>  Verfying Loading screen on UI   <<<<======= ");
					
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//body")));

		        		//log.info(" <<<==  Into loading Screen bar of the Application  ==>>>  ");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loader-inner line-scale')]")));
						if (ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loader-inner line-scale')]"))
								.apply(webdriver)) {
							//log.info(" <<<==  Loading Screen........ is not displayed  ==>>>  ");
							val = false;
						}
						//log.info(" <<<==  Into loading Screen bar of the Application  ==>>>  ");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'snackbar')]")));
						if (ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'snackbar')]"))
								.apply(webdriver)) {
							//log.info(" <<<==  Loading Screen........ is not displayed  ==>>>  ");
							val = false;
						}
						//log.info(" <<<==  Into data loading Screen bar of the Application  ==>>>  ");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.id("holidayAbsenceDataLoad")));
						if (ExpectedConditions
								.invisibilityOfElementLocated(By.id("holidayAbsenceDataLoad"))
								.apply(webdriver)) {
							//log.info(" <<<==  Loading Screen........ is not displayed  ==>>>  ");
							val = false;
						}
						
						
						return true;
					}
					// TODO Auto-generated method stub
			return val;
				};
				
		};
}
}