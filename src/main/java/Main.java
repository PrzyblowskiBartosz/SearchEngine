import com.findwise.SearchEngine;
import com.findwise.SearchEngineImpl;
import com.findwise.storage.DocumentStorage;
import com.findwise.storage.DocumentStorageImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        DocumentStorage storage = DocumentStorageImpl.getInstance();

        SearchEngine searchEngine = SearchEngineImpl.getInstance();


    }
}