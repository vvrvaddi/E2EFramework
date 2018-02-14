/**
 * 
 */
package dl.automation.core.providers;

import dl.automation.core.interfaces.*;
import dl.automation.core.utils.ExecutionEventsEmitter;


public class ProviderFactory {

	public static IResultProvider<IResultItem> getResultProvider(ExecutionEventsEmitter eventEmitter){
		return new ResultsProvider<IResultItem>(eventEmitter);
	}
	
	public static IExcelReportProvider getExcelReportProvider(IResultCollector collector){
		IExcelReportProvider excelReportProvider = new ExcelReportProvider();
		excelReportProvider.init(collector);
		return excelReportProvider;
	}	
		
	public static IHtmlReportProvider getHtmlReportProvider(IResultCollector collector){
		IHtmlReportProvider htmlReportProvider = new HtmlReportProvider();
		htmlReportProvider.init(collector);
		return htmlReportProvider;
	}

	public static IEmailReportProvider getEmailReportProvider(IResultCollector collector){
		IEmailReportProvider emailReportProvider = new EmailReportProvider();
		emailReportProvider.init(collector);
		return  emailReportProvider;
	}
	
	public static IScreenShotProvider getScreenShotProvider(){
		IScreenShotProvider screenShotProvider = new ScreenShotProvider();
		return screenShotProvider;
	}

}
