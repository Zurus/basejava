package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurator {
    private static final File PROPS = new File("config\\properties.properties");
    private static final Configurator INSTANCE = new Configurator();

    private static final String URL_PROPERTIES = "db.url";
    private static final String USER_PROPERTIES = "db.user";
    private static final String PASSWORD_PROPERTIES = "db.password";

    private Properties properties = new Properties();
    private File storageDir;

    public static Configurator get() {
        return INSTANCE;
    }

    private Configurator() {
        try (InputStream is = new FileInputStream(PROPS)) {
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getUrl() {
        return getProperty(URL_PROPERTIES);
    }

    public String getUserName() {
        return getProperty(USER_PROPERTIES);
    }

    public String getPassword() {
        return getProperty(PASSWORD_PROPERTIES);
    }

    private String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
