import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import com.findwise.SearchEngineImpl;
import com.findwise.storage.IndexEntryStorage;
import com.findwise.storage.IndexEntryStorageImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

public class SearchEngineTest {

    IndexEntryStorage indexEntryStorage = IndexEntryStorageImpl.getInstance();
    SearchEngine searchEngine = SearchEngineImpl.getInstance();

    @Test
    public void Should_Index_Document() {
        searchEngine.indexDocument("document1", "the brown fox jumped over the brown dog");

        Optional<Set<IndexEntry>> result =  indexEntryStorage.getIndexEntriesByToken("brown");

        Assert.assertTrue("Should not be empty", result.isPresent());
        Assert.assertEquals("Should have one size", 1, result.get().size());
    }

    @Test
    public void Should_Update_Indexes_Scores_Values_In_Storage() {
        searchEngine.indexDocument("Document1",  "My name is Bartosz");
        searchEngine.indexDocument("Document2",  "My car is Honda");
        searchEngine.indexDocument("Document3",  "The car I drive is Honda Civic");
        searchEngine.indexDocument("Document4",  "My car is a Civic model by Honda");

        Optional<Set<IndexEntry>> result =  indexEntryStorage.getIndexEntriesByToken("honda");

        Assert.assertTrue("Should not be empty", result.isPresent());
        Assert.assertEquals("Should have one size", 1, result.get().size());
        Assert.assertEquals("Token 'honda', should have different scores", 0.0625, result.get().iterator().next().getScore(), 0.0001);
    }

    @Test
    public void Should_Find_Two_Results_For_Token() {
        searchEngine.search("brown");
    }

}
