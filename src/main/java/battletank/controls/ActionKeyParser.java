package battletank.controls;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ActionKeyParser {

    private JsonParser jsonParser;

    public ActionKeyParser() {
        jsonParser = new JsonParser();
    }

    public Map<Action, List<String>> getActionMapping() {
        String jsonSettings = getFile("controls.json");
        Type type = new TypeToken<Map<Action, List<String>>>() {
        }.getType();
        HashMap<Action, List<String>> map = new Gson().fromJson(jsonSettings, type);
        return map;
    }

    public Map<String, Action> getControlMapping() {

        HashMap<String, Action> controlMapping = new HashMap<String, Action>();

        Map<Action, List<String>> actionMapping;

        actionMapping = getActionMapping();

        for (Action a : actionMapping.keySet()) {
            List<String> actionKeys = actionMapping.get(a);

            for (String key : actionKeys) {
                controlMapping.put(key, a);
            }
        }

        return controlMapping;
    }


    private String getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream(fileName);

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result.toString();
    }


}