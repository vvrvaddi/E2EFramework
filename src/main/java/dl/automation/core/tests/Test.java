/**
 * 
 */
package dl.automation.core.tests;

import dl.automation.core.executor.IDLWebdriver;
import dl.automation.core.interfaces.IExecutionContext;
import dl.automation.core.interfaces.ITest;
import dl.automation.core.utils.ExecutionEventsEmitter;


public class Test implements ITest {
	
	private IExecutionContext context;
	private ExecutionEventsEmitter eventEmitter;

	/**
	 * 
	 */
	public Test(IExecutionContext context) {
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITest#execute()
	 */
	@Override
	public void execute(IDLWebdriver webDriver) throws Exception {
		// TODO Auto-generated method stub

	}


	/**
	 * @return the context
	 */
	public IExecutionContext getContext() {
		return context;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITest#register(dl.automation.core.utils.ExecutionEventsEmitter)
	 */
	@Override
	public void register(ExecutionEventsEmitter eventEmitter) {
		this.eventEmitter = eventEmitter;
	}

	/**
	 * @return the eventEmitter
	 */
	public ExecutionEventsEmitter getEventEmitter() {
		return eventEmitter;
	}
}
