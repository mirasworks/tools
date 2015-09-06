package com.mirasworks.configuration.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mirasworks.tools.file.FileUtil;

/**
 *
 * @author Koda
 *         TODO see
 *         http://commons.apache.org/proper/commons-io/javadocs/api-release
 *         /index.html?org/apache/commons/io/monitor/package-summary.html
 *         for file watching
 */
public class ConfigurationManager {

    private Logger l = LoggerFactory.getLogger(ConfigurationManager.class);

    private final static String configurationFolderPropertieName = "configurationFolder";
    private final static String pluginPathPropertieName = "plugin.path";
    private String pluginPath = "";
    private String defaultConfigurationFolder = "_conf/dev/";
    private String configurationFolder = "";
    private boolean hasLogger = false;
    private FileUtil fileUtil = new FileUtil();
    protected Properties properties = null;
    SortedMap<String, String> configurationMap = new TreeMap<String, String>();

    public ConfigurationManager() {

    }

    public String getPluginPath() {
        return pluginPath;
    }

    /**
    * check if the configuration folder had been set in system properties
    * if it had been set it verify if the folder exists and application has
    * credentials access. If not exists it try to create it
    *
    * @param folder
    * @return
    */
    public boolean initConfiguration() {

        // try to early configure with default
        loadLoggingSystem(defaultConfigurationFolder);

        if (systemHasPropertie(configurationFolderPropertieName)) {
            configurationFolder = System.getProperty(configurationFolderPropertieName);
        } else {
            System.err.println("configuration folder must be specified with  -Dargs exemple : -D"
                    + configurationFolderPropertieName + "=_conf/dev/");
            System.out.println("try to use default configuration file " + defaultConfigurationFolder);
            configurationFolder = defaultConfigurationFolder;
        }

        if (systemHasPropertie(pluginPathPropertieName)) {
            pluginPath = System.getProperty(pluginPathPropertieName);
        } else {
            System.out.println("plugin path had not been specified with  -Dargs exemple : -D"
                    + pluginPathPropertieName + "=c:/_dev/_src/hello");
            System.out.println("Application launched without plugins enabled");

        }

        // check folder exists
        if (!fileUtil.folderExists(configurationFolder)) {
            if (createConfigurationFolder(configurationFolder) == false) {
                System.err.println("fail to create configuration folder " + configurationFolder);
                return false;
            }
        }

        // reload logging with correct path
        if (!loadLoggingSystem(configurationFolder)) {
            System.err.println("fail to load logging system");
            return false;
        }
        if (!loadConfiguration()) {
            l.error("fail to load configuration");
            return false;
        }
        return true;
    }

    /**
    * create the configuration folder if not exists
    *
    * @return
    */
    private boolean createConfigurationFolder(String configurationFolder) {
        try {
            return fileUtil.mkdir(configurationFolder);

        } catch (SecurityException e) {
            if (hasLogger() == true) {
                l.info("any right access to create {}", configurationFolder);
            } else {
                System.err.println("any rights to create " + configurationFolder);
            }
            recoverFailCreateConfFolder();
            return false;
        }
    }

    private void recoverFailCreateConfFolder() {
        System.err.println("try to recreate the configuration folder fail [" + configurationFolder
                + "] change the access right to enable the application to access on it");
        // TODO implement command line
        // System.out.println("then type <reloadconfig> command");
    }

    public boolean systemHasPropertie(String propertie) {
        String prop = System.getProperty(propertie);
        return prop != null && !prop.isEmpty();
    }

    public boolean loadLoggingSystem(String folder) {
        System.out.println("Configuration path " + folder + "/log4j.properties");
        PropertyConfigurator.configureAndWatch(folder + "/log4j.properties", 200);
        hasLogger = true;
        return true;
    }

    public String getPropertieFilePath() {
        return getConfigurationFolder() + "app.conf";
    }

    public boolean loadConfiguration() {
        try {
            properties = loadPropertieFile(getPropertieFilePath());
            return true;
        } catch (Exception e) {

            if (hasLogger() == true) {
                l.error("propertie file could not be loaded : {}", e.getMessage());
            } else {
                System.err.println("propertie file could not be loaded " + e.getMessage());
            }
        }
        return false;

    }


    /**
     * return a configuration, config are available from configuration files
     * or from system properties
     * @param key
     * @param defaultValue
     * @return
     */
    public String getKey(String key, String defaultValue)  {
        if (key == null) {
            return "";
        }

        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            l.warn("{} not configured. Default value set to {}",key);
            return defaultValue;
        } else {
            return value;
        }
    }

    private Properties loadPropertieFile(String fileName) throws IOException {

        FileInputStream propertieFile = null;
        Properties prop = null;
        @SuppressWarnings("rawtypes")
        Enumeration keys = null;

        propertieFile = new FileInputStream(fileName);
        prop = new Properties(System.getProperties());
        prop.load(propertieFile);
        System.setProperties(prop);

        // TODO verify if old sys prop are there
        keys = prop.propertyNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            configurationMap.put(key, System.getProperty(key));
        }

        // set the system properties
        return prop;
    }

    public String systemPropertiesToString() {

        Properties prop = System.getProperties();
        @SuppressWarnings("rawtypes")
        Enumeration keys = prop.propertyNames();

        TreeSet<String> set = new TreeSet<String>();
        StringBuffer strBuff = new StringBuffer();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            set.add(key + " = " + System.getProperty(key) + "\r\n");
            configurationMap.put(key, System.getProperty(key));
        }

        for (String propStr : set) {
            strBuff.append(propStr);
        }
        return strBuff.toString();

    }

    public boolean hasLogger() {
        return hasLogger;
    }

    private String getConfigurationFolder() {
        return configurationFolder + "/";
    }

    @Override
    public String toString() {

        StringBuffer strBuff = new StringBuffer();
        strBuff.append(super.toString());
        strBuff.append(" : configuration \r\n");
        for (Map.Entry<String,String> entry : configurationMap.entrySet()) {
            strBuff.append(entry.getKey() );
            strBuff.append( ": ");
            strBuff.append(entry.getValue());
            strBuff.append("\r\n");
        }
        return strBuff.toString();
    }



}
