/**
 * 
 */
package dl.automation.core.types.config;

import java.util.ArrayList;
import java.util.List;

import dl.automation.core.interfaces.ISuiteConfig;


class Suite implements ISuiteConfig {
	private String name;
	private Boolean run;
	private int seq;
	private String baseUrl;
	private String browser;	
	private String browserDrivePath;
	private List<String> testCases;
	private ArrayList<String> modules;
	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}
	/**
	 * @return the run
	 */
	@Override
	public Boolean getRun() {
		return run;
	}
	/**
	 * @return the baseUrl
	 */
	@Override
	public String getBaseUrl() {
		return baseUrl;
	}
	/**
	 * @return the browser
	 */
	@Override
	public String getBrowser() {
		return browser;
	}
	
	@Override
	public ArrayList<String> getModules(){
		return modules;
	}
	
	/**
	 * @return the browserDrivePath
	 */
	@Override
	public String getBrowserDrivePath() {
		return browserDrivePath;
	}
	/**
	 * @return the testCases
	 */
	@Override
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


	public void setModules(ArrayList<String> modules) {
		this.modules = modules;		
	}
	

	@Override
	public int getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}
				

}
