/**
 * 
 */
package dl.automation.core.model;


public class TaskStepModel {
	private String taskId="";
	private String description = "";
	private int stepNumber = 0;
	private TestObjectModel testObjectModel = null;
	private TestDataObjectModel dataObject = null;
	private String action = "";
	private String expression = "";
	private String comments="";	
	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	 * @return the testObjectModel
	 */
	public TestObjectModel getTestObjectModel() {
		return testObjectModel;
	}
	/**
	 * @param testObjectModel the testObjectModel to set
	 */
	public void setTestObjectModel(TestObjectModel testObjectModel) {
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
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
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

}
