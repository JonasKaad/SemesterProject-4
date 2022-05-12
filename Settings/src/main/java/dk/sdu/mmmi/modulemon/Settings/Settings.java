package dk.sdu.mmmi.modulemon.Settings;

import dk.sdu.mmmi.modulemon.common.services.IGameSettings;

import java.util.HashMap;
import java.util.Map;

public class Settings implements IGameSettings {
    private Map<String, Object> settingsMap = new HashMap<>();

    @Override
    public void setSetting(String setting, Object value) {
        settingsMap.put(setting, value);
    }

    @Override
    public Object getSetting(String setting) {
        if (!settingsMap.containsKey(setting)) {
            return null;
        }
        return settingsMap.get(setting);
    }
}
