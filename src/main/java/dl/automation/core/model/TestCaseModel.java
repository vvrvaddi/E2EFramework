package dl.automation.core.model;

import java.util.ArrayList;
import java.util.List;

import dl.automation.core.interfaces.ITestStep;


public class TestCaseModel {
	// Private fields
	private String id = "";
	private String title = "";
	private String source = "";
	private String comments = "";
	private String module = "";
	private String suite = "";
	private String suiteType = "";
	private String priority = "";
	private String execution = "";
	private String requirement = "";
	private List<TestStepModel> steps = new ArrayList<TestStepModel>();
	private List<String> dataSets=new ArrayList<>();
	
	
	/**
	 * @return the steps
	 */
	public List<TestStepModel> getSteps() {
		return steps;
	}
	
	// Getters/Setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getSuite() {
		return suite;
	}
	public void setSuite(String suite) {
		this.suite = suite;
	}
	public String getSuiteType() {
		return suiteType;
	}
	public void setSuiteType(String suiteType) {
		this.suiteType = suiteType;
	}
	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/**
	 * @return the execution
	 */
	public String getExecution() {
		return execution;
	}
	
	/**
	 * @param execution the execution to set
	 */
	public void setExecution(String execution) {
		this.execution = execution;
	}
	/**
	 * @return the requirement
	 */
	public String getRequirement() {
		return requirement;
	}
	/**
	 * @param requirement the requirement to set
	 */
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	
	public void addTestStep(TestStepModel ts){
		this.steps.add(ts);
	}
	
	public void addTestSteps(List<TestStepModel> testStepModels){
		this.steps.addAll(testStepModels);
	}
	
	public List<String> getdataSetList() {
		return dataSets;
	}
	
	public void setDataSets(List<String> dataSets) {
		this.dataSets = dataSets;
	}
	
	 
}
