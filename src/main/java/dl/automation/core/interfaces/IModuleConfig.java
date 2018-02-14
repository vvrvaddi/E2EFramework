package dl.automation.core.interfaces;

import java.util.List;


public interface IModuleConfig {
	
	/**
	 * @return the name
	 */
	public String getName();
	/**
	 * @return the run
	 */
	public Boolean getRun();
	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl();	
	/**
	 * @return the browser
	 */
	public String getBrowser();
	/**
	 * @return the browserDrivePath
	 */
	public String getBrowserDrivePath();
	/**
	 * @return the testCases
	 */
	public List<String> getTestCases();
}