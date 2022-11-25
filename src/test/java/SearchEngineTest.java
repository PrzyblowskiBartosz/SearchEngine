
import com.findwise.SearchEngine;
import com.findwise.SearchEngineImpl;
import org.junit.Test;

public class SearchEngineTest {

    SearchEngine searchEngine = SearchEngineImpl.getInstance();

    @Test
    public void indexDocumentTest() {
        searchEngine.indexDocument("document1", "the brown fox jumped over the brown dog");


    }

    @Test
    public void searchTest() {
        searchEngine.search("brown");


    }
}
