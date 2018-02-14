/**
 * 
 */
package dl.automation.core.interfaces;
import dl.automation.core.executor.IDLWebdriver;
import java.util.List;
import java.util.Map;


public interface ITestApp extends ITest{

    void execute(IDLWebdriver webDriver, List<String> testCaseNumbers,Map<String,List<String>> failedcasedatasets) throws Exception;
}
