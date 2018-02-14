/**
 * 
 */
package dl.automation.core.interfaces;

import java.util.HashMap;

public interface IExecutionContext extends IContext {
	public IExecutionContext getParentContext();
	public HashMap<String, Object> getContextualData();
}
