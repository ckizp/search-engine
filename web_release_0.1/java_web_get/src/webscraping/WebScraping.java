package webscraping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.URL;

public class WebScraping {
    public static String scrapeUrl(String url) throws Exception {
        URL urlObj = new URL(url);
        URLConnection connection = urlObj.openConnection();

        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                content.append(line.replaceAll("\\<.*?>", ""));
            }
        }

        return content.toString();
    }
}
