/**
 * 
 */
package dl.automation.core.model;

import dl.automation.core.types.enums.Platform;

import java.util.ArrayList;
import java.util.List;


public class TestDataObjectModel {
	private String id ="";
	private String comments = "";
	private Platform platForm;
	private List<String> dataValues = new ArrayList<String>();
	private List<String> dataTypes =  new ArrayList<String>();

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
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the dataValue
	 */
	public List<String> getDataValues() {
		return dataValues;
	}
	
	// added by venkat
	public void GetDataSet(String dataValue) {
		this.dataValues.add(dataValue);
	}
	
	public void getDataSets(List<String> dataValues){
		this.dataValues.addAll(dataValues);
	}
	
	/**
	 * @param dataValue the dataValue to set
	 */
	public void setDataValue(String dataValue) {
		this.dataValues.add(dataValue);
	}
	
	
	public void setDataValues(List<String> dataValues){
		dataValues.forEach(value -> this.setDataValue(value));
	}
	/**
	 * @return the dataType
	 */
	public List<String> getDataTypes() {
		return dataTypes;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataTypes.add(dataType);
	}
	
	/**
	 * @param dataTypes
	 */
	public void setDataTypes(List<String> dataTypes){
		this.dataTypes.addAll(dataTypes);
	}

	public Platform getPlatForm() {
		return platForm;
	}

	public void setPlatForm(Platform platForm) {
		this.platForm = platForm;
	}
	
	
}
