package xyz.cuddlecloud.discord.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.cuddlecloud.discord.Discord;
import xyz.cuddlecloud.discord.util.Reference;
import xyz.cuddlecloud.javax.logging.Loggy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class ConfigFile {

    private static final Map<String, String> jsonMap = new HashMap<>();
    private static final JsonObject jsonObject = new JsonObject();

    private ConfigFile() {}

    public static void setup() {
        readFile();
        Discord.loggy.log(Loggy.Level.TRACE, (Reference.VERSIONS + " : " + getVersion()));
        if(!(isVersionEqual())) setOldDataForJsonObject();
    }

    private static void setNewDataForJsonObject() {
        jsonObject.addProperty("version", Reference.VERSIONS);
        jsonObject.addProperty("botToken", "{botToken}");
        jsonObject.addProperty("roleId", "{roleId}");
        jsonObject.addProperty("channelIdForCreateRoom", "{channelIdForCreateRoom}");
        jsonObject.addProperty("categoryIdForCreateRoom", "{categoryIdForCreateRoom}");
        jsonObject.addProperty("activity", "Playing {activity}");

        jsonMap.put("version", Reference.VERSIONS);
        jsonMap.put("botToken", "{botToken}");
        jsonMap.put("roleId", "{roleId}");
        jsonMap.put("categoryIdForCreateRoom", "{categoryIdForCreateRoom}");
        jsonMap.put("channelIdForCreateRoom", "{channelIdForCreateRoom}");
        jsonMap.put("activity", "Playing {activity}");

        writeFile();
    }

    private static void setOldDataForJsonObject() {
        jsonObject.addProperty("version", Reference.VERSIONS);
        jsonObject.addProperty("botToken", getBotToken());
        jsonObject.addProperty("roleId", getRoleId());
        jsonObject.addProperty("channelIdForCreateRoom", getChannelIdForCreateRoom());
        jsonObject.addProperty("categoryIdForCreateRoom", getCategoryIdForCreateRoom());
        jsonObject.addProperty("activity", getActivity());

        writeFile();
    }

    private static void writeFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJsonString = gson.toJson(jsonObject);
        //System.out.println(prettyJsonString);
        File file = new File(Reference.directory.get() + "\\config.json");
        File oldFile = new File(Reference.directory.get() + "\\config.json.old");

        /*if(oldFile.delete()) {
            Discord.loggy.log(Loggy.Level.TRACE, oldFile + " removed");
        }else {
            Discord.loggy.log(Loggy.Level.TRACE, oldFile + " cant' remove");
        }*/

        if(file.renameTo(oldFile)) {
            Discord.loggy.log(Loggy.Level.TRACE, file + " rename success.");
        }else {
            Discord.loggy.log(Loggy.Level.TRACE, file + " can't rename");
        }

        try(FileWriter wr = new FileWriter(Reference.directory.get() + "\\config.json")) {
            wr.write(prettyJsonString);
            wr.flush();
        } catch (IOException e) {
            Discord.loggy.log(Loggy.Level.ERROR, ConfigFile.class.getSimpleName(), e.getClass().getSimpleName(), e);
        }
    }

    private static void readFile() {
        try(FileReader r = new FileReader(Reference.directory.get() + "\\config.json")) {
            JsonObject jso = JsonParser.parseReader(r).getAsJsonObject();

            for(Map.Entry<String, ?> entry : jso.entrySet()) {
                String keystr = entry.getKey();
                String valueStr = entry.getValue().toString().replace("\"", "");
                jsonMap.put(keystr, valueStr);
            }
        } catch (FileNotFoundException e) {
            setNewDataForJsonObject();
        } catch (IOException e) {
            Discord.loggy.log(Loggy.Level.ERROR, ConfigFile.class.getSimpleName(), e.getClass().getSimpleName(), e);
        }
    }

    public static boolean isVersionEqual() {
        return (Reference.VERSIONS).equalsIgnoreCase(getVersion());
    }

    public static String getVersion() {
        return jsonMap.get("version") == null ? "null" : jsonMap.get("version");
    }

    public static String getBotToken() {
        return jsonMap.get("botToken") == null ? "null" : jsonMap.get("botToken");
    }

    public static String getRoleId() {
        return jsonMap.get("roleId") == null ? "null" : jsonMap.get("roleId");
    }

    public static String getChannelIdForCreateRoom() {
        return jsonMap.get("channelIdForCreateRoom") == null ? "null" : jsonMap.get("channelIdForCreateRoom");
    }

    public static String getCategoryIdForCreateRoom() {
        return jsonMap.get("categoryIdForCreateRoom") == null ? "null" : jsonMap.get("categoryIdForCreateRoom");
    }

    public static String getActivity() {
        return jsonMap.get("activity") == null ? "null" : jsonMap.get("activity");
    }

    public static void setBotToken(String s) {
        jsonMap.put("botToken", s);
        setOldDataForJsonObject();
    }

    public static void setRoleId(String s) {
        jsonMap.put("roleId", s);
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

    public static void setActivity(String s) {
        jsonMap.put("activity", s);
        setOldDataForJsonObject();
    }
}
