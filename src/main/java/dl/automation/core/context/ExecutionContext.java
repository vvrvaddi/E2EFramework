/**
 * 
 */
package dl.automation.core.context;

import java.util.HashMap;

import dl.automation.core.interfaces.IExecutionContext;


public class ExecutionContext implements IExecutionContext {

	private IExecutionContext parent = null;
	private HashMap<String, Object> contextualData = null;
	
	/**
	 * 
	 */
	public ExecutionContext(IExecutionContext parent) {
		this.parent = parent;
		contextualData = new HashMap<String, Object>();
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.IExecutionContext#getParentContext()
	 */
	@Override
	public IExecutionContext getParentContext() {
		return this.parent;
	}

	/**
	 * @return the intermediateResults
	 */
	public HashMap<String, Object> getContextualData() {
		return contextualData;
	}
}
