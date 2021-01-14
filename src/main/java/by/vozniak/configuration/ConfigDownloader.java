package by.vozniak.configuration;

import by.vozniak.crawler.Crawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

/**
 *ConfigDownloader class reads the configuration file, the path to which is passed
 * to it in the constructor and sets the values of seed (the starting url for the search)
 * and the terms to be searched for.
 */
public class ConfigDownloader {
    private String seed;
    private String resource;
    private List<String> termsForSearch = new ArrayList<>();
    private String location;

    /**
     * Constructor - creating a new object, sets the value to the resource field
     * {@link ConfigDownloader#resource}
     */
    public ConfigDownloader(String resource) {
        this.resource = resource;
        fileDownloading();
    }

    /**
     *The method is called from the class constructor when a new object is created.
     * Loads the configuration file (properties), sets the value to the seed field
     * {@link ConfigDownloader#seed} and the termsForSearch field {@link ConfigDownloader#termsForSearch},
     * and also determines the current location (path) {@link ConfigDownloader#location} of the program.
     */
    private void fileDownloading() {
        Crawler.getLogger().fine("Download completed");
        Properties property = new Properties();
        Set<Object> set;
        URL path = getClass().getProtectionDomain().getCodeSource().getLocation();
        int lastSlash = path.getFile().lastIndexOf("/");
        location = path.getFile().substring(1, lastSlash + 1);
        try(FileInputStream input = new FileInputStream(resource)) {
            Crawler.getLogger().info("Reading the configuration file...");
            property.load(input);
            set = property.keySet();
            Crawler.getLogger().fine("The configuration file has been read");
            for (Object s : set) {
                String key = (String) s;
                if (key.equals("seed")) {
                    seed = property.getProperty(key);
                } else {
                    termsForSearch.add(property.getProperty(key));
                }
            }
        } catch (IOException e) {
            System.err.println("Couldn't read config");
            Crawler.getLogger().log(Level.SEVERE, "Exception: ", e);
        }
    }

    /**
     * Method for getting the value of the {@link ConfigDownloader#resource}
     * @return returns the path to the configuration file
     */
    public String getResource() {
        return resource;
    }

    /**
     * Method for getting the value of the {@link ConfigDownloader#seed}
     * @return returns the starting url for the search
     */
    public String getSeed() {
        return seed;
    }

    /**
     * Method for getting the value of the {@link ConfigDownloader#termsForSearch}
     * @return returns the list of terms to be searched for
     */
    public List<String> getTermsForSearch() {
        return termsForSearch;
    }

    /**
     * Method for getting the value of the {@link ConfigDownloader#location}
     * @return returns the current location of the program
     */
    public String getLocation() {
        return location;
    }
}
