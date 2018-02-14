/**
 * 
 */
package dl.automation.core.model;

import dl.automation.core.types.enums.IdentifierType;
import dl.automation.core.types.enums.Platform;


public class TestObjectModel {
	private String id="";
	private String identifier ="";
	private IdentifierType identifierType;
	private String comments = "";
	private String page = "";
	private Platform platForm;
	
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
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	/**
	 * @return the identifierType
	 */
	public IdentifierType getIdentifierType() {
		return identifierType;
	}
	/**
	 * @param identifierType the identifierType to set
	 */
	public void setIdentifierType(IdentifierType identifierType) {
		this.identifierType = identifierType;
	}

    public Platform getPlatForm() {
        return platForm;
    }

    public void setPlatForm(Platform platForm) {
        this.platForm = platForm;
    }
}
