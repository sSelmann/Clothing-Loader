package Operations;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import jdk.internal.org.xml.sax.helpers.DefaultHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class XMLOperations extends DefaultHandler {

    public static void main(String[] args) throws FileNotFoundException {
        XML xml = new XMLDocument(new File("input/figuredata.xml"));
        System.out.println(xml.xpath("//sets//set"));

    }

    public static List<XML> getFiguremapLibs(String filePath) throws FileNotFoundException {
        XML xml = new XMLDocument(new File(filePath));
        return xml.nodes("//map//lib");
    }

    public static List<String> getFiguremapLibIDsFromFile(String filePath) throws FileNotFoundException {
        XML xml = new XMLDocument(new File(filePath));
        return xml.xpath("//map//lib/@id");
    }

    public static String getFiguremapLibIDsFromContent(String XMLcontent) throws FileNotFoundException {
        XML xml = new XMLDocument(XMLcontent);
        return xml.xpath("//lib/@id").get(0);
    }

    public static HashMap<String,HashSet<String>> getFigureMapValues(String filePath) throws FileNotFoundException {
        HashMap<String,HashSet<String>> values=new HashMap<>();
        List<XML> listOfLibs=getFiguremapLibs(filePath);
        for (int i = 0; i < listOfLibs.size(); i++) {
            values.put(getFiguremapLibIDsFromContent(listOfLibs.get(i).toString()), getFiguremapLibPartIDs(filePath,i));
        }
        return values;
    }

    public static HashSet<String> getFiguremapLibPartIDs(String filePath, int index) throws FileNotFoundException {
        List<XML> list=getFiguremapLibs(filePath);

        HashSet<String> set=new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            XML xml=new XMLDocument(list.get(index).node());
            set.addAll(xml.xpath("//lib//part/@id"));
        }
        return set;
    }


    public static List<XML> getFigureDataSets(String filePath) throws FileNotFoundException {
        XML xml = new XMLDocument(new File(filePath));
        return xml.nodes("//sets//set");
    }

    public static String getFigureDataSetFigureType(String filePath, int index) throws FileNotFoundException {
        List<XML> sets=getFigureDataSets(filePath);
        String set=sets.get(index).toString();
        XML xml = new XMLDocument(set);
        return xml.xpath("//set//part/@type").get(0);
    }

    public static HashMap<String,HashSet<String>> getFigureDataValues(String filePath) throws FileNotFoundException {
        HashMap<String,HashSet<String>> map=new HashMap<>();
        List<XML> sets= getFigureDataSets(filePath);
        for (XML s: sets) {
            XML xml = new XMLDocument(s.toString());
            HashSet<String> setIDs= new HashSet<>(xml.xpath("//set//part/@id"));
            map.put(xml.xpath("//set/@id").get(0),setIDs);
        }
        return map;
    }

}