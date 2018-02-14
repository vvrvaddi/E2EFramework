/**
 * 
 */
package dl.automation.core.providers;

import java.io.FileNotFoundException;
import java.io.IOException;

import dl.automation.core.interfaces.IReportProvider;
import dl.automation.core.interfaces.IResultCollector;


public abstract class ReportProvider implements IReportProvider {
	private IResultCollector resultCollector;
	/**
	 * @return the resultCollector
	 */
	public IResultCollector getResultCollector() {
		return resultCollector;
	}
	/**
	 * 
	 */
	public ReportProvider() {
	}
	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.IReportProvider#init(dl.automation.core.interfaces.IResultCollector)
	 */
	@Override
	public void init(IResultCollector collector) {
		this.resultCollector = collector;
	}
	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.IReportProvider#generateReport()
	 */
	@Override
	public abstract void generateReport() throws FileNotFoundException, IOException;
}
