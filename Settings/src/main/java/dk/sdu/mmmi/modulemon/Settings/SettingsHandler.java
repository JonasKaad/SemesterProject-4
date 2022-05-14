package dk.sdu.mmmi.modulemon.Settings;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SettingsHandler {

    private Map<String, Object> configCache;
    private File file;

    public SettingsHandler(String filePath) throws IOException {

        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        this.file = file;
        this.configCache = new HashMap<>();

        String contents = String.join("", Files.readAllLines(Paths.get(file.getAbsolutePath())));
        if(!contents.isEmpty()) {
            JSONObject config = new JSONObject(contents);
            for (Iterator<String> it = config.keys(); it.hasNext(); ) {
                String key = it.next();
                if (config.get(key) instanceof String) {
                    configCache.put(key, config.get(key));
                } else if (config.get(key) instanceof Integer) {
                    configCache.put(key, config.get(key));
                } else if (config.get(key) instanceof Boolean) {
                    configCache.put(key, config.get(key));
                }
            }
        }
    }

    public boolean hasSetting(String key){
        return this.configCache.containsKey(key);
    }

    public Object getSetting(String key){
        return this.configCache.get(key);
    }
    public void setSetting(String key, Object value){
        this.configCache.put(key, value);
        try {
            save();
        } catch (IOException e) {
            System.out.println("Failed to save settings!! " + e.getMessage());
        }
    }

    private synchronized void save() throws IOException {
        JSONObject obj = new JSONObject();
        for(Map.Entry<String, Object> pair : this.configCache.entrySet()){
            obj.put(pair.getKey(), pair.getValue());
        }
        String contents = obj.toString(4);
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.file.getAbsolutePath()));
        writer.write(contents);
        writer.close();
    }
}