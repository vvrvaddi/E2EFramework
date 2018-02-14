/**
 *
 */
package dl.automation.core.providers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import dl.automation.core.interfaces.IScreenShotProvider;
import dl.automation.core.types.config.ConfigurationFactory;


public class ScreenShotProvider extends ReportProvider implements IScreenShotProvider {

    /**
     *
     */
    public ScreenShotProvider() {
        super();
    }

    public String saveScreenShot(File screenShot) {        
	        String dirpath=null;
		    File destination= null;
		    dirpath = ConfigurationFactory.getCurrentAppConfig().getResultFolder()+"\\ScreenShots\\";
		    String dirpath1 = FilenameUtils.separatorsToSystem(dirpath);
		     
		    Date date = new Date() ;
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss") ;
		    destination = new File(dirpath1+dateFormat.format(date)+".png");           
          
            try {
            FileUtils.moveFile(screenShot, destination);
                return destination.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
      
        

    /* (non-Javadoc)
     * @see dl.automation.core.providers.ReportProvider#generateReport()
     */
    @Override
    public void generateReport() throws FileNotFoundException, IOException {
        // TODO Auto-generated method stub

    }

}
