/**
 *
 */
package dl.automation.core.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dl.automation.core.excelloader.BaseExcelLoader;
import dl.automation.core.types.config.ConfigurationFactory;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import dl.automation.core.excelloader.NExcelLoader;
import dl.automation.core.model.TestObjectModel;
import dl.automation.core.utils.Log4jUtil;


public class TestORLoaderFactory {
    static Logger log = Log4jUtil.loadLogger(TestORLoaderFactory.class);

    public static List<TestObjectModel> getORFromFile() throws EncryptedDocumentException, InvalidFormatException, IOException, NullPointerException {
        List<TestObjectModel> testORelement = new ArrayList<>();
        BaseExcelLoader excelLoader = null;       
        String inputFolderN = ConfigurationFactory.getCurrentAppConfig().getInputFolderN();        
        if (inputFolderN != null && !inputFolderN.equalsIgnoreCase("") && !inputFolderN.equalsIgnoreCase(" ")){
            excelLoader = new NExcelLoader();
            testORelement.addAll(getORFromFolder(inputFolderN, excelLoader));
        }
        return testORelement;
    }    
    
    private static List<TestObjectModel> getORFromFolder(String folderPath, BaseExcelLoader excelLoader) throws IOException, InvalidFormatException {        
        List<TestObjectModel> testRepository = new ArrayList<>();
    if (folderPath != null && !folderPath.equalsIgnoreCase("") && !folderPath.equalsIgnoreCase(" ")){
            Stream<Path> fileStream = Files.list(Paths.get(folderPath));
            List<String> paths = fileStream.map(String::valueOf).collect(Collectors.toList());
            for (String path : paths){
            if (path.contains("_OR.xlsx")&&!path.contains("\\~")) {
            testRepository.addAll(excelLoader.loadOR(path));            
              }                
            }
        }
        return testRepository;
    }

    public static List<TestObjectModel> getTestCasesFromDatbase() {
        return null;
    }
}
