/**
 * 
 */
package dl.automation.core.context;
import dl.automation.core.interfaces.IAppExecutionContext;
import dl.automation.core.interfaces.IExecutionContext;
//import dl.automation.core.interfaces.ISuiteExecutionContext;
//import dl.automation.core.interfaces.ITestApp;
//import dl.automation.core.interfaces.ITestSuite;

public class AppExecutionContext extends ExecutionContext implements IAppExecutionContext{

	/**
	 * 
	 */
	public AppExecutionContext(IExecutionContext parent) {
		super(parent);
	}


}
