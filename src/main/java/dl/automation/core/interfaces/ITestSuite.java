/**
 * 
 */
package dl.automation.core.interfaces;
import java.util.List;
import java.util.Map;

import dl.automation.core.executor.IDLWebdriver;


public interface ITestSuite extends ITest{
	/**
	 * @return the suiteName
	 */
	public String getSuiteName();
	
	public ISuiteRunnerConfig getSuiteRunnerConfig(); 
    
    public void execute(IDLWebdriver webdriver, List<String> testCaseNumbers,Map<String,List<String>> failedcasedatasets) throws Exception;
}
