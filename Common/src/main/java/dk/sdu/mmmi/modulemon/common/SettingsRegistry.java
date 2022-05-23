package dk.sdu.mmmi.modulemon.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SettingsRegistry {

    private static final Object instanceLock = new Object();
    private static SettingsRegistry instance;
    private Map<UUID, String> settingsMap;

    private UUID music_volume = UUID.randomUUID();
    private UUID sound_volume = UUID.randomUUID();
    private UUID ai_alphaBeta = UUID.randomUUID();
    private UUID ai_processing_time = UUID.randomUUID();
    private UUID rectangle_style = UUID.randomUUID();
    private UUID battle_theme = UUID.randomUUID();
    private SettingsRegistry(){
        settingsMap = new HashMap<>();
        populateSettings();
    }


    /**
     * Registers and populates the settings map. This has to be done manually based on the settings that gets put into
     * the settings.json file. The key is a random UUID, with the value being the setting found in the json file.
     */
    private void populateSettings(){
        settingsMap = new HashMap<>();
        settingsMap.put(music_volume, "musicVolume");
        settingsMap.put(sound_volume, "soundVolume");
        settingsMap.put(ai_alphaBeta, "AI alpha-beta pruning");
        settingsMap.put(ai_processing_time, "AI processing time");
        settingsMap.put(rectangle_style, "personaRectangles");
        settingsMap.put(battle_theme, "battleMusicTheme");
    }

    /*
    All the methods below return their corresponding name of a setting, which is the on found in the settings.json.
    So when we want a method to load the Music volume setting, we don't need to know what it's called in the individual
    component, we just use this registry and call the relevant method, in this case "getMusicVolumeSetting()".
     */
    public String getMusicVolumeSetting(){
        return settingsMap.get(music_volume);
    }
    public String getSoundVolumeSetting(){
        return settingsMap.get(sound_volume);
    }

    public String getAIAlphaBetaSetting(){
        return settingsMap.get(ai_alphaBeta);
    }

    public String getAIProcessingTimeSetting(){
        return settingsMap.get(ai_processing_time);
    }

    public String getRectangleStyleSetting(){
        return settingsMap.get(rectangle_style);
    }

    public String getBattleMusicThemeSetting(){
        return settingsMap.get(battle_theme);
    }


    public static SettingsRegistry getInstance(){
        if(instance == null){
            synchronized (instanceLock){
                if(instance == null){
                    instance = new SettingsRegistry();
                }
            }
        }
        return instance;
    }


}
