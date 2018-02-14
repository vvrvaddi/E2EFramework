/**
 * 
 */
package dl.automation.core.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;


public interface IReportProvider {
	public void init(IResultCollector collector);
	public void generateReport() throws FileNotFoundException, IOException;
}
