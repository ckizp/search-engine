package web_information;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class jsouping {

    public static void main(String[] args) throws IOException {
        String url = "https://fr.vikidia.org/wiki/Pomme";
        Document doc = Jsoup.connect(url).get();

        String title = doc.title().replace("Vikidia, ", "");

        Element content = doc.select("div.mw-parser-output").first();
        Elements paragraphs = content.select("p");
        
        StringBuilder text = new StringBuilder();
        for (Element paragraph : paragraphs) {
            text.append(paragraph.text()).append("\n");
        }
        
        System.out.println("Title: " + title);
        System.out.println("Text: " + text.toString());
    }

}
