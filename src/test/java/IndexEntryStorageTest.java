import com.findwise.model.IndexEntry;
import com.findwise.model.IndexEntryImpl;
import com.findwise.storage.IndexEntryStorage;
import com.findwise.storage.IndexEntryStorageImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IndexEntryStorageTest {

    IndexEntryStorage storage = IndexEntryStorageImpl.getInstance();

    @After
    public void crearStorage() {
        storage.clearContext();
    }

    @Test
    public void Should_Insert_Index_Entry_Element() {
        IndexEntry indexEntry = new IndexEntryImpl("word", 1.1);

        storage.addIndexEntries("Document1", Collections.singletonList(indexEntry));
        Optional<IndexEntry> result = storage.getIndexEntryById("Document1", "word");

        Assert.assertTrue("Should not be empty", result.isPresent());
        Assert.assertEquals("Should be equals", indexEntry, result.get());
    }

    @Test
    public void Should_Update_Index_Entry_Scores_Element() {
        IndexEntry indexEntry = new IndexEntryImpl("word", 1.1);
        IndexEntry indexEntry2 = new IndexEntryImpl("record", 1.2);
        storage.addIndexEntries("Document1", Arrays.asList(indexEntry, indexEntry2));

        indexEntry2 = new IndexEntryImpl("record", 2.2);
        storage.updateIndexEntry("Document1", indexEntry2);
        Optional<IndexEntry> result = storage.getIndexEntryById("Document1", "record");

        Assert.assertTrue("Should not be empty", result.isPresent());
        Assert.assertEquals("Should be equals", 2.2, result.get().getScore(), 0.0);
    }

    @Test
    public void Should_Return_Empty_Optional() {
        Optional<IndexEntry> result = storage.getIndexEntryById("Document1", "record");

        Assert.assertTrue("Should not be empty", result.isEmpty());
    }

    @Test
    public void Should_Return_Map_With_Documentc_Containing_Looked_For_Token() {
        prepareStorage();

        Map<String, List<IndexEntry>> results = storage.getDocumentsWithToken("fox");

       Assert.assertEquals("Returned map should contain 3 elements", 3, results.size());
    }

    @Test
    public void Should_Return_Empty_Map_When_Look_For_Uknown_Word() {
        prepareStorage();

        Map<String, List<IndexEntry>> results = storage.getDocumentsWithToken("uknown");

        Assert.assertTrue("Returned map should be empty", results.isEmpty());
    }

    private void prepareStorage() {
         //doc1
        List<IndexEntry> doc1Tokens = Stream.of(
                (IndexEntry) new IndexEntryImpl("brown", 1.0),
                new IndexEntryImpl("fox", 1.2),
                new IndexEntryImpl("jumped", 1.3)      ).toList();

        //doc2
        List<IndexEntry> doc2Tokens = Stream.of(
                (IndexEntry) new IndexEntryImpl("brown", 1.4),
                new IndexEntryImpl("fox", 1.5),
                new IndexEntryImpl("jumped", 1.6)).toList();

        //doc3
        List<IndexEntry> doc3Tokens = Stream.of(
                (IndexEntry) new IndexEntryImpl("red", 1.4),
                new IndexEntryImpl("fox", 1.7),
                new IndexEntryImpl("bit", 1.8)).toList();

        storage.addIndexEntries("Document1", doc1Tokens);
        storage.addIndexEntries("Document2", doc2Tokens);
        storage.addIndexEntries("Document3", doc3Tokens);
    }
}
