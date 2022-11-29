import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import com.findwise.SearchEngineImpl;
import com.findwise.storage.DocumentStorage;
import com.findwise.storage.DocumentStorageImpl;
import com.findwise.storage.IndexEntryStorage;
import com.findwise.storage.IndexEntryStorageImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SearchEngineTest {

    IndexEntryStorage indexEntryStorage = IndexEntryStorageImpl.getInstance();
    DocumentStorage documentStorage = DocumentStorageImpl.getInstance();
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
        searchEngine.indexDocument("Document1",  "name Bartosz");
        searchEngine.indexDocument("Document2",  "car Honda");
        searchEngine.indexDocument("Document3",  "car drive Honda Civic");
        searchEngine.indexDocument("Document4",  "car Civic model Honda");

        Optional<Set<IndexEntry>> result =  indexEntryStorage.getIndexEntriesByToken("honda");

        Assert.assertTrue("Should not be empty", result.isPresent());
        Assert.assertEquals("Should have one size", 3, result.get().size());
        Assert.assertEquals("Token 'honda', should have different scores", 0.03125, result.get().iterator().next().getScore(), 0.001);
    }

    @Test
    public void Should_Find_Results_For_Token() {
        searchEngine.indexDocument("Document1",  "My name is Bartosz");
        searchEngine.indexDocument("Document2",  "My car is Honda");
        searchEngine.indexDocument("Document3",  "The car I drive is Honda Civic");
        searchEngine.indexDocument("Document4",  "My car is a Civic model by Honda");

        List<IndexEntry> results = searchEngine.search("Honda");

        Assert.assertFalse("Should not be empty", results.isEmpty());
        Assert.assertEquals("Should have one size", 3, results.size());
        Assert.assertEquals("First should be Document2", "Document2", results.get(0).getId());
        Assert.assertEquals("First should be Document3", "Document3", results.get(1).getId());
        Assert.assertEquals("First should be Document4", "Document4", results.get(2).getId());
    }

    @After
    public void clearStorages() {
        indexEntryStorage.clearContext();
        documentStorage.clearContext();
    }

}
