/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    String uuid;

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object obj) {
        return uuid.equals(((Resume)obj).uuid);
    }
}
