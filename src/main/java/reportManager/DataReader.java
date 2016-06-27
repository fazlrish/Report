package reportManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rish on 27.06.2016.
 */
public class DataReader {

    private File dataFile;
    private List<String> id = new ArrayList<String>();
    private List<String> date = new ArrayList<String>();
    private List<String> fullName = new ArrayList<String>();

    public DataReader(File dataFile) throws IOException {
        this.dataFile = dataFile;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile),"UTF-16LE"));
        while (true){
            String data = reader.readLine();
            if (data==null){
                break;
            }
            String[] dataLine = data.split("\t");
            id.add(dataLine[0]);
            date.add(dataLine[1]);
            fullName.add(dataLine[2]);
        }
    }

    public List<String> getId() {
        return id;
    }

    public List<String> getDate() {
        return date;
    }

    public List<String> getFullName() {
        return fullName;
    }
}
