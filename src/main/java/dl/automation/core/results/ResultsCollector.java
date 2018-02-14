/**
 * 
 */
package dl.automation.core.results;

import dl.automation.core.interfaces.IResultCollector;
import dl.automation.core.interfaces.IResultItem;
import dl.automation.core.interfaces.IResultProvider;
import java.util.ArrayList;
import java.util.List;


public class ResultsCollector implements IResultCollector {

    private IResultProvider<IResultItem> resultProvider;
    private ArrayList<IResultItem> resultSet;

    /**
     * 
     */
    public ResultsCollector(IResultProvider<IResultItem> resultProvider) {
        this.resultProvider = resultProvider;
        this.resultSet = new ArrayList<>();
        this.resultProvider.getResults().subscribe(item -> {
        	IResultItem rsItem = null;
            boolean found = false;
            for (IResultItem rs : this.resultSet) {
            	  if (item.getTestCaseTitle().equalsIgnoreCase(rs.getTestCaseTitle())&&item.getDataSet().equalsIgnoreCase(rs.getDataSet())) {                   
                	found = true;
                    rsItem = rs;
                    break;
                }
            }
            if(found){
                this.resultSet.remove(rsItem);
            } 
            resultSet.add(item);
        });
    }

    /* (non-Javadoc)
     * @see dl.automation.core.interfaces.IResultCollector#getResultProvider()
     */
    @Override
    public IResultProvider<IResultItem> getResultProvider() {
        return resultProvider;
    }

    /* (non-Javadoc)
     * @see dl.automation.core.interfaces.IResultCollector#getRestultSet()
     */
    @Override
    public List<IResultItem> getRestultSet() {
        return resultSet;
    }

}