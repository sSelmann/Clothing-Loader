package Operations;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import jdk.internal.org.xml.sax.helpers.DefaultHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class XMLOperations extends DefaultHandler {

    public static void main(String[] args) throws FileNotFoundException {
        XML xml = new XMLDocument(new File("input/figuredata.xml"));
        System.out.println(xml.xpath("//sets//set/@id"));

    }

    public static List<XML> getFiguremapLibs(String filePath) throws FileNotFoundException {
        XML xml = new XMLDocument(new File(filePath));
        return xml.nodes("//map//lib");
    }

    public static List<XML> getFigureDataSets(String filePath) throws FileNotFoundException {
        XML xml = new XMLDocument(new File(filePath));
        return xml.nodes("//sets//set");
    }

    public static String getFigureDataSetFigureType(String filePath, int index) throws FileNotFoundException {
        List<XML> sets=getFigureDataSets(filePath);
        String set=sets.get(index).toString();
        XML xml = new XMLDocument(set);
        System.out.println(set);
        return xml.xpath("//set//part/@type").get(0);
    }

    public static List<String> getFigureDataSetIDs(String filePath) throws FileNotFoundException {
        XML xml = new XMLDocument(new File(filePath));
        return xml.xpath("//sets//set/@id");
    }

    public static List<String> getFiguremapLibIDs(String filePath) throws FileNotFoundException {
        XML xml = new XMLDocument(new File(filePath));
        return xml.xpath("//map/@id");
    }

    public static HashSet<String> getFiguremapLibPartIDs(String filePath) throws FileNotFoundException {
        List<XML> list=getFiguremapLibs(filePath);
        HashSet<String> set=new HashSet<>();
        for (XML s: list) {
            set.addAll(s.xpath("//map//lib//part/@id"));
        }
        return set;
    }

}