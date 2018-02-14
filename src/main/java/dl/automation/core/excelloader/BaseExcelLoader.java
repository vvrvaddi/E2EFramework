/**
 * 
 */
package dl.automation.core.excelloader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.esotericsoftware.kryo.Kryo;
import org.apache.log4j.Logger;
import dl.automation.core.model.*;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import dl.automation.core.loader.TestCaseLoader;
import dl.automation.core.model.TestCaseModel;
import dl.automation.core.model.TestDataObjectModel;
import dl.automation.core.model.TestObjectModel;
import dl.automation.core.model.TestStepModel;
import dl.automation.core.utils.Log4jUtil;
import dl.automation.core.utils.StringUtils;

public class BaseExcelLoader implements TestCaseLoader {
	static Logger log = Log4jUtil.loadLogger(BaseExcelLoader.class);
	protected List<TestCaseModel> cases = null;
	protected List<TestObjectModel> objectRepo = null;
	protected List<TestDataObjectModel> dataRepo = null;
	protected List<TestStepModel> testStepModels = null;
	protected Workbook workBook = null;
	protected Kryo kryo = new Kryo();

	protected List<TaskStepModel> taskStepModels = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see dl.automation.core.loader.TestCaseLoader#load(java.lang.String)
	 */
	@Override
	public List<TestCaseModel> load(String filePath)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
	    String file =filePath;
		log.info("Load Excel file: "+file);
		if (Files.exists(Paths.get(filePath))) {
			try {
              
				FileInputStream inputStream = new FileInputStream(filePath);
				workBook = WorkbookFactory.create(inputStream);				
				cases = this.readTestCases(workBook);				
				dataRepo = readDataRepo(workBook);				
			
			} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
				throw e;
			}
		} else {
			System.out.println("File does not exist");
		}
		return cases;
	}
	
	public List<TestObjectModel> loadOR(String filePath)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		 String file =filePath;
		log.info("Load Excel file" + file);
		if (Files.exists(Paths.get(filePath))) {
			try {
              
				FileInputStream inputStream = new FileInputStream(filePath);
				workBook = WorkbookFactory.create(inputStream);				
				objectRepo = readObjectRepo(workBook);
				
			} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
				throw e;
			}
		} else {
			System.out.println("File does not exist");
		}
		return objectRepo;
	}

	protected void readTaskModels() {
	}

	/**
	 * @param workBook
	 * @param sheetName
	 * @return Iterator<Row>
	 */
	protected Iterator<Row> readSheet(Workbook workBook, String sheetName) {
		Sheet sheet = workBook.getSheet(sheetName);
		return sheet.rowIterator();
	}

	/**
	 * @param parameters
	 * @return
	 */
	protected List<String> parseInputParamenters(String parameters) {
		return StringUtils.splitParams("Index", parameters);
	}

	/**
	 * @param cellValue
	 * @return
	 */
	protected List<String> parseInputParameterTypes(String parameterTypes) {
		return StringUtils.splitParamTypes(":", parameterTypes);
	}

	/**
	 * @param rowIterator
	 */
	protected List<TestCaseModel> readTestCases(Workbook workBook) {
		List<TestCaseModel> cases = new ArrayList<TestCaseModel>();
		Iterator<Row> rowIterator = readSheet(workBook, "TestCases");
		Boolean headerPassed = false;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (headerPassed) {
				if (!isRowEmpty(row)) {
					cases.add(createTestCase(row.cellIterator()));
				}
			} else {
				headerPassed = true;
			}
		}
		log.debug("Reading of all the TC's under Test Cases Sheet is done  ");
		return cases;
	}

	/**
	 * @param cellIterator
	 * @return
	 */
	protected TestCaseModel createTestCase(Iterator<Cell> cellIterator) {
		return null;
	}

	/**
	 * @param cell
	 * @return
	 */
	protected Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			return String.valueOf(((Double) cell.getNumericCellValue()).intValue());
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return null;
		}
	}

	/**
	 * @param workBook
	 * @return
	 */
	protected List<TestDataObjectModel> readDataRepo(Workbook workBook) {
		log.debug("Reading Data repository");
		List<TestDataObjectModel> dataRepo = new ArrayList<TestDataObjectModel>();
		Iterator<Row> rowIterator = readSheet(workBook, "TestData");
		Boolean headerPassed = false;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (headerPassed) {
				if (!isRowEmpty(row)) {
					dataRepo.add(createTestDataObject(row.cellIterator()));
				}
			} else {
				headerPassed = true;
			}
		}

		return dataRepo;
	}

	/**
	 * @param cellIterator
	 * @return
	 */
	protected TestDataObjectModel createTestDataObject(Iterator<Cell> cellIterator) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param workBook
	 * @return List<TestObjectModel>
	 */
	protected List<TestObjectModel> readObjectRepo(Workbook workBook) {
		log.debug("Reading object Repository");
		List<TestObjectModel> objectRepo = new ArrayList<TestObjectModel>();
		Iterator<Row> rowIterator = readSheet(workBook, "ObjectRepo");
		Boolean headerPassed = false;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (headerPassed) {
				if (!isRowEmpty(row)) {
					objectRepo.add(createTestObject(row.cellIterator()));
				}
			} else {
				headerPassed = true;
			}
		}
		return objectRepo;
	}

	/**
	 * @param cellIterator
	 * @return
	 */
	protected TestObjectModel createTestObject(Iterator<Cell> cellIterator) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param cases
	 * @param workBook
	 * @return List<TestStepModel>
	 */
	protected List<TestStepModel> readTestSteps(Workbook workBook, List<TestCaseModel> testCaseModels,
			List<TestObjectModel> objectRepo, List<TestDataObjectModel> dataRepo) {
		log.debug("Reading steps under TestSteps");
		List<TestStepModel> steps = new ArrayList<TestStepModel>();
		Iterator<Row> rowIterator = readSheet(workBook, "TestSteps");
		Boolean headerPassed = false;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (headerPassed) {
				if (!isRowEmpty(row)) {
					TestStepModel step = createTestStep(row.cellIterator(), testCaseModels, objectRepo, dataRepo);
					steps.add(step);
					log.debug(" Read Step : " + step.getTestCase().getId() + " : " + step.getStepNumber()
 							+ " Row No In Excel : " + row.getRowNum());
				}
			} else {
				headerPassed = true;
			}
		}
		return steps;
	}

	/**
	 * @param cellIterator
	 * @param testCaseModels
	 * @param objectRepo
	 * @param dataRepo
	 * @return
	 */
	protected TestStepModel createTestStep(Iterator<Cell> cellIterator, List<TestCaseModel> testCaseModels,
			List<TestObjectModel> objectRepo, List<TestDataObjectModel> dataRepo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param testCaseModels
	 * @param testStepModels
	 */
	protected void LinkTestStepsToCases(List<TestCaseModel> testCaseModels, List<TestStepModel> testStepModels) {
		log.debug("Adding Test Steps to Test Cases");
		for (TestCaseModel testCaseModel : testCaseModels) {
			List<TestStepModel> steps = testStepModels.stream()
					.filter(ts -> ts.getTestCase().getId().equals(testCaseModel.getId())).collect(Collectors.toList());
			testCaseModel.addTestSteps(steps);
		}
	}

	/**
	 * @param cellValue
	 * @return
	 */
	protected TestDataObjectModel createTestDataObject(String cellValue) {
		TestDataObjectModel testDataObjectModel = new TestDataObjectModel();
		if (cellValue != null && cellValue != "") {
			testDataObjectModel.setDataValues(parseInputParamenters(cellValue));
		}
		return testDataObjectModel;
	}

	/**
	 * @param row
	 * @return
	 */
	public static boolean isRowEmpty(Row row) {
		Cell cell = row.getCell(0);
		if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
			return false;

		return true;
	}

}
