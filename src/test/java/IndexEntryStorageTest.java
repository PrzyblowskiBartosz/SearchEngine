import com.findwise.IndexEntry;
import com.findwise.IndexEntryImpl;
import com.findwise.storage.IndexEntryStorage;
import com.findwise.storage.IndexEntryStorageImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

public class IndexEntryStorageTest {

    IndexEntryStorage storage = IndexEntryStorageImpl.getInstance();

    @After
    public void crearStorage() {
        storage.clearContext();
    }

   @Test
    public void Should_Create_Index_Entry_Index() {
        prepareStorage();

        Optional<Set<IndexEntry>> result = storage.getIndexEntriesByToken("brown");

        Assert.assertTrue("Results should not be empty", result.isPresent());
        Assert.assertEquals("Results should contains 3 indexes", 3, result.get().size());

        IndexEntry indexEntry = result.get().iterator().next();
        Assert.assertTrue("Result should have id and score", indexEntry.getId()!= null && indexEntry.getScore() != 0.0);
    }

    @Test
    public void Should_Update_Index_Entry_Scores() {
        storage.addEditIndexEntry("fox", "doc1", 0.5);
        storage.addEditIndexEntry("fox", "doc1", 1.0);

        Optional<Set<IndexEntry>> result = storage.getIndexEntriesByToken("fox");

        Assert.assertTrue("Result should not be empty", result.isPresent());
        Assert.assertEquals("Result should have size = 1", 1, result.get().size());
        Assert.assertEquals("Result should have score = 1.0", 1.0, result.get().iterator().next().getScore(), 0.0);
    }

    private void prepareStorage() {
        List<IndexEntry> brown = Stream.of(
                (IndexEntry) new IndexEntryImpl("doc1", 1.0),
                new IndexEntryImpl("doc3", 1.2),
                new IndexEntryImpl("doc4", 1.3)).toList();

        List<IndexEntry> fox = Stream.of(
                (IndexEntry) new IndexEntryImpl("doc2", 1.4),
                new IndexEntryImpl("doc4", 1.5),
                new IndexEntryImpl("doc1", 1.6)).toList();

        List<IndexEntry> red = Stream.of(
                (IndexEntry) new IndexEntryImpl("doc1", 1.4),
                new IndexEntryImpl("doc2", 1.7),
                new IndexEntryImpl("doc3", 1.8)).toList();

        brown.forEach(index -> storage.addEditIndexEntry("brown", index.getId(), index.getScore()));
        fox.forEach(index -> storage.addEditIndexEntry("fox", index.getId(), index.getScore()));
        red.forEach(index -> storage.addEditIndexEntry("red", index.getId(), index.getScore()));
    }
}
