/**
 * 
 */
package dl.automation.core.interfaces;

import java.time.Duration;
import java.time.LocalDateTime;


public interface ITestStepResult {

	public int getTestStepNumber();
	public LocalDateTime getStartTime();
	public LocalDateTime getEndTime();
	public void setEndTime(LocalDateTime endTime);
	public Duration getExecutionTime();
	public void setError(Throwable error);
	public Throwable getError();
	public String getEvidence();
	public void setEvidence(String evidence);
	public String action();
	public void setAction(String action);
	public int getFailedStepNumber();

}

