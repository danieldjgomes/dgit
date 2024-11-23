package dgit;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static String getType(File file) {
        return file.isDirectory() ? "tree" : "blob";
    }
    public static File createFileIfNotExist(String path){
        try{
            File index = new File(path);
            if(!index.exists()){
                index.createNewFile();
            }
            return index;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File createFolderIfNotExist(String path){
        File index = new File(path);
        if(!index.exists()){
            index.mkdir();
        }
        return index;
    }

}
