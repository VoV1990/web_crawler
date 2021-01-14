package by.vozniak.crawler;

import by.vozniak.configuration.ConfigDownloaderTest;
import by.vozniak.tools.CSVReaderTest;
import by.vozniak.tools.CSVWriterTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConfigDownloaderTest.class,
        CrawlerTest.class,
        CSVWriterTest.class,
        CSVReaderTest.class})
public class CrawlerSuite {
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

}