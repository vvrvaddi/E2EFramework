/**
 * 
 */
package dl.automation.core.types.config;

import dl.automation.core.interfaces.ISuiteRunnerConfig;


public class SuiteRunnerConfig implements ISuiteRunnerConfig {


	private String baseUrl="";
	private String browser="";
	private String browserDriverPath="";
	private String module="";
	private int seq;
	
	/**
	 * 
	 */
	
	public SuiteRunnerConfig(String baseUrl,  String browser, String browserDriverPath,int seq) {
		this.baseUrl = baseUrl;		
		this.browser = browser;
		this.browserDriverPath = browserDriverPath;
		this.seq = seq;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ISuiteRunnerConfig#getBaseUrl()
	 */
	@Override
	public String getBaseUrl() {
		return this.baseUrl;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ISuiteRunnerConfig#setBaseUrl(java.lang.String)
	 */
	@Override
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;

	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ISuiteRunnerConfig#getBrowser()
	 */
	@Override
	public String getBrowser() {
		return this.browser;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ISuiteRunnerConfig#setBrowser(java.lang.String)
	 */
	@Override
	public void setBrowser(String browser) {
		this.browser = browser;

	}
	
	@Override
	public String getModule() {
		return module;

	}
	
	@Override
	public void setModule(String browser) {
		this.module  = module;

	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ISuiteRunnerConfig#getBrowserDriverPath()
	 */
	@Override
	public String getBrowserDriverPath() {
		return this.browserDriverPath;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ISuiteRunnerConfig#setBrowserDriverPath(java.lang.String)
	 */
	@Override
	public void setBrowserDriverPath(String browserDriverPath) {
		this.browserDriverPath = browserDriverPath;

	}
	

	@Override
	public void setSeq(int number) {
		this.seq = number;
	}
	
	@Override
	public int getSeq() {
		return this.seq;
	}

}
