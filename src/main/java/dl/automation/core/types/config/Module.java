/**
 * 
 */
package dl.automation.core.types.config;

import java.util.List;

import dl.automation.core.interfaces.IModuleConfig;


class Module implements IModuleConfig {
	private String name;
	private Boolean run;
	private String baseUrl;
	private String browser;
	private String browserDrivePath;
	private List<String> testCases;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the run
	 */
	public Boolean getRun() {
		return run;
	}
	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
	/**
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}
	

	
	/**
	 * @return the browserDrivePath
	 */
	public String getBrowserDrivePath() {
		return browserDrivePath;
	}
	/**
	 * @return the testCases
	 */
	public List<String> getTestCases() {
		return testCases;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param run the run to set
	 */
	public void setRun(Boolean run) {
		this.run = run;
	}
	/**
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	/**
	 * @param browser the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	/**
	 * @param browserDrivePath the browserDrivePath to set
	 */
	public void setBrowserDrivePath(String browserDrivePath) {
		this.browserDrivePath = browserDrivePath;
	}
	/**
	 * @param testCases the testCases to set
	 */
	public void setTestCases(List<String> testCases) {
		this.testCases = testCases;
	}
		
}
