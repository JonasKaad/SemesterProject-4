package dk.sdu.mmmi.modulemon.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.HashMap;
import java.util.Map;

public class AssetLoader {


    private static final Object _instanceLock = new Object();
    private static AssetLoader instance;

    private Map<String, Object> assetMap;


    public Texture getTextureAsset(String pathToAsset, Class classForAssetResources) {
        String key = pathToAsset + "_" + classForAssetResources.getName();
        if(!assetMap.containsKey(key)){
            Object value = new Texture(new OSGiFileHandle(pathToAsset, classForAssetResources));
            assetMap.put(key, value);
        }
        Object retrieved = assetMap.get(key);
        return (Texture) retrieved;
    }

    public Image getImageAsset(String pathToAsset, Class classForAssetResources) {
        String key = pathToAsset + "_" + classForAssetResources.getName()+".image";
        if(!assetMap.containsKey(key)){
            Object value = new Image(new Texture(new OSGiFileHandle(pathToAsset, classForAssetResources)));
            assetMap.put(key, value);
        }
        Object retrieved = assetMap.get(key);
        return (Image) retrieved;
    }

    public Sound getSoundAsset(String pathToAsset, Class classForAssetResources) {
        String key = pathToAsset + "_" + classForAssetResources.getName();
        if(!assetMap.containsKey(key)){
            Object value = Gdx.audio.newSound(new OSGiFileHandle(pathToAsset, classForAssetResources));
            assetMap.put(key, value);
        }
        Object retrieved = assetMap.get(key);
        return (Sound) retrieved;
    }

    public Music getMusicAsset(String pathToAsset, Class classForAssetResources) {
        String key = pathToAsset + "_" + classForAssetResources.getName();
        if(!assetMap.containsKey(key)){
            Object value = Gdx.audio.newMusic(new OSGiFileHandle(pathToAsset, classForAssetResources));
            assetMap.put(key, value);
        }
        Object retrieved = assetMap.get(key);
        return (Music) retrieved;
    }

    private AssetLoader(){
        assetMap = new HashMap<>();
    }


    public static AssetLoader getInstance(){
        if(instance == null){
            synchronized (_instanceLock){
                if(instance == null){
                    instance = new AssetLoader();
                }
            }
        }
        return instance;
    }
}
