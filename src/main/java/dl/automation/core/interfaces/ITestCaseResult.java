/**
 * 
 */
package dl.automation.core.interfaces;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import dl.automation.core.types.enums.ResultStatus;


public interface ITestCaseResult {

	/**
	 * @return the errorMessage
	 */
	public Throwable getError();
	/**
	 * @param error the errorMessage to set
	 */
	public void setError(Throwable error);
	/**
	 * @return the endTime
	 */
	public LocalDateTime getEndTime();
	/**
	 * @param dataset the dataset to set
	 */
	public void setDataSet(String dataSet);
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalDateTime endTime);
	/**
	 * @return the evidence
	 */
	public String getEvidence();
	/**
	 * @return the DataSet
	 */
	public String getDataSet();
	/**
	 * @return the Priority
	 */
	public String getPriority();
	/**
	 * @param evidence the evidence to set
	 */
	public void setEvidence(String evidence);
	/**
	 * @return the startTime
	 */
	public LocalDateTime getStartTime();
	/**
	 * @return testCaseId the testCaseId to get
	 */
	public String getTestCaseId();
	/**
	 * @return testCaseTitle the testCaseTitle to get
	 */
	public String getTestCaseTitle();

	/**
	 * @return module name of the testcase
	 */
	public String getModuleName();
	
	/**
	 * @return the exectionTime
	 */
	public Duration getExecutionTime();

	
	/**
	 * @return
	 */
	public List<ITestStepResult> getTestStepResults();
	
	/**
	 * @return the status
	 */
	public ResultStatus getStatus();
	/**
	 * @param status the status to set
	 */
	public void setStatus(ResultStatus status);
	
	/**
	 * @return suite name
	 */
	public String getSuiteName();
	
	/**
	 * @param suiteName
	 */
	public void setSuiteName(String suiteName);

    /**
     * @param step
     */
    public void setFailedStepNumber(int step);

    /**
     * @return
     */
    public String getFailedStepNumber();

    
    public int getIterationNumber();
    public void setIterationNumber(int iterationNumber);
    public void setPriority(String priority);
	
}
