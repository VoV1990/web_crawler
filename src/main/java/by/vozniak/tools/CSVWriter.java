package by.vozniak.tools;

import by.vozniak.crawler.Crawler;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Class CSVWriter creates a csv file and writes data to it
 */
public class CSVWriter {
    private static String pathToCSV;
    private static CSVFormat header;
    private static BufferedWriter writer;
    private static CSVPrinter csvPrinter;

    /**
     * The method writes the transmitted data to a file
     * @param statInfo - list that stores data for writing to a csv file
     */
    public static void write(List<String> statInfo) {
        try {
            csvPrinter.printRecord(statInfo);
            csvPrinter.flush();
        } catch (IOException e) {
            Crawler.getLogger().log(Level.SEVERE, "Failed to write to file: ", e);
        }
    }
    /**
     * Method for getting the value of the {@link CSVWriter#pathToCSV}
     * @return returns the path to the csv file
     */
    public static String getPathToCSV() {
        return pathToCSV;
    }

    /**
     * Method for getting the value of the {@link CSVWriter#header}
     * @return returns the header of the csv file
     */
    public static CSVFormat getHeader() {
        return header;
    }

    /**
     * The method defines the path to the csv file {@link CSVWriter#pathToCSV}
     * @param pathToCSV - the path to the csv file
     */
    public static void setPathToCSV(String pathToCSV) {
        CSVWriter.pathToCSV = pathToCSV;
        initializeWriter();
    }

    /**
     * The method initializes CSVPrinter
     */
    private static void initializeWriter() {
        try {
            writer = Files.newBufferedWriter(Paths.get(pathToCSV));
            csvPrinter = new CSVPrinter(writer, header);
        } catch (IOException e) {
            Crawler.getLogger().log(Level.SEVERE, "Exception: ", e);
        }
    }

    /**
     * The method closes CSVPrinter
     */
    public static void close() {
        try {
            csvPrinter.flush();
            csvPrinter.close();
        } catch (IOException e) {
            Crawler.getLogger().log(Level.WARNING, "Exception: ", e);
        }
    }

    /**
     * The method defines the header of the csv file {@link CSVWriter#header}
     * @param columnNames - a list that stores the terms obtained from the configuration file
     *                    and that need to be found. Forms a header from them
     */
    public static void setHeader(List<String> columnNames) {
        List<String> columns = new ArrayList<>(columnNames.size());
        columns.addAll(0, columnNames);
        columns.add(0, "URL");
        columns.add("Sum");
        String[]array = columns.toArray(new String[0]);
        header = CSVFormat.DEFAULT.withHeader(array);
    }

    public static BufferedWriter getWriter() {
        return writer;
    }

    public static CSVPrinter getCsvPrinter() {
        return csvPrinter;
    }
}
