package reportManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rish on 26.06.2016.
 */
public class SettingsReader {

    private Map<String, Integer> page;
    private Map<String, Integer> columns;
    private List<String> columnsName;
    private NodeList nodeList;
    private Element elem;

    public SettingsReader(File file) {

        page = new HashMap<String, Integer>();
        columns = new HashMap<String, Integer>();
        columnsName = new ArrayList<String>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();
            nodeList = document.getElementsByTagName("page");
            elem = (Element) nodeList.item(0);
            page.put("width", Integer.parseInt(elem.getElementsByTagName("width").item(0).getTextContent()));
            page.put("height", Integer.parseInt(elem.getElementsByTagName("height").item(0).getTextContent()));
            nodeList = document.getElementsByTagName("columns");
            NodeList columnList = nodeList.item(0).getChildNodes();
            for (int i = 0; i < columnList.getLength(); i++) {
                Node column = columnList.item(i);
                if (column.getNodeType() != Node.TEXT_NODE) {
                    String title = "";
                    Integer width = 0;
                    NodeList columnProperties = column.getChildNodes();
                    for (int j = 0; j < columnProperties.getLength();j++) {
                        Node columnProp = columnProperties.item(j);
                        if (columnProp.getNodeType() != Node.TEXT_NODE) {
                            if (columnProp.getNodeName().equals("title")){
                                title = columnProp.getTextContent();
                            }
                            if (columnProp.getNodeName().equals("width")){
                                width = Integer.parseInt(columnProp.getTextContent());
                            }
                        }
                    }
                    columns.put(title,width);
                    columnsName.add(title);
                }
            }
        } catch (Exception e) {

        }

    }

    public Map<String,Integer> getPageSettings() {
        return page;
    }

    public Map<String,Integer> getColumnMap() {
        return columns;
    }

    public List<String> getColumnsName() {
        return columnsName;
    }
}
