/**
 * 
 */
package dl.automation.core.utils;

import java.time.LocalDateTime;

import dl.automation.core.interfaces.IResultItem;
import dl.automation.core.interfaces.ITestStepResult;
import dl.automation.core.results.ResultItem;
import dl.automation.core.results.TestStepResult;
import dl.automation.core.types.enums.ResultStatus;
import rx.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExecutionEventsEmitter {
	private List<IResultItem> resultItems;
	private PublishSubject<IResultItem> pulishResult;

	public ExecutionEventsEmitter() {
		this.pulishResult = PublishSubject.create();
		resultItems = new ArrayList<>();
	}

	private IResultItem getCurrentTestCase(String testCaseTitle){
		List<IResultItem> matched = resultItems.stream().filter(r -> r.getTestCaseTitle().equalsIgnoreCase(testCaseTitle)).collect(Collectors.toList());
		if(matched != null && matched.size() == 1){
			return matched.get(0);
		}else{
			return null;
		}
	}

	private ITestStepResult getCurrentTestStep(IResultItem current, int stepNumber){
		if(current != null){
			List<ITestStepResult> steps = current.getTestStepResults().stream().filter(t -> t.getTestStepNumber() == stepNumber ).collect(Collectors.toList());
			if(steps != null && steps.size() == 1){
				return steps.get(0);
			}
		}
		return null;
	}

	public void startTestCase(String testCaseId, String testCaseTitle, String moduleName, String suiteName,String dataSet,String priority) {
		resultItems.add(new ResultItem(testCaseId, testCaseTitle, moduleName, suiteName,dataSet,priority));
	}

	public void endTestTestCase(String testCaseId, Throwable error, String evidence) {
		IResultItem current = this.getCurrentTestCase(testCaseId);
		if(current != null){			
			Boolean isStepFailed = current.getTestStepResults().stream().anyMatch(sr -> sr.getError() != null);
			if (isStepFailed) {
				ITestStepResult sResult = current.getTestStepResults().stream().filter(sr -> sr.getError() != null)
						.findFirst().get();
				error = sResult.getError();
				evidence = sResult.getEvidence();
			}
			if (error != null) {
				current.setStatus(ResultStatus.Fail);
			} else {
				current.setStatus(ResultStatus.Pass);
			}
			current.setError(error);
			current.setEvidence(evidence);
			current.setEndTime(LocalDateTime.now());
			pulishResult.onNext(current);
		}
		
	}

	public void startTestStep(String testCaseTitle, int stepNumber,String action) {
		IResultItem current = this.getCurrentTestCase(testCaseTitle);
		if(current != null){
			current.getTestStepResults().add(new TestStepResult(stepNumber,action));
		}
	}

	public void endTestStep(String testCaseTitle,  int stepNumber, Throwable error, String evidence) {
		
		IResultItem currentTestCase = this.getCurrentTestCase(testCaseTitle);
		if(currentTestCase != null){
			ITestStepResult currentTestStep = getCurrentTestStep(currentTestCase, stepNumber);
			if(currentTestStep != null){
				currentTestStep.setError(error);
				currentTestStep.setEndTime(LocalDateTime.now());
				currentTestStep.setEvidence(evidence);
				if (error != null) {
					currentTestCase.setFailedStepNumber(currentTestStep.getTestStepNumber());
				}
			}
		}
	}

	/**
	 * @return the pulishResult
	 */
	public PublishSubject<IResultItem> getPulishResult() {
		return pulishResult;
	}
}
