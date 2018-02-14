/**
 * 
 */
package dl.automation.core.types.config;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import dl.automation.core.interfaces.IAppConfig;
import dl.automation.core.interfaces.ITestConfig;
import dl.automation.core.utils.Log4jUtil;

public class ConfigurationFactory {
	static Logger log = Log4jUtil.loadLogger(ConfigurationFactory.class);
	private static IAppConfig currentAppConfig = null;

	/**
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws YamlException
	 */
	public static ITestConfig getConfigurationFromFile(String filePath) throws FileNotFoundException, YamlException {
		String fileType = FilenameUtils.getExtension(filePath).toLowerCase();
		switch (fileType) {
		case "yaml":
			return getConfigurationFromYaml(filePath);
		default:
			return null;
		}
	}
	

	/**
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws YamlException
	 */
	@SuppressWarnings("rawtypes")
	private static ITestConfig getConfigurationFromYaml(String filePath) throws FileNotFoundException, YamlException {
		YamlReader reader = new YamlReader(new FileReader(filePath));
		log.info("Reading YAML file properties under ConfigurationFactory");
		
		String dirPath = System.getProperty("user.dir");
		
		while (true) {
			Map applications = (Map) reader.read();
			if (applications != null) {
				TestConfiguration testConfiguration = new TestConfiguration();
				testConfiguration.setApplications(new ArrayList<IAppConfig>());
				Iterator appIterator = applications.entrySet().iterator();
				while (appIterator.hasNext()) {
					Entry app = (Entry) appIterator.next();
					ArrayList appConfigs = (ArrayList) app.getValue();
					Iterator appConfigsIterator = appConfigs.iterator();
					while (appConfigsIterator.hasNext()) {
						HashMap appConfigMap = (HashMap) appConfigsIterator.next();
						Iterator appConfigIterator = appConfigMap.entrySet().iterator();
						while (appConfigIterator.hasNext()) {
							Entry appConfigEntry = (Entry) appConfigIterator.next();
						    HashMap appConfig = (HashMap) appConfigEntry.getValue();
						    Application appConfigObj = new Application();
						    appConfigObj.setSuites(new ArrayList<>()); 	
							Object value = appConfig.get("baseUrl");
							appConfigObj.setBaseUrl(value == null ? "" : value.toString());
							value = appConfig.get("name");
							appConfigObj.setName(value == null ? "" : value.toString());
							value = appConfig.get("environment");
							appConfigObj.setEnvironment(value == null ? "": value.toString());
							
							value = appConfig.get("inputFolder");
							value = dirPath + (String)value;
							String inputfolder=FilenameUtils.separatorsToSystem((String) value);
							appConfigObj.setInputFolderN(inputfolder == null ? "" : inputfolder.toString());
							
							value = appConfig.get("uploadsFolder");
							value = dirPath + (String)value;
							String uploadsFolder=FilenameUtils.separatorsToSystem((String) value);
							appConfigObj.setUploadsFolder(uploadsFolder == null ? "" : uploadsFolder.toString());

							value = appConfig.get("resultFolder");
							value = dirPath + (String)value;
							String resfolder=FilenameUtils.separatorsToSystem((String) value);
							appConfigObj.setResultFolder(resfolder == null ? "" : resfolder.toString());							
							
							value = appConfig.get("platform");
							appConfigObj.setPlatform(value == null ? "" : value.toString());
							
						 	value = appConfig.get("failedIterations");
                            appConfigObj.setIterations(value == null ? 0 : Integer.parseInt(value.toString())); 
      
                            value = appConfig.get("dbURL");
							appConfigObj.setDBUrl(value == null ? "" : value.toString());
							value = appConfig.get("dbUname");
							appConfigObj.setDBUname(value == null ? "" : value.toString());
							value = appConfig.get("dbPassword");
							appConfigObj.setDBPassword(value == null ? "" : value.toString());
							
							ArrayList suitesList = (ArrayList)appConfig.get("suites");
							if(suitesList != null){
								for(Object suiteObj : suitesList){
									if(suiteObj != null){
										HashMap suiteMap = (HashMap) ((HashMap)suiteObj).get("suite");
										Suite suite = new Suite();
										value = suiteMap.get("baseUrl");
										suite.setBaseUrl(value == null ? "" : value.toString());
										value = suiteMap.get("browser");
										suite.setBrowser(value == null ? "" : value.toString());
										
										value = suiteMap.get("seq");
										suite.setSeq(value == null ? 1 : Integer.valueOf(value.toString()));
										
										value = suiteMap.get("browserDriverPath");
										if(!value.toString().startsWith("http")){
											value = dirPath + (String)value;
											value = FilenameUtils.separatorsToSystem((String) value);
										}
										suite.setBrowserDrivePath(value == null ? "" : value.toString());

										value = suiteMap.get("name");
										suite.setName(value == null ? "" : value.toString());
										value = suiteMap.get("run");
										suite.setRun(value != null ? value.toString().equalsIgnoreCase("true") ? true : false : false);
									
										@SuppressWarnings("unchecked")
										ArrayList<String> ModuleList = (ArrayList<String>)suiteMap.get("modules");									
										if(ModuleList != null){
										@SuppressWarnings("unchecked")
										ArrayList<String> modules = (ArrayList)ModuleList;
										suite.setModules(modules); 
										}				
										appConfigObj.getSuites().add(suite);
									}
								}
							}
							testConfiguration.getApplications().add(appConfigObj);
						}
					}
					return testConfiguration;
				}
			} else {
				break;
			}
		}
	   return null;
	}

	/**
	 * @return the currentAppConfig
	 */
	public static IAppConfig getCurrentAppConfig() {
		return currentAppConfig;
	}

	/**
	 * @param currentAppConfig
	 *            the currentAppConfig to set
	 */
	public static void setCurrentAppConfig(IAppConfig currentAppConfig) {
		ConfigurationFactory.currentAppConfig = currentAppConfig;
	}
}