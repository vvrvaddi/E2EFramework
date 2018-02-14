/**
 * 
 */
package dl.automation.core.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dl.automation.core.executor.IDLWebdriver;
import dl.automation.core.interfaces.IAppExecutionContext;
import dl.automation.core.interfaces.ITestApp;
import dl.automation.core.interfaces.ITestSuite;
import dl.automation.core.utils.ExecutionEventsEmitter;
import sun.util.logging.resources.logging;

public class TestApp extends Test implements ITestApp {

	private List<ITestSuite> suites= null;

	/**
	 * 
	 */
	public TestApp(IAppExecutionContext context, List<ITestSuite> suites) {
		super(context);
		this.suites = suites;
	}
	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITest#execute()
	 */
	@Override
	public void execute(IDLWebdriver webDriver) throws Exception {
		HashMap<Integer, List<ITestSuite>> parellalSuties = new HashMap<Integer, List<ITestSuite>>();
		for(ITestSuite suite : suites){
			Integer seq = suite.getSuiteRunnerConfig().getSeq();
			if(parellalSuties.containsKey(seq)){
				//System.out.println("inParallel --" +seq);
				parellalSuties.get(seq).add(suite);
			}else {
				List<ITestSuite> psuties = new ArrayList<>();
				psuties.add(suite);
				parellalSuties.put(seq, psuties);
				//System.out.println("inSequential --" +seq);
			}
		 }
			
			for(List<ITestSuite> psuites : parellalSuties.values()){
			
				psuites.stream().parallel().forEach((suite) -> {
					try {
						//System.out.println("suite name --"+ suite.getSuiteName());
						suite.execute(webDriver);		
									
					}
					catch (Exception e1){
						e1.printStackTrace();
					}
				});
		}
	}

    public void execute(IDLWebdriver webDriver, List<String> testCaseNumbers,Map<String,List<String>> failedcasedatasets) throws Exception{
    	HashMap<Integer, List<ITestSuite>> parellalSuties = new HashMap<Integer, List<ITestSuite>>();
		for(ITestSuite suite : suites){
			Integer seq = suite.getSuiteRunnerConfig().getSeq();
			if(parellalSuties.containsKey(seq)){
				
				parellalSuties.get(seq).add(suite);
			}else {
				List<ITestSuite> psuties = new ArrayList<>();
				psuties.add(suite);
				parellalSuties.put(seq, psuties);
			}
		 }
			for(List<ITestSuite> psuites : parellalSuties.values()){
				psuites.stream().parallel().forEach((suite) -> {
					try {
						suite.execute(webDriver,testCaseNumbers, failedcasedatasets);
					}
					catch (Exception e1){
						e1.printStackTrace();
					}
				});
		}
    }

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITest#register(dl.automation.core.utils.ExecutionEventsEmitter)
	 */
	@Override
	public void register(ExecutionEventsEmitter eventEmitter) {
		super.register(eventEmitter);
		this.suites.forEach(suite -> suite.register(eventEmitter));
	}

}
