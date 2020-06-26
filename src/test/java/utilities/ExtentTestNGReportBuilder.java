package utilities;

import java.io.File;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;



import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;



public abstract class ExtentTestNGReportBuilder {

	public static ExtentReports extentReports;
	public static ExtentTest extentTest;
	public static String sFile;
	/**
	 * @author Santosh
	 * @description: This function will be set before the complete suite
	 */
	@BeforeSuite
	public synchronized void beforeSuite() {
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");
			DateFormat timeFormat = new SimpleDateFormat("HH_mm_ss");
			Calendar cal = Calendar.getInstance();
			dateFormat.format(cal.getTime());
			timeFormat.format(cal.getTime());
			String sSuiteSelected = "UI";
			String sProjectName = "ABSA_Assessment";
			
			String outputDir=null;



			outputDir = System.getProperty("user.dir")+"/reports/"
					+ dateFormat.format(cal.getTime()) + "/" + sProjectName
					+ "_Execution_" + sSuiteSelected + "_"
					+ timeFormat.format(cal.getTime());


			File file = new File(outputDir);
			file.mkdirs();
			Reporter.log("Current Project Results Directory: "
					+ outputDir, true);
			String sExtentReportPath = outputDir
					+ "/CompleteExecutionReport.html";
			

			System.out.println("Report Path::::" + sExtentReportPath);
			
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
					sExtentReportPath);
			
			htmlReporter.loadXMLConfig(System.getProperty("user.dir")+ "\\resources\\extent-config.xml");
			// htmlReporter.setAppendExisting(true);

			extentReports = new ExtentReports();
			extentReports.attachReporter(htmlReporter);
			// Add for System INFO
			String sHostName = null;
			try {
				sHostName = InetAddress.getLocalHost().getHostName();
			} catch (Exception e) {
				// TODO: handle exception
			}
			extentReports.setSystemInfo("OS", System.getProperty("os.name"));
			extentReports.setSystemInfo("Host Name", sHostName);
			extentReports.setSystemInfo("Java Version",
					System.getProperty("java.version"));
			extentReports.setSystemInfo("User Name",
					System.getProperty("user.name"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		


	}
}
