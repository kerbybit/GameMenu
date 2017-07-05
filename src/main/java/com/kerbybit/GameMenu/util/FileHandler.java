package com.kerbybit.GameMenu.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kerbybit.GameMenu.Main;
import com.kerbybit.GameMenu.command.CommandMenu;
import net.minecraft.util.EnumChatFormatting;

import java.io.*;

public class FileHandler {
    public static JsonObject loadFile() throws IOException {
        StringBuilder jsonString = new StringBuilder();

        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(Main.dir+ "menu.json"),"UTF-8"));
        while ((line = bufferedReader.readLine()) != null) {
            jsonString.append(line);
        }
        bufferedReader.close();

        return new JsonParser().parse(jsonString.toString()).getAsJsonObject();
    }

    public static void checkDir() {
        File dir = new File(Main.dir);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                CommandMenu.showMessage(EnumChatFormatting.RED + "Unable to create GameMenu files!");
            }
        }
    }

    public static String getValue(JsonObject json, String value) {
        try {
            JsonObject obj = json;
            String[] seg = value.split("\\.");
            String returnString = "null";
            for (String element : seg) {
                if (obj != null) {
                    JsonElement ele = obj.get(element);
                    if (!ele.isJsonObject()) {
                        returnString = ele + "";
                    } else {
                        obj = ele.getAsJsonObject();
                        returnString = obj + "";
                    }
                } else {
                    return "null";
                }
            }
            if (returnString.startsWith("\"") && returnString.endsWith("\"")) {
                returnString = returnString.substring(1, returnString.length()-1);
            }
            return returnString;
        } catch (Exception e) {
            return "null";
        }
    }

    public static void generateFile() throws IOException {
        PrintWriter writer = new PrintWriter(Main.dir + "menu.json", "UTF-8");
        writer.println("{\n" +
                "   \"settings\":{\n" +
                "       \"particles\":{\n" +
                "           \"enabled\":true,\n" +
                "           \"particle size\":2,\n" +
                "           \"particle packing\":5,\n" +
                "           \"particle color\":{\n" +
                "               \"rainbow\":true,\n" +
                "               \"rainbow stripes\":10,\n" +
                "               \"rainbow speed\":50,\n" +
                "               \"red\":255,\n" +
                "               \"green\":255,\n" +
                "               \"blue\":255\n" +
                "           },\n" +
                "           \"line thickness\":1,\n" +
                "           \"line max length\":50,\n" +
                "           \"line color\":{\n" +
                "               \"rainbow\":false,\n" +
                "               \"rainbow stripes\":10,\n" +
                "               \"rainbow speed\":50,\n" +
                "               \"red\":255,\n" +
                "               \"green\":255,\n" +
                "               \"blue\":255\n" +
                "           }\n" +
                "       },\n" +
                "       \"animation speed\":5,\n" +
                "       \"open with hypixel compass\":true,\n" +
                "       \"check extra fast (may cause lag)\":true\n" +
                "   },\n" +
                "   \"top\":{\n" +
                "       \"name\":\"&6Prototype Lobby\",\n" +
                "       \"color\":\"#000000\",\n" +
                "       \"command\":\"ptl\"\n" +
                "   },\n" +
                "   \"left\":{\n" +
                "       \"name\":\"&3&lPvP Minigames\",\n" +
                "       \"color\":\"#00102b\",\n" +
                "       \"buttons\":[\n" +
                "           {\n" +
                "               \"name\":\"Blitz\",\n" +
                "               \"command\":\"blitz\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"diamond_sword\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"SkyWars\",\n" +
                "               \"command\":\"skywars\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"ender_eye\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"SkyClash\",\n" +
                "               \"command\":\"skyclash\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"fire_charge\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"BedWars\",\n" +
                "               \"command\":\"bedwars\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"bed\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"Mega Walls\",\n" +
                "               \"command\":\"mw\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"soul_sand\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"Crazy Walls\",\n" +
                "               \"command\":\"crazywalls\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"skull\",\n" +
                "                   \"meta\":3,\n" +
                "                   \"owner\":\"kerbybit\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"UHC Champions\",\n" +
                "               \"command\":\"uhc\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"golden_apple\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"Speed UHC\",\n" +
                "               \"command\":\"speeduhc\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"golden_carrot\"\n" +
                "               }\n" +
                "           }\n" +
                "       ]\n" +
                "   },\n" +
                "   \"right\":{\n" +
                "       \"name\":\"&2&lArcade Minigames\",\n" +
                "       \"color\":\"#001c03\",\n" +
                "       \"buttons\":[\n" +
                "           {\n" +
                "               \"name\":\"Arcade Games\",\n" +
                "               \"command\":\"arcade\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"slime_ball\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"Housing\",\n" +
                "               \"command\":\"home\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"dark_oak_door\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"Smash Heroes\",\n" +
                "               \"command\":\"smash\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"skull\",\n" +
                "                   \"meta\":3,\n" +
                "                   \"owner\":\"spiderman\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"TnT Games\",\n" +
                "               \"command\":\"tnt\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"tnt\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"Warlords\",\n" +
                "               \"command\":\"warlords\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"stone_axe\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"Cops v Crims\",\n" +
                "               \"command\":\"cvc\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"iron_bars\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"Classics\",\n" +
                "               \"command\":\"classic\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"jukebox\"\n" +
                "               }\n" +
                "           },{\n" +
                "               \"name\":\"Murder\",\n" +
                "               \"command\":\"ptl\",\n" +
                "               \"icon\":{\n" +
                "                   \"item\":\"compass\"\n" +
                "               }\n" +
                "           }\n" +
                "       ]\n" +
                "   },\n" +
                "   \"bottom\":{\n" +
                "       \"name\":\"&cExit\",\n" +
                "       \"color\":\"#000000\",\n" +
                "       \"command\":\"*exit*\"\n" +
                "   }\n" +
                "}");
        writer.close();
    }
}
