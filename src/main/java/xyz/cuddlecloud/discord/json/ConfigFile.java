package xyz.cuddlecloud.discord.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.util.Reference;
import xyz.cuddlecloud.javax.logging.Loggy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class ConfigFile {

    private static final Map<String, String> jsonMap = new HashMap<>();
    private static final JSONObject j1 = new JSONObject();
    private static final JSONObject j2 = new JSONObject();
    private static final JSONObject j3 = new JSONObject();
    private static final JSONObject j4 = new JSONObject();
    private static JSONArray ja;

    private ConfigFile() {}

    public static void setup() {
        readFile();
        System.out.println(Reference.VERSIONS + " : " + getVersion());
        if(!(isVersionEqual())) setOldDataForJsonObject();
    }

    private static void setNewDataForJsonObject() {
        j1.put("version", Reference.VERSIONS);

        j2.put("botToken", "{botToken}");

        j3.put("roldId", "{roldId}");

        j4.put("channelIdForCreateRoom", "{channelIdForCreateRoom}");
        j4.put("categoryIdForCreateRoom", "{categoryIdForCreateRoom}");

        ja = new JSONArray();
        ja.add(j1);
        ja.add(j2);
        ja.add(j3);
        ja.add(j4);

        jsonMap.put("version", Reference.VERSIONS);
        jsonMap.put("botToken", "{botToken}");
        jsonMap.put("roldId", "{roldId}");
        jsonMap.put("categoryIdForCreateRoom", "{categoryIdForCreateRoom}");
        jsonMap.put("channelIdForCreateRoom", "{channelIdForCreateRoom}");

        writeFile();
    }

    private static void setOldDataForJsonObject() {
        j1.put("version", Reference.VERSIONS);

        j2.put("botToken", getBotToken());

        j3.put("roldId", getRoleId());

        j4.put("categoryIdForCreateRoom", getCategoryIdForCreateRoom());
        j4.put("channelIdForCreateRoom", getChannelIdForCreateRoom());

        ja = new JSONArray();
        ja.add(j1);
        ja.add(j2);
        ja.add(j3);
        ja.add(j4);

        writeFile();
    }

    private static void writeFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJsonString = gson.toJson(ja);

        File file = new File(Reference.directory.get() + "\\config.json");

        if(file.renameTo(new File(Reference.directory.get() + "\\config.json.old"))) {
            Discord.loggy.log(Loggy.Level.TRACE, file + " rename success.");
        }else {
            Discord.loggy.log(Loggy.Level.TRACE, file + " can't rename");
        }

        try(FileWriter wr = new FileWriter(Reference.directory.get() + "\\config.json")) {
            wr.write(prettyJsonString);
            wr.flush();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFile() {
        JSONParser p = new JSONParser();
        try(FileReader r = new FileReader(Reference.directory.get() + "\\config.json")) {
            JSONArray jsonArray = (JSONArray) p.parse(r);

            for(Object o : jsonArray) {
                JSONObject jo = (JSONObject) o;
                for(Object key : jo.keySet()) {
                    String keyStr = (String) key;
                    String valueStr = (String) jo.get(keyStr);
                    jsonMap.put(keyStr, valueStr);
                }
            }
        } catch (FileNotFoundException e) {
            setNewDataForJsonObject();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isVersionEqual() {
        return (Reference.VERSIONS).equalsIgnoreCase(getVersion());
    }

    public static String getVersion() {
        return jsonMap.get("version");
    }

    public static String getBotToken() {
        return jsonMap.get("botToken");
    }

    public static String getRoleId() {
        return jsonMap.get("roldId");
    }

    public static String getChannelIdForCreateRoom() {
        return jsonMap.get("channelIdForCreateRoom");
    }

    public static String getCategoryIdForCreateRoom() {
        return jsonMap.get("categoryIdForCreateRoom");
    }

    public static void setBotToken(String s) {
        jsonMap.put("botToken", s);
        setOldDataForJsonObject();
    }

    public static void setRoleId(String s) {
        jsonMap.put("roldId", s);
        setOldDataForJsonObject();
    }

    public static void setChannelIdForCreateRoom(String s) {
        jsonMap.put("channelIdForCreateRoom", s);
        setOldDataForJsonObject();
    }

    public static void setCategoryIdForCreateRoom(String s) {
        jsonMap.put("categoryIdForCreateRoom", s);
        setOldDataForJsonObject();
    }
}
