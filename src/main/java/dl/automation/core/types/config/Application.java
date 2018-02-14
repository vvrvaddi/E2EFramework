/**
 * 
 */
package dl.automation.core.types.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import dl.automation.core.interfaces.IAppConfig;
import dl.automation.core.interfaces.IModuleConfig;
import dl.automation.core.interfaces.ISuiteConfig;
import dl.automation.core.utils.Log4jUtil;


class Application implements IAppConfig {
    static Logger log = Log4jUtil.loadLogger(Application.class);
    private String name;
    private String platform;
    private String resultFolder;
    private String appBaseUrl;
    private String BaseUrl;
    private String DBURL;
    private String dbUname;
    private String dbPassword;
    private String browser;
    private String browserDriverPath;
    private String environment;
    private String excelResultsPath;
    private String htmlResultsPath;
    private List<ISuiteConfig> suites = new ArrayList<>(); 
    private List<IModuleConfig> modules = new ArrayList<>(); 
    private String inputFolderN;
    private String UploadsFolder;
    private Integer iterations;

    @Override
    public Integer getIterations() {
        return this.iterations;
    }

    @Override
    public void setIterations(Integer value) {
        this.iterations = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dl.automation.core.types.config.IAppConfig#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dl.automation.core.types.config.IAppConfig#getPlatform()
     */
    @Override
    public String getPlatform() {
        return platform;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dl.automation.core.types.config.IAppConfig#getSuites()
     */
    @Override
    public List<ISuiteConfig> getSuites() {   
    
        return suites;
    }
    
    @Override
    public List<IModuleConfig> getModules() {   
    
        return modules;
    } 
    
    
   
    /*
     * (non-Javadoc)
     * 
     * @see dl.automation.core.types.config.IAppConfig#getAppBaseUrl()
     */
    @Override
    public String getAppBaseUrl() {
        log.info("URL of the Application" + appBaseUrl);
        return appBaseUrl;
    }
    
    @Override
    public String getBaseUrl() {
        log.info("URL of the Application");
        return BaseUrl;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see dl.automation.core.types.config.IAppConfig#getBrowser()
     */
    @Override
    public String getBrowser() {
        log.info("Browser for the Application to launch  " + browser);
        return browser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dl.automation.core.types.config.IAppConfig#getBrowserDriverPath()
     */
    @Override
    public String getBrowserDriverPath() {
        return browserDriverPath;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param platform
     *            the platform to set
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * @param appBaseUrl
     *            the appBaseUrl to set
     */
    public void setAppBaseUrl(String appBaseUrl) {
        this.appBaseUrl = appBaseUrl;
    }
    
    public void setBaseUrl(String baseUrl) {
        this.BaseUrl = baseUrl;
    }

    /**
     * @param browser
     *            the browser to set
     */
    public void setBrowser(String browser) {
        this.browser = browser;
    }

    /**
     * @param browserDriverPath
     *            the browserDriverPath to set
     */
    public void setBrowserDriverPath(String browserDriverPath) {
        this.browserDriverPath = browserDriverPath;
    }

    /**
     * @param suites
     *            the suites to set
     */
    public void setSuites(List<ISuiteConfig> suites) {
        this.suites = suites;
    }
    
    public void setModules(List<IModuleConfig> modules) {
        this.modules = modules;
    }
  

    /**
     * @return the resultsFolder
     */
    public String getResultFolder() {
        return resultFolder;
    }

    /**
     * @param resultsFolder
     *            the resultsFolder to set
     */
    public void setResultFolder(String resultsFolder) {
        this.resultFolder = resultsFolder;
    }

    @Override
    public String getEnvironment() {
        return this.environment;
    }

    @Override
    public void setEnvironment(String env) {
        this.environment = env;
    }

    /* (non-Javadoc)
     * @see dl.automation.core.interfaces.IAppConfig#setExcelResultsPath(java.lang.String)
     */
    @Override
    public void setExcelResultsPath(String filePath) {
        this.excelResultsPath = filePath;
    }

    /* (non-Javadoc)
     * @see dl.automation.core.interfaces.IAppConfig#getExcelResultsPath()
     */
    @Override
    public String getExcelResultsPath() {
        return this.excelResultsPath;
    }

    /* (non-Javadoc)
     * @see dl.automation.core.interfaces.IAppConfig#setHtmlResultsPath(java.lang.String)
     */
    @Override
    public void setHtmlResultsPath(String filePath) {
        this.htmlResultsPath = filePath;
    }

    /* (non-Javadoc)
     * @see dl.automation.core.interfaces.IAppConfig#getHtmlResultsPath()
     */
    @Override
    public String getHtmlResultsPath() {
        return this.htmlResultsPath;
    }
   
    @Override
    public void setInputFolderN(String inputFolderN) {
        this.inputFolderN = inputFolderN;
    }
    

    @Override
    public String getInputFolderN() {
        return inputFolderN;
    }  
    
    @Override
    public void setUploadsFolder(String UploadsFolder) {
        this.UploadsFolder = UploadsFolder;
    }
    

    @Override
    public String getUploadsFolder() {
        return UploadsFolder;
    }  
 
    public String getDBUrl() {
        log.info("URL of the DB: " + DBURL);
        return DBURL;
    } 
    public void setDBUrl(String DBURL) {
        this.DBURL = DBURL;
    }  
    
    public String getDBUname() {
        log.info("UserName of the DB: " + dbUname);
        return dbUname;
    } 
    public void setDBUname(String dbUname) {
        this.dbUname = dbUname;
    }  
    
    public String getDBPassword() {
        log.info("Password of the DB: " + dbPassword);
        return dbPassword;
    } 
    public void setDBPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }  
   
}
