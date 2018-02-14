/**
 * 
 */
package dl.automation.core.browsers;

import dl.automation.core.executor.IDLWebdriver;
import dl.automation.core.types.enums.Browsers;

public final class DriverFactory {
	public static IDLWebdriver getDriver(Browsers browser, String driverExecutablePath, boolean isRemote){
		switch (browser) {
		case Chrome:
			ChromeDriver chromeDriver = new ChromeDriver(driverExecutablePath, isRemote);
			chromeDriver.initilaize();
			chromeDriver.manage().window().maximize();
			return (IDLWebdriver)chromeDriver;
		case Firefox:			
			FirefoxDriver fireFoxDriver = new FirefoxDriver(driverExecutablePath, isRemote);
			fireFoxDriver.initilaize();
			fireFoxDriver.manage().window().maximize();
			return (IDLWebdriver)fireFoxDriver;
		case IE11:
			return null;
		case Opera:
			return null;
		case Safari:
			return null;
		default:
			return null;
		}
	}
}
