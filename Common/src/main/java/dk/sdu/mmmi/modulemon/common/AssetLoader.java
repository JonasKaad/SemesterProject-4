package dk.sdu.mmmi.modulemon.common;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;
import java.util.Map;

public class AssetLoader {


    private static final Object _instanceLock = new Object();
    private static AssetLoader instance;

    Map<String, Object> assetMap;


    public Texture getTextureAsset(String pathToAsset, Class classForAssetResources) {
        String key = pathToAsset + "_" + classForAssetResources.getName();
        //T asset;
        if(!assetMap.containsKey(key)){
            Object value = new Texture(new OSGiFileHandle(pathToAsset, classForAssetResources));
            assetMap.put(key, value);
        }
        Object retrieved = assetMap.get(key);
        return (Texture) retrieved;
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
