/**
 * 
 */
package dl.automation.core.results;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

import dl.automation.core.interfaces.ITestCaseResult;
import dl.automation.core.interfaces.ITestStepResult;
import dl.automation.core.types.enums.ResultStatus;
import dl.automation.core.utils.DateTimeUtils;

public class TestCaseResult implements ITestCaseResult {
	private String testCaseId;
	private String testCaseTitle;
	private String moduleName;
	private String priority;
	private ResultStatus status;
	private Throwable error;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String evidence;
	private String dataSet;
	private String suiteName;
    private String failedStepNumber = null;
	private List<ITestStepResult> stepResults;

	public TestCaseResult(String testCaseId, String testCaseTitle, String moduleName, String suiteName,String dataSet,String priority) {
		this.testCaseId = testCaseId;
		this.testCaseTitle = testCaseTitle;
		this.moduleName = moduleName;
        this.suiteName = suiteName;
        this.dataSet = dataSet;
        this.priority = priority;
		this.startTime = LocalDateTime.now();
		stepResults = new ArrayList<ITestStepResult>();
	} 
	
	

	/**
	 * @return the errorMessage
	 */
	public Throwable getError() {
		return error;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setError(Throwable error) {
		this.error = error;
	}

	/**
	 * @return the endTime
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the evidence
	 */
	public String getEvidence() {
		evidence =FilenameUtils.separatorsToSystem(evidence);
		return evidence;
	}

	/**
	 * @param evidence
	 *            the evidence to set
	 */
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	/**
	 * @return the startTime
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestCaseResult#getTestCaseId()
	 */
	@Override
	public String getTestCaseId() {
		return this.testCaseId;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestCaseResult#getTestCaseTitle()
	 */
	@Override
	public String getTestCaseTitle() {
		return this.testCaseTitle;
	}

	/**
	 * @return module name of the testcase
	 */
	@Override
	public String getModuleName() {
		return this.moduleName;
	}
	/**
	 * @return Priority  of the testcase
	 */
	@Override
	public String getPriority() {
		return this.priority;
	}
	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestCaseResult#getExecutionTime()
	 */
	@Override
	public Duration getExecutionTime() {
		return DateTimeUtils.getDuration(startTime, endTime);
	}

    /* (non-Javadoc)
     * @see dl.automation.core.interfaces.ITestCaseResult#getTestStepResults()
     */
	@Override
	public List<ITestStepResult> getTestStepResults() {
		//return this.stepResults.stream().map(tsr -> (TestStepResult)tsr).collect(Collectors.toList());
		return this.stepResults;
	}

	/**
	 * @return the status
	 */
	public ResultStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ResultStatus status) {
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestCaseResult#getSuiteName()
	 */
	@Override
	public String getSuiteName() {
		return suiteName;
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestCaseResult#setSuiteName(java.lang.String)
	 */
	@Override
	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
		
	}

    /**
     * @param step
     */
    @Override
    public void setFailedStepNumber(int step) {
        this.failedStepNumber = String.valueOf(step);
    }

    /**
     * @return
     */
    @Override
    public String getFailedStepNumber() {
        return this.failedStepNumber;
    }

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestCaseResult#setDataset(java.lang.String)
	 */
	@Override
	public void setDataSet(String dataSet) {
		// TODO Auto-generated method stub
		this.dataSet = String.valueOf(dataSet);
		
	}
	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestCaseResult#setPriority(java.lang.String)
	 */
	@Override
	public void setPriority(String priority) {
		// TODO Auto-generated method stub
		this.priority = String.valueOf(priority);
		
	}
	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestCaseResult#getDataSet()
	 */
	@Override
	public String getDataSet() {
		// TODO Auto-generated method stub
		  return this.dataSet;
	}

	@Override
	public int getIterationNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setIterationNumber(int iterationNumber) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see dl.automation.core.interfaces.ITestCaseResult#setDataSet(java.lang.String)
	 */
	
}
