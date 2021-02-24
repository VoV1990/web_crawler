package by.vozniak.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The class CSVReader reads the csv file of statistics generated as a result of the program,
 * sorts the data obtained from it and sends a list of 10 sites where the search terms
 * are most often found to the CSVWriter for writing to a new csv file.
 */
public class CSVReader {
    private List<String[]> statistic = new ArrayList<>();
    private String path;

    /**
     * Constructor - creating a new object, sets the value to the path field
     * {@link CSVReader#path}
     */
    public CSVReader(String path) {
        this.path = path;
    }

    /**
     * The method reads the csv file of statistics and calls the method sort()
     */
    public void read() {
        String split = ",";
        String line;
        try(BufferedReader reader = new BufferedReader(new FileReader(path + "statistic.csv"))) {
            while (reader.ready()) {
                line = reader.readLine();
                statistic.add(line.split(split));
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        sort();
    }

    /**
     * The method sorts the list from the data obtained from the csv file
     */
    private void sort() {
        statistic.remove(0);
        statistic.sort(Comparator.comparingInt(o -> -Integer.parseInt(o[o.length - 1])));
        transferToWriter();
    }

    /**
     * The method sends a list of 10 sites where the search terms
     * are most often found to the CSVWriter for writing to a new csv file
     */
    private void transferToWriter() {
        CSVWriter.setPathToCSV(path + "top10.csv");
        List<String> top10 = new ArrayList<>();
        if(!statistic.isEmpty())
            for (int i = 0; i < 10; i++) {
            top10.addAll(Arrays.asList(statistic.get(i)));
            CSVWriter.write(top10);
            top10 = new ArrayList<>();
            }
    }
}
