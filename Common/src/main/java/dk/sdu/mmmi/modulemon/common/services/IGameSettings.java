package dk.sdu.mmmi.modulemon.common.services;

public interface IGameSettings {
    void setSetting(String setting, Object value);
    Object getSetting(String setting);
}
