import com.urise.webapp.exception.StorageException;

public class MainException {
    public static void main(String[] args) {
        if (true) throw new StorageException("asfd","asdf");
        System.out.println("asdfasdf");
    }
}
