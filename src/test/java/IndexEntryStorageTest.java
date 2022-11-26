import com.findwise.model.IndexEntry;
import com.findwise.model.IndexEntryImpl;
import com.findwise.storage.IndexEntryStorage;
import com.findwise.storage.IndexEntryStorageImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

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
}
