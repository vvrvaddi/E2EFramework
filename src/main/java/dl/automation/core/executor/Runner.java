/**
 * 
 */
package dl.automation.core.executor;

import dl.automation.core.interfaces.IRunnerConfig;
import dl.automation.core.interfaces.ITestApp;


public interface Runner extends Runnable{
	public void initialize(ITestApp testApp, IRunnerConfig runnerConfig);
}
