package Utils;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import jdk.internal.org.xml.sax.helpers.DefaultHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;

public class XMLOperations extends DefaultHandler {

    public static void main(String[] args) throws FileNotFoundException {
        XML xml = new XMLDocument(new File("input/figuremap.xml"));
        System.out.println(xml.xpath("//map//lib//part/@id"));
        String id = xml.xpath("//order/@id").get(0);
        String name = xml.xpath("//order[@id=4]/text()").toString();
        System.out.println(id);
        System.out.println(name);
        System.out.println(xml.nodes("orders"));
    }


    public static List<XML> getFiguremapLibs(String filePath) throws FileNotFoundException {
        XML xml = new XMLDocument(new File(filePath));
        return xml.nodes("//map//lib");
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