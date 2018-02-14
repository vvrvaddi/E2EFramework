/**
 * 
 */
package dl.automation.core.context;

import dl.automation.core.interfaces.IExecutionContext;
import dl.automation.core.interfaces.ITestStepExecutionContext;


public class TestStepExecutionContext extends ExecutionContext implements ITestStepExecutionContext {

	/**
	 * @param parent
	 */
	public TestStepExecutionContext(IExecutionContext parent) {
		super(parent);
	}

}
