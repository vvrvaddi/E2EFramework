/**
 *
 */
package dl.automation.core.excelloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import dl.automation.core.types.config.ConfigurationFactory;
import dl.automation.core.types.enums.Platform;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import dl.automation.core.loader.TestORLoaderFactory;
import dl.automation.core.model.TaskModel;
import dl.automation.core.model.TestCaseModel;
import dl.automation.core.model.TestDataObjectModel;
import dl.automation.core.model.TestObjectModel;
import dl.automation.core.model.TestStepModel;
import dl.automation.core.types.enums.IdentifierType;
import dl.automation.core.types.enums.StepTypes;

public class NExcelLoader extends BaseExcelLoader {
    private List<TaskModel> tasks = new ArrayList<>();
 
    @Override
    public List<TestCaseModel> load(String filePath)
            throws EncryptedDocumentException, InvalidFormatException, IOException {
        try {      	
            super.load(filePath);
            readTaskModels(); 
            if(objectRepo==null)
            {
            objectRepo = TestORLoaderFactory.getORFromFile();
            }
            testStepModels = readTestSteps(workBook, cases, objectRepo, dataRepo);
            LinkTestStepsToCases(cases, testStepModels);
        } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            throw e;
        } finally {
            if (workBook != null) {
                workBook.close();
            }
        }
        return cases;
    }
    
   
    /**
     * @param cellIterator
     * @return
     */
    @Override
    protected TestCaseModel createTestCase(Iterator<Cell> cellIterator) {
        log.debug("Create Testcases");
        TestCaseModel tc = new TestCaseModel();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            int index = cell.getColumnIndex();

            Object cellValue = getCellValue(cell);
            if (cellValue != null) {
                switch (index) {
                    case 0:
                        tc.setId(cellValue.toString());
                        log.debug("Current Test case " + tc.getId());
                        break;
                    case 1:
                        tc.setTitle((String) cellValue);
                        break;
                    case 2:
                        tc.setModule((String) cellValue);
                        break;
                    case 3:
                        tc.setSuite((String) cellValue);
                        break;
                    case 4:
                        tc.setPriority((String) cellValue);
                        break;
                    case 5:
                        tc.setExecution((String) cellValue);
                        break;
                    case 6:
                        tc.setRequirement((String) cellValue);
                        break;
                    case 7:
                        tc.setSource((String) cellValue);
                        break;
                    case 8:
                        tc.setComments((String) cellValue);
                        break;
                    default:
                        break;
                }
            }
        }
        return tc;
    }

    /**
     * @param cellIterator
     * @param testCaseModels
     * @param objectRepo
     * @param dataRepo
     * @return
     */
    @Override
    protected TestStepModel createTestStep(Iterator<Cell> cellIterator, List<TestCaseModel> testCaseModels,
                                           List<TestObjectModel> objectRepo, List<TestDataObjectModel> dataRepo) {
        TestStepModel ts = new TestStepModel();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            int index = cell.getColumnIndex();
            Object cellValue = getCellValue(cell);
            if (cellValue != null) {
                switch (index) {
                    case 0:
                        String tcid = cellValue.toString();
                        log.debug("Reading Test steps of " + tcid);
                        try {
                            TestCaseModel tcase = testCaseModels.stream().filter(tc -> tc.getId().equals(tcid)).findFirst()
                                    .get();
                            ts.setTestCase(tcase);
                        } catch (NoSuchElementException ex) {
                            System.out.println(
                                    "In data Sheet Row:" + cell.getRowIndex() + cell.getAddress() + ex.getMessage());
                        }
                        break;
                    case 1:
                        ts.setStepNumber((new Double(cellValue.toString()).intValue()));
                        break;
                    case 2:
                        ts.setPage((String) cellValue);
                        break;
                    case 3:
                        setTestObjectOrTask(objectRepo, ts, (String) cellValue);
                        break;
                    case 4:
                        ts.setDataObject(getTestDataObject((String) cellValue, ts.getTestObject(), dataRepo));
                        break;
                    case 5:
                        ts.setAction((String) cellValue);
                        break;
                    case 6:
                        ts.setCondition((String) cellValue);
                        break;
                    case 7:
                        ts.setComments((String) cellValue);
                    default:
                        break;
                }
            }
        }
        return ts;
    }

    private void setTestObjectOrTask(List<TestObjectModel> objectRepo, TestStepModel ts, String cellValue) {
        TestObjectModel testObjectModel = getTestObject(cellValue, objectRepo);
        if (testObjectModel != null) {
            ts.setTestObject(testObjectModel);
            ts.setStepType(StepTypes.Step);
        } else {
            TaskModel taskModel = getTaskModel(cellValue, tasks);
            if (taskModel != null) {
                ts.setTaskModel(taskModel);
                ts.setStepType(StepTypes.Task);
            }
        }

    }

    private TaskModel getTaskModel(String cellValue, List<TaskModel> tasks) {
        try {
            Optional<TaskModel> task = tasks.stream().filter(t -> t.getId().equalsIgnoreCase(cellValue)).findFirst();
            return task.get();
        } catch (NullPointerException e) {

        } catch (NoSuchElementException e) {

        }
        return null;
    }

    /**
     * @param cellIterator
     * @return
     */
    @Override
    protected TestObjectModel createTestObject(Iterator<Cell> cellIterator) {
        log.debug("Creating Object Repository ");
        TestObjectModel tObject = new TestObjectModel();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            int index = cell.getColumnIndex();
            Object cellValue = getCellValue(cell);
            if (cellValue != null) {
                switch (index) {
                    case 0:
                        tObject.setId((String) cellValue);
                        log.debug(tObject.getIdentifier());
                        break;
                    case 1:
                        tObject.setIdentifier((String) cellValue);
                        log.debug(tObject.getIdentifier());
                        break;
                    case 2:
                        tObject.setIdentifierType(IdentifierType.valueOf((String) cellValue));
                        break;
                    case 3:
                        tObject.setPlatForm(Platform.valueOf((String) cellValue));
                        break;
                    case 4:
                        tObject.setComments((String) cellValue);
                        break;
                    case 5:
                        tObject.setPage((String) cellValue);
                        break;
                    default:
                        break;
                }
            }
        }
        return tObject;
    }

    /**
     * @param cellIterator
     * @return
     */
    @Override
    protected TestDataObjectModel createTestDataObject(Iterator<Cell> cellIterator) {
        TestDataObjectModel tdo = new TestDataObjectModel();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            int index = cell.getColumnIndex();
            Object cellValue = getCellValue(cell);
          
            if (cellValue != null) {
                switch (index) {
                    case 0:
                        tdo.setId((String) cellValue);
                        break;
                    case 1:
                        tdo.setDataValues(super.parseInputParamenters((String) cellValue));
                        log.debug(tdo.getDataValues());
                        break;
                    case 2:
                        tdo.setDataValues(super.parseInputParamenters((String) cellValue));
                        log.debug(tdo.getDataValues());
                        //log.info(tdo.GetDataSet(""));
                        break;
                  
                    case 3:
                        tdo.setPlatForm(Platform.valueOf((String) cellValue));
                        break;
                    case 4:
                        tdo.setComments((String) cellValue);
                        break;
                    default:
                        break;
                }
            }
        }

        return tdo;
    }
            


   
    /**
     * @param cellValue
     * @param testObjectModel
     * @param dataRepo
     * @return
     */
    private TestDataObjectModel getTestDataObject(String cellValue, TestObjectModel testObjectModel,
                                                  List<TestDataObjectModel> dataRepo) {
        TestDataObjectModel testDataObjectModel = null;
        String[] matches = cellValue.split(":");
        if (matches.length > 1) {
            switch (matches[0]) {

                case "td":
                    testDataObjectModel = dataRepo.stream().filter(tdobj -> tdobj.getId().equals(matches[1])
                                                                                            && (tdobj.getPlatForm() == Platform.valueOf(ConfigurationFactory.getCurrentAppConfig().getPlatform())
                                                                                                    || tdobj.getPlatForm() == Platform.Common)
                                                                    ).findFirst()
                            .get();
                    break;
                case "in":
                    testDataObjectModel = super.createTestDataObject(cellValue);
                    break;

                case "RND":

                    testDataObjectModel = new TestDataObjectModel();

                    if (matches.length == 2) {

                        testDataObjectModel.getDataValues().add(generateRandom(Integer.valueOf(matches[1])));

                    } else if (matches.length == 3) {

                        testDataObjectModel.getDataValues().add(matches[2] + generateRandom(Integer.valueOf(matches[1])));

                    }

                    break;

                default:
                    break;
            }

        } else {
            testDataObjectModel = super.createTestDataObject(cellValue);
        }
        return testDataObjectModel;
    }

    private String generateRandom(int digits) {

        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int count = 0; count < digits; count++) {

            sb.append(String.valueOf(random.nextInt(10)));

        }

        return sb.toString();

    }

    /**
     * @param cellValue
     * @param objectRepo
     * @return
     */
    private TestObjectModel getTestObject(String cellValue, List<TestObjectModel> objectRepo) {
        try {
            Optional<TestObjectModel> testObject = objectRepo.stream().filter(tom -> tom.getId().equals(cellValue)
                                                                                                    && (tom.getPlatForm() == Platform.valueOf(ConfigurationFactory.getCurrentAppConfig().getPlatform())
                                                                                                            || tom.getPlatForm() == Platform.Common)
                                                                                ).findFirst();
            return testObject.get();
        } catch (NullPointerException ex) {

        } catch (NoSuchElementException es) {

        }
        return null;
    }

}
