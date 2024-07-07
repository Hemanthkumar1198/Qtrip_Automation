package qtriptest;

import java.io.File;
import java.sql.Timestamp;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentReportSingleton {

    public static ExtentReportSingleton instanceofExtentReportSingleton = null;
    private ExtentReports report;


    public static String getTimestamp()
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return String.valueOf(timestamp.getTime());
    }
    
   private ExtentReportSingleton(){
        //report = new ExtentReports(System.getProperty("user.dir"+"/report.html"), true);
        report = new ExtentReports(System.getProperty("user.dir")+ "/report"+getTimestamp()+".html", true);
        report.loadConfig(new File(System.getProperty("user.dir")+"/config.xml"));
    }

    public static ExtentReportSingleton getInstaceOfReportSingleton(){
        if(instanceofExtentReportSingleton == null){
                instanceofExtentReportSingleton = new ExtentReportSingleton();
        }
        return instanceofExtentReportSingleton;
    }

    public ExtentReports getReport(){
        return report;
    }
}