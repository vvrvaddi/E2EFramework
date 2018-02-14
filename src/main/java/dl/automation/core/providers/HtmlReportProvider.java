/**
 *
 */
package dl.automation.core.providers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

import dl.automation.core.interfaces.IHtmlReportProvider;
import dl.automation.core.interfaces.IResultItem;
import dl.automation.core.interfaces.ITestStepResult;
import dl.automation.core.types.config.ConfigurationFactory;

public class HtmlReportProvider extends ReportProvider implements IHtmlReportProvider {
    ExtentReports reports;

    /**
     * This is the Html report provider class.
     */
    public HtmlReportProvider() {
        	super();        
	        String dirpath=null;	        
			dirpath = ConfigurationFactory.getCurrentAppConfig().getResultFolder()+"\\HtmlReport\\";
			String dirpath1 = FilenameUtils.separatorsToSystem(dirpath);
			 
			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss") ;
			String filePath = dirpath1+dateFormat.format(date)+".html";
       
	        ConfigurationFactory.getCurrentAppConfig().setHtmlResultsPath(filePath);
	        reports = new ExtentReports(filePath, true, NetworkMode.ONLINE);
    }
    /* (non-Javadoc)
     * @see dl.automation.core.providers.ReportProvider#generateReport()
     */
    @Override
    public void generateReport() throws FileNotFoundException, IOException {
        this.getResultCollector().getRestultSet()
                .forEach(item -> add(item));
        reports.close();
    }

    /**
     * @param item
     * @return
     */
    private Object add(IResultItem item) {
        ExtentTest test = reports
                .startTest( item.getTestCaseId() +  " - " +item.getTestCaseTitle())
           		.assignCategory(item.getSuite());
        
     
        item.getTestStepResults()
                .forEach(tsResult -> {
                    ExtentTest testStep = add(tsResult, test);
                   /* if(testStep.getRunStatus() == LogStatus.FAIL){
                        test.log(LogStatus.FAIL, item.getFailedStepNumber(), item.getError());
                    } */
                });    
        
        reports.endTest(test);      
        reports.flush();
        return test;
    }

    /**
     * @param tsResult
     * @param test
     * @return
     */
    private ExtentTest add(ITestStepResult tsResult, ExtentTest test) {
        ExtentTest testStep = reports.startTest(Integer.toString(tsResult.getTestStepNumber()));      
        testStep.setStartedTime(Date.from(tsResult.getStartTime().toInstant(ZoneOffset.UTC)));
        testStep.setEndedTime(Date.from(tsResult.getEndTime().toInstant(ZoneOffset.UTC)));
        if (tsResult.getError() != null) {
            testStep.log(LogStatus.FAIL, String.valueOf(tsResult.getTestStepNumber()), tsResult.getError());
        } else {
            testStep.log(LogStatus.PASS, String.valueOf(tsResult.getTestStepNumber()), tsResult.action());
        }
        test.appendChild(testStep);
        reports.endTest(testStep);
        return testStep;
    }

}
