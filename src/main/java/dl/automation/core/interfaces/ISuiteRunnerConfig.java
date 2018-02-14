/**
 * 
 */
package dl.automation.core.interfaces;

public interface ISuiteRunnerConfig {
	/**
	 * @return the baseUrl
	 */
	String getBaseUrl();

	/**
	 * @param baseUrl the baseUrl to set
	 */
	void setBaseUrl(String baseUrl);

	/**
	 * @return the browser
	 */
	String getBrowser();

	/**
	 * @param browser the browser to set
	 */
	void setBrowser(String browser);

	/**
	 * @return the browserDriverPath
	 */
	String getBrowserDriverPath();

	/**
	 * @param browserDriverPath the browserDriverPath to set
	 */
	void setBrowserDriverPath(String browserDriverPath);

	void setModule(String browser);

	String getModule();
	
	void setSeq(int number);
	
    int getSeq();
}
