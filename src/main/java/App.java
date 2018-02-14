
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import dl.automation.core.utils.Log4jUtil;


public class App {
	static Logger log = Log4jUtil.loadLogger(App.class);

	public static ExecutorService service = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());

	public static void main(String[] args) throws IOException, AWTException {
		CommandLineParser parser = new DefaultParser();
		if (args.length > 0) {

			try {

				Options options = getOptions();
				CommandLine line = parser.parse(options, args);
				if (!line.hasOption("DB")) {
					String configFilePath = tryGetValue(line, "C");
					if (configFilePath != null) {
												
                        runInvoker(configFilePath);
                       /* screenRecorder.stop();*/
                        
					} else {
						printUsage(options);
					}
				} else {
					log.info("Reading from DB");
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info("Parse Error", e);
			}
		}
	}


	private static void runInvoker(String configFilePath){
	    Invoker.invoke(configFilePath);
    }

	private static Options getOptions() {
		Options options = new Options();
		options.addOption("NE", "NExcel", true, "Input excel file folder path");
		options.addOption("C", "config", true, "Input config file path");
		return options;
	}

	private static String tryGetValue(CommandLine line, String optionCode) {
		if (line.hasOption(optionCode)) {
			return line.getOptionValue(optionCode);
		}
		return null;
	}

	private static void printUsage(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("cli", options);
	}

}
