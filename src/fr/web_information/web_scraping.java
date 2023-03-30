package web_information;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.PrintStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class web_scraping {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://fr.vikidia.org/wiki/BMW");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = in.readLine()) != null) {
            content.append(line);
        }
        in.close();
        Document doc = Jsoup.parse(content.toString(), url.toString());
        String text = doc.text();
        /* On avait un problème d'encodage UTF-8 : par exemple sur 
           l'application de l'accent, ca me sorter un caractère non reconnue 
           exemple  :  "é" -> "?"
        */
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println(text);

    }
}

