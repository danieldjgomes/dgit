package dgit.builder;

import dgit.FileCompressor;

import java.io.*;
import java.util.Base64;
import java.util.zip.Deflater;

public class BlobRegister {

    public static void registryBlob(File file) {

        String sha1 = FileCompressor.sha1(file);
        String deflated = deflateFile(file);
        File indexFolder = new File(".dgit/objects/" + sha1.substring(0, 2));
        if (!indexFolder.exists()) {
            indexFolder.mkdirs();
        }
        File deflatedFile = new File(".dgit/objects/" + sha1.substring(0, 2) + "/" + sha1.substring(2));
        try {
            deflatedFile.createNewFile();
            FileWriter deflatedWriter = new FileWriter(deflatedFile);
            deflatedWriter.write(deflated);
            deflatedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String deflateFile(File file) {
        byte[] buffer = new byte[8192];
        int count;

        // Read file content into a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            while ((count = bis.read(buffer)) > 0) {
                baos.write(buffer, 0, count);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] fileData = baos.toByteArray();

        // Deflate (compress) the file data
        Deflater deflater = new Deflater();
        deflater.setInput(fileData);
        deflater.finish();

        ByteArrayOutputStream deflatedOutput = new ByteArrayOutputStream();
        byte[] deflateBuffer = new byte[8192];
        while (!deflater.finished()) {
            int deflatedCount = deflater.deflate(deflateBuffer);
            deflatedOutput.write(deflateBuffer, 0, deflatedCount);
        }
        deflater.end();

        // Convert deflated data to Base64 for easier representation
        return Base64.getEncoder().encodeToString(deflatedOutput.toByteArray());

    }
}
