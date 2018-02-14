/**
 * 
 */
package dl.automation.core.browsers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

class ChromeDriver extends BaseDriver {

	private static ChromeDriverService chromeDriverService;

	/**
	 * @param webDriver
	 */
	public ChromeDriver(String driverExecutablePath, boolean remote) {
		super(remote);
		if (remote) {
			this.urlPath = driverExecutablePath;
		} else {

			chromeDriverService = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File(driverExecutablePath)).usingAnyFreePort().build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dl.automation.core.executor.IDLWebdriver#initilaize()
	 */
	@Override
	public void initilaize() {
		try {
			ChromeOptions options = new ChromeOptions();
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			options.addArguments("--disable-popup-blocking");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

			if (!this.isRemote) {
				chromeDriverService.start();
				super.initilaize(new org.openqa.selenium.chrome.ChromeDriver(chromeDriverService, capabilities));

			} else {
				this.url = new URL(this.urlPath);
				WebDriver chromeDriver = new RemoteWebDriver(this.url, capabilities);
				super.initilaize(chromeDriver);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dl.automation.core.executor.IDLWebdriver#run(java.util.List)
	 */
	@Override
	public void run(String baseUrl) {
		this.get(baseUrl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dl.automation.core.executor.IDLWebdriver#cleanup()
	 */
	@Override
	public void cleanup() {

	}

	@Override
	public void quit() {
		super.quit();
	}

}
