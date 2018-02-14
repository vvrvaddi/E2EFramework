/**
 * 
 */
package dl.automation.core.interfaces;

import java.util.List;


public interface IResultCollector {
	public IResultProvider<IResultItem> getResultProvider();
	public List<IResultItem> getRestultSet();
}
