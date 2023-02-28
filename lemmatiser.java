import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Lemmatizer {
    private HashMap<String, String> lemmas;

    public Lemmatizer(String filename) throws IOException {
        this.lemmas = new HashMap<String, String>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                String word = parts[0];
                String lemma = parts[1];
                this.lemmas.put(word, lemma);
            }
        }
        br.close();
    }

    public String lemmatize(String word) {
        String lemma = this.lemmas.get(word);
        if (lemma != null) {
            return lemma;
        } else {
            return word;
        }
    }

    public String processText(String text) {
        String[] words = text.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(lemmatize(word)).append(" ");
        }
        return sb.toString().trim();
    }
}
