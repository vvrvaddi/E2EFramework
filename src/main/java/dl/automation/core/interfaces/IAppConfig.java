/**
 * 
 */
package dl.automation.core.interfaces;

import java.util.List;


public interface IAppConfig {
	/**
	 * @return the name
	 */
	public String getName() ;
	/**
	 * @return the platform
	 */
	public String getPlatform();
	/**
	 * @return the suites
	 */
	public List<ISuiteConfig> getSuites();
	
	public List<IModuleConfig> getModules();
	
	/**
	 * @return the appBaseUrl
	 */
	public String getAppBaseUrl();
	/**
	 * @return the browser
	 */
	
	public String getBaseUrl();
	/**
	 * @return the browser
	 */
	
	public String getBrowser();
	/**
	 * @return the browserDriverPath
	 */
	public String getBrowserDriverPath();
	/**
	 * @return the resultsFolder
	 */
	public String getResultFolder();
	/**
	 * @param resultsFolder the resultsFolder to set
	 */
	public void setResultFolder(String resultsFolder);

    public String getEnvironment();

	public void setEnvironment(String env);
	
	public void setExcelResultsPath(String filePath);
	
	public String getExcelResultsPath();
	
	public void setHtmlResultsPath(String filePath);
	
	public String getHtmlResultsPath();

	void setInputFolderN(String inputFolderN);
	
	void setUploadsFolder(String UploadsFolder);

	public String getInputFolderN();
	
	public String getUploadsFolder();

    public Integer getIterations();
    
    public void setIterations(Integer iterations);
    
    public String getDBUrl();
    
    public String getDBUname();
    
    public String getDBPassword();
	
	
}