package dgit;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.Deflater;

public class FileCompressor {

    public static String sha1(File file){
        byte[] buffer= new byte[8192];
        int count;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            while ((count = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : digest.digest()) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static String sha1(String input) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes = input.getBytes();  // Convert string to byte array

        // Update the digest with the byte array of the string
        digest.update(bytes);

        // Get the final hash
        byte[] hash = digest.digest();

        // Convert the hash bytes to a hexadecimal string
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
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
