package by.vozniak.tools;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CSVReaderTest {
    @Test
    public void testRead() {
        CSVReader reader = new CSVReader("src/test/resources/");
        List<String> terms = new ArrayList<>();
        terms.add("Term1");
        terms.add("Term2");
        terms.add("Term3");
        CSVWriter.setHeader(terms);
        reader.read();
        CSVWriter.close();
        File file = new File("src/test/resources/top10.csv");
        assertTrue(file.exists());
    }
}