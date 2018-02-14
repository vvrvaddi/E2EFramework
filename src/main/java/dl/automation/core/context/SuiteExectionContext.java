/**
 * 
 */
package dl.automation.core.context;


import dl.automation.core.interfaces.IExecutionContext;
import dl.automation.core.interfaces.ISuiteExecutionContext;


public class SuiteExectionContext extends ExecutionContext implements ISuiteExecutionContext {

	/**
	 * 
	 */
	public SuiteExectionContext(IExecutionContext parent) {
		super(parent);
	}

}
