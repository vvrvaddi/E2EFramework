/**
 *
 */
package dl.automation.core.model;

import dl.automation.core.types.enums.StepTypes;


public class TestStepModel {
    //Private fields
    private TestCaseModel testCaseModel = null;
    private String description = "";
    private int stepNumber = 0;
    private String page = "";
    private TestObjectModel testObjectModel = null;
    private TaskModel taskModel = null;
    private TestDataObjectModel dataObject = null;
    private String action = "";
    private String condition = "";
    private String comments = "";
    private StepTypes stepType;
    
    /**
	 * 
	 */
	public TestStepModel() {
		this.setStepType(StepTypes.Step);
	}

    // Getts/Setters

    /**
     * @return the testCaseModel
     */
    public TestCaseModel getTestCase() {
        return testCaseModel;
    }

    /**
     * @param testCaseModel the testCaseModel to set
     */
    public void setTestCase(TestCaseModel testCaseModel) {
        this.testCaseModel = testCaseModel;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the stepNumber
     */
    public int getStepNumber() {
        return stepNumber;
    }

    /**
     * @param stepNumber the stepNumber to set
     */
    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    /**
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return the testObjectModel
     */
    public TestObjectModel getTestObject() {
        return testObjectModel;
    }

    /**
     * @param testObjectModel the testObjectModel to set
     */
    public void setTestObject(TestObjectModel testObjectModel) {
        this.testObjectModel = testObjectModel;
    }

    /**
     * @return the dataObject
     */
    public TestDataObjectModel getDataObject() {
        return dataObject;
    }

    /**
     * @param dataObject the dataObject to set
     */
    public void setDataObject(TestDataObjectModel dataObject) {
        this.dataObject = dataObject;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return
     */
    public StepTypes getStepType() {
        return stepType;
    }

    /**
     * @param stepType
     */
    public void setStepType(StepTypes stepType) {
        this.stepType = stepType;
    }

    /**
     * @return
     */
    public TaskModel getTaskModel() {
        return taskModel;
    }

    /**
     * @param taskModel
     */
    public void setTaskModel(TaskModel taskModel) {
        this.taskModel = taskModel;
    }
}
