package battletank.controls;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ActionKeyParser {

    JsonParser jsonParser;

    ActionKeyParser(){
        jsonParser=new JsonParser();
    }

    public List<ActionListener> createActionListenersFromDefault(){
        String jsonSetting = getFile("controls.json");
        JsonElement jsonTree = jsonParser.parse(jsonSetting);
        //TODO: Implements Control
        
        return null;
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