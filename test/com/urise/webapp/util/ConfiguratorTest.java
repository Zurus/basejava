/*
 * @author DivaevAM
 * @since 06.10.2021
 */

package com.urise.webapp.util;

import com.Configurator;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfiguratorTest {

    @Test
    public void TestExistProperty() {
        final String URL_PROPERTIES = "db.url";
        final String expectedURL = "jdbc:postgresql://localhost:5432/resumes";
        final String actualURL = Configurator.get().getProperty(URL_PROPERTIES);
        Assert.assertEquals(expectedURL, actualURL);
    }

    @Test
    public void TestNotExistProperty() {
        final String URL_PROPERTIES = "not.exist";
        final String actualURL = Configurator.get().getProperty(URL_PROPERTIES);
        Assert.assertNull(actualURL);
    }
}