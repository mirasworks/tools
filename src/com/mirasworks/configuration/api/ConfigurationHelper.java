package com.mirasworks.configuration.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

@Deprecated
public class ConfigurationHelper {

    public ConfigurationHelper() {

    }

    /**
     * @param fileName
     *            file to load
     * @throws IOException
     */
    @Deprecated
    public Properties loadPropertieFile(String fileName) throws IOException {


        FileInputStream propertieFile = null;

        propertieFile = new FileInputStream(fileName);

        Properties p = new Properties(System.getProperties());
        p.load(propertieFile);

        // set the system properties
        System.setProperties(p);
        return p;
    }
    @Deprecated
    public static String systemPropertiesToString() {

        Properties prop = System.getProperties();
        Enumeration<?> keys = prop.propertyNames();

        TreeSet<String> set = new TreeSet<String>();
        StringBuffer strBuff = new StringBuffer();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            set.add(key + " = " + System.getProperty(key) + "\r\n");
        }

        for (String propStr : set) {
            strBuff.append(propStr);
        }
        return strBuff.toString();

    }
}
