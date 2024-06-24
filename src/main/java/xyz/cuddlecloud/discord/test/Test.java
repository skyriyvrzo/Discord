package xyz.cuddlecloud.discord.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import xyz.cuddlecloud.discord.util.Reference;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {

	static Map<String, String> jjjsssooonnn = new HashMap<>();

	public static void test() throws IOException {
		JSONObject j1 = new JSONObject();
		j1.put("version", Reference.VERSIONS);

		JSONObject j2 = new JSONObject();
		j2.put("botToken", "{botToken}");

		JSONObject j3 = new JSONObject();
		j3.put("roldId", "{roldId}");

		JSONObject j4 = new JSONObject();
		j4.put("categoryIdForCreateRoom", "{categoryIdForCreateRoom}");
		j4.put("channelIdForCreateRoom", "{channelIdForCreateRoom}");

		JSONArray ja = new JSONArray();
		ja.add(j1);
		ja.add(j2);
		ja.add(j3);
		ja.add(j4);

		/*ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File(Reference.directory.get() + "\\test.json"), ja);
		*/

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJsonString = gson.toJson(ja);

		try(FileWriter wr = new FileWriter(Reference.directory.get() + "\\gson.json")) {
			wr.write(prettyJsonString);
			wr.flush();
		}catch (Exception e){
			e.printStackTrace();
		}

		JSONParser parser = new JSONParser();
		try(FileReader reader = new FileReader(Reference.directory.get() + "\\gson.json")){
			JSONArray jsonArray = (JSONArray) parser.parse(reader);

			for(Object o : jsonArray) {
				JSONObject jsonObject = (JSONObject) o;
				for(Object key : jsonObject.keySet()) {
					String keyStr = (String) key;
					String valueStr = (String) jsonObject.get(keyStr);
					jjjsssooonnn.put(keyStr, valueStr);
				}
			}
		} catch (ParseException e) {
            throw new RuntimeException(e);
        }

		System.out.println(jjjsssooonnn.get("botToken"));
    }
}