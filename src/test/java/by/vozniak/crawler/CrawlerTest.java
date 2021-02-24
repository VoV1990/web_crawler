package by.vozniak.crawler;

import by.vozniak.tools.CSVWriter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class CrawlerTest {
    public CrawlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testMain() {
        assertTrue(true);
    }

    @Test
    public void testGetLogger() {
        Logger logger = Logger.getLogger(Crawler.class.getName());
        assertEquals(logger, Crawler.getLogger());
    }

    @Test
    public void testGetUserAgent() {
        String userAgent = "Google Chrome 53 (Win 10 x64): Mozilla/5.0 (Windows NT 10.0; WOW64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
        assertEquals(userAgent, Crawler.getUserAgent());
    }

    @Test
    public void testGetPageContent() {
        List<String> termsForSearch = Arrays.asList("Cake", "Dinner");
        Crawler.setTermsForSearch(termsForSearch);
        CSVWriter.setHeader(termsForSearch);
        CSVWriter.setPathToCSV("src/test/data/statistic.csv");
        Crawler.setMaxDepth(0);
        Crawler.getPageContent(
            "https://www.taste.com.au/baking/articles/top-50-cakes/4zag3onm", 0);
        assertFalse(Crawler.getPagesToBrowse().isEmpty());
        assertEquals(0, Crawler.getMaxDepth());
        assertEquals(10000, Crawler.getMaxPagesQuantity());
        assertFalse(Crawler.getPagesToBrowse().isEmpty());
        assertNotNull(Crawler.getTermsForSearch());
        assertFalse(Crawler.getVisitedPages().isEmpty());
    }

}