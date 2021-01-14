package by.vozniak.crawler;

import by.vozniak.configuration.ConfigDownloader;
import by.vozniak.tools.CSVReader;
import by.vozniak.tools.CSVWriter;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *The Crawler class is an implementation of a web crawler that searches
 * for specified terms and records the number of their displays on each page.
 * The search depth from the start page is 8, the maximum number of pages is 10000.
 * At startup the program reads the configuration file passed to the arguments.
 * @author Vladimir Vozniak
 * @version 1.0
 */
public class Crawler {
    private static final int MAX_PAGES_QUANTITY = 10000;
    private static final int MAX_DEPTH = 8;
    private static Set<String> visitedPages = new HashSet<>();
    private static Map<String, Integer> pagesToBrowse = new LinkedHashMap<>();
    private static List<String> termsForSearch;
    private static String textFromHtml;
    private static final String USER_AGENT = "Google Chrome 53 (Win 10 x64): Mozilla/5.0 (Windows NT 10.0; WOW64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
    private static final Logger logger = Logger.getLogger(Crawler.class.getName());

    /**
     *The method uses Jsoup to connect to the page, load its content,
     * and calls the crawlPages method which iterates through LinkedHashMap and calls the method again
     * @param url - URL of web page
     * @param depth - current depth from the start page
     */
    private static void getPageContent(String url, int depth) {
        Document htmlDoc;
        Elements links;
        if(visitedPages.size() < MAX_PAGES_QUANTITY) {
            try {
                try {
                    System.out.println("URL: " + url);
                    System.out.println("depth: " + depth);
                    htmlDoc = Jsoup.connect(url).userAgent(USER_AGENT).get();
                    links = htmlDoc.select("a[href]");
                    textFromHtml = htmlDoc.body().text();
                    searchWord(url);
                    visitedPages.add(url);
                    if(!pagesToBrowse.isEmpty())
                        pagesToBrowse.remove(url);
                    ++depth;
                    for (Element link : links) {
                        String absUrl = link.absUrl("href");
                        if(!visitedPages.contains(absUrl))
                            pagesToBrowse.put(absUrl, depth);
                    }
                    crawlPages();
                } catch (HttpStatusException | MalformedURLException ex) {
                    logger.log(Level.WARNING, "Exception: ", ex);
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Exception: ", e);
            }
        }
    }

    /**
     *The method iterates through LinkedHashMap and calls the method getPageContent().
     */
    private static void crawlPages() {
        for (Map.Entry<String, Integer> entry : pagesToBrowse.entrySet()) {
            String url = entry.getKey();
            int depth = entry.getValue();
            if(depth < MAX_DEPTH)
                getPageContent(url, depth);
        }
    }

    /**
     *The method searches for the specified terms in the page text and counts the number
     * of repetitions of each and the total result on the page. The information is placed in
     * an ArrayList and passed to the write method of the CSVWriter class for writing to a csv file.
     * @param url - URL of web page
     */
    private static void searchWord(String url) {
        List<String> statInfo = new ArrayList<>();
        String[] words = textFromHtml.split("\\s+");
        long sum = 0;
        statInfo.add(url);
        for (String term : termsForSearch) {
            long quantity = Arrays.stream(words)
                    .map(String::toLowerCase)
                    .filter(s -> s.contains(term.toLowerCase()))
                    .count();
            sum += quantity;
            statInfo.add(String.valueOf(quantity));
        }
        statInfo.add(String.valueOf(sum));
        CSVWriter.write(statInfo);
    }

    /**
     * Method for getting the value of the {@link Crawler#logger field}
     * @return Logger
     */
    public static Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(
                    Crawler.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
        logger.setLevel(Level.ALL);
        logger.info("The app is running...");
        ConfigDownloader downloader = null;
        try {
            logger.info("Downloading the configuration file...");
            downloader = new ConfigDownloader(args[0]);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
            logger.log(Level.SEVERE, "Exception: ", ex);
        }
        assert downloader != null;
        termsForSearch = downloader.getTermsForSearch();
        CSVWriter.setHeader(termsForSearch);
        CSVWriter.setPathToCSV(downloader.getLocation() + "statistic.csv");
        logger.info("Exploring the pages...");
        getPageContent(downloader.getSeed(), 0);
        CSVWriter.close();
        logger.fine("The app has shut down");
        CSVReader reader = new CSVReader(downloader.getLocation());
        reader.read();
    }
}
