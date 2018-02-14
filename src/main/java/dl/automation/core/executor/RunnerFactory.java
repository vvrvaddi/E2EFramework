/**
 * 
 */
package dl.automation.core.executor;

import dl.automation.core.interfaces.IRunnerConfig;
import dl.automation.core.interfaces.ITestApp;
import dl.automation.core.types.enums.Runners;


public class RunnerFactory {
	public static Runner getRunner(Runners runnerType, ITestApp testApp, IRunnerConfig runnerConfig){
		BaseRunner runner = null;
		switch(runnerType){
		case Selenium:
			runner = new SeleniumRunner();
			runner.initialize(testApp, runnerConfig);
		case Appium:
			break;
		default:
			break;
		}
		return runner;		
	}
}
