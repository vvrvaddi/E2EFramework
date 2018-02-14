/**
 * 
 */
package dl.automation.core.results;

import java.util.ArrayList;
import java.util.List;

import dl.automation.core.interfaces.IResultCollector;
import dl.automation.core.interfaces.IResultItem;
import dl.automation.core.interfaces.IResultProvider;


public class ResultsCollectorFactory {

	private static List<IResultCollector> resultCollectors = null;
	
	private static final synchronized List<IResultCollector> getResultCollectors(){
		if(resultCollectors == null){
			resultCollectors = new ArrayList<>();
		}
		return resultCollectors;
	}
	
	public static ResultsCollector collect(IResultProvider<IResultItem> resultProvider){
		ResultsCollector collector = new ResultsCollector(resultProvider);
		getResultCollectors().add(collector);
		return collector;
	}

}
