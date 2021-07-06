import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
//        try
//        {
//            File file = new File(".\\.gitignore");
//            System.out.println(file.getCanonicalFile().toString());
//            File dir = new File("./src/com/urise/webapp");
//            System.out.println(dir.isDirectory());
//            String[] list = dir.list();
//            if (!Objects.isNull(list)) {
//                for(String name : list) {
//                    System.out.println(name);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File sourceDir = new File("./");
        String[] list = sourceDir.list();
        for (String name : list){
            System.out.println(name);
        }
    }
}
