package battletank.controls;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ActionKeyParser {

    private JsonParser jsonParser;

    ActionKeyParser(){
        jsonParser=new JsonParser();
    }

    public Map<Action,List<String>> getControlMapping(){
        String jsonSettings = getFile("controls.json");
        Type type = new TypeToken<Map<Action,List<String>>>(){}.getType();
        HashMap<Action,List<String>> map = new Gson().fromJson(jsonSettings,type);
        return map;
    }

    private String getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return result.toString();
    }
}