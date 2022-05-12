package dk.sdu.mmmi.modulemon.Settings;

import dk.sdu.mmmi.modulemon.common.services.IGameSettings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Settings implements IGameSettings {

    private SettingsHandler settingsHandler;

    public Settings(){
        try {
            settingsHandler = new SettingsHandler("settings.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setSetting(String setting, Object value) {
        settingsHandler.setSetting(setting, value);
    }

    @Override
    public Object getSetting(String setting) {
        return settingsHandler.getSetting(setting);
    }
}
