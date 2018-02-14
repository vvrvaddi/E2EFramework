/**
 * 
 */
package dl.automation.core.executor;

import org.apache.log4j.Logger;

import dl.automation.core.interfaces.IRunnerConfig;
import dl.automation.core.interfaces.ITestApp;
import dl.automation.core.utils.Log4jUtil;


class SeleniumRunner extends BaseRunner {

	static Logger log = Log4jUtil.loadLogger(SeleniumRunner.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see dl.automation.core.executor.BaseRunner#run()
	 */
	@Override
	public void run() {
		super.run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dl.automation.core.executor.BaseRunner#initialize(java.util.List)
	 */
	@Override
	public void initialize(ITestApp testApp, IRunnerConfig runnerConfig) {
		log.info("Initialize Selenium Runner");
		super.setTest(testApp);
		super.setRunnerConfig(runnerConfig);
	}
}
