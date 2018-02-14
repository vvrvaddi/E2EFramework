/**
 * 
 */
package dl.automation.core.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import dl.automation.core.browsers.DriverFactory;
import dl.automation.core.executor.IDLWebdriver;
import dl.automation.core.interfaces.ISuiteExecutionContext;
import dl.automation.core.interfaces.ISuiteRunnerConfig;
import dl.automation.core.interfaces.ITestCase;
import dl.automation.core.interfaces.ITestSuite;
import dl.automation.core.types.enums.Browsers;
import dl.automation.core.utils.ExecutionEventsEmitter;
import dl.automation.core.utils.StringUtils;

public class TestSuite extends Test implements ITestSuite {

    private List<ITestCase> testCases = null;
    private String suiteName = "";
    public ISuiteRunnerConfig suiteRunnerConfig = null;
    public IDLWebdriver baseDriver;
    int count;

    public TestSuite(ISuiteExecutionContext context, List<ITestCase> testCases,
            ISuiteRunnerConfig suiteRunnerConfig) {
        super(context);
        this.testCases = testCases;
        this.suiteRunnerConfig = suiteRunnerConfig;
       
        testCases.forEach(tc -> {       
     	    String source=tc.getTestCaseModel().getSource();
     	
     	    List<String> val=StringUtils.splitDataSets(source);
     	    tc.getTestCaseModel().setDataSets(val);
     	 
        });        
    }

    
	/**
     * @return the suiteRunnerConfig
     */
    public ISuiteRunnerConfig getSuiteRunnerConfig() {
        return suiteRunnerConfig;
    }

    /**
     * @param suiteRunnerConfig
     *            the suiteRunnerConfig to set
     */
    public void setSuiteRunnerConfig(ISuiteRunnerConfig suiteRunnerConfig) {
        this.suiteRunnerConfig = suiteRunnerConfig;
    }   
   
    @Override
    public void execute(IDLWebdriver webDriver) throws Exception {
        execute(this.testCases);    
    
    }

    public void execute(IDLWebdriver webdriver, List<String> testCaseNumbers,Map<String,List<String>> failedcasedatasets) throws Exception{
        List<ITestCase> filteredTestCases = new ArrayList<>();
        testCases.forEach(testCase -> {
            testCaseNumbers.forEach(tcn -> {
                String id = testCase.getTestCaseModel().getId();
                if(tcn.equalsIgnoreCase(id)){
                	testCase.getTestCaseModel().setDataSets(failedcasedatasets.get(id));
                	filteredTestCases.add(testCase);
                }
            });
        });
        execute(filteredTestCases);
    }

    /**
     * @for browser quit and launch
     */
    public void browserLaunch() {
        testCases.size();
    }

    /**
     * @return the suiteName
     */
    public String getSuiteName() {
        return suiteName;
    }

    /* (non-Javadoc)
     * @see dl.automation.core.interfaces.ITest#register(dl.automation.core.utils.ExecutionEventsEmitter)
     */
    @Override
    public void register(ExecutionEventsEmitter eventEmitter) {
        super.register(eventEmitter);
        this.testCases.forEach(tc -> tc.register(eventEmitter));
    }

    public void execute(List<ITestCase> runnableTestCases) throws Exception {

        Browsers browser = Browsers.valueOf(suiteRunnerConfig.getBrowser());
        runnableTestCases.forEach(tc -> {           
            try {
            		String path = suiteRunnerConfig.getBrowserDriverPath();
            		if(path.startsWith("http")){
            			baseDriver = DriverFactory.getDriver(browser, path, true);
            		}else{
            			baseDriver = DriverFactory.getDriver(browser, path, false);
            		}
                    baseDriver.run(suiteRunnerConfig.getBaseUrl());                    
                    tc.execute(baseDriver);  
                    baseDriver.quit();

            } catch (Exception e) {
                e.printStackTrace();
                baseDriver.quit();
            }
          
        }); 
       
    }	

}
