package dgit.builder;

import dgit.FilePrinter;

import java.io.File;

public class HeadService {

    public static void changeHead(String branchName){
        File head = new File(".dgit/HEAD");
        FilePrinter.writeFile(head,"ref: refs/heads/" + branchName);
        System.out.println("Current working branch: " + branchName);

    }
}
