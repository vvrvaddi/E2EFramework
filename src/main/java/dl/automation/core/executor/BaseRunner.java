/**
 * 
 */
package dl.automation.core.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import dl.automation.core.interfaces.IReportProvider;
import dl.automation.core.interfaces.IResultItem;
import dl.automation.core.interfaces.IRunnerConfig;
import dl.automation.core.interfaces.ITest;
import dl.automation.core.interfaces.ITestApp;
import dl.automation.core.results.ResultsCollectorFactory;
import dl.automation.core.types.config.ConfigurationFactory;
import dl.automation.core.types.enums.ResultStatus;
import dl.automation.core.results.ResultsCollector;


abstract class BaseRunner implements Runner {

    private ITest test = null;
    private IRunnerConfig runnerConfig = null;
    /**
     * 
     */
    public BaseRunner() {
    	
    }
    
    /**
     * @return the test
     */
    public ITest getTest() {
        return test;
    }

    /**
     * @param test
     *            the test to set
     */
    public void setTest(ITest test) {
        this.test = test;
    }

    /**
     * @return the runnerConfig
     */
    public IRunnerConfig getRunnerConfig() {
        return runnerConfig;
    }

    /**
     * @param runnerConfig
     *            the runnerConfig to set
     */
    public void setRunnerConfig(IRunnerConfig runnerConfig) {
        this.runnerConfig = runnerConfig;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
    	try {
    	int noOfIterations = ConfigurationFactory.getCurrentAppConfig().getIterations();
    	ResultsCollector resultCollector = ResultsCollectorFactory.collect(runnerConfig.getResultProvider());
    	
    	for (int i = 0; i < noOfIterations; i++) {
    	int index = i;
    	if (index < 1) {
    	this.test.execute(null);
    	
    	resultCollector.getRestultSet().forEach(resultItem -> {
    	if (resultItem.getStatus() == ResultStatus.Pass) {    	
    	resultItem.setIterationNumber(index + 1);
    	}
    	});
    	}else{
    	List<IResultItem> failures = resultCollector.getRestultSet().stream().filter(r ->
    	r.getStatus() != ResultStatus.Pass).collect(Collectors.toList());
    	List<String> failedTestCaseNumbers = new ArrayList<>();
    	Map<String,List<String>> failedcasedatasets= new HashMap<String,List<String>>();
    	failures.forEach(fr -> { 
    	String tcid=fr.getTestCaseId();
    	List<String> datasets=new ArrayList<>(); 
    	failures.stream().filter(f -> f.getTestCaseId().equalsIgnoreCase(tcid)).forEach(g -> {
    	datasets.add(g.getDataSet());
    	});
    	failedcasedatasets.put(tcid,datasets);
    	

    	});
    	failures.forEach(fr -> {
    	         failedTestCaseNumbers.add(fr.getTestCaseId());  });
    	
    	 List<String> failedTestCaseNumbersdis = failedTestCaseNumbers.stream().distinct().collect(Collectors.toList());
    	 ((ITestApp)this.test).execute(null, failedTestCaseNumbersdis,failedcasedatasets);

    	 }
    	}
    
    	List<IReportProvider> reportProviders = runnerConfig.getReportProviders();
    	for (IReportProvider reportProvider : reportProviders) {
    	reportProvider.generateReport();
    	}
    	} 
    	catch (Throwable e) {    	
    	   e.printStackTrace();
    	  }
    	}

    /*
     * (non-Javadoc)
     * 
     * @see dl.automation.core.executor.Runner#initialize(java.util.List)
     */
    @Override
    public abstract void initialize(ITestApp testApp, IRunnerConfig runnerConfig);

}
