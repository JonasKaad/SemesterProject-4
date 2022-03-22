package dk.sdu.mmmi.modulemon.BattleScene;

import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class OSGiFileHandle extends FileHandle {

    private String resourceName;
    private byte[] cachedBytes;
    public OSGiFileHandle(String resourceName){
        super(String.valueOf(OSGiFileHandle.class.getResource(resourceName)));
        this.resourceName = resourceName;
    }

    @Override
    public byte[] readBytes() {
        if(cachedBytes != null){
            return cachedBytes;
        }
        //From https://stackoverflow.com/a/1264737
        InputStream is = this.read();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[(int)Math.pow(2,14)]; //Magical number, what?!

        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        }catch(Exception _){
            System.out.println("Loading bytes failed for: " + this.resourceName);
        }

        cachedBytes = buffer.toByteArray();
        return cachedBytes;
    }

    @Override
    public InputStream read() {
        return OSGiFileHandle.class.getResourceAsStream(this.resourceName);
    }
}
