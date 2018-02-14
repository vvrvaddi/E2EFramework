/**
 * 
 */
package dl.automation.core.interfaces;

import java.util.List;


public interface IRunnerConfig {
	/**
	 * @return the resultProvider
	 */
	IResultProvider<IResultItem> getResultProvider();

	/**
	 * @return the reportProvider
	 */
	List<IReportProvider> getReportProviders();

}