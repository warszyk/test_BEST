package logging;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    public static ExtentReports createExtentReportFile(String fileName){
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + System.getProperty("file.separator") + fileName + ".html");
        htmlReporter.config().setDocumentTitle("Regression Tests");
        htmlReporter.config().setReportName("Suite");
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setProtocol(Protocol.HTTPS);
        htmlReporter.config().setTimeStampFormat("yyyy-MM-dd - HH:mm:ss");
        htmlReporter.config().setJS("<![CDATA[ $(document).ready(function() { });]]>");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        return extentReports;
    }

    public static void closeReport(ExtentReports extentReports) {
        if(extentReports != null) extentReports.flush();
    }

    public static ExtentTest startTest(ExtentReports extentReports, String testName) {
        return extentReports.createTest(testName);
    }
}