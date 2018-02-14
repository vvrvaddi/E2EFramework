/**
 * 
 */
package dl.automation.core.interfaces;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import dl.automation.core.types.enums.ResultStatus;


public interface IResultItem {
	/**
	 * @return the testCaseId
	 */
	public String getTestCaseId();
	
	/**
	 * @return the testCaseTitle
	 */
	public String getTestCaseTitle();

    /**
     * @return the module name
     */
    public String getModuleName();

    /**
     * @return the DataSet
     */
    public String getDataSet();
	/**
	 * @return the errorMessage
	 */
	public Throwable getError();
	/**
	 * @param error the errorMessage to set
	 */
	public void setError(Throwable error);

	/**
	 * @return the startTime
	 */
	public LocalDateTime getStartTime();
	/**
	 * @return the endTime
	 */
	public LocalDateTime getEndTime();

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalDateTime endTime);

	/**
	 * @return the runningTime
	 */
	public Duration getExecutionTime();
	/**
	 * @return the evidence
	 */
	public String getEvidence();

	/**
	 * @param evidence the evidence to set
	 */
	public void setEvidence(String evidence);
	
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
	public String getSuite();
	
	/**
	 * @param suiteName
	 */
	public void setSuite(String suiteName);

    /**
     *  @param dataset the dataset to set
     */
    public void setDataSet(String dataSet);
 
    /**
     * @param step
     */
    public void setFailedStepNumber(int step);

    /**
     * @return
     */
    public String getFailedStepNumber();

	/**
	 * @return testStep results
	 */
	public List<ITestStepResult> getTestStepResults();

    public int getIterationNumber();
    public void setIterationNumber(int iterationNumber);
    /**
	 * @return testcase prority 
	 */
    public void setPriority(String priority);
    /**
	 * @return testStep results
	 */
    public String getPriority();
}