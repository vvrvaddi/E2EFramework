/**
 * 
 */
package dl.automation.core.loader;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import dl.automation.core.model.TestCaseModel;

public interface TestCaseLoader {
	public List<TestCaseModel> load(String filePathOrConnectionString) throws EncryptedDocumentException, InvalidFormatException, IOException;

}
