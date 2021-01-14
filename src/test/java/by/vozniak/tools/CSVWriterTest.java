package by.vozniak.tools;

import org.apache.commons.csv.CSVFormat;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CSVWriterTest {
    public CSVWriterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testSetHeader() {
        List<String> terms = new ArrayList<>();
        terms.add("Term1");
        terms.add("Term2");
        terms.add("Term3");
        String[]array = {"URL", "Term1", "Term2", "Term3", "Sum"};
        CSVFormat header = CSVFormat.DEFAULT.withHeader(array);
        CSVWriter.setHeader(terms);
        CSVFormat headerTest = CSVWriter.getHeader();
        assertEquals(header, headerTest);
    }

    @Test
    public void testSetPathToCSV() {
        CSVWriter.setPathToCSV("/data/statistic.csv");
        String path = CSVWriter.getPathToCSV();
        assertEquals("/data/statistic.csv", path);
    }

    @Test
    public void testWrite() {
        List<String> terms = new ArrayList<>();
        terms.add("Term1");
        terms.add("Term2");
        terms.add("Term3");
        CSVWriter.setHeader(terms);
        CSVWriter.setPathToCSV("src/test/data/statistic.csv");
        List<String> statistic = new ArrayList<>();
        statistic.add("http://asds.com");
        statistic.add("1");
        statistic.add("2");
        statistic.add("3");
        statistic.add("6");
        CSVWriter.write(statistic);
        CSVWriter.close();
        File file = new File("src/test/data/statistic.csv");
        assertTrue(file.exists());
    }

}