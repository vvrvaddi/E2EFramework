import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.esotericsoftware.yamlbeans.YamlException;

import dl.automation.core.context.AppExecutionContext;
import dl.automation.core.context.SuiteExectionContext;
import dl.automation.core.context.TestCaseExecutionContext;
import dl.automation.core.context.TestStepExecutionContext;
import dl.automation.core.executor.Runner;
import dl.automation.core.executor.RunnerConfig;
import dl.automation.core.executor.RunnerFactory;
import dl.automation.core.interfaces.IAppConfig;
import dl.automation.core.interfaces.IAppExecutionContext;
import dl.automation.core.interfaces.IRunnerConfig;
import dl.automation.core.interfaces.ISuiteConfig;
import dl.automation.core.interfaces.ISuiteExecutionContext;
import dl.automation.core.interfaces.ISuiteRunnerConfig;
import dl.automation.core.interfaces.ITestApp;
import dl.automation.core.interfaces.ITestCase;
import dl.automation.core.interfaces.ITestConfig;
import dl.automation.core.interfaces.ITestStep;
import dl.automation.core.interfaces.ITestSuite;
import dl.automation.core.loader.TestCaseLoaderFactory;
import dl.automation.core.model.TestCaseModel;
import dl.automation.core.model.TestStepModel;
import dl.automation.core.tests.TestApp;
import dl.automation.core.tests.TestCase;
import dl.automation.core.tests.TestStep;
import dl.automation.core.tests.TestSuite;
import dl.automation.core.types.config.ConfigurationFactory;
import dl.automation.core.types.config.SuiteRunnerConfig;
import dl.automation.core.types.enums.Platform;
import dl.automation.core.types.enums.Runners;
import dl.automation.core.utils.ExecutionEventsEmitter;
import dl.automation.core.utils.Log4jUtil;

public class Invoker {
	static Logger log = Log4jUtil.loadLogger(Invoker.class);
	static   ISuiteRunnerConfig suiteRunnerConfig = null;
	public static void invoke(String configFilePath){
		try {
			// log.info("Invoker class --> Invoke Method ");
			ITestConfig tconfig = ConfigurationFactory.getConfigurationFromFile(configFilePath);
			for (IAppConfig appCofnig : tconfig.getApplications()) {
				IAppExecutionContext appContext = new AppExecutionContext(null);
				ConfigurationFactory.setCurrentAppConfig(appCofnig);
				String paltformStr = appCofnig.getPlatform();
			
				if (paltformStr != null && paltformStr != "") {
					try {    
						
						List<ITestSuite> testSuites = new ArrayList<>();
						Platform platform = Platform.valueOf(appCofnig.getPlatform());
						HashMap<String, List<TestCaseModel>> testCase = new HashMap<String, List<TestCaseModel>>();						
						List<TestCaseModel> testCaseModels = TestCaseLoaderFactory.getTestCasesFromFile();          
						List<ISuiteConfig> runnableSuites = appCofnig.getSuites().stream().filter(s -> s.getRun())
								.collect(Collectors.toList());
						List<TestCaseModel> runnableTestCases = new ArrayList<TestCaseModel>();
						List<TestCaseModel> runnableTestCases1 = new ArrayList<TestCaseModel>(); 

						String suiteName = "";
						if (runnableSuites.size() > 0) {    

							for (ISuiteConfig suite : runnableSuites) {							
								suiteName = suite.getName();
								log.info("Suite Name: "+ suiteName);
								
								List<TestCaseModel> suiteTestCases = testCaseModels.stream()
										.filter(tc -> tc.getSuite().equalsIgnoreCase(suite.getName())).collect(Collectors.toList());
								if (suite.getModules() !=null && suite.getModules().size() >0 ) {																	
																
									suite.getModules().forEach(module ->{
										List<TestCaseModel> moduleTestCases = suiteTestCases.stream()
												.filter(mtc -> mtc.getModule().equals(module)).collect(Collectors.toList());	
																	
										runnableTestCases1
										.addAll(moduleTestCases.stream().filter(tc -> tc.getExecution().trim().toLowerCase().equalsIgnoreCase("yes")).collect(Collectors.toList()));
										
									});									
									testCase.put(suite.getName(), runnableTestCases1);										
									runnableTestCases.addAll((Collection<? extends TestCaseModel>) testCase.get(suite.getName()));				
									

								} 
								else {
									runnableTestCases.addAll(suiteTestCases.stream()
										.filter(tc -> tc.getExecution().trim().toLowerCase().equalsIgnoreCase("yes")).collect(Collectors.toList()));
								}
								
								
								suiteRunnerConfig = new SuiteRunnerConfig(suite.getBaseUrl(), suite.getBrowser(),suite.getBrowserDrivePath(), suite.getSeq());   
								runnableTestCases1.clear();								
								testSuites.add(createTestSuite(runnableTestCases, appContext, suiteRunnerConfig));								
								runnableTestCases.clear();								
							}							

							invokeRunner(createTestApp(testSuites, appContext), platform);
							
						}


					} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
						System.out.println(e.getMessage());}
				}                       

			}

		} catch (FileNotFoundException | YamlException e) {
			e.printStackTrace();
		}
	}
	public static void invokeRunner(ITestApp testApp, Platform platform) {
		ExecutionEventsEmitter eventEmitter = new ExecutionEventsEmitter();
		IRunnerConfig runnerConfig = new RunnerConfig(eventEmitter);
		testApp.register(eventEmitter);
		run(testApp,runnerConfig,platform);
	} 
	
	public static void run(ITestApp testApp,IRunnerConfig runnerConfig, Platform platform){ 
		switch (platform) {
		case Desktop:
			Runner runner = RunnerFactory.getRunner(Runners.Selenium, testApp, runnerConfig);
			runner.run();			
			break;

		default:
			break;
		}
	}
    
	private static ITestApp createTestApp(List<ITestSuite> suites, IAppExecutionContext appContext) {
		TestApp app = new TestApp(appContext, suites);
		return app;
	}
	
	

	private static ITestSuite createTestSuite(List<TestCaseModel> runnableTestCases,
			IAppExecutionContext appContext, ISuiteRunnerConfig suiteRunnerConfig) {
		//log.info("Creating Test Suite");
		ISuiteExecutionContext suiteContext = new SuiteExectionContext(appContext);
		List<ITestCase> tCases = new ArrayList<>();
		for (TestCaseModel tc : runnableTestCases) {
			TestCaseExecutionContext caseContext = new TestCaseExecutionContext(suiteContext);
			List<TestStepModel> testStepModels = tc.getSteps();
			Collections.sort(testStepModels, (a, b) -> a.getStepNumber() - b.getStepNumber());
			List<ITestStep> tSteps = new ArrayList<>();
			for (TestStepModel ts : testStepModels) {
				TestStepExecutionContext stepContext = new TestStepExecutionContext(caseContext);
				TestStep tStep = new TestStep(ts, stepContext);
				tSteps.add(tStep);
			}
			TestCase tCase = new TestCase(tc, caseContext, tSteps,suiteRunnerConfig);			
			tCases.add(tCase);
		}
		ITestSuite suite = new TestSuite(suiteContext, tCases, suiteRunnerConfig);

		return suite;
	}
}