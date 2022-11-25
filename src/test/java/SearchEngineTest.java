import com.findwise.SearchEngine;
import com.findwise.SearchEngineImpl;
import org.junit.Test;

public class SearchEngineTest {

    SearchEngine searchEngine = SearchEngineImpl.getInstance();

    @Test
    public void searchEngineTest() {
        searchEngine.indexDocument("document1", "the brown fox jumped over the brown dog");
    }
}
