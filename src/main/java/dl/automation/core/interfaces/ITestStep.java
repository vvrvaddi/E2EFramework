/**
 * 
 */
package dl.automation.core.interfaces;

import dl.automation.core.executor.IDLWebdriver;


public interface ITestStep extends ITest{

    boolean isConditional();

    int getNext();

    void execute(IDLWebdriver webDriver, int jumpTo) throws Exception;
  
}
