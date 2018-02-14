package dl.automation.core.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jUtil {

	static Logger log;
	static String filePath=null;
	
	public static void  GetOS() {		
		  filePath = System.getProperty("user.dir") + "\\log4j.properties";
		  String dirpath = FilenameUtils.separatorsToSystem(filePath);
		  PropertyConfigurator.configure(dirpath);
		}      
  	

	public static Logger loadLogger(Class<?> className) {
		log = Logger.getLogger(className);
		GetOS();
		return log;
	}

}