package main;



import reportManager.ReportMaker;

import java.io.IOException;


/**
 * Created by Rish on 25.06.2016.
 */
public class Report {
    public static void main(String[] args) throws IOException {

        String settingsXMLFilePath = args[0];// "settings.xml";
        String dataTSVFilePath = args[1]; //"source-data.tsv";
        String reportTXTFilePath = args[2];

        ReportMaker reportMaker = new ReportMaker(settingsXMLFilePath, dataTSVFilePath, reportTXTFilePath);
        reportMaker.makeReport();



    }
}
