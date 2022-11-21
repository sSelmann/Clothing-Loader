package Utils;

import Operations.JsonOperations;
import Operations.StringOperations;
import Operations.XMLOperations;
import com.jcabi.xml.XML;

import java.io.*;
import java.util.*;

public class Events {

    static String inputFiguredataXMLPath = "input/figuredata.xml";
    static String inputFiguremapJSONPath = "input/FigureMap.json";
    static String inputFigureDataJSONPath = "input/FigureData.json";
    static String inputJsonFigureMap = StringOperations.readFile(inputFiguremapJSONPath);
    static String inputJsonFigureData = StringOperations.readFile(inputFigureDataJSONPath);

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
                matchXMLDataAndGenerateSql(inputFiguremapXMLPath, inputJsonFigureMap);

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
                //matchJSONDataAndGenerateSql(inputFiguremapJSONPath);

                for (int i = 0; i < JsonOperations.getJsonArrayLength(inputFigureMap,"FigureMap"); i++) {
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

    private static void matchXMLDataAndGenerateSql(String figureMapXMLPath, String figureMapJSONPath) throws FileNotFoundException {
        HashMap<String, ArrayList<String>> figureMapXMLDataList = XMLOperations.getFigureMapValues(figureMapXMLPath);
        System.out.println("figureMapXMLDataList = " + figureMapXMLDataList);
        Map<String, ArrayList<String>> figureMapJSONSDataList = JsonOperations.getFigureMapTypeObjectID(figureMapJSONPath);
        List<String> keylist=new ArrayList<>(figureMapJSONSDataList.keySet());

        List<String> itemNameList = StringOperations.getItemFileNames();
        List<String> keyNameList = new ArrayList<>();

        for (String s : itemNameList) {
            String figureItemName = GetConfig.ini.get("Add_To_Clothing_Catalog_&_Market_Catalog", s);
            keyNameList.add(figureItemName);

        }

        for (String s: keylist) {
            if (figureMapXMLDataList.containsKey(s)) {
                figureMapJSONSDataList.remove(s);
            }
        }

        if (figureMapJSONSDataList.size()>0) {
            figureMapXMLDataList.putAll(figureMapJSONSDataList);
        }

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
                if (figureMapXMLDataList.get(figureItemName) != null) {
                    ArrayList<String> figurePartIDs = figureMapXMLDataList.get(figureItemName);
                    System.out.println("figurePartIDs = " + figurePartIDs);

                    HashMap<String, ArrayList<String>> figureDataXMLSets = XMLOperations.getFigureDataValues(inputFiguredataXMLPath);
                    HashMap<String, ArrayList<String>> figureDataJSONSets=JsonOperations.getFigureDataValues(inputJsonFigureData);
                    figureDataXMLSets.putAll(figureDataJSONSets);
                    System.out.println("figureDataXMLSets = " + figureDataXMLSets);

                    List<String> keyLists = new ArrayList<>(figureDataXMLSets.keySet());
                    System.out.println(keyLists);
                    for (int i = 0; i < keyLists.size(); i++) {
                        System.out.println(keyLists.get(i));
                        if (figureDataXMLSets.get(keyLists.get(i)).containsAll(figurePartIDs)) {
                            int customParam = Integer.parseInt(keyLists.get(i));

                            generateSQL(s, customParam, "output/sqls/catalog_items.sql", "output/sqls/items_base.sql", "output/sqls/catalog_clothing.sql", randomItemID);
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




    private static void generateSQL(String itemName, int figuredataSetID, String catalogSQLFilePath, String itemsBaseSQLFilePath, String catalogClothingSQLFilePath, long randomIDNumber) {

        try {

            Writer writeItems = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(catalogSQLFilePath, true), "UTF-8"));
            writeItems.write("INSERT INTO `catalog_items` (`id`, `page_id`, `item_ids`, `catalog_name`)VALUES ('" + randomIDNumber + "', 'PAGE_ID', '" + randomIDNumber + "', '" + itemName + "');\n");

            Writer writeCatalogItems = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(itemsBaseSQLFilePath, true), "UTF-8"));
            writeCatalogItems.write("INSERT INTO `items_base` (`id`,`item_name`,`public_name`,`stack_height`,`allow_stack`,`sprite_id`,`interaction_type`,`interaction_modes_count`)VALUES ('" + randomIDNumber + "', '" + itemName + "', '" + itemName + "_name', '1', '1', '" + randomIDNumber + "', 'default', '1');\n");

            Writer writeCatalogClothing = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(catalogClothingSQLFilePath, true), "UTF-8"));
            writeCatalogClothing.write("INSERT INTO `catalog_clothing` (`name`,`set_id`)VALUES ('" + itemName + "', '" + figuredataSetID + "');\n");

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


