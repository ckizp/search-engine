package search_engine;

public class Main {
	public static void main(String[] args) {
		String cheminBin = "";
		String[] chemins = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
		for (String chemin : chemins) {
		    if (chemin.contains(Main.class.getName().replace(".", "/"))) {
		        cheminBin = chemin.substring(0, chemin.lastIndexOf(System.getProperty("file.separator")) + 1);
		        break;
		    }
		}
		
		String cheminSource = System.getProperty("user.dir") + "/src/fr/" + Main.class.getPackage().getName().replace(".", "/");
		
		System.out.println(cheminSource);
	}
}
