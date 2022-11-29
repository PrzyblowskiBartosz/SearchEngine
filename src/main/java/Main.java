import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import com.findwise.SearchEngineImpl;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        System.out.println("Hello Simple Search Engine!");

        SearchEngine searchEngine = SearchEngineImpl.getInstance();

        File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        final File folder = new File(jarFile.getPath() + "/documents/");

        System.out.println("Indexed documents:");
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            try {
                String documentName = fileEntry.getName();
                String documentContent = Files.readString(Path.of(fileEntry.toURI()));
                System.out.println(documentName + ": " + documentContent);

                searchEngine.indexDocument(documentName, documentContent);
            } catch (Exception ex) {
                System.out.println("Error during processing file " + fileEntry.getName() + ". Skip file.");
            }
        }

        Scanner input = new Scanner (System.in);
        String term = "";
        while(!Objects.equals(term, "0")) {
            System.out.println("\nTo exit insert '0'");
            System.out.print("Input term: ");
            term = input.nextLine();
            if (!Objects.equals(term, "0")) {
                List<String> output = searchEngine.search(term).stream().map(IndexEntry::getId).toList();
                System.out.println(output);
            }
        }
    }
}