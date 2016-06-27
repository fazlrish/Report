package reportManager;

import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import com.sun.xml.internal.ws.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rish on 26.06.2016.
 */
public class ReportMaker {

    private File settingsFile;
    private File dataFile;
    private static SettingsReader settingsReader;
    private static DataReader dataReader;
    private static BufferedWriter writer;

    public ReportMaker(String settingsXMLFilePath, String dataTSVFilePath, String reportTXTFilePath) throws IOException {
        settingsFile = new File(settingsXMLFilePath);
        dataFile = new File(dataTSVFilePath);
        settingsReader = new SettingsReader(settingsFile);
        dataReader = new DataReader(dataFile);
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportTXTFilePath)));
    }

    public static void makeReport() throws IOException {
        Map<String, Integer> page = settingsReader.getPageSettings();
        Map<String, Integer> columns = settingsReader.getColumnMap();
        List<String> columnsName = settingsReader.getColumnsName();
        List<String> id = dataReader.getId();
        id.set(0,"1");
        List<String> date = dataReader.getDate();
        List<String> fullName = dataReader.getFullName();

        int dataCounter = 0;
        int row = 1;
        ReportMaker.buildHeader(columnsName, columns, writer);
        while (id.size() > dataCounter) {
            if (row>=page.get("height")-2){
                writer.write("~\n");
                row = 0;
                ReportMaker.buildHeader(columnsName, columns, writer);
            }
            String cutLine = "";
            for (int i = 0; i < page.get("width"); i++) {
                cutLine += "-";
            }
            cutLine += "\n";
            writer.write(cutLine);
            row++;
            row += ReportMaker.buildBody(id.get(dataCounter), date.get(dataCounter), fullName.get(dataCounter), columns, columnsName, writer);
            dataCounter++;
            /*while (row < page.get("height") - 2) {
            }
            */
        }
        writer.close();

    }

    private static int buildBody(String id, String date, String Name, Map<String, Integer> columns, List<String> columnsName, BufferedWriter writer) throws IOException {

        int row = 0;
        String[] line = new String[]{id, date, Name};
        while (!ReportMaker.isEmpty(line)){
            String body = "| ";
            for (String columnName : columnsName) {
                String columnData = line[columnsName.indexOf(columnName)];
                if (columnData.toCharArray().length <= columns.get(columnName)) {
                    body += columnData;
                    int dif = columns.get(columnName) - columnData.length();
                    System.out.println(columns.get(columnName));
                    System.out.println(columnData.length());
                    for (int i = 0; i < dif; i++) {
                        body += " ";
                    }
                    line[columnsName.indexOf(columnName)] = "";
                } else {
                    char[] charSet = columnData.toCharArray();
                    String toBodyString = "";
                    for (int i = 0; i < columns.get(columnName); i++) {
                        toBodyString+=charSet[i];
                    }
                    body+=toBodyString;
                    String restPart = "";
                    for (int i = columns.get(columnName);i<charSet.length;i++){
                        restPart+=charSet[i];
                    }
                    line[columnsName.indexOf(columnName)] = restPart;
                }
                body+=" | ";
                if (columnsName.indexOf(columnName)==line.length-1){
                    writer.write(body+"\n");
                    row++;
                }
            }
        }
        return row;
    }

    private static boolean isEmpty(String[] line) {
        boolean result = false;
        for (String word: line){
            if (word.equals("")){
                result = true;
            }
            else{
                result = false;
            }
        }
        return result;
    }

    private static void buildHeader(List<String> columnsName, Map<String, Integer> columns, BufferedWriter writer) throws IOException {
        String header = "| ";
        for (String columnName : columnsName) {
            header += columnName;
            for (int i = 0; i < columns.get(columnName) - columnName.length(); i++) {
                header += " ";
            }
            header += " | ";
        }
        writer.write(header + "\n");

    }
}
