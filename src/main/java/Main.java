import com.findwise.SearchEngine;
import com.findwise.SearchEngineImpl;
import com.findwise.SearchEngineStorage;
import com.findwise.SearchEngingeStorageImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        SearchEngineStorage storage = SearchEngingeStorageImpl.getInstance();
        // storage.addDocumentsList

        SearchEngine searchEngine = SearchEngineImpl.getInstance();


    }
}