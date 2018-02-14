package dl.automation.core.providers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import dl.automation.core.interfaces.IEmailReportProvider;
import dl.automation.core.interfaces.IResultCollector;
import dl.automation.core.interfaces.IResultItem;
import dl.automation.core.types.config.ConfigurationFactory;
import dl.automation.core.types.enums.ResultStatus;
import dl.automation.core.utils.Log4jUtil;

public class EmailReportProvider extends ReportProvider implements IEmailReportProvider {
	int count = 1;
	static Logger log = Log4jUtil.loadLogger(EmailReportProvider.class);

	@Override
	public void generateReport() throws FileNotFoundException, FileNotFoundException {
		IResultCollector collector = super.getResultCollector();
		List<IResultItem> resultItems = collector.getRestultSet();;
		HashMap<String, String> data = new HashMap<>();
		data.put("{{ExecutedOn}}", String.valueOf(LocalDateTime.now().toString()));
		data.put("{{Environment}}", String.valueOf(ConfigurationFactory.getCurrentAppConfig().getEnvironment()));
		data.put("{{Platform}}", String.valueOf(ConfigurationFactory.getCurrentAppConfig().getPlatform()));
		
		LocalDateTime startTime = resultItems.get(0).getStartTime();
		data.put("{{StartTime}}", startTime.toString());
		LocalDateTime endTime = resultItems.get(resultItems.size() - 1).getEndTime();
		data.put("{{EndTime}}", endTime.toString());
		Duration duration = Duration.between(endTime, startTime);

		String executionTime = duration.toString().substring(duration.toString().indexOf("T-") + 2);
		data.put("{{ExecutionTime}}", executionTime);

		// data.put("{{ExecutionTime}}",DateFormatUtils.ISO_TIME_NO_T_TIME_ZONE_FORMAT.format(duration));
		prepareHtmlMail(data, resultItems);
	}

	private void prepareHtmlMail(HashMap<String, String> data, List<IResultItem> resultItems) {

		count = 1;	
		String filePath=null;				
		String dirpath=null;
		
		
		dirpath = System.getProperty("user.dir")+"\\Email.properties";
		filePath = FilenameUtils.separatorsToSystem(dirpath);	  

		try {
			FileInputStream fileInput = new FileInputStream(filePath);

			Properties prop = new Properties();

			log.info("Path of email connfiguration==>>  " + filePath);

			prop.load(fileInput);

			String toRecipent = prop.getProperty("to");

			String[] recipents = toRecipent.split(",");

			log.info("Email sending to the Recipients ==>>  " + toRecipent);

			int portNumer = Integer.parseInt(prop.getProperty("SMTPport"));

			HtmlEmail email = new HtmlEmail();

			email.setHostName(prop.getProperty("hostName"));

			email.setSmtpPort(portNumer);

			email.setAuthenticator(
					new DefaultAuthenticator(prop.getProperty("username"), prop.getProperty("password")));

			email.setSSLOnConnect(true);

			email.setFrom(prop.getProperty("From"), "Automation Team");

			email.setSubject(prop.getProperty("subject"));

			email.addTo(recipents);

			StringBuffer templatedData = readEmailTempalte();

			StringBuffer resultSetTable = new StringBuffer();

			Map<String, List<IResultItem>> modules = resultItems.stream()
					.collect(Collectors.groupingBy(IResultItem::getModuleName));

			modules.forEach((mn, results) -> {
				List<IResultItem> passedResults = results.stream().filter(r -> r.getStatus() == ResultStatus.Pass)
						.collect(Collectors.toList());
				List<IResultItem> failedResults = results.stream().filter(r -> r.getStatus() == ResultStatus.Fail)
						.collect(Collectors.toList());
				updateResultTable(resultSetTable, Integer.toString(count), mn, Integer.toString(results.size()), Integer.toString(passedResults.size()), Integer.toString(failedResults.size()));
				++count;
			});
			
			List<IResultItem> passedResults = resultItems.stream().filter(r -> r.getStatus() == ResultStatus.Pass)
					.collect(Collectors.toList());
			List<IResultItem> failedResults = resultItems.stream().filter(r -> r.getStatus() == ResultStatus.Fail)
					.collect(Collectors.toList());
			List<IResultItem> lowpriority = resultItems.stream().filter(r -> r.getPriority().equalsIgnoreCase("Low"))
					.collect(Collectors.toList());
			List<IResultItem> mediumpriority = resultItems.stream().filter(r -> r.getPriority().equalsIgnoreCase("Medium"))
					.collect(Collectors.toList());
			List<IResultItem> highpriority = resultItems.stream().filter(r -> r.getPriority().equalsIgnoreCase("High"))
					.collect(Collectors.toList());
						
			updateResultTable(resultSetTable, " ", "Total", Integer.toString(resultItems.size()), Integer.toString(passedResults.size()), Integer.toString(failedResults.size()));
			
			updateResultTable(resultSetTable, " ", "Priority", "Low : "+Integer.toString(lowpriority.size()), "Medium : "+Integer.toString(mediumpriority.size()), "High : "+Integer.toString(highpriority.size()));

			templatedData.insert(templatedData.indexOf("</table>") - 1, resultSetTable);
			String htmlString = templatedData.toString();
			Iterator<Map.Entry<String, String>> dataIterator = data.entrySet().iterator();
			while (dataIterator.hasNext()) {
				Map.Entry<String, String> item = dataIterator.next();
				htmlString = htmlString.replace(item.getKey().trim(), item.getValue().trim());
			}

			email.attach(new File(ConfigurationFactory.getCurrentAppConfig().getExcelResultsPath()));
			
			email.setHtmlMsg(htmlString);

			// set the alternative message
			email.setTextMsg("Your email client does not support HTML messages");

			// send the email
			boolean value = Boolean.parseBoolean(prop.getProperty("ConfigureEmail"));

			if (value) {

				log.info("Sending email to the recipients....");

				email.send();

				log.info("Email sent to all the recipients listed ==>>");

			} else {

				log.info("There is No email sending Configuration...");
			}
		} catch (EmailException ex) {

			log.error(ex.getMessage());

		} catch (Exception e) {

			log.error(e.getMessage());
		}
	}

	private StringBuffer readEmailTempalte() {
		InputStream input = getClass().getResourceAsStream("/Email_Template.html");
		StringBuffer templateData = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				templateData.append(line);
				templateData.append(System.lineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return templateData;
	}
	
	private void updateResultTable(StringBuffer resultSetTable, String sno, String title,  String total, String passed, String failed){
		StringBuffer row = new StringBuffer();
		row.append("<tr><td>");
		row.append(sno);
		row.append("</td><td>");
		row.append(title);
		row.append("</td><td>");
		row.append(total);
		row.append("</td><td>");
		row.append(passed);
		row.append("</td><td>");
		row.append(failed);
		row.append("</td></tr>");
		resultSetTable.append(row.toString());
	}
	
}
