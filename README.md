![enter image description here](https://i.hizliresim.com/cfxyi3a.gif)
-
Tool to help you add clothes to your hotel. This tool adds the data of the clothes you want to add to your existing hotel data.

### Note
This tool works with both nitro and flash data and does not add the clothing to your hotel directly. It provides you with the necessary data (sql,xml,json) so that you can add clothing to hotel.

##  Requirements

- JAVA 8

## Things you should know

The data required to add Clothing to a Hotel are as follows;
- Clothing figure `swf`/`nitro` file 
- Figuredata `xml`/`json` data  
- Figuremap `xml`/`json` data 

*If you want to add the clothes to the market catalog, you also need these;*
- Clothing furni item  `swf`/`nitro` file 
- catalog_clothing `sql✅` data
- catalog_item `sql✅` data
- items_base `sql✅` data 
- Furnidata `xml✅`/`json✅` data

So what will this tool do for you? When you provide the following data, the tool will add the provided data to your existing hotel data and the tool will provide for you all the missing requirements mentioned in ✅ above.

required data you need to provide;
- `swf` or `nitro` figure files of the clothing to be added.
- `xml` or `json` figuremap data of the clothing to be added.
- `xml` or `json` figuredata of the clothing to be added.
- `swf` or `nitro`  furni item files of the clothing to be added (*if you have and want to add the clothing to the market catalogue*).

#### Difference between figure and figure furni item.
![enter image description here](https://i.hizliresim.com/1mdr4a7.png)

It is the figure item we see in the clothing catalog on the **left**. 
The one on the **right** is the figure furni item that you can buy from the catalog and put in the room. Both have separate files.

They both have separate files and there is sql configuration in the game that connects these two objects.

## Usage
#### 1- First, place your existing hotel data in the [backup folder](https://github.com/sSelmann/Clothing-Loader/tree/master/resource/backupfiles). Delete the sample backup files and add your own.

files you need to add:
|                |For Flash                          |For Nitro|
|----------------|-------------------------------|-----------------------------|
|Figuremap|`figuremap.xml`            |`FigureMap.json`            |
|Figuredata|`figuredata.xml`            |`FigureData.json`            |

Depending on the data you have, put them there. If you want to add the clothing to the flash hotel and you have the xml data, put the figuremap.xml and figuredata.xml data in the folder. **But** that doesn't mean you can't put the json files there at the same time.

**2**- Then add the figuremap data of the clothing to be added to the figuremap file.

> **Note:** All examples to be mentioned are available in the [resource](https://github.com/sSelmann/Clothing-Loader/tree/master/resource)
> file.


figuremap `xml` data example:

    <lib id="shirt_U_starryjumper" revision="68416">  
     <part id="4660" type="ch"/>  
     <part id="4660" type="ls"/>  
     <part id="4660" type="rs"/>  
     <part id="4661" type="ch"/>  
    </lib>
If you have `xml` data, paste data between the `<map> ..paste here.. </map>` tags inside the [figuremap.xml](https://github.com/sSelmann/Clothing-Loader/blob/master/resource/input/figuremap.xml) file.

figuremap `json` data example:

    {  
      "id": "hat_U_straw2",  
      "revision": 35467,  
      "parts": [  
        {  
          "id": 2588,  
      "type": "ha"  
      },  
      {  
          "id": 2589,  
      "type": "ha"  
      }  
      ]  
    }

For the `json` data, put it in the `{"FigureMap":[ ..paste here.. ]}` array inside the [FigureMap.json](https://github.com/sSelmann/Clothing-Loader/blob/master/resource/input/FigureMap.json) file.

**3**- Add the figuredata of the clothing to be added to the figuredata file.

figuredata `xml` example:

    <set id="5014" gender="U" club="0" colorable="1" selectable="1" preselectable="0" sellable="1">  
     <part id="4660" type="ch" colorable="1" index="0" colorindex="1"/>  
     <part id="4660" type="ls" colorable="1" index="0" colorindex="1"/>  
     <part id="4660" type="rs" colorable="1" index="0" colorindex="1"/>  
     <part id="4661" type="ch" colorable="0" index="1" colorindex="0"/>  
     <part id="4661" type="ls" colorable="0" index="1" colorindex="0"/>  
     <part id="4661" type="rs" colorable="0" index="1" colorindex="0"/>  
    </set>
If you have `xml` data, paste data between the `<sets> ..paste here.. </sets>` tags inside the [figuredata.xml](https://github.com/sSelmann/Clothing-Loader/blob/master/resource/input/figuredata.xml) file.

figuredata `json` example:

    {  
      "id": 3347,  
      "gender": "U",  
      "club": 0,  
      "colorable": true,  
      "selectable": true,  
      "preselectable": false,  
      "sellable": false,  
      "parts": [  
        {  
          "id": 2588,  
      "type": "ha",  
      "colorable": true,  
      "index": 0,  
      "colorindex": 1  
      },  
      {  
          "id": 2589,  
      "type": "ha",  
      "colorable": true,  
      "index": 1,  
      "colorindex": 2  
      }  
      ],  
      "hiddenLayers": [  
        {  
          "partType": "hrb"  
      }  
      ]  
    }

For the `json` data, put it in the `{"FigureData":[ ..paste here.. ]}` array inside the [FigureData.json](https://github.com/sSelmann/Clothing-Loader/blob/master/resource/input/FigureData.json) file.

**3**- Add the figure furni item file to itemfiles folder.
***If you don't have the figure furni item swf/nitro file or don't want to add the clothes to the market catalog, you can skip this step.***

Put the figure furni item in the [itemFiles](https://github.com/sSelmann/Clothing-Loader/tree/master/resource/itemfiles) folder.

example `nitro`/`swf` figure furni item file name:

    clothing_starryjumper.swf
    clothing_straw2.nitro

**4**- Match item and figure files.

The tool cannot know which item will be the item of which figure. So we have to specify that in the config.ini file.

For example, if the figure file of the clothing_starryjumper item is shirt_U_starryjumper, we need to specify it like this in the config.ini:

    [Add_To_Clothing_Catalog_&_Market_Catalog]
    clothing_starryjumper=shirt_U_starryjumper
    
 
After doing that, everything is ready! just run the run.bat file.

## Outputs
You can see the new figuredata and figuremap files added to your existing file data in the output file.

    output/xml/figuremap.xml
    output/xml/figuredata.xml
    output/json/FigureMap.xml
    output/json/FigureData.xml

however, if you want to add clothing to the market catalog, you can see the output files of the sql and furnidata codes:

    output/xml/furnidata.xml
    output/json/FurnitureData.json
    output/sql/catalog_items.sql
    output/sql/catalog_clothing.sql
    output/sql/items_base.sql
Run the sql files in the database. Don't forget to edit the PAGE_ID in the catalog_items.sql file.

You can replace the **Figuredata** and **Figuremap** `xml`/`json` files with your existing hotel data.

You can add your data in FurnitureData.json and furnidata.xml to your existing Furnituredata and furnidata files.
