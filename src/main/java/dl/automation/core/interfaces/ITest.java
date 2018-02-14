/**
 * 
 */
package dl.automation.core.interfaces;

import dl.automation.core.executor.IDLWebdriver;
import dl.automation.core.utils.ExecutionEventsEmitter;


public interface ITest {
	public void execute(IDLWebdriver webDriver) throws Exception;
	public void register(ExecutionEventsEmitter eventEmitter);
}
