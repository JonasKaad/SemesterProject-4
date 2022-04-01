package dk.sdu.mmmi.modulemon.BattleScene;

import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class OSGiFileHandle extends FileHandle {

    private String resourceName;
    public OSGiFileHandle(String resourceName, Class classToPullFrom){
        super(String.valueOf(classToPullFrom.getResource(resourceName)));
        this.resourceName = resourceName;
    }

    @Override
    public InputStream read() {
        return OSGiFileHandle.class.getResourceAsStream(this.resourceName);
    }
}
