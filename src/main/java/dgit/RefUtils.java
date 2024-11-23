package dgit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RefUtils {

    public static String getHeadBranch() {
        try {
            File head = new File(".dgit/HEAD");
            String firstLine = Files.readString(head.toPath());
            String prefix = "ref: ";
            int index = firstLine.indexOf(prefix);
            String path = firstLine.substring(index + prefix.length()).trim();

            File file = new File(".dgit/" + path);

            if(file.exists()){
            return Files.readString(file.toPath());
            }
            return "";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
