package dk.sdu.mmmi.modulemon.Common.services;

/**
 * This interface is used for handling the game. This includes creating and adding data or removing it.
 */
public interface IGamePluginService {
    /**
     * This method initializes and adds the data.
     * @param gameData float Delta, int Height, int Width
     * @param world Vector data
     */
   // void start(GameData gameData, World world);

    /**
     * This method removes and cleans up the data on uninstall
     * @param gameData float Delta, int Height, int Width
     * @param world Vector data
     */
    //void stop(GameData gameData, World world);
}
