/**
 * 
 */
package dl.automation.core.types.config;

import java.util.List;

import dl.automation.core.interfaces.IAppConfig;
import dl.automation.core.interfaces.ITestConfig;


class TestConfiguration implements ITestConfig {
	private List<IAppConfig> applications;

	/**
	 * @param applications the applications to set
	 */
	public void setApplications(List<IAppConfig> applications) {
		this.applications = applications;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.types.config.ITestConfig#getApplications()
	 */
	@Override
	public List<IAppConfig> getApplications() {
		return applications;
	}

}
