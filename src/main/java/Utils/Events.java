package Utils;

import Operations.JsonOperations;
import Operations.StringOperations;
import Operations.XMLOperations;
import com.jcabi.xml.XML;

import java.io.*;
import java.util.*;

public class Events {

    static String inputFiguredataXMLPath = "input/figuredata.xml";

    public static void addFiguremapData() throws IOException {

        String hotelFiguremapXMLPath = "backupFiles/figuremap.xml";
        String hotelFiguremapJSONPath = "backupFiles/FigureMap.json";
        String inputFiguremapXMLPath = "input/figuremap.xml";
        String inputFiguremapJSONPath = "input/FigureMap.json";
        File fileXML = new File(hotelFiguremapXMLPath);
        File fileJSON = new File(hotelFiguremapJSONPath);

        boolean xml = fileXML.exists();
        boolean json = fileJSON.exists();

        if (xml || json) {

            if (xml) {
                String hotelFigureMap = StringOperations.readFile(hotelFiguremapXMLPath);
                List<XML> figuremapDatas = XMLOperations.getFiguremapLibs(inputFiguremapXMLPath);
                matchDataAndGenerateSql(inputFiguremapXMLPath);

                String toBeeAddedData = "";
                for (XML s : figuremapDatas) {
                    toBeeAddedData = toBeeAddedData.concat(s.toString());
                }

                FileWriter myWriter = new FileWriter("output/xml/figuremap.xml");
                int indexOfLastMapTag = hotelFigureMap.lastIndexOf("</map>");
                String output = new StringBuffer(hotelFigureMap).insert(indexOfLastMapTag, toBeeAddedData).toString();
                myWriter.write(output);
                myWriter.close();

            }

            if (json) {

                String hotelFigureMap = StringOperations.readFile(hotelFiguremapJSONPath);
                String inputFigureMap = StringOperations.readFile(inputFiguremapJSONPath);
                String toBeeAddedData = "";

                for (int i = 0; i < JsonOperations.getJsonArrayLength(inputFigureMap, "FigureMap"); i++) {
                    Map<String, ArrayList<String>> map = JsonOperations.getFigureMapTypeObjectID(inputFigureMap, i);
                    String object = JsonOperations.getJsonObject(inputFigureMap, i, "FigureMap").toString(4);
                    toBeeAddedData = toBeeAddedData.concat("," + object);
                }

                hotelFigureMap = hotelFigureMap.replaceAll("\\s+", "");
                hotelFigureMap = new StringBuffer(hotelFigureMap).insert(hotelFigureMap.length() - 2, toBeeAddedData).toString();
                StringOperations.isJSONValid(hotelFigureMap);
                StringOperations.beautifyWriteJson(hotelFigureMap, "output/json/FigureMap.json");

            }

        } else {
            System.out.println("figuremap is not found");
        }

    }


    public static void addFiguredata() throws IOException {

        String hotelFiguredataXMLPath = "backupfiles/figuredata.xml";
        String hotelFiguredataJSONPath = "backupfiles/FigureData.json";
        String inputFiguredataJSONPath = "input/FigureData.json";
        File xmlFile = new File(hotelFiguredataXMLPath);
        File jsonFile = new File(hotelFiguredataJSONPath);

        boolean xml = xmlFile.exists();
        boolean json = jsonFile.exists();

        if (xml || json) {

            if (xml) {

                String hotelFigureData = StringOperations.readFile(hotelFiguredataXMLPath);
                List<XML> sets = XMLOperations.getFigureDataSets(inputFiguredataXMLPath);

                for (int i = 0; i < sets.size(); i++) {
                    String set = sets.get(i).toString();
                    String figureType = XMLOperations.getFigureDataSetFigureType(inputFiguredataXMLPath, i);
                    hotelFigureData = StringOperations.sendFigureTypes(figureType, hotelFigureData, set, "xml");

                }

            }

            if (json) {

                String hotelFigureData = StringOperations.readFile(hotelFiguredataJSONPath).replaceAll("\\s+", "");
                ;
                String inputFigureData = StringOperations.readFile(inputFiguredataJSONPath).replaceAll("\\s+", "");
                ;

                for (int i = 0; i < JsonOperations.getJsonArrayLength(inputFigureData, "FigureData"); i++) {
                    String toBeeAddedData = JsonOperations.getJsonObject(inputFigureData, i, "FigureData").toString();
                    String figureType = JsonOperations.getFigureDataFirstFigureType(inputFigureData, i);
                    hotelFigureData = StringOperations.sendFigureTypes(figureType, hotelFigureData, toBeeAddedData + ",", "json");
                }

                StringOperations.isJSONValid(hotelFigureData);
                StringOperations.beautifyWriteJson(hotelFigureData, "output/json/FigureData.json");

            }
        } else {
            System.out.println("figuremap is not found");
        }

    }

    private static void matchDataAndGenerateSql(String figureMapPath) throws FileNotFoundException {
        HashMap<String, HashSet<String>> figureMapDataList = XMLOperations.getFigureMapValues(figureMapPath);
        List<String> itemNameList = StringOperations.getItemFileNames();
        File catalogSQLFile = new File("output/sqls/catalog_items.sql");
        File itemsBaseSQLFile = new File("output/sqls/items_base.sql");
        File catalogClothingPath = new File("output/sqls/catalog_clothing.sql");
        File furniDataXMLPath = new File("output/furnidata.xml");
        File furnitureDataJsonPath = new File("output/json/FurnitureData.json");
        catalogSQLFile.delete();
        itemsBaseSQLFile.delete();
        catalogClothingPath.delete();
        furniDataXMLPath.delete();
        furnitureDataJsonPath.delete();

        long randomItemID = StringOperations.createRandomNumber();

        for (String s : itemNameList) {

            if (GetConfig.ini.get("Add_To_Clothing_Catalog_&_Market_Catalog", s) != null) {
                String figureItemName = GetConfig.ini.get("Add_To_Clothing_Catalog_&_Market_Catalog", s);
                if (figureMapDataList.get(figureItemName) != null) {
                    HashSet<String> figurePartIDs = figureMapDataList.get(figureItemName);
                    HashMap<String, HashSet<String>> figureDataSets = XMLOperations.getFigureDataValues(inputFiguredataXMLPath);
                    List<String> keyLists = new ArrayList<>(figureDataSets.keySet());
                    for (int i = 0; i < keyLists.size(); i++) {
                        if (figureDataSets.get(keyLists.get(i)).containsAll(figurePartIDs)) {
                            int customParam = Integer.parseInt(keyLists.get(i));

                            generateSQL(s, figureItemName, customParam, "output/sqls/catalog_items.sql", "output/sqls/items_base.sql", "output/sqls/catalog_clothing.sql", randomItemID);
                            XMLOperations.createXmlData(s, customParam, randomItemID, "output/xml/furnidata.xml");
                            JsonOperations.createJsonData(s,customParam,randomItemID,"output/json/FurnitureData.json");
                            randomItemID++;
                        }
                    }
                } else {
                    System.out.println("There is no matching figuremap id for " + figureItemName + " please check config.");
                }
            } else {
                System.out.println("There is no matching figure item name for " + s + " please check config.");
            }

        }
    }


    private static void generateSQL(String itemName, String figureItemName, int figuredataSetID, String catalogSQLFilePath, String itemsBaseSQLFilePath, String catalogClothingSQLFilePath, long randomIDNumber) {

        try {

            Writer writeItems = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(catalogSQLFilePath, true), "UTF-8"));
            writeItems.write("INSERT INTO `catalog_items` (`id`, `page_id`, `item_ids`, `catalog_name`)VALUES ('" + randomIDNumber + "', 'PAGE_ID', '" + randomIDNumber + "', '" + itemName + "');\n");

            Writer writeCatalogItems = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(itemsBaseSQLFilePath, true), "UTF-8"));
            writeCatalogItems.write("INSERT INTO `items_base` (`id`,`item_name`,`public_name`,`stack_height`,`allow_stack`,`sprite_id`,`interaction_type`,`interaction_modes_count`)VALUES ('" + randomIDNumber + "', '" + itemName + "', '" + itemName + "_name', '1', '1', '" + randomIDNumber + "', 'default', '1');\n");

            Writer writeCatalogClothing = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(catalogClothingSQLFilePath, true), "UTF-8"));
            writeCatalogClothing.write("INSERT INTO `catalog_clothing` (`name`,`set_id`)VALUES ('" + figureItemName + "', '" + figuredataSetID + "');\n");

            writeItems.close();
            writeCatalogItems.close();
            writeCatalogClothing.close();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}


