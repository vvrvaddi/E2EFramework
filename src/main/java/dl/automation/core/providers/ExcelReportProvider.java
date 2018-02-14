/**
 *
 */
package dl.automation.core.providers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;

import dl.automation.core.interfaces.IExcelReportProvider;
import dl.automation.core.interfaces.IResultItem;
import dl.automation.core.types.config.ConfigurationFactory;
import dl.automation.core.utils.DateTimeUtils;


public class ExcelReportProvider extends ReportProvider implements IExcelReportProvider {

	private int currentRowIndex = 1;

	/**
	 *
	 */
	public ExcelReportProvider() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dl.automation.core.providers.ReportProvider#generateReport()
	 */
	@Override
	public void generateReport() throws FileNotFoundException, IOException {
		currentRowIndex = 1;
		try {

		InputStream inputStream = getClass().getResourceAsStream("/Automation_TestResults_Template.xlsx");
		// FileInputStream inputStream = new FileInputStream()
		Workbook wb = WorkbookFactory.create(inputStream);
		CreationHelper creationHelper = wb.getCreationHelper();
		Sheet resultSheet = wb.getSheetAt(0);
		this.getResultCollector().getRestultSet().forEach(item -> addRow(item, resultSheet, creationHelper));
		inputStream.close();

		String dirpath=null;
		File destination= null;		
		dirpath = ConfigurationFactory.getCurrentAppConfig().getResultFolder()+"\\ExcelReport\\";
		String dirpath1 = FilenameUtils.separatorsToSystem(dirpath);
		 
		Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss") ;
		destination = new File(dirpath1+dateFormat.format(date)+".xlsx");

		ConfigurationFactory.getCurrentAppConfig().setExcelResultsPath(destination.getAbsolutePath());
		FileOutputStream fileOutput = FileUtils.openOutputStream(destination);
		wb.write(fileOutput);
		fileOutput.close();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
		// throw e;
		}
		}


	/**
	 * @param item
	 * @param resultSheet
	 * @param creationHelper
	 * @return
	 */
	private void addRow(IResultItem item, Sheet resultSheet, CreationHelper creationHelper) {
		Row row = resultSheet.getRow(this.currentRowIndex);

		if (row == null) {
			row = resultSheet.createRow((short) this.currentRowIndex);
		}
		addCell(0, item.getTestCaseId(), row, creationHelper);
		addCell(1, item.getTestCaseTitle(), row, creationHelper);
		addCell(2, item.getStatus().toString(), row, creationHelper);
		addCell(3, item.getModuleName(), row, creationHelper);
		addCell(4, item.getSuite(), row, creationHelper);
		addCell(5, item.getDataSet(), row, creationHelper);
		addCell(6, item.getStartTime().toString(), row, creationHelper);
		addCell(7, item.getEndTime().toString(), row, creationHelper);
		addCell(8, DateTimeUtils.getDurationAsString(item.getExecutionTime()), row, creationHelper);
       
		
		if (item.getFailedStepNumber() != null) {
			addCell(9, item.getFailedStepNumber(), row, creationHelper);
		}
		if (item.getError() != null) {
			addCell(10, item.getError().getMessage(), row, creationHelper);
		}
		if (item.getEvidence() != null) {
			try {
			XSSFHyperlink link = (XSSFHyperlink) creationHelper.createHyperlink(Hyperlink.LINK_FILE);
			link.setAddress(URLEncoder.encode(item.getEvidence(), "UTF-8").replaceAll("%2F", "/"));

			addCell(11, "Click here", link, row, creationHelper);
			} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			}
		} 
		
		++this.currentRowIndex;
	}

	private void addCell(int cellIndex, String value, Row row, CreationHelper creationHelper) {
		Cell cell = row.getCell(cellIndex);
		if (cell == null) {
			cell = row.createCell(cellIndex);
		}
		cell.setCellValue(creationHelper.createRichTextString(value));
	}

	private void addCell(int cellIndex, String value, XSSFHyperlink link, Row row, CreationHelper creationHelper) {
		addCell(cellIndex, value, row, creationHelper);
		row.getCell(cellIndex).setHyperlink(link);
	}
}
