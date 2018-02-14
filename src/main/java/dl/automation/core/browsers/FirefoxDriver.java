/**
 * 
 */
package dl.automation.core.browsers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


class FirefoxDriver extends BaseDriver {
	private static GeckoDriverService geckDriverService;

	/**
	 * 
	 */
public FirefoxDriver(String driverExecutablePath, boolean remote) {
	super(remote);
	
	if(remote){
				this.urlPath = driverExecutablePath;
		
		}else{	
			
			geckDriverService = new GeckoDriverService.Builder()
					  .usingDriverExecutable(new File(driverExecutablePath))
					  .usingAnyFreePort()
					  .build();
		}	
		
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.executor.IDLWebdriver#initilaize()
	 */

	@Override
	public void initilaize() {
		try {	
			DesiredCapabilities capabilities=DesiredCapabilities.firefox();			
			capabilities.setCapability("marionette", false);	
		
			if(!this.isRemote){
				geckDriverService.start();
				 super.initilaize(new org.openqa.selenium.firefox.FirefoxDriver(capabilities));
			}else{
				this.url = new URL(urlPath);
				WebDriver firefox = new RemoteWebDriver(this.url, capabilities);
				super.initilaize(firefox);
			}			
			
		} catch (IOException e) {		
			e.printStackTrace();
		}
	
		
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.executor.IDLWebdriver#run(java.util.List)
	 */
	@Override
	public void run(String baseUrl) {
		this.get(baseUrl);
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.executor.IDLWebdriver#cleanup()
	 */
	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void quit() {
		super.quit();
	}

}
