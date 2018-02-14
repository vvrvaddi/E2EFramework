/**
 * 
 */
package dl.automation.core.results;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import dl.automation.core.interfaces.IResultItem;
import dl.automation.core.interfaces.ITestCaseResult;
import dl.automation.core.interfaces.ITestStepResult;
import dl.automation.core.types.enums.ResultStatus;


public class ResultItem implements IResultItem {
	
	private ITestCaseResult testCaseResult;

	
	/**
	 * @param testCaseId
	 * @param testCaseTitle
	 */
	public ResultItem(String testCaseId, String testCaseTitle, String moduleName, String suiteName,String dataSet,String priority) {
		//this();
		this.testCaseResult = new TestCaseResult(testCaseId, testCaseTitle, moduleName, suiteName,dataSet,priority);
	} 
	
	
	/**
	 * 
	 */
	public ResultItem() {
		super();
	}

	/**
	 * @return the testCaseId
	 */
	public String getTestCaseId() {
		return testCaseResult.getTestCaseId();
	}
	
	/**
	 * @return the testCaseTitle
	 */
	public String getTestCaseTitle() {
		return testCaseResult.getTestCaseTitle();
	}

	/**
	 * @return the module name
	 */
	@Override
	public String getModuleName() {
		return testCaseResult.getModuleName();
	}

	/**
	 * @return the errorMessage
	 */
	public Throwable getError() {
		return testCaseResult.getError();
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setError(Throwable error) {
		this.testCaseResult.setError(error);
	}

	/**
	 * @return the startTime
	 */
	public LocalDateTime getStartTime() {
		return testCaseResult.getStartTime();
	}
	
	/**
	 * @return the endTime
	 */
	public LocalDateTime getEndTime() {
		return testCaseResult.getEndTime();
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.testCaseResult.setEndTime(endTime);
	}


	/**
	 * @return the runningTime
	 */
	public Duration getExecutionTime() {
		return this.testCaseResult.getExecutionTime();
	}
	/**
	 * @return the evidence
	 */
	public String getEvidence() {
		return this.testCaseResult.getEvidence();
	}

	/**
	 * @param evidence the evidence to set
	 */
	public void setEvidence(String evidence) {
		this.testCaseResult.setEvidence(evidence);
	}
	/**
	 * @return the status
	 */
	public ResultStatus getStatus() {
		return this.testCaseResult.getStatus();
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ResultStatus status) {
		this.testCaseResult.setStatus(status);
	}

	
	@Override
	public String getSuite() {
		return this.testCaseResult.getSuiteName();
	}

	@Override
	public void setSuite(String suiteName) {
		this.testCaseResult.setSuiteName(suiteName);
		
	}

    /**
     * @param step
     */
    @Override
    public void setFailedStepNumber(int step) {
        this.testCaseResult.setFailedStepNumber(step);
    }

    /**
     * @param DataSet
     */
    @Override
    public void setDataSet(String dataSet) {
        this.testCaseResult.setDataSet(dataSet);
    }

    /**
     * @param DataSet
     */
    @Override
    public void setPriority(String priority) {
        this.testCaseResult.setPriority(priority);
    }
    /**
     * @return
     */
    @Override
    public String getFailedStepNumber() {
        return this.testCaseResult.getFailedStepNumber();
    }
    /**
     * @return 
     */
    @Override
    public String getDataSet() {
        return this.testCaseResult.getDataSet();
    }
    /**
     * @return 
     */
    @Override
    public String getPriority() {
        return this.testCaseResult.getPriority();
    }
    /* (non-Javadoc)
     * @see dl.automation.core.interfaces.IResultItem#getTestStepResults()
     */
	@Override
	public List<ITestStepResult> getTestStepResults() {
		return this.testCaseResult.getTestStepResults();
	}

    
    public int getIterationNumber(){
        return this.testCaseResult.getIterationNumber();
    }
    public void setIterationNumber(int iterationNumber){
        this.testCaseResult.setIterationNumber(iterationNumber);
    }

	

	

}
