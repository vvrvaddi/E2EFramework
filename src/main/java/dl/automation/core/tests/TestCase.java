/**
 *
 */
package dl.automation.core.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import dl.automation.core.actions.ActionExecutor;
import dl.automation.core.exceptions.AssertFailedException;
import org.apache.log4j.Logger;
import dl.automation.core.executor.IDLWebdriver;
import dl.automation.core.interfaces.ISuiteRunnerConfig;
import dl.automation.core.interfaces.ITestCase;
import dl.automation.core.interfaces.ITestCaseExecutionContext;
import dl.automation.core.interfaces.ITestStep;
import dl.automation.core.model.TestCaseModel;

import dl.automation.core.utils.ExecutionEventsEmitter;
import dl.automation.core.utils.Log4jUtil;
import dl.automation.core.utils.StringUtils;

public class TestCase extends Test implements ITestCase {
	
	
	
    static Logger log = Log4jUtil.loadLogger(TestCase.class);
    static int jumpTo;       
    private TestCaseModel tcModel;
    private List<ITestStep> steps;      
    public ISuiteRunnerConfig suiteRunnerConfig = null;
    
    /**
     *
     */
    public TestCase(TestCaseModel tcModel, ITestCaseExecutionContext context, List<ITestStep> steps, ISuiteRunnerConfig suiteRunnerConfig) {
        super(context);
        this.tcModel = tcModel;
        this.steps = steps;
        this.suiteRunnerConfig =suiteRunnerConfig;
    }
    
    
  
    /*
     * (non-Javadoc)	
     *
     * @see dl.automation.core.interfaces.ITest#execute()
     */
    public ISuiteRunnerConfig getSuiteRunnerConfig() {
        return suiteRunnerConfig;
    }

    /**
     * @param suiteRunnerConfig
     *            the suiteRunnerConfig to set
     */
    public void setSuiteRunnerConfig(ISuiteRunnerConfig suiteRunnerConfig) {
        this.suiteRunnerConfig = suiteRunnerConfig;
    }
    
   
	@Override
    public void execute(IDLWebdriver webDriver) throws Exception {	
		  String source= tcModel.getSource(); 
		  List<String> val1=StringUtils.splitDataSets(source);	      
	      ArrayList<String> val =new ArrayList<String>();
	             
	      for(String s :val1){
	        	  val.add(s);
	        	  if(Integer.parseInt(s)>1)
	      	       {  
	      	       Thread.sleep(3000);	
	      	       String url= suiteRunnerConfig.getBaseUrl();
	      	       webDriver.get(url);	      	                
	      	    }
	   
        super.getEventEmitter().startTestCase(tcModel.getId(), tcModel.getTitle(), tcModel.getModule(), tcModel.getSuite(),s,tcModel.getPriority());
                
        Throwable ex = null; 
        jumpTo = -1;
        
        for (ITestStep step : steps) {        	
        	ActionExecutor.dataset=Integer.parseInt(s);        	
              
            try {         
                if (jumpTo > -1) {
                    step.execute(webDriver, jumpTo);
                    //jumpTo = -1;
                } else {
                    step.execute(webDriver);
                    if (step.isConditional()){
                        jumpTo = step.getNext();
                    }
                }
            } catch (AssertFailedException e) {
                e.printStackTrace();
                log.info("Assert FAILED - " + tcModel.getId());
                if(jumpTo > -1){
                	jumpTo = -1;
                }
                if (step.isConditional()) {
                    jumpTo = step.getNext();
                    log.info("Next step will be " + jumpTo);
                } else {
                    ex = e;
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                ex = e;
                log.info("FAILED - " + tcModel.getId());
                break;
            }
           
        }  
       
        super.getEventEmitter().endTestTestCase(tcModel.getTitle(),ex, null);
    
    }
    
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * dl.automation.core.interfaces.ITest#register(dl.automation.core.utils.
     * ExecutionEventsEmitter)
     */
    @Override
    public void register(ExecutionEventsEmitter eventEmitter) {
        super.register(eventEmitter);
        this.steps.forEach(ts -> ts.register(eventEmitter));
    }

    public TestCaseModel getTestCaseModel(){
        return this.tcModel;
    }

}
