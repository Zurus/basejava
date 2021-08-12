import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        try {
            File file = new File(".\\.gitignore");
            System.out.println(file.getCanonicalFile().toString());
            File dir = new File("./src/com/urise/webapp");
            System.out.println(dir.isDirectory());
            String[] list = dir.list();
            if (!Objects.isNull(list)) {
                for (String name : list) {
                    System.out.println(name);
                }
            }
            printDirectoryDeeply(dir, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printDirectoryDeeply(File dir, String sepr) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(sepr + "File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(sepr + "Directory " + file.getName());
                    printDirectoryDeeply(file, sepr+"\t");
                }
            }
        }
    }
}
