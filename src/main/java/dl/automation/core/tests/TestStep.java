/**
 *
 */
package dl.automation.core.tests;

import java.io.IOException;
import java.util.List;

import dl.automation.core.exceptions.AssertFailedException;
import dl.automation.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import dl.automation.core.actions.ActionExecutor;
import dl.automation.core.executor.IDLWebdriver;
import dl.automation.core.interfaces.ITestStep;
import dl.automation.core.interfaces.ITestStepExecutionContext;
import dl.automation.core.model.TaskModel;
import dl.automation.core.model.TaskStepModel;
import dl.automation.core.model.TestDataObjectModel;
import dl.automation.core.model.TestObjectModel;
import dl.automation.core.model.TestStepModel;
import dl.automation.core.utils.Log4jUtil;

public class TestStep extends Test implements ITestStep {
	static Logger log = Log4jUtil.loadLogger(TestStep.class);
	private TestStepModel tsModel;
	private boolean conditional = false;
	private int stepOnFalse;
	private int stepOnTrue;
	private int next;

	/**
	 *
	 */
	public TestStep(TestStepModel tsModel, ITestStepExecutionContext context) {
		super(context);
		this.tsModel = tsModel;
		this.getContext().getParentContext().getContextualData().put("Conditional", false);
		this.parseIfCondition();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see dl.automation.core.interfaces.ITest#execute()
	 */
	@Override
	public void execute(IDLWebdriver webDriver) throws Exception {
		switch (tsModel.getStepType()) {
		case Step:
			executeStep(webDriver);
			break;
		case Task:
			executeTask(webDriver);
			break;
		default:
			break;
		}
	}

	@Override
	public void execute(IDLWebdriver webDriver, int jumpTo) throws Exception {
		if (tsModel.getStepNumber() == jumpTo) {
			execute(webDriver);
			TestCase.jumpTo = -1;

		}
	}

	private void execute(IDLWebdriver webDriver, int stepNumber, TestObjectModel testObjectModel, String action,
			TestDataObjectModel testDataObjectModel) throws Exception {
		try {

			super.getEventEmitter().startTestStep(tsModel.getTestCase().getTitle(), stepNumber, action);
			if (testObjectModel != null) {
				List<WebElement> elements = webDriver.findElements(testObjectModel.getIdentifier(),
						testObjectModel.getIdentifierType());
				ActionExecutor.execute(webDriver, super.getContext(), elements, action, testDataObjectModel);
			} else {
				ActionExecutor.execute(webDriver, super.getContext(), null, action, testDataObjectModel);
			}
			super.getEventEmitter().endTestStep(tsModel.getTestCase().getTitle(), stepNumber, null, null);
			this.next = this.stepOnTrue;
		} catch (AssertFailedException ex) {
			try {
				String filePath = webDriver.screenShot((TakesScreenshot) webDriver.getWebDriver());
				this.next = this.stepOnFalse;
				String cond = tsModel.getCondition();
				if (cond == null || cond.equals("")) {
					super.getEventEmitter().endTestStep(tsModel.getTestCase().getTitle(), stepNumber, ex, filePath);
					throw ex;
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				String filePath = webDriver.screenShot((TakesScreenshot) webDriver.getWebDriver());
				super.getEventEmitter().endTestStep(tsModel.getTestCase().getTitle(), stepNumber, e, filePath);
				throw e;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void executeStep(IDLWebdriver webDriver) throws Exception {
		log.info("Executing - " + tsModel.getTestCase().getTitle() + " - " + tsModel.getStepNumber() + "  "
				+ tsModel.getDescription());
		TestObjectModel testObjectModel = tsModel.getTestObject();

		int stepNumber = tsModel.getStepNumber();
		String action = tsModel.getAction();
		TestDataObjectModel testDataObjectModel = tsModel.getDataObject();
		execute(webDriver, stepNumber, testObjectModel, action, testDataObjectModel);
		log.info("PASSED - " + tsModel.getTestCase().getTitle() + " - " + tsModel.getStepNumber());
	}

	private void executeTask(IDLWebdriver webDriver) throws Exception {
		TaskModel taskModel = tsModel.getTaskModel();
		List<TaskStepModel> steps = taskModel.getSteps();
		for (TaskStepModel taskStep : steps) {

			execute(webDriver, taskStep.getStepNumber(), taskStep.getTestObjectModel(), taskStep.getAction(),
					taskStep.getDataObject());
		}
	}

	private void parseIfCondition() {
		String strCondition = tsModel.getCondition();
		if (strCondition != null && !strCondition.equalsIgnoreCase("")) {
			this.conditional = true;
			List<String> cases = StringUtils.splitParams(":", strCondition);
			this.stepOnTrue = new Double(cases.get(1).trim()).intValue();
			this.stepOnFalse = new Double(cases.get(2).trim()).intValue();
		}
	}

	public boolean isConditional() {
		return conditional;
	}

	public int getNext() {
		return next;
	}

}
