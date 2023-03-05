package search_engine_tests;

import java.nio.file.Paths;
import java.util.Scanner;
import java.util.HashSet;
import java.nio.file.InvalidPathException;

import search_engine.IndexedPage;
import search_engine.SearchEngine;
import search_engine.Lemmatizer;
import java.io.IOException;

public class SearchEngineTests {
	
	public static void main(String[] args) {
		// Tests avec le constructeur IndexedPage(String[] lines)
		IndexedPage page1 = new IndexedPage(new String[] {"http://fr.example.org", "hello:10", "world:5"});
		System.out.println(page1);
		System.out.println(page1.getPonderation("hello")); 
		System.out.println(page1.getPonderation("other"));
		IndexedPage page2 = new IndexedPage(new String[] {"http://fr.example2.org", "hello:5", "france:2"});
		System.out.println("Degre de similarite des deux pages: " + page1.proximity(page2) + "\n");
		
		//Tests avec le constructeur IndexedPage(String text)
		IndexedPage page3 = new IndexedPage("La lumière éclaire le ciel bleu, les oiseaux chantent et la vie est belle. Les couleurs de l'arc-en-ciel sont magnifiques et les rayons du soleil réchauffent la terre. Les océans sont profonds et les montagnes sont hautes. Les fleurs sont colorées et les arbres sont verts. Une brise légère souffle et la pluie tombe doucement. La nuit est calme et les étoiles brillent. La nature est magique et la vie est merveilleuse. Le monde est grand et les possibilités sont infinies. La joie et l'amour sont partout et la paix est possible. Nous sommes tous connectés et nous devons respecter la terre.");
		IndexedPage page4 = new IndexedPage("La lumière éclaire le ciel bleu et les oiseaux chantent leur mélodie enchanteresse, les couleurs de l'arc-en-ciel étincelantes et les rayons du soleil réchauffant la terre. Les océans profonds, les montagnes hautes et les fleurs vibrantes avec leurs couleurs variées. Les arbres verts et une brise légère qui souffle au loin. La pluie tombant doucement et les étoiles brillant dans le ciel nocturne. La nature magique et la vie merveilleuse. Le monde grand et les possibilités infinies. La joie et l'amour répandus partout et la paix possible. Nous sommes tous connectés et devons respecter notre planète. La beauté du monde nous offre une grande variété de merveilles à admirer et à célébrer. La liberté et le bonheur à portée de main. Une chance de vivre en harmonie avec la nature et de profiter de la vie. La lumière, l'espoir et l'amour pour tous. La vie est précieuse et nous devons la protéger et l'apprécier.");
		System.out.println(page4.toString() + " La page 4 a pour norme : " + page4.getNorm());
		System.out.println("Le nombre d'occurrences du mot 'lumière' dans la page 4 est : " + page4.getCount("lumiere"));
		System.out.println("valeur de la coordonnée correspondant au mot 'lumière' : " + page4.getPonderation("lumiere"));
		System.out.println("Degré de similarité entre la page 3 et page 4 : " + page3.proximity(page4));
			
		
		Lemmatizer lemmatizer = null;
		try {
			lemmatizer = new Lemmatizer("french_dictionary.txt");
		} catch (IOException e) {
			System.err.println("Erreur d'entrée ou de sortie : " + e.getMessage());
			return;
		}
		
		HashSet<String> blacklist = new HashSet<String>();
        blacklist.add("and");
        blacklist.add("the");
        blacklist.add("of");

		String lemmatizedText = lemmatizer.processText(page3.getText());
		IndexedPage lemmatizedPage = new IndexedPage(lemmatizedText);

		System.out.println("Texte lemmatisé");
		System.out.println(lemmatizedPage.getText());
		
	}
	
}
	

