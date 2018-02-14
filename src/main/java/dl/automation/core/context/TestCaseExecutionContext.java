/**
 * 
 */
package dl.automation.core.context;

import dl.automation.core.interfaces.IExecutionContext;
import dl.automation.core.interfaces.ITestCaseExecutionContext;



public class TestCaseExecutionContext extends ExecutionContext implements ITestCaseExecutionContext {
	
	/**
	 * 
	 */
	public TestCaseExecutionContext(IExecutionContext parent) {
		super(parent);
	}

}
