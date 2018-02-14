/**
 * 
 */
package dl.automation.core.results;

import java.time.Duration;
import java.time.LocalDateTime;

import dl.automation.core.interfaces.ITestStepResult;
import dl.automation.core.utils.DateTimeUtils;


public class TestStepResult implements ITestStepResult {

	private int testStepNumber;
	private String action;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Throwable error;
	private String evidence = "";
	private int FailedStepNumber;
	
	
	/**
	 * 
	 */
	public TestStepResult(int stepNumber, String action) {
		this.testStepNumber = stepNumber;
		this.action = action;
		this.startTime = LocalDateTime.now();
	} 
	
		
	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestStepResult#getTestStepNumber()
	 */
	@Override
	public int getTestStepNumber() {
	
		return testStepNumber;
		
	}
	
	
	public int getFailedStepNumber() {
	
		return FailedStepNumber;
		
	}
		

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestStepResult#getStartTime()
	 */
	@Override
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestStepResult#getEndTime()
	 */
	@Override
	public LocalDateTime getEndTime() {
		return endTime;
		
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestStepResult#setEndTime()
	 */
	@Override
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestStepResult#executionTime()
	 */
	@Override
	public Duration getExecutionTime() {
		return DateTimeUtils.getDuration(this.startTime, this.endTime);
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestStepResult#getEvidence()
	 */
	@Override
	public String getEvidence() {
		return this.evidence;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestStepResult#setEvidence()
	 */
	@Override
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	

	public String action() {	
		return this.action;		
	}


	public void setAction(String action) {
		this.action = action;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestStepResult#setError(java.lang.Throwable)
	 */
	@Override
	public void setError(Throwable error) {
		this.error = error;
		
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestStepResult#getError()
	 */
	@Override
	public Throwable getError() {
		return this.error;
	}

}
