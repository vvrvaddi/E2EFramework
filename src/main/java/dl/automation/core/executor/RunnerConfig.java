/**
 * 
 */
package dl.automation.core.executor;

import java.util.ArrayList;
import java.util.List;

import dl.automation.core.interfaces.IReportProvider;
import dl.automation.core.interfaces.IResultCollector;
import dl.automation.core.interfaces.IResultItem;
import dl.automation.core.interfaces.IResultProvider;
import dl.automation.core.interfaces.IRunnerConfig;
import dl.automation.core.providers.ProviderFactory;
import dl.automation.core.results.ResultsCollectorFactory;
import dl.automation.core.utils.ExecutionEventsEmitter;


public class RunnerConfig implements IRunnerConfig {
	private IResultProvider<IResultItem> resultProvider;
	private List<IReportProvider> reportProviders;
	private IResultCollector resultCollector;
	
	public RunnerConfig(ExecutionEventsEmitter eventEmitter){
		super();
		reportProviders = new ArrayList<>();
		resultProvider = ProviderFactory.getResultProvider(eventEmitter);
		resultCollector = ResultsCollectorFactory.collect(resultProvider);
		reportProviders.add(ProviderFactory.getExcelReportProvider(resultCollector));
		reportProviders.add(ProviderFactory.getHtmlReportProvider(resultCollector));
		reportProviders.add(ProviderFactory.getEmailReportProvider(resultCollector));
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.executor.IRunnerConfig#getResultProvider()
	 */
	@Override
	public IResultProvider<IResultItem> getResultProvider() {
		return resultProvider;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.executor.IRunnerConfig#getReportProvider()
	 */
	@Override
	public List<IReportProvider> getReportProviders() {
		return reportProviders;
	}
}
