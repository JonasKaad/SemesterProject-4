package dk.sdu.mmmi.modulemon.BundleController;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ResourceUtil {

    private String resourceName;
    private byte[] cachedBytes;

    public ResourceUtil(String resourceName) {
        this.resourceName = resourceName;
    }

    public byte[] readBytes() {
        if (cachedBytes != null) {
            return cachedBytes;
        }
        //From https://stackoverflow.com/a/1264737
        InputStream is = this.read();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[(int) Math.pow(2, 20)]; //Buffer about 1 MB size

        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        } catch (Exception _) {
            System.out.println("Loading bytes failed for: " + this.resourceName);
        }

        cachedBytes = buffer.toByteArray();
        return cachedBytes;
    }

    public InputStream read() {
        return ResourceUtil.class.getResourceAsStream(this.resourceName);
    }
}
