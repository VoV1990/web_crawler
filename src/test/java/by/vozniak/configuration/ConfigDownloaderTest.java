package by.vozniak.configuration;

import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

public class ConfigDownloaderTest {
    @Test
    public void testGetResource() {
        ConfigDownloader downloader = new ConfigDownloader("config.properties");
        String resource = downloader.getResource();
        assertEquals("config.properties", resource);
    }

    @Test
    public void testGetSeed() {
        ConfigDownloader downloader = new ConfigDownloader("config.properties");
        String seed = downloader.getSeed();
        assertEquals("https://www.taste.com.au/baking/articles/top-50-cakes/4zag3onm", seed);
    }

    @Test
    public void testGetTermsForSearch() {
        ConfigDownloader downloader = new ConfigDownloader("config.properties");
        List<String> terms = downloader.getTermsForSearch();
        assertEquals(3, terms.size());
        assertTrue(terms.contains("Cake"));
    }

    @Test
    public void testGetLocation() {
        ConfigDownloader downloader = new ConfigDownloader("config.properties");
        URL path = getClass().getProtectionDomain().getCodeSource().getLocation();
        int lastSlash = path.getFile().lastIndexOf("/");
        String location = path.getFile().substring(1, lastSlash + 1);
        File file = new File(location);
        File file1 = new File(downloader.getLocation());
        assertEquals(file.getParent(), file1.getParent());
    }
}